package io.github.tmiskow.sqlplusplus.parser

import io.github.tmiskow.sqlplusplus.UnitSpec
import io.github.tmiskow.sqlplusplus.lexer.Token

abstract class ParserSpec extends UnitSpec {
  override type LexerOutput = Seq[Token]
  override def tokenizerMethod: lexer.Parser[Seq[Token]] = lexer.all

  override def parseString(string: String): parser.Result = {
    val tokens = tokenizeString(string).right.get
    val result = parseTokens(tokens)
    result
  }
}
