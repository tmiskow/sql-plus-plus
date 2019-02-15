package io.github.tmiskow.sqlplusplus.lexer

trait OperatorLexer extends BaseLexer {
  override def operator: Parser[Token] = arithmeticOperator | comparisonOperator

  private def arithmeticOperator: Parser[OperatorToken] =
    ("*" | "/" | "+" | "-" | "^" | "%" | "DIV" | "MOD") ^^ OperatorToken

  private def comparisonOperator: Parser[OperatorToken] =
    ("=" | "!=" | "<>" | "<=" | "<" | ">=" | ">") ^^ OperatorToken
}
