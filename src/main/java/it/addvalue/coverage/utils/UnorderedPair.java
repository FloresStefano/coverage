package it.addvalue.coverage.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class UnorderedPair<T> {

	private final T item1;
	private final T item2;

	public int hashCode() {
		return item1.hashCode() ^ item2.hashCode();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;

		} else if (o == null) {
			return false;

		} else if (o.getClass() != UnorderedPair.class) {
			return false;
		}

		UnorderedPair<?> that = (UnorderedPair<?>) o;

		return this.item1.equals(that.item1) && this.item2.equals(that.item2) ||
		       this.item1.equals(that.item2) && this.item2.equals(that.item1);
	}

	public String toString() {
		return "{" + item1 + ", " + item2 + '}';
	}

}
