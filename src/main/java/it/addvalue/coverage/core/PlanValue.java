package it.addvalue.coverage.core;

import it.addvalue.coverage.bean.Workshift;
import it.addvalue.csp.engine.Value;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PlanValue implements Value {

	private Workshift workshift;

}
