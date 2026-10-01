package org.example;

import tvdsl.hw2.Token;
import org.example.TokenType;

%%
%public
%class JFlexLexer
%unicode
%type org.example.Token
%%

or                      { return new Token(TokenType.OR, yytext()); }
if                      { return new Token(TokenType.IF, yytext()); }
not                     { return new Token(TokenType.NOT, yytext()); }
and                     { return new Token(TokenType.AND, yytext()); }
else                    { return new Token(TokenType.ELSE, yytext()); }
uses                    { return new Token(TokenType.USES, yytext()); }
true                    { return new Token(TokenType.TRUE, yytext()); }
false                   { return new Token(TokenType.FALSE, yytext()); }
while                   { return new Token(TokenType.WHILE, yytext()); }
returns                 { return new Token(TokenType.RETURNS, yytext()); }
function                { return new Token(TokenType.FUNCTION, yytext()); }

[A-Za-z_][A-Za-z0-9_]*  { return new Token(TokenType.IDENT, yytext()); }

0|[1-9][0-9]*           { return new Token(TokenType.INT, yytext()); }

"("                      { return new Token(TokenType.LPAREN, yytext()); }
")"                      { return new Token(TokenType.RPAREN, yytext()); }
":"                      { return new Token(TokenType.COLON, yytext()); }
"="                      { return new Token(TokenType.ASSIGN, yytext()); }
"+"                      { return new Token(TokenType.PLUS, yytext()); }
"*"                     { return new Token(TokenType.MULT, yytext()); }
";"                      { return new Token(TokenType.SEMI, yytext()); }
"{"                      { return new Token(TokenType.LBRACE, yytext()); }
"}"                      { return new Token(TokenType.RBRACE, yytext()); }

[ \t\r\n]+              { }
// "//".*              { return new Token(TokenType.COMMENT, yytext()); }