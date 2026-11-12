package tvdsl.hw3;

import tvdsl.hw3.ast.Program;
import tvdsl.hw3.ast.PrintVisitor;
import tvdsl.hw3.ast.TypeCheckVisitor;
import java_cup.runtime.Symbol;
import tvdsl.hw3.types.SemanticError;

import java.io.FileReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java tvdsl.hw3.Main <filepath>");
            System.exit(1);
        }

        Symbol result = null;
        parser p = null;

        try {
            Lexer lexer = new Lexer(new FileReader(args[0]));
            p = new parser(lexer);
            result = p.parse();
        } catch (Exception e) {
            System.err.println("Parsing failed: " + e.getMessage());
        }

        if (result != null && result.value instanceof Program) {
            Program prog = (Program) result.value;

            PrintVisitor printer = new PrintVisitor();
            prog.accept(printer);
            System.out.println(printer.result());

            TypeCheckVisitor checker = new TypeCheckVisitor();
            prog.accept(checker);

            List<SemanticError> errors = checker.getTable().getErrors();
            if (errors.isEmpty()) {
                System.out.println("No semantic errors.");
            } else {
                System.out.println("Semantic errors (" + errors.size() + "):");
                for (SemanticError e : errors) System.out.println("  " + e);
            }
        }
    }
}