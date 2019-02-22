package io.github.tmiskow.sqlplusplus.interpreter.queries

import io.github.tmiskow.sqlplusplus.interpreter.value._
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Context, Environment, InterpreterException}
import io.github.tmiskow.sqlplusplus.lexer.{KeywordToken, Token}
import io.github.tmiskow.sqlplusplus.parser._

trait SelectBlockInterpreter extends BaseInterpreter {
  override def evaluateSelectBlock(selectBlock: SelectBlockAst, environment: Environment): CollectionValue = {
    val context = (selectBlock.fromClause, selectBlock.whereClause, selectBlock.select) match {
      case (None, _, _: SelectRegularAst) =>
        throw InterpreterException("Regular SELECT clause must contain a FROM clause")
      case (None, Some(_), _) =>
        throw InterpreterException("WHERE clause must apply to FROM clause")
      case (None, None, _) => Context(Seq.empty, Seq[Environment](environment))
      case (Some(fromClause), maybeWhereClause, _) =>
        val unfilteredContext = evaluateFromClause(fromClause, environment)
        evaluateWhereClause(maybeWhereClause, unfilteredContext)
    }
    val values = evaluateSelect(selectBlock.select, context)
    ArrayValue(evaluateModifier(values, selectBlock.modifier))
  }

  private def evaluateSelect(select: SelectAst, context: Context): Seq[Value] =
    select match {
      case selectValue: SelectValueAst =>
        evaluateSelectValue(selectValue, context)
      case selectRegular: SelectRegularAst =>
        evaluateSelectRegular(selectRegular, context)
    }

  def evaluateSelectValue(selectValue: SelectValueAst, context: Context): Seq[Value] =
    evaluateExpressionInContext(selectValue.expression, context)

  private def evaluateModifier(values: Seq[Value], modifier: Option[Token]): Seq[Value] =
    modifier match {
      case None | Some(KeywordToken("ALL")) => values
      case Some(KeywordToken("DISTINCT")) => values.distinct
      case _ => throw InterpreterException(s"Unexpected modifier")
    }

  private def evaluateWhereClause(maybeWhereClause: Option[WhereClauseAst], context: Context): Context =
    maybeWhereClause match {
      case None => context
      case Some(whereClause) =>
        val predicates = evaluateExpressionInContext(whereClause.comparisonExpression, context)
        val environments = for ((predicate, environment) <- predicates zip context.environments if predicate == TrueValue)
          yield environment
        Context(context.variables, environments)
    }

  private def evaluateExpressionInContext(expression: ExpressionAst, context: Context): Seq[Value] =
    for (environment <- context.environments) yield evaluateExpression(expression, environment)
}
