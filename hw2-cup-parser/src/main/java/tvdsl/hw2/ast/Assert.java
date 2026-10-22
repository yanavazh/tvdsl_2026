package tvdsl.hw2.ast;

public class Assert implements Statement {
    public final Node predicate;
    public Assert(Node predicate) { this.predicate = predicate; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitAssert(this); }
}