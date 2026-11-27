package tvdsl.hw4.ir;

import tvdsl.hw4.cfg.BasicBlock;
import java.util.List;

public class IRPrinter {

    public static String operand(Operand o) {
        return switch (o) {
            case ConstInt c -> String.valueOf(c.value);
            case ConstBool b -> String.valueOf(b.value);
            case VarRef v -> v.name;
            case TempRef t -> t.name;
            default -> "???";
        };
    }

    public static String instr(Instr i) {
        return switch (i) {
            case AssignConst a -> operand(a.dst) + " := " + operand(a.c);
            case AssignMov a -> operand(a.dst) + " := " + operand(a.src);
            case AssignBinOp a -> operand(a.dst) + " := " + operand(a.left) + " " + a.op + " " + operand(a.right);
            case AssignUnOp a -> operand(a.dst) + " := " + a.op + " " + operand(a.x);

            case AssignLoad a -> operand(a.dst) + " := load " + operand(a.arr) + "[" + operand(a.idx) + "]";
            case AssignUpdate a -> operand(a.dst) + " := update(" + operand(a.arr) + ", " + operand(a.idx) + ", " + operand(a.val) + ")";

            case AssignCall a -> {
                StringBuilder sb = new StringBuilder();
                sb.append(operand(a.dst)).append(" := call ").append(a.func).append("(");
                for (int k = 0; k < a.args.size(); k++) {
                    if (k > 0) sb.append(", ");
                    sb.append(operand(a.args.get(k)));
                }
                yield sb.append(")").toString();
            }

            case MultiAssignCall a -> {
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < a.dsts.size(); k++) {
                    if (k > 0) sb.append(", ");
                    sb.append(operand(a.dsts.get(k)));
                }
                sb.append(" := call ").append(a.func).append("(");
                for (int k = 0; k < a.args.size(); k++) {
                    if (k > 0) sb.append(", ");
                    sb.append(operand(a.args.get(k)));
                }
                yield sb.append(")").toString();
            }

            case IfGoto a -> "if " + operand(a.cond) + " goto " + a.label;
            case Goto a -> "goto " + a.label;
            case LabelI a -> a.label + ":";

            case Return a -> {
                StringBuilder sb = new StringBuilder("return");
                for (int k = 0; k < a.vals.size(); k++) {
                    sb.append(k == 0 ? " " : ", ").append(operand(a.vals.get(k)));
                }
                yield sb.toString();
            }

            default -> "???";
        };
    }

    public static String printBlock(BasicBlock b) {
        StringBuilder sb = new StringBuilder();
        sb.append(b.label).append(":");
        if (!b.succ.isEmpty()) {
            sb.append(" -> ");
            for (int k = 0; k < b.succ.size(); k++) {
                if (k > 0) sb.append(", ");
                sb.append(b.succ.get(k).label);
            }
        }
        sb.append("\n");
        for (Instr i : b.instrs) {
            sb.append("  ").append(instr(i)).append("\n");
        }
        return sb.toString();
    }

    public static String printProgram(List<BasicBlock> blocks) {
        StringBuilder sb = new StringBuilder();
        for (BasicBlock b : blocks) {
            sb.append(printBlock(b)).append("\n");
        }
        return sb.toString();
    }
}