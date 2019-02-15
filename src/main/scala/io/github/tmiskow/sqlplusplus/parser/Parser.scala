package io.github.tmiskow.sqlplusplus.parser

import io.github.tmiskow.sqlplusplus.parser.expressions.ExpressionParser
import io.github.tmiskow.sqlplusplus.parser.expressions.operator.{ArithmeticExpressionParser, ComparisonExpressionParser}
import io.github.tmiskow.sqlplusplus.parser.expressions.primary.{ConstructorParser, PrimaryExpressionParser}
import io.github.tmiskow.sqlplusplus.parser.queries.{QueryParser, SelectBlockParser}

object Parser extends BaseParser
  with ExpressionParser
  with ArithmeticExpressionParser
  with ComparisonExpressionParser
  with PrimaryExpressionParser
  with ConstructorParser
  with QueryParser
  with SelectBlockParser
