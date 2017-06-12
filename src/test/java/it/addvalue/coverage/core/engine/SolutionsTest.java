package it.addvalue.coverage.core.engine;

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

		Solutions solutions = new Solutions(domains);

		Set<Solution> actualSolutions = new HashSet<Solution>();
		for (Solution solution : solutions) {
			System.out.println(solution);
			actualSolutions.add(solution);
		}

		assertThat(actualSolutions, is(equalTo(expectedSolutions())));
	}

	private Set<Solution> expectedSolutions() {
		Set<Solution> expectedSolutions = new HashSet<Solution>();
		for (Value xValue : domains.get(x)) {
			for (Value yValue : domains.get(y)) {
				for (Value zValue : domains.get(z)) {
					for (Value wValue : domains.get(w)) {
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

		Solutions solutions = new Solutions(domains);

		Set<Solution> actualSolutions = new HashSet<Solution>();
		for (Solution solution : solutions) {
			System.out.println(solution);
			actualSolutions.add(solution);
		}

		assertThat(actualSolutions, is(emptySet()));
	}

	private Set<Solution> emptySet() {
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

