package io.github.tmiskow.sqlplusplus.parser.expressions.operator

import io.github.tmiskow.sqlplusplus._
import io.github.tmiskow.sqlplusplus.lexer.{FloatNumericLiteralToken, IntNumericLiteralToken}
import io.github.tmiskow.sqlplusplus.parser._

class ExpressionParserSpec extends ParserSpec {
  override def parserMethod: parser.Parser[Ast] = parser.expression

  "Parser" should "parse integer numeric literals tokens" in {
    val strings = List("17", "-13")
    for (string <- strings) {
      val result = parseString(string)
      result shouldBe Right(LiteralAst(IntNumericLiteralToken(string)))
    }
  }

  it should "parse float numeric literals tokens" in {
    val strings = List("3.14", "-16.5f")
    for (string <- strings) {
      val result = parseString(string)
      result shouldBe Right(LiteralAst(FloatNumericLiteralToken(string)))
    }
  }

  it should "parse addition and subtraction tokens" in {
    val result = parseString("17 + 3.14 - 16.5f")
    result shouldBe Right(
      SubtractionAst(
        AdditionAst(
          LiteralAst(IntNumericLiteralToken("17")),
          LiteralAst(FloatNumericLiteralToken("3.14"))),
        LiteralAst(FloatNumericLiteralToken("16.5f"))))
  }

  it should "parse multiplication and division tokens" in {
    val result = parseString("17 * 3.14 + 2137 / 16.5f")
    result shouldBe Right(
      AdditionAst(
        MultiplicationAst(
          LiteralAst(IntNumericLiteralToken("17")),
          LiteralAst(FloatNumericLiteralToken("3.14"))),
        DivisionAst(
          LiteralAst(IntNumericLiteralToken("2137")),
          LiteralAst(FloatNumericLiteralToken("16.5f")))))
  }

  it should "parse integer division token" in {
    val result = parseString("17 DIV 3")
    result shouldBe Right(
      IntegerDivisionAst(
        LiteralAst(IntNumericLiteralToken("17")),
        LiteralAst(IntNumericLiteralToken("3"))))
  }

  it should "parse modulo tokens" in {
    val result = parseString("17 MOD 3 + 57 % 5")
    result shouldBe Right(
      AdditionAst(
        ModuloAst(
          LiteralAst(IntNumericLiteralToken("17")),
          LiteralAst(IntNumericLiteralToken("3"))),
        ModuloAst(
          LiteralAst(IntNumericLiteralToken("57")),
          LiteralAst(IntNumericLiteralToken("5")))))
  }

  it should "parse exponentiation expressions" in {
    val result = parseString("2 * 17 ^ 3.14 * 2137 ^ 16.5f ^ 3")
    result shouldBe Right(
      MultiplicationAst(
        MultiplicationAst(
          LiteralAst(IntNumericLiteralToken("2")),
          ExponentiationAst(
            LiteralAst(IntNumericLiteralToken("17")),
            LiteralAst(FloatNumericLiteralToken("3.14")))),
        ExponentiationAst(
          ExponentiationAst(
            LiteralAst(IntNumericLiteralToken("2137")),
            LiteralAst(FloatNumericLiteralToken("16.5f"))),
          LiteralAst(IntNumericLiteralToken("3")))))
  }

  it should "parse parenthesised expressions" in {
    val string = "(17 * (2137 - 5)) ^ (3.14 / (4.5 + 0f))"
    val result = parseString(string)
    result shouldBe Right(
      ExponentiationAst(
        MultiplicationAst(
          LiteralAst(IntNumericLiteralToken("17")),
          SubtractionAst(
            LiteralAst(IntNumericLiteralToken("2137")),
            LiteralAst(IntNumericLiteralToken("5")))),
        DivisionAst(
          LiteralAst(FloatNumericLiteralToken("3.14")),
          AdditionAst(LiteralAst(FloatNumericLiteralToken("4.5")),
            LiteralAst(FloatNumericLiteralToken("0f"))))))
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
