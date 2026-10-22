package tvdsl.hw1;

public class TokenType {
    public static final int EOF = 0;
    public static final int ERROR = 1;
    public static final int FUNCTION = 2;
    public static final int RETURNS = 3;
    public static final int USES = 4;
    public static final int IF = 5;
    public static final int WHILE = 6;
    public static final int ELSE = 7;
    public static final int TRUE = 8;
    public static final int FALSE = 9;
    public static final int NOT = 10;
    public static final int AND = 11;
    public static final int OR = 12;
    public static final int IDENT = 13;
    public static final int INT = 14;
    public static final int COMMENT = 15;
    public static final int LPAREN = 16;
    public static final int RPAREN = 17;
    public static final int COLON = 18;
    public static final int ASSIGN = 19;
    public static final int PLUS = 20;
    public static final int MULT = 21;
    public static final int SEMI = 22;
    public static final int LBRACE = 23;
    public static final int RBRACE = 24;

    // Метод для получения имени токена
    public static String getName(int type) {
        return switch (type) {
            case EOF -> "EOF";
            case ERROR -> "ERROR";
            case FUNCTION -> "FUNCTION";
            case RETURNS -> "RETURNS";
            case USES -> "USES";
            case IF -> "IF";
            case WHILE -> "WHILE";
            case ELSE -> "ELSE";
            case TRUE -> "TRUE";
            case FALSE -> "FALSE";
            case NOT -> "NOT";
            case AND -> "AND";
            case OR -> "OR";
            case IDENT -> "IDENT";
            case INT -> "INT";
            case COMMENT -> "COMMENT";
            case LPAREN -> "LPAREN";
            case RPAREN -> "RPAREN";
            case COLON -> "COLON";
            case ASSIGN -> "ASSIGN";
            case PLUS -> "PLUS";
            case MULT -> "MULT";
            case SEMI -> "SEMI";
            case LBRACE -> "LBRACE";
            case RBRACE -> "RBRACE";
            default -> "UNKNOWN";
        };
    }
}
