package tvdsl.hw3.ast;
import java.util.List;

public class AssignMulti extends AnnotatedNode implements Statement {
    public final List<String> vars;
    public final FunctionCall call;
    public AssignMulti(List<String> vars, FunctionCall call) { this.vars = vars; this.call = call; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitAssignMulti(this); }

    @Override
    public int getLine() {
        return call.getLine();
    }

    @Override
    public int getColumn() {
        return call.getColumn();
    }
}