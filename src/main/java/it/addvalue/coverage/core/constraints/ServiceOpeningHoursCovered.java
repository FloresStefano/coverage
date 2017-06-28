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
public class ServiceOpeningHoursCovered implements Constraint {

	private final Set<PlanVariable> constrainedVariables;
	private final Service           service;
	private final PlanDay           day;

	public Set<? extends Variable> variables() {
		return constrainedVariables;
	}

	public boolean verify(Solution solution) {
		DailyCoverageMerger dailyCoverageMerger = new DailyCoverageMerger();

		for (PlanVariable variable : constrainedVariables) {
			PlanWorkshift workshift = solution.valueOf(variable);
			PlanStaff staff = variable.getStaff();

			if (staff.canHandle(service) && staff.isPresentOn(day) && workshift.hasScheduleOn(day)) {
				dailyCoverageMerger.add(workshift.dailyScheduleOn(day));
			}
		}

		return dailyCoverageMerger.covers(service.getCoverageFrom(), service.getCoverageTo());
	}

	public String toString() {
		return this.getClass().getSimpleName() + ": " + day + ", " + service.getName();
	}

}
