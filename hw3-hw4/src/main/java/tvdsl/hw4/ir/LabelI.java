package tvdsl.hw4.ir;

// L:
public class LabelI implements Instr {
    public final String label;

    public LabelI(String label) {
        this.label = label;
    }
}