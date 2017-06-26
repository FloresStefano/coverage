package it.addvalue.coverage.core.model;

import it.addvalue.coverage.bean.Workshift;
import it.addvalue.csp.engine.Value;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(of = "workshift")
public class PlanWorkshift implements Value {

	private final Workshift workshift;
	private final Map<String, int[]> dailySchedule = new HashMap<String, int[]>();

	public Long idWorkshift() {
		return workshift.getId();
	}

	public boolean hasScheduleOn(PlanDay day) {
		return dailySchedule.containsKey(day.getCalendar().getName());
	}

	public int[] dailyScheduleOn(PlanDay day) {
		return dailySchedule.get(day.getCalendar().getName());
	}

	public String toString() {
		return workshift.getName();
	}

}
