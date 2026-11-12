package tvdsl.hw3.ast;

import tvdsl.hw3.types.Type;

public abstract class AnnotatedNode implements Node {
    private Type type;

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}