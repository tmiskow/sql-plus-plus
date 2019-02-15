package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer._
import io.github.tmiskow.sqlplusplus.parser._

trait QueryParser extends BaseParser {
  override def query: Parser[Ast] = (expression | selectStatement) <~ SemicolonToken

  //TODO: ... ~ OrderbyClause? ~ LimitClause?
  def selectStatement: Parser[Ast] = (withClause?) ~ selectSetOperation ^^ {
    case withClause ~ selectSetOperation => SelectStatementAst(withClause, selectSetOperation)
  }

  //TODO: ... ~ (KeywordToken("UNION") ~> KeywordToken("ALL") ~> (selectBlock | subquery))*
  def selectSetOperation: Parser[SelectSetOperationAst] = selectBlock ^^ SelectSetOperationAst

  def subquery: Parser[Ast] = LeftParenthesisToken ~> selectStatement <~ RightParenthesisToken

  def withClause: Parser[WithClauseAst] = (KeywordToken("WITH") ~> rep1sep(withElement, CommaToken)) ^^ WithClauseAst
  def withElement: Parser[WithElementAst] = (variable ~ (KeywordToken("AS") ~> expression)) ^^ {
    case variable ~ expression => WithElementAst(variable, expression)
  }

  def letClause: Parser[Ast] = KeywordToken("LET") ~> rep1sep(letElement, CommaToken) ^^ LetClauseAst
  def letElement: Parser[LetElementAst] = (variable ~ (OperatorToken("=") ~> expression)) ^^ {
    case variable ~ expression => LetElementAst(variable, expression)
  }

  override def variable: Parser[VariableAst] = accept("variable", {
    case VariableToken(name) => VariableAst(name)
  })
}
