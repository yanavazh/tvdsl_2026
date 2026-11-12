package tvdsl.hw3.ast;

public class ArrayAccess extends AnnotatedNode implements Expr {
    public final String name;
    public final Expr index;
    private final int line, column;

    public ArrayAccess(String name, Expr index, int line, int column) {
        this.name = name;
        this.index = index;
        this.line = line;
        this.column = column;
    }

    @Override public <R> R accept(Visitor<R> v) {
        return v.visitArrayAccess(this);
    }

    @Override public int getLine() {
        return line;
    }
    @Override public int getColumn() {
        return column;
    }
}