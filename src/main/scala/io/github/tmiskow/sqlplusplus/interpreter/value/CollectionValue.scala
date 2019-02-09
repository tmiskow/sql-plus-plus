package io.github.tmiskow.sqlplusplus.interpreter.value

abstract class CollectionValue(val collection: Seq[Value]) extends Value {
  def toCollectionValue: CollectionValue = this
}
