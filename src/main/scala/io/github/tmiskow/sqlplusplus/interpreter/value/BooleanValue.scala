package io.github.tmiskow.sqlplusplus.interpreter.value

sealed abstract class BooleanValue(value: Boolean) extends Value {
  override def toCollectionValue: CollectionValue = ArrayValue.fromValues(List(this))
  override def toString: String = value.toString
}

object BooleanValue {
  def from(value: Boolean): BooleanValue = if (value) TrueValue else FalseValue
}

object TrueValue extends BooleanValue(true)
object FalseValue extends BooleanValue(false)

