package it.addvalue.coverage.core.engine;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class Csp {

	private Set<Variable>         variables;
	private Map<Variable, Domain> domains;
	private Set<Constraint>       constraints;

	public boolean verifyConsistency(Solution solution) {
		for (Constraint constraint : constraints) {
			if (solution.isCompleteFor(constraint) && !constraint.verify(solution)) {
				return false;
			}
		}
		return true;
	}

	public Csp restrictDomain(Variable variable, Value value) {
		Csp copy = new Csp();
		copy.variables = variables;
		copy.domains = new HashMap<Variable, Domain>(this.domains);
		copy.domains.put(variable, Domain.singletonSet(value));
		copy.constraints = constraints;
		return copy;
	}

	public Csp clone() {
		Csp copy = new Csp();
		copy.variables = this.variables;
		copy.domains = new HashMap<Variable, Domain>(this.domains);
		copy.constraints = this.constraints;
		return copy;
	}

	public Iterable<Solution> solutionsFor(Constraint constraint) {
		return new Solutions(domainsFor(constraint));
	}

	private Map<Variable, Domain> domainsFor(Constraint constraint) {
		Map<Variable, Domain> result = new HashMap<Variable, Domain>();
		for (Variable constraintVariable : constraint.variables()) {
			result.put(constraintVariable, domains.get(constraintVariable));
		}
		return result;
	}

	public Set<Constraint> constraintsInvolving(Variable variable) {
		Set<Constraint> result = new HashSet<Constraint>();
		for (Constraint constraint : constraints) {
			if (constraint.variables().contains(variable)) {
				result.add(constraint);
			}
		}
		return result;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("domains:\n");
		for (Map.Entry<Variable, Domain> entry : domains.entrySet()) {
			sb.append("\t").append(entry.getKey()).append(" in ").append(entry.getValue()).append("\n");
		}
		sb.append("constraints:\n");
		for (Constraint constraint : constraints) {
			sb.append("\t").append(constraint).append("\n");
		}
		return sb.toString();
	}

}
