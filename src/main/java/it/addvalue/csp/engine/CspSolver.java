package it.addvalue.csp.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static it.addvalue.csp.collections.Collections.copySet;
import static it.addvalue.csp.collections.Collections.oneOf;
import static it.addvalue.csp.collections.Collections.shuffle;
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
		Trace.beginSolve(csp);

		Set<Solution> solutions = csp.newSolutionSet();

		solveRecursively(csp, Solution.empty(), solutions);

		Trace.endSolve(solutions);

		return solutions;
	}

	private boolean solveRecursively(Csp csp, Solution solution, Set<Solution> solutions) {
		if (solution.isCompleteFor(csp)) {
			Trace.solutionFound(solution);
			solutions.add(solution);
			return !csp.isFullSearch() && csp.reachedSolutionCount(solutions);
		}

		if (useMac) {
			csp = maintainArcConsistency(csp);
		}
		Variable variable = selectUnassignedVariable(csp, solution);
		for (Value value : shuffle(csp.domainOf(variable))) {
			Solution newSolution = solution.addAssignment(variable, value);
			if (csp.verifyConsistency(newSolution)) {
				Csp newCsp = csp.restrictDomain(variable, value);
				if (solveRecursively(newCsp, newSolution, solutions)) {
					return true;
				}
			}
		}
		return false;
	}

	private Variable selectUnassignedVariable(Csp csp, Solution solution) {
		Set<Variable> variables = copySet(csp.variables());
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
		Iterator<Variable> it = variables.iterator();
		while (it.hasNext()) {
			if (solution.assigns(it.next())) {
				it.remove();
			}
		}
	}

	private void retainVariablesWithMinimumRemainingValues(Set<Variable> variables, Map<Variable, Domain> domains) {
		int minCardinality = minimumCardinality(variables, domains);
		Iterator<Variable> it = variables.iterator();
		while (it.hasNext()) {
			if (domainCardinality(domains, it.next()) > minCardinality) {
				it.remove();
			}
		}
	}

	private void retainMostConstrainedVariables(Set<Variable> variables, Set<Constraint> constraints) {
		Set<Variable> unassignedVariables = copySet(variables);
		int maxDegree = maximumDegree(unassignedVariables, constraints);
		Iterator<Variable> it = variables.iterator();
		while (it.hasNext()) {
			if (variableDegree(unassignedVariables, it.next(), constraints) < maxDegree) {
				it.remove();
			}
		}
	}

	private int minimumCardinality(Set<Variable> unassignedVariables, Map<Variable, Domain> domains) {
		Iterator<Variable> it = unassignedVariables.iterator();
		int result = domainCardinality(domains, it.next());
		while (it.hasNext()) {
			result = min(result, domainCardinality(domains, it.next()));
		}
		return result;
	}

	private int maximumDegree(Set<Variable> unassignedVariables, Set<Constraint> constraints) {
		Iterator<Variable> it = unassignedVariables.iterator();
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
		for (Constraint constraint : constraints) {
			if (constraint.variables().contains(unassignedVariableToCheck) &&
			    unassignedVariables.containsAll(constraint.variables())) {
				variableDegree++;
			}
		}
		return variableDegree;
	}

	private Csp maintainArcConsistency(Csp csp) {
		csp = csp.clone();
		Set<Constraint> constraintsToCheck = copySet(csp.getConstraints());

		while (!constraintsToCheck.isEmpty()) {
			Constraint constraint = oneOf(constraintsToCheck);

			for (Variable restrictedVariable : removeInconsistentValues(csp, constraint)) {
				constraintsToCheck.addAll(csp.constraintsInvolving(restrictedVariable));
			}

			constraintsToCheck.remove(constraint);
		}

		return csp;
	}

	private Set<Variable> removeInconsistentValues(Csp csp, Constraint constraint) {
		Map<Variable, Domain> newDomains = new HashMap<Variable, Domain>();
		for (Variable constraintVariable : constraint.variables()) {
			newDomains.put(constraintVariable, new Domain());
		}
		for (Solution solution : csp.solutionsFor(constraint)) {
			if (constraint.verify(solution)) {
				for (Map.Entry<Variable, Value> assignment : solution.assignments().entrySet()) {
					newDomains.get(assignment.getKey()).add(assignment.getValue());
				}
			} else {
				Trace.constraintViolated(constraint, solution);
			}
		}

		Set<Variable> restrictedVariables = new HashSet<Variable>();
		for (Variable constraintVariable : constraint.variables()) {
			Domain newDomain = newDomains.get(constraintVariable);
			if (!csp.getDomains().get(constraintVariable).equals(newDomain)) {
				restrictedVariables.add(constraintVariable);
				csp.getDomains().put(constraintVariable, newDomain);
			}
		}
		return restrictedVariables;
	}

}
