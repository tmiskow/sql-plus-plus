package io.github.tmiskow.sqlplusplus.interpreter.expressions.operator

import io.github.tmiskow.sqlplusplus.interpreter.value.{ArrayValue, IntValue, ObjectValue, Value}
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Environment, InterpreterException}
import io.github.tmiskow.sqlplusplus.parser.{FieldAst, IndexAst, PathExpressionAst, SpecifierAst}

trait PathExpressionInterpreter extends BaseInterpreter {
  override def evaluatePathExpression(pathExpression: PathExpressionAst, environment: Environment): Value = {
    val value = evaluateExpression(pathExpression.expression, environment)
    evaluateValueWithPathSpecifiers(value, pathExpression.specifiers, environment)
  }

  private def evaluateValueWithPathSpecifiers(value: Value, specifiers: Seq[SpecifierAst], environment: Environment): Value =
    specifiers match {
      case Nil => value
      case _ => value match {
        case objectValue: ObjectValue =>
          evaluateObjectValueWithPathSpecifiers(objectValue, specifiers, environment)
        case arrayValue: ArrayValue =>
          evaluateArrayValueWithPathSpecifiers(arrayValue, specifiers, environment)
        case _ =>
          throw InterpreterException("Path specifiers can apply to objects or arrays only")
      }
    }

  private def evaluateObjectValueWithPathSpecifiers(objectValue: ObjectValue, specifiers: Seq[SpecifierAst], environment: Environment): Value =
    specifiers.head match {
      case IndexAst(_) =>
        throw InterpreterException("Index specifier cannot apply to an object")
      case FieldAst(variable) => objectValue.map.get(variable.name) match {
        case None =>
          throw InterpreterException(s"$objectValue does not contain field named ${variable.name}")
        case Some(value) =>
          evaluateValueWithPathSpecifiers(value, specifiers.drop(1), environment)
      }
    }

  private def evaluateArrayValueWithPathSpecifiers(arrayValue: ArrayValue, specifiers: Seq[SpecifierAst], environment: Environment): Value =
    specifiers.head match {
      case FieldAst(_) =>
        throw InterpreterException("Field specifier cannot apply to an array")
      case IndexAst(expression) => evaluateExpression(expression, environment) match {
        case IntValue(index) =>
          val value = arrayValue.seq(index)
          evaluateValueWithPathSpecifiers(value, specifiers.drop(1), environment)
        case _ => throw InterpreterException("Index specifier must be an integer")
      }
    }
}
