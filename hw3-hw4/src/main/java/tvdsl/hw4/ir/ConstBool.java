package tvdsl.hw4.ir;

public class ConstBool implements Operand {
    public final boolean value;

    public ConstBool(boolean value) {
        this.value = value;
    }
}