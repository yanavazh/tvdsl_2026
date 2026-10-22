package tvdsl.hw2.ast;

import java.util.List;

public class FunctionCall implements Expr {
    public final String name;
    public final List<Expr> args;
    public FunctionCall(String name, List<Expr> args) { this.name = name; this.args = args; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitFunctionCall(this); }
}