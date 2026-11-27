package tvdsl.hw4.ir;

// x := c
public class AssignConst implements Instr {
    public final Operand dst;
    public final Operand c;
    public AssignConst(Operand dst, Operand c) {
        this.dst = dst;
        this.c = c;
    }
}