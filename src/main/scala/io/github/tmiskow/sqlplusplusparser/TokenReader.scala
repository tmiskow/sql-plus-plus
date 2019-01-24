package io.github.tmiskow.sqlplusplusparser

import scala.util.parsing.input.{NoPosition, Position, Reader}

private class TokenReader[T <: Token](tokens: Seq[T]) extends Reader[T] {
  override def first: T = tokens.head
  override def atEnd: Boolean = tokens.isEmpty
  override def pos: Position = NoPosition
  override def rest: Reader[T] = new TokenReader(tokens.tail)
}
