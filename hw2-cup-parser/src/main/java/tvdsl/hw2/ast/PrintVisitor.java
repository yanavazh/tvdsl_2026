package tvdsl.hw2.ast;

import java.util.List;

public class PrintVisitor extends Visitor<Void> {
    private final StringBuilder sb = new StringBuilder();
    private int depth = 0;

    public String result() { return sb.toString(); }

    private void line(String text) {
        for (int i = 0; i < depth; i++) sb.append("  ");
        sb.append(text).append('\n');
    }

    @Override
    public Void visitProgram(Program p) {
        line("Program");
        depth++; p.func.accept(this); depth--;
        return null;
    }

    @Override
    public Void visitFunction(Function f) {
        line("Function " + f.name);
        depth++;
        line("params:");
        depth++;
        for (VariableDef v : f.params) v.accept(this);
        depth--;

        line("returns:");
        depth++;
        for (VariableDef v : f.returns) v.accept(this);
        depth--;

        if (!f.uses.isEmpty()) {
            line("uses:");
            depth++;
            for (LocalVarDef v : f.uses) v.accept(this);
            depth--;
        }

        line("body:");
        depth++; f.body.accept(this); depth--;
        depth--;
        return null;
    }

    @Override
    public Void visitVariableDef(VariableDef v) {
        line("VarDef " + v.name + " : " + v.type);
        return null;
    }

    @Override
    public Void visitLocalVarDef(LocalVarDef v) {
        line("LocalVarDef " + v.name + (v.type != null ? " : " + v.type : ""));
        return null;
    }

    @Override
    public Void visitAssign(Assign a) {
        line("Assign[" + a.kind + "] " + a.varName);
        depth++;
        if (a.index != null) {
            line("index:");
            depth++; a.index.accept(this); depth--;
        }
        line("value:");
        depth++; a.expr.accept(this); depth--;
        depth--;
        return null;
    }

    @Override
    public Void visitAssignMulti(AssignMulti a) {
        line("AssignMulti vars: " + a.vars);
        depth++;
        a.call.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void visitIf(If i) {
        line("If");
        depth++;
        line("cond:");
        depth++; i.condition.accept(this); depth--;
        line("then:");
        depth++; i.thenS.accept(this); depth--;
        if (i.elseS != null) {
            line("else:");
            depth++; i.elseS.accept(this); depth--;
        }
        depth--;
        return null;
    }

    @Override
    public Void visitWhile(While w) {
        line("While");
        depth++;
        line("cond:");
        depth++; w.condition.accept(this); depth--;
        if (w.invariants != null && !w.invariants.isEmpty()) {
            line("invariant:");
            depth++;
            for (Node inv : w.invariants) inv.accept(this);
            depth--;
        }
        line("body:");
        depth++; w.body.accept(this); depth--;
        depth--;
        return null;
    }

    @Override
    public Void visitBlock(Block b) {
        line("Block");
        depth++;
        for (Statement s : b.stmts) s.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void visitAssert(Assert a) {
        line("Assert");
        depth++; a.predicate.accept(this); depth--;
        return null;
    }

    @Override
    public Void visitAssume(Assume a) {
        line("Assume");
        depth++; a.predicate.accept(this); depth--;
        return null;
    }

    @Override
    public Void visitNumber(Number n) {
        line("Num " + n.value);
        return null;
    }

    @Override
    public Void visitVar(Var v) {
        line("Var " + v.name);
        return null;
    }

    @Override
    public Void visitBinOp(BinOp b) {
        line("BinOp " + b.op);
        depth++; b.left.accept(this); b.right.accept(this); depth--;
        return null;
    }

    @Override
    public Void visitFunctionCall(FunctionCall c) {
        line("Call " + c.name);
        depth++;
        for (Expr e : c.args) e.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void visitArrayAccess(ArrayAccess a) {
        line("ArrayAccess " + a.name);
        depth++; a.index.accept(this); depth--;
        return null;
    }

    @Override
    public Void visitBoolLit(BoolLit b) {
        line("Bool " + b.value);
        return null;
    }

    @Override
    public Void visitCompare(Compare c) {
        line("Compare " + c.op);
        depth++; c.left.accept(this); c.right.accept(this); depth--;
        return null;
    }

    @Override
    public Void visitLogicOp(LogicOp l) {
        line("Logic " + l.op);
        depth++; l.left.accept(this); l.right.accept(this); depth--;
        return null;
    }

    @Override
    public Void visitNot(Not n) {
        line("Not");
        depth++; n.inner.accept(this); depth--;
        return null;
    }

    @Override
    public Void visitQuantifier(Quantifier q) {
        line("Quant " + q.kind);
        depth++;
        q.variable.accept(this);
        q.body.accept(this);
        depth--;
        return null;
    }

    @Override
    public Void visitFormulaRef(FormulaRef f) {
        line("FormulaRef " + f.name);
        depth++;
        for (Expr e : f.args) e.accept(this);
        depth--;
        return null;
    }
}