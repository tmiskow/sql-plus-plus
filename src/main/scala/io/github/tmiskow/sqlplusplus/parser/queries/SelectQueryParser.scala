package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer.{KeywordToken, Token}
import io.github.tmiskow.sqlplusplus.parser.{Ast, BaseParser, SelectQueryAst}

trait SelectQueryParser extends BaseParser {
  override def selectQuery: Parser[Ast] = (selectKeyword ~> modifier) ~ (valueKeyword ~> expression) ^^ {
    case modifierAst ~ expressionAst => SelectQueryAst(modifierAst, expressionAst)
  }

  def selectKeyword: Parser[Token] = KeywordToken("SELECT")

  def valueKeyword: Parser[Token] =
    KeywordToken("VALUE") | KeywordToken("ELEMENT") | KeywordToken("RAW")

  def modifier: Parser[Option[Token]] = opt(KeywordToken("ALL") | KeywordToken("DISTINCT"))
}
