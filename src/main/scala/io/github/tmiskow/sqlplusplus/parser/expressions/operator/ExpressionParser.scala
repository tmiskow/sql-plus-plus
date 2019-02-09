package io.github.tmiskow.sqlplusplus.parser.expressions.operator

import io.github.tmiskow.sqlplusplus.lexer._
import io.github.tmiskow.sqlplusplus.parser._

trait ExpressionParser extends BaseParser {
  override def expression: Parser[ExpressionAst] = chainl1(term,
    OperatorToken("+") ^^^ AdditionAst | OperatorToken("-") ^^^ SubtractionAst)

  override def variable: Parser[VariableAst] = accept("variable", {
    case VariableToken(name) => VariableAst(name)
  })

  private def term: Parser[ExpressionAst] = chainl1(priorityTerm,
    OperatorToken("*") ^^^ MultiplicationAst
      | OperatorToken("/") ^^^ DivisionAst
      | OperatorToken("DIV") ^^^ IntegerDivisionAst
      | (OperatorToken("%") | OperatorToken("MOD")) ^^^ ModuloAst
  )

  private def priorityTerm: Parser[ExpressionAst] =
    chainl1(factor, OperatorToken("^") ^^^ ExponentiationAst)

  def factor: Parser[ExpressionAst] =
    literal | variable | LeftParenthesisToken ~> expression <~ RightParenthesisToken

  private def literal: Parser[ExpressionAst] = stringLiteral | numericLiteral |
    (NullLiteralToken | MissingLiteralToken | TrueLiteralToken | FalseLiteralToken) ^^ LiteralAst

  private def stringLiteral: Parser[ExpressionAst] = accept("literal", {
    case token@StringLiteralToken(_) => LiteralAst(token)
  })

  private def numericLiteral: Parser[ExpressionAst] = accept("literal", {
    case token : IntNumericLiteralToken => LiteralAst(token)
    case token : FloatNumericLiteralToken => LiteralAst(token)
  })
}
