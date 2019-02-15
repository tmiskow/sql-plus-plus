package io.github.tmiskow.sqlplusplus.interpreter.value

case class ArrayValue(override val collection: List[Value]) extends CollectionValue(collection)

object ArrayValue {
  def fromValues(values: Seq[Value]) = ArrayValue(values.toList)
}
