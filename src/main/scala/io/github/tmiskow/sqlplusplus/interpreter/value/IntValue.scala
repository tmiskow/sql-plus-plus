package io.github.tmiskow.sqlplusplus.interpreter.value

case class IntValue(value: Int) extends ScalarValue {
  override def toString: String = value.toString

  override def +(other: Value): Value = other match {
    case IntValue(otherValue) => IntValue(value + otherValue)
    case FloatValue(otherValue) => FloatValue(value.toFloat + otherValue)
    case _ => super.+(other)
  }

  override def -(other: Value): Value = other match {
    case IntValue(otherValue) => IntValue(value - otherValue)
    case FloatValue(otherValue) => FloatValue(value.toFloat - otherValue)
    case _ => super.-(other)
  }

  override def *(other: Value): Value = other match {
    case IntValue(otherValue) => IntValue(value * otherValue)
    case FloatValue(otherValue) => FloatValue(value.toFloat * otherValue)
    case _ => super.*(other)
  }

  override def /(other: Value): Value = other match {
    case IntValue(otherValue) => FloatValue(value.toFloat / otherValue.toFloat)
    case FloatValue(otherValue) => FloatValue(value.toFloat / otherValue)
    case _ => super./(other)
  }

  override def %(other: Value): Value = other match {
    case IntValue(otherValue) => IntValue(value % otherValue)
    case _ => super.%(other)
  }

  override def ^(other: Value): Value = other match {
    case IntValue(otherValue) =>
      IntValue(scala.math.pow(value, otherValue).toInt)
    case FloatValue(otherValue) =>
      FloatValue(scala.math.pow(value, otherValue).toFloat)
    case _ => super.^(other)
  }

  override def integerDivision(other: Value): Value = other match {
    case IntValue(otherValue) => IntValue(value / otherValue)
    case _ => super.integerDivision(other)
  }
}
