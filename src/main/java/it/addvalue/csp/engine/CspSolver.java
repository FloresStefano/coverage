package it.addvalue.csp.engine;

import lombok.val;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static it.addvalue.csp.collections.Collections.oneOf;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class CspSolver {

	private boolean useMrv = true;
	private boolean useMcv = true;
	private boolean useMac = true;

	public void useMinimumRemainingValuesPolicy(boolean mrv) {
		this.useMrv = mrv;
	}

	public void useMostConstrainedVariablePolicy(boolean mcv) {
		this.useMcv = mcv;
	}

	public void useMaintainingArcConsistencyPolicy(boolean mac) {
		this.useMac = mac;
	}

	public Set<Solution> solve(Csp csp) {
		val solutions = csp.newSolutionSet();

		solveRecursively(csp, Solution.empty(), solutions);

		return solutions;
	}

	private boolean solveRecursively(Csp csp, Solution solution, Set<Solution> solutions) {
		if (solution.isCompleteFor(csp)) {
			solutions.add(solution);
			return !csp.isFullSearch() && csp.reachedSolutionCount(solutions);
		}

		if (useMac) {
			csp = maintainArcConsistency(csp);
		}
		val variable = selectUnassignedVariable(csp, solution);
		for (val value : csp.domainOf(variable)) {
			val newSolution = solution.addAssignment(variable, value);
			if (csp.verifyConsistency(newSolution)) {
				val newCsp = csp.restrictDomain(variable, value);
				if (solveRecursively(newCsp, newSolution, solutions)) {
					return true;
				}
			}
		}
		return false;
	}

	private Variable selectUnassignedVariable(Csp csp, Solution solution) {
		val variables = new HashSet<Variable>(csp.variables());
		retainUnassignedVariables(variables, solution);

		if (useMrv && variables.size() > 1) {
			retainVariablesWithMinimumRemainingValues(variables, csp.getDomains());
		}

		if (useMcv && variables.size() > 1) {
			retainMostConstrainedVariables(variables, csp.getConstraints());
		}

		return oneOf(variables);
	}

	private void retainUnassignedVariables(Set<Variable> variables, Solution solution) {
		val it = variables.iterator();
		while (it.hasNext()) {
			if (solution.assigns(it.next())) {
				it.remove();
			}
		}
	}

	private void retainVariablesWithMinimumRemainingValues(Set<Variable> variables, Map<Variable, Domain> domains) {
		val minCardinality = minimumCardinality(variables, domains);
		val it = variables.iterator();
		while (it.hasNext()) {
			if (domainCardinality(domains, it.next()) > minCardinality) {
				it.remove();
			}
		}
	}

	private void retainMostConstrainedVariables(Set<Variable> variables, Set<Constraint> constraints) {
		val unassignedVariables = new HashSet<Variable>(variables);
		val maxDegree = maximumDegree(unassignedVariables, constraints);
		val it = variables.iterator();
		while (it.hasNext()) {
			if (variableDegree(unassignedVariables, it.next(), constraints) < maxDegree) {
				it.remove();
			}
		}
	}

	private int minimumCardinality(Set<Variable> unassignedVariables, Map<Variable, Domain> domains) {
		val it = unassignedVariables.iterator();
		int result = domainCardinality(domains, it.next());
		while (it.hasNext()) {
			result = min(result, domainCardinality(domains, it.next()));
		}
		return result;
	}

	private int maximumDegree(Set<Variable> unassignedVariables, Set<Constraint> constraints) {
		val it = unassignedVariables.iterator();
		int maxDegree = variableDegree(unassignedVariables, it.next(), constraints);
		while (it.hasNext()) {
			maxDegree = max(maxDegree, variableDegree(unassignedVariables, it.next(), constraints));
		}
		return maxDegree;
	}

	private int domainCardinality(Map<Variable, Domain> domains, Variable variable) {
		return domains.get(variable).size();
	}

	private int variableDegree(Set<Variable> unassignedVariables,
	                           Variable unassignedVariableToCheck,
	                           Set<Constraint> constraints) {
		int variableDegree = 0;
		for (val constraint : constraints) {
			if (constraint.variables().contains(unassignedVariableToCheck) &&
			    unassignedVariables.containsAll(constraint.variables())) {
				variableDegree++;
			}
		}
		return variableDegree;
	}

	private Csp maintainArcConsistency(Csp csp) {
		csp = csp.clone();
		val constraintsToCheck = new HashSet<Constraint>(csp.getConstraints());

		while (!constraintsToCheck.isEmpty()) {
			val constraint = oneOf(constraintsToCheck);

			for (val restrictedVariable : removeInconsistentValues(csp, constraint)) {
				constraintsToCheck.addAll(csp.constraintsInvolving(restrictedVariable));
			}

			constraintsToCheck.remove(constraint);
		}

		return csp;
	}

	private Set<Variable> removeInconsistentValues(Csp csp, Constraint constraint) {
		val newDomains = new HashMap<Variable, Domain>();
		for (val constraintVariable : constraint.variables()) {
			newDomains.put(constraintVariable, new Domain());
		}
		for (val solution : csp.solutionsFor(constraint)) {
			if (constraint.verify(solution)) {
				for (val assignment : solution.assignments().entrySet()) {
					newDomains.get(assignment.getKey()).add(assignment.getValue());
				}
			}
		}

		val restrictedVariables = new HashSet<Variable>();
		for (val constraintVariable : constraint.variables()) {
			val newDomain = newDomains.get(constraintVariable);
			if (!csp.getDomains().get(constraintVariable).equals(newDomain)) {
				restrictedVariables.add(constraintVariable);
				csp.getDomains().put(constraintVariable, newDomain);
			}
		}
		return restrictedVariables;
	}

}
