package io.github.tmiskow.sqlplusplus

sealed abstract class Error(location: Location, message: String)
case class LexerError(location: Location, message: String) extends Error(location, message)
case class ParserError(location: Location, message: String) extends Error(location, message)

case class Location(line: Int, column: Int) {
  override def toString = s"$line:$column"
}
