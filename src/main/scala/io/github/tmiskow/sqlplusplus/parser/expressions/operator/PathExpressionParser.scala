package io.github.tmiskow.sqlplusplus.parser.expressions.operator

import io.github.tmiskow.sqlplusplus.lexer.{DotToken, LeftArrayBracketToken, RightArrayBracketToken}
import io.github.tmiskow.sqlplusplus.parser._

trait PathExpressionParser extends BaseParser {
  override def pathExpression: Parser[PathExpressionAst] = (constructor | variable) ~ rep1(field | index) ^^ {
    case expression ~ specifiers => PathExpressionAst(expression, specifiers)
  }

  private def index: Parser[IndexAst] =
    (LeftArrayBracketToken ~> arithmeticExpression <~ RightArrayBracketToken) ^^ IndexAst

  private def field: Parser[FieldAst] = DotToken ~> identifier ^^ FieldAst

  private def identifier: Parser[VariableAst] = variable
}
