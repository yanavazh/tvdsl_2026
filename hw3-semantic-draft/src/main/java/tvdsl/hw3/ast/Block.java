package tvdsl.hw3.ast;

import java.util.List;

public class Block extends AnnotatedNode implements Statement {
    public final List<Statement> stmts;
    public Block(List<Statement> stmts) { this.stmts = stmts; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitBlock(this); }

    @Override public int getLine() { return stmts.isEmpty() ? 0 : stmts.get(0).getLine(); }
    @Override public int getColumn() { return stmts.isEmpty() ? 0 : stmts.get(0).getColumn(); }
}