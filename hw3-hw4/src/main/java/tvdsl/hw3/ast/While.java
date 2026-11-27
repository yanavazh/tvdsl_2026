package tvdsl.hw3.ast;
import java.util.List;

public class While extends AnnotatedNode implements Statement {
    public final Node condition;
    public final List<Node> invariants;
    public final Statement body;
    public While(Node condition, List<Node> invariants, Statement body) {
        this.condition = condition; this.invariants = invariants; this.body = body;
    }
    @Override public <R> R accept(Visitor<R> v) { return v.visitWhile(this); }

    @Override public int getLine() {
        return condition.getLine();
    }
    @Override public int getColumn() {
        return condition.getColumn();
    }
}