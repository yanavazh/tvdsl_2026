package tvdsl.hw2.ast;

public class LogicOp implements Node {
    public final String op; // and, or
    public final Node left, right;
    public LogicOp(String op, Node left, Node right) { this.op = op; this.left = left; this.right = right; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitLogicOp(this); }
}