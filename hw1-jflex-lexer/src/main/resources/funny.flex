package org.example;

import tvdsl.hw1.Token;
import org.example.TokenType;

%%
%public
%class JFlexLexer
%unicode
%type org.example.Token
%%

or                      { return new Token(TokenType.OR, yytext(), line, column); }
if                      { return new Token(TokenType.IF, yytext(), line, column); }
not                     { return new Token(TokenType.NOT, yytext(), line, column); }
and                     { return new Token(TokenType.AND, yytext(), line, column); }
else                    { return new Token(TokenType.ELSE, yytext(), line, column); }
uses                    { return new Token(TokenType.USES, yytext(), line, column); }
true                    { return new Token(TokenType.TRUE, yytext(), line, column); }
false                   { return new Token(TokenType.FALSE, yytext(), line, column); }
while                   { return new Token(TokenType.WHILE, yytext(), line, column); }
returns                 { return new Token(TokenType.RETURNS, yytext(), line, column); }
function                { return new Token(TokenType.FUNCTION, yytext(), line, column); }

[A-Za-z_][A-Za-z0-9_]*  { return new Token(TokenType.IDENT, yytext(), line, column); }

0|[1-9][0-9]*           { return new Token(TokenType.INT, yytext(), line, column); }

"("                      { return new Token(TokenType.LPAREN, yytext(), line, column); }
")"                      { return new Token(TokenType.RPAREN, yytext(), line, column); }
":"                      { return new Token(TokenType.COLON, yytext(), line, column); }
"="                      { return new Token(TokenType.ASSIGN, yytext(), line, column); }
"+"                      { return new Token(TokenType.PLUS, yytext(), line, column); }
"*"                      { return new Token(TokenType.MULT, yytext(), line, column); }
";"                      { return new Token(TokenType.SEMI, yytext(), line, column); }
"{"                      { return new Token(TokenType.LBRACE, yytext(), line, column); }
"}"                      { return new Token(TokenType.RBRACE, yytext(), line, column); }

[ \t\r\n]+              {
                            if (yytext().charAt(0) == '\n') {
                                line++;
                                column = 0;
                            } else {
                                column += yytext().length();
                            }
                        }
// "//".*              { return new Token(TokenType.COMMENT, yytext(), line, column); }

.                       {
    return new Token(TokenType.ERROR, yytext(), line, column);
}