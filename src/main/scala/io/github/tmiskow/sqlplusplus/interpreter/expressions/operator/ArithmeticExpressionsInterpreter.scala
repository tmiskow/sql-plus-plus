package io.github.tmiskow.sqlplusplus.interpreter.expressions.operator

import io.github.tmiskow.sqlplusplus.interpreter.value.Value
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment}
import io.github.tmiskow.sqlplusplus.parser._

trait ArithmeticExpressionsInterpreter extends BaseInterpreter {
  override def evaluateArithmeticExpression(expression: ArithmeticExpressionAst, environment: Environment): Value =
    expression match {
      case AdditionAst(left, right) => evaluateBinaryOperation(_ + _)(left, right, environment)
      case SubtractionAst(left, right) => evaluateBinaryOperation(_ - _)(left, right, environment)
      case MultiplicationAst(left, right) => evaluateBinaryOperation(_ * _)(left, right, environment)
      case DivisionAst(left, right) => evaluateBinaryOperation(_ / _)(left, right, environment)
      case ExponentiationAst(left, right) => evaluateBinaryOperation(_ ^ _)(left, right, environment)
      case ModuloAst(left, right) => evaluateBinaryOperation(_ % _)(left, right, environment)
      case IntegerDivisionAst(left, right) =>
        evaluateBinaryOperation(_.integerDivision(_))(left, right, environment)
    }

  private def evaluateBinaryOperation(operation: (Value, Value) => Value)(left: ExpressionAst, right: ExpressionAst, environment: Environment): Value = {
    val leftValue = evaluateExpression(left, environment)
    val rightValue = evaluateExpression(right, environment)
    operation(leftValue, rightValue)
  }
}
