package io.github.tmiskow.sqlplusplus.parser

import io.github.tmiskow.sqlplusplus.parser.expressions.ExpressionParser
import io.github.tmiskow.sqlplusplus.parser.expressions.operator.{ArithmeticExpressionParser, ComparisonExpressionParser, PathExpressionParser}
import io.github.tmiskow.sqlplusplus.parser.expressions.primary.{ConstructorParser, PrimaryExpressionParser}
import io.github.tmiskow.sqlplusplus.parser.queries._

object Parser extends BaseParser
  with ExpressionParser
  with ArithmeticExpressionParser
  with ComparisonExpressionParser
  with PathExpressionParser
  with PrimaryExpressionParser
  with ConstructorParser
  with QueryParser
  with SelectBlockParser
  with SelectValueParser
  with SelectRegularParser
  with FromClauseParser
