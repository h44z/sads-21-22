package at.uibk.dps.dsB.ex0.creators;

import at.uibk.dps.dsB.ex0.IncomeProblem;
import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.core.problem.Creator;

import javax.inject.Inject;

/**
 * The {@link Creator} class which will be used to initialize the genotypes
 * encoding individual problem solutions.
 * 
 * @author Christoph Haas
 *
 */
public class MyFirstCreator implements Creator<DoubleGenotype> {
	protected final IncomeProblem problem;

	@Inject
	public MyFirstCreator(IncomeProblem problem) {
		this.problem = problem;
	}

	@Override
	public DoubleGenotype create() {
		DoubleGenotype genotype = new DoubleGenotype(problem.getMinGrossIncome(), problem.getMaxGrossIncome());
		genotype.init(problem.getRandom(), 1);

		return genotype;
	}

}
