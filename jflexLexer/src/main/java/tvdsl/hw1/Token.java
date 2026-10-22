package tvdsl.hw1;

public class Token {
    public final int type;
    public final String value;
    public final int line;    // номер строки
    public final int column;  // номер столбца

    public Token(int type, String value, int line, int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return String.format("Token{%s, value='%s', line=%d, column=%d}",
                TokenType.getName(type), value, line, column);
    }
}
