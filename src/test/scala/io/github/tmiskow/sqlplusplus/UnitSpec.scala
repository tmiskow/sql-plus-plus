package io.github.tmiskow.sqlplusplus

import io.github.tmiskow.sqlplusplus.lexer.{BaseLexer, Lexer, Token}
import io.github.tmiskow.sqlplusplus.parser.{Ast, BaseParser, Parser}
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
abstract class UnitSpec extends FlatSpec with Matchers {
  type LexerOutput
  val lexer: BaseLexer = Lexer
  val parser: BaseParser = Parser

  def tokenizerMethod: lexer.Parser[LexerOutput] = ???
  def parserMethod: parser.Parser[Ast] = ???
  def parseString(string: String): parser.Result = ???

  def tokenizeString(string: String): lexer.Result[LexerOutput] = lexer.tokenizeString(string, tokenizerMethod)
  def parseTokens(tokens: Seq[Token]): parser.Result = parser.parseTokens(tokens, parserMethod)
}
