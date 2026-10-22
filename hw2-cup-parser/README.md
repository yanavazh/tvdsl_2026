# HW2. Парсер: LR(SLR) + интеграция с лексером
## Цель
* Реализовать синтаксический анализ языка Funny.
* Поддержать обработку ошибок (через генератор).
* Подготовить AST-вывод для следующих этапов и интеграцию с лексером проекта.
## Реализация
* Язык реализации: Java.
* Система сборки: Maven.
* Версия JDK: 21.0.12.
* IDE: IntelliJ IDEA.
* Генераторы: JFlex 1.9.1, CUP 11b-20160615-3.

Т.к. лексер из HW1 был сгенерирован на неполном множестве терминалов языка Funny и использовал кастомный класс Token, лексер для HW2 был дополнен и переписан с учётом нужного кода для интеграции с парсером CUP.  
### Структура проекта
```text
├── src
│   ├── main
│   │   ├── cup
│   │   │   └── parser.cup
│   │   ├── flex
│   │   │   └── funny.flex
│   │   ├── java
│   │   │   └── tvdsl
│   │   │       └── hw2
│   │   │           ├── Main.java
│   │   │           └── ast
│   │   └── resources
│   │       └── test.funny
│   └── test
│       ├── java/tvdsl/hw2
│       │   └── ParserTest.java
│       └── resources
└── pom.xml
```
* `parser.cup`: грамматика языка Funny для генератора CUP.
* `funny.flex`: правила лексического анализа (токены).
* `Main.java`: класс, запускающий парсер и печатающий AST.
* `ast/`: классы узлов дерева и PrintVisitor для их обхода.
* `ParserTest.java`: тесты парсера.
## Работа программы
### Вход
```test.txt
main() returns r:int {
    r = 1 + 2 * 3;
}
```
### Выход
```
Program
  Function main
    params:
    returns:
      VarDef r : int
    body:
      Block
        Assign[VAR] r
          value:
            BinOp +
              Num 1
              BinOp *
                Num 2
                Num 3
```
## Тестирование
Для проверки работоспособности парсера написаны JUnit-тесты (`ParserTest.java`):
* `testGoodFiles` — проверяет, что корректные программы (`good_basic.funny`, `good_parentheses.funny`, `good_precedence.funny`, `good_unary.funny`) разбираются без ошибок.
* `testBadFiles` — проверяет, что при наличии синтаксической ошибки (например, пропущенной ; в `bad_missing_semicolon.funny`) парсер выбрасывает исключение и не зацикливается.
* `testAstSnapshot` — сравнивает AST-дерево, построенное для `good_basic.funny`, с эталонной строкой (snapshot-тест).
## Запуск
Сборка проекта и запуск тестов:
```bash
cd hw2-cup-parser
mvn clean package
mvn test
```
Запуск парсера на тестовом файле:
```bash
java -cp target/classes:target/generated-sources/jflex:target/generated-sources/cup tvdsl.hw2.Main src/main/resources/test.funny
```
(Или через кнопку Run в IntelliJ IDEA, указав путь к файлу в Program arguments).
## Грамматика
```
program = function ;

function = identifier "(" [ variableDefList ] ")"
           [ "requires" predicate ]
           "returns" variableDefList
           [ "ensures" predicate ]
           [ "uses" localVarDefList ]
           statement ;

variableDefList = variableDef { "," variableDef } ;
variableDef = identifier ":" variableType ;

localVarDefList = localVarDef { "," localVarDef } ;
localVarDef = identifier [ ":" variableType ] ;

variableType = "int" | "int[]" ;

statement = assignment
          | conditional
          | whileLoop
          | block
          | assertStmt
          | assumeStmt ;

assignment = identifier "=" expr ";"
           | arrayAccess "=" expr ";"
           | identifier "," identifier "=" functionCall ";" ;

conditional = "if" "(" condition ")" statement [ "else" statement ] ;

whileLoop = "while" "(" condition ")" [ "invariant" predicate ] statement ;

block = "{" { statement } "}" ;

assertStmt = "assert" predicate ";" ;
assumeStmt = "assume" predicate ";" ;

expr = term { ( "+" | "-" | "*" | "/" ) term } ;
term = functionCall
     | arrayAccess
     | number
     | identifier
     | "-" term
     | "(" expr ")" ;

functionCall = identifier "(" [ exprList ] ")" ;
exprList = expr { "," expr } ;

arrayAccess = identifier "[" expr "]" ;

condition = "true"
          | "false"
          | comparison
          | "not" condition
          | condition "and" condition
          | condition "or" condition
          | condition "->" condition
          | "(" condition ")" ;

comparison = expr "==" expr
           | expr "!=" expr
           | expr ">=" expr
           | expr "<=" expr
           | expr ">"  expr
           | expr "<"  expr ;

predicate = quantifier
          | "true"
          | "false"
          | comparison
          | "not" predicate
          | predicate "and" predicate
          | predicate "or" predicate
          | "(" predicate ")" ;

quantifier = ("forall" | "exists") "(" variableDef "|" predicate ")" ;
```