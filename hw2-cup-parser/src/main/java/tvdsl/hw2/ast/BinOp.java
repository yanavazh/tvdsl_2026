package tvdsl.hw2.ast;

public class BinOp implements Expr {
    public final String op;
    public final Expr left;
    public final Expr right;
    public BinOp(String op, Expr left, Expr right) { this.op = op; this.left = left; this.right = right; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitBinOp(this); }
}