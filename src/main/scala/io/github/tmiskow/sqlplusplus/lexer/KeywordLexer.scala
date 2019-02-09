package io.github.tmiskow.sqlplusplus.lexer

trait KeywordLexer extends BaseLexer {
  override def keyword: Parser[Token] = Set(
    "all",
    "as",
    "distinct",
    "let",
    "select",
    "with",
    "raw",
    "element",
    "value"
  ).map(keyword => keyword.ignoreCase ^^ (_.toUpperCase()) ^^ KeywordToken ^^ {
    case KeywordToken("RAW") | KeywordToken("ELEMENT") => KeywordToken("VALUE")
    case token => token
  }).reduce((a, b) => a | b)
}
