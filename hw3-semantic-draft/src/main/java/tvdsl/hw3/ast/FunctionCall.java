package tvdsl.hw3.ast;

import java.util.List;

public class FunctionCall extends AnnotatedNode implements Expr {
    public final String name;
    public final List<Expr> args;
    private final int line, column;

    public FunctionCall(String name, List<Expr> args, int line, int column) {
        this.name = name;
        this.args = args;
        this.line = line;
        this.column = column;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visitFunctionCall(this);
    }

    @Override public int getLine() {
        return line;
    }
    @Override public int getColumn() {
        return column;
    }
}