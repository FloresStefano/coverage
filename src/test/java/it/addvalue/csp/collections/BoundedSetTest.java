package it.addvalue.csp.collections;

import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class BoundedSetTest {

	@Test
	public void neverExceedsMaxSize() {
		Set<String> set = new BoundedSet<String>(4);

		set.add("one");
		set.add("two");
		set.add("three");
		set.add("four");
		set.add("five");

		System.out.println(set);
		assertThat(set, hasSize(equalTo(4)));
	}

}
