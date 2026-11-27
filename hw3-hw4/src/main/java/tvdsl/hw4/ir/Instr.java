package tvdsl.hw4.ir;

public interface Instr {
    default boolean isTerminator() {
        return false;
    }
}