package io.github.tmiskow.sqlplusplus.lexer

import io.github.tmiskow.sqlplusplus.lexer.expressions.operator.ExpressionLexer
import io.github.tmiskow.sqlplusplus.lexer.expressions.primary.{LiteralLexer, ReferenceLexer}
import io.github.tmiskow.sqlplusplus.lexer.queries.SelectQueryLexer

object Lexer extends BaseLexer
  with LiteralLexer
  with ExpressionLexer
  with ReferenceLexer
  with SelectQueryLexer
