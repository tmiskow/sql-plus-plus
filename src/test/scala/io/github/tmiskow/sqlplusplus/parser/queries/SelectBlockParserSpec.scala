package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer.{FloatNumericLiteralToken, IntNumericLiteralToken, KeywordToken, NumericLiteralToken}
import io.github.tmiskow.sqlplusplus.parser._

class SelectBlockParserSpec extends ParserSpec {
  override def parserMethod: parser.Parser[Ast] = parser.selectBlock

  "Parser" should "parse select block" in {
    val string = "SELECT ALL VALUE 45 + 36"
    val result = parseString(string)
    result shouldBe Right(
      SelectBlockAst(
        Some(KeywordToken("ALL")),
        AdditionAst(
          LiteralAst(IntNumericLiteralToken("45")),
          LiteralAst(IntNumericLiteralToken("36")))))
  }

  it should "parse select query without block" in {
    val string = "SELECT VALUE 26.3f"
    val result = parseString(string)
    result shouldBe Right(
      SelectBlockAst(
        None,
        LiteralAst(FloatNumericLiteralToken("26.3f"))))
  }
}
