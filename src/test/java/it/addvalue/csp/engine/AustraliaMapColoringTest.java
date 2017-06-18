package it.addvalue.csp.engine;

import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static it.addvalue.csp.collections.Collections.setOf;
import static it.addvalue.csp.collections.Collections.unorderedPairsFrom;
import static it.addvalue.csp.engine.AustraliaMapColoringTest.State.NSW;
import static it.addvalue.csp.engine.AustraliaMapColoringTest.State.NT;
import static it.addvalue.csp.engine.AustraliaMapColoringTest.State.Q;
import static it.addvalue.csp.engine.AustraliaMapColoringTest.State.SA;
import static it.addvalue.csp.engine.AustraliaMapColoringTest.State.V;
import static it.addvalue.csp.engine.AustraliaMapColoringTest.State.WA;
import static it.addvalue.csp.engine.CspSolverTestUtils.are;
import static it.addvalue.csp.engine.CspSolverTestUtils.theSolutionsOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class AustraliaMapColoringTest {

	private Map<Variable, Domain> domains;

	@Before
	public void setup() {
		domains = new HashMap<Variable, Domain>();
		for (val state : State.values()) {
			domains.put(state, Domain.containing(Color.values()));
		}
	}

	@Test
	public void findColoringsWithBinaryConstraints() {
		assertThat(theSolutionsOf(theAustraliaColoringBinaryProblem()), are(not(theEmptySet())));
	}

	private Csp theAustraliaColoringBinaryProblem() {
		val csp = new Csp();
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

	private static Set<Solution> theEmptySet() {
		return new HashSet<Solution>();
	}

	private static Constraint mustHaveDifferentColors(final Variable... states) {
		val stateSet = setOf(states);
		return new Constraint() {

			public Set<Variable> variables() {
				return stateSet;
			}

			public boolean verify(Solution solution) {
				for (val pair : unorderedPairsFrom(states)) {
					val color1 = solution.evaluate(pair.getItem1());
					val color2 = solution.evaluate(pair.getItem2());
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
	public void findColoringsWithNaryConstraints() {
		assertThat(theSolutionsOf(theAustraliaColoringNaryProblem()), are(not(theEmptySet())));
	}

	private Csp theAustraliaColoringNaryProblem() {
		val csp = new Csp();
		csp.setDomains(domains);
		csp.setConstraints(setOf(mustHaveDifferentColors(WA, NT, SA),
		                         mustHaveDifferentColors(NT, SA, Q),
		                         mustHaveDifferentColors(SA, Q, NSW),
		                         mustHaveDifferentColors(SA, NSW, V)));
		return csp;
	}

	@Test
	public void verifySolverCorrectnessWithNaryConstraints() {
		assertThat(theSolutionsOf(theAustraliaColoringNaryProblem()),
		           are(equalTo(theSolutionsOf(theAustraliaColoringBinaryProblem()))));
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

