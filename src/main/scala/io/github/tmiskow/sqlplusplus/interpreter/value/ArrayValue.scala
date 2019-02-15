package io.github.tmiskow.sqlplusplus.interpreter.value

case class ArrayValue(override val collection: List[Value]) extends CollectionValue(collection) {
  override def toString: String = collection.mkString("[", ",", "]")

  override def distinct: CollectionValue = {
    ArrayValue.fromValues(collection.distinct)
  }
}

object ArrayValue {
  def fromValues(values: Seq[Value]) = ArrayValue(values.toList)
}
