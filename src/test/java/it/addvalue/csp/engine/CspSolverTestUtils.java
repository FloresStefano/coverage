package it.addvalue.csp.engine;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;

public class CspSolverTestUtils {

	private CspSolverTestUtils() {
	}

	public static Set<Solution> theSolutionsOf(Csp problem) {
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
				description.appendText("was ").appendValue(set(actual));
			}

		};
	}

	public static <T> Matcher<Set<T>> subsetOf(final Set<T> expected) {
		return new BaseMatcher<Set<T>>() {

			@SuppressWarnings("unchecked")
			private Set<T> set(Object o) {
				return (Set<T>) o;
			}

			public boolean matches(Object actual) {
				return expected.containsAll(set(actual));
			}

			public void describeTo(Description description) {
				description.appendText("is subset of ").appendValue(expected);
			}

			public void describeMismatch(Object actual, Description description) {
				description.appendText("was ").appendValue(set(actual));
			}

		};
	}

	public static Matcher<Iterable<Solution>> orderedByStrictlyAscendingValuesOf(final CostFunction cost) {
		return new BaseMatcher<Iterable<Solution>>() {

			@SuppressWarnings("unchecked")
			private Iterable<Solution> iterable(Object o) {
				return (Iterable<Solution>) o;
			}

			public boolean matches(Object actual) {
				int prevCost = Integer.MIN_VALUE;
				for (Solution solution : iterable(actual)) {
					int currCost = cost.evaluate(solution);
					if (currCost > prevCost) {
						prevCost = currCost;
					} else {
						return false;
					}
				}
				return true;
			}

			public void describeTo(Description description) {
				description.appendText("is strictly ordered by ascending cost");
			}

			public void describeMismatch(Object actual, Description description) {
				List<Integer> costs = new ArrayList<Integer>();
				for (Solution solution : iterable(actual)) {
					costs.add(cost.evaluate(solution));
				}
				description.appendText("costs were: ").appendValueList("[", ", ", "]", costs);
			}

		};
	}

	public static Matcher<Iterable<Solution>> orderedByLooselyAscendingValuesOf(final CostFunction cost) {
		return new BaseMatcher<Iterable<Solution>>() {

			@SuppressWarnings("unchecked")
			private Iterable<Solution> iterable(Object o) {
				return (Iterable<Solution>) o;
			}

			public boolean matches(Object actual) {
				int prevCost = Integer.MIN_VALUE;
				for (Solution solution : iterable(actual)) {
					int currCost = cost.evaluate(solution);
					if (currCost >= prevCost) {
						prevCost = currCost;
					} else {
						return false;
					}
				}
				return true;
			}

			public void describeTo(Description description) {
				description.appendText("is loosely ordered by ascending cost");
			}

			public void describeMismatch(Object actual, Description description) {
				List<Integer> costs = new ArrayList<Integer>();
				for (Solution solution : iterable(actual)) {
					costs.add(cost.evaluate(solution));
				}
				description.appendText("costs were: ").appendValueList("[", ", ", "]", costs);
			}

		};
	}

	public static <T> Matcher<Iterable<T>> inOrderEqualTo(final T... expected) {
		return inOrderEqualTo(asList(expected));
	}

	public static <T> Matcher<Iterable<T>> inOrderEqualTo(final Iterable<T> expected) {
		return new BaseMatcher<Iterable<T>>() {

			@SuppressWarnings("unchecked")
			private Iterable<T> iterable(Object o) {
				return (Iterable<T>) o;
			}

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

			public void describeTo(Description description) {
				description.appendText("iterates to ").appendValue(expected);
			}

			public void describeMismatch(Object actual, Description description) {
				description.appendText("was ").appendValue(iterable(actual));
			}

		};
	}

	public static Set<Solution> theEmptySet() {
		return new HashSet<Solution>();
	}

	public static <T> Matcher<T> are(Matcher<T> matcher) {
		return is(matcher);
	}

	public static <T> Matcher<T> are(T value) {
		return is(value);
	}

}
