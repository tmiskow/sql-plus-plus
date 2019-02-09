package io.github.tmiskow.sqlplusplus.parser.expressions

import io.github.tmiskow.sqlplusplus.lexer._
import io.github.tmiskow.sqlplusplus.parser.{ArrayConstructorAst, Ast, LiteralAst, ParserSpec}

class ConstructorParserSpec extends ParserSpec {
  override def parserMethod: parser.Parser[Ast] = parser.constructor

  "Parser" should "parse array constructors" in {
    val result = parseString("[1, 3.5f, 'abba', true, null, false]")
    result shouldBe Right(
      ArrayConstructorAst(List(
        LiteralAst(IntNumericLiteralToken("1")),
        LiteralAst(FloatNumericLiteralToken("3.5f")),
        LiteralAst(StringLiteralToken("'abba'")),
        LiteralAst(TrueLiteralToken),
        LiteralAst(NullLiteralToken),
        LiteralAst(FalseLiteralToken))))
  }
}
