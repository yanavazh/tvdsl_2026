package tvdsl.hw4.ir;

// x := y op z
public class AssignBinOp implements Instr {
    public final Operand dst;
    public final String op;
    public final Operand left;
    public final Operand right;

    public AssignBinOp(Operand dst, String op, Operand left, Operand right) {
        this.dst = dst;
        this.op = op;
        this.left = left;
        this.right = right;
    }
}