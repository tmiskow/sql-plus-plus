package io.github.tmiskow.sqlplusplus.parser.expressions

import io.github.tmiskow.sqlplusplus.parser.{BaseParser, ExpressionAst}

trait ExpressionParser extends BaseParser {
  override def expression: Parser[ExpressionAst] = constructor | comparisonExpression | arithmeticExpression
}
