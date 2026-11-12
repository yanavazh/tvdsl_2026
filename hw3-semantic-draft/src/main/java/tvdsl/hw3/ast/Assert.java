package tvdsl.hw3.ast;

public class Assert extends AnnotatedNode implements Statement {
    public final Node predicate;

    public Assert(Node predicate) {
        this.predicate = predicate;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visitAssert(this);
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