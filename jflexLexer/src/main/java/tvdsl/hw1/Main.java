package tvdsl.hw1;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        String inputFileName = "/home/yana/NSU/TV/tvdsl_2026/jflexLexer/src/main/resources/test.txt";
        try {
            Reader reader = new InputStreamReader(new FileInputStream(inputFileName), StandardCharsets.US_ASCII);
            JFlexLexer lexer = new JFlexLexer(reader);
            Token token;
            while ((token = lexer.yylex()) != null) {
                System.out.println(token);
            }
        }
        catch (IOException e) {
            System.err.println("Не удалось открыть файл для чтения:" + e.getLocalizedMessage());
        }
    }
}