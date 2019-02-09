package io.github.tmiskow.sqlplusplus.interpreter.queries

import io.github.tmiskow.sqlplusplus.interpreter.value.{ArrayValue, Value}
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment}
import io.github.tmiskow.sqlplusplus.parser._

trait SelectBlockInterpreter extends BaseInterpreter {
  override def evaluateSelectBlock(selectBlock: SelectBlockAst, environment: Environment): Value =
    selectBlock.fromClause match {
      case None => evaluateSelectClause(selectBlock.selectClause, environment)
      case Some(fromClause) => {
        val iterator = evaluateFromClause(fromClause, environment)
        val selectedValues: List[Value] = for ((variable, expression) <- iterator) {
          val temporaryEnvironment = environment.clone()
          temporaryEnvironment.put(variable.name, evaluateExpression(expression, environment))
          evaluateSelectClause(selectBlock.selectClause, temporaryEnvironment)
        }
        ArrayValue.fromValues(selectedValues)
      }
    }

  private def evaluateSelectClause(selectClause: SelectClauseAst, environment: Environment): Value = {
    evaluateExpression(selectClause.expression, environment)
  }

  private def evaluateFromClause(fromClause: FromClauseAst, environment: Environment): Seq[(VariableAst, Value)] = {
    //TODO: implement for multiple terms
    assert(fromClause.terms.size == 1)
    val fromTerm = fromClause.terms.head
    evaluateFromTerm(fromTerm, environment)
  }

  private def evaluateFromTerm(fromTerm: FromTermAst, environment: Environment): Seq[(VariableAst, Value)] = {

  }
}
