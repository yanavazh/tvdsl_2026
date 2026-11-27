package tvdsl.hw4.ir;

// x := y
public class AssignMov implements Instr {
    public final Operand dst;
    public final Operand src;

    public AssignMov(Operand dst, Operand src) {
        this.dst = dst;
        this.src = src;
    }
}