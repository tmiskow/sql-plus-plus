package io.github.tmiskow.sqlplusplusparser.expressions

import io.github.tmiskow.sqlplusplusparser._

class ExpressionLexerSpec extends UnitSpec {
  override val lexer = new Object with ExpressionLexer

  "Lexer" should "tokenize numeric literals" in {
    val strings = List("17", "3.14", "16.5f")
    for (string <- strings) {
      val expectedTokens = List(NumericLiteralToken(string))
      val result = lexer(string)
      result shouldBe Right(expectedTokens)
    }
  }

  it should "tokenize operators" in {
    val strings = List("+", "-", "*", "/", "%", "DIV", "MOD")
    for (string <- strings) {
      val result = lexer(string)
      val expectedTokens = List(OperatorToken(string))
      result shouldBe Right(expectedTokens)
    }
  }

  it should "tokenize parenthesises" in {
    val strings = List("(", ")")
    val expectedResults = List(
      List(LeftParenthesisToken),
      List(RightParenthesisToken)
    )
    for ((string, expectedTokens) <- strings zip expectedResults) {
      val result = lexer(string)
      result shouldBe Right(expectedTokens)
    }
  }

  it should "tokenize expressions into sequences of individual tokens" in {
    val strings = List(
      "3 / 15.6",
      "(15 - 19.67f) * 3"
    )
    val expectedResults = List(
      List(
        NumericLiteralToken("3"),
        OperatorToken("/"),
        NumericLiteralToken("15.6")
      ),
      List(
        LeftParenthesisToken,
        NumericLiteralToken("15"),
        OperatorToken("-"),
        NumericLiteralToken("19.67f"),
        RightParenthesisToken,
        OperatorToken("*"),
        NumericLiteralToken("3")
      )
    )
    for ((string, expectedTokens) <- strings zip expectedResults) {
      val result = lexer(string)
      result shouldBe Right(expectedTokens)
    }
  }

  it should "tokenize meaningless sequences of allowed symbols" in {
    val strings = List(
      ")(+-*/",
      "3.14 1.27 5.76f 3 0 1"
    )
    val expectedResults = List(
      List(
        RightParenthesisToken,
        LeftParenthesisToken,
        OperatorToken("+"),
        OperatorToken("-"),
        OperatorToken("*"),
        OperatorToken("/")
      ),
      List(
        NumericLiteralToken("3.14"),
        NumericLiteralToken("1.27"),
        NumericLiteralToken("5.76f"),
        NumericLiteralToken("3"),
        NumericLiteralToken("0"),
        NumericLiteralToken("1")
      )
    )
    for ((string, expectedTokens) <- strings zip expectedResults) {
      val result = lexer(string)
      result shouldBe Right(expectedTokens)
    }
  }
}
