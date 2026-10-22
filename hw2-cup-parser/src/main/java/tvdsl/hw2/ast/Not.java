package tvdsl.hw2.ast;

public class Not implements Node {
    public final Node inner;
    public Not(Node inner) { this.inner = inner; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitNot(this); }
}