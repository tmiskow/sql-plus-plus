package io.github.tmiskow.sqlplusplus.lexer

class SymbolLexerSpec extends LexerSpec {
  override def tokenizerMethod: lexer.Parser[Token] = lexer.symbol

  "Lexer" should "tokenize parenthesises" in {
    val strings = List("(", ")")
    val expectedResults = List(LeftParenthesisToken, RightParenthesisToken)
    for ((string, expectedTokens) <- strings zip expectedResults) {
      val result = tokenizeString(string)
      result shouldBe Right(expectedTokens)
    }
  }

  it should "tokenize commas" in {
    val string = ","
    val result = tokenizeString(string)
    result shouldBe Right(CommaToken)
  }

  it should "tokenize semicolons" in {
    val string = ";"
    val result = tokenizeString(string)
    result shouldBe Right(SemicolonToken)
  }
}
