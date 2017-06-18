package it.addvalue.coverage.mock.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomUtils {

	private static final Random generator = new Random();

	public static <T> Set<T> randomItemsIn(Collection<T> collection, int count) {
		count = Math.min(count, collection.size());
		Set<T> result = new HashSet<T>();
		while (result.size() < count) {
			result.add(randomItemIn(collection));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T randomItemIn(Collection<T> collection) {
		Object[] items = collection.toArray();
		return (T) items[generator.nextInt(items.length)];
	}

	public static int randomInRange(int minInclusive, int maxInclusive) {
		return generator.nextInt((maxInclusive - minInclusive) + 1) + minInclusive;
	}

}
