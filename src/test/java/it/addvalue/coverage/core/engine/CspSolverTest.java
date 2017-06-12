package it.addvalue.coverage.core.engine;

import lombok.EqualsAndHashCode;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static it.addvalue.coverage.core.engine.CspSolverTestUtils.contains;
import static it.addvalue.coverage.core.engine.CspSolverTestUtils.solutionsOf;
import static it.addvalue.coverage.utils.Collections.setOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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
		domains.put(x, Domain.containing($1, $2, $3));
		domains.put(y, Domain.containing($2, $3, $4));
		domains.put(z, Domain.containing($3, $4, $5));
	}

	@Test
	public void aFeasibleProblemHasSolutions() {
		assertThat(solutionsOf(feasibleProblem()), is(equalTo(setOf(expectedSolution()))));
	}

	private Csp feasibleProblem() {
		Csp csp = new Csp();
		csp.setDomains(domains);
		csp.setConstraints(setOf(constraint1(), constraint2(), constraint3()));
		return csp;
	}

	private Solution expectedSolution() {
		return Solution.builder().set(x, $3).set(y, $2).set(z, $4).build();
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
				return x(solution) + z(solution) == 7;
			}

			public String toString() {
				return "x + z = 7";
			}

		};
	}

	private Constraint constraint3() {
		return new Constraint() {

			public Set<Variable> variables() {
				return setOf(y, z);
			}

			public boolean verify(Solution solution) {
				return y(solution) + z(solution) == 6;
			}

			public String toString() {
				return "y + z = 6";
			}

		};
	}

	private int x(Solution solution) {
		return ((TestValue) solution.evaluate(x)).value;
	}

	private int y(Solution solution) {
		return ((TestValue) solution.evaluate(y)).value;
	}

	private int z(Solution solution) {
		return ((TestValue) solution.evaluate(z)).value;
	}

	@Test
	public void anInfeasibleProblemHasNoSolutions() {
		assertThat(solutionsOf(infeasibleProblem()), is(emptySet()));
	}

	private Csp infeasibleProblem() {
		Csp csp = new Csp();
		csp.setDomains(domains);
		csp.setConstraints(setOf(constraint1(), constraint2(), constraint3(), constraint4()));
		return csp;
	}

	private Set<Solution> emptySet() {
		return new HashSet<Solution>();
	}

	private Constraint constraint4() {
		return new Constraint() {

			public Set<Variable> variables() {
				return setOf(x);
			}

			public boolean verify(Solution solution) {
				return x(solution) > 3;
			}

			public String toString() {
				return "x > 3";
			}

		};
	}

	@Test
	public void ifCostFunctionIsDefinedThenSolutionsAreSortedByAscendingCost() {
		assertThat(solutionsOf(problemWithCostFunction()), is(equalTo(solutionsWithAscendingCost())));
	}

	@Test
	public void ifMaxResultsIsDefinedThenSolutionsAreFewerThanItsValue() {
		assertThat(solutionsOf(problemWithMaxResults()),
		           hasSize(lessThanOrEqualTo(problemWithMaxResults().getMaxSolutions())));
	}

	private Csp problemWithMaxResults() {
		Csp csp = new Csp();
		csp.setDomains(domains);
		csp.setConstraints(setOf(constraint1(), constraint3()));
		csp.setMaxSolutions(2);
		return csp;
	}

	@Test
	public void ifMaxResultsIsDefinedThenSolutionsAreSubsetOfThoseOfTheUnboundedProblem() {
		assertThat(solutionsOf(problemWithoutMaxResults()), contains(solutionsOf(problemWithMaxResults())));
	}

	private Csp problemWithoutMaxResults() {
		Csp csp = new Csp();
		csp.setDomains(domains);
		csp.setConstraints(setOf(constraint1(), constraint3()));
		return csp;
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

	private Csp problemWithCostFunction() {
		Csp csp = new Csp();
		csp.setDomains(domains);
		csp.setConstraints(setOf(constraint1(), constraint3()));
		csp.setCostFunction(costFunction());
		return csp;
	}

	private Set<Solution> solutionsWithAscendingCost() {
		Set<Solution> solutions = problemWithCostFunction().newSolutionSet();
		solutions.add(Solution.builder().set(x, $1).set(y, $3).set(z, $3).build());
		solutions.add(Solution.builder().set(x, $2).set(y, $3).set(z, $3).build());
		solutions.add(Solution.builder().set(x, $3).set(y, $2).set(z, $4).build());
		return solutions;
	}

	private static class TestVariable implements Variable {

		private final String name;

		private TestVariable(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

	}

	@EqualsAndHashCode(of = "value")
	private static class TestValue implements Value {

		private final int value;

		private TestValue(int value) {
			this.value = value;
		}

		public String toString() {
			return String.valueOf(value);
		}

	}

}
