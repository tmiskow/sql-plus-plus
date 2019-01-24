package io.github.tmiskow.sqlplusplusparser

import scala.util.parsing.combinator.Parsers

trait BaseParser extends Parsers {
  override type Elem = Token

  def apply(tokens: Seq[Elem]): Either[ParserError, Ast] = {
    val reader = new TokenReader(tokens)
    all(reader) match {
      case NoSuccess(message, next) =>
        Left(ParserError(Location(next.pos.line, next.pos.column), message))
      case Success(result, input) => input match {
        case _ if input.atEnd => Right(result)
        case _ =>
          Left(ParserError(Location(input.pos.line, input.pos.column), "Reached an and of input"))
      }
    }
  }

  protected def all: Parser[Ast]
}
