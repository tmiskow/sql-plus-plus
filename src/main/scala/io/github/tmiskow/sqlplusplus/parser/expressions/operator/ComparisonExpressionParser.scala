package io.github.tmiskow.sqlplusplus.parser.expressions.operator

import io.github.tmiskow.sqlplusplus.lexer.OperatorToken
import io.github.tmiskow.sqlplusplus.parser._

trait ComparisonExpressionParser extends BaseParser {
  override def comparisonExpression: Parser[ExpressionAst] = equality | inequality | lessThan | lessOrEqualThan

  private def equality: Parser[EqualityAst] =
    (arithmeticExpression ~ OperatorToken("=") ~ arithmeticExpression) ^^ {
      case left ~ _ ~ right => EqualityAst(left, right)
    }

  private def inequality: Parser[InequalityAst] =
    (arithmeticExpression ~ (OperatorToken("!=") | OperatorToken("<>")) ~ arithmeticExpression) ^^ {
      case left ~ _ ~ right => InequalityAst(left, right)
    }

  private def lessThan: Parser[LessThanAst] =
    (arithmeticExpression ~ OperatorToken("<") ~ arithmeticExpression) ^^ {
      case left ~ _ ~ right => LessThanAst(left, right)
    } | (arithmeticExpression ~ OperatorToken(">") ~ arithmeticExpression) ^^ {
      case left ~ _ ~ right => LessThanAst(right, left)
    }

  private def lessOrEqualThan: Parser[LessOrEqualThanAst] =
    (arithmeticExpression ~ OperatorToken("<=") ~ arithmeticExpression) ^^ {
      case left ~ _ ~ right => LessOrEqualThanAst(left, right)
    } | (arithmeticExpression ~ OperatorToken(">=") ~ arithmeticExpression) ^^ {
      case left ~ _ ~ right => LessOrEqualThanAst(right, left)
    }
}
