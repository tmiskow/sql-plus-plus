package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer.{CommaToken, KeywordToken, Token}
import io.github.tmiskow.sqlplusplus.parser._

trait SelectBlockParser extends BaseParser {
  override def selectBlock: Parser[SelectBlockAst] =
    ((KeywordToken("SELECT") ~> modifier) ~ (KeywordToken("VALUE") ~> expression) ~ (fromClause?)) ^^
      {case modifier ~ expression ~ fromClause => SelectBlockAst(expression, fromClause, modifier)}

  private def fromClause: Parser[FromClauseAst] =
    (KeywordToken("FROM") ~> rep1sep(fromTerm, CommaToken)) ^^ FromClauseAst

  //TODO: ... (( <AS> )? Variable)? ( ( JoinType )? ( JoinClause | UnnestClause ) )*
  private def fromTerm: Parser[FromTermAst] =
    (expression ~ (KeywordToken("AS") ~> variable)) ^^ {case expression ~ variable => FromTermAst(expression, variable)}

  private def modifier: Parser[Option[Token]] = opt(KeywordToken("ALL") | KeywordToken("DISTINCT"))
}
