package tvdsl.hw2.ast;

public class VariableDef implements Node {
    public final String name;
    public final String type;
    public VariableDef(String name, String type) { this.name = name; this.type = type; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitVariableDef(this); }
}