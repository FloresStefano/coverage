package it.addvalue.coverage.core.engine;

import java.util.List;

public class CspSolverTestUtils {

	private CspSolverTestUtils() {
	}

	public static List<Solution> solve(Csp problem) {
		return solve(problem, new CspSolver());
	}

	public static List<Solution> solve(Csp problem, CspSolver solver) {
		System.out.println("Problem definition:");
		System.out.println(problem);

		System.out.println("Solving problem...\n");
		List<Solution> solutions = solver.solve(problem);

		switch (solutions.size()) {
		case 0:
			System.out.println("No solutions found");
			return solutions;
		case 1:
			System.out.println("Found 1 solution:");
			System.out.println(solutions.get(0));
			System.out.println();
			return solutions;
		default:
			System.out.println("Found " + solutions.size() + " solutions:");
			for (Solution solution : solutions) {
				System.out.println(solution);
			}
			System.out.println();
			return solutions;
		}
	}

}
