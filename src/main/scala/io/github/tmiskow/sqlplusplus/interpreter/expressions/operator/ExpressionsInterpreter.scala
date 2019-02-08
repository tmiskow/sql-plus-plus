package io.github.tmiskow.sqlplusplus.interpreter.expressions.operator

import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, IntValue, Value}
import io.github.tmiskow.sqlplusplus.parser.{AdditionAst, ExpressionAst, LiteralAst}

trait ExpressionsInterpreter extends BaseInterpreter {
  override def evaluateExpression(expression: ExpressionAst): Result[Value] = {
    expression match {
      case ast@LiteralAst(_) => evaluateLiteral(ast)
      case AdditionAst(left, right) => evaluateAddition(left, right)
      case _ => Right(IntValue(0))
    }
  }

  private def evaluateAddition(left: ExpressionAst, right: ExpressionAst): Result[Value] = {
    evaluateExpression(left) match {
      case result@Left(_) => result
      case Right(leftValue) => evaluateExpression(right) match {
        case result@Left(_) => result
        case Right(rightValue) => Right(leftValue + rightValue)
      }
    }
  }
}
