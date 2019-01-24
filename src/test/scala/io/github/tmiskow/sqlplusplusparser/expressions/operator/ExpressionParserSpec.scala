package io.github.tmiskow.sqlplusplusparser.expressions.operator

import io.github.tmiskow.sqlplusplusparser._

class ExpressionParserSpec extends ParserSpec {
  override def parserMethod: parser.Parser[Ast] = parser.expression

  "Parser" should "parse numeric literals tokens" in {
    val strings = List("17", "3.14", "-16.5f")
    for (string <- strings) {
      val result = parseString(string)
      result shouldBe Right(LiteralAst(NumericLiteralToken(string)))
    }
  }

  it should "parse addition and subtraction tokens" in {
    val result = parseString("17 + 3.14 - 16.5f")
    result shouldBe Right(
      SubtractionAst(
        AdditionAst(
          LiteralAst(NumericLiteralToken("17")),
          LiteralAst(NumericLiteralToken("3.14"))),
        LiteralAst(NumericLiteralToken("16.5f"))))
  }

  it should "parse multiplication and division tokens" in {
    val result = parseString("17 * 3.14 + 2137 / 16.5f")
    result shouldBe Right(
      AdditionAst(
        MultiplicationAst(
          LiteralAst(NumericLiteralToken("17")),
          LiteralAst(NumericLiteralToken("3.14"))),
        DivisionAst(
          LiteralAst(NumericLiteralToken("2137")),
          LiteralAst(NumericLiteralToken("16.5f")))))
  }

  it should "parse integer division token" in {
    val result = parseString("17 DIV 3")
    result shouldBe Right(
      IntegerDivisionAst(
        LiteralAst(NumericLiteralToken("17")),
        LiteralAst(NumericLiteralToken("3"))))
  }

  it should "parse modulo tokens" in {
    val result = parseString("17 MOD 3 + 57 % 5")
    result shouldBe Right(
      AdditionAst(
        ModuloAst(
          LiteralAst(NumericLiteralToken("17")),
          LiteralAst(NumericLiteralToken("3"))),
        ModuloAst(
          LiteralAst(NumericLiteralToken("57")),
          LiteralAst(NumericLiteralToken("5")))))
  }

  it should "parse exponentiation expressions" in {
    val result = parseString("2 * 17 ^ 3.14 * 2137 ^ 16.5f ^ 3")
    result shouldBe Right(
      MultiplicationAst(
        MultiplicationAst(
          LiteralAst(NumericLiteralToken("2")),
          ExponentiationAst(
            LiteralAst(NumericLiteralToken("17")),
            LiteralAst(NumericLiteralToken("3.14")))),
        ExponentiationAst(
          ExponentiationAst(
            LiteralAst(NumericLiteralToken("2137")),
            LiteralAst(NumericLiteralToken("16.5f"))),
          LiteralAst(NumericLiteralToken("3")))))
  }

  it should "parse parenthesised expressions" in {
    val string = "(17 * (2137 - 5)) ^ (3.14 / (4.5 + 0f))"
    val result = parseString(string)
    result shouldBe Right(
      ExponentiationAst(
        MultiplicationAst(
          LiteralAst(NumericLiteralToken("17")),
          SubtractionAst(
            LiteralAst(NumericLiteralToken("2137")),
            LiteralAst(NumericLiteralToken("5")))),
        DivisionAst(
          LiteralAst(NumericLiteralToken("3.14")),
          AdditionAst(LiteralAst(NumericLiteralToken("4.5")),
            LiteralAst(NumericLiteralToken("0f"))))))
  }

  it should "return error on incomplete expressions" in {
    val strings = List("17 +", "-", "3 + (5")
    for (string <- strings) {
      val result = parseString(string)
      result shouldBe a[Left[_, _]]
      result.left.get shouldBe a[ParserError]
    }
  }
}
