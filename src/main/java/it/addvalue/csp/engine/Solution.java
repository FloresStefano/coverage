package it.addvalue.csp.engine;

import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class Solution {

	private final Map<Variable, Value> assignments;

	public Solution() {
		assignments = new HashMap<Variable, Value>();
	}

	private Solution(Map<Variable, Value> assignments) {
		this.assignments = assignments;
	}

	public static Solution empty() {
		return new Solution();
	}

	public static Solution fromMap(Map<Variable, Value> assignments) {
		Solution solution = new Solution();
		solution.assignments.putAll(assignments);
		return solution;
	}

	public boolean assigns(Variable variable) {
		return assignments.containsKey(variable);
	}

	@SuppressWarnings("unchecked")
	public <K extends Variable, V extends Value> Map<K, V> assignments() {
		return (Map<K, V>) Collections.unmodifiableMap(assignments);
	}

	@SuppressWarnings("unchecked")
	public <T extends Value> T valueOf(Variable variable) {
		return (T) assignments.get(variable);
	}

	public boolean isCompleteFor(Csp csp) {
		return assignments.keySet().containsAll(csp.variables());
	}

	public boolean isCompleteFor(Constraint constraint) {
		return assignments.keySet().containsAll(constraint.variables());
	}

	public Solution addAssignment(Variable variable, Value value) {
		Solution result = new Solution();
		result.assignments.putAll(this.assignments);
		result.assignments.put(variable, value);
		return result;
	}

	public String toString() {
		return assignments.toString();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		final Map<Variable, Value> assignments = new HashMap<Variable, Value>();

		public Builder set(Variable variable, Value value) {
			assignments.put(variable, value);
			return this;
		}

		public Solution build() {
			return new Solution(assignments);
		}

	}

}
