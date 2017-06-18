package it.addvalue.coverage.core;

import it.addvalue.coverage.Input;
import it.addvalue.coverage.Output;
import it.addvalue.coverage.bean.Allocation;
import it.addvalue.csp.engine.Constraint;
import it.addvalue.csp.engine.CostFunction;
import it.addvalue.csp.engine.Csp;
import it.addvalue.csp.engine.CspSolver;
import it.addvalue.csp.engine.Domain;
import it.addvalue.csp.engine.Solution;
import it.addvalue.csp.engine.Variable;
import lombok.Data;
import lombok.Getter;
import lombok.val;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class CoverageGenerator {

	private int     maxSolutions;
	private boolean fullSearch;

	public Output generate(Input input) {
		val csp = cspFrom(input);
		val solutions = solve(csp);
		return outputFrom(solutions);
	}

	private Csp cspFrom(Input input) {
		val context = new Context(input);
		val csp = new Csp();
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
		val output = new Output();
		for (val solution : solutions) {
			val allocations = new HashSet<Allocation>();
			output.getAllocations().add(allocations);

			for (val assignmentEntry : solution.assignments().entrySet()) {
				val key = (PlanKey) assignmentEntry.getKey();
				val value = (PlanValue) assignmentEntry.getValue();

				for (val calendar : key.getTimeUnit().getCalendars()) {
					val allocation = new Allocation();
					allocation.setIdCalendar(calendar.getId());
					allocation.setIdStaff(key.getStaff().getId());
					allocation.setIdWorkShift(value.getWorkshift().getId());
					allocations.add(allocation);
				}
			}
		}
		return output;
	}

}

class Context {

	@Getter
	public final Map<Variable, Domain> domains     = new HashMap<Variable, Domain>();
	@Getter
	public final Set<Constraint>       constraints = new HashSet<Constraint>();
	private final Input input;

	public Context(Input input) {
		this.input = input;
		initializeDomains();
		initializeConstraints();
	}

	private void initializeDomains() {
		val planValuesByWorkshiftId = new HashMap<Long, PlanValue>();
		for (val workshift : input.getWorkshifts()) {
			planValuesByWorkshiftId.put(workshift.getId(), new PlanValue(workshift));
		}

		val timeUnits = new HashMap<PlanTimeUnit.Key, PlanTimeUnit>();
		for (val calendar : input.getCalendars()) {
			val timeUnitKey = new PlanTimeUnit.Key(calendar.getWeekyear(), calendar.getWeekOfWeekyear());
			PlanTimeUnit timeUnit = timeUnits.get(timeUnitKey);
			if (timeUnit == null) {
				timeUnit = new PlanTimeUnit(timeUnitKey);
				timeUnits.put(timeUnitKey, timeUnit);
			}
			timeUnit.getCalendars().add(calendar);
		}

		for (val timeUnit : timeUnits.values()) {
			for (val staff : input.getStaffs()) {
				val variable = new PlanKey(staff, timeUnit);

				val domain = new Domain();
				for (val idWorkshift : staff.getIdWorkshifts()) {
					domain.add(planValuesByWorkshiftId.get(idWorkshift));
				}

				domains.put(variable, domain);
			}
		}
	}

	private void initializeConstraints() {
		// TODO
	}

	public CostFunction getCostFunction() {
		// TODO
		return null;
	}

}
