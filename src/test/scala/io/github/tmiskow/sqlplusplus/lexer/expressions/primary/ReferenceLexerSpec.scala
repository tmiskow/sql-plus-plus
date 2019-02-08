package io.github.tmiskow.sqlplusplus.lexer.expressions.primary

import io.github.tmiskow.sqlplusplus._
import io.github.tmiskow.sqlplusplus.lexer.{ParameterToken, Token, VariableToken}

class ReferenceLexerSpec extends LexerSpec  {
  override def tokenizerMethod: lexer.Parser[Token] = lexer.reference

  "Lexer" should "tokenize identifiers" in {
    val strings = List("_first_variable", "secondVariable")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(VariableToken(string))
    }
  }

  it should "tokenize delimited identifiers" in {
    val strings = List("`weird variable-name`", "`\n escaped characters \"`")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(VariableToken(string))
    }
  }

  it should "tokenize named parameters" in {
    val strings = List("$parameter", "$`delimited parameter`")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(ParameterToken(string))
    }
  }

  it should "tokenize positional parameters" in {
    val strings = List("$234", "?")
    for (string <- strings) {
      val result = tokenizeString(string)
      result shouldBe Right(ParameterToken(string))
    }
  }
}
