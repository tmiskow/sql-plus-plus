package io.github.tmiskow.sqlplusplusparser

sealed abstract class ExpressionAST
case class AdditionAST(e1: ExpressionAST, e2: ExpressionAST) extends ExpressionAST
case class SubtractionAST(e1: ExpressionAST, e2: ExpressionAST) extends ExpressionAST
case class MultiplicationAST(e1: ExpressionAST, e2: ExpressionAST) extends ExpressionAST
case class DivisionAST(e1: ExpressionAST, e2: ExpressionAST) extends ExpressionAST
case class IntegerDivisionAST(e1: ExpressionAST, e2: ExpressionAST) extends ExpressionAST
case class ModuloAST(e1: ExpressionAST, e2: ExpressionAST) extends ExpressionAST
case class ExponentiationAST(e1: ExpressionAST, e2: ExpressionAST) extends ExpressionAST
case class NumberAST(e: NumericLiteralToken) extends ExpressionAST
