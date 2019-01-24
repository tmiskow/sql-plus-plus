package io.github.tmiskow.sqlplusplusparser

import scala.util.parsing.combinator.Parsers

trait BaseParser extends Parsers {
  override type Elem = Token
  type Result = Either[ParserError, Ast]

  def apply(tokens: Seq[Elem]): Result = ???

  def parseTokens(tokens: Seq[Elem], parser: Parser[Ast]): Result = {
    val reader = new TokenReader(tokens)
    parser(reader) match {
      case NoSuccess(message, next) =>
        Left(ParserError(Location(next.pos.line, next.pos.column), message))
      case Success(result, input) => input match {
        case _ if input.atEnd => Right(result)
        case _ =>
          Left(ParserError(
            Location(input.pos.line, input.pos.column),
            "Reached an and of input"))
      }
    }
  }

  def all: Parser[Ast] = selectQuery | expression
  def selectQuery: Parser[Ast] = ???
  def expression: Parser[Ast] = ???
  def reference: Parser[Ast] = ???
}
