package tvdsl.hw2.ast;

public class Assume implements Statement {
    public final Node predicate;
    public Assume(Node predicate) { this.predicate = predicate; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitAssume(this); }
}