package tvdsl.hw4.ir;

// goto L
public class Goto implements Instr {
    public final String label;

    public Goto(String label) {
        this.label = label;
    }

    @Override public boolean isTerminator() {
        return true;
    }
}