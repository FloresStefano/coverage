package it.addvalue.coverage.core;

import it.addvalue.coverage.bean.PlanCalendar;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static it.addvalue.csp.collections.Collections.setOf;

public class PlanWeekSetBuilder {

	private final Map<Key, PlanWeek> map = new HashMap<Key, PlanWeek>();

	public PlanWeek add(PlanCalendar planCalendar) {
		Key key = Key.builder()
		             .weekyear(planCalendar.getWeekyear())
		             .weekOfWeekyear(planCalendar.getWeekOfWeekyear())
		             .build();
		PlanWeek planWeek = map.get(key);
		if (planWeek == null) {
			planWeek = PlanWeek.builder()
			                   .weekyear(planCalendar.getWeekyear())
			                   .weekOfWeekyear(planCalendar.getWeekOfWeekyear())
			                   .build();
			map.put(key, planWeek);
		}
		return planWeek;
	}

	public Set<PlanWeek> planWeeks() {
		return setOf(map.values());
	}

	@Data
	@Builder
	@EqualsAndHashCode
	public static class Key {
		private final int weekyear;
		private final int weekOfWeekyear;
	}

}
