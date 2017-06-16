package it.addvalue.csp.engine;

import it.addvalue.csp.collections.BoundedSet;
import it.addvalue.csp.collections.BoundedSortedSet;
import lombok.Data;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <table>
 * <tr>
 * <th>fullSearch</th>
 * <th>maxSolutions</th>
 * <th>costFunction</th>
 * <th>Risultato ricerca</th>
 * <th>Ordinamento</th>
 * </tr>
 * <tr>
 * <td>true</td>
 * <td>N</td>
 * <td>definita</td>
 * <td>le N soluzioni meno costose</td>
 * <td>per costo crescente</td>
 * </tr>
 * <tr>
 * <td>true</td>
 * <td>N</td>
 * <td>non definita</td>
 * <td>N soluzioni qualsiasi fra tutte</td>
 * <td>non predicibile</td>
 * </tr>
 * <tr>
 * <td>*</td>
 * <td>unbounded</td>
 * <td>definita</td>
 * <td>tutte le soluzioni del problema</td>
 * <td>per costo crescente</td>
 * </tr>
 * <tr>
 * <td>*</td>
 * <td>unbounded</td>
 * <td>non definita</td>
 * <td>tutte le soluzioni del problema</td>
 * <td>non predicibile</td>
 * </tr>
 * <tr>
 * <td>false</td>
 * <td>N</td>
 * <td>definita</td>
 * <td>le prime N soluzioni trovate</td>
 * <td>per costo crescente</td>
 * </tr>
 * <tr>
 * <td>false</td>
 * <td>N</td>
 * <td>non definita</td>
 * <td>le prime N soluzioni trovate</td>
 * <td>non predicibile</td>
 * </tr>
 * </table>
 */
@Data
public class Csp implements Cloneable {

	public static final int UNBOUNDED = Integer.MAX_VALUE;

	private Map<Variable, Domain> domains;
	private Set<Constraint>       constraints;
	private CostFunction costFunction = null;
	private int          maxSolutions = UNBOUNDED;
	private boolean      fullSearch   = false;

	public Set<Solution> newSolutionSet() {
		if (hasCostFunctionDefined()) {
			return sortedSolutionSetWithAscendingCost();
		} else {
			return unsortedSolutionSet();
		}
	}

	private boolean hasCostFunctionDefined() {
		return costFunction != null;
	}

	private Set<Solution> sortedSolutionSetWithAscendingCost() {
		return new BoundedSortedSet<Solution>(maxSolutions, ascendingCostComparator());
	}

	private Set<Solution> unsortedSolutionSet() {
		return new BoundedSet<Solution>(maxSolutions);
	}

	private Comparator<Solution> ascendingCostComparator() {
		return new Comparator<Solution>() {

			public int compare(Solution solution1, Solution solution2) {
				return costFunction.evaluate(solution1) - costFunction.evaluate(solution2);
			}

		};
	}

	public Set<Variable> variables() {
		return domains.keySet();
	}

	public boolean verifyConsistency(Solution solution) {
		for (Constraint constraint : constraints) {
			if (solution.isCompleteFor(constraint) && !constraint.verify(solution)) {
				return false;
			}
		}
		return true;
	}

	public Csp restrictDomain(Variable variable, Value value) {
		Csp copy = clone();
		copy.domains.put(variable, Domain.containing(value));
		return copy;
	}

	public Csp clone() {
		try {
			Csp copy = (Csp) super.clone();
			copy.domains = new HashMap<Variable, Domain>(this.domains);
			return copy;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
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
		if (costFunction != null) {
			sb.append("solution cost:\n\t").append(costFunction).append("\n");
		}
		if (maxSolutions < Integer.MAX_VALUE) {
			sb.append("max solutions: ").append(maxSolutions).append("\n");
		}
		sb.append("full search: ").append(fullSearch).append("\n");
		return sb.toString();
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

	public Domain domainOf(Variable variable) {
		return domains.get(variable);
	}

	public boolean reachedSolutionCount(Set<Solution> solutions) {
		return solutions.size() >= maxSolutions;
	}

}
