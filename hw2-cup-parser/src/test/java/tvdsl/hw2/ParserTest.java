package tvdsl.hw2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import tvdsl.hw2.ast.PrintVisitor;
import tvdsl.hw2.ast.Program;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class ParserTest {

    private void parseFile(String fileName) throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            throw new RuntimeException("Файл не найден в ресурсах: " + fileName);
        }
        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        Lexer lexer = new Lexer(reader);
        parser p = new parser(lexer);
        p.parse();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "good_basic.funny",
            "good_parentheses.funny",
            "good_precedence.funny",
            "good_unary.funny"
    })
    public void testGoodFiles(String fileName) {
        Assertions.assertDoesNotThrow(() -> {
            parseFile(fileName);
        }, "Файл " + fileName + " должен успешно парситься, но упал с ошибкой");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "bad_missing_semicolon.funny"
    })
    public void testBadFiles(String fileName) {
        Assertions.assertThrows(Exception.class, () -> {
            parseFile(fileName);
        }, "Файл " + fileName + " должен вызывать ошибку синтаксиса");
    }

    @Test
    public void testAstSnapshot() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("good_basic.funny");
        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

        Lexer lexer = new Lexer(reader);
        parser p = new parser(lexer);

        Program prog = (Program) p.parse().value;

        PrintVisitor printer = new PrintVisitor();
        prog.accept(printer);
        String actual = printer.result();

        String expected =
                "Program\n" +
                        "  Function main\n" +
                        "    params:\n" +
                        "    returns:\n" +
                        "      VarDef r : int\n" +
                        "    body:\n" +
                        "      Block\n" +
                        "        Assign[VAR] r\n" +
                        "          value:\n" +
                        "            BinOp +\n" +
                        "              Num 1\n" +
                        "              BinOp *\n" +
                        "                Num 2\n" +
                        "                Num 3\n";

        Assertions.assertEquals(expected, actual, "AST-дерево не совпадает с эталонным");
    }
}