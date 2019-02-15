package io.github.tmiskow.sqlplusplus.interpreter.queries

import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment, InterpreterException}
import io.github.tmiskow.sqlplusplus.interpreter.value.Value
import io.github.tmiskow.sqlplusplus.parser._

trait QueryInterpreter extends BaseInterpreter {
  override def evaluateQuery(query: Ast): Value = query match {
    case expression: ExpressionAst => evaluateExpression(expression, Environment.empty)
    case selectStatement: SelectStatementAst => evaluateSelectStatement(selectStatement)
    case _ => throw InterpreterException("Unexpected query AST type")
  }

  private def evaluateSelectStatement(selectStatement: SelectStatementAst): Value = {
    val environment = selectStatement.withClause match {
      case None => Environment.empty
      case Some(withClause) => evaluateWithClause(withClause)
    }
    evaluateSelectSetOperation(selectStatement.selectSetOperation, environment)
  }

  private def evaluateWithClause(withClause: WithClauseAst): Environment = {
    val environment = Environment.empty
    for (withElement <- withClause.withElements) {
      evaluateWithElement(withElement, environment)
    }
    environment
  }

  private def evaluateWithElement(withElement: WithElementAst, environment: Environment): Unit = {
    val variableName = withElement.variable.name
    val value = evaluateExpression(withElement.expression, environment)
    environment.put(variableName, value)
  }

  private def evaluateSelectSetOperation(selectSetOperation: SelectSetOperationAst, environment: Environment): Value = {
    evaluateSelectBlock(selectSetOperation.selectBlock, environment)
  }
}
