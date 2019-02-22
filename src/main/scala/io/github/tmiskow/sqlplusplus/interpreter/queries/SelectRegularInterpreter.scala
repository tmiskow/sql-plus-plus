package io.github.tmiskow.sqlplusplus.interpreter.queries

import io.github.tmiskow.sqlplusplus.interpreter.value.{ObjectValue, Value}
import io.github.tmiskow.sqlplusplus.interpreter.{BaseInterpreter, Context, InterpreterException}
import io.github.tmiskow.sqlplusplus.parser.{AsteriskProjectionAst, ExpressionProjectionAst, PathProjectionAst, SelectRegularAst}

trait SelectRegularInterpreter extends BaseInterpreter {
  override def evaluateSelectRegular(selectRegular: SelectRegularAst, context: Context): Seq[ObjectValue] =
    selectRegular.projections match {
      case AsteriskProjectionAst :: Nil => evaluateAsteriskProjection(context)
      case projections => (for (projection <- projections) yield projection match {
        case AsteriskProjectionAst =>
          throw InterpreterException("Asterisk projection must be an only projection")
        case pathProjection: PathProjectionAst =>
          evaluatePathProjection(pathProjection, context)
        case expressionProjection: ExpressionProjectionAst =>
          evaluateExpressionProjection(expressionProjection, context)
      }).reduce { (values: Seq[ObjectValue], newValues: Seq[ObjectValue]) =>
        for ((value, newValue) <- values zip newValues)
          yield ObjectValue(value.map ++ newValue.map)
      }
    }

  private def evaluateAsteriskProjection(context: Context): Seq[ObjectValue] =
    for (environment <- context.environments) yield ObjectValue((
      for (variable <- context.variables)
        yield (variable, environment.get(variable))
      ).toMap)

  private def evaluatePathProjection(pathProjection: PathProjectionAst, context: Context): Seq[ObjectValue] =
    for (environment <- context.environments)
      yield environment.get(pathProjection.variable.name) match {
        case objectValue: ObjectValue => objectValue
        case _ => throw InterpreterException("Path projection result must be an object value")
      }

  def evaluateExpressionProjection(expressionProjection: ExpressionProjectionAst, context: Context): Seq[ObjectValue] =
    for (environment <- context.environments) yield {
      val value = evaluateExpression(expressionProjection.expression, environment)
      ObjectValue(Map(expressionProjection.variable.name -> value))
    }
}
