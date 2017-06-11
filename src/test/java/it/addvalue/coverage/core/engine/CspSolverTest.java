package it.addvalue.coverage.core.engine;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static it.addvalue.coverage.core.engine.CspSolverTestUtils.solve;
import static it.addvalue.coverage.core.engine.Solution.INFEASIBLE;
import static it.addvalue.coverage.utils.SetUtils.setOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CspSolverTest {

	private Variable              x;
	private Variable              y;
	private Variable              z;
	private Map<Variable, Domain> domains;

	@Before
	public void setup() {
		x = new TestVariable("X");
		y = new TestVariable("Y");
		z = new TestVariable("Z");
		domains = new HashMap<Variable, Domain>();
		domains.put(x, Domain.containing(new TestValue(1), new TestValue(2), new TestValue(3)));
		domains.put(y, Domain.containing(new TestValue(2), new TestValue(3), new TestValue(4)));
		domains.put(z, Domain.containing(new TestValue(3), new TestValue(4), new TestValue(5)));
	}

	@Test
	public void testFeasibleCsp() {
		Csp csp = new Csp();
		csp.setDomains(domains);
		csp.setConstraints(setOf(constraint1(), constraint2(), constraint3()));

		Solution solution = solve(csp);

		assertThat(x(solution), is(equalTo(3)));
		assertThat(y(solution), is(equalTo(2)));
		assertThat(z(solution), is(equalTo(4)));
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
	public void testInfeasibleCsp() {
		Csp csp = new Csp();
		csp.setDomains(domains);
		csp.setConstraints(setOf(constraint1(), constraint2(), constraint3(), constraint4()));

		Solution solution = solve(csp);

		assertThat(solution, is(INFEASIBLE));
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

	private static class TestVariable implements Variable {

		private final String name;

		private TestVariable(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

	}

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
