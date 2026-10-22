package tvdsl.hw2;

import java_cup.runtime.Symbol;

%%
%public
%class Lexer
%unicode
%cup
%line
%column

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }
%}

%%

"int[]"     { return symbol(sym.VARIABLE_TYPE, yytext()); }
"int"       { return symbol(sym.VARIABLE_TYPE, yytext()); }

"or"        { return symbol(sym.OR); }
"if"        { return symbol(sym.IF); }
"not"       { return symbol(sym.NOT); }
"and"       { return symbol(sym.AND); }
"else"      { return symbol(sym.ELSE); }
"uses"      { return symbol(sym.USES); }
"true"      { return symbol(sym.TRUE); }
"false"     { return symbol(sym.FALSE); }
"while"     { return symbol(sym.WHILE); }
"returns"   { return symbol(sym.RETURNS); }
"forall"    { return symbol(sym.FORALL); }
"exists"    { return symbol(sym.EXISTS); }
"requires"  { return symbol(sym.REQUIRES); }
"ensures"   { return symbol(sym.ENSURES); }
"invariant" { return symbol(sym.INVARIANT); }
"assert"    { return symbol(sym.ASSERT); }
"assume"    { return symbol(sym.ASSUME); }

[A-Za-z_][A-Za-z0-9_]*  { return symbol(sym.IDENTIFIER, yytext()); }
0|[1-9][0-9]*           { return symbol(sym.NUMBER, Integer.valueOf(yytext())); }

"("         { return symbol(sym.LPAREN); }
")"         { return symbol(sym.RPAREN); }
","         { return symbol(sym.COMMA); }
":"         { return symbol(sym.COLON); }
"="         { return symbol(sym.ASSIGN); }
"+"         { return symbol(sym.PLUS); }
"-"         { return symbol(sym.MINUS); }
"*"         { return symbol(sym.TIMES); }
"/"         { return symbol(sym.DIVIDE); }
";"         { return symbol(sym.SEMI); }
"{"         { return symbol(sym.LBRACE); }
"}"         { return symbol(sym.RBRACE); }
"=="        { return symbol(sym.EQ); }
"!="        { return symbol(sym.NEQ); }
">="        { return symbol(sym.GE); }
"<="        { return symbol(sym.LE); }
">"         { return symbol(sym.GT); }
"<"         { return symbol(sym.LT); }
"["         { return symbol(sym.LBRACKET); }
"]"         { return symbol(sym.RBRACKET); }
"->"        { return symbol(sym.IMPLIES); }

[ \t\r\n]+  { /* игнорируем */ }
"//".*      { /* игнорируем однострочные комментарии */ }

[^]         { throw new RuntimeException("Illegal character: " + yytext()); }