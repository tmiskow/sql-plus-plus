package io.github.tmiskow.sqlplusplus

sealed trait Error
case class LexerError(location: Location, message: String) extends Error
case class ParserError(location: Location, message: String) extends Error
case class InterpreterError(message: String) extends Error

case class Location(line: Int, column: Int) {
  override def toString = s"$line:$column"
}
