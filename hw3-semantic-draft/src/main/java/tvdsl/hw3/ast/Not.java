package tvdsl.hw3.ast;

public class Not extends AnnotatedNode implements Node {
    public final Node inner;
    public Not(Node inner) { this.inner = inner; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitNot(this); }

    @Override public int getLine() {
        return inner.getLine();
    }
    @Override public int getColumn() {
        return inner.getColumn();
    }
}