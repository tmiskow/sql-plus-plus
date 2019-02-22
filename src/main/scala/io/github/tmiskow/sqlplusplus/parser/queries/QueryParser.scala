package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer._
import io.github.tmiskow.sqlplusplus.parser._

trait QueryParser extends BaseParser {
  override def query: Parser[Ast] = (expression | selectStatement) <~ SemicolonToken

  //TODO: ... ~ OrderbyClause? ~ LimitClause?
  private def selectStatement: Parser[Ast] = (withClause?) ~ selectBlock ^^ {
    case withClause ~ selectBlock => SelectStatementAst(withClause, selectBlock)
  }

  private def withClause: Parser[WithClauseAst] =
    (KeywordToken("WITH") ~> rep1sep(withElement, CommaToken)) ^^ WithClauseAst

  private def withElement: Parser[WithElementAst] = (identifier ~ (KeywordToken("AS") ~> expression)) ^^ {
    case variable ~ expression => WithElementAst(variable, expression)
  }
}
