package tvdsl.hw4.opt;

import tvdsl.hw4.cfg.BasicBlock;
import tvdsl.hw4.ir.*;
import java.util.List;

public class AlgebraicSimplification {
    public static boolean optimize(List<BasicBlock> blocks) {
        boolean changed = false;
        for (BasicBlock b : blocks) {
            for (int i = 0; i < b.instrs.size(); i++) {
                Instr ins = b.instrs.get(i);

                if (ins instanceof AssignBinOp) {
                    AssignBinOp op = (AssignBinOp) ins;
                    Instr newInstr = simplify(op);

                    if (newInstr != null) {
                        b.instrs.set(i, newInstr);
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    private static Instr simplify(AssignBinOp op) {
        boolean isLeftZero = isZero(op.left);
        boolean isRightZero = isZero(op.right);
        boolean isLeftOne = isOne(op.left);
        boolean isRightOne = isOne(op.right);

        if (op.op.equals("+") && isRightZero) {
            return new AssignMov(op.dst, op.left);
        }
        if (op.op.equals("+") && isLeftZero) {
            return new AssignMov(op.dst, op.right);
        }

        if (op.op.equals("*") && (isLeftZero || isRightZero)) {
            return new AssignConst(op.dst, new ConstInt(0));
        }
        if (op.op.equals("*") && isRightOne) {
            return new AssignMov(op.dst, op.left);
        }
        if (op.op.equals("*") && isLeftOne) {
            return new AssignMov(op.dst, op.right);
        }

        if (op.op.equals("-") && isRightZero) {
            return new AssignMov(op.dst, op.left);
        }
        if (op.op.equals("-") && operandEquals(op.left, op.right)) {
            return new AssignConst(op.dst, new ConstInt(0));
        }

        if (op.op.equals("/") && isRightOne) {
            return new AssignMov(op.dst, op.left);
        }

        return null;
    }

    // Проверка, что операнд — это константа 0
    private static boolean isZero(Operand o) {
        return o instanceof ConstInt && ((ConstInt) o).value == 0;
    }

    // Проверка, что операнд — это константа 1
    private static boolean isOne(Operand o) {
        return o instanceof ConstInt && ((ConstInt) o).value == 1;
    }

    // Проверка, что два операнда — это одна и та же переменная (например, x - x)
    private static boolean operandEquals(Operand a, Operand b) {
        if (a instanceof VarRef && b instanceof VarRef) {
            return ((VarRef) a).name.equals(((VarRef) b).name);
        }
        if (a instanceof TempRef && b instanceof TempRef) {
            return ((TempRef) a).name.equals(((TempRef) b).name);
        }
        return false;
    }
}