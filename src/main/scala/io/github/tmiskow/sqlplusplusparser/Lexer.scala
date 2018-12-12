package io.github.tmiskow.sqlplusplusparser

import scala.util.parsing.combinator.JavaTokenParsers

object Lexer extends JavaTokenParsers {
  def parse(string: String): ParseResult[List[Tokens]] = parse(tokens, string)
  private def tokens: Parser[List[Tokens]] = rep(parenthesis | literal | operator)
  private def literal: Parser[Tokens] =
    floatingPointNumber ^^ NumericLiteralToken | stringLiteral ^^ StringLiteralToken
  private def operator: Lexer.Parser[OperatorToken] = ("*" | "/" | "+" | "-") ^^ OperatorToken
  private def parenthesis: Parser[Tokens] = leftParenthesis | rightParenthesis
  private def leftParenthesis: Parser[Tokens] = """\(""".r ^^ (_ => LeftParenthesisToken)
  private def rightParenthesis: Parser[Tokens] = """\)""".r ^^ (_ => RightParenthesisToken)
}
