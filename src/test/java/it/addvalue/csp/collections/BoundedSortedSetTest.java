package it.addvalue.csp.collections;

import org.junit.Test;

import java.util.Set;

import static it.addvalue.csp.collections.Collections.defaultComparatorFor;
import static it.addvalue.csp.engine.CspSolverTestUtils.inOrderEqualTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BoundedSortedSetTest {

	@Test
	public void collectsTheLesserItemsNeverExceedingMaxSize() {
		Set<String> set = new BoundedSortedSet<String>(4, defaultComparatorFor(String.class));

		set.add("bravo");
		set.add("delta");
		set.add("alpha");
		set.add("echo");
		set.add("golf");
		set.add("hotel");
		set.add("charlie");
		set.add("foxtrot");

		assertThat(set, is(inOrderEqualTo("alpha", "bravo", "charlie", "delta")));
	}

}
