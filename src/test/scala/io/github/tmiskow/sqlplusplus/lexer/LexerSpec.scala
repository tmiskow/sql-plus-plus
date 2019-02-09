package io.github.tmiskow.sqlplusplus.lexer

import io.github.tmiskow.sqlplusplus.UnitSpec

abstract class LexerSpec extends UnitSpec {
  override type LexerOutput = Token
}
