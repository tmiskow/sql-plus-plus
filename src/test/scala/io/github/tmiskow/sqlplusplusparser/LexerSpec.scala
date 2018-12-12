package io.github.tmiskow.sqlplusplusparser

class LexerSpec extends UnitSpec {
  it should "tokenize numeric literals" in {
    var string = "17"
    var result = Lexer.parse(string)
    assert(result.successful)
    assert(result.get == List(NumericLiteralToken(string)))

    string = "3.14"
    result = Lexer.parse(string)
    assert(result.successful)
    assert(result.get == List(NumericLiteralToken(string)))
  }

  it should "tokenize string literals" in {
    val string = "\"string\""
    val result = Lexer.parse(string)
    assert(result.successful)
    assert(result.get == List(StringLiteralToken(string)))
  }
}
