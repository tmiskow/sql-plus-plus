package io.github.tmiskow.sqlplusplus.interpreter.value

trait ScalarValue extends Value {
  def toCollectionValue: CollectionValue = ArrayValue(List(this))
}