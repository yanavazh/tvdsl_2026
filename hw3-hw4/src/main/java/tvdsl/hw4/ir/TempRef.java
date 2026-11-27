package tvdsl.hw4.ir;

public class TempRef implements Operand {
    public final String name;

    public TempRef(String name) {
        this.name = name;
    }
}