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
public class TeamLeaderPresenceConstraint implements Constraint {

	private final Set<PlanVariable> constrainedVariables;
	private final Set<Service>      services;
	private final PlanDay           day;

	public Set<? extends Variable> variables() {
		return constrainedVariables;
	}

	public boolean verify(Solution solution) {
		DailyCoverageMerger teamLeaderDailyCoverageMerger = new DailyCoverageMerger();

		for (PlanVariable variable : constrainedVariables) {
			PlanWorkshift workshift = solution.valueOf(variable);
			PlanStaff staff = variable.getStaff();

			if (staff.isTeamLeader() && staff.isPresentOn(day) && workshift.hasScheduleOn(day)) {
				teamLeaderDailyCoverageMerger.add(workshift.dailyScheduleOn(day));
			}
		}

		for (Service service : services) {
			if (!teamLeaderDailyCoverageMerger.covers(service.getCoverageFrom(), service.getCoverageTo())) {
				return false;
			}
		}
		return true;
	}

}
