package at.uibk.dps.dsB.task2.part1;

import net.sf.opendse.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link SpecificationGenerator} generates the {@link Specification}
 * modeling the orchestration of the customer modeling application discussed in
 * Lecture 1.
 * 
 * @author Fedor Smirnov
 */
public final class SpecificationGenerator {

	private SpecificationGenerator() {
	}

	/**
	 * Generates the specification modeling the orchestration of the customer
	 * monitoring application.
	 * 
	 * @return the specification modeling the orchestration of the customer
	 *         monitoring application
	 */
	public static Specification generate() {
		Application<Task, Dependency> appl = generateApplication();
		Architecture<Resource, Link> arch = generateArchitecture();
		Mappings<Task, Resource> mappings = generateMappings(arch, appl);
		return new Specification(appl, arch, mappings);
	}

	/**
	 * Generates the application graph
	 * 
	 * @return the application graph
	 */
	private static Application<Task, Dependency> generateApplication() {
		Application<Task, Dependency> application = new Application<>();

		List<Task> tasks =  new ArrayList<>();
		for(int i = 0; i < 6; i++) {
			tasks.add(new Task ("t" + i));
		}
		List<Communication> communications = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			communications.add(new Communication ("c" + i));
		}

		// add nodes to the application
		for(Task t : tasks) {
			application.addVertex(t);
		}
		for(Communication c : communications) {
			application.addVertex(c);
		}

		// create dependency edges
		application.addEdge(new Dependency("d0"), tasks.get(0), communications.get(0));
		application.addEdge(new Dependency("d1"), communications.get(0), tasks.get(1));
		application.addEdge(new Dependency("d2"), tasks.get(1), communications.get(1));
		application.addEdge(new Dependency("d3"), tasks.get(1), communications.get(2));
		application.addEdge(new Dependency("d4"), communications.get(1), tasks.get(2));
		application.addEdge(new Dependency("d5"), communications.get(2), tasks.get(3));
		application.addEdge(new Dependency("d6"), tasks.get(2), communications.get(3));
		application.addEdge(new Dependency("d7"), tasks.get(3), communications.get(4));
		application.addEdge(new Dependency("d8"), communications.get(3), tasks.get(4));
		application.addEdge(new Dependency("d9"), communications.get(3), tasks.get(5));
		application.addEdge(new Dependency("d10"), communications.get(4), tasks.get(4));
		application.addEdge(new Dependency("d11"), communications.get(4), tasks.get(5));

		return application;
	}

	/**
	 * Generates the architecture graph
	 * 
	 * @return the architecture graph
	 */
	private static Architecture<Resource, Link> generateArchitecture() {
		Architecture<Resource, Link> architecture = new Architecture<>();

		List<Resource> resources = new ArrayList<>();
		for(int i = 0; i < 8; i++) {
			resources.add(new Resource ("r" + i));
		}

		// same costs as in customerMonitoringSpec.xml
		resources.get(0).setAttribute("costs", 5);
		resources.get(1).setAttribute("costs", 10);
		resources.get(2).setAttribute("costs", 10);
		resources.get(3).setAttribute("costs", 1);
		resources.get(4).setAttribute("costs", 1);
		resources.get(5).setAttribute("costs", 1);
		resources.get(6).setAttribute("costs", 15);
		resources.get(7).setAttribute("costs", 20);

		for(Resource r : resources) {
			architecture.addVertex(r);
		}

		// create link between resources, same as in customerMonitoringSpec.xml
		architecture.addEdge(new Link("l0"), resources.get(0), resources.get(2));
		architecture.addEdge(new Link("l1"), resources.get(0), resources.get(2));
		architecture.addEdge(new Link("l2"), resources.get(1), resources.get(2));
		architecture.addEdge(new Link("l3"), resources.get(1), resources.get(2));
		architecture.addEdge(new Link("l4"), resources.get(3), resources.get(2));
		architecture.addEdge(new Link("l5"), resources.get(4), resources.get(2));
		architecture.addEdge(new Link("l6"), resources.get(5), resources.get(2));
		architecture.addEdge(new Link("l7"), resources.get(6), resources.get(2));
		architecture.addEdge(new Link("l8"), resources.get(7), resources.get(2));
		architecture.addEdge(new Link("l9"), resources.get(3), resources.get(4));
		architecture.addEdge(new Link("l10"), resources.get(3), resources.get(5));
		architecture.addEdge(new Link("l11"), resources.get(4), resources.get(5));

		return architecture;
	}

	/**
	 * Generates the mapping edges
	 * 
	 * @param arch the architecture graph
	 * @param appl the application graph
	 * @return the mapping edges
	 */
	private static Mappings<Task, Resource> generateMappings(Architecture<Resource, Link> arch,
			Application<Task, Dependency> appl) {
		Mappings<Task, Resource> mappings = new Mappings<>();

		mappings.add(new Mapping<>("m0", appl.getVertex("t0"), arch.getVertex("r0")));
		mappings.add(new Mapping<>("m1", appl.getVertex("t0"), arch.getVertex("r1")));
		mappings.add(new Mapping<>("m2", appl.getVertex("t1"), arch.getVertex("r1")));
		mappings.add(new Mapping<>("m3", appl.getVertex("t1"), arch.getVertex("r2")));
		mappings.add(new Mapping<>("m4", appl.getVertex("t1"), arch.getVertex("r6")));
		mappings.add(new Mapping<>("m5", appl.getVertex("t1"), arch.getVertex("r7")));
		mappings.add(new Mapping<>("m6", appl.getVertex("t2"), arch.getVertex("r2")));
		mappings.add(new Mapping<>("m7", appl.getVertex("t2"), arch.getVertex("r6")));
		mappings.add(new Mapping<>("m8", appl.getVertex("t2"), arch.getVertex("r7")));
		mappings.add(new Mapping<>("m9", appl.getVertex("t3"), arch.getVertex("r6")));
		mappings.add(new Mapping<>("m10", appl.getVertex("t3"), arch.getVertex("r7")));
		mappings.add(new Mapping<>("m11", appl.getVertex("t4"), arch.getVertex("r3")));
		mappings.add(new Mapping<>("m12", appl.getVertex("t4"), arch.getVertex("r4")));
		mappings.add(new Mapping<>("m13", appl.getVertex("t4"), arch.getVertex("r5")));
		mappings.add(new Mapping<>("m14", appl.getVertex("t5"), arch.getVertex("r3")));
		mappings.add(new Mapping<>("m15", appl.getVertex("t5"), arch.getVertex("r4")));
		mappings.add(new Mapping<>("m16", appl.getVertex("t5"), arch.getVertex("r5")));

		return mappings;
	}

}
