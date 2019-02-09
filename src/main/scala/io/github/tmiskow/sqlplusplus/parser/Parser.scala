package io.github.tmiskow.sqlplusplus.parser

import io.github.tmiskow.sqlplusplus.parser.expressions.operator.ExpressionParser
import io.github.tmiskow.sqlplusplus.parser.expressions.primary.ConstructorParser
import io.github.tmiskow.sqlplusplus.parser.queries.{QueryParser, SelectBlockParser}

object Parser extends BaseParser
  with ExpressionParser
  with ConstructorParser
  with QueryParser
  with SelectBlockParser
