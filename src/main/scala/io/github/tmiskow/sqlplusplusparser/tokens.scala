package io.github.tmiskow.sqlplusplusparser

trait Token

case object LeftParenthesisToken extends Token
case object RightParenthesisToken extends Token
case class OperatorToken(string: String) extends Token
case class NumericLiteralToken(string: String) extends Token
case class StringLiteralToken(string: String) extends Token

case class KeywordToken(string: String) extends Token