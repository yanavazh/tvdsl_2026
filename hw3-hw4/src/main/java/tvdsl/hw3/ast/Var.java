package tvdsl.hw3.ast;

public class Var extends AnnotatedNode implements Expr {
    public final String name;
    private final int line, column;

    public Var(String name, int line, int column) {
        this.name = name;
        this.line = line;
        this.column = column;
    }
    @Override public <R> R accept(Visitor<R> v) {
        return v.visitVar(this);
    }

    @Override public int getLine() {
        return line;
    }
    @Override public int getColumn() {
        return column;
    }
}