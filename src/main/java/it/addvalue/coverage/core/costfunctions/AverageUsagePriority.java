package it.addvalue.coverage.core.costfunctions;

import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.core.model.PlanStaff;
import it.addvalue.coverage.core.model.PlanVariable;
import it.addvalue.coverage.core.model.PlanWorkshift;
import it.addvalue.csp.engine.CostFunction;
import it.addvalue.csp.engine.Solution;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class AverageUsagePriority implements CostFunction {

	private final Set<Service> services;

	public int evaluate(Solution solution) {
		int totalUsagePriority = 0;
		int totalWeight = 0;
		for (Map.Entry<PlanVariable, PlanWorkshift> assignment : assignmentsIn(solution)) {
			PlanStaff staff = assignment.getKey().getStaff();
			PlanWorkshift workshift = assignment.getValue();

			for (Service service : services) {
				if (staff.canHandle(service)) {
					int workshiftWeeklyDuration = workshift.weeklyDuration();
					int usagePriorityForService = staff.usagePriorityFor(service);
					totalUsagePriority += usagePriorityForService * workshiftWeeklyDuration;
					totalWeight += workshiftWeeklyDuration;
				}
			}
		}
		return totalUsagePriority / totalWeight;
	}

	private Set<Map.Entry<PlanVariable, PlanWorkshift>> assignmentsIn(Solution solution) {
		return solution.<PlanVariable, PlanWorkshift>assignments().entrySet();
	}

	public String toString() {
		return this.getClass().getSimpleName();
	}

}
