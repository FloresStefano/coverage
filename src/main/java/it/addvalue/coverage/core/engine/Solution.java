package it.addvalue.coverage.core.engine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Solution<K extends PlanKey, V extends PlanValue> {

	private final Stack<Assignment> stack       = new Stack<Assignment>();
	private final Map<K, V>         assignments = new HashMap<K, V>();
	private final Set<K> keys;
	private final Set<V> values;

	public Solution(Set<K> keys, Set<V> values) {
		this.keys = keys;
		this.values = values;
	}

	public void set(K key, V value) {
		stack.push(new Assignment(key, value));
		assignments.put(key, value);
	}

	public void backtrack() {
		Assignment assignment = stack.pop();
		assignments.remove(assignment.key);
	}

	public boolean isComplete() {
		return assignments.keySet().equals(keys);
	}

	public Map<K, V> toMap() {
		return Collections.unmodifiableMap(assignments);
	}

	private class Assignment {

		public final K key;
		public final V value;

		private Assignment(K key, V value) {
			this.key = key;
			this.value = value;
		}

	}

}
