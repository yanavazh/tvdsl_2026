package tvdsl.hw3.ast;

public class LogicOp extends AnnotatedNode implements Node {
    public final String op; // and, or
    public final Node left, right;
    public LogicOp(String op, Node left, Node right) { this.op = op; this.left = left; this.right = right; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitLogicOp(this); }

    @Override public int getLine() {
        return left.getLine();
    }
    @Override public int getColumn() {
        return left.getColumn();
    }
}