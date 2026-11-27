package tvdsl.hw4.ir;

public class VarRef implements Operand {
    public final String name;

    public VarRef(String name) {
        this.name = name;
    }
}