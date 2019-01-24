package io.github.tmiskow.sqlplusplusparser.expressions

import io.github.tmiskow.sqlplusplusparser._

trait ExpressionParser extends BaseParser {
  override def all: Parser[Ast] = expression

  def expression: Parser[ExpressionAst] = chainl1(term,
    OperatorToken("+") ^^^ AdditionAst | OperatorToken("-") ^^^ SubtractionAst)

  private def term: Parser[ExpressionAst] = chainl1(priorityTerm,
    OperatorToken("*") ^^^ MultiplicationAst
    | OperatorToken("/") ^^^ DivisionAst
    | OperatorToken("DIV") ^^^ IntegerDivisionAst
    | (OperatorToken("%") | OperatorToken("MOD"))  ^^^ ModuloAst
  )

  private def priorityTerm: Parser[ExpressionAst] =
    chainl1(factor, OperatorToken("^") ^^^ ExponentiationAst)

  private def factor: Parser[ExpressionAst] =
    number | LeftParenthesisToken ~> expression <~ RightParenthesisToken

  private def number: Parser[ExpressionAst] =
    accept("number", {case token @ NumericLiteralToken(_) => NumberAst(token)})
}
