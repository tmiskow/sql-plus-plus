package io.github.tmiskow.sqlplusplus.parser

import io.github.tmiskow.sqlplusplus.lexer.Token

sealed trait Ast

sealed trait ExpressionAst extends Ast

case class LiteralAst(token: Token) extends ExpressionAst
case class VariableAst(name: String) extends ExpressionAst
case class PathExpressionAst(expression: ExpressionAst, specifiers: Seq[SpecifierAst]) extends ExpressionAst

sealed trait SpecifierAst extends Ast
case class FieldAst(identifier: VariableAst) extends SpecifierAst
case class IndexAst(expression: ExpressionAst) extends SpecifierAst

sealed trait ArithmeticExpressionAst extends ExpressionAst
case class AdditionAst(left: ExpressionAst, right: ExpressionAst) extends ArithmeticExpressionAst
case class SubtractionAst(left: ExpressionAst, right: ExpressionAst) extends ArithmeticExpressionAst
case class MultiplicationAst(left: ExpressionAst, right: ExpressionAst) extends ArithmeticExpressionAst
case class DivisionAst(left: ExpressionAst, right: ExpressionAst) extends ArithmeticExpressionAst
case class IntegerDivisionAst(left: ExpressionAst, right: ExpressionAst) extends ArithmeticExpressionAst
case class ModuloAst(left: ExpressionAst, right: ExpressionAst) extends ArithmeticExpressionAst
case class ExponentiationAst(left: ExpressionAst, right: ExpressionAst) extends ArithmeticExpressionAst

sealed trait ComparisonExpressionAst extends ExpressionAst
case class EqualityAst(left: ExpressionAst, right: ExpressionAst) extends ComparisonExpressionAst
case class InequalityAst(left: ExpressionAst, right: ExpressionAst) extends ComparisonExpressionAst
case class LessThanAst(left: ExpressionAst, right: ExpressionAst) extends ComparisonExpressionAst
case class LessOrEqualThanAst(left: ExpressionAst, right: ExpressionAst) extends ComparisonExpressionAst

sealed trait ConstructorAst extends ExpressionAst
case class ArrayConstructorAst(elements: Seq[ExpressionAst]) extends ConstructorAst
case class ObjectConstructorAst(elements: Map[LiteralAst, ExpressionAst]) extends ConstructorAst

sealed trait ProjectionAst extends Ast
case class ExpressionProjectionAst(expression: ExpressionAst, variable: VariableAst) extends ProjectionAst
case class PathProjectionAst(variable: VariableAst) extends ProjectionAst
case object AsteriskProjectionAst extends ProjectionAst

case class WithClauseAst(withElements: List[WithElementAst]) extends Ast
case class WithElementAst(variable: VariableAst, expression: ExpressionAst) extends Ast

case class SelectSetOperationAst(selectBlock: SelectBlockAst) extends Ast
case class UnnestClauseAst(expression: ExpressionAst, variable: VariableAst) extends Ast
case class WhereClauseAst(comparisonExpression: ComparisonExpressionAst) extends Ast

case class FromClauseAst(terms: Seq[FromTermAst]) extends Ast
case class FromTermAst
(expression: ExpressionAst,
 variable: VariableAst,
 unnestClause: Option[UnnestClauseAst]) extends Ast

case class SelectStatementAst
(withClause: Option[WithClauseAst],
 selectBlock: SelectBlockAst) extends Ast

case class SelectBlockAst
(select: SelectAst,
 modifier: Option[Token],
 fromClause: Option[FromClauseAst],
 whereClause: Option[WhereClauseAst]) extends Ast

sealed trait SelectAst extends Ast
case class SelectRegularAst(projections: Seq[ProjectionAst]) extends SelectAst
case class SelectValueAst(expression: ExpressionAst) extends SelectAst
