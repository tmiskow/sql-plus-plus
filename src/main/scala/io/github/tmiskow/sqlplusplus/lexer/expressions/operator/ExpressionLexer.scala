package io.github.tmiskow.sqlplusplus.lexer.expressions.operator

import io.github.tmiskow.sqlplusplus.lexer._

trait ExpressionLexer extends BaseLexer {
  override def expression: Parser[Token] = parenthesis | literal | operator

  def operator: Parser[Token] =
    ("*" | "/" | "+" | "-" | "^" | "%" | "DIV" | "MOD") ^^ OperatorToken

  def parenthesis: Parser[Token] =
    ("(" ^^^ LeftParenthesisToken) | (")" ^^^ RightParenthesisToken)
}
