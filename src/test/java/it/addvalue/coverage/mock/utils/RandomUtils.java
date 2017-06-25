package it.addvalue.coverage.mock.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomUtils {

	private static final Random generator = new Random();

	private RandomUtils() {
	}

	public static <T> Set<T> randomItemsIn(Collection<T> collection, int count) {
		List<T> destructibleList = listFrom(collection);
		Set<T> result = new HashSet<T>();
		while (result.size() < count && !destructibleList.isEmpty()) {
			result.add(removeRandomItemIn(destructibleList));
		}
		return result;
	}

	private static <T> ArrayList<T> listFrom(Collection<T> collection) {
		return new ArrayList<T>(collection);
	}

	public static <T> T removeRandomItemIn(List<T> list) {
		return list.remove(randomIndexOf(list));
	}

	public static <T> int randomIndexOf(Collection<T> list) {
		return generator.nextInt(list.size());
	}

	public static <T> T randomItemIn(Collection<T> collection) {
		return randomItemIn(arrayFrom(collection));
	}

	public static <T> T randomItemIn(T... items) {
		return items[randomIndexOf(items)];
	}

	@SuppressWarnings("unchecked")
	private static <T> T[] arrayFrom(Collection<T> collection) {
		return (T[]) collection.toArray();
	}

	public static <T> int randomIndexOf(T... items) {
		return generator.nextInt(items.length);
	}

	public static int randomInRangeInclusively(int minInclusive, int maxInclusive) {
		return generator.nextInt((maxInclusive - minInclusive) + 1) + minInclusive;
	}

}
