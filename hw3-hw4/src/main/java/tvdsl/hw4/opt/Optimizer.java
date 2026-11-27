package tvdsl.hw4.opt;

import tvdsl.hw4.cfg.BasicBlock;
import java.util.List;

public class Optimizer {
    public static void optimize(List<BasicBlock> blocks) {
        boolean changed = true;
        while (changed) {
            boolean c1 = ConstantPropagation.optimize(blocks);
            boolean c2 = AlgebraicSimplification.optimize(blocks);
            boolean c3 = LocalDCE.optimize(blocks);
            changed = c1 || c2 || c3;
        }
    }
}