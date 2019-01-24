package io.github.tmiskow.sqlplusplusparser.expressions

import io.github.tmiskow.sqlplusplusparser._

trait ExpressionLexer extends BaseLexer {
  protected override def token: Parser[Token] =
    leftParenthesis | rightParenthesis | numericLiteral | characterLiteral | operator

  private def numericLiteral: Parser[Token] = floatingPointNumber ^^ NumericLiteralToken

  private def characterLiteral: Parser[Token] = stringLiteral ^^ StringLiteralToken

  private def operator: Parser[Token] =
    ("*" | "/" | "+" | "-" | "^" | "%" | "DIV" | "MOD") ^^ OperatorToken

  private def leftParenthesis: Parser[Token] = "(" ^^^ LeftParenthesisToken

  private def rightParenthesis: Parser[Token] = ")" ^^^ RightParenthesisToken
}
