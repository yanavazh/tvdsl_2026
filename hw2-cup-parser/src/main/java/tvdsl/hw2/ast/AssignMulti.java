package tvdsl.hw2.ast;
import java.util.List;

public class AssignMulti implements Statement {
    public final List<String> vars;
    public final FunctionCall call;
    public AssignMulti(List<String> vars, FunctionCall call) { this.vars = vars; this.call = call; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitAssignMulti(this); }
}