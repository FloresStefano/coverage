package it.addvalue.coverage.core;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.csp.engine.Variable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
public class PlanKey implements Variable {

	private Staff staff;
	private Set<PlanCalendar> calendars = new HashSet<PlanCalendar>();

}
