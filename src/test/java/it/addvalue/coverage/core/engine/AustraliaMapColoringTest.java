package it.addvalue.coverage.core.engine;

import it.addvalue.coverage.utils.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static it.addvalue.coverage.core.engine.AustraliaMapColoringTest.State.NSW;
import static it.addvalue.coverage.core.engine.AustraliaMapColoringTest.State.NT;
import static it.addvalue.coverage.core.engine.AustraliaMapColoringTest.State.Q;
import static it.addvalue.coverage.core.engine.AustraliaMapColoringTest.State.SA;
import static it.addvalue.coverage.core.engine.AustraliaMapColoringTest.State.V;
import static it.addvalue.coverage.core.engine.AustraliaMapColoringTest.State.WA;
import static it.addvalue.coverage.core.engine.CspSolverTestUtils.solutionsOf;
import static it.addvalue.coverage.utils.Collections.setOf;
import static it.addvalue.coverage.utils.Collections.unorderedPairsFrom;
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
		assertThat(solutionsOf(binaryProblem()), is(not(emptySet())));
	}

	private Csp binaryProblem() {
		Csp csp = new Csp();
		csp.setDomains(domains);
		csp.setConstraints(setOf(mustHaveDifferentColors(WA, NT),
		                         mustHaveDifferentColors(WA, SA),
		                         mustHaveDifferentColors(NT, SA),
		                         mustHaveDifferentColors(NT, Q),
		                         mustHaveDifferentColors(SA, Q),
		                         mustHaveDifferentColors(SA, NSW),
		                         mustHaveDifferentColors(Q, NSW),
		                         mustHaveDifferentColors(SA, V),
		                         mustHaveDifferentColors(V, NSW)));
		return csp;
	}

	private Set<Solution> emptySet() {
		return new HashSet<Solution>();
	}

	private Constraint mustHaveDifferentColors(final Variable... states) {
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
				return stateSet + " must have different colors";
			}

		};
	}

	@Test
	public void testWithNaryConstraints() {
		assertThat(solutionsOf(naryProblem()), is(not(emptySet())));
	}

	private Csp naryProblem() {
		Csp csp = new Csp();
		csp.setDomains(domains);
		csp.setConstraints(setOf(mustHaveDifferentColors(WA, NT, SA),
		                         mustHaveDifferentColors(NT, SA, Q),
		                         mustHaveDifferentColors(SA, Q, NSW),
		                         mustHaveDifferentColors(SA, NSW, V)));
		return csp;
	}

	@Test
	public void equivalentProblemsHaveSameSolution() {
		assertThat(solutionsOf(binaryProblem()), is(equalTo(solutionsOf(naryProblem()))));
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
		red,
		green,
		blue
	}

}

