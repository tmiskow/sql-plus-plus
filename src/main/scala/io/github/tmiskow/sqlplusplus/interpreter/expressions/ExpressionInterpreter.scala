package io.github.tmiskow.sqlplusplus.interpreter.expressions

import io.github.tmiskow.sqlplusplus.interpreter.value.{BooleanValue, Value}
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment, InterpreterException}
import io.github.tmiskow.sqlplusplus.parser._

trait ExpressionInterpreter extends BaseInterpreter {
  override def evaluateExpression(expression: ExpressionAst, environment: Environment): Value =
    expression match {
      case literal: LiteralAst => evaluateLiteral(literal)
      case variable: VariableAst => evaluateVariable(variable, environment)
      case constructor: ConstructorAst => evaluateConstructor(constructor, environment)
      case comparisonExpression: ComparisonExpressionAst =>
        evaluateComparisonExpression(comparisonExpression, environment)
      case arithmeticExpression: ArithmeticExpressionAst =>
        evaluateArithmeticExpression(arithmeticExpression, environment)
      case pathExpression: PathExpressionAst =>
        evaluatePathExpression(pathExpression, environment)
      case _ => throw InterpreterException(s"Unexpected expression AST type: $expression")
    }
}
