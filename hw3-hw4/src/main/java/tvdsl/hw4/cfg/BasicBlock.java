package tvdsl.hw4.cfg;

import tvdsl.hw4.ir.Instr;
import java.util.ArrayList;
import java.util.List;

public class BasicBlock {
    public final String label;
    public final List<Instr> instrs = new ArrayList<>();
    public final List<BasicBlock> succ = new ArrayList<>();
    public final List<BasicBlock> pred = new ArrayList<>();

    public BasicBlock(String label) {
        this.label = label;
    }

    public boolean hasTerminator() {
        if (instrs.isEmpty()) return false;
        Instr last = instrs.getLast();
        return last.isTerminator();
    }

    public void addSucc(BasicBlock b) {
        if (succ.contains(b)) return;
        succ.add(b);
        b.pred.add(this);
    }
}