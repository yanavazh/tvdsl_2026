package tvdsl.hw4.ir;

// if x goto L
public class IfGoto implements Instr {
    public final Operand cond;
    public final String label;
    public IfGoto(Operand cond, String label) {
        this.cond = cond;
        this.label = label;
    }

    @Override public boolean isTerminator() {
        return true;
    }
}