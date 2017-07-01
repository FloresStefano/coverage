package it.addvalue.csp.engine;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static it.addvalue.csp.collections.Collections.setOf;
import static it.addvalue.csp.engine.Csp.UNBOUNDED;
import static it.addvalue.csp.engine.CspSolverTestUtils.are;
import static it.addvalue.csp.engine.CspSolverTestUtils.inOrderEqualTo;
import static it.addvalue.csp.engine.CspSolverTestUtils.orderedByLooselyAscendingValuesOf;
import static it.addvalue.csp.engine.CspSolverTestUtils.subsetOf;
import static it.addvalue.csp.engine.CspSolverTestUtils.theEmptySet;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class CspSolverTest {

	private Variable              x;
	private Variable              y;
	private Variable              z;
	private Value                 $1;
	private Value                 $2;
	private Value                 $3;
	private Value                 $4;
	private Value                 $5;
	private Map<Variable, Domain> domains;

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

	@Test
	public void aFeasibleProblemHasSolutions() {
		assertThat(theSolutionsOf(aProblem().feasible()), are(equalTo(theExpectedSolutions())));
	}

	private Set<Solution> theSolutionsOf(ProblemBuilder builder) {
		return CspSolverTestUtils.theSolutionsOf(builder.build());
	}

	private ProblemBuilder aProblem() {
		return new ProblemBuilder();
	}

	private Set<Solution> theExpectedSolutions() {
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

	@Test
	public void anInfeasibleProblemHasNoSolutions() {
		assertThat(theSolutionsOf(aProblem().infeasible()), are(theEmptySet()));
	}

	@Test
	public void aWeightedSearchProducesSolutionsOrderedByAscendingCost() {
		assertThat(theSolutionsOf(aProblem().feasible().withCostFunction()), are(orderedByAscendingCost()));
	}

	private Matcher<Iterable<Solution>> orderedByAscendingCost() {
		return orderedByLooselyAscendingValuesOf(aProblem().costFunction());
	}

	@Test
	public void aBoundedSearchProducesAtMostNSolutions() {
		assertThat(theSolutionsOf(aProblem().feasible().withBoundedMaxSolutions()),
		           hasSize(lessThanOrEqualTo(aProblem().maxSolutions())));
	}

	@Test
	public void aFullBoundedWeightedSearchProducesTheCheapestSolutionsOrderedByAscendingCost() {
		assertThat(theSolutionsOf(aProblem().feasible().withFullSearch().withBoundedMaxSolutions().withCostFunction()),
		           are(inOrderEqualTo(theCheapestSolutionsOrderedByAscendingCost())));
	}

	private Iterable<Solution> theCheapestSolutionsOrderedByAscendingCost() {
		return asList(solution($3, $1, $1), solution($4, $1, $1), solution($5, $1, $1));
	}

	@Test
	public void aBoundedSearchProducesASubsetOfTheUnboundedSearchSolutions() {
		assertThat(theSolutionsOf(aProblem().feasible().withBoundedMaxSolutions()),
		           are(subsetOf(theSolutionsOf(aProblem().feasible().withUnboundedMaxSolutions()))));
	}

	@RequiredArgsConstructor
	private static class TestVariable implements Variable {

		private final String name;

		public String toString() {
			return name;
		}

	}

	@RequiredArgsConstructor
	@EqualsAndHashCode(of = "value")
	private static class TestValue implements Value {

		private final int value;

		public String toString() {
			return String.valueOf(value);
		}

	}

	private class ProblemBuilder {

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

		private int x(Solution solution) {
			return ((TestValue) solution.valueOf(x)).value;
		}

		private int y(Solution solution) {
			return ((TestValue) solution.valueOf(y)).value;
		}

		private int z(Solution solution) {
			return ((TestValue) solution.valueOf(z)).value;
		}

		public ProblemBuilder infeasible() {
			csp.setConstraints(setOf(constraint1(), constraint2(), constraint3(), constraint4()));
			return this;
		}

		private Constraint constraint4() {
			return new Constraint() {

				public Set<Variable> variables() {
					return setOf(y);
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

		public ProblemBuilder withBoundedMaxSolutions() {
			csp.setMaxSolutions(maxSolutions());
			return this;
		}

		private int maxSolutions() {
			return 3;
		}

		public ProblemBuilder withUnboundedMaxSolutions() {
			csp.setMaxSolutions(UNBOUNDED);
			return this;
		}

		public ProblemBuilder withCostFunction() {
			csp.setCostFunction(costFunction());
			return this;
		}

		private CostFunction costFunction() {
			return new CostFunction() {

				public int evaluate(Solution solution) {
					return x(solution) + y(solution) + z(solution);
				}

				public String toString() {
					return "x + y + z";
				}

			};
		}

		public Csp build() {
			return csp;
		}
	}
}
