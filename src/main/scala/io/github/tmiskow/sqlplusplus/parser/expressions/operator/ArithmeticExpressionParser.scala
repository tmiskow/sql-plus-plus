package io.github.tmiskow.sqlplusplus.parser.expressions.operator

import io.github.tmiskow.sqlplusplus.lexer._
import io.github.tmiskow.sqlplusplus.parser._

trait ArithmeticExpressionParser extends BaseParser {
  override def arithmeticExpression: Parser[ExpressionAst] = chainl1(term,
    OperatorToken("+") ^^^ AdditionAst | OperatorToken("-") ^^^ SubtractionAst)

  private def term: Parser[ExpressionAst] = chainl1(priorityTerm,
    OperatorToken("*") ^^^ MultiplicationAst
      | OperatorToken("/") ^^^ DivisionAst
      | OperatorToken("DIV") ^^^ IntegerDivisionAst
      | (OperatorToken("%") | OperatorToken("MOD")) ^^^ ModuloAst
  )

  private def priorityTerm: Parser[ExpressionAst] =
    chainl1(factor, OperatorToken("^") ^^^ ExponentiationAst)

  private def factor: Parser[ExpressionAst] =
    literal | variable | LeftParenthesisToken ~> arithmeticExpression <~ RightParenthesisToken
}
