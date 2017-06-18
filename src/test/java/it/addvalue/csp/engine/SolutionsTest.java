package it.addvalue.csp.engine;

import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SolutionsTest {

	private Variable              x;
	private Variable              y;
	private Variable              z;
	private Variable              w;
	private Map<Variable, Domain> domains;

	@Before
	public void setup() {
		x = new TestVariable("X");
		y = new TestVariable("Y");
		z = new TestVariable("Z");
		w = new TestVariable("W");
		domains = new HashMap<Variable, Domain>();
	}

	@Test
	public void testNonEmptySolution() {
		domains.put(x, Domain.containing(new TestValue("x1"), new TestValue("x2"), new TestValue("x3")));
		domains.put(y, Domain.containing(new TestValue("y1"), new TestValue("y2"), new TestValue("y3")));
		domains.put(z, Domain.containing(new TestValue("z1"), new TestValue("z2")));
		domains.put(w, Domain.containing(new TestValue("w1")));

		val solutions = new Solutions(domains);

		val actualSolutions = new HashSet<Solution>();
		for (val solution : solutions) {
			System.out.println(solution);
			actualSolutions.add(solution);
		}

		assertThat(actualSolutions, is(equalTo(expectedSolutions())));
	}

	private Set<Solution> expectedSolutions() {
		val expectedSolutions = new HashSet<Solution>();
		for (val xValue : domains.get(x)) {
			for (val yValue : domains.get(y)) {
				for (val zValue : domains.get(z)) {
					for (val wValue : domains.get(w)) {
						expectedSolutions.add(Solution.builder()
						                              .set(x, xValue)
						                              .set(y, yValue)
						                              .set(z, zValue)
						                              .set(w, wValue)
						                              .build());
					}
				}
			}
		}
		return expectedSolutions;
	}

	@Test
	public void testEmptySolution() {
		domains.put(x, Domain.containing(new TestValue("x1"), new TestValue("x2"), new TestValue("x3")));
		domains.put(y, Domain.containing(new TestValue("y1"), new TestValue("y2"), new TestValue("y3")));
		domains.put(z, Domain.containing(new TestValue("z1"), new TestValue("z2")));
		domains.put(w, Domain.empty());

		val solutions = new Solutions(domains);

		val actualSolutions = new HashSet<Solution>();
		for (val solution : solutions) {
			System.out.println(solution);
			actualSolutions.add(solution);
		}

		assertThat(actualSolutions, is(theEmptySet()));
	}

	private Set<Solution> theEmptySet() {
		return new HashSet<Solution>();
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

		private final String value;

		private TestValue(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}

	}

}

