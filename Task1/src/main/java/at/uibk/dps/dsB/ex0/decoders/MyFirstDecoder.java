package at.uibk.dps.dsB.ex0.decoders;

import org.opt4j.core.Genotype;
import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.core.problem.Decoder;

import java.util.Arrays;
import java.util.List;

/**
 * The {@link Decoder} class which will be used to decode the genotypes, i.e.,
 * transform them into a representation which can be processed by the evaluator.
 * 
 * @author Christoph Haas
 *
 */
public class MyFirstDecoder implements Decoder<Genotype, Object> {

	@Override
	public Object decode(Genotype genotype) {
		DoubleGenotype dg = (DoubleGenotype) genotype;
		double grossIncome = dg.get(0); // only use the first value of the genotype
		double netIncome = calculateNetIncome(grossIncome);
		double levies = grossIncome - netIncome;

		return new IncomeObject(grossIncome, netIncome, levies);
	}

	/**
	 * calculateNetIncome calculates the net income according to austrian tax rules of 2021.
	 *
	 * @param grossIncome the gross income
	 * @return the net income
	 */
	public double calculateNetIncome(double grossIncome) {
		double netIncome = 0;

		// tax rates base on: https://www.usp.gv.at/en/steuern-finanzen/einkommensteuer/tarifstufen-berechnungsformeln.html
		List<Double> taxRates = Arrays.asList(0.0, 0.2, 0.35, 0.42, 0.48, 0.5, 0.55);
		List<Double> taxRateLimits = Arrays.asList(0.0, 11000.0, 18000.0, 31000.0, 60000.0, 90000.0, 1000000.0);

		for(int i = taxRateLimits.size() - 1; i >= 0; i--) {
			double taxRateLimit = taxRateLimits.get(i);
			double taxRate = taxRates.get(i);

			if(grossIncome > taxRateLimit) {
				double taxableIncome = grossIncome - taxRateLimit;
				double taxes = taxableIncome * taxRate;
				netIncome += taxableIncome - taxes;

				grossIncome = taxRateLimit;
			}
		}

		return netIncome;
	}
}
