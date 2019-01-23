package io.github.tmiskow.sqlplusplusparser

import scala.util.parsing.combinator.JavaTokenParsers

object Lexer extends JavaTokenParsers {
  def apply(string: String): Either[LexerError, List[Token]] = {
    parse(tokens, string) match {
      case NoSuccess(message, next) =>
        Left(LexerError(Location(next.pos.line, next.pos.column), message))
      case Success(result, _) => Right(result)
    }
  }

  private def tokens: Parser[List[Token]] =
    rep(leftParenthesis | rightParenthesis | numericLiteral | characterLiteral | operator)

  private def numericLiteral: Parser[Token] = floatingPointNumber ^^ NumericLiteralToken

  private def characterLiteral: Parser[Token] = super.stringLiteral ^^ StringLiteralToken

  private def operator: Parser[Token] =
    ("*" | "/" | "+" | "-" | "^" | "%" | "DIV" | "MOD") ^^ OperatorToken

  private def leftParenthesis: Parser[Token] = "(" ^^^ LeftParenthesisToken

  private def rightParenthesis: Parser[Token] = ")" ^^^ RightParenthesisToken
}
