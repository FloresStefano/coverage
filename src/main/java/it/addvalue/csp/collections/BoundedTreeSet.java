package it.addvalue.csp.collections;

import java.util.Comparator;
import java.util.TreeSet;

public class BoundedTreeSet<E> extends TreeSet<E> {

	private final int maxSize;

	public BoundedTreeSet(int maxSize, Comparator<? super E> comparator) {
		super(comparator);
		this.maxSize = maxSize;
	}

	public boolean add(E e) {
		boolean modified = super.add(e);

		if (modified && maxSizeExceeded()) {
			remove(last());
		}

		return modified;
	}

	private boolean maxSizeExceeded() {
		return size() > maxSize;
	}

}
