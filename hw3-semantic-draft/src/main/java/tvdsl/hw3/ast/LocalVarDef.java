package tvdsl.hw3.ast;

public class LocalVarDef extends AnnotatedNode implements Node {
    public final String name;
    public final String type; // может быть null
    private final int line, column;

    public LocalVarDef(String name, String type, int line, int column) {
        this.name = name;
        this.type = type;
        this.line = line;
        this.column = column;
    }
    @Override public <R> R accept(Visitor<R> v) {
        return v.visitLocalVarDef(this);
    }

    @Override public int getLine() {
        return line;
    }
    @Override public int getColumn() {
        return column;
    }
}