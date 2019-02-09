package io.github.tmiskow.sqlplusplus.parser.expressions.primary

import io.github.tmiskow.sqlplusplus.lexer.{CommaToken, LeftArrayBracketToken, RightArrayBracketToken}
import io.github.tmiskow.sqlplusplus.parser.{ArrayConstructorAst, BaseParser, ConstructorAst}

trait ConstructorParser extends BaseParser {
  override def constructor: Parser[ConstructorAst] = arrayConstructor

  private def arrayConstructor: Parser[ConstructorAst] =
    LeftArrayBracketToken ~> repsep(expression, CommaToken) <~ RightArrayBracketToken ^^ ArrayConstructorAst
}
