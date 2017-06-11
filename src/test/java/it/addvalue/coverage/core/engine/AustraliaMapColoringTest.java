package it.addvalue.coverage.core.engine;

import it.addvalue.coverage.utils.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static it.addvalue.coverage.core.engine.AustraliaMapColoringTest.State.NSW;
import static it.addvalue.coverage.core.engine.AustraliaMapColoringTest.State.NT;
import static it.addvalue.coverage.core.engine.AustraliaMapColoringTest.State.Q;
import static it.addvalue.coverage.core.engine.AustraliaMapColoringTest.State.SA;
import static it.addvalue.coverage.core.engine.AustraliaMapColoringTest.State.V;
import static it.addvalue.coverage.core.engine.AustraliaMapColoringTest.State.WA;
import static it.addvalue.coverage.core.engine.CspSolverTestUtils.solve;
import static it.addvalue.coverage.core.engine.Solution.INFEASIBLE;
import static it.addvalue.coverage.utils.SetUtils.setOf;
import static it.addvalue.coverage.utils.SetUtils.unorderedPairsFrom;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class AustraliaMapColoringTest {

	private Map<Variable, Domain> domains;

	@Before
	public void setup() {
		domains = new HashMap<Variable, Domain>();
		Domain colors = Domain.containing(Color.values());
		for (State state : State.values()) {
			domains.put(state, colors);
		}
	}

	@Test
	public void testWithBinaryConstraints() {
		assertThat(solutionOf(binaryProblem()), is(not(INFEASIBLE)));
	}

	private Solution solutionOf(Csp problem) {
		return solve(problem);
	}

	private Csp binaryProblem() {
		Csp csp = new Csp();
		csp.setDomains(domains);
		csp.setConstraints(setOf(haveDifferentColors(WA, NT),
		                         haveDifferentColors(WA, SA),
		                         haveDifferentColors(NT, SA),
		                         haveDifferentColors(NT, Q),
		                         haveDifferentColors(SA, Q),
		                         haveDifferentColors(SA, NSW),
		                         haveDifferentColors(Q, NSW),
		                         haveDifferentColors(SA, V),
		                         haveDifferentColors(V, NSW)));
		return csp;
	}

	private Constraint haveDifferentColors(final Variable... states) {
		final Set<Variable> stateSet = setOf(states);
		return new Constraint() {

			public Set<Variable> variables() {
				return stateSet;
			}

			public boolean verify(Solution solution) {
				for (Pair<Variable> pair : unorderedPairsFrom(states)) {
					Value color1 = solution.evaluate(pair.getItem1());
					Value color2 = solution.evaluate(pair.getItem2());
					if (color1.equals(color2)) {
						return false;
					}
				}
				return true;
			}

			public String toString() {
				return Arrays.asList(states) + " have different colors";
			}

		};
	}

	@Test
	public void testWithNaryConstraints() {
		assertThat(solutionOf(naryProblem()), is(not(INFEASIBLE)));
	}

	private Csp naryProblem() {
		Csp csp = new Csp();
		csp.setDomains(domains);
		csp.setConstraints(setOf(haveDifferentColors(WA, NT, SA),
		                         haveDifferentColors(NT, SA, Q),
		                         haveDifferentColors(SA, Q, NSW),
		                         haveDifferentColors(SA, NSW, V)));
		return csp;
	}

	@Test
	public void equivalentProblemsHaveSameSolution() {
		assertThat(solutionOf(binaryProblem()), is(equalTo(solutionOf(naryProblem()))));
	}

	public enum State implements Variable {
		// Western Australia
		WA,
		// Northern Territory
		NT,
		// South Australia
		SA,
		// Queensland
		Q,
		// New South Wales
		NSW,
		// Victoria
		V,
		// Tasmania
		T
	}

	public enum Color implements Value {
		RED,
		GREEN,
		BLUE
	}

}

