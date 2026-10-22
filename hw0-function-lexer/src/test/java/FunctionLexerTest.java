import tvdsl.hw0.FunctionLexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FunctionLexerTest {
    public static void main(String[] args) {
        String[] testFiles = {
                "src/test/java/test_accept_1.txt",
                "src/test/java/test_accept_2.txt",
                "src/test/java/test_reject_1.txt",
                "src/test/java/test_reject_2.txt",
                "src/test/java/test_empty.txt"
        };

        for (String file : testFiles) {
            System.out.println("~~~ Тест " + file + " ~~~");

            try {
                String content = Files.readString(Paths.get(file));
                if (content.isEmpty()) {
                    System.out.println("Файл пуст");
                } else {
                    System.out.println("Содержимое файла: \"" + content + "\"");
                }
            } catch (IOException e) {
                System.err.println("Не удалось прочитать файл: " + e.getMessage());
                continue;
            }
            FunctionLexer lexer = new FunctionLexer(file);
            lexer.runProcess();
            System.out.println();
        }
    }
}
