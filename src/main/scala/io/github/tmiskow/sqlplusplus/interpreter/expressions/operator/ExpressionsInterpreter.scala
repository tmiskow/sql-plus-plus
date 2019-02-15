package io.github.tmiskow.sqlplusplus.interpreter.expressions.operator

import io.github.tmiskow.sqlplusplus.interpreter.value.{ArrayValue, Value}
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment, InterpreterException}
import io.github.tmiskow.sqlplusplus.parser._

trait ExpressionsInterpreter extends BaseInterpreter {
  override def evaluateExpression(expression: ExpressionAst, environment: Environment): Value =
    expression match {
      case literal: LiteralAst => evaluateLiteral(literal)
      case variable: VariableAst => evaluateVariable(variable, environment)
      case operatorExpression: OperatorExpressionAst => evaluateOperatorExpression(operatorExpression, environment)
      case constructor: ConstructorAst => evaluateConstructor(constructor, environment)
      case _ => throw InterpreterException(s"Unexpected expression AST type: $expression")
    }

  private def evaluateOperatorExpression(expression: ExpressionAst, environment: Environment): Value =
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

  def evaluateConstructor(constructor: ConstructorAst, environment: Environment): Value =
    constructor match {
      case arrayConstructor: ArrayConstructorAst => evaluateArrayConstructor(arrayConstructor, environment)
    }

  private def evaluateArrayConstructor(arrayConstructor: ArrayConstructorAst, environment: Environment): ArrayValue = {
    val elements = arrayConstructor.elements
    val values = for (expression <- elements) yield evaluateExpression(expression, environment)
    ArrayValue(values.toList)
  }


  private def evaluateVariable(variable: VariableAst, environment: Environment): Value =
    environment.get(variable.name)

  private def evaluateBinaryOperation(operation: (Value, Value) => Value)
                                     (left: ExpressionAst, right: ExpressionAst, environment: Environment): Value = {
    val leftValue = evaluateExpression(left, environment)
    val rightValue = evaluateExpression(right, environment)
    operation(leftValue, rightValue)
  }
}
