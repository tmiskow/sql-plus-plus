package io.github.tmiskow.sqlplusplus.lexer

trait LiteralLexer extends BaseLexer {
  private val intPattern = """-?\d+"""
  private val floatPattern = """-?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?[fFdD]?"""

  override def literal: Parser[Token] =
    stringLiteral | numericLiteral | nullLiteral | missingLiteral | booleanLiteral

  private def stringLiteral: Parser[Token] = {
    val insideCharacters = """([^"\x00-\x1F\x7F\\]|\\[\\'"bfnrt]|\\u[a-fA-F0-9]{4})*"""
    val quote = "(\"|\')"
    (quote + insideCharacters + quote).r
  } ^^ {_.drop(1).dropRight(1)} ^^ StringLiteralToken

  private def numericLiteral: Parser[Token] = floatPattern.r ^^ {
    case string if string.matches(intPattern) => IntNumericLiteralToken(string.toInt)
    case string => FloatNumericLiteralToken(string.toFloat)
  }

  private def nullLiteral: Parser[Token] = "null".ignoreCase ^^^ NullLiteralToken

  private def missingLiteral: Parser[Token] = "missing".ignoreCase ^^^ MissingLiteralToken

  private def booleanLiteral: Parser[Token] =
    ("true".ignoreCase ^^^ TrueLiteralToken) | ("false".ignoreCase ^^^ FalseLiteralToken)
}
