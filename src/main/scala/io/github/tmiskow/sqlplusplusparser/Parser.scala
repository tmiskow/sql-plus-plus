package io.github.tmiskow.sqlplusplusparser

import io.github.tmiskow.sqlplusplusparser.expressions.operator.ExpressionParser
import io.github.tmiskow.sqlplusplusparser.queries.SelectQueryParser

object Parser extends BaseParser
  with ExpressionParser
  with SelectQueryParser
