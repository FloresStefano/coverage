package it.addvalue.coverage.core.engine;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
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
	private Set<Value>            xValues;
	private Set<Value>            yValues;
	private Set<Value>            zValues;
	private Set<Value>            wValues;
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
		domains.put(x, new Domain(xValues = setOf(new TestValue("x1"), new TestValue("x2"), new TestValue("x3"))));
		domains.put(y, new Domain(yValues = setOf(new TestValue("y1"), new TestValue("y2"), new TestValue("y3"))));
		domains.put(z, new Domain(zValues = setOf(new TestValue("z1"), new TestValue("z2"))));
		domains.put(w, new Domain(wValues = setOf(new TestValue("w1"))));

		Solutions solutions = new Solutions(domains);

		Set<Solution> actualSolutions = new HashSet<Solution>();
		for (Solution solution : solutions) {
			System.out.println(solution);
			actualSolutions.add(solution);
		}

		assertThat(actualSolutions, is(equalTo(expectedSolutions())));
	}

	private Set<Value> setOf(Value... values) {
		return new HashSet<Value>(Arrays.asList(values));
	}

	private Set<Solution> expectedSolutions() {
		Set<Solution> expectedSolutions = new HashSet<Solution>();
		for (Value xValue : xValues) {
			for (Value yValue : yValues) {
				for (Value zValue : zValues) {
					for (Value wValue : wValues) {
						Map<Variable, Value> map = new HashMap<Variable, Value>();
						map.put(x, xValue);
						map.put(y, yValue);
						map.put(z, zValue);
						map.put(w, wValue);
						expectedSolutions.add(Solution.fromMap(map));
					}
				}
			}
		}
		return expectedSolutions;
	}

	@Test
	public void testEmptySolution() {
		domains.put(x, new Domain(xValues = setOf(new TestValue("x1"), new TestValue("x2"), new TestValue("x3"))));
		domains.put(y, new Domain(yValues = setOf(new TestValue("y1"), new TestValue("y2"), new TestValue("y3"))));
		domains.put(z, new Domain(zValues = setOf(new TestValue("z1"), new TestValue("z2"))));
		domains.put(w, new Domain(wValues = setOf()));

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

