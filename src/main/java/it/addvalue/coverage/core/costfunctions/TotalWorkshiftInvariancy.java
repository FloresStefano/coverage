package it.addvalue.coverage.core.costfunctions;

import it.addvalue.coverage.core.model.PlanStaff;
import it.addvalue.coverage.core.model.PlanVariable;
import it.addvalue.coverage.core.model.PlanWeek;
import it.addvalue.coverage.core.model.PlanWorkshift;
import it.addvalue.csp.engine.CostFunction;
import it.addvalue.csp.engine.Solution;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TotalWorkshiftInvariancy implements CostFunction {

	public int evaluate(Solution solution) {
		int totalInvariancy = 0;

		Map<PlanStaff, List<PlanWeekAndWorkshift>> map = new HashMap<PlanStaff, List<PlanWeekAndWorkshift>>();

		for (Map.Entry<PlanVariable, PlanWorkshift> assignment : assignmentsIn(solution)) {
			PlanStaff staff = assignment.getKey().getStaff();
			PlanWeek week = assignment.getKey().getWeek();
			PlanWorkshift workshift = assignment.getValue();

			List<PlanWeekAndWorkshift> planWeekAndWorkshifts = map.get(staff);
			if (planWeekAndWorkshifts == null) {
				planWeekAndWorkshifts = new ArrayList<PlanWeekAndWorkshift>();
				map.put(staff, planWeekAndWorkshifts);
			}
			planWeekAndWorkshifts.add(new PlanWeekAndWorkshift(week, workshift));
		}

		for (List<PlanWeekAndWorkshift> planWeekAndWorkshifts : map.values()) {
			Collections.sort(planWeekAndWorkshifts);

			PlanWorkshift prev = null;
			for (PlanWeekAndWorkshift planWeekAndWorkshift : planWeekAndWorkshifts) {
				if (planWeekAndWorkshift.getWorkshift() == prev) {
					totalInvariancy++;
				}
				prev = planWeekAndWorkshift.getWorkshift();
			}
		}

		return totalInvariancy;
	}

	private Set<Map.Entry<PlanVariable, PlanWorkshift>> assignmentsIn(Solution solution) {
		return solution.<PlanVariable, PlanWorkshift>assignments().entrySet();
	}

	public String toString() {
		return this.getClass().getSimpleName();
	}

	@Data
	private class PlanWeekAndWorkshift implements Comparable<PlanWeekAndWorkshift> {

		private final PlanWeek      week;
		private final PlanWorkshift workshift;

		public int compareTo(PlanWeekAndWorkshift that) {
			return this.week.compareTo(that.week);
		}

	}

}
