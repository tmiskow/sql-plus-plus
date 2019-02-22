package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer.KeywordToken
import io.github.tmiskow.sqlplusplus.parser.{BaseParser, SelectValueAst}

trait SelectValueParser extends BaseParser {
  override def selectValue: Parser[SelectValueAst] =
    KeywordToken("VALUE") ~> expression ^^ SelectValueAst
}
