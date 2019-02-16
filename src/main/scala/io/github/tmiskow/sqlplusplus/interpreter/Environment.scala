package io.github.tmiskow.sqlplusplus.interpreter

import io.github.tmiskow.sqlplusplus.interpreter.value.Value

import scala.collection.mutable

class Environment {
  private type Key = String
  private val map: mutable.Map[Key, Value] = mutable.Map[Key, Value]()

  def get(key: Key): Value = map.get(key) match {
    case Some(value) => value
    case None => throw InterpreterException(s"Variable not found: $key")
  }

  def put(key: Key, value: Value): Unit = map.put(key, value)

  def clear(): Unit = map.clear()

  def withEntry(key: Key, value: Value): Environment = {
    val clone = this.clone()
    clone.put(key, value)
    clone
  }

  override def clone(): Environment = {
    val clone: Environment = new Environment()
    clone.map ++= map
    clone
  }
}

object Environment {
  def empty: Environment = new Environment()
}