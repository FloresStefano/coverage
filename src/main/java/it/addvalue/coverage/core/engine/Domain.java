package it.addvalue.coverage.core.engine;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(of = "values")
public class Domain implements Iterable<Value> {

	private final Set<Value> values;

	public Domain() {
		this.values = new HashSet<Value>();
	}

	public Domain(Set<Value> values) {
		this.values = new HashSet<Value>(values);
	}

	public static Domain singletonSet(Value value) {
		return new Domain(Collections.singleton(value));
	}

	public void add(Value value) {
		values.add(value);
	}

	public Iterator<Value> iterator() {
		return values.iterator();
	}

	public Iterable<Value> sortedBy(Comparator<Value> heuristic) {
		List<Value> list = new ArrayList<Value>(values);
		Collections.sort(list, heuristic);
		return list;
	}

	public int size() {
		return values.size();
	}

	public boolean isEmpty() {
		return values.isEmpty();
	}

	public Domain clone() {
		return new Domain(this.values);
	}

	public String toString() {
		return values.toString();
	}

}
