package io.github.tmiskow.sqlplusplusparser

import io.github.tmiskow.sqlplusplusparser.expressions.operator.ExpressionLexer
import io.github.tmiskow.sqlplusplusparser.expressions.quantified.{LiteralLexer, ReferenceLexer}
import io.github.tmiskow.sqlplusplusparser.queries.SelectQueryLexer

object Lexer extends BaseLexer
  with LiteralLexer
  with ExpressionLexer
  with ReferenceLexer
  with SelectQueryLexer
