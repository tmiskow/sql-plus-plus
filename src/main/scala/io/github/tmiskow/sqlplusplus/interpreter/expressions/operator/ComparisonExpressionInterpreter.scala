package io.github.tmiskow.sqlplusplus.interpreter.expressions.operator

import io.github.tmiskow.sqlplusplus.interpreter.value.{BooleanValue, Value}
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment}
import io.github.tmiskow.sqlplusplus.parser._

trait ComparisonExpressionInterpreter extends BaseInterpreter {
  override def evaluateComparisonExpression(comparisonExpression: ComparisonExpressionAst, environment: Environment): Value =
    comparisonExpression match {
      case EqualityAst(left, right) => evaluateBinaryComparison(_ === _)(left, right, environment)
      case InequalityAst(left, right) => evaluateBinaryComparison(_ <> _)(left, right, environment)
      case LessThanAst(left, right) => evaluateBinaryComparison(_ < _)(left, right, environment)
      case LessOrEqualThanAst(left, right) => evaluateBinaryComparison(_ <= _)(left, right, environment)
    }

  private def evaluateBinaryComparison
  (operation: (Value, Value) => BooleanValue)
  (left: ExpressionAst, right: ExpressionAst, environment: Environment): BooleanValue = {
    val leftValue = evaluateExpression(left, environment)
    val rightValue = evaluateExpression(right, environment)
    operation(leftValue, rightValue)
  }
}
