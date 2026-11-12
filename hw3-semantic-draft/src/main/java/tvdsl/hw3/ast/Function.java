package tvdsl.hw3.ast;

import java.util.List;

public class Function extends AnnotatedNode implements Node {
    public final String name;
    public final List<VariableDef> params;
    public final List<Node> requires;
    public final List<VariableDef> returns;
    public final List<Node> ensures;
    public final List<LocalVarDef> uses;
    public final Statement body;
    public final int line, column;

    public Function(String name,
                    List<VariableDef> params,
                    List<Node> requires,
                    List<VariableDef> returns,
                    List<Node> ensures,
                    List<LocalVarDef> uses,
                    Statement body,
                    int line, int column) {
        this.name = name;
        this.params = params;
        this.requires = requires;
        this.returns = returns;
        this.ensures = ensures;
        this.uses = uses;
        this.body = body;
        this.line = line;
        this.column = column;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visitFunction(this);
    }

    @Override public int getLine() { return line; }
    @Override public int getColumn() { return column; }
}