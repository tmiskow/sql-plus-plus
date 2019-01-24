package io.github.tmiskow.sqlplusplusparser.queries

import io.github.tmiskow.sqlplusplusparser._

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
