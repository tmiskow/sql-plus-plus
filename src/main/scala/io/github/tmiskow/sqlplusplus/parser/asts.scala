package io.github.tmiskow.sqlplusplus.parser

import io.github.tmiskow.sqlplusplus.lexer.Token

sealed trait Ast

sealed trait ExpressionAst extends Ast

case class LiteralAst(token: Token) extends ExpressionAst
case class VariableAst(name: String) extends ExpressionAst

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

case class WithClauseAst(withElements: List[WithElementAst]) extends Ast
case class WithElementAst(variable: VariableAst, expression: ExpressionAst) extends Ast

case class LetClauseAst(letElements: List[LetElementAst]) extends Ast
case class LetElementAst(variable: VariableAst, expression: ExpressionAst) extends Ast

case class SelectSetOperationAst(selectBlock: SelectBlockAst) extends Ast
case class FromClauseAst(terms: Seq[FromTermAst]) extends Ast
case class FromTermAst(expression: ConstructorAst, variable: VariableAst) extends Ast
case class WhereClauseAst(comparisonExpression: ComparisonExpressionAst) extends Ast
case class SelectStatementAst(withClause: Option[WithClauseAst], selectSetOperation: SelectSetOperationAst) extends Ast
case class SelectBlockAst(expression: ExpressionAst, modifier: Option[Token], fromClause: Option[FromClauseAst], whereClause: Option[WhereClauseAst]) extends Ast
