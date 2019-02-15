package io.github.tmiskow.sqlplusplus.lexer

class OperatorLexerSpec extends LexerSpec {
  override def tokenizerMethod: lexer.Parser[Token] = lexer.operator

  "Lexer" should "tokenize arithmetic operators" in {
    val strings = List("+", "-", "*", "/", "%", "DIV", "MOD")
    for (string <- strings) {
      val result = tokenizeString(string)
      val expectedToken = OperatorToken(string)
      result shouldBe Right(expectedToken)
    }
  }


  it should "tokenize comparison operators" in {
    val strings = List("=", "!=", "<>", "<", "<=", ">", ">=")
    for (string <- strings) {
      val result = tokenizeString(string)
      val expectedToken = OperatorToken(string)
      result shouldBe Right(expectedToken)
    }
  }
}
