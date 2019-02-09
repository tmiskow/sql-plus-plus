package io.github.tmiskow.sqlplusplus.interpreter.queries

import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Value}
import io.github.tmiskow.sqlplusplus.parser.SelectBlockAst

trait SelectQueryInterpreter extends BaseInterpreter {
  def evaluateSelectQuery(query: SelectBlockAst): Result[Value] = {
    val expression = query.expression
    evaluateExpression(expression) match {
      case result@Left(_) => result
      case Right(value) => Right(value)
    }
  }
}
