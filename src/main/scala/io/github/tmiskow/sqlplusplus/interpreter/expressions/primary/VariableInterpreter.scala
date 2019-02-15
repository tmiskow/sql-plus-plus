package io.github.tmiskow.sqlplusplus.interpreter.expressions.primary

import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment}
import io.github.tmiskow.sqlplusplus.interpreter.value.Value
import io.github.tmiskow.sqlplusplus.parser.VariableAst

trait VariableInterpreter extends BaseInterpreter {
  override def evaluateVariable(variable: VariableAst, environment: Environment): Value =
    environment.get(variable.name)
}
