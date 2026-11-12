package tvdsl.hw3.ast;

public class BoolLit extends AnnotatedNode implements Node {
    public final boolean value;

    public BoolLit(boolean value) {
        this.value = value;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visitBoolLit(this);
    }

    @Override
    public int getLine() {
        return 0; // У литерала нет позиции
    }

    @Override
    public int getColumn() {
        return 0;
    }
}