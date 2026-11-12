package tvdsl.hw3.types;

public class SemanticError {
    public final int line, column;
    public final String message;

    public SemanticError(int line, int column, String message) {
        this.line = line; this.column = column; this.message = message;
    }
    @Override public String toString() {
        return "Error at line " + line + ", col " + column + ": " + message;
    }
}