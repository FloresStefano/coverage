package it.addvalue.csp.engine;

import lombok.Data;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

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
		return solver.solve(problem);
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

	public static Matcher<Iterable<Solution>> costs(final CostFunction costFunction,
	                                                final Matcher<Iterable<Integer>> matcher) {
		return new BaseMatcher<Iterable<Solution>>() {

			@SuppressWarnings("unchecked")
			private Iterable<Solution> iterable(Object o) {
				return (Iterable<Solution>) o;
			}

			public boolean matches(Object actual) {
				List<Integer> costs = new ArrayList<Integer>();
				for (Solution solution : iterable(actual)) {
					costs.add(costFunction.evaluate(solution));
				}
				return matcher.matches(costs);
			}

			public void describeTo(Description description) {
				description.appendText("costs ").appendDescriptionOf(matcher);
			}

			public void describeMismatch(Object actual, Description description) {
				description.appendText("solutions were: ").appendValueList("[", ", ", "]", iterable(actual));
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

	public static Matcher<Iterable<Solution>> satisfyTheConstraintsOf(final Csp csp) {
		return new BaseMatcher<Iterable<Solution>>() {

			@Data
			class Mismatch {
				private final Solution   violatingSolution;
				private final Constraint violatedConstraint;
			}

			@SuppressWarnings("unchecked")
			private Iterable<Solution> iterable(Object o) {
				return (Iterable<Solution>) o;
			}

			public boolean matches(Object actual) {
				return findMismatch(actual) == null;
			}

			private Mismatch findMismatch(Object actual) {
				for (Solution solution : iterable(actual)) {
					for (Constraint constraint : csp.getConstraints()) {
						if (!constraint.verify(solution)) {
							return new Mismatch(solution, constraint);
						}
					}
				}
				return null;
			}

			public void describeTo(Description description) {
				description.appendText("satisfy the problem constraints");
			}

			public void describeMismatch(Object actual, Description description) {
				Mismatch mismatch = findMismatch(actual);
				description.appendText("solution ")
				           .appendValue(mismatch.getViolatingSolution())
				           .appendText(" violated constraint ")
				           .appendValue(mismatch.getViolatedConstraint());
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
				description.appendText("in order equal to ").appendValue(expected);
			}

			public void describeMismatch(Object actual, Description description) {
				description.appendText("was ").appendValue(iterable(actual));
			}

		};
	}

	public static <T> Matcher<Iterable<T>> inOrder(final Iterable<Matcher<T>> matchers) {
		return new BaseMatcher<Iterable<T>>() {

			@SuppressWarnings("unchecked")
			private Iterable<T> iterable(Object o) {
				return (Iterable<T>) o;
			}

			@SuppressWarnings("unchecked")
			public boolean matches(Object actual) {
				Iterator<T> it = iterable(actual).iterator();
				for (Matcher<T> matcher : matchers) {
					if (!it.hasNext() || !matcher.matches(it.next())) {
						return false;
					}
				}
				return !it.hasNext();
			}

			public void describeTo(Description description) {
				description.appendText("in order equal to ").appendValue(matchers);
			}

			public void describeMismatch(Object actual, Description description) {
				description.appendText("was ").appendValue(iterable(actual));
			}

		};
	}

	public static Set<Solution> theEmptySet() {
		return new HashSet<Solution>();
	}

	public static <T> Matcher<T> are(final Matcher<T> matcher) {
		return new Is<T>(matcher) {
			public void describeTo(Description description) {
				description.appendText("are ").appendDescriptionOf(matcher);
			}
		};
	}

	public static <T> Matcher<T> have(final Matcher<T> matcher) {
		return new Is<T>(matcher) {
			public void describeTo(Description description) {
				description.appendText("have ").appendDescriptionOf(matcher);
			}
		};
	}

	public static <T> Matcher<T> are(T value) {
		final Matcher<T> matcher = IsEqual.equalTo(value);
		return new Is<T>(matcher) {
			public void describeTo(Description description) {
				description.appendText("are ").appendDescriptionOf(matcher);
			}
		};
	}

}
