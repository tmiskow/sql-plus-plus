package io.github.tmiskow.sqlplusplus.lexer

sealed trait Token

case object LeftParenthesisToken extends Token
case object RightParenthesisToken extends Token
case class OperatorToken(string: String) extends Token

case class StringLiteralToken(string: String) extends Token
case object NullLiteralToken extends Token
case object MissingLiteralToken extends Token
case object TrueLiteralToken extends Token
case object FalseLiteralToken extends Token

trait NumericLiteralToken extends Token
case class IntNumericLiteralToken(string: String) extends NumericLiteralToken
case class FloatNumericLiteralToken(string: String) extends NumericLiteralToken

case class VariableToken(string: String) extends Token
case class ParameterToken(string: String) extends Token

case class KeywordToken(string: String) extends Token