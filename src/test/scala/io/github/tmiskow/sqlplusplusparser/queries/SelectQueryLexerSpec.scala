package io.github.tmiskow.sqlplusplusparser.queries

import io.github.tmiskow.sqlplusplusparser._

class SelectQueryLexerSpec extends LexerSpec {
  override def tokenizerMethod: lexer.Parser[Token] = lexer.selectQuery

  "Lexer" should "tokenize select keyword" in {
    val string = "SELECT"
    val result = tokenizeString(string)
    result shouldBe Right(KeywordToken(string))
  }

  it should "tokenize modifier keywords" in {
    val strings = List("ALL", "DISTINCT")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(KeywordToken(string))
    }
  }

  it should "tokenize value keywords" in {
    val strings = List("VALUE", "ELEMENT", "RAW")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(KeywordToken(string))
    }
  }

  it should "tokenize case-insensitively" in {
    val strings = List("value", "SeLeCt", "RAW")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(KeywordToken(string.toUpperCase))
    }
  }
}
