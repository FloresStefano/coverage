package it.addvalue.csp.collections;

import java.util.HashSet;

public class BoundedSet<E> extends HashSet<E> {

	private final int maxSize;

	public BoundedSet(int maxSize) {
		this.maxSize = maxSize;
	}

	public boolean add(E e) {
		boolean modified = super.add(e);

		if (modified && maxSizeExceeded()) {
			remove(anItem());
		}

		return modified;
	}

	private boolean maxSizeExceeded() {
		return size() > maxSize;
	}

	private E anItem() {
		return iterator().next();
	}

}
