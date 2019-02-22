package io.github.tmiskow.sqlplusplus.lexer

trait KeywordLexer extends BaseLexer {
  override def keyword: Parser[Token] = KeywordLexer.allKeywordStrings.map(keyword =>
    keyword.ignoreCase ^^ (_.toUpperCase()) ^^ KeywordToken ^^ {
      case KeywordToken("RAW") | KeywordToken("ELEMENT") => KeywordToken("VALUE")
      case token => token
    }).reduce((a, b) => a | b)
}

object KeywordLexer {
  val valueKeywordStrings: Set[String] = Set("raw", "element", "value")

  val keywordStrings: Set[String] = Set(
    "all",
    "as",
    "distinct",
    "let",
    "select",
    "with",
    "from",
    "where",
    "unnest"
  )

  val allKeywordStrings: Set[String] = valueKeywordStrings ++ keywordStrings
}