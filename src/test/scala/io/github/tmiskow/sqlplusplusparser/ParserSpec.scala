package io.github.tmiskow.sqlplusplusparser

abstract class ParserSpec extends UnitSpec {
  override type LexerOutput = Seq[Token]
  override def tokenizerMethod: lexer.Parser[Seq[Token]] = lexer.all

  override def parseString(string: String): parser.Result = {
    val tokens = tokenizeString(string).right.get
    val result = parseTokens(tokens)
    result
  }
}
