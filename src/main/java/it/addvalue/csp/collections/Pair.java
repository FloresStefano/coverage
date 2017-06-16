package it.addvalue.csp.collections;

import lombok.Data;

@Data
public abstract class Pair<T> {

	protected final T item1;
	protected final T item2;

	private Pair(T item1, T item2) {
		this.item1 = item1;
		this.item2 = item2;
	}

	public static <T> Pair<T> ordered(T item1, T item2) {
		return new Pair<T>(item1, item2) {

			public boolean equals(Object o) {
				if (o == this) {
					return true;

				} else if (o == null) {
					return false;

				} else if (o.getClass() != this.getClass()) {
					return false;
				}

				Pair<?> that = (Pair<?>) o;

				return this.item1.equals(that.item1) && this.item2.equals(that.item2);
			}

			public int hashCode() {
				return this.item1.hashCode() * 31 + this.item2.hashCode();
			}

			public String toString() {
				return "[" + this.item1 + ", " + this.item2 + ']';
			}

		};
	}

	public static <T> Pair<T> unordered(T item1, T item2) {
		return new Pair<T>(item1, item2) {

			public boolean equals(Object o) {
				if (o == this) {
					return true;

				} else if (o == null) {
					return false;

				} else if (o.getClass() != this.getClass()) {
					return false;
				}

				Pair<?> that = (Pair<?>) o;

				return this.item1.equals(that.item1) && this.item2.equals(that.item2) ||
				       this.item1.equals(that.item2) && this.item2.equals(that.item1);
			}

			public int hashCode() {
				return this.item1.hashCode() ^ this.item2.hashCode();
			}

			public String toString() {
				return "{" + this.item1 + ", " + this.item2 + '}';
			}

		};
	}

}
