package it.addvalue.csp.engine;

import lombok.Data;

import java.util.Set;

@Data
class Results {

	private long iterations = 0L;
	private final Set<Solution> solutions;

	public void incrementIterations() {
		++iterations;
	}

	public void addSolution(Solution solution) {
		solutions.add(solution);
	}

}
