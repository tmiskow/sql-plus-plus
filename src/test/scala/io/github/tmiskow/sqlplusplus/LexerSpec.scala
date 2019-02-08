package io.github.tmiskow.sqlplusplus

import io.github.tmiskow.sqlplusplus.lexer.Token

abstract class LexerSpec extends UnitSpec {
  override type LexerOutput = Token
}
