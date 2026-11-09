package tvdsl.hw2.ast;

import java.util.List;

public class Program implements Node {
    public final List<Function> functions;

    public Program(List<Function> functions) {
        this.functions = functions;
    }

    @Override public <R> R accept(Visitor<R> v) { return v.visitProgram(this); }
}