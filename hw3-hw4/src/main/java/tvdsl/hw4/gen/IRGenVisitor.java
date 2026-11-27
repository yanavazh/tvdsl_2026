package tvdsl.hw4.gen;

import tvdsl.hw3.ast.*;
import tvdsl.hw4.cfg.BasicBlock;
import tvdsl.hw4.ir.*;

import java.util.ArrayList;
import java.util.List;

public class IRGenVisitor extends Visitor<Void> {
    private final List<BasicBlock> blocks = new ArrayList<>();
    private BasicBlock current;
    private int lblId = 0, tmpId = 0;
    private Operand last;

    public List<BasicBlock> getBlocks() {
        return blocks;
    }

    private BasicBlock newBlock() {
        BasicBlock b = new BasicBlock("B" + lblId);
        lblId++;
        blocks.add(b);
        return b;
    }

    private Operand newTempOperand() {
        Operand t = new TempRef("t" + tmpId);
        tmpId++;
        return t;
    }

    private void emit(Instr i) {
        current.instrs.add(i);
    }

    private void switchTo(BasicBlock next) {
        if (current != null && current != next && !current.hasTerminator()) {
            current.addSucc(next);
        }
        current = next;
    }

    private void gotoIfNeeded(BasicBlock target) {
        if (!current.hasTerminator()) {
            emit(new Goto(target.label));
            current.addSucc(target);
        }
    }

    private Operand genExpr(Node e) {
        last = null;
        e.accept(this);
        return last;
    }

    @Override
    public Void visitProgram(Program p) {
        for (Function f : p.functions) {
            f.accept(this);
        }
        return null;
    }

    @Override
    public Void visitFunction(Function f) {
        current = newBlock();
        f.body.accept(this);
        List<Operand> rets = new ArrayList<>();
        for (VariableDef v : f.returns) {
            rets.add(new VarRef(v.name));
        }
        emit(new Return(rets));
        return null;
    }

    @Override
    public Void visitBlock(Block b) {
        for (Statement s : b.stmts) {
            s.accept(this);
        }
        return null;
    }

    @Override
    public Void visitAssign(Assign a) {
        Operand v = genExpr(a.expr);
        if (a.kind == Assign.Kind.ARRAY) {
            Operand idx = genExpr(a.index);
            emit(new AssignUpdate(new VarRef(a.varName), new VarRef(a.varName), idx, v));
        } else {
            emit(new AssignMov(new VarRef(a.varName), v));
        }
        return null;
    }

    @Override
    public Void visitAssignMulti(AssignMulti a) {
        List<Operand> args = new ArrayList<>();
        for (Expr e : a.call.args) {
            args.add(genExpr(e));
        }
        List<Operand> dsts = new ArrayList<>();
        for (String v : a.vars) {
            dsts.add(new VarRef(v));
        }
        emit(new MultiAssignCall(dsts, a.call.name, args));
        return null;
    }

    @Override
    public Void visitIf(If i) {
        Operand c = genExpr(i.condition);
        BasicBlock thenB = newBlock();
        BasicBlock elseB = null;
        if (i.elseS != null) {
            elseB = newBlock();
        }
        BasicBlock endB = newBlock();

        emit(new IfGoto(c, thenB.label));
        current.addSucc(thenB);
        if (elseB != null) {
            emit(new Goto(elseB.label));
            current.addSucc(elseB);
        } else {
            emit(new Goto(endB.label));
            current.addSucc(endB);
        }

        switchTo(thenB);
        i.thenS.accept(this);
        gotoIfNeeded(endB);

        if (elseB != null) {
            switchTo(elseB);
            i.elseS.accept(this);
            gotoIfNeeded(endB);
        }

        switchTo(endB);
        return null;
    }

    @Override
    public Void visitWhile(While w) {
        BasicBlock condB = newBlock();
        BasicBlock bodyB = newBlock();
        BasicBlock endB = newBlock();

        emit(new Goto(condB.label));
        current.addSucc(condB);

        switchTo(condB);
        Operand c = genExpr(w.condition);
        emit(new IfGoto(c, bodyB.label));
        current.addSucc(bodyB);
        emit(new Goto(endB.label));
        current.addSucc(endB);

        switchTo(bodyB);
        w.body.accept(this);
        gotoIfNeeded(condB);

        switchTo(endB);
        return null;
    }

    @Override
    public Void visitNumber(tvdsl.hw3.ast.Number n) {
        last = new ConstInt(n.value);
        return null;
    }

    @Override
    public Void visitBoolLit(BoolLit b) {
        last = new ConstBool(b.value);
        return null;
    }

    @Override
    public Void visitVar(Var v) {
        last = new VarRef(v.name);
        return null;
    }

    @Override
    public Void visitBinOp(BinOp b) {
        Operand l = genExpr(b.left);
        Operand r = genExpr(b.right);
        Operand t = newTempOperand();
        emit(new AssignBinOp(t, b.op, l, r));
        last = t;
        return null;
    }

    @Override
    public Void visitCompare(Compare c) {
        Operand l = genExpr(c.left);
        Operand r = genExpr(c.right);
        Operand t = newTempOperand();
        emit(new AssignBinOp(t, "cmp_" + c.op, l, r));
        last = t;
        return null;
    }

    @Override
    public Void visitLogicOp(LogicOp l) {
        Operand a = genExpr(l.left);
        Operand b = genExpr(l.right);
        Operand t = newTempOperand();
        emit(new AssignBinOp(t, l.op, a, b));
        last = t;
        return null;
    }

    @Override
    public Void visitNot(Not n) {
        Operand x = genExpr(n.inner);
        Operand t = newTempOperand();
        emit(new AssignUnOp(t, "not", x));
        last = t;
        return null;
    }

    @Override
    public Void visitArrayAccess(ArrayAccess a) {
        Operand idx = genExpr(a.index);
        Operand t = newTempOperand();
        emit(new AssignLoad(t, new VarRef(a.name), idx));
        last = t;
        return null;
    }

    @Override
    public Void visitFunctionCall(FunctionCall c) {
        List<Operand> args = new ArrayList<>();
        for (Expr e : c.args) {
            args.add(genExpr(e));
        }
        Operand t = newTempOperand();
        emit(new AssignCall(t, c.name, args));
        last = t;
        return null;
    }

    @Override
    public Void visitQuantifier(Quantifier q) {
        last = new ConstBool(true);
        return null;
    }

    @Override
    public Void visitFormulaRef(FormulaRef f) {
        last = new ConstBool(true);
        return null;
    }

    @Override
    public Void visitAssert(Assert a) {
        return null;
    }

    @Override
    public Void visitAssume(Assume a) {
        return null;
    }

    @Override
    public Void visitVariableDef(VariableDef v) {
        return null;
    }

    @Override
    public Void visitLocalVarDef(LocalVarDef v) {
        return null;
    }
}