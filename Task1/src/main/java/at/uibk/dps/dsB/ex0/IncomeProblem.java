package at.uibk.dps.dsB.ex0;

import org.opt4j.core.start.Constant;

import javax.inject.Inject;
import java.util.Random;

/**
 * The IncomeProblem contains all values that are relevant for the problem creator.
 *
 * Problem description:
 *
 * Given the current tax situation in austria, the goal of this optimization problem is
 * to find the best gross income in order to minimize levies and maximize net income.
 *
 * @author Christoph Haas
 */
public class IncomeProblem {
    protected final Random random;
    protected final double minGrossIncome;
    protected final double maxGrossIncome;

    @Inject
    public IncomeProblem(@Constant(value = "seed", namespace = IncomeProblem.class) int seed,
                         @Constant(value = "minIncome", namespace = IncomeProblem.class) double minIncome,
                         @Constant(value = "maxIncome", namespace = IncomeProblem.class) double maxIncome) {
        this.random = new Random(seed);
        this.minGrossIncome = minIncome;
        this.maxGrossIncome = maxIncome;
    }

    public Random getRandom() {
        return random;
    }

    public double getMinGrossIncome() {
        return minGrossIncome;
    }

    public double getMaxGrossIncome() {
        return maxGrossIncome;
    }
}
