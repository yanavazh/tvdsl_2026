package tvdsl.hw3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import tvdsl.hw3.ast.Program;
import tvdsl.hw3.ast.TypeCheckVisitor;
import tvdsl.hw3.types.SemanticError;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ParserTest {

    private List<SemanticError> checkFile(String fileName) throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        if (is == null) throw new RuntimeException("Файл не найден: " + fileName);
        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

        Lexer lexer = new Lexer(reader);
        parser p = new parser(lexer);
        Program prog = (Program) p.parse().value;

        TypeCheckVisitor checker = new TypeCheckVisitor();
        prog.accept(checker);

        return checker.getTable().getErrors();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "good_calls_single.funny",
            "good_array_update_nested.funny"
    })
    public void testGoodFiles(String fileName) throws Exception {
        List<SemanticError> errors = checkFile(fileName);
        Assertions.assertTrue(errors.isEmpty(),
                "Файл " + fileName + " содержит ошибки: " + errors);
    }

    @Test
    public void testUnknownIdentifier() throws Exception {
        List<SemanticError> errors = checkFile("bad_unknown_identifier.funny");
        Assertions.assertFalse(errors.isEmpty(), "Должна быть ошибка");
        Assertions.assertTrue(
                errors.stream().anyMatch(e -> e.message.contains("Unknown identifier 'x'")),
                "Ожидалось 'Unknown identifier 'x'', получено: " + errors);
    }

    @Test
    public void testUnknownFunction() throws Exception {
        List<SemanticError> errors = checkFile("bad_unknown_function.funny");
        Assertions.assertFalse(errors.isEmpty(), "Должна быть ошибка");
        Assertions.assertTrue(
                errors.stream().anyMatch(e -> e.message.contains("Unknown function 'foo'")),
                "Ожидалось 'Unknown function 'foo'', получено: " + errors);
    }

    @Test
    public void testWrongArgCount() throws Exception {
        List<SemanticError> errors = checkFile("bad_arg_count.funny");
        Assertions.assertFalse(errors.isEmpty(), "Должна быть ошибка");
        Assertions.assertTrue(
                errors.stream().anyMatch(e -> e.message.contains("expects 2 args")
                        || e.message.contains("expects 2 argument")),
                "Ожидалось сообщение про 'expects 2 args', получено: " + errors);
    }

    @Test
    public void testIndexNotInt() throws Exception {
        List<SemanticError> errors = checkFile("bad_index_not_int.funny");
        Assertions.assertFalse(errors.isEmpty(), "Должна быть ошибка");
        Assertions.assertTrue(
                errors.stream().anyMatch(e -> e.message.contains("Array index must be int")
                        || e.message.contains("Type mismatch")),
                "Ожидалось сообщение про индекс массива, получено: " + errors);
    }
}