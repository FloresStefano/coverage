package it.addvalue.csp.engine;

import it.addvalue.csp.collections.BoundedSet;
import it.addvalue.csp.collections.BoundedTreeSet;
import lombok.Data;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
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

	public Set<Solution> createSolutionSet() {
		if (maxSolutions > 0) {
			if (costFunction != null) {
				return newBoundedSortedSolutionSet(maxSolutions, costFunction);
			} else {
				return newBoundedSolutionSet(maxSolutions);
			}
		} else {
			if (costFunction != null) {
				return newSortedSolutionSet(costFunction);
			} else {
				return newSolutionSet();
			}
		}
	}

	private static Set<Solution> newBoundedSortedSolutionSet(int maxSolutions, CostFunction costFunction) {
		return new BoundedTreeSet<Solution>(maxSolutions, new AscendingCostComparator(costFunction));
	}

	private static Set<Solution> newBoundedSolutionSet(int maxSolutions) {
		return new BoundedSet<Solution>(maxSolutions);
	}

	private static SortedSet<Solution> newSortedSolutionSet(CostFunction costFunction) {
		return new TreeSet<Solution>(new AscendingCostComparator(costFunction));
	}

	private static Set<Solution> newSolutionSet() {
		return new HashSet<Solution>();
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
		sb.append("domains:");
		for (Map.Entry<Variable, Domain> entry : domains.entrySet()) {
			sb.append("\n\t").append(entry.getKey()).append(" in ").append(entry.getValue());
		}
		sb.append("\nconstraints:");
		for (Constraint constraint : constraints) {
			sb.append("\n\t").append(constraint);
		}
		if (costFunction != null) {
			sb.append("\nsolution cost:\n\t").append(costFunction);
		}
		if (maxSolutions > 0) {
			sb.append("\nmax solutions: ").append(maxSolutions);
		}
		if (maxIterations > 0) {
			sb.append("\nmax iterations: ").append(maxIterations);
		}
		if (fullSearch) {
			sb.append("\nfull search");
		}
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

	public boolean reachedMaxSolutions(Results results) {
		return maxSolutions > 0 && results.getSolutions().size() >= maxSolutions;
	}

	public boolean reachedMaxIterations(Results results) {
		return maxIterations > 0L && results.getIterations() >= maxIterations;
	}

	@Data
	private static class AscendingCostComparator implements Comparator<Solution> {

		private final CostFunction costFunction;

		public int compare(Solution solution1, Solution solution2) {
			if (solution1.equals(solution2)) {
				return 0;
			}
			int diff = costFunction.evaluate(solution1) - costFunction.evaluate(solution2);
			if (diff != 0) {
				return diff;
			}
			return System.identityHashCode(solution1) - System.identityHashCode(solution2);
		}

	}

}
