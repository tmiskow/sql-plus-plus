package io.github.tmiskow.sqlplusplus.lexer

import io.github.tmiskow.sqlplusplus.{LexerError, Location}

import scala.util.parsing.combinator.JavaTokenParsers

trait BaseLexer extends JavaTokenParsers {
  type Result[T] = Either[LexerError, T]

  protected class StringExtensions(str: String) {
    def ignoreCase: Parser[String] = ("""(?i)\Q""" + str + """\E""").r
  }
  protected implicit def extendString(string: String): StringExtensions =
    new StringExtensions(string)

  def apply: Result[Seq[Token]] = ???

  def tokenizeString[T](string: String, parser: Parser[T]): Result[T] = {
    parse(parser, string) match {
      case NoSuccess(message, next) =>
        Left(LexerError(Location(next.pos.line, next.pos.column), message))
      case Success(result, input) => input match {
        case _ if input.atEnd => Right(result)
        case _ =>
          Left(LexerError(
            Location(input.pos.line, input.pos.column),
            "Reached an and of input"))
      }
    }
  }

  def all: Parser[Seq[Token]] = rep1(selectQuery | expression | literal)
  def selectQuery: Parser[Token] = ???
  def expression: Parser[Token] = ???
  def reference: Parser[Token] = ???
  def literal: Parser[Token] = ???
}
