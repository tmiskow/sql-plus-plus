package io.github.tmiskow.sqlplusplus.parser.expressions.operator

import io.github.tmiskow.sqlplusplus._
import io.github.tmiskow.sqlplusplus.lexer.{FloatNumericLiteralToken, IntNumericLiteralToken}
import io.github.tmiskow.sqlplusplus.parser._

class ArithmeticExpressionParserSpec extends ParserSpec {
  override def parserMethod: parser.Parser[Ast] = parser.arithmeticExpression

  "Parser" should "parse addition" in {
    val result = parseString("17 + 3.14")
    result shouldBe Right(
      AdditionAst(
        LiteralAst(IntNumericLiteralToken(17)),
        LiteralAst(FloatNumericLiteralToken(3.14f))))
  }

  it should "parse subtraction" in {
    val result = parseString("3.14 - 16.5f")
    result shouldBe Right(
      SubtractionAst(
        LiteralAst(FloatNumericLiteralToken(3.14f)),
        LiteralAst(FloatNumericLiteralToken(16.5f))))
  }

  it should "parse multiplication" in {
    val result = parseString("17 * 3.14")
    result shouldBe Right(
      MultiplicationAst(
        LiteralAst(IntNumericLiteralToken(17)),
        LiteralAst(FloatNumericLiteralToken(3.14f))))
  }

  it should "parse division" in {
    val result = parseString("2137 / 16.5f")
    result shouldBe Right(
      DivisionAst(
        LiteralAst(IntNumericLiteralToken(2137)),
        LiteralAst(FloatNumericLiteralToken(16.5f))))
  }

  it should "parse integer division" in {
    val result = parseString("17 DIV 3")
    result shouldBe Right(
      IntegerDivisionAst(
        LiteralAst(IntNumericLiteralToken(17)),
        LiteralAst(IntNumericLiteralToken(3))))
  }

  it should "parse modulo operation" in {
    val strings = List("17 MOD 3", "17 % 3")
    for (string <- strings) {
      val result = parseString(string)
      result shouldBe Right(
        ModuloAst(
          LiteralAst(IntNumericLiteralToken(17)),
          LiteralAst(IntNumericLiteralToken(3))))
    }
  }

  it should "parse exponentiation" in {
    val result = parseString("17 ^ 3.14")
    result shouldBe Right(
      ExponentiationAst(
        LiteralAst(IntNumericLiteralToken(17)),
        LiteralAst(FloatNumericLiteralToken(3.14f))))
  }

  it should "parse parenthesised expressions" in {
    val string = "(17 * (2137 - 5))"
    val result = parseString(string)
    result shouldBe Right(
      MultiplicationAst(
        LiteralAst(IntNumericLiteralToken(17)),
        SubtractionAst(
          LiteralAst(IntNumericLiteralToken(2137)),
          LiteralAst(IntNumericLiteralToken(5)))))
  }

  it should "parse complex expressions" in {
    val string = "(17 * (2137 - 5)) ^ (3.14 / (4.5 + 0f))"
    val result = parseString(string)
    result shouldBe Right(
      ExponentiationAst(
        MultiplicationAst(
          LiteralAst(IntNumericLiteralToken(17)),
          SubtractionAst(
            LiteralAst(IntNumericLiteralToken(2137)),
            LiteralAst(IntNumericLiteralToken(5)))),
        DivisionAst(
          LiteralAst(FloatNumericLiteralToken(3.14f)),
          AdditionAst(LiteralAst(FloatNumericLiteralToken(4.5f)),
            LiteralAst(FloatNumericLiteralToken(0f))))))
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
