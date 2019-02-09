package io.github.tmiskow.sqlplusplus.interpreter.value

import io.github.tmiskow.sqlplusplus.interpreter.InterpreterException

trait Value {
  private def throwDefaultError = throw InterpreterException("Value operation not permitted")

  def +(other: Value): Value = throwDefaultError

  def -(other: Value): Value = throwDefaultError

  def *(other: Value): Value = throwDefaultError

  def /(other: Value): Value = throwDefaultError

  def %(other: Value): Value = throwDefaultError

  def ^(other: Value): Value = throwDefaultError

  def integerDivision(other: Value): Value = throwDefaultError

  def toCollectionValue: CollectionValue
}