package tvdsl.hw3.ast;

public class Compare extends AnnotatedNode implements Node {
    public final String op;
    public final Expr left, right;
    public Compare(String op, Expr left, Expr right) { this.op = op; this.left = left; this.right = right; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitCompare(this); }

    @Override public int getLine() {
        return left.getLine();
    }
    @Override public int getColumn() {
        return left.getColumn();
    }
}