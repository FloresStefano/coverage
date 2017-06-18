package it.addvalue.csp.collections;

import lombok.val;
import org.junit.Test;

import java.util.Set;

import static it.addvalue.csp.collections.Collections.emptySet;
import static it.addvalue.csp.collections.Collections.setOf;
import static it.addvalue.csp.collections.Collections.unorderedPairsFrom;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unchecked")
public class CollectionsTest {

	@Test
	public void testLimitCase() {
		Set<Pair<Integer>> visitedPairs = emptySet();

		for (val pair : unorderedPairsFrom(1, 2)) {
			visitedPairs.add(pair);
		}

		assertThat(visitedPairs, is(equalTo(setOf(pair(1, 2)))));
	}

	private static Pair<Integer> pair(Integer item1, Integer item2) {
		return Pair.unordered(item1, item2);
	}

	@Test
	public void testUnorderedPairs() {
		Set<Pair<Integer>> visitedPairs = emptySet();

		for (val pair : unorderedPairsFrom(1, 2, 3, 4)) {
			visitedPairs.add(pair);
		}

		assertThat(visitedPairs, is(equalTo(setOf(pair(1, 2), //
		                                          pair(1, 3), //
		                                          pair(1, 4), //
		                                          pair(2, 3), //
		                                          pair(2, 4), //
		                                          pair(3, 4)))));
	}

}
