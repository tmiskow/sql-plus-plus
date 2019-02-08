package io.github.tmiskow.sqlplusplus.interpreter

sealed trait Value {
  def +(other: Value): Value = ???
}

case class IntValue(value: Int) extends Value {
  override def +(other: Value): Value = {
    other match {
      case IntValue(otherValue) => IntValue(value + otherValue)
      case FloatValue(otherValue) => FloatValue(value.toFloat + otherValue)
      case StringValue(otherValue) => StringValue(value.toString + otherValue)
    }
  }

  override def toString: String = value.toString
}

case class FloatValue(value: Float) extends Value {
  override def +(other: Value): Value = {
    other match {
      case IntValue(otherValue) => FloatValue(value + otherValue.toFloat)
      case FloatValue(otherValue) => FloatValue(value + otherValue)
      case StringValue(otherValue) => StringValue(value.toString + otherValue)
    }
  }

  override def toString: String = value.toString
}

case class StringValue(value: String) extends Value {
  override def +(other: Value): Value = {
    other match {
      case IntValue(otherValue) => StringValue(value + otherValue.toString)
      case FloatValue(otherValue) => StringValue(value + otherValue.toString)
      case StringValue(otherValue) => StringValue(value + otherValue)
    }
  }

  override def toString: String = value.toString
}