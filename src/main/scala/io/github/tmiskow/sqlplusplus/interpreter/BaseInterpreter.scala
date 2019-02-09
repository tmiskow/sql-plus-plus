package io.github.tmiskow.sqlplusplus.interpreter

import io.github.tmiskow.sqlplusplus.interpreter.value.Value
import io.github.tmiskow.sqlplusplus.parser._

trait BaseInterpreter {
  def evaluateQuery(query: Ast): Value = ???
  def evaluateSelectBlock(selectBlock: SelectBlockAst, environment: Environment): Value = ???
  def evaluateSelectClause(query: SelectClauseAst, environment: Environment): Value = ???
  def evaluateExpression(expression: ExpressionAst, environment: Environment): Value = ???
  def evaluateLiteral(literal: LiteralAst): Value = ???
}
