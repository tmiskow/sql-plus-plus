package io.github.tmiskow.sqlplusplus.interpreter.queries

import io.github.tmiskow.sqlplusplus.interpreter.value.{ArrayValue, CollectionValue, Value}
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment, InterpreterException}
import io.github.tmiskow.sqlplusplus.lexer.KeywordToken
import io.github.tmiskow.sqlplusplus.parser._

trait SelectBlockInterpreter extends BaseInterpreter {
  override def evaluateSelectBlock(selectBlock: SelectBlockAst, environment: Environment): Value =
    selectBlock.fromClause match {
      case None => evaluateExpression(selectBlock.expression, environment)
      case Some(fromClause) =>
        val fromTerm = evaluateFromClause(fromClause, environment).head
        val values = evaluateExpressionInFromTermContext(selectBlock.expression, fromTerm, environment)
        selectBlock.modifier match {
          case None | Some(KeywordToken("ALL")) => values
          case Some(KeywordToken("DISTINCT")) => values.distinct
          case _ => throw InterpreterException(s"Unexpected modifier")
        }
    }

  private def evaluateFromClause(fromClause: FromClauseAst, environment: Environment): Seq[FromTermAst] = {
    //TODO: implement for multiple terms
    assert(fromClause.terms.size == 1)
    fromClause.terms
  }

  private def evaluateExpressionInFromTermContext(expression: ExpressionAst, fromTerm: FromTermAst, environment: Environment): CollectionValue = {
    val collection =
      evaluateExpression(fromTerm.expression, environment).toCollectionValue.collection
    val values = for (value <- collection) yield {
      val temporaryEnvironment = environment.clone()
      temporaryEnvironment.put(fromTerm.variable.name, value)
      evaluateExpression(expression, temporaryEnvironment)
    }
    ArrayValue.fromValues(values)
  }
}
