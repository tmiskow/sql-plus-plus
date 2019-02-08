package io.github.tmiskow.sqlplusplus.interpreter.expressions.primary

import io.github.tmiskow.sqlplusplus.InterpreterError
import io.github.tmiskow.sqlplusplus.interpreter._
import io.github.tmiskow.sqlplusplus.lexer.{FloatNumericLiteralToken, IntNumericLiteralToken, NumericLiteralToken, StringLiteralToken}
import io.github.tmiskow.sqlplusplus.parser.LiteralAst

trait LiteralInterpreter extends BaseInterpreter {
  override def evaluateLiteral(literal: LiteralAst): Result[Value] = {
    literal.token match {
      case IntNumericLiteralToken(string) => Right(IntValue(string.toInt))
      case FloatNumericLiteralToken(string) => Right(FloatValue(string.toFloat))
      case StringLiteralToken(string) => Right(StringValue(string))
      case _ => Left(InterpreterError(""))
    }
  }
}
