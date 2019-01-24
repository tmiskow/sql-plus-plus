package io.github.tmiskow.sqlplusplusparser

trait Ast

case class LiteralAst(token: Token) extends Ast

case class AdditionAst(left: Ast, right: Ast) extends Ast
case class SubtractionAst(left: Ast, right: Ast) extends Ast
case class MultiplicationAst(left: Ast, right: Ast) extends Ast
case class DivisionAst(left: Ast, right: Ast) extends Ast
case class IntegerDivisionAst(left: Ast, right: Ast) extends Ast
case class ModuloAst(left: Ast, right: Ast) extends Ast
case class ExponentiationAst(left: Ast, right: Ast) extends Ast

case class VariableAst(token: Token) extends Ast
case class ParameterAst(token: Token) extends Ast

case class SelectQueryAst(modifier: Option[Token], expression: Ast) extends Ast