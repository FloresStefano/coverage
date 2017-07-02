package it.addvalue.csp.engine;

import it.addvalue.csp.collections.Collections;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matcher;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static it.addvalue.csp.collections.Collections.first;
import static it.addvalue.csp.collections.Collections.setOf;
import static it.addvalue.csp.engine.Csp.UNBOUNDED;
import static it.addvalue.csp.engine.CspSolverTestUtils.inOrderEqualTo;

public class CspSolverTestBase {

	private Variable              x;
	private Variable              y;
	private Variable              z;
	private Value                 $1;
	private Value                 $2;
	private Value                 $3;
	private Value                 $4;
	private Value                 $5;
	private Map<Variable, Domain> domains;
	private ProblemBuilder        theProblem;

	@Before
	public void setup() {
		x = new TestVariable("X");
		y = new TestVariable("Y");
		z = new TestVariable("Z");
		$1 = new TestValue(1);
		$2 = new TestValue(2);
		$3 = new TestValue(3);
		$4 = new TestValue(4);
		$5 = new TestValue(5);
		domains = new HashMap<Variable, Domain>();
		domains.put(x, Domain.containing($1, $2, $3, $4, $5));
		domains.put(y, Domain.containing($1, $2, $3, $4, $5));
		domains.put(z, Domain.containing($1, $2, $3, $4, $5));
	}

	protected static Set<Solution> theSolutionsOf(ProblemBuilder builder) {
		return CspSolverTestUtils.theSolutionsOf(builder.build());
	}

	protected ProblemBuilder aProblem() {
		return theProblem = new ProblemBuilder();
	}

	protected Set<Solution> theExpectedSolutions() {
		return setOf(solution($2, $1, $2),
		             solution($3, $1, $1),
		             solution($3, $1, $2),
		             solution($3, $2, $1),
		             solution($4, $1, $1),
		             solution($4, $1, $2),
		             solution($4, $2, $1),
		             solution($5, $1, $1),
		             solution($5, $1, $2),
		             solution($5, $2, $1));
	}

	private Solution solution(Value xValue, Value yValue, Value zValue) {
		return Solution.builder().set(x, xValue).set(y, yValue).set(z, zValue).build();
	}

	protected Matcher<Iterable<Integer>> theLowestCostsInOrder() {
		final int[] costs = {5, 5, 6, 6, 6, 7, 7, 7, 8, 8};
		return inOrderEqualTo(first(costs, maxSolutions()));
	}

	protected int maxSolutions() {
		return 3;
	}

	protected Matcher<Iterable<Solution>> cost(Matcher<Iterable<Integer>> matcher) {
		return CspSolverTestUtils.costs(theCostFunction(), matcher);
	}

	protected CostFunction theCostFunction() {
		return theProblem.csp.getCostFunction();
	}

	protected static Matcher<Iterable<Solution>> satisfyTheConstraintsOf(ProblemBuilder builder) {
		return CspSolverTestUtils.satisfyTheConstraintsOf(builder.build());
	}

	protected Matcher<Iterable<Solution>> orderedByLooselyAscendingCosts() {
		return CspSolverTestUtils.orderedByLooselyAscendingValuesOf(theCostFunction());
	}

	protected ProblemBuilder theProblem() {
		return theProblem;
	}

	protected CostFunction costFunction() {
		return new CostFunction() {

			public int evaluate(Solution solution) {
				return x(solution) + y(solution) + z(solution);
			}

			public String toString() {
				return "x + y + z";
			}

		};
	}

	private int x(Solution solution) {
		return ((TestValue) solution.valueOf(x)).value;
	}

	private int y(Solution solution) {
		return ((TestValue) solution.valueOf(y)).value;
	}

	private int z(Solution solution) {
		return ((TestValue) solution.valueOf(z)).value;
	}

	@RequiredArgsConstructor
	protected static class TestVariable implements Variable {

		private final String name;

		public String toString() {
			return name;
		}

	}

	@RequiredArgsConstructor
	@EqualsAndHashCode(of = "value")
	protected static class TestValue implements Value {

		private final int value;

		public String toString() {
			return String.valueOf(value);
		}

	}

	protected class ProblemBuilder {

		private final Csp csp = new Csp();

		public ProblemBuilder() {
			csp.setDomains(domains);
		}

		public ProblemBuilder feasible() {
			csp.setConstraints(setOf(constraint1(), constraint2(), constraint3()));
			return this;
		}

		private Constraint constraint1() {
			return new Constraint() {

				public Set<Variable> variables() {
					return setOf(x, y, z);
				}

				public String toString() {
					return "x + y > z";
				}

				public boolean verify(Solution solution) {
					return x(solution) + y(solution) > z(solution);
				}

			};
		}

		private Constraint constraint2() {
			return new Constraint() {

				public Set<Variable> variables() {
					return setOf(x, z);
				}

				public boolean verify(Solution solution) {
					return x(solution) + z(solution) >= 4;
				}

				public String toString() {
					return "x + z >= 4";
				}

			};
		}

		private Constraint constraint3() {
			return new Constraint() {

				public Set<Variable> variables() {
					return setOf(y, z);
				}

				public boolean verify(Solution solution) {
					return y(solution) + z(solution) <= 3;
				}

				public String toString() {
					return "y + z <= 3";
				}

			};
		}

		public ProblemBuilder infeasible() {
			csp.setConstraints(setOf(constraint1(), constraint2(), constraint3(), constraint4()));
			return this;
		}

		private Constraint constraint4() {
			return new Constraint() {

				public Set<Variable> variables() {
					return Collections.setOf(y);
				}

				public String toString() {
					return "y > 4";
				}

				public boolean verify(Solution solution) {
					return y(solution) > 4;
				}

			};
		}

		public ProblemBuilder withFullSearch() {
			csp.setFullSearch(true);
			return this;
		}

		public ProblemBuilder withBounded(int maxSolutions) {
			csp.setMaxSolutions(maxSolutions);
			return this;
		}

		public ProblemBuilder withUnboundedMaxSolutions() {
			csp.setMaxSolutions(UNBOUNDED);
			return this;
		}

		public ProblemBuilder with(CostFunction costFunction) {
			csp.setCostFunction(costFunction);
			return this;
		}

		public Csp build() {
			return csp;
		}

	}

}
