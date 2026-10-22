package tvdsl.hw2.ast;

public class Number implements Expr {
    public final int value;
    public Number(int value) { this.value = value; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitNumber(this); }
}