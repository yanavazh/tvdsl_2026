package tvdsl.hw2.ast;

public abstract class Visitor<R> {
    public R visitProgram(Program p) { throw new UnsupportedOperationException(); }
    public R visitFunction(Function f) { throw new UnsupportedOperationException(); }
    public R visitVariableDef(VariableDef v) { throw new UnsupportedOperationException(); }
    public R visitLocalVarDef(LocalVarDef v) { throw new UnsupportedOperationException(); }

    public R visitAssign(Assign a) { throw new UnsupportedOperationException(); }
    public R visitIf(If i) { throw new UnsupportedOperationException(); }
    public R visitWhile(While w) { throw new UnsupportedOperationException(); }
    public R visitBlock(Block b) { throw new UnsupportedOperationException(); }

    public R visitNumber(Number n) { throw new UnsupportedOperationException(); }
    public R visitVar(Var v) { throw new UnsupportedOperationException(); }
    public R visitBinOp(BinOp b) { throw new UnsupportedOperationException(); }
    public R visitFunctionCall(FunctionCall c) { throw new UnsupportedOperationException(); }
    public R visitArrayAccess(ArrayAccess a) { throw new UnsupportedOperationException(); }

    public R visitBoolLit(BoolLit b) { throw new UnsupportedOperationException(); }
    public R visitCompare(Compare c) { throw new UnsupportedOperationException(); }
    public R visitLogicOp(LogicOp l) { throw new UnsupportedOperationException(); }
    public R visitNot(Not n) { throw new UnsupportedOperationException(); }

    public R visitAssert(Assert a) { throw new UnsupportedOperationException(); }
    public R visitAssume(Assume a) { throw new UnsupportedOperationException(); }
    public R visitQuantifier(Quantifier q) { throw new UnsupportedOperationException(); }
    public R visitFormulaRef(FormulaRef f) { throw new UnsupportedOperationException(); }
    public R visitAssignMulti(AssignMulti a) { throw new UnsupportedOperationException(); }
}