package io.github.tmiskow.sqlplusplus.interpreter.value

trait CollectionValue extends Value {
  def distinct: CollectionValue = ???
}
