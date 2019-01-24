package io.github.tmiskow.sqlplusplusparser.queries

import io.github.tmiskow.sqlplusplusparser._
import io.github.tmiskow.sqlplusplusparser.expressions.ExpressionParser

trait SelectQueryParser extends ExpressionParser {
    override def all: Parser[Ast] =
      (KeywordToken("SELECT") ~> opt(KeywordToken("ALL") | KeywordToken("DISTINCT"))) ~
      ((KeywordToken("VALUE") | KeywordToken("ELEMENT") | KeywordToken("RAW"))
        ~> super.expression) ^^ {
        case Some(modifier: KeywordToken) ~ expression => SelectQueryAst(Option(modifier), expression)
        case None ~ expression => SelectQueryAst(None, expression)
      }
}
