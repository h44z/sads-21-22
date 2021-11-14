package at.uibk.dps.dsB.ex0.modules;

import at.uibk.dps.dsB.ex0.IncomeProblem;
import org.opt4j.benchmarks.knapsack.KnapsackModule;
import org.opt4j.benchmarks.knapsack.KnapsackProblemRandom;
import org.opt4j.benchmarks.queens.QueensProblem;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.problem.ProblemModule;

import at.uibk.dps.dsB.ex0.creators.MyFirstCreator;
import at.uibk.dps.dsB.ex0.decoders.MyFirstDecoder;
import at.uibk.dps.dsB.ex0.evaluators.MyFirstEvaluator;
import org.opt4j.core.start.Constant;

import javax.inject.Inject;
import java.util.Random;

/**
 * In general, Opt4J's modules are used to configure the binding of interfaces to
 * concrete classes. In this case, we are binding the Creator, Decoder, and
 * Evaluator interfaces to the classes defined throughout the Exercise0 project.
 * 
 * You don't have to alter anything within this class.
 * 
 * @author Christoph Haas
 */
@Info("Net income maximisation Problem Suite.")
public class MyFirstProblemModule extends ProblemModule {
	@Info("Annual minimal gross income.")
	@Order(0)
	@Constant(value = "minIncome", namespace = IncomeProblem.class)
	protected double minGrossIncome = 20000.0;

	@Info("Annual maximal gross income.")
	@Order(1)
	@Constant(value = "maxIncome", namespace = IncomeProblem.class)
	protected double maxGrossIncome = 600000.0;

	@Info("The seed of the problem generator.")
	@Order(2)
	@Constant(value = "seed", namespace = IncomeProblem.class)
	protected int seed = 0;

	@Override
	protected void config() {
		bind(IncomeProblem.class).in(SINGLETON);

		bindProblem(MyFirstCreator.class, MyFirstDecoder.class, MyFirstEvaluator.class);
	}

	public double getMinGrossIncome() {
		return minGrossIncome;
	}

	public void setMinGrossIncome(double minGrossIncome) {
		this.minGrossIncome = minGrossIncome;
	}

	public double getMaxGrossIncome() {
		return maxGrossIncome;
	}

	public void setMaxGrossIncome(double maxGrossIncome) {
		this.maxGrossIncome = maxGrossIncome;
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}
}
