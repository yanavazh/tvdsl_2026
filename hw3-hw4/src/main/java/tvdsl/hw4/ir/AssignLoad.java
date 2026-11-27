package tvdsl.hw4.ir;

// x := load a[i]
public class AssignLoad implements Instr {
    public final Operand dst;
    public final Operand arr;
    public final Operand idx;

    public AssignLoad(Operand dst, Operand arr, Operand idx) {
        this.dst = dst;
        this.arr = arr;
        this.idx = idx;
    }
}