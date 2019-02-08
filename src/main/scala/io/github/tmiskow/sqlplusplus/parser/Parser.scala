package io.github.tmiskow.sqlplusplus.parser

import io.github.tmiskow.sqlplusplus.parser.expressions.operator.ExpressionParser
import io.github.tmiskow.sqlplusplus.parser.queries.SelectQueryParser

object Parser extends BaseParser
  with ExpressionParser
  with SelectQueryParser
