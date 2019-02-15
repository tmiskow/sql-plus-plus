package io.github.tmiskow.sqlplusplus.interpreter.queries

import io.github.tmiskow.sqlplusplus.interpreter.value.{ArrayValue, CollectionValue, Value}
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment}
import io.github.tmiskow.sqlplusplus.parser._

trait SelectBlockInterpreter extends BaseInterpreter {
  override def evaluateSelectBlock(selectBlock: SelectBlockAst, environment: Environment): Value =
    selectBlock.fromClause match {
      case None => evaluateSelectClause(selectBlock.selectClause, environment)
      case Some(fromClause) => {
        val fromTerm = evaluateFromClause(fromClause, environment).head
        val collection = evaluateFromTerm(fromTerm, environment).collection
        val selectedValues: Seq[Value] = for (value <- collection) yield {
          val temporaryEnvironment = environment.clone()
          temporaryEnvironment.put(fromTerm.variable.name, value)
          evaluateSelectClause(selectBlock.selectClause, temporaryEnvironment)
        }
        ArrayValue.fromValues(selectedValues)
      }
    }

  override def evaluateSelectClause(selectClause: SelectClauseAst, environment: Environment): Value = {
    evaluateExpression(selectClause.expression, environment)
  }

  private def evaluateFromClause(fromClause: FromClauseAst, environment: Environment): Seq[FromTermAst] = {
    //TODO: implement for multiple terms
    assert(fromClause.terms.size == 1)
    fromClause.terms
  }

  private def evaluateFromTerm(fromTerm: FromTermAst, environment: Environment): CollectionValue = {
    evaluateExpression(fromTerm.expression, environment).toCollectionValue
  }
}
