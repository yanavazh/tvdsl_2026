package tvdsl.hw3.ast;
import java.util.List;

public class FormulaRef extends AnnotatedNode implements Node {
    public final String name;
    public final List<Expr> args;
    public FormulaRef(String name, List<Expr> args) { this.name = name; this.args = args; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitFormulaRef(this); }

    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public int getColumn() {
        return 0;
    }
}