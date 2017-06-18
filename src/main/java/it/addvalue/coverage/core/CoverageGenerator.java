package it.addvalue.coverage.core;

import it.addvalue.coverage.Input;
import it.addvalue.coverage.Output;
import it.addvalue.csp.engine.Csp;
import it.addvalue.csp.engine.CspSolver;
import it.addvalue.csp.engine.Solution;
import lombok.val;

import java.util.Set;

public class CoverageGenerator {

	private final CspSolver solver;

	public CoverageGenerator() {
		solver = createSolver();
	}

	private CspSolver createSolver() {
		return new CspSolver();
	}

	public Output generate(Input input) {
		val csp = cspFrom(input);
		val solutions = solver.solve(csp);
		return outputFrom(input, solutions);
	}

	private Csp cspFrom(Input input) {
		// TODO
		val csp = new Csp();
		return csp;
	}

	private Output outputFrom(Input input, Set<Solution> solutions) {
		// TODO
		val output = new Output();
		return output;
	}

}
