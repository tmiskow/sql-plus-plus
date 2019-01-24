package io.github.tmiskow.sqlplusplusparser.expressions.operator

import io.github.tmiskow.sqlplusplusparser._

trait ExpressionParser extends BaseParser {
  override def expression: Parser[Ast] = chainl1(term,
    OperatorToken("+") ^^^ AdditionAst | OperatorToken("-") ^^^ SubtractionAst)

  def term: Parser[Ast] = chainl1(priorityTerm,
    OperatorToken("*") ^^^ MultiplicationAst
      | OperatorToken("/") ^^^ DivisionAst
      | OperatorToken("DIV") ^^^ IntegerDivisionAst
      | (OperatorToken("%") | OperatorToken("MOD")) ^^^ ModuloAst
  )

  def priorityTerm: Parser[Ast] =
    chainl1(factor, OperatorToken("^") ^^^ ExponentiationAst)

  def factor: Parser[Ast] =
    literal | LeftParenthesisToken ~> expression <~ RightParenthesisToken

  def literal: Parser[Ast] = strLiteral | numericLiteral |
    (NullLiteralToken | MissingLiteralToken | TrueLiteralToken | FalseLiteralToken) ^^ LiteralAst

  def strLiteral: Parser[Ast] = accept("literal", {
    case token@StringLiteralToken(_) => LiteralAst(token)
  })

  def numericLiteral: Parser[Ast] = accept("literal", {
    case token@NumericLiteralToken(_) => LiteralAst(token)
  })
}
