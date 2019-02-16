package io.github.tmiskow.sqlplusplus.parser.expressions.primary

import io.github.tmiskow.sqlplusplus.lexer._
import io.github.tmiskow.sqlplusplus.parser._

trait ConstructorParser extends BaseParser {
  override def constructor: Parser[ConstructorAst] = arrayConstructor | objectConstructor

  private def arrayConstructor: Parser[ConstructorAst] =
    LeftArrayBracketToken ~> repsep(expression, CommaToken) <~ RightArrayBracketToken ^^ ArrayConstructorAst

  private def objectConstructor: Parser[ConstructorAst] =
    LeftObjectBracketToken ~> repsep(fieldBinding, CommaToken) <~ RightObjectBracketToken ^^ {_.toMap} ^^ ObjectConstructorAst

  def fieldBinding: Parser[(LiteralAst, ExpressionAst)] = (stringLiteral <~ ColonToken) ~ expression ^^ {
    case stringLiteral ~ expression => stringLiteral -> expression
  }
}
