package io.github.tmiskow.sqlplusplus.interpreter.expressions.primary

import io.github.tmiskow.sqlplusplus.interpreter._
import io.github.tmiskow.sqlplusplus.interpreter.value.{FloatValue, IntValue, StringValue, Value}
import io.github.tmiskow.sqlplusplus.lexer.{FloatNumericLiteralToken, IntNumericLiteralToken, StringLiteralToken}
import io.github.tmiskow.sqlplusplus.parser.LiteralAst

trait LiteralInterpreter extends BaseInterpreter {
  override def evaluateLiteral(literal: LiteralAst): Value = {
    literal.token match {
      case IntNumericLiteralToken(int) => IntValue(int)
      case FloatNumericLiteralToken(float) => FloatValue(float)
      case StringLiteralToken(string) => StringValue(string)
      case _ => throw InterpreterException("Unexpected literal AST type")
    }
  }
}
