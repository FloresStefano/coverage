package it.addvalue.csp.engine;

import org.apache.commons.collections4.SetUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
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

	public static <T> Matcher<Set<T>> contains(final Set<T> expected) {
		return new BaseMatcher<Set<T>>() {

			public boolean matches(Object actual) {
				return set(actual).containsAll(expected);
			}

			@SuppressWarnings("unchecked")
			private Set<T> set(Object o) {
				return (Set<T>) o;
			}

			public void describeTo(Description description) {
				description.appendText("contains ").appendValue(expected);
			}

			public void describeMismatch(Object actual, Description description) {
				description.appendValue(SetUtils.difference(expected, set(actual))).appendText(" were also present");
			}

		};
	}

	public static <T> Matcher<Iterable<T>> iteratesTo(final T... expected) {
		return new BaseMatcher<Iterable<T>>() {

			@SuppressWarnings("unchecked")
			public boolean matches(Object actual) {
				Iterator<T> it = iterable(actual).iterator();
				for (T expectedValue : expected) {
					if (!it.hasNext() || !it.next().equals(expectedValue)) {
						return false;
					}
				}
				return !it.hasNext();
			}

			@SuppressWarnings("unchecked")
			private Iterable<T> iterable(Object o) {
				return (Iterable<T>) o;
			}

			public void describeTo(Description description) {
				description.appendText("iterates to ").appendValue(Arrays.toString(expected));
			}

			public void describeMismatch(Object actual, Description description) {
				description.appendText("was ").appendValue(iterable(actual));
			}

		};
	}

	public static Set<Solution> noSolutions() {
		return new HashSet<Solution>();
	}

}
