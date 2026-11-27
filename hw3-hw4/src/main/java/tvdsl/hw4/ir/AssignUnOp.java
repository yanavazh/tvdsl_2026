package tvdsl.hw4.ir;

// x := op y
public class AssignUnOp implements Instr {
    public final Operand dst;
    public final String op;
    public final Operand x;

    public AssignUnOp(Operand dst, String op, Operand x) {
        this.dst = dst;
        this.op = op;
        this.x = x;
    }
}