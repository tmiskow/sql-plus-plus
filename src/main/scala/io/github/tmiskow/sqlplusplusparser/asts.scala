package io.github.tmiskow.sqlplusplusparser

trait Ast

trait ExpressionAst extends Ast
case class AdditionAst(e1: ExpressionAst, e2: ExpressionAst) extends ExpressionAst
case class SubtractionAst(e1: ExpressionAst, e2: ExpressionAst) extends ExpressionAst
case class MultiplicationAst(e1: ExpressionAst, e2: ExpressionAst) extends ExpressionAst
case class DivisionAst(e1: ExpressionAst, e2: ExpressionAst) extends ExpressionAst
case class IntegerDivisionAst(e1: ExpressionAst, e2: ExpressionAst) extends ExpressionAst
case class ModuloAst(e1: ExpressionAst, e2: ExpressionAst) extends ExpressionAst
case class ExponentiationAst(e1: ExpressionAst, e2: ExpressionAst) extends ExpressionAst
case class NumberAst(e: NumericLiteralToken) extends ExpressionAst

trait QueryAst extends Ast
case class SelectQueryAst(modifier: Option[KeywordToken], expression: ExpressionAst) extends QueryAst