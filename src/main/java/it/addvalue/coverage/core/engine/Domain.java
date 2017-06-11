package it.addvalue.coverage.core.engine;

import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static it.addvalue.coverage.utils.SetUtils.setOf;

@EqualsAndHashCode(of = "values")
public class Domain implements Iterable<Value> {

	private final Set<Value> values;

	public Domain() {
		this.values = new HashSet<Value>();
	}

	public Domain(Set<Value> values) {
		this.values = new HashSet<Value>(values);
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

	public Iterator<Value> iterator() {
		return values.iterator();
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
