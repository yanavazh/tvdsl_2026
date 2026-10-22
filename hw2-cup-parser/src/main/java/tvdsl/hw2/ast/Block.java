package tvdsl.hw2.ast;

import java.util.List;

public class Block implements Statement {
    public final List<Statement> stmts;
    public Block(List<Statement> stmts) { this.stmts = stmts; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitBlock(this); }
}