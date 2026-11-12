package tvdsl.hw3.ast;

public class VariableDef extends AnnotatedNode implements Node {
    public final String name;
    public final String type;
    private final int line, column;

    public VariableDef(String name, String type, int line, int column) {
        this.name = name;
        this.type = type;
        this.line = line;
        this.column = column;
    }
    @Override public <R> R accept(Visitor<R> v) {
        return v.visitVariableDef(this);
    }

    @Override public int getLine() {
        return line;
    }
    @Override public int getColumn() {
        return column;
    }
}