package tvdsl.hw2.ast;

public class ArrayAccess implements Expr {
    public final String name;
    public final Expr index;
    public ArrayAccess(String name, Expr index) { this.name = name; this.index = index; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitArrayAccess(this); }
}