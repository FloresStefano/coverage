package it.addvalue.coverage.core;

import it.addvalue.coverage.bean.Staff;
import it.addvalue.csp.engine.Variable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
public class PlanKey implements Variable {

	private final Staff        staff;
	private final PlanTimeUnit timeUnit;

}
