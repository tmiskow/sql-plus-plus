package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer.{CommaToken, KeywordToken}
import io.github.tmiskow.sqlplusplus.parser._

trait FromClauseParser extends BaseParser {
  override def fromClause: Parser[FromClauseAst] =
    (KeywordToken("FROM") ~> rep1sep(fromTerm, CommaToken)) ^^ FromClauseAst

  private def fromTerm: Parser[FromTermAst] =
    fromCollection ~ ((KeywordToken("AS") ?) ~> identifier) ~ (unnestClause ?) ^^ {
      case collection ~ identifier ~ unnestClause =>
        FromTermAst(collection, identifier, unnestClause)
    }

  private def fromCollection: Parser[ExpressionAst] = constructor | identifier

  private def unnestClause: Parser[UnnestClauseAst] =
    (KeywordToken("UNNEST") ~> expression) ~ ((KeywordToken("AS") ?) ~> identifier) ^^ {
      case expression ~ identifier => UnnestClauseAst(expression, identifier)
    }
}
