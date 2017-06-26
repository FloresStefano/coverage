package it.addvalue.coverage.core.model;

import it.addvalue.csp.engine.Variable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PlanVariable implements Variable {

	private final PlanStaff staff;
	private final PlanWeek  week;

	public Long idStaff() {
		return staff.getStaff().getId();
	}

	public String toString() {
		return String.format("{%s, %s}", staff, week);
	}

}
