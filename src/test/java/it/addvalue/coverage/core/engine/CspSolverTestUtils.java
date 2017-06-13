package it.addvalue.coverage.core.engine;

import org.apache.commons.collections4.SetUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.HashSet;
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
					System.out.printf("\tcost = %d: ", problem.getCostFunction().evaluate(solution));
				}
				System.out.println(solution);
			}
		}
		System.out.println();
		return solutions;
	}

	public static Matcher<? super Set<Solution>> contains(final Set<Solution> solutions) {
		return new BaseMatcher<Set<Solution>>() {

			private Set<Solution> arg;

			@SuppressWarnings("unchecked")
			public boolean matches(Object o) {
				return (arg = (Set<Solution>) o).containsAll(solutions);
			}

			public void describeTo(Description description) {
				description.appendValue(arg).appendText(" to contain ").appendValue(solutions);
			}

			public void describeMismatch(Object item, Description description) {
				description.appendValue(SetUtils.difference(solutions, arg)).appendText(" were also present");
			}

		};
	}

	public static Set<Solution> noSolutions() {
		return new HashSet<Solution>();
	}

}
