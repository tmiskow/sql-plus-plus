package io.github.tmiskow.sqlplusplus.interpreter.value

case class FloatValue(value: Float) extends ScalarValue {
  override def toString: String = value.toString

  override def +(other: Value): Result = other match {
    case IntValue(otherValue) => Right(FloatValue(value + otherValue.toFloat))
    case FloatValue(otherValue) => Right(FloatValue(value + otherValue))
    case _ => super.+(other)
  }

  override def -(other: Value): Result = other match {
    case IntValue(otherValue) => Right(FloatValue(value - otherValue.toFloat))
    case FloatValue(otherValue) => Right(FloatValue(value - otherValue))
    case _ => super.-(other)
  }

  override def *(other: Value): Result = other match {
    case IntValue(otherValue) => Right(FloatValue(value * otherValue.toFloat))
    case FloatValue(otherValue) => Right(FloatValue(value * otherValue))
    case _ => super.*(other)
  }

  override def /(other: Value): Result = other match {
    case IntValue(otherValue) => Right(FloatValue(value / otherValue.toFloat))
    case FloatValue(otherValue) => Right(FloatValue(value / otherValue))
    case _ => super./(other)
  }

  override def ^(other: Value): Result = other match {
    case IntValue(otherValue) =>
      Right(FloatValue(scala.math.pow(value, otherValue).toFloat))
    case FloatValue(otherValue) =>
      Right(FloatValue(scala.math.pow(value, otherValue).toFloat))
    case _ => super.^(other)
  }
}