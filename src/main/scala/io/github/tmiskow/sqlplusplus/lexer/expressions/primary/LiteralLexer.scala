package io.github.tmiskow.sqlplusplus.lexer.expressions.primary

import io.github.tmiskow.sqlplusplus.lexer._

trait LiteralLexer extends BaseLexer {
  override def literal: Parser[Token] = (
    strLiteral
      | numericLiteral
      | nullLiteral
      | missingLiteral
      | booleanLiteral
    )

  def strLiteral: Parser[Token] = stringLiteral ^^ StringLiteralToken

  override def stringLiteral: Parser[String] = {
    val insideCharacters = ""+"""([^"\x00-\x1F\x7F\\]|\\[\\'"bfnrt]|\\u[a-fA-F0-9]{4})*"""+""
    val quote = "(\"|\')"
    (quote + insideCharacters + quote).r
  }

  def numericLiteral: Parser[Token] = floatingPointNumber ^^ {
    case string if string.matches("""-?\d+""") => IntNumericLiteralToken(string)
    case string => FloatNumericLiteralToken(string)
  }

  def nullLiteral: Parser[Token] = "null".ignoreCase ^^^ NullLiteralToken

  def missingLiteral: Parser[Token] = "missing".ignoreCase ^^^ MissingLiteralToken

  def booleanLiteral: Parser[Token] =
    ("true".ignoreCase ^^^ TrueLiteralToken) | ("false".ignoreCase ^^^ FalseLiteralToken)
}
