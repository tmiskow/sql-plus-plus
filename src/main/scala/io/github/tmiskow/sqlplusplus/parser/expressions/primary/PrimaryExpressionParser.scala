package io.github.tmiskow.sqlplusplus.parser.expressions.primary

import io.github.tmiskow.sqlplusplus.lexer._
import io.github.tmiskow.sqlplusplus.parser.{BaseParser, ExpressionAst, LiteralAst, VariableAst}

trait PrimaryExpressionParser extends BaseParser {
  override def primaryExpression: Parser[ExpressionAst] = identifier | literal | constructor

  override def identifier: Parser[VariableAst] = accept("variable", {
    case VariableToken(name) => VariableAst(name)
  })

  override def literal: Parser[LiteralAst] = stringLiteral | numericLiteral |
    (NullLiteralToken | MissingLiteralToken | TrueLiteralToken | FalseLiteralToken) ^^ LiteralAst

  override def stringLiteral: Parser[LiteralAst] = accept("literal", {
    case token@StringLiteralToken(_) => LiteralAst(token)
  })

  private def numericLiteral: Parser[LiteralAst] = accept("literal", {
    case token : IntNumericLiteralToken => LiteralAst(token)
    case token : FloatNumericLiteralToken => LiteralAst(token)
  })
}
