package io.github.tmiskow.sqlplusplus.parser

import io.github.tmiskow.sqlplusplus.lexer.Token

trait Ast

trait ExpressionAst extends Ast
case class LiteralAst(token: Token) extends ExpressionAst
case class AdditionAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class SubtractionAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class MultiplicationAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class DivisionAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class IntegerDivisionAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class ModuloAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class ExponentiationAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst

case class VariableAst(token: Token) extends Ast
case class ParameterAst(token: Token) extends Ast

case class SelectQueryAst(modifier: Option[Token], expression: ExpressionAst) extends Ast