package it.addvalue.coverage.core;

import it.addvalue.csp.engine.Variable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PlanVariable implements Variable {

	private final PlanStaff planStaff;
	private final PlanWeek  planWeek;

}
