package io.github.tmiskow.sqlplusplus.interpreter.queries

import io.github.tmiskow.sqlplusplus.interpreter.value.ArrayValue
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Context, Environment, InterpreterException}
import io.github.tmiskow.sqlplusplus.parser._

trait FromClauseInterpreter extends BaseInterpreter {
  override def evaluateFromClause(fromClause: FromClauseAst, environment: Environment): Context = {
    assert(fromClause.terms.size == 1)
    val fromTerm = fromClause.terms.head
    val environments = evaluateExpression(fromTerm.expression, environment) match {
      case ArrayValue(values) =>
        for (value <- values) yield environment.withEntry(fromTerm.variable.name, value)
      case _ => throw InterpreterException("Collection used in FROM clause must be an array")
    }
    Context(Seq[String](fromTerm.variable.name), environments)
  }
}
