package io.github.tmiskow.sqlplusplus.lexer

class KeywordLexerSpec extends LexerSpec {
  override def tokenizerMethod: lexer.Parser[Token] = lexer.keyword

  "Lexer" should "tokenize keywords" in {
    val strings = List("select", "all", "distinct", "with", "let")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(KeywordToken(string.toUpperCase))
    }
  }

  it should "tokenize value keywords" in {
    val strings = List("VALUE", "ELEMENT", "RAW")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(KeywordToken("VALUE"))
    }
  }

  it should "tokenize case-insensitively" in {
    val strings = List("value", "SeLeCt", "ALL")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(KeywordToken(string.toUpperCase))
    }
  }
}
