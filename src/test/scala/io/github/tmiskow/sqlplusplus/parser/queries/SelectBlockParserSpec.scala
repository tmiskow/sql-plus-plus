package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer.{FloatNumericLiteralToken, IntNumericLiteralToken, KeywordToken, NumericLiteralToken}
import io.github.tmiskow.sqlplusplus.parser
import io.github.tmiskow.sqlplusplus.parser._
import org.junit.Test

class SelectBlockParserSpec extends ParserSpec {
  override def parserMethod: parser.Parser[Ast] = parser.selectBlock

  "Parser" should "parse select block with modifier" in {
    val string = "SELECT ALL VALUE 45 + 36"
    val result = parseString(string)
    result shouldBe Right(
      SelectBlockAst(
        AdditionAst(
          LiteralAst(IntNumericLiteralToken("45")),
          LiteralAst(IntNumericLiteralToken("36"))),
        None,
        Some(KeywordToken("ALL"))))
  }

  it should "parse select block without modifier" in {
    val string = "SELECT VALUE 26.3f"
    val result = parseString(string)
    result shouldBe Right(
      SelectBlockAst(
        LiteralAst(FloatNumericLiteralToken("26.3f")),
        None,
        None))
  }

  it should "parse select block with from clause" in {
    val string = "SELECT VALUE foo FROM [1, 2, 3] AS foo"
    val result = parseString(string)
    result shouldBe Right(
      SelectBlockAst(
        VariableAst("foo"),
        Some(FromClauseAst(List(
          FromTermAst(
            ArrayConstructorAst(List(
              LiteralAst(IntNumericLiteralToken("1")),
              LiteralAst(IntNumericLiteralToken("2")),
              LiteralAst(IntNumericLiteralToken("3")))),
            VariableAst("foo"))))),
        None))
  }
}
