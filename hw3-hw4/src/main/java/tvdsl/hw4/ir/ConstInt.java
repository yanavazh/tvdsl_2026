package tvdsl.hw4.ir;

public class ConstInt implements Operand {
    public final int value;

    public ConstInt(int value) {
        this.value = value;
    }
}