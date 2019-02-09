package io.github.tmiskow.sqlplusplus.lexer

import scala.util.matching.Regex

trait ReferenceLexer extends BaseLexer {
  override def reference: Parser[Token] = variable | parameter

  private def parameter: Parser[Token] = namedParameter | positionalParameter

  private def variable: Parser[Token] = (identifierRegex | delimitedIdentifierRegex) ^^ VariableToken

  private def namedParameter: Parser[Token] =
    "$" ~> (identifierRegex | delimitedIdentifierRegex) ^^ (name => ParameterToken("$" + name))

  private def positionalParameter: Parser[Token] = ("$\\d+" | "?") ^^ ParameterToken

  private def identifierRegex: Regex = "[\\w_][\\w\\d_$]*".r

  private def delimitedIdentifierRegex: Regex = {
    def insideCharacter = "(?:\\\"|\\\\|\\/|\\b|\\f|\\n|\\r|\\t|[^`\\\\])*"
    ("`" + insideCharacter + "`").r
  }
}
