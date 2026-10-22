package tvdsl.hw2.ast;

public class Var implements Expr {
    public final String name;
    public Var(String name) { this.name = name; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitVar(this); }
}