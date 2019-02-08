package io.github.tmiskow.sqlplusplus.parser.expressions.operator

import io.github.tmiskow.sqlplusplus._
import io.github.tmiskow.sqlplusplus.lexer.{LeftParenthesisToken, OperatorToken, RightParenthesisToken}

class ExpressionLexerSpec extends LexerSpec {
  override def tokenizerMethod: lexer.Parser[LexerOutput] = lexer.expression

  "Lexer" should "tokenize operators" in {
    val strings = List("+", "-", "*", "/", "%", "DIV", "MOD")
    for (string <- strings) {
      val result = tokenizeString(string)
      val expectedToken = OperatorToken(string)
      result shouldBe Right(expectedToken)
    }
  }

  it should "tokenize parenthesises" in {
    val strings = List("(", ")")
    val expectedResults = List(LeftParenthesisToken, RightParenthesisToken)
    for ((string, expectedTokens) <- strings zip expectedResults) {
      val result = tokenizeString(string)
      result shouldBe Right(expectedTokens)
    }
  }
}
