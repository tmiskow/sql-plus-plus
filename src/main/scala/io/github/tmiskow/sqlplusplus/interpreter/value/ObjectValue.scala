package io.github.tmiskow.sqlplusplus.interpreter.value

import play.api.libs.json.Json

case class ObjectValue(map: Map[String, Value]) extends CollectionValue {
  override def toString: String = Json.prettyPrint(Json.toJson(this))

  override def distinct: CollectionValue = this
}
