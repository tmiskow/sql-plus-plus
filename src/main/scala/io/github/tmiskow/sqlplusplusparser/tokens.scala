package io.github.tmiskow.sqlplusplusparser

sealed trait Token

case object LeftParenthesisToken extends Token
case object RightParenthesisToken extends Token
case class OperatorToken(string: String) extends Token

case class StringLiteralToken(string: String) extends Token
case class NumericLiteralToken(string: String) extends Token
case object NullLiteralToken extends Token
case object MissingLiteralToken extends Token
case object TrueLiteralToken extends Token
case object FalseLiteralToken extends Token

case class VariableToken(string: String) extends Token
case class ParameterToken(string: String) extends Token

case class KeywordToken(string: String) extends Token