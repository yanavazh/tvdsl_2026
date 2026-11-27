package tvdsl.hw4.opt;

import tvdsl.hw4.cfg.BasicBlock;
import tvdsl.hw4.ir.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantPropagation {
    public static boolean optimize(List<BasicBlock> blocks) {
        boolean changed = false;
        for (BasicBlock b : blocks) {
            Map<String, Operand> env = new HashMap<>();

            for (int i = 0; i < b.instrs.size(); i++) {
                Instr ins = b.instrs.get(i);

                switch (ins) {
                    case AssignBinOp assignBinOp -> {
                        AssignBinOp binOp = assignBinOp;

                        Operand newLeft = getConstOrVar(binOp.left, env);
                        Operand newRight = getConstOrVar(binOp.right, env);

                        if (newLeft != binOp.left || newRight != binOp.right) {
                            b.instrs.set(i, new AssignBinOp(binOp.dst, binOp.op, newLeft, newRight));
                            changed = true;
                            binOp = (AssignBinOp) b.instrs.get(i);
                        }

                        if (binOp.left instanceof ConstInt && binOp.right instanceof ConstInt) {
                            int l = ((ConstInt) binOp.left).value;
                            int r = ((ConstInt) binOp.right).value;
                            int res = calculate(binOp.op, l, r);
                            Operand c = new ConstInt(res);

                            b.instrs.set(i, new AssignConst(binOp.dst, c));
                            addToEnv(binOp.dst, c, env);
                            changed = true;
                        } else {
                            removeFromEnv(binOp.dst, env);
                        }
                    }
                    case AssignConst c -> addToEnv(c.dst, c.c, env);
                    case AssignMov mov -> {
                        Operand newSrc = getConstOrVar(mov.src, env);

                        if (newSrc != mov.src) {
                            b.instrs.set(i, new AssignMov(mov.dst, newSrc));
                            changed = true;
                        }

                        if (newSrc instanceof ConstInt || newSrc instanceof ConstBool) {
                            addToEnv(mov.dst, newSrc, env);
                        } else {
                            removeFromEnv(mov.dst, env);
                        }
                    }
                    case null, default -> {
                        if (ins instanceof AssignCall) {
                            removeFromEnv(((AssignCall) ins).dst, env);
                        } else if (ins instanceof AssignLoad) {
                            removeFromEnv(((AssignLoad) ins).dst, env);
                        } else if (ins instanceof AssignUnOp) {
                            removeFromEnv(((AssignUnOp) ins).dst, env);
                        }
                    }
                }
            }
        }
        return changed;
    }

    private static Operand getConstOrVar(Operand op, Map<String, Operand> env) {
        if (op instanceof VarRef || op instanceof TempRef) {
            String name = (op instanceof VarRef) ? ((VarRef) op).name : ((TempRef) op).name;
            if (env.containsKey(name)) {
                return env.get(name);
            }
        }
        return op;
    }

    private static void addToEnv(Operand dst, Operand c, Map<String, Operand> env) {
        if (dst instanceof VarRef) env.put(((VarRef) dst).name, c);
        if (dst instanceof TempRef) env.put(((TempRef) dst).name, c);
    }

    private static void removeFromEnv(Operand dst, Map<String, Operand> env) {
        if (dst instanceof VarRef) env.remove(((VarRef) dst).name);
        if (dst instanceof TempRef) env.remove(((TempRef) dst).name);
    }

    private static int calculate(String op, int l, int r) {
        switch (op) {
            case "+": return l + r;
            case "-": return l - r;
            case "*": return l * r;
            case "/": return l / r;
            default: return 0;
        }
    }
}