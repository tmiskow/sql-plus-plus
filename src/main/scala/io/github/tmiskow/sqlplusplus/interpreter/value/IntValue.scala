package io.github.tmiskow.sqlplusplus.interpreter.value

import io.github.tmiskow.sqlplusplus.ValueError

case class IntValue(value: Int) extends ScalarValue {
  override def toString: String = value.toString

  override def +(other: Value): Result = other match {
    case IntValue(otherValue) => Right(IntValue(value + otherValue))
    case FloatValue(otherValue) => Right(FloatValue(value.toFloat + otherValue))
    case _ => Left(ValueError("Operation not possible"))
  }

  override def -(other: Value): Result = other match {
    case IntValue(otherValue) => Right(IntValue(value - otherValue))
    case FloatValue(otherValue) => Right(FloatValue(value.toFloat - otherValue))
    case _ => Left(ValueError("Operation not possible"))
  }

  override def *(other: Value): Result = other match {
    case IntValue(otherValue) => Right(IntValue(value * otherValue))
    case FloatValue(otherValue) => Right(FloatValue(value.toFloat * otherValue))
    case _ => Left(ValueError("Operation not possible"))
  }

  override def /(other: Value): Result = other match {
    case IntValue(otherValue) => Right(FloatValue(value.toFloat / otherValue.toFloat))
    case FloatValue(otherValue) => Right(FloatValue(value.toFloat / otherValue))
    case _ => Left(ValueError("Operation not possible"))
  }

  override def %(other: Value): Result = other match {
    case IntValue(otherValue) => Right(IntValue(value % otherValue))
    case _ => Left(ValueError("Operation not possible"))
  }

  override def ^(other: Value): Result = other match {
    case IntValue(otherValue) =>
      Right(IntValue(scala.math.pow(value, otherValue).toInt))
    case FloatValue(otherValue) =>
      Right(FloatValue(scala.math.pow(value, otherValue).toFloat))
    case _ => Left(ValueError("Operation not possible"))
  }

  override def integerDivision(other: Value): Result = other match {
    case IntValue(otherValue) => Right(IntValue(value / otherValue))
    case _ => Left(ValueError("Operation not possible"))
  }
}
