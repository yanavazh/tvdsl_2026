package tvdsl.hw2.ast;

public interface Node {
    <R> R accept(Visitor<R> v);
}