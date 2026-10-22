package tvdsl.hw2.ast;

public class If implements Statement {
    public final Node condition; // Condition (сделаем общим Node для простоты)
    public final Statement thenS;
    public final Statement elseS; // null, если нет else
    public If(Node condition, Statement thenS, Statement elseS) {
        this.condition = condition; this.thenS = thenS; this.elseS = elseS;
    }
    @Override public <R> R accept(Visitor<R> v) { return v.visitIf(this); }
}