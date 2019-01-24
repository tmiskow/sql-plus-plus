package io.github.tmiskow.sqlplusplusparser

import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
abstract class UnitSpec extends FlatSpec with Matchers {
  val lexer: BaseLexer = Lexer
  val parser: BaseParser = Parser
  protected def parseString(string: String): Ast = {
    val tokens = lexer(string).right.get
    val result = parser(tokens)
    result.right.get
  }
}
