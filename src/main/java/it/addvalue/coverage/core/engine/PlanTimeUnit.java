package it.addvalue.coverage.core.engine;

import it.addvalue.coverage.bean.PlanCalendar;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class PlanTimeUnit {

	private final List<PlanCalendar> calendarList;

}
