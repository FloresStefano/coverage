package it.addvalue.coverage.core.engine;

import java.util.Set;

public class CspSolverTestUtils {

	private CspSolverTestUtils() {
	}

	public static Set<Solution> solutionsOf(Csp problem) {
		return solve(problem);
	}

	public static Set<Solution> solve(Csp problem) {
		return solve(problem, new CspSolver());
	}

	public static Set<Solution> solve(Csp problem, CspSolver solver) {
		System.out.println("Problem definition:");
		System.out.println(problem);

		System.out.println("Solving problem...\n");
		Set<Solution> solutions = solver.solve(problem);

		if (solutions.isEmpty()) {
			System.out.println("No solutions found");
		} else {
			if (solutions.size() == 1) {
				System.out.println("1 solution found:");
			} else {
				System.out.println(solutions.size() + " solutions found:");
			}
			for (Solution solution : solutions) {
				if (problem.getCostFunction() != null) {
					System.out.printf("\tcost=%d : ", problem.getCostFunction().evaluate(solution));
				}
				System.out.println(solution);
			}
		}
		System.out.println();
		return solutions;
	}

}
