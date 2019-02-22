package io.github.tmiskow.sqlplusplus.interpreter

import io.github.tmiskow.sqlplusplus.interpreter.value.{CollectionValue, Value}
import io.github.tmiskow.sqlplusplus.parser._

trait BaseInterpreter {
  def evaluateQuery(query: Ast, environment: Environment): Value = ???
  def evaluateSelectBlock(selectBlock: SelectBlockAst, environment: Environment): Value = ???
  def evaluateSelectRegular(selectRegular: SelectRegularAst, context: Context): Seq[Value] = ???
  def evaluateFromClause(fromClause: FromClauseAst, environment: Environment): Context = ???
  def evaluateExpression(expression: ExpressionAst, environment: Environment): Value = ???
  def evaluateVariable(variable: VariableAst, environment: Environment): Value = ???
  def evaluateComparisonExpression(comparisonExpression: ComparisonExpressionAst, environment: Environment): Value = ???
  def evaluateArithmeticExpression(arithmeticExpression: ArithmeticExpressionAst, environment: Environment): Value = ???
  def evaluatePathExpression(pathExpression: PathExpressionAst, environment: Environment): Value = ???
  def evaluateConstructor(constructor: ConstructorAst, environment: Environment): CollectionValue = ???
  def evaluateLiteral(literal: LiteralAst): Value = ???
}
