package tvdsl.hw2;

public class TokenType {
    public static final int FUNCTION = 1;
    public static final int RETURNS = 2;
    public static final int USES = 3;
    public static final int IF = 4;
    public static final int WHILE = 5;
    public static final int ELSE = 6;
    public static final int TRUE = 7;
    public static final int FALSE = 8;
    public static final int NOT = 9;
    public static final int AND = 10;
    public static final int OR = 11;
    public static final int IDENT = 12;
    public static final int INT = 13;
    public static final int COMMENT = 14;
    public static final int LPAREN = 15;
    public static final int RPAREN = 16;
    public static final int COLON = 17;
    public static final int ASSIGN = 18;
    public static final int PLUS = 19;
    public static final int MULT = 20;
    public static final int SEMI = 21;
    public static final int LBRACE = 22;
    public static final int RBRACE = 23;

    public static String getName(int type) {
        return switch (type) {
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
