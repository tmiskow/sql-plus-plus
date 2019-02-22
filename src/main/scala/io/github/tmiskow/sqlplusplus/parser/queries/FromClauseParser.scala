package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer.{CommaToken, KeywordToken}
import io.github.tmiskow.sqlplusplus.parser.{BaseParser, FromClauseAst, FromTermAst}

trait FromClauseParser extends BaseParser {
  override def fromClause: Parser[FromClauseAst] =
    (KeywordToken("FROM") ~> rep1sep(fromTerm, CommaToken)) ^^ FromClauseAst

  //TODO: ... (( <AS> )? Variable)? ( ( JoinType )? ( JoinClause | UnnestClause ) )*
  private def fromTerm: Parser[FromTermAst] =
    ((constructor | identifier) ~ ((KeywordToken("AS") ?) ~> identifier)) ^^ {
      case collection ~ identifier => FromTermAst(collection, identifier)
    }
}
