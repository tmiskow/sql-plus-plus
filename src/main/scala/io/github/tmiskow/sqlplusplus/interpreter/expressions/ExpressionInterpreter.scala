package io.github.tmiskow.sqlplusplus.interpreter.expressions

import io.github.tmiskow.sqlplusplus.interpreter.value.{BooleanValue, Value}
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment, InterpreterException}
import io.github.tmiskow.sqlplusplus.parser._

trait ExpressionInterpreter extends BaseInterpreter {
  def evaluateComparisonExpression(comparisonExpression: ComparisonExpressionAst, environment: Environment): Value =
    comparisonExpression match {
      case EqualityAst(left, right) => evaluateBinaryComparison(_ === _)(left, right, environment)
      case InequalityAst(left, right) => evaluateBinaryComparison(_ <> _)(left, right, environment)
      case LessThanAst(left, right) => evaluateBinaryComparison(_ < _)(left, right, environment)
      case LessOrEqualThanAst(left, right) => evaluateBinaryComparison(_ <= _)(left, right, environment)
    }

  def evaluateBinaryComparison
  (operation: (Value, Value) => BooleanValue)
  (left: ExpressionAst, right: ExpressionAst, environment: Environment): BooleanValue = {
    val leftValue = evaluateExpression(left, environment)
    val rightValue = evaluateExpression(right, environment)
    operation(leftValue, rightValue)
  }

  override def evaluateExpression(expression: ExpressionAst, environment: Environment): Value =
    expression match {
      case literal: LiteralAst => evaluateLiteral(literal)
      case variable: VariableAst => evaluateVariable(variable, environment)
      case constructor: ConstructorAst => evaluateConstructor(constructor, environment)
      case comparisonExpression: ComparisonExpressionAst =>
        evaluateComparisonExpression(comparisonExpression, environment)
      case arithmeticExpression: ArithmeticExpressionAst =>
        evaluateArithmeticExpression(arithmeticExpression, environment)
      case _ => throw InterpreterException(s"Unexpected expression AST type: $expression")
    }
}
