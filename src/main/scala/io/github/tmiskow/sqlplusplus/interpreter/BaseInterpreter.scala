package io.github.tmiskow.sqlplusplus.interpreter

import io.github.tmiskow.sqlplusplus.interpreter.value.{CollectionValue, Value}
import io.github.tmiskow.sqlplusplus.parser._

trait BaseInterpreter {
  def evaluateQuery(query: Ast): Value = ???
  def evaluateSelectBlock(selectBlock: SelectBlockAst, environment: Environment): Value = ???
  def evaluateExpression(expression: ExpressionAst, environment: Environment): Value = ???
  def evaluateVariable(variable: VariableAst, environment: Environment): Value = ???
  def evaluateArithmeticExpression(operatorExpression: ArithmeticExpressionAst, environment: Environment): Value = ???
  def evaluateConstructor(constructor: ConstructorAst, environment: Environment): CollectionValue = ???
  def evaluateLiteral(literal: LiteralAst): Value = ???
}
