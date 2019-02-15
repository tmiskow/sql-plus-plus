package io.github.tmiskow.sqlplusplus.interpreter.expressions.primary

import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment}
import io.github.tmiskow.sqlplusplus.interpreter.value.{ArrayValue, CollectionValue, Value}
import io.github.tmiskow.sqlplusplus.parser.{ArrayConstructorAst, ConstructorAst}

trait ConstructorInterpreter extends BaseInterpreter {
  override def evaluateConstructor(constructor: ConstructorAst, environment: Environment): CollectionValue =
    constructor match {
      case arrayConstructor: ArrayConstructorAst => evaluateArrayConstructor(arrayConstructor, environment)
    }

  private def evaluateArrayConstructor(arrayConstructor: ArrayConstructorAst, environment: Environment): ArrayValue = {
    val elements = arrayConstructor.elements
    val values = for (expression <- elements) yield evaluateExpression(expression, environment)
    ArrayValue(values.toList)
  }
}
