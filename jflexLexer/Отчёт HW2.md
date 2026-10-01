## Цель
* Изучить генератор лексических анализаторов JFlex.
* Написать файл формата .flex для генерации лексера для языка Funny.
* Сгенерировать лексер для языка Funny с помощью JFlex.
* Провести тест сгенерированного лексера на примере; убедиться в работоспособности лексера. 
## Реализация
* Язык реализации: Java.
* Система сборки: Maven.
* Версия JDK: 21.0.12.
* IDE: IntelllijIdea.
### Структура проекта
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── tvdsl
│   │   │       └── hw2
│   │   │           ├── JFlexLexer.java
│   │   │           ├── Main.java
│   │   │           ├── Token.java
│   │   │           └── TokenType.java
│   │   └── resources
│   │       ├── funny.flex
│   │       └── test.txt
```
`JFlexLexer.java`: класс сгенерированного лексера. 
`Main.java`: класс -- точка запуска программы.
`Token.java`: класс для хранения информации токена.
`TokenType.java`: класс для хранения типов токена.
`funny.flex`: файл для генератора JFlex.
`test.txt`: файл с примером кода на языке Funny.
### Список регулярных выражений
```funny.flex
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
```
## Работа программы
### Вход
```test.txt
main() returns r:int {
    r = 1 + 2 * 3;
}
```
### Выход
```
Token{IDENT, value='main'}
Token{LPAREN, value='('}
Token{RPAREN, value=')'}
Token{RETURNS, value='returns'}
Token{IDENT, value='r'}
Token{COLON, value=':'}
Token{IDENT, value='int'}
Token{LBRACE, value='{'}
Token{IDENT, value='r'}
Token{ASSIGN, value='='}
Token{INT, value='1'}
Token{PLUS, value='+'}
Token{INT, value='2'}
Token{MULT, value='*'}
Token{INT, value='3'}
Token{SEMI, value=';'}
Token{RBRACE, value='}'}
```