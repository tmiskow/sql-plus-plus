package io.github.tmiskow.sqlplusplus.lexer

sealed trait Token

sealed trait SymbolToken extends Token
case object LeftParenthesisToken extends SymbolToken
case object RightParenthesisToken extends SymbolToken
case object LeftArrayBracketToken extends SymbolToken
case object RightArrayBracketToken extends SymbolToken
case object LeftObjectBracketToken extends SymbolToken
case object RightObjectBracketToken extends SymbolToken
case object CommaToken extends SymbolToken
case object SemicolonToken extends SymbolToken
case object ColonToken extends SymbolToken
case class OperatorToken(string: String) extends SymbolToken

sealed trait LiteralToken extends Token
case class StringLiteralToken(string: String) extends LiteralToken
case object NullLiteralToken extends LiteralToken
case object MissingLiteralToken extends LiteralToken
case object TrueLiteralToken extends LiteralToken
case object FalseLiteralToken extends LiteralToken
sealed trait NumericLiteralToken extends LiteralToken
case class IntNumericLiteralToken(int: Int) extends NumericLiteralToken
case class FloatNumericLiteralToken(float: Float) extends NumericLiteralToken

case class VariableToken(string: String) extends Token
case class ParameterToken(string: String) extends Token

case class KeywordToken(string: String) extends Token