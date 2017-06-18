package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static it.addvalue.coverage.mock.utils.CsvUtils.csvadd;

public class PlanCalendarRepository {

	public final Map<Long, PlanCalendar> data = new HashMap<Long, PlanCalendar>();
	private      long                    id   = 0;

	public PlanCalendar newItem(int year, int dayOfYear, Set<PlanCalendarDetail> details, Set<Service> services) {
		Calendar c = calendar(year, dayOfYear);

		PlanCalendar item = new PlanCalendar();
		item.setId(id);
		item.setName(c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ITALIAN));
		item.setDay(c.getTime());
		item.setWeekOfYear(c.get(Calendar.WEEK_OF_YEAR));
		item.setDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
		item.setDetails(details);
		item.setTotalExpectedCalls(totalExpectedCallsFor(services));
		item.setTotalExpectedCallsDetail(totalExpectedCallsDetailFor(services));
		data.put(id, item);
		id++;
		return item;
	}

	private Calendar calendar(int year, int dayOfYear) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, dayOfYear); // 0-based
		calendar.set(Calendar.YEAR, year);
		return calendar;
	}

	private int totalExpectedCallsFor(Set<Service> services) {
		int totalExpectedCalls = 0;
		for (Service service : services) {
			totalExpectedCalls += service.getDailyCalls();
		}
		return totalExpectedCalls;
	}

	private String totalExpectedCallsDetailFor(Set<Service> services) {
		String totalExpectedCallsDetail = "0,0,0,0,0,0";
		for (Service service : services) {
			totalExpectedCallsDetail = csvadd(totalExpectedCallsDetail, service.getDailyCallsDetail());
		}
		return totalExpectedCallsDetail;
	}

}
