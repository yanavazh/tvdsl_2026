package tvdsl.hw4.ir;

import java.util.List;

// x := call f(...)
public class AssignCall implements Instr {
    public final Operand dst;
    public final String func;
    public final List<Operand> args;

    public AssignCall(Operand dst, String func, List<Operand> args) {
        this.dst = dst;
        this.func = func;
        this.args = args;
    }
}