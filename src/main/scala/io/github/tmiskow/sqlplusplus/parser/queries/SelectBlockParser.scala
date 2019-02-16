package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer.{CommaToken, KeywordToken, Token}
import io.github.tmiskow.sqlplusplus.parser._

trait SelectBlockParser extends BaseParser {
  override def selectBlock: Parser[SelectBlockAst] =
    selectClause ~ (fromClause?) ~ (whereClause?) ^^ {
      case modifier ~ expression ~ fromClause ~ whereClause =>
        SelectBlockAst(expression, modifier, fromClause, whereClause)
    }

  private def selectClause: Parser[Option[Token] ~ ExpressionAst] =
    (KeywordToken("SELECT") ~> modifier) ~ (KeywordToken("VALUE") ~> expression)

  private def whereClause: Parser[WhereClauseAst] =
    KeywordToken("WHERE") ~> comparisonExpression ^^ WhereClauseAst

  private def fromClause: Parser[FromClauseAst] =
    (KeywordToken("FROM") ~> rep1sep(fromTerm, CommaToken)) ^^ FromClauseAst

  //TODO: ... (( <AS> )? Variable)? ( ( JoinType )? ( JoinClause | UnnestClause ) )*
  private def fromTerm: Parser[FromTermAst] = (constructor ~ (KeywordToken("AS") ~> variable)) ^^ {
    case constructor ~ variable => FromTermAst(constructor, variable)
  }

  private def modifier: Parser[Option[Token]] = (KeywordToken("ALL") | KeywordToken("DISTINCT"))?
}
