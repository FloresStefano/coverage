package it.addvalue.csp.engine;

import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Solutions implements Iterable<Solution> {

	private final Map<Variable, Domain> domains;

	public Solutions(Map<Variable, Domain> domains) {
		this.domains = domains;
	}

	public Iterator<Solution> iterator() {
		return emptySolution() ? emptySolutionIterator() : nonEmptySolutionIterator();
	}

	private boolean emptySolution() {
		for (val domain : domains.values()) {
			if (domain.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	private Iterator<Solution> emptySolutionIterator() {
		return new Iterator<Solution>() {
			public boolean hasNext() {
				return false;
			}

			public Solution next() {
				throw new UnsupportedOperationException();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	private Iterator<Solution> nonEmptySolutionIterator() {
		val variableValues = new HashMap<Variable, Value>();
		val variables = new ArrayList<Variable>(domains.keySet());
		val domainIterators = new HashMap<Variable, Iterator<Value>>();
		for (val variable : domains.keySet()) {
			domainIterators.put(variable, newDomainIterator(variable));
		}
		return new Iterator<Solution>() {

			private boolean initialized = false;

			private void nextRecursively(int i) {
				if (i < variables.size()) {
					val variable = variables.get(i);
					Iterator<Value> it = domainIterators.get(variable);
					if (!it.hasNext()) {
						it = newDomainIterator(variable);
						domainIterators.put(variable, it);
						nextRecursively(i + 1);
					} else if (!initialized) {
						nextRecursively(i + 1);
					}
					variableValues.put(variable, it.next());
				}
			}

			public boolean hasNext() {
				for (val it : domainIterators.values()) {
					if (it.hasNext()) {
						return true;
					}
				}
				return false;
			}

			public Solution next() {
				nextRecursively(0);
				initialized = true;
				return Solution.fromMap(variableValues);
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	private Iterator<Value> newDomainIterator(Variable variable) {
		return domains.get(variable).iterator();
	}

}
