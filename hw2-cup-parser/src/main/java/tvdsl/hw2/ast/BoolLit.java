package tvdsl.hw2.ast;

public class BoolLit implements Node {
    public final boolean value;
    public BoolLit(boolean value) { this.value = value; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitBoolLit(this); }
}