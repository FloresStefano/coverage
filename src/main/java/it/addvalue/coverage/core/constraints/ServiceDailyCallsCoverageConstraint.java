package it.addvalue.coverage.core.constraints;

import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.core.model.PlanDay;
import it.addvalue.coverage.core.model.PlanStaff;
import it.addvalue.coverage.core.model.PlanVariable;
import it.addvalue.coverage.core.model.PlanWorkshift;
import it.addvalue.csp.engine.Constraint;
import it.addvalue.csp.engine.Solution;
import it.addvalue.csp.engine.Variable;
import lombok.Data;

import java.util.Set;

@Data
public class ServiceDailyCallsCoverageConstraint implements Constraint {

	private final Set<PlanVariable> constrainedVariables;
	private final Service           service;
	private final PlanDay           day;

	public Set<? extends Variable> variables() {
		return constrainedVariables;
	}

	public boolean verify(Solution solution) {
		DailyCallsAccumulator dailyCallsAccumulator = new DailyCallsAccumulator();

		for (PlanVariable variable : constrainedVariables) {
			PlanWorkshift workshift = solution.valueOf(variable);
			PlanStaff staff = variable.getStaff();

			if (staff.canHandle(service) && staff.isPresentOn(day) && workshift.hasScheduleOn(day)) {
				dailyCallsAccumulator.add(workshift.dailyScheduleOn(day), staff.hourlyCallsFor(service));
			}
		}

		return dailyCallsAccumulator.getTotalCalls() >= service.getDailyCalls() &&
		       dailyCallsAccumulator.covers(day.markedDailyCallsDetailFor(service));
	}

}
