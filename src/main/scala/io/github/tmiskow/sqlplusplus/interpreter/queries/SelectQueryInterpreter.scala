package io.github.tmiskow.sqlplusplus.interpreter.queries

import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Value}
import io.github.tmiskow.sqlplusplus.parser.SelectQueryAst

trait SelectQueryInterpreter extends BaseInterpreter {
  def evaluateSelectQuery(query: SelectQueryAst): Result[Value] = {
    val expression = query.expression
    evaluateExpression(expression) match {
      case result@Left(_) => result
      case Right(value) => Right(value)
    }
  }
}
