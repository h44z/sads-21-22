package at.uibk.dps.dsB.task2.part2.evaluation;

import net.sf.opendse.model.*;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;

import at.uibk.dps.dsB.task2.part2.properties.PropertyProvider;
import at.uibk.dps.dsB.task2.part2.properties.PropertyProviderStatic;
import net.sf.opendse.optimization.ImplementationEvaluator;

import java.util.Collection;
import java.util.Set;

/**
 * Evaluator for the makespan of the Piw3000
 * 
 * @author Fedor Smirnov
 */
public class TimingEvaluator implements ImplementationEvaluator {

	protected final PropertyProvider propertyProvider = new PropertyProviderStatic();

	protected static final int priority = 0;

	protected final Objective makeSpanObjective = new Objective("Makespan [TU]", Sign.MIN);

	public static final String accumulatedUsageAttribute = "Accumulated Usage";

	@Override
	public Specification evaluate(Specification implementation, Objectives objectives) {
		objectives.add(makeSpanObjective, calculateMakespan(implementation));
		// Implementation annotated => return the impl
		return implementation;
	}

	/**
	 * Does the actual makespan calculation.
	 * 
	 * @param implementation the orchestration under evaluation
	 * @return the makespan of the orchestration
	 */
	protected double calculateMakespan(Specification implementation) {
		Application<Task, Dependency> application = implementation.getApplication();
		Collection<Task> tasks = application.getVertices();
		Mappings<Task, Resource> mappings = implementation.getMappings();
		Routings<Task, Resource, Link> routings = implementation.getRoutings();

		// calculate the time that one tasks needs, we need to get the maximum
		// of this time -> makespan

		double makespan = 0.0;
		for (Task task : tasks) {
			makespan = Math.max(makespan, calcTaskTime(application, mappings, routings, task));
		}

		return makespan;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	/**
	 * Recursively calculate the time when the given task ends.
	 *
	 * @param a the application.
	 * @param m the mappings.
	 * @param r the routings.
	 * @param t the task.
	 * @return the longest runtime of the task.
	 */
	private double calcTaskTime(Application<Task, Dependency> a, Mappings<Task, Resource> m, Routings<Task, Resource, Link> r, Task t) {
		Collection<Task> predecessors = a.getPredecessors(t);

		// calc start time ( = the latest end time from predecessor tasks)
		double timeStart = 0.0;
		for(Task predecessor : predecessors) {
			timeStart = Math.max(timeStart, calcTaskTime(a, m, r, predecessor));
		}

		// calculate task runtime and add it to the start time
		double timeRun = 0.0;

		if(t instanceof Communication) { // communication task
			// sum up communication cost
			for (Link link : r.get(t).getEdges()) {
				timeRun += propertyProvider.getTransmissionTime((Communication) t, link);
			}
		} else { // calculation task that use resources
			// get assigned resource for the task
			Set<Mapping<Task, Resource>> resources = m.get(t);

			for(Mapping<Task, Resource> resource : resources) {
				double timeResourceRun = propertyProvider.getExecutionTime(resource);

				switch(resource.getTarget().getType()) {
					case "FOG":
					case "EDGE":
						break;
					case "CLOUD":
						timeResourceRun /= propertyProvider.getNumberOfAvailableInstances(resource.getTarget());
						break;
				}

				timeRun += timeResourceRun;
			}
		}

		// check if task is iterative, if so, multiply the previously calculated execution time
		if(t.getType() != null) {
			switch (t.getType()) {
				case "ITERATIVE_CARS" -> timeRun *= propertyProvider.getCarNumber();
				case "ITERATIVE_PEOPLE" -> timeRun *= propertyProvider.getNumberOfPeople();
			}
		}

		return timeStart + timeRun;
	}
}
