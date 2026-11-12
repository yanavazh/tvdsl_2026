package tvdsl.hw3.ast;

import java.util.List;

public class Program extends AnnotatedNode implements Node {
    public final List<Function> functions;

    public Program(List<Function> functions) {
        this.functions = functions;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visitProgram(this);
    }

    @Override
    public int getLine() {
        return functions.isEmpty() ? 0 : functions.get(0).getLine();
    }

    @Override
    public int getColumn() {
        return functions.isEmpty() ? 0 : functions.get(0).getColumn();
    }
}