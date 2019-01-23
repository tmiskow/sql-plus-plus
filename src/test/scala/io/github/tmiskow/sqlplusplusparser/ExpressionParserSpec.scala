package io.github.tmiskow.sqlplusplusparser

class ExpressionParserSpec extends UnitSpec {
  it should "parse numeric literals tokens" in {
    val strings = List("17", "3.14", "-16.5f")
    for (string <- strings) {
      val result = parseString(string)
      result shouldBe NumberAST(NumericLiteralToken(string))
    }
  }

  it should "parse addition and subtraction tokens" in {
    val result = parseString("17 + 3.14 - 16.5f")
    result shouldBe
      SubtractionAST(
        AdditionAST(
          NumberAST(NumericLiteralToken("17")),
          NumberAST(NumericLiteralToken("3.14"))),
        NumberAST(NumericLiteralToken("16.5f")))
  }

  it should "parse multiplication and division tokens" in {
    val result = parseString("17 * 3.14 + 2137 / 16.5f")
    result shouldBe
      AdditionAST(
        MultiplicationAST(
          NumberAST(NumericLiteralToken("17")),
          NumberAST(NumericLiteralToken("3.14"))),
        DivisionAST(
          NumberAST(NumericLiteralToken("2137")),
          NumberAST(NumericLiteralToken("16.5f"))))
  }

  it should "parse integer division token" in {
    val result = parseString("17 DIV 3")
    result shouldBe
      IntegerDivisionAST(
        NumberAST(NumericLiteralToken("17")),
        NumberAST(NumericLiteralToken("3")))
  }

  it should "parse modulo tokens" in {
    val result = parseString("17 MOD 3 + 57 % 5")
    result shouldBe
      AdditionAST(
        ModuloAST(
          NumberAST(NumericLiteralToken("17")),
          NumberAST(NumericLiteralToken("3"))),
        ModuloAST(
          NumberAST(NumericLiteralToken("57")),
          NumberAST(NumericLiteralToken("5"))))
  }

  it should "parse exponentiation expressions" in {
    val result = parseString("2 * 17 ^ 3.14 * 2137 ^ 16.5f ^ 3")
    result shouldBe
      MultiplicationAST(
        MultiplicationAST(
          NumberAST(NumericLiteralToken("2")),
          ExponentiationAST(
            NumberAST(NumericLiteralToken("17")),
            NumberAST(NumericLiteralToken("3.14")))),
        ExponentiationAST(
          ExponentiationAST(
            NumberAST(NumericLiteralToken("2137")),
            NumberAST(NumericLiteralToken("16.5f"))),
          NumberAST(NumericLiteralToken("3"))))
  }

  it should "parse parenthesised expressions" in {
    val string = "(17 * (2137 - 5)) ^ (3.14 / (4.5 + 0f))"
    val result = parseString(string)
    result shouldBe
      ExponentiationAST(
        MultiplicationAST(
          NumberAST(NumericLiteralToken("17")),
          SubtractionAST(
            NumberAST(NumericLiteralToken("2137")),
            NumberAST(NumericLiteralToken("5")))),
        DivisionAST(
          NumberAST(NumericLiteralToken("3.14")),
          AdditionAST(NumberAST(NumericLiteralToken("4.5")),
            NumberAST(NumericLiteralToken("0f")))))
  }

  it should "return error on incomplete expressions" in {
    val strings = List("17 +", "-", "3 + (5")
    for (string <- strings) {
      val tokens = Lexer(string).right.get
      val result = ExpressionParser(tokens).left.get
      result shouldBe a[ParserError]
    }
  }

  private def parseString(string: String): ExpressionAST = {
    val tokens = Lexer(string).right.get
    val result = ExpressionParser(tokens)
    result.right.get
  }
}
