package io.github.tmiskow.sqlplusplus.interpreter.expressions.primary

import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment}
import io.github.tmiskow.sqlplusplus.interpreter.value.{ArrayValue, CollectionValue, ObjectValue, Value}
import io.github.tmiskow.sqlplusplus.lexer.StringLiteralToken
import io.github.tmiskow.sqlplusplus.parser.{ArrayConstructorAst, ConstructorAst, LiteralAst, ObjectConstructorAst}

trait ConstructorInterpreter extends BaseInterpreter {
  override def evaluateConstructor(constructor: ConstructorAst, environment: Environment): CollectionValue =
    constructor match {
      case arrayConstructor: ArrayConstructorAst => evaluateArrayConstructor(arrayConstructor, environment)
      case objectConstructor: ObjectConstructorAst => evaluateObjectConstructor(objectConstructor, environment)
    }

  private def evaluateArrayConstructor(arrayConstructor: ArrayConstructorAst, environment: Environment): ArrayValue = {
    val elements = arrayConstructor.elements
    val values = for (expression <- elements) yield evaluateExpression(expression, environment)
    new ArrayValue(values)
  }

  private def evaluateObjectConstructor(objectConstructor: ObjectConstructorAst, environment: Environment): CollectionValue = {
    val expressionMap = objectConstructor.elements
    val valuesMap = expressionMap.map { case (stringLiteral, expression) =>
      assert(stringLiteral.token.isInstanceOf[StringLiteralToken])
      val key = stringLiteral.token.asInstanceOf[StringLiteralToken].string
      val value = evaluateExpression(expression, environment)
      key -> value
    }
    ObjectValue(valuesMap)
  }
}
