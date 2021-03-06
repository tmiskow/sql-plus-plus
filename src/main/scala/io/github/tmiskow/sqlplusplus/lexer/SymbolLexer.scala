package io.github.tmiskow.sqlplusplus.lexer

trait SymbolLexer extends BaseLexer {
  override def symbol: Parser[Token] = (
    ";" ^^^ SemicolonToken
    | "," ^^^ CommaToken
    | "." ^^^ DotToken
    | ":" ^^^ ColonToken
    | "[" ^^^ LeftArrayBracketToken
    | "]" ^^^ RightArrayBracketToken
    | "(" ^^^ LeftParenthesisToken
    | ")" ^^^ RightParenthesisToken
    | "{" ^^^ LeftObjectBracketToken
    | "}" ^^^ RightObjectBracketToken
  )
}
