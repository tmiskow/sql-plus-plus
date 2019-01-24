package io.github.tmiskow.sqlplusplusparser.queries

import io.github.tmiskow.sqlplusplusparser._
import io.github.tmiskow.sqlplusplusparser.expressions.ExpressionLexer

trait SelectQueryLexer extends ExpressionLexer {
  override def token: Parser[Token] = keyword | super.token

  private def keyword: Parser[Token] = (
    "select".ignoreCase
    | "all".ignoreCase
    | "distinct".ignoreCase
    | "value".ignoreCase
    | "element".ignoreCase
    | "raw".ignoreCase
    ) ^^ {string => KeywordToken(string.toUpperCase)}
}
