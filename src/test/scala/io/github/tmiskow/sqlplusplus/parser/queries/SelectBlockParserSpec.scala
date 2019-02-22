package io.github.tmiskow.sqlplusplus.parser.queries

import io.github.tmiskow.sqlplusplus.lexer.{FloatNumericLiteralToken, IntNumericLiteralToken, KeywordToken}
import io.github.tmiskow.sqlplusplus.parser._

class SelectBlockParserSpec extends ParserSpec {
  override def parserMethod: parser.Parser[Ast] = parser.selectBlock

  "Parser" should "parse SELECT value block" in {
    val string = "SELECT VALUE 45 + 36"
    val result = parseString(string)
    result shouldBe Right(
      SelectBlockAst(
        SelectValueAst(
          AdditionAst(
            LiteralAst(IntNumericLiteralToken(45)),
            LiteralAst(IntNumericLiteralToken(36)))),
        None,
        None,
        None))
  }

  it should "parse regular SELECT block with asterisk projection" in {
    val string = "SELECT * FROM Users user"
    val result = parseString(string)
    result shouldBe Right(
      SelectBlockAst(
        SelectRegularAst(Seq(
          AsteriskProjectionAst)),
        None,
        Some(FromClauseAst(Seq(
          FromTermAst(
            VariableAst("Users"),
            VariableAst("user"),
            None)))),
        None))
  }

  it should "parse regular SELECT block with multiple projections" in {
    val string = "SELECT user.*, user.id * 2 AS doubledId FROM Users user"
    val result = parseString(string)
    result shouldBe Right(
      SelectBlockAst(
        SelectRegularAst(Seq(
          PathProjectionAst(VariableAst("user")),
          ExpressionProjectionAst(
            MultiplicationAst(
              PathExpressionAst(
                VariableAst("user"),
                Seq(FieldAst(VariableAst("id")))),
              LiteralAst(IntNumericLiteralToken(2))),
            VariableAst("doubledId")))),
        None,
        Some(FromClauseAst(Seq(
          FromTermAst(
            VariableAst("Users"),
            VariableAst("user"),
            None)))),
        None))
  }

  it should "parse SELECT block with WHERE clause" in {
    val string = "SELECT VALUE foo FROM [1, 2, 3] AS foo WHERE foo > 2"
    val result = parseString(string)
    result shouldBe Right(
      SelectBlockAst(
        SelectValueAst(VariableAst("foo")),
        None,
        Some(FromClauseAst(List(
          FromTermAst(
            ArrayConstructorAst(List(
              LiteralAst(IntNumericLiteralToken(1)),
              LiteralAst(IntNumericLiteralToken(2)),
              LiteralAst(IntNumericLiteralToken(3)))),
            VariableAst("foo"),
            None)))),
        Some(WhereClauseAst(
          LessThanAst(
            LiteralAst(IntNumericLiteralToken(2)),
            VariableAst("foo"))))))
  }

  it should "parse SELECT block with modifier" in {
    for (modifier <- List("ALL", "DISTINCT")) {
      val string = s"SELECT $modifier VALUE foo FROM [1, 2, 3] AS foo"
      val result = parseString(string)
      result shouldBe Right(
        SelectBlockAst(
          SelectValueAst(VariableAst("foo")),
          Some(KeywordToken(modifier)),
          Some(FromClauseAst(List(
            FromTermAst(
              ArrayConstructorAst(List(
                LiteralAst(IntNumericLiteralToken(1)),
                LiteralAst(IntNumericLiteralToken(2)),
                LiteralAst(IntNumericLiteralToken(3)))),
              VariableAst("foo"),
              None)))),
          None))
    }
  }
}
