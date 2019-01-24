package io.github.tmiskow.sqlplusplusparser.expressions.quantified

import io.github.tmiskow.sqlplusplusparser._

class LiteralLexerSpec extends LexerSpec {
  override def tokenizerMethod: lexer.Parser[Token] = lexer.literal

  "Lexer" should "tokenize numeric literals" in {
    val strings = List("3", "4.5f", "-37.2")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(NumericLiteralToken(string))
    }
  }

  it should "tokenize string literals" in {
    val strings = List("\"string\"", "'string'")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(StringLiteralToken(string))
    }
  }

  it should "tokenize special literals" in {
    val strings = List("true", "false", "null", "missing")
    val expectedTokens = List(TrueLiteralToken, FalseLiteralToken, NullLiteralToken, MissingLiteralToken)
    for ((string, expectedToken) <- strings zip expectedTokens) {
      val result = lexer.tokenizeString(string, lexer.literal)
      result shouldBe Right(expectedToken)
    }
  }
}
