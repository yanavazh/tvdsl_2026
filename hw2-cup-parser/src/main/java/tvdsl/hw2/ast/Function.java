package tvdsl.hw2.ast;

import java.util.List;

public class Function implements Node {
    public final String name;
    public final List<VariableDef> params;
    public final List<VariableDef> returns;
    public final List<LocalVarDef> uses;
    public final Statement body;

    public Function(String name, List<VariableDef> params, List<VariableDef> returns, List<LocalVarDef> uses, Statement body) {
        this.name = name;
        this.params = params;
        this.returns = returns;
        this.uses = uses;
        this.body = body;
    }
    @Override public <R> R accept(Visitor<R> v) { return v.visitFunction(this); }
}