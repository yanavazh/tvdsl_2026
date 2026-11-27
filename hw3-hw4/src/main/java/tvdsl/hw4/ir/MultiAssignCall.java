package tvdsl.hw4.ir;

import java.util.List;

// x_1, ..., x_m := call f(...)
public class MultiAssignCall implements Instr {
    public final List<Operand> dsts;
    public final String func;
    public final List<Operand> args;

    public MultiAssignCall(List<Operand> dsts, String func, List<Operand> args) {
        this.dsts = dsts;
        this.func = func;
        this.args = args;
    }
}