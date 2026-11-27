package tvdsl.hw3.ast;

public class Number extends AnnotatedNode implements Expr {
    public final int value;
    private final int line, column;

    public Number(int value, int line, int column) {
        this.value = value;
        this.line = line;
        this.column = column;
    }

    @Override public <R> R accept(Visitor<R> v) {
        return v.visitNumber(this);
    }

    @Override public int getLine() {
        return line;
    }
    @Override public int getColumn() {
        return column;
    }
}