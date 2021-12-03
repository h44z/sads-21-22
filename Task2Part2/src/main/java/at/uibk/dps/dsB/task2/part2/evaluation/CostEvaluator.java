package at.uibk.dps.dsB.task2.part2.evaluation;

import net.sf.opendse.model.*;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;

import org.opt4j.core.Objective.Sign;

import at.uibk.dps.dsB.task2.part2.properties.PropertyProvider;
import at.uibk.dps.dsB.task2.part2.properties.PropertyProviderStatic;
import net.sf.opendse.optimization.ImplementationEvaluator;

import java.util.Collection;
import java.util.Set;

/**
 * The {@link CostEvaluator} is used to calculate the costs of different
 * orchestrations of the PIW3000.
 * 
 * @author Fedor Smirnov
 *
 */
public class CostEvaluator implements ImplementationEvaluator {

	protected final Objective costObjective = new Objective("Costs [Distopistan Dorrar]", Sign.MIN);
	protected final PropertyProvider propertyProvider = new PropertyProviderStatic();

	@Override
	public Specification evaluate(Specification implementation, Objectives objectives) {
		double costs = calculateImplementationCost(implementation);
		objectives.add(costObjective, costs);
		// No changes to the implementation => return null
		return null;
	}

	/**
	 * Does the actual cost calculation
	 * 
	 * @param implementation the solution which is being evaluated
	 * @return the cost of the implementation
	 */
	protected double calculateImplementationCost(Specification implementation) {
		Collection<Task> tasks = implementation.getApplication().getVertices();
		Mappings<Task, Resource> mappings = implementation.getMappings();
		Routings<Task, Resource, Link> routings = implementation.getRoutings();

		double cost = 0.0;
		for(Task task : tasks) {
			cost += calcTaskCost(mappings, routings, task);
		}

		return cost;
	}

	@Override
	public int getPriority() {
		// To be executed after the timing evaluator
		return TimingEvaluator.priority + 1;
	}

	/**
	 * Calculate the cost for the task.
	 *
	 * @param m the mappings.
	 * @param r the routings.
	 * @param t the task.
	 * @return the longest runtime of the task.
	 */
	private double calcTaskCost(Mappings<Task, Resource> m, Routings<Task, Resource, Link> r, Task t) {
		double cost = 0.0;

		double multiplier = 1.0;
		if(t.getType() != null) {
			switch (t.getType()) {
				case "ITERATIVE_CARS" -> multiplier = propertyProvider.getCarNumber();
				case "ITERATIVE_PEOPLE" -> multiplier = propertyProvider.getNumberOfPeople();
			}
		}

		if(t instanceof Communication) { // communication task
			// sum up communication cost
			for (Link link : r.get(t).getEdges()) {
				Double linkCost = link.getAttribute("COST");
				if(linkCost != null) {
					cost += linkCost * multiplier;
				}
			}
		} else { // calculation task that use resources
			// get assigned resource for the task
			Set<Mapping<Task, Resource>> resources = m.get(t);

			for(Mapping<Task, Resource> resource : resources) {
				Double tmpCost = resource.getTarget().getAttribute("COST");
				double resourceCost = 0.0;
				if (tmpCost != null) {
					resourceCost = tmpCost;
				}
				switch (resource.getTarget().getType()) {
					case "FOG", "EDGE" -> cost += resourceCost;
					case "CLOUD" -> cost += resourceCost * multiplier * propertyProvider.getExecutionTime(resource);
				}
			}
		}

		return cost;
	}
}
