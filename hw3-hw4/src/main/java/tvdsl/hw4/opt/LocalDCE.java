package tvdsl.hw4.opt;

import tvdsl.hw4.cfg.BasicBlock;
import tvdsl.hw4.ir.*;
import java.util.List;

public class LocalDCE {
    public static boolean optimize(List<BasicBlock> blocks) {
        boolean changed = false;
        for (BasicBlock b : blocks) {
            for (int i = b.instrs.size() - 1; i >= 0; i--) {
                Instr ins = b.instrs.get(i);

                if (canRemove(ins, b.instrs)) {
                    b.instrs.remove(i);
                    changed = true;
                }
            }
        }
        return changed;
    }

    private static boolean canRemove(Instr ins, List<Instr> blockInstrs) {
        if (ins instanceof AssignCall || ins instanceof MultiAssignCall ||
                ins instanceof AssignUpdate || ins instanceof IfGoto ||
                ins instanceof Goto || ins instanceof Return) {
            return false;
        }

        Operand dst = getDst(ins);
        if (dst == null) return false;

        return !isUsedLater(dst, blockInstrs);
    }

    private static Operand getDst(Instr ins) {
        if (ins instanceof AssignConst) return ((AssignConst) ins).dst;
        if (ins instanceof AssignMov) return ((AssignMov) ins).dst;
        if (ins instanceof AssignBinOp) return ((AssignBinOp) ins).dst;
        if (ins instanceof AssignUnOp) return ((AssignUnOp) ins).dst;
        if (ins instanceof AssignLoad) return ((AssignLoad) ins).dst;
        return null;
    }

    private static boolean isUsedLater(Operand dst, List<Instr> instrs) {
        for (Instr ins : instrs) {
            if (ins instanceof AssignBinOp) {
                AssignBinOp op = (AssignBinOp) ins;
                if (operandEquals(op.left, dst) || operandEquals(op.right, dst)) return true;
            } else if (ins instanceof AssignMov) {
                if (operandEquals(((AssignMov) ins).src, dst)) return true;
            } else if (ins instanceof AssignUnOp) {
                if (operandEquals(((AssignUnOp) ins).x, dst)) return true;
            } else if (ins instanceof IfGoto) {
                if (operandEquals(((IfGoto) ins).cond, dst)) return true;
            } else if (ins instanceof Return) {
                for (Operand v : ((Return) ins).vals) {
                    if (operandEquals(v, dst)) return true;
                }
            } else if (ins instanceof AssignUpdate) {
                AssignUpdate up = (AssignUpdate) ins;
                if (operandEquals(up.arr, dst) || operandEquals(up.idx, dst) || operandEquals(up.val, dst)) return true;
            } else if (ins instanceof AssignLoad) {
                AssignLoad ld = (AssignLoad) ins;
                if (operandEquals(ld.arr, dst) || operandEquals(ld.idx, dst)) return true;
            } else if (ins instanceof AssignCall) {
                for (Operand arg : ((AssignCall) ins).args) {
                    if (operandEquals(arg, dst)) return true;
                }
            }
        }
        return false;
    }

    private static boolean operandEquals(Operand a, Operand b) {
        if (a instanceof VarRef && b instanceof VarRef)
            return ((VarRef) a).name.equals(((VarRef) b).name);
        if (a instanceof TempRef && b instanceof TempRef)
            return ((TempRef) a).name.equals(((TempRef) b).name);
        return false;
    }
}