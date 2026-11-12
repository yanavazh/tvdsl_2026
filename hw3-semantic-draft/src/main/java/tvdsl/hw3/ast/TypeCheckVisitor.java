package tvdsl.hw3.ast;

import tvdsl.hw3.types.*;

import java.util.ArrayList;
import java.util.List;

public class TypeCheckVisitor extends Visitor<Type> {
    private final SymbolTable table = new SymbolTable();

    public SymbolTable getTable() { return table; }

    private void error(Node node, String msg) {
        table.addError(new SemanticError(node.getLine(), node.getColumn(), msg));
    }

    private Type parseType(String t) {
        if (t == null) return null;
        return t.equals("int[]") ? Type.INT_ARRAY : Type.INT;
    }

    @Override
    public Type visitProgram(Program p) {
        table.pushScope(); // модульный scope

        for (Function f : p.functions) {
            List<Type> params = new ArrayList<>();
            for (VariableDef v : f.params) params.add(parseType(v.type));
            List<Type> rets = new ArrayList<>();
            for (VariableDef v : f.returns) rets.add(parseType(v.type));
            table.insert(new Symbol(f.name, params, rets, f.getLine(), f.getColumn()));
        }

        for (Function f : p.functions) {
            f.accept(this);
        }

        table.popScope();
        return null;
    }

    @Override
    public Type visitFunction(Function f) {
        table.pushScope();

        for (VariableDef v : f.params) {
            table.insert(new Symbol(v.name, "param", parseType(v.type), false,
                    v.getLine(), v.getColumn()));
        }

        for (Node req : f.requires) {
            req.accept(this);
        }

        for (VariableDef v : f.returns) {
            table.insert(new Symbol(v.name, "return", parseType(v.type), true,
                    v.getLine(), v.getColumn()));
        }

        for (Node ens : f.ensures) {
            ens.accept(this);
        }

        for (LocalVarDef v : f.uses) {
            table.insert(new Symbol(v.name, "local", parseType(v.type), true,
                    v.getLine(), v.getColumn()));
        }

        f.body.accept(this);

        table.popScope();
        return null;
    }

    @Override
    public Type visitBlock(Block b) {
        table.pushScope();
        for (Statement s : b.stmts) s.accept(this);
        table.popScope();
        return null;
    }

    @Override
    public Type visitAssign(Assign a) {
        Symbol s = table.lookup(a.varName);
        if (s == null) {
            error(a, "Unknown identifier '" + a.varName + "'");
            return null;
        }
        if (!s.mutable) {
            error(a, "Cannot assign to read-only '" + a.varName + "'");
        }

        Type exprType = a.expr.accept(this);
        if (exprType == Type.ERROR) return null;

        Type targetType;
        if (a.kind == Assign.Kind.ARRAY) {
            // Присваивание в b[i] — левая часть int
            if (s.type != Type.INT_ARRAY) {
                error(a, "'" + a.varName + "' is not an array");
                return null;
            }
            Type idxType = a.index.accept(this);
            if (idxType != Type.INT && idxType != Type.ERROR) {
                error(a.index, "Array index must be int, got " + idxType);
            }
            targetType = Type.INT;
        } else {
            targetType = s.type;
            if (targetType == null) {
                s.type = exprType; // вывод типа
                return null;
            }
        }

        if (targetType != exprType) {
            error(a, "Type mismatch for '" + a.varName + "': expected " + targetType
                    + ", got " + exprType);
        }
        return null;
    }

    @Override
    public Type visitIf(If i) {
        Type cond = i.condition.accept(this);
        if (cond != Type.PRED && cond != Type.ERROR) {
            error(i.condition, "Condition must be pred, got " + cond);
        }
        i.thenS.accept(this);
        if (i.elseS != null) i.elseS.accept(this);
        return null;
    }

    @Override
    public Type visitWhile(While w) {
        Type cond = w.condition.accept(this);
        if (cond != Type.PRED && cond != Type.ERROR) {
            error(w.condition, "While condition must be pred, got " + cond);
        }
        if (w.invariants != null) {
            for (Node inv : w.invariants) inv.accept(this);
        }
        w.body.accept(this);
        return null;
    }

    @Override
    public Type visitAssert(Assert a) {
        Type p = a.predicate.accept(this);
        if (p != Type.PRED && p != Type.ERROR) error(a, "Assert requires pred");
        return null;
    }

    @Override
    public Type visitAssume(Assume a) {
        Type p = a.predicate.accept(this);
        if (p != Type.PRED && p != Type.ERROR) error(a, "Assume requires pred");
        return null;
    }

    @Override
    public Type visitAssignMulti(AssignMulti a) {
        a.call.accept(this);
        return null;
    }

    @Override
    public Type visitNumber(Number n) {
        n.setType(Type.INT);
        return Type.INT;
    }

    @Override
    public Type visitVar(Var v) {
        Symbol s = table.lookup(v.name);
        if (s == null) {
            error(v, "Unknown identifier '" + v.name + "'");
            v.setType(Type.ERROR);
            return Type.ERROR;
        }
        if (s.type == null) {
            error(v, "Cannot infer type of '" + v.name + "' (never assigned)");
            v.setType(Type.ERROR);
            return Type.ERROR;
        }
        v.setType(s.type);
        return s.type;
    }

    @Override
    public Type visitBinOp(BinOp b) {
        Type lt = b.left.accept(this);
        Type rt = b.right.accept(this);
        if (lt == Type.ERROR || rt == Type.ERROR) { b.setType(Type.ERROR); return Type.ERROR; }
        if (lt != Type.INT || rt != Type.INT) {
            error(b, "Arithmetic requires int, got " + lt + " and " + rt);
            b.setType(Type.ERROR);
            return Type.ERROR;
        }
        b.setType(Type.INT);
        return Type.INT;
    }

    @Override
    public Type visitFunctionCall(FunctionCall c) {
        Symbol s = table.lookup(c.name);
        if (s == null || !"func".equals(s.kind)) {
            error(c, "Unknown function '" + c.name + "'");
            c.setType(Type.ERROR);
            return Type.ERROR;
        }
        if (c.args.size() != s.paramTypes.size()) {
            error(c, "Function '" + c.name + "' expects " + s.paramTypes.size()
                    + " args, got " + c.args.size());
            c.setType(Type.ERROR);
            return Type.ERROR;
        }
        for (int i = 0; i < c.args.size(); i++) {
            Type at = c.args.get(i).accept(this);
            Type pt = s.paramTypes.get(i);
            if (at != Type.ERROR && pt != at) {
                error(c.args.get(i), "Arg #" + (i+1) + " of '" + c.name
                        + "': expected " + pt + ", got " + at);
            }
        }
        Type ret = s.returnTypes.isEmpty() ? Type.ERROR : s.returnTypes.get(0);
        c.setType(ret);
        return ret;
    }

    @Override
    public Type visitArrayAccess(ArrayAccess a) {
        Symbol s = table.lookup(a.name);
        if (s == null) {
            error(a, "Unknown array '" + a.name + "'");
            a.setType(Type.ERROR);
            return Type.ERROR;
        }
        if (s.type != Type.INT_ARRAY) {
            error(a, "'" + a.name + "' is not an array");
            a.setType(Type.ERROR);
            return Type.ERROR;
        }
        Type idx = a.index.accept(this);
        if (idx != Type.INT && idx != Type.ERROR) {
            error(a.index, "Array index must be int, got " + idx);
        }
        a.setType(Type.INT);
        return Type.INT;
    }

    @Override
    public Type visitBoolLit(BoolLit b) { b.setType(Type.PRED); return Type.PRED; }

    @Override
    public Type visitCompare(Compare c) {
        Type lt = c.left.accept(this);
        Type rt = c.right.accept(this);
        if (lt == Type.ERROR || rt == Type.ERROR) { c.setType(Type.ERROR); return Type.ERROR; }
        if (lt != Type.INT || rt != Type.INT) {
            error(c, "Comparison requires int, got " + lt + " and " + rt);
            c.setType(Type.ERROR);
            return Type.ERROR;
        }
        c.setType(Type.PRED);
        return Type.PRED;
    }

    @Override
    public Type visitLogicOp(LogicOp l) {
        Type lt = l.left.accept(this);
        Type rt = l.right.accept(this);
        if (lt == Type.ERROR || rt == Type.ERROR) { l.setType(Type.ERROR); return Type.ERROR; }
        if (lt != Type.PRED || rt != Type.PRED) {
            error(l, "Logic op requires pred, got " + lt + " and " + rt);
            l.setType(Type.ERROR);
            return Type.ERROR;
        }
        l.setType(Type.PRED);
        return Type.PRED;
    }

    @Override
    public Type visitNot(Not n) {
        Type inner = n.inner.accept(this);
        if (inner == Type.ERROR) { n.setType(Type.ERROR); return Type.ERROR; }
        if (inner != Type.PRED) {
            error(n, "Not requires pred, got " + inner);
            n.setType(Type.ERROR);
            return Type.ERROR;
        }
        n.setType(Type.PRED);
        return Type.PRED;
    }

    @Override
    public Type visitQuantifier(Quantifier q) {
        table.pushScope();
        table.insert(new Symbol(q.variable.name, "quant", parseType(q.variable.type), false,
                q.variable.getLine(), q.variable.getColumn()));
        q.body.accept(this);
        table.popScope();
        q.setType(Type.PRED);
        return Type.PRED;
    }

    @Override
    public Type visitFormulaRef(FormulaRef f) {
        for (Expr arg : f.args) arg.accept(this);
        f.setType(Type.PRED);
        return Type.PRED;
    }

    @Override
    public Type visitVariableDef(VariableDef v) { return null; }
    @Override
    public Type visitLocalVarDef(LocalVarDef v) { return null; }
}