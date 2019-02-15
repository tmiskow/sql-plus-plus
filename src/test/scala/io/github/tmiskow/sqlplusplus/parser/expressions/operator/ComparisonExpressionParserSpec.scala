package io.github.tmiskow.sqlplusplus.parser.expressions.operator

import io.github.tmiskow.sqlplusplus.lexer.{FloatNumericLiteralToken, IntNumericLiteralToken}
import io.github.tmiskow.sqlplusplus.parser._

class ComparisonExpressionParserSpec extends ParserSpec {
  override def parserMethod: parser.Parser[Ast] = parser.comparisonExpression

  "Parser" should "parse equality" in {
    val result = parseString("17 = 3.14")
    result shouldBe Right(
      EqualityAst(
        LiteralAst(IntNumericLiteralToken("17")),
        LiteralAst(FloatNumericLiteralToken("3.14"))))
  }

  it should "parse inequalities" in {
    val strings = Seq("21 != 3", "21 <> 3")
    for (string <- strings) {
      val result = parseString(string)
      result shouldBe Right(
        InequalityAst(
          LiteralAst(IntNumericLiteralToken("21")),
          LiteralAst(IntNumericLiteralToken("3"))))
    }
  }

  it should "parse strict inequalities" in {
    val strings = Seq("13 < 756", "756 > 13")
    for (string <- strings) {
      val result = parseString(string)
      result shouldBe Right(
        LessThanAst(
          LiteralAst(IntNumericLiteralToken("13")),
          LiteralAst(IntNumericLiteralToken("756"))))
    }
  }

  it should "parse non strict inequalities" in {
    val strings = Seq("0.05 <= -65", "-65 >= 0.05")
    for (string <- strings) {
      val result = parseString(string)
      result shouldBe Right(
        LessOrEqualThanAst(
          LiteralAst(FloatNumericLiteralToken("0.05")),
          LiteralAst(IntNumericLiteralToken("-65"))))
    }
  }
}
