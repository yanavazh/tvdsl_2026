package tvdsl.hw2.ast;

public class LocalVarDef implements Node {
    public final String name;
    public final String type; // может быть null
    public LocalVarDef(String name, String type) { this.name = name; this.type = type; }
    @Override public <R> R accept(Visitor<R> v) { return v.visitLocalVarDef(this); }
}