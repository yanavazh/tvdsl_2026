package tvdsl.hw3.types;

import java.util.List;

public class Symbol {
    public final String name;
    public final String kind;
    public Type type;
    public final boolean mutable;
    public final int line, column;

    public final List<Type> paramTypes;
    public final List<Type> returnTypes;

    public Symbol(String name, String kind, Type type, boolean mutable, int line, int column) {
        this(name, kind, type, mutable, line, column, null, null);
    }

    public Symbol(String name, List<Type> paramTypes, List<Type> returnTypes, int line, int column) {
        this(name, "func", null, false, line, column, paramTypes, returnTypes);
    }

    private Symbol(String name, String kind, Type type, boolean mutable, int line, int column, List<Type> paramTypes, List<Type> returnTypes) {
        this.name = name;
        this.kind = kind;
        this.type = type;
        this.mutable = mutable;
        this.line = line;
        this.column = column;
        this.paramTypes = paramTypes;
        this.returnTypes = returnTypes;
    }
}