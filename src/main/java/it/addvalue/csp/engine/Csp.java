package it.addvalue.csp.engine;

import it.addvalue.csp.collections.BoundedSet;
import it.addvalue.csp.collections.BoundedSortedSet;
import lombok.Data;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * <table border=1>
 * <tr>
 * <th>{@code fullSearch}</th>
 * <th>{@code maxSolutions}</th>
 * <th>{@code costFunction}</th>
 * <th>Solutions found</th>
 * <th>Sorting</th>
 * </tr>
 * <tr>
 * <td>{@code true}</td>
 * <td>{@code n}</td>
 * <td>defined</td>
 * <td>the cheapest {@code n}</td>
 * <td>ascending cost</td>
 * </tr>
 * <tr>
 * <td>{@code true}</td>
 * <td>{@code n}</td>
 * <td>undefined</td>
 * <td>random {@code n}</td>
 * <td>unpredictable</td>
 * </tr>
 * <tr>
 * <td>{@code false}</td>
 * <td>{@code n}</td>
 * <td>defined</td>
 * <td>the first {@code n}</td>
 * <td>ascending cost</td>
 * </tr>
 * <tr>
 * <td>{@code false}</td>
 * <td>{@code n}</td>
 * <td>undefined</td>
 * <td>the first {@code n}</td>
 * <td>unpredictable</td>
 * </tr>
 * <tr>
 * <td>*</td>
 * <td>{@code UNBOUNDED}</td>
 * <td>defined</td>
 * <td>all (!) by distinct cost</td>
 * <td>ascending cost</td>
 * </tr>
 * <tr>
 * <td>*</td>
 * <td>{@code UNBOUNDED}</td>
 * <td>undefined</td>
 * <td>all (!)</td>
 * <td>unpredictable</td>
 * </tr>
 * </table>
 */
@Data
public class Csp implements Cloneable {

	public static final int UNBOUNDED = -1;

	private Map<Variable, Domain> domains       = new HashMap<Variable, Domain>();
	private Set<Constraint>       constraints   = new HashSet<Constraint>();
	private CostFunction          costFunction  = null;
	private int                   maxSolutions  = UNBOUNDED;
	private long                  maxIterations = UNBOUNDED;
	private boolean               fullSearch    = false;

	public Set<Solution> newSolutionSet() {
		if (maxSolutions > 0) {
			if (costFunction != null) {
				return new BoundedSortedSet<Solution>(maxSolutions, ascendingCostComparator());
			} else {
				return new BoundedSet<Solution>(maxSolutions);
			}
		} else {
			if (costFunction != null) {
				return new TreeSet<Solution>(ascendingCostComparator());
			} else {
				return new HashSet<Solution>();
			}
		}
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
			if (solution.isCompleteFor(constraint)) {
				if (!constraint.verify(solution)) {
					Trace.constraintViolated(constraint, solution);
					return false;
				}
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
		if (maxSolutions > 0) {
			sb.append("max solutions: ").append(maxSolutions).append("\n");
		}
		if (maxIterations > 0) {
			sb.append("max iterations: ").append(maxIterations).append("\n");
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

	public boolean reachedMaxSolutions(Set<Solution> solutions) {
		return maxSolutions > 0 && solutions.size() >= maxSolutions;
	}

	public boolean reachedMaxIterations(long numIterations) {
		return maxIterations > 0L && numIterations >= maxIterations;
	}

}
