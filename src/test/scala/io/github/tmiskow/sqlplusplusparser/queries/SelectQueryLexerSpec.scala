package io.github.tmiskow.sqlplusplusparser.queries

import io.github.tmiskow.sqlplusplusparser.{KeywordToken, NumericLiteralToken, UnitSpec}

class SelectQueryLexerSpec extends UnitSpec {
  override val lexer = new Object with SelectQueryLexer
  "Lexer" should "tokenize select query" in {
    val string = "SELECT VALUE 3"
    val result = lexer(string)
    result shouldBe Right(List(
      KeywordToken("SELECT"),
      KeywordToken("VALUE"),
      NumericLiteralToken("3"),
    ))
  }
}
