package it.addvalue.coverage.core;

import it.addvalue.coverage.bean.PlanCalendar;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = "key")
public class PlanTimeUnit {

	private final Key key;
	private final Set<PlanCalendar> calendars = new HashSet<PlanCalendar>();

	@EqualsAndHashCode
	@RequiredArgsConstructor
	public static class Key {
		private final int weekyear;
		private final int weekOfWeekyear;
	}

}
