package io.github.tmiskow.sqlplusplus.interpreter.value

case class FloatValue(value: Float) extends ScalarValue {
  override def toString: String = value.toString

  override def +(other: Value): Value = other match {
    case IntValue(otherValue) => FloatValue(value + otherValue.toFloat)
    case FloatValue(otherValue) => FloatValue(value + otherValue)
    case _ => super.+(other)
  }

  override def -(other: Value): Value = other match {
    case IntValue(otherValue) => FloatValue(value - otherValue.toFloat)
    case FloatValue(otherValue) => FloatValue(value - otherValue)
    case _ => super.-(other)
  }

  override def *(other: Value): Value = other match {
    case IntValue(otherValue) => FloatValue(value * otherValue.toFloat)
    case FloatValue(otherValue) => FloatValue(value * otherValue)
    case _ => super.*(other)
  }

  override def /(other: Value): Value = other match {
    case IntValue(otherValue) => FloatValue(value / otherValue.toFloat)
    case FloatValue(otherValue) => FloatValue(value / otherValue)
    case _ => super./(other)
  }

  override def ^(other: Value): Value = other match {
    case IntValue(otherValue) =>
      FloatValue(scala.math.pow(value, otherValue).toFloat)
    case FloatValue(otherValue) =>
      FloatValue(scala.math.pow(value, otherValue).toFloat)
    case _ => super.^(other)
  }
}