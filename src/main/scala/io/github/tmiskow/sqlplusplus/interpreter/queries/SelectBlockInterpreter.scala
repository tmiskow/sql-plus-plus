package io.github.tmiskow.sqlplusplus.interpreter.queries

import io.github.tmiskow.sqlplusplus.interpreter.value.{ArrayValue, CollectionValue, TrueValue, Value}
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment, InterpreterException}
import io.github.tmiskow.sqlplusplus.lexer.{KeywordToken, Token}
import io.github.tmiskow.sqlplusplus.parser._

trait SelectBlockInterpreter extends BaseInterpreter {
  override def evaluateSelectBlock(selectBlock: SelectBlockAst, environment: Environment): CollectionValue = {
    val values = evaluateFromClause(selectBlock.expression, selectBlock.fromClause, environment)
    val filteredValues = evaluateWhereClause(values, selectBlock.whereClause, selectBlock.fromClause, environment)
    ArrayValue.fromValues(evaluateModifier(filteredValues, selectBlock.modifier))
  }

  private def evaluateModifier(values: Seq[Value], modifier: Option[Token]): Seq[Value] =
    modifier match {
      case None | Some(KeywordToken("ALL")) => values
      case Some(KeywordToken("DISTINCT")) => values.distinct
      case _ => throw InterpreterException(s"Unexpected modifier")
    }

  private def evaluateFromClause
  (expression: ExpressionAst, maybeFromClause: Option[FromClauseAst], environment: Environment): Seq[Value] =
    maybeFromClause match {
      case None => evaluateExpression(expression, environment).toCollectionValue.collection
      case Some(fromClause) =>
        evaluateExpressionInFromClauseContext(expression, fromClause, environment)
    }

  private def evaluateWhereClause
  (values: Seq[Value], maybeWhereClause: Option[WhereClauseAst], maybeFromClause: Option[FromClauseAst], environment: Environment)
  : Seq[Value] =
    maybeWhereClause match {
      case None => values
      case Some(whereClause) => maybeFromClause match {
        case None =>
          val predicate = evaluateExpression(whereClause.comparisonExpression, environment)
          if (predicate == TrueValue) values else Seq[Value]()
        case Some(fromClause) =>
          val predicates = evaluateExpressionInFromClauseContext(whereClause.comparisonExpression, fromClause, environment)
          for ((predicate, value) <- predicates zip values if predicate == TrueValue) yield value
      }
    }

  private def evaluateExpressionInIteratingEnvironment
  (expression: ExpressionAst, variable: VariableAst, values: Seq[Value], environment: Environment): Seq[Value] =
    for (value <- values) yield evaluateExpression(expression, environment.withEntry(variable.name, value))

  private def evaluateExpressionInFromClauseContext
  (expression: ExpressionAst, fromClause: FromClauseAst, environment: Environment): Seq[Value] = {
    //TODO: implement for multiple terms
    assert(fromClause.terms.size == 1)
    val fromTerm = fromClause.terms.head
    val values = evaluateExpression(fromTerm.expression, environment).toCollectionValue.collection
    evaluateExpressionInIteratingEnvironment(expression, fromTerm.variable, values, environment)
  }
}
