package io.github.tmiskow.sqlplusplusparser

import scala.util.parsing.combinator.JavaTokenParsers

trait BaseLexer extends JavaTokenParsers {
  protected class StringExtensions(str: String) {
    def ignoreCase: Parser[String] = ("""(?i)\Q""" + str + """\E""").r
  }

  def apply(string: String): Either[LexerError, Seq[Token]] = {
    parse(tokens, string) match {
      case NoSuccess(message, next) =>
        Left(LexerError(Location(next.pos.line, next.pos.column), message))
      case Success(result, input) => input match {
        case _ if input.atEnd => Right(result)
        case _ =>
          Left(LexerError(Location(input.pos.line, input.pos.column), "Reached an and of input"))
      }
    }
  }

  protected def token: Parser[Token]

  protected implicit def extendString(str: String): StringExtensions = new StringExtensions(str)

  private def tokens: Parser[Seq[Token]] = rep1(token)

}
