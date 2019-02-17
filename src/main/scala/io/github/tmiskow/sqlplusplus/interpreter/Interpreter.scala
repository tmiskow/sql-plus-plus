package io.github.tmiskow.sqlplusplus.interpreter

import io.github.tmiskow.sqlplusplus.interpreter.expressions.ExpressionInterpreter
import io.github.tmiskow.sqlplusplus.interpreter.expressions.operator.{ArithmeticExpressionsInterpreter, ComparisonExpressionInterpreter, PathExpressionInterpreter}
import io.github.tmiskow.sqlplusplus.interpreter.expressions.primary.{ConstructorInterpreter, LiteralInterpreter, VariableInterpreter}
import io.github.tmiskow.sqlplusplus.interpreter.queries.{QueryInterpreter, SelectBlockInterpreter}
import io.github.tmiskow.sqlplusplus.lexer.{BaseLexer, Lexer, Token}
import io.github.tmiskow.sqlplusplus.parser.{Ast, BaseParser, Parser}

object Interpreter extends BaseInterpreter
  with QueryInterpreter
  with SelectBlockInterpreter
  with ExpressionInterpreter
  with ArithmeticExpressionsInterpreter
  with ComparisonExpressionInterpreter
  with PathExpressionInterpreter
  with ConstructorInterpreter
  with VariableInterpreter
  with LiteralInterpreter {
  val lexer: BaseLexer = Lexer
  val parser: BaseParser = Parser

  def main(args: Array[String]): Unit = {
    while (true) {
      val input = scala.io.StdIn.readLine()
      handleInput(input)
    }
  }

  private def handleInput(string: String): Unit = {
    val result = lexer.tokenizeString(string, lexer.all)
    result match {
      case Left(error) => println(error)
      case Right(tokens) => handleTokens(tokens)
    }
  }

  private def handleTokens(tokens: Seq[Token]): Unit = {
    val result = parser.parseTokens(tokens, parser.query)
    result match {
      case Left(error) => println(error)
      case Right(ast) => handleAst(ast)
    }
  }

  private def handleAst(ast: Ast): Unit = {
    val result = evaluateQuery(ast)
    println(result.toArrayValue)
  }
}
