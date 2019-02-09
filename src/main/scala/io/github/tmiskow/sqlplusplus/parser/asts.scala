package io.github.tmiskow.sqlplusplus.parser

import io.github.tmiskow.sqlplusplus.lexer.{Token, VariableToken}

sealed trait Ast

sealed trait ExpressionAst extends Ast
case class LiteralAst(token: Token) extends ExpressionAst
case class AdditionAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class SubtractionAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class MultiplicationAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class DivisionAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class IntegerDivisionAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class ModuloAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class ExponentiationAst(left: ExpressionAst, right: ExpressionAst) extends ExpressionAst
case class VariableAst(name: String) extends ExpressionAst
case class ParameterAst(token: Token) extends ExpressionAst

sealed trait ConstructorAst extends ExpressionAst
case class ArrayConstructorAst(elements: Seq[ExpressionAst]) extends ConstructorAst

case class WithClauseAst(withElements: List[WithElementAst]) extends Ast
case class WithElementAst(variable: VariableAst, expression: ExpressionAst) extends Ast

case class LetClauseAst(letElements: List[LetElementAst]) extends Ast
case class LetElementAst(variable: VariableAst, expression: ExpressionAst) extends Ast

case class SelectStatementAst(withClause: Option[WithClauseAst], selectSetOperation: SelectSetOperationAst) extends Ast
case class SelectSetOperationAst(selectBlock: SelectClauseAst) extends Ast
case class SelectBlockAst(selectClause: SelectClauseAst, fromClause: Option[FromClauseAst]) extends Ast
case class SelectClauseAst(modifier: Option[Token], expression: ExpressionAst) extends Ast
case class FromClauseAst(terms: Seq[FromTermAst]) extends Ast
case class FromTermAst(expression: ExpressionAst, variable: VariableAst) extends Ast