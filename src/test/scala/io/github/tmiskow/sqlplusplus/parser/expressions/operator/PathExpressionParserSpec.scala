package io.github.tmiskow.sqlplusplus.parser.expressions.operator

import io.github.tmiskow.sqlplusplus.lexer.IntNumericLiteralToken
import io.github.tmiskow.sqlplusplus.parser.{FieldAst, _}

class PathExpressionParserSpec extends ParserSpec {
  override def parserMethod: parser.Parser[Ast] = parser.pathExpression

  "Parser" should "parse field path expressions" in {
    val result = parseString("x.y.z")
    result shouldBe Right(
      PathExpressionAst(
        VariableAst("x"),
        Seq[SpecifierAst](
          FieldAst(VariableAst("y")),
          FieldAst(VariableAst("z")))))
  }

  it should "parse index path expressions" in {
    val result = parseString("array[3][2][1]")
    result shouldBe Right(
      PathExpressionAst(
        VariableAst("array"),
        Seq[SpecifierAst](
          IndexAst(LiteralAst(IntNumericLiteralToken(3))),
          IndexAst(LiteralAst(IntNumericLiteralToken(2))),
          IndexAst(LiteralAst(IntNumericLiteralToken(1))))))
  }
}
