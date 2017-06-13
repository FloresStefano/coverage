package it.addvalue.csp.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Collections {

	private Collections() {
	}

	public static <T> T oneOf(Set<T> set) {
		return set.iterator().next();
	}

	public static <T> Set<T> emptySet() {
		return new HashSet<T>();
	}

	@SuppressWarnings("unchecked")
	public static <T> Iterable<Pair<T>> unorderedPairsFrom(Set<T> set) {
		return unorderedPairsFrom((T[]) set.toArray());
	}

	public static <T> Iterable<Pair<T>> unorderedPairsFrom(final T... items) {
		final int imax = items.length - 2;
		final int jmax = items.length - 1;
		return new Iterable<Pair<T>>() {

			private int i = -1;
			private int j = -1;

			public Iterator<Pair<T>> iterator() {
				return new Iterator<Pair<T>>() {

					public boolean hasNext() {
						return (j < jmax) ? i <= imax : i < imax;
					}

					public Pair<T> next() {
						if (i < 0) {
							i = 0;
							j = 1;
						} else if (j == jmax) {
							i++;
							j = i + 1;
						} else {
							j++;
						}
						return Pair.unordered(items[i], items[j]);
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}

				};
			}

		};
	}

	public static <T> Set<T> setOf(T... items) {
		return new HashSet<T>(Arrays.asList(items));
	}

}
