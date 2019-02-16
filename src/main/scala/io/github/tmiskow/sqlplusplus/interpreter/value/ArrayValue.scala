package io.github.tmiskow.sqlplusplus.interpreter.value

import play.api.libs.json.Json

case class ArrayValue(seq: Seq[Value]) extends CollectionValue {
  override def toArrayValue: ArrayValue = this

  override def toString: String = Json.prettyPrint(Json.toJson(this))

  override def distinct: CollectionValue = ArrayValue(seq.distinct)
}
