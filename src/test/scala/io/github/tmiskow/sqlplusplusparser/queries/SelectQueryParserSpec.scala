package io.github.tmiskow.sqlplusplusparser.queries

import io.github.tmiskow.sqlplusplusparser.{NumberAst, _}

class SelectQueryParserSpec extends UnitSpec {
  override val lexer = new Object with SelectQueryLexer
  override val parser = new Object with SelectQueryParser

  "Parser" should "parse select query" in {
    val string = "SELECT ALL VALUE 45 + 36"
    val result = parseString(string)
    result shouldBe
      SelectQueryAst(
        Some(KeywordToken("ALL")),
        AdditionAst(
          NumberAst(NumericLiteralToken("45")),
          NumberAst(NumericLiteralToken("36"))))
  }

  it should "parse select query without modifier" in {
    val string = "SELECT VALUE 26.3f"
    val result = parseString(string)
    result shouldBe
      SelectQueryAst(
        None,
        NumberAst(NumericLiteralToken("26.3f")))
  }
}
