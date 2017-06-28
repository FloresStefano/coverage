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
public class TotalExpectedDailyCallsCovered implements Constraint {

	private final Set<PlanVariable> constrainedVariables;
	private final Set<Service>      services;
	private final PlanDay           day;

	public Set<? extends Variable> variables() {
		return constrainedVariables;
	}

	public boolean verify(Solution solution) {
		DailyCallsAccumulator dailyCallsAccumulator = new DailyCallsAccumulator();

		for (PlanVariable variable : constrainedVariables) {
			PlanWorkshift workshift = solution.valueOf(variable);
			PlanStaff staff = variable.getStaff();

			for (Service service : services) {
				if (staff.canHandle(service) && staff.isPresentOn(day) && workshift.hasScheduleOn(day)) {
					int[] dailySchedule = workshift.dailyScheduleOn(day);

					dailyCallsAccumulator.add(dailySchedule, staff.hourlyCallsFor(service));
				}
			}
		}

		return dailyCallsAccumulator.getTotalCalls() >= day.getCalendar().getTotalExpectedCalls() &&
		       dailyCallsAccumulator.covers(day.getTotalExpectedCallsDetail());
	}

	public String toString() {
		return this.getClass().getSimpleName() + ": " + day;
	}

}
