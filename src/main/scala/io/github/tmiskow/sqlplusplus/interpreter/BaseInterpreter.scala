package io.github.tmiskow.sqlplusplus.interpreter

import io.github.tmiskow.sqlplusplus.InterpreterError
import io.github.tmiskow.sqlplusplus.parser.{ExpressionAst, LiteralAst}

trait BaseInterpreter {
  type Result[T] = Either[InterpreterError, T]
  def evaluateExpression(expression: ExpressionAst): Result[Value] = ???
  def evaluateLiteral(literal: LiteralAst): Result[Value] = ???
}
