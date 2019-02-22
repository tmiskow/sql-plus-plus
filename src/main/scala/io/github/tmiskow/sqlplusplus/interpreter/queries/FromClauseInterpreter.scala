package io.github.tmiskow.sqlplusplus.interpreter.queries

import io.github.tmiskow.sqlplusplus.interpreter.value.ArrayValue
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Context, Environment, InterpreterException}
import io.github.tmiskow.sqlplusplus.parser._

trait FromClauseInterpreter extends BaseInterpreter {

  private def evaluateUnnestClause(environment: Environment, maybeUnnestClause: Option[UnnestClauseAst]): Seq[Environment] =
    maybeUnnestClause match {
      case None => Seq(environment)
      case Some(unnestClause) => evaluateExpression(unnestClause.expression, environment) match {
        case ArrayValue(values) => for (value <- values)
          yield environment.withEntry(unnestClause.variable.name, value)
        case _ => throw InterpreterException("UNNEST clause must apply to array value")
      }
    }

  override def evaluateFromClause(fromClause: FromClauseAst, environment: Environment): Context = {
    assert(fromClause.terms.size == 1)
    val fromTerm = fromClause.terms.head
    val environments = evaluateExpression(fromTerm.expression, environment) match {
      case ArrayValue(values) =>
        for (value <- values) yield environment.withEntry(fromTerm.variable.name, value)
      case _ => throw InterpreterException("Collection used in FROM clause must be an array")
    }
    val unnestedEnvironments = for {
      environment <- environments
      unnestedEnvironment <- evaluateUnnestClause(environment, fromTerm.unnestClause)
    } yield unnestedEnvironment
    Context(Seq[String](fromTerm.variable.name), unnestedEnvironments)
  }
}
