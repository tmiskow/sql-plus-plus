package io.github.tmiskow.sqlplusplusparser.expressions

import io.github.tmiskow.sqlplusplusparser._

class ExpressionParserSpec extends UnitSpec {
  override val lexer = new Object with ExpressionLexer
  override val parser = new Object with ExpressionParser

  it should "parse numeric literals tokens" in {
    val strings = List("17", "3.14", "-16.5f")
    for (string <- strings) {
      val result = parseString(string)
      result shouldBe NumberAst(NumericLiteralToken(string))
    }
  }

  it should "parse addition and subtraction tokens" in {
    val result = parseString("17 + 3.14 - 16.5f")
    result shouldBe
      SubtractionAst(
        AdditionAst(
          NumberAst(NumericLiteralToken("17")),
          NumberAst(NumericLiteralToken("3.14"))),
        NumberAst(NumericLiteralToken("16.5f")))
  }

  it should "parse multiplication and division tokens" in {
    val result = parseString("17 * 3.14 + 2137 / 16.5f")
    result shouldBe
      AdditionAst(
        MultiplicationAst(
          NumberAst(NumericLiteralToken("17")),
          NumberAst(NumericLiteralToken("3.14"))),
        DivisionAst(
          NumberAst(NumericLiteralToken("2137")),
          NumberAst(NumericLiteralToken("16.5f"))))
  }

  it should "parse integer division token" in {
    val result = parseString("17 DIV 3")
    result shouldBe
      IntegerDivisionAst(
        NumberAst(NumericLiteralToken("17")),
        NumberAst(NumericLiteralToken("3")))
  }

  it should "parse modulo tokens" in {
    val result = parseString("17 MOD 3 + 57 % 5")
    result shouldBe
      AdditionAst(
        ModuloAst(
          NumberAst(NumericLiteralToken("17")),
          NumberAst(NumericLiteralToken("3"))),
        ModuloAst(
          NumberAst(NumericLiteralToken("57")),
          NumberAst(NumericLiteralToken("5"))))
  }

  it should "parse exponentiation expressions" in {
    val result = parseString("2 * 17 ^ 3.14 * 2137 ^ 16.5f ^ 3")
    result shouldBe
      MultiplicationAst(
        MultiplicationAst(
          NumberAst(NumericLiteralToken("2")),
          ExponentiationAst(
            NumberAst(NumericLiteralToken("17")),
            NumberAst(NumericLiteralToken("3.14")))),
        ExponentiationAst(
          ExponentiationAst(
            NumberAst(NumericLiteralToken("2137")),
            NumberAst(NumericLiteralToken("16.5f"))),
          NumberAst(NumericLiteralToken("3"))))
  }

  it should "parse parenthesised expressions" in {
    val string = "(17 * (2137 - 5)) ^ (3.14 / (4.5 + 0f))"
    val result = parseString(string)
    result shouldBe
      ExponentiationAst(
        MultiplicationAst(
          NumberAst(NumericLiteralToken("17")),
          SubtractionAst(
            NumberAst(NumericLiteralToken("2137")),
            NumberAst(NumericLiteralToken("5")))),
        DivisionAst(
          NumberAst(NumericLiteralToken("3.14")),
          AdditionAst(NumberAst(NumericLiteralToken("4.5")),
            NumberAst(NumericLiteralToken("0f")))))
  }

  it should "return error on incomplete expressions" in {
    val strings = List("17 +", "-", "3 + (5")
    for (string <- strings) {
      val tokens = lexer(string).right.get
      val result = parser(tokens).left.get
      result shouldBe a[ParserError]
    }
  }
}
