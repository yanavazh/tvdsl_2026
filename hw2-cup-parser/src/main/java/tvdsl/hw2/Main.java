package tvdsl.hw2;

import tvdsl.hw2.ast.PrintVisitor;
import tvdsl.hw2.ast.Program;
import java_cup.runtime.Symbol;

import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Использование: java tvdsl.hw2.Main <путь_к_файлу>");
            System.exit(1);
        }

        Lexer lexer = new Lexer(new FileReader(args[0]));
        parser p = new parser();
        p.setScanner(lexer);

        Symbol result = p.parse();

        if (result.value != null) {
            Program prog = (Program) result.value;
            PrintVisitor printer = new PrintVisitor();
            prog.accept(printer);
            System.out.print(printer.result());
        } else {
            System.out.println("Парсер вернул null.");
        }
    }
}