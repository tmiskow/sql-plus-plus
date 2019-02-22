package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer._
import io.github.tmiskow.sqlplusplus.parser._

trait SelectRegularParser extends BaseParser {
  override def selectRegular: Parser[SelectRegularAst] =
    rep1sep(projection, CommaToken) ^^ SelectRegularAst

  private def projection: Parser[ProjectionAst] =
    asteriskProjection | pathProjection | expressionProjection

  private def expressionProjection: Parser[ExpressionProjectionAst] =
    (arithmeticExpression ~ ((KeywordToken("AS")?) ~> identifier)) ^^ {
      case expression ~ variable => ExpressionProjectionAst(expression, variable)
    }

  private def pathProjection: Parser[PathProjectionAst] =
    (identifier <~ DotToken <~ OperatorToken("*")) ^^ PathProjectionAst

  private def asteriskProjection: Parser[AsteriskProjectionAst.type] =
    OperatorToken("*") ^^^ AsteriskProjectionAst
}
