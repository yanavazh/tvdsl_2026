package tvdsl.hw2.ast;

public class Program implements Node {
    public final Function func;
    public Program(Function f) { this.func = f; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitProgram(this); }
}