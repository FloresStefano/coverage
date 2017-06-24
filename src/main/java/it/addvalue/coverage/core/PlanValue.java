package it.addvalue.coverage.core;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.Workshift;
import it.addvalue.csp.engine.Value;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

import static it.addvalue.coverage.utils.CsvUtils.toIntArray;

@Data
@EqualsAndHashCode
public class PlanValue implements Value {

	private final Workshift workshift;
	private final Map<String, int[]> dailySchedule = new HashMap<String, int[]>();

	public PlanValue(Workshift workshift) {
		this.workshift = workshift;
		for (Map.Entry<String, String> dailyScheduleEntry : workshift.getDailySchedule().entrySet()) {
			String weekDayName = dailyScheduleEntry.getKey();
			String dailySchedule = dailyScheduleEntry.getValue();
			this.dailySchedule.put(weekDayName, toIntArray(dailySchedule));
		}
	}

	public boolean isScheduled(PlanCalendar day) {
		return dailySchedule.containsKey(day.getName());
	}

	public int[] dailyScheduleOn(PlanCalendar day) {
		return dailySchedule.get(day.getName());
	}

}
