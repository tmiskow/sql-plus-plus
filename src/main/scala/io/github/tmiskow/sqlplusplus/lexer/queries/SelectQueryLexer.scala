package io.github.tmiskow.sqlplusplus.lexer.queries

import io.github.tmiskow.sqlplusplus.lexer.{BaseLexer, KeywordToken, Token}

trait SelectQueryLexer extends BaseLexer {
  override def selectQuery: Parser[Token] = keyword | expression

  def keyword: Parser[Token] = (
    "select".ignoreCase
      | "all".ignoreCase
      | "distinct".ignoreCase
      | "value".ignoreCase
      | "element".ignoreCase
      | "raw".ignoreCase
    ) ^^ { string => KeywordToken(string.toUpperCase) }
}
