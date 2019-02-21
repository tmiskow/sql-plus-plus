package io.github.tmiskow.sqlplusplus.interpreter.queries

import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment, InterpreterException}
import io.github.tmiskow.sqlplusplus.interpreter.value.Value
import io.github.tmiskow.sqlplusplus.parser._

trait QueryInterpreter extends BaseInterpreter {
  override def evaluateQuery(query: Ast, environment: Environment): Value = query match {
    case expression: ExpressionAst => evaluateExpression(expression, environment)
    case selectStatement: SelectStatementAst => evaluateSelectStatement(selectStatement, environment)
    case _ => throw InterpreterException("Unexpected query AST type")
  }

  private def evaluateSelectStatement(selectStatement: SelectStatementAst, environment: Environment): Value = {
    val newEnvironment = selectStatement.withClause match {
      case None => environment
      case Some(withClause) => evaluateWithClause(withClause, environment)
    }
    evaluateSelectSetOperation(selectStatement.selectSetOperation, newEnvironment)
  }

  private def evaluateWithClause(withClause: WithClauseAst, environment: Environment): Environment = {
    var newEnvironment = environment
    for (withElement <- withClause.withElements) {
      newEnvironment = evaluateWithElement(withElement, environment)
    }
    newEnvironment
  }

  private def evaluateWithElement(withElement: WithElementAst, environment: Environment): Environment = {
    val variableName = withElement.variable.name
    val value = evaluateExpression(withElement.expression, environment)
    environment.withEntry(variableName, value)
  }

  private def evaluateSelectSetOperation(selectSetOperation: SelectSetOperationAst, environment: Environment): Value = {
    evaluateSelectBlock(selectSetOperation.selectBlock, environment)
  }
}
