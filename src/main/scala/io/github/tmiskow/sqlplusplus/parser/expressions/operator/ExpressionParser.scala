package io.github.tmiskow.sqlplusplus.parser.expressions.operator

import io.github.tmiskow.sqlplusplus.lexer._
import io.github.tmiskow.sqlplusplus.parser._

trait ExpressionParser extends BaseParser {
  override def expression: Parser[ExpressionAst] = chainl1(term,
    OperatorToken("+") ^^^ AdditionAst | OperatorToken("-") ^^^ SubtractionAst)

  def term: Parser[ExpressionAst] = chainl1(priorityTerm,
    OperatorToken("*") ^^^ MultiplicationAst
      | OperatorToken("/") ^^^ DivisionAst
      | OperatorToken("DIV") ^^^ IntegerDivisionAst
      | (OperatorToken("%") | OperatorToken("MOD")) ^^^ ModuloAst
  )

  def priorityTerm: Parser[ExpressionAst] =
    chainl1(factor, OperatorToken("^") ^^^ ExponentiationAst)

  def factor: Parser[ExpressionAst] =
    literal | LeftParenthesisToken ~> expression <~ RightParenthesisToken

  def literal: Parser[ExpressionAst] = strLiteral | numericLiteral |
    (NullLiteralToken | MissingLiteralToken | TrueLiteralToken | FalseLiteralToken) ^^ LiteralAst

  def strLiteral: Parser[ExpressionAst] = accept("literal", {
    case token@StringLiteralToken(_) => LiteralAst(token)
  })

  def numericLiteral: Parser[ExpressionAst] = accept("literal", {
    case token : IntNumericLiteralToken => LiteralAst(token)
    case token : FloatNumericLiteralToken => LiteralAst(token)
  })
}
