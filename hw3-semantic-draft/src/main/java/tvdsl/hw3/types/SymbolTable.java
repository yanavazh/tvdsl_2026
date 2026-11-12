package tvdsl.hw3.types;

import java.util.*;

public class SymbolTable {
    private final Deque<Map<String, Symbol>> scopes = new ArrayDeque<>();
    private final List<SemanticError> errors = new ArrayList<>();

    public void pushScope() {
        scopes.push(new HashMap<>());
    }
    public void popScope() {
        scopes.pop();
    }

    public void insert(Symbol s) {
        Map<String, Symbol> current = scopes.peek();
        if (current.containsKey(s.name)) {
            errors.add(new SemanticError(s.line, s.column,"Duplicate declaration of '" + s.name + "'"));
            return;
        }
        current.put(s.name, s);
    }

    public Symbol lookup(String name) {
        for (Map<String, Symbol> scope : scopes) {
            if (scope.containsKey(name)) return scope.get(name);
        }
        return null;
    }

    public void addError(SemanticError e) {
        errors.add(e);
    }
    public List<SemanticError> getErrors() {
        return errors;
    }
}