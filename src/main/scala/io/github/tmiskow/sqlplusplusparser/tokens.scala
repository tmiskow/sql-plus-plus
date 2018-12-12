package io.github.tmiskow.sqlplusplusparser

sealed abstract class Tokens
  case object LeftParenthesisToken extends Tokens
  case object RightParenthesisToken extends Tokens
  case class OperatorToken(string: String) extends Tokens
  abstract class LiteralToken(string: String) extends Tokens
    case class StringLiteralToken(string: String) extends LiteralToken(string)
    case class NumericLiteralToken(string: String) extends LiteralToken(string)
