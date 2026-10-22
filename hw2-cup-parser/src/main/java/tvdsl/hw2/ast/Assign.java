package tvdsl.hw2.ast;

public class Assign implements Statement {
    public enum Kind { VAR, ARRAY }
    public final Kind kind;
    public final String varName;
    public final Expr index; // null для VAR
    public final Expr expr;

    private Assign(Kind kind, String varName, Expr index, Expr expr) {
        this.kind = kind; this.varName = varName; this.index = index; this.expr = expr;
    }
    public static Assign var(String name, Expr e) { return new Assign(Kind.VAR, name, null, e); }
    public static Assign array(String name, Expr idx, Expr e) { return new Assign(Kind.ARRAY, name, idx, e); }

    @Override public <R> R accept(Visitor<R> v) { return v.visitAssign(this); }
}