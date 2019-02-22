package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer.{CommaToken, KeywordToken, Token}
import io.github.tmiskow.sqlplusplus.parser._

trait SelectBlockParser extends BaseParser {
  override def selectBlock: Parser[SelectBlockAst] =
    selectClause ~ (fromClause ?) ~ (whereClause ?) ^^ {
      case modifier ~ select ~ fromClause ~ whereClause =>
        SelectBlockAst(select, modifier, fromClause, whereClause)
    }

  private def selectClause: Parser[Option[Token] ~ SelectAst] =
    (KeywordToken("SELECT") ~> modifier) ~ (selectValue | selectRegular)

  private def whereClause: Parser[WhereClauseAst] =
    KeywordToken("WHERE") ~> comparisonExpression ^^ WhereClauseAst

  private def modifier: Parser[Option[Token]] = (KeywordToken("ALL") | KeywordToken("DISTINCT")) ?
}
