package io.github.tmiskow.sqlplusplusparser.expressions.operator

import io.github.tmiskow.sqlplusplusparser._

trait ExpressionLexer extends BaseLexer {
  override def expression: Parser[Token] = parenthesis | literal | operator

  def operator: Parser[Token] =
    ("*" | "/" | "+" | "-" | "^" | "%" | "DIV" | "MOD") ^^ OperatorToken

  def parenthesis: Parser[Token] =
    ("(" ^^^ LeftParenthesisToken) | (")" ^^^ RightParenthesisToken)
}
