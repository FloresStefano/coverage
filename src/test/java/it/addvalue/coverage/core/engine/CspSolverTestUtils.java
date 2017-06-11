package it.addvalue.coverage.core.engine;

public class CspSolverTestUtils {

	private CspSolverTestUtils() {
	}

	public static Solution solve(Csp problem) {
		return solve(problem, new CspSolver());
	}

	public static Solution solve(Csp problem, CspSolver solver) {
		System.out.println("Problem definition:");
		System.out.println(problem);

		System.out.println("Solving problem...\n");
		Solution solution = solver.solve(problem);

		System.out.println("Solution:");
		System.out.println(solution);
		System.out.println();

		return solution;
	}

}
