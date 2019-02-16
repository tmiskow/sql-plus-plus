package io.github.tmiskow.sqlplusplus.parser

import io.github.tmiskow.sqlplusplus.lexer.Token
import io.github.tmiskow.sqlplusplus.{Location, ParserError}

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

  def all: Parser[Ast] = query | selectBlock | expression | variable
  def variable: Parser[VariableAst] = ???
  def literal: Parser[LiteralAst] = ???
  def stringLiteral: Parser[LiteralAst] = ???
  def query: Parser[Ast] = ???
  def selectBlock: Parser[SelectBlockAst] = ???
  def expression: Parser[ExpressionAst] = ???
  def arithmeticExpression: Parser[ExpressionAst] = ???
  def comparisonExpression: Parser[ComparisonExpressionAst] = ???
  def constructor: Parser[ConstructorAst] = ???
}
