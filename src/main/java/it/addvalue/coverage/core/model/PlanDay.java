package it.addvalue.coverage.core.model;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.Service;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(of = "calendar")
@ToString(of = "calendar")
public class PlanDay {

	private final PlanCalendar calendar;
	private final int[]        totalExpectedCallsDetail;
	private final Map<Service, int[]> markedDailyCallsDetailByService = new HashMap<Service, int[]>();

	public Long idCalendar() {
		return calendar.getId();
	}

	public int[] markedDailyCallsDetailFor(Service service) {
		return markedDailyCallsDetailByService.get(service);
	}

	public boolean beforeInclusively(Date limit) {
		return calendar.getDay().compareTo(limit) <= 0;
	}

	public boolean beforeExclusively(Date limit) {
		return calendar.getDay().compareTo(limit) < 0;
	}

	public boolean afterInclusively(Date limit) {
		return calendar.getDay().compareTo(limit) >= 0;
	}

	public boolean afterExclusively(Date limit) {
		return calendar.getDay().compareTo(limit) > 0;
	}

}
