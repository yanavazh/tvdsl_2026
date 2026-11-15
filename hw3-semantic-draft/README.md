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

В HW3 используется парсер и AST из HW2. В парсер и AST-классы добавлен код для отслеживания позиции токена.
## Структура проекта
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
## Работа программы
На вход программа получает файл с кодом на языке Funny. Далее сгенерированный с помощью CUP парсер получает на вход токены, полученные через сгенерированный с помощью JFlex лексер, строит AST-дерево и возвращает корень дерева — AST-класс `Program`. Если в коде есть синтаксическая ошибка, парсер не бросает Exception, а останавливается. В этом случае парсер возвращает null или частично построенное дерево.
### Семантический анализ
Если корень полученного из парсера AST-дерева — класс Program:
1. `PrintVisitor` печатает AST-дерево, спускаясь через `accept()` к листьям дерева.
2. `TypeCheckVisitor` проверяет семантику полученного AST-дерева:
    - В классе `SymbolTable` реализована таблица символов — `Deque<Map<String, Symbol>>`, которая работает как стек — при заходе в классы `Program`, `Function`, `Block` и `Quantifier` `TypeCheckVisitor` кладёт на стек отдельный `Map<String, Symbol>`, при выходе — забирает его из стека. При добавлении переменной в `Map<String, Symbol>` проверяется отсутствие переменной с таким же именем в стеке функции (исключает повторное объявление переменной) и во всём стеке программы (исключает затенение).
    - Также в классе `SymbolTable` `List<SemanticError>`, в который добавляются все семантические ошибки.
    - При заходе в класс `Program` в стек кладутся имена функций с параметрами (для проверки `FunctionCall` — функция должна быть объявлена), после чего начинается проход по всем функциям.
    - В Funny параметры функции являются _read-only_, поэтому в таблице символов такие переменные указываются как `mutable = false`.
    - Если переменная объявлена в _uses_ без аннотации (тип `null` в таблице символов), то при первом присваивании в `visitAssign()` её тип выводится из типа правой части. Дальнейшие присваивания проверяются на совместимость.
3. Печатается `List<SemanticError>`.
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