package io.github.tmiskow.sqlplusplus.interpreter.value


case class StringValue(value: String) extends ScalarValue {
  override def toString: String = value.toString
}
