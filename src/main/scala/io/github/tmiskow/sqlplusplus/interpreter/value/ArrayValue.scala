package io.github.tmiskow.sqlplusplus.interpreter.value

import play.api.libs.json.Json

case class ArrayValue(override val collection: List[Value]) extends CollectionValue(collection) {
  override def toString: String = Json.prettyPrint(Json.toJson(this))

  override def distinct: CollectionValue = {
    ArrayValue.fromValues(collection.distinct)
  }
}

object ArrayValue {
  def fromValues(values: Seq[Value]) = ArrayValue(values.toList)
}
