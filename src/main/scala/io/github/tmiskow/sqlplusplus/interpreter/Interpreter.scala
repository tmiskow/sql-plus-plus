package io.github.tmiskow.sqlplusplus.interpreter

import io.github.tmiskow.sqlplusplus.interpreter.expressions.operator.ExpressionsInterpreter
import io.github.tmiskow.sqlplusplus.interpreter.expressions.primary.LiteralInterpreter
import io.github.tmiskow.sqlplusplus.interpreter.queries.SelectQueryInterpreter
import io.github.tmiskow.sqlplusplus.lexer.{BaseLexer, Lexer, Token}
import io.github.tmiskow.sqlplusplus.parser.{Ast, BaseParser, Parser, SelectQueryAst}

object Interpreter extends BaseInterpreter
  with SelectQueryInterpreter
  with ExpressionsInterpreter
  with LiteralInterpreter
{
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
    val result = parser.parseTokens(tokens, parser.selectQuery)
    result match {
      case Left(error) => println(error)
      case Right(ast) => handleAst(ast)
    }
  }

  private def handleAst(ast: Ast): Unit = ast match {
    case query: SelectQueryAst =>
      val result = evaluateSelectQuery(query)
      result match {
        case Left(error) => println(error)
        case Right(value) => println(value)
      }
  }
}
