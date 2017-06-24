package it.addvalue.coverage.core;

import it.addvalue.coverage.Input;
import it.addvalue.coverage.Output;
import it.addvalue.coverage.bean.Allocation;
import it.addvalue.csp.engine.Csp;
import it.addvalue.csp.engine.CspSolver;
import it.addvalue.csp.engine.Solution;
import it.addvalue.csp.engine.Value;
import it.addvalue.csp.engine.Variable;
import lombok.Data;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class CoverageGenerator {

	private int     maxSolutions;
	private boolean fullSearch;

	public Output generate(Input input) {
		Csp csp = cspFrom(input);
		Set<Solution> solutions = solve(csp);
		return outputFrom(solutions);
	}

	private Csp cspFrom(Input input) {
		Context context = new Context(input);
		Csp csp = new Csp();
		csp.setDomains(context.getDomains());
		csp.setConstraints(context.getConstraints());
		csp.setCostFunction(context.getCostFunction());
		csp.setMaxSolutions(maxSolutions);
		csp.setFullSearch(fullSearch);
		return csp;
	}

	private Set<Solution> solve(Csp csp) {
		return new CspSolver().solve(csp);
	}

	private Output outputFrom(Set<Solution> solutions) {
		Output output = new Output();
		for (Solution solution : solutions) {
			Set<Allocation> allocations = new HashSet<Allocation>();
			output.getAllocations().add(allocations);

			for (Map.Entry<Variable, Value> assignmentEntry : solution.assignments().entrySet()) {
				PlanVariable key = (PlanVariable) assignmentEntry.getKey();
				PlanValue value = (PlanValue) assignmentEntry.getValue();

				for (PlanDay day : key.getPlanWeek().getDays()) {
					Allocation allocation = new Allocation();
					allocations.add(allocation);

					allocation.setIdCalendar(day.getPlanCalendar().getId());
					allocation.setIdStaff(key.getPlanStaff().getStaff().getId());
					allocation.setIdWorkShift(value.getWorkshift().getId());
				}
			}
		}
		return output;
	}

}

