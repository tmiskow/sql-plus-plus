package io.github.tmiskow.sqlplusplus.lexer

trait OperatorLexer extends BaseLexer {
  override def operator: Parser[Token] =
    ("*" | "/" | "+" | "-" | "^" | "%" | "DIV" | "MOD") ^^ OperatorToken
}
