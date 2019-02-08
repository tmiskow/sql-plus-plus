package io.github.tmiskow.sqlplusplus.lexer.expressions.primary

import io.github.tmiskow.sqlplusplus._
import io.github.tmiskow.sqlplusplus.lexer._

class LiteralLexerSpec extends LexerSpec {
  override def tokenizerMethod: lexer.Parser[Token] = lexer.literal

  "Lexer" should "tokenize integer literals" in {
    val strings = List("3", "-2137")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(IntNumericLiteralToken(string))
    }
  }

  it should "tokenize floating point literals" in {
    val strings = List("4.5f", "-37.2")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(FloatNumericLiteralToken(string))
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
