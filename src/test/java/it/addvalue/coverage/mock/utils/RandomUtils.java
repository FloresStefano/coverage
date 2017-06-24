package it.addvalue.coverage.mock.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomUtils {

	private static final Random generator = new Random();

	private RandomUtils() {
	}

	public static <T> Set<T> randomItemsIn(Collection<T> collection, int count) {
		count = Math.min(count, collection.size());
		Set<T> result = new HashSet<T>();
		T[] array = arrayFrom(collection);
		while (result.size() < count) {
			result.add(randomItemIn(array));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private static <T> T[] arrayFrom(Collection<T> collection) {
		return (T[]) collection.toArray();
	}

	public static <T> T randomItemIn(T... items) {
		return items[generator.nextInt(items.length)];
	}

	public static <T> T randomItemIn(Collection<T> collection) {
		return randomItemIn(arrayFrom(collection));
	}

	public static int randomInRange(int minInclusive, int maxInclusive) {
		return generator.nextInt((maxInclusive - minInclusive) + 1) + minInclusive;
	}

}
