package io.github.tmiskow.sqlplusplusparser.queries

import io.github.tmiskow.sqlplusplusparser._

class SelectQueryParserSpec extends ParserSpec {
  override def parserMethod: parser.Parser[Ast] = parser.selectQuery

  "Parser" should "parse select query" in {
    val string = "SELECT ALL VALUE 45 + 36"
    val result = parseString(string)
    result shouldBe Right(
      SelectQueryAst(
        Some(KeywordToken("ALL")),
        AdditionAst(
          LiteralAst(NumericLiteralToken("45")),
          LiteralAst(NumericLiteralToken("36")))))
  }

  it should "parse select query without modifier" in {
    val string = "SELECT VALUE 26.3f"
    val result = parseString(string)
    result shouldBe Right(
      SelectQueryAst(
        None,
        LiteralAst(NumericLiteralToken("26.3f"))))
  }
}
