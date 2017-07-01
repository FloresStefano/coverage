package it.addvalue.csp.engine;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static it.addvalue.csp.collections.Collections.copySet;
import static it.addvalue.csp.collections.Collections.setOf;

@EqualsAndHashCode(of = "values")
public class Domain implements Iterable<Value> {

	private final Set<Value> values;

	public Domain() {
		this.values = new HashSet<Value>();
	}

	public Domain(Set<Value> values) {
		this.values = copySet(values);
	}

	public static Domain containing(Value... values) {
		return new Domain(setOf(values));
	}

	public static Domain empty() {
		return new Domain();
	}

	public void add(Value value) {
		values.add(value);
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
		Iterator<Value> it = iterator();
		if (!it.hasNext()) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for (; ; ) {
			sb.append(it.next());
			if (!it.hasNext()) {
				return sb.append('}').toString();
			}
			sb.append(',').append(' ');
		}
	}

	public Iterator<Value> iterator() {
		return values.iterator();
	}

	public Iterable<Value> shuffled() {
		List<Value> list = new ArrayList<Value>(values);
		java.util.Collections.shuffle(list);
		return list;
	}

}
