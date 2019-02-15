package io.github.tmiskow.sqlplusplus.interpreter.value

import io.github.tmiskow.sqlplusplus.interpreter.InterpreterException
import play.api.libs.json._

trait Value {
  private def throwDefaultError = throw InterpreterException("Value operation not permitted")

  def ===(other: Value): BooleanValue = throwDefaultError

  def <>(other: Value): BooleanValue = throwDefaultError

  def <(other: Value): BooleanValue = throwDefaultError

  def <=(other: Value): BooleanValue = throwDefaultError

  def +(other: Value): Value = throwDefaultError

  def -(other: Value): Value = throwDefaultError

  def *(other: Value): Value = throwDefaultError

  def /(other: Value): Value = throwDefaultError

  def %(other: Value): Value = throwDefaultError

  def ^(other: Value): Value = throwDefaultError

  def integerDivision(other: Value): Value = throwDefaultError

  def toCollectionValue: CollectionValue
}

object Value {
  implicit object ValueFormat extends Format[Value] {
    def writes(value: Value): JsValue = value match {
      case IntValue(integer) => JsNumber(integer)
      case FloatValue(float) => JsNumber(float.toDouble)
      case StringValue(string) => JsString(string)
      case ArrayValue(collection) => JsArray(collection.map {writes})
      case TrueValue => JsTrue
      case FalseValue => JsFalse
    }

    def reads(json: JsValue): JsResult[Value] = ???
  }
}