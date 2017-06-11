package it.addvalue.coverage.utils;

import org.junit.Test;

import java.util.Set;

import static it.addvalue.coverage.utils.SetUtils.emptySet;
import static it.addvalue.coverage.utils.SetUtils.setOf;
import static it.addvalue.coverage.utils.SetUtils.unorderedPairsFrom;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unchecked")
public class SetUtilsTest {

	@Test
	public void testLimitCase() {
		Set<UnorderedPair<Integer>> visitedPairs = emptySet();

		for (UnorderedPair<Integer> pair : unorderedPairsFrom(1, 2)) {
			visitedPairs.add(pair);
		}

		assertThat(visitedPairs, is(equalTo(setOf(pair(1, 2)))));
	}

	private static UnorderedPair<Integer> pair(Integer item1, Integer item2) {
		return new UnorderedPair<Integer>(item1, item2);
	}

	@Test
	public void testUnorderedPairs() {
		Set<UnorderedPair<Integer>> visitedPairs = emptySet();

		for (UnorderedPair<Integer> pair : unorderedPairsFrom(1, 2, 3, 4)) {
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
