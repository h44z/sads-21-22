package at.uibk.dps.dsB.ex0.evaluators;

import at.uibk.dps.dsB.ex0.decoders.IncomeObject;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;

/**
 * The {@link Evaluator} class which will be used to evaluate the phenotypes
 * returned by the {@link Decoder}.
 * 
 * @author Christoph Haas
 *
 */
public class MyFirstEvaluator implements Evaluator<Object> {

	protected final Objective myObjective = new Objective("Objective to maximize", Sign.MAX);

	@Override
	public Objectives evaluate(Object phenotype) {
		// No need to do anything in this method
		double fitness = calculatePhenotypeFitness(phenotype);
		Objectives result = new Objectives();
		result.add(myObjective, fitness);
		return result;
	}

	/**
	 * Calculates the fitness (bigger fitness -> better solution) of the given
	 * phenotype.
	 * 
	 * @param phenotype the given phenotype
	 * @return the fitness of the given phenotype
	 */
	protected double calculatePhenotypeFitness(Object phenotype) {
		IncomeObject income = (IncomeObject) phenotype;


		// Calculate net / gross ratio and levies / gross ratio
		double netGrossRatio = income.getNetIncome() / income.getGrossIncome();
		double leviesGrossRatio = income.getLevies() / income.getGrossIncome();

		// Ideal ratio: netGrossRatio = 1 and leviesGrossRatio = 0
		return netGrossRatio - leviesGrossRatio;
	}
}
