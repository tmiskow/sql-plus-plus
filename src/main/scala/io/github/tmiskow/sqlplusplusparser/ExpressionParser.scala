package io.github.tmiskow.sqlplusplusparser

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.{NoPosition, Position, Reader}

object ExpressionParser extends Parsers {
  override type Elem = Token

  private class TokenReader(tokens: Seq[Token]) extends Reader[Token] {
    override def first: Token = tokens.head
    override def atEnd: Boolean = tokens.isEmpty
    override def pos: Position = NoPosition
    override def rest: Reader[Token] = new TokenReader(tokens.tail)
  }

  def apply(tokens: Seq[Token]): Either[ParserError, ExpressionAST] = {
    val reader = new TokenReader(tokens)
    expression(reader) match {
      case NoSuccess(message, next) =>
        Left(ParserError(Location(next.pos.line, next.pos.column), message))
      case Success(result, input) => input match {
        case _ if input.atEnd =>Right(result)
        case _ =>
          Left(ParserError(Location(input.pos.line, input.pos.column), "Reached an and of input"))
      }
    }
  }

  private def expression: Parser[ExpressionAST] = chainl1(term,
    OperatorToken("+") ^^^ AdditionAST | OperatorToken("-") ^^^ SubtractionAST)

  private def term: Parser[ExpressionAST] = chainl1(priorityTerm,
    OperatorToken("*") ^^^ MultiplicationAST
    | OperatorToken("/") ^^^ DivisionAST
    | OperatorToken("DIV") ^^^ IntegerDivisionAST
    | (OperatorToken("%") | OperatorToken("MOD"))  ^^^ ModuloAST
  )

  private def priorityTerm: Parser[ExpressionAST] =
    chainl1(factor, OperatorToken("^") ^^^ ExponentiationAST)

  private def factor: Parser[ExpressionAST] =
    number | LeftParenthesisToken ~> expression <~ RightParenthesisToken

  private def number: Parser[ExpressionAST] =
    accept("number", {case token @ NumericLiteralToken(_) => NumberAST(token)})
}
