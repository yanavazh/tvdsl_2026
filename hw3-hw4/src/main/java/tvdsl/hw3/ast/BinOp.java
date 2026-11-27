package tvdsl.hw3.ast;

public class BinOp extends AnnotatedNode implements Expr {
    public final String op;
    public final Expr left;
    public final Expr right;

    public BinOp(String op, Expr left, Expr right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override public <R> R accept(Visitor<R> v) {
        return v.visitBinOp(this);
    }

    @Override public int getLine() {
        return left.getLine();
    }
    @Override public int getColumn() {
        return left.getColumn();
    }
}