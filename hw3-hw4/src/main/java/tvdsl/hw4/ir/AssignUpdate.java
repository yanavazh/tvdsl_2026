package tvdsl.hw4.ir;

// x := update(a, i, y)
public class AssignUpdate implements Instr {
    public final Operand dst;
    public final Operand arr;
    public final Operand idx;
    public final Operand val;

    public AssignUpdate(Operand dst, Operand arr, Operand idx, Operand val) {
        this.dst = dst;
        this.arr = arr;
        this.idx = idx;
        this.val = val;
    }
}