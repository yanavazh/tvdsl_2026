package tvdsl.hw3.ast;

public class Assume extends AnnotatedNode implements Statement {
    public final Node predicate;

    public Assume(Node predicate) {
        this.predicate = predicate;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visitAssume(this);
    }

    @Override
    public int getLine() {
        return predicate.getLine();
    }

    @Override
    public int getColumn() {
        return predicate.getColumn();
    }
}