package tvdsl.hw3.ast;

import tvdsl.hw3.types.Type;

public interface Node {
    <R> R accept(Visitor<R> v);
    int getLine();
    int getColumn();

    Type getType();
    void setType(Type type);
}