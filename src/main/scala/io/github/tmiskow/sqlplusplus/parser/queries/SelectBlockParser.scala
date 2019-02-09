package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer.{KeywordToken, Token}
import io.github.tmiskow.sqlplusplus.parser.{BaseParser, SelectBlockAst}

trait SelectBlockParser extends BaseParser {
  override def selectBlock: Parser[SelectBlockAst] =
    (KeywordToken("SELECT") ~> modifier) ~ (KeywordToken("VALUE") ~> expression) ^^ {
    case modifierAst ~ expressionAst => SelectBlockAst(modifierAst, expressionAst)
  }

  def modifier: Parser[Option[Token]] = opt(KeywordToken("ALL") | KeywordToken("DISTINCT"))
}
