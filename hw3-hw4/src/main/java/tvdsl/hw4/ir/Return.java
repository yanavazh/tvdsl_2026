package tvdsl.hw4.ir;

import java.util.List;

// return x_1, ..., x_m
public class Return implements Instr {
    public final List<Operand> vals;
    public Return(List<Operand> vals) { this.vals = vals; }

    @Override public boolean isTerminator() { return true; }
}