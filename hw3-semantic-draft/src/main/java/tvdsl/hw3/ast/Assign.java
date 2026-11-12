package tvdsl.hw3.ast;

public class Assign extends AnnotatedNode implements Statement {
    public enum Kind { VAR, ARRAY }
    public final Kind kind;
    public final String varName;
    public final Expr index;
    public final Expr expr;
    private final int line, column;

    private Assign(Kind kind, String varName, Expr index, Expr expr, int line, int column) {
        this.kind = kind;
        this.varName = varName;
        this.index = index;
        this.expr = expr;
        this.line = line;
        this.column = column;
    }
    public static Assign var(String name, Expr e, int line, int column) {
        return new Assign(Kind.VAR, name, null, e, line, column);
    }
    public static Assign array(String name, Expr idx, Expr e, int line, int column) {
        return new Assign(Kind.ARRAY, name, idx, e, line, column);
    }

    @Override public <R> R accept(Visitor<R> v) {
        return v.visitAssign(this);
    }

    @Override public int getLine() {
        return line;
    }
    @Override public int getColumn() {
        return column;
    }
}