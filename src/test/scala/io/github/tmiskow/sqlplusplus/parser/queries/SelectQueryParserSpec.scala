package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer.{FloatNumericLiteralToken, IntNumericLiteralToken, KeywordToken, NumericLiteralToken}
import io.github.tmiskow.sqlplusplus.parser._

class SelectQueryParserSpec extends ParserSpec {
  override def parserMethod: parser.Parser[Ast] = parser.selectQuery

  "Parser" should "parse select query" in {
    val string = "SELECT ALL VALUE 45 + 36"
    val result = parseString(string)
    result shouldBe Right(
      SelectQueryAst(
        Some(KeywordToken("ALL")),
        AdditionAst(
          LiteralAst(IntNumericLiteralToken("45")),
          LiteralAst(IntNumericLiteralToken("36")))))
  }

  it should "parse select query without modifier" in {
    val string = "SELECT VALUE 26.3f"
    val result = parseString(string)
    result shouldBe Right(
      SelectQueryAst(
        None,
        LiteralAst(FloatNumericLiteralToken("26.3f"))))
  }
}
