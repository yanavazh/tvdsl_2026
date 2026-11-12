package tvdsl.hw3.ast;

public class Quantifier extends AnnotatedNode implements Node {
    public final String kind; // "forall" | "exists"
    public final VariableDef variable;
    public final Node body;

    public Quantifier(String kind, VariableDef variable, Node body) {
        this.kind = kind;
        this.variable = variable;
        this.body = body;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visitQuantifier(this);
    }

    @Override
    public int getLine() {
        return variable.getLine();
    }

    @Override
    public int getColumn() {
        return variable.getColumn();
    }
}