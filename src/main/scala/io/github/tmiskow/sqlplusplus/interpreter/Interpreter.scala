package io.github.tmiskow.sqlplusplus.interpreter

import java.io.FileReader

import io.github.tmiskow.sqlplusplus.Error
import io.github.tmiskow.sqlplusplus.interpreter.expressions.ExpressionInterpreter
import io.github.tmiskow.sqlplusplus.interpreter.expressions.operator.{ArithmeticExpressionsInterpreter, ComparisonExpressionInterpreter, PathExpressionInterpreter}
import io.github.tmiskow.sqlplusplus.interpreter.expressions.primary.{ConstructorInterpreter, LiteralInterpreter, VariableInterpreter}
import io.github.tmiskow.sqlplusplus.interpreter.queries.{FromClauseInterpreter, QueryInterpreter, SelectBlockInterpreter, SelectRegularInterpreter}
import io.github.tmiskow.sqlplusplus.interpreter.value.Value
import io.github.tmiskow.sqlplusplus.lexer.{BaseLexer, Lexer, Token}
import io.github.tmiskow.sqlplusplus.parser.{ArrayConstructorAst, BaseParser, Parser}

object Interpreter extends BaseInterpreter
  with QueryInterpreter
  with SelectBlockInterpreter
  with SelectRegularInterpreter
  with FromClauseInterpreter
  with ExpressionInterpreter
  with ArithmeticExpressionsInterpreter
  with ComparisonExpressionInterpreter
  with PathExpressionInterpreter
  with ConstructorInterpreter
  with VariableInterpreter
  with LiteralInterpreter {
  val lexer: BaseLexer = Lexer
  val parser: BaseParser = Parser

  def evaluateArgs(fileName: String, variableName: String): Environment = {
    val reader = new FileReader(fileName)
    val tokens = lexer.parse(lexer.all, reader) match {
      case lexer.NoSuccess(message, _) => throw InterpreterException(message)
      case lexer.Success(result: Seq[Token], _) => result
    }
    reader.close()
    val ast = parser.parseTokens(tokens, parser.constructor) match {
      case Left(error) => throw InterpreterException(error.message)
      case Right(result) => result
    }
    val arrayValue = ast match {
      case arrayAst: ArrayConstructorAst => evaluateConstructor(arrayAst, Environment.empty)
      case _ => throw InterpreterException("Dataset must be an array")
    }
    Environment.empty.withEntry(variableName, arrayValue)
  }

  def handleArgs(args: List[String]): Environment = args match {
    case Nil => Environment.empty
    case _ if args.size < 2 => throw InterpreterException("Args: <file name> <variable name>")
    case fileName :: variableName :: _ => evaluateArgs(fileName, variableName)
  }

  def main(args: Array[String]): Unit = {
    val environment = handleArgs(args.toList)
    while (true) {
      printPrompt()
      val query = scala.io.StdIn.readLine()
      handleQuery(query, environment) match {
        case Left(error) => println(error)
        case Right(value) => println(value)
      }
    }
  }

  private def printPrompt(): Unit = print("> ")

  private def handleQuery(string: String, environment: Environment): Either[Error, Value] =
    tokenizeQuery(string) match {
      case Left(error) => Left(error)
      case Right(tokens) => parseQuery(tokens) match {
        case Left(error) => Left(error)
        case Right(ast) => Right(evaluateQuery(ast, environment).toArrayValue)
      }
    }

  private def tokenizeQuery(string: String): lexer.Result[Seq[Token]] =
    lexer.tokenizeString(string, lexer.all)

  private def parseQuery(tokens: Seq[Token]): parser.Result =
    parser.parseTokens(tokens, parser.query)
}
