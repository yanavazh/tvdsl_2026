package tvdsl.hw4;

import tvdsl.hw3.Lexer;
import tvdsl.hw3.parser;
import tvdsl.hw3.ast.Program;
import tvdsl.hw3.ast.TypeCheckVisitor;
import tvdsl.hw3.types.SemanticError;
import tvdsl.hw4.cfg.BasicBlock;
import tvdsl.hw4.gen.IRGenVisitor;
import tvdsl.hw4.ir.IRPrinter;
import java_cup.runtime.Symbol;

import java.io.FileReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java tvdsl.hw4.Main <filepath>");
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
            System.exit(1);
        }

        if (result != null && result.value instanceof Program) {
            Program prog = (Program) result.value;

            TypeCheckVisitor checker = new TypeCheckVisitor();
            prog.accept(checker);

            List<SemanticError> errors = checker.getTable().getErrors();
            if (errors.isEmpty()) {
                System.out.println("No semantic errors.");
            } else {
                System.out.println("Semantic errors (" + errors.size() + "):");
                for (SemanticError e : errors) System.out.println("  " + e);
                System.exit(1);
            }

            IRGenVisitor irGen = new IRGenVisitor();
            prog.accept(irGen);
            List<BasicBlock> blocks = irGen.getBlocks();

            System.out.println("IR & CFG");
            System.out.println(IRPrinter.printProgram(blocks));

            tvdsl.hw4.opt.Optimizer.optimize(blocks);
            System.out.println("After optimizations");
            System.out.println(IRPrinter.printProgram(blocks));
        } else {
            System.err.println("Parsing returned null or not a Program.");
            System.exit(1);
        }
    }
}