package io.github.tmiskow.sqlplusplusparser.expressions.quantified

import io.github.tmiskow.sqlplusplusparser._

import scala.util.matching.Regex

trait ReferenceLexer extends BaseLexer {
  override def reference: Parser[Token] = variable | parameter

  def parameter: Parser[Token] = namedParameter | positionalParameter

  def variable: Parser[Token] = (identifierRegex | delimitedIdentifierRegex) ^^ VariableToken

  def namedParameter: Parser[Token] =
    "$" ~> (identifierRegex | delimitedIdentifierRegex) ^^ (name => ParameterToken("$" + name))

  def positionalParameter: Parser[Token] = ("$\\d+" | "?") ^^ ParameterToken

  def identifierRegex: Regex = "[\\w_][\\w\\d_$]*".r

  def delimitedIdentifierRegex: Regex = {
    def insideCharacter = "(?:\\\"|\\\\|\\/|\\b|\\f|\\n|\\r|\\t|[^`\\\\])*"
    ("`" + insideCharacter + "`").r
  }
}
