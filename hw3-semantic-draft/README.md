# HW3. Семантические проверки и типы
## Цель
- Добавить проверки имен и типов в AST.
- Проверять корректность типов (параметры/возвраты с явной аннотацией; локальные можно оставлять без аннотации, см. бонус).
- Подготовить аннотированный AST для оптимизаций и генерация кодаерации.
## Реализация
* Язык реализации: Java.
* Система сборки: Maven.
* Версия JDK: 21.0.12.
* IDE: IntelliJ IDEA.
* Генераторы: JFlex 1.9.1, CUP 11b-20160615-3.

В HW3 используется парсер и AST из HW2. Добавлены:
* Секция parser code в parser.cup для перехвата синтаксических ошибок (без фатального Exception).
* Информация о позициях (line, column) в узлах AST (через методы getLine()/getColumn()).
* Типы (Type) и аннотирование узлов (setType()/getType()).
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
│   │   │       ├── hw3
│   │   │       │   ├── Main.java
│   │   │       │   └── ast
│   │   │       └── hw3
│   │   │           └── types
│   │   └── resources
│   │       └── test.funny
│   └── test
│       ├── java/tvdsl/hw3
│       │   └── ParserTest.java
│       └── resources
└── pom.xml
```
* `parser.cup`: грамматика языка Funny для генератора CUP.
* `funny.flex`: правила лексического анализа (токены).
* `Main.java`: класс, запускающий парсер и печатающий AST.
* `ast/`: классы узлов дерева и PrintVisitor для их обхода.
* `types/`: Type, Symbol, SymbolTable, SemanticError.
* `ParserTest.java`: тесты парсера.
## Архитектура семантического анализа
Семантический анализ реализован паттерном `Visitor`: `TypeCheckVisitor extends Visitor<Type>` обходит AST, возвращая тип каждого узла.
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

No semantic errors.
```
## Тестирование
Для проверки работоспособности парсера написаны JUnit-тесты (`ParserTest.java`):
* `testGoodFiles` — корректные программы (good_calls_single.funny, good_array_update_nested.funny) без семантических ошибок.
* `testUnknownIdentifier` — bad_unknown_identifier.funny → ошибка «Unknown identifier 'x'».
* `testUnknownFunction` — bad_unknown_function.funny → ошибка «Unknown function 'foo'».
* `testWrongArgCount` — bad_arg_count.funny → ошибка «Function 'add' expects 2 args, got 1».
* `testIndexNotInt` — bad_index_not_int.funny → ошибка «Array index must be int».
## Запуск
Сборка проекта и запуск тестов:
```bash
cd hw3-semantic-draft
mvn clean package
mvn test
```
Запуск парсера на тестовом файле:
```bash
java -cp target/classes:target/generated-sources/jflex:target/generated-sources/cup tvdsl.hw3.Main src/main/resources/test.funny
```
(Или через кнопку Run в IntelliJ IDEA, указав путь к файлу в Program arguments).