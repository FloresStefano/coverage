package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Service;
import lombok.EqualsAndHashCode;
import lombok.val;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static it.addvalue.coverage.mock.utils.CsvUtils.csvadd;

@EqualsAndHashCode
public class PlanCalendarRepository implements Serializable {

	public final Map<Long, PlanCalendar> data = new HashMap<Long, PlanCalendar>();

	private static final long serialVersionUID = 1L;

	private long id = 0;

	public PlanCalendar newItem(LocalDate date, Set<PlanCalendarDetail> details, Set<Service> services) {
		val item = new PlanCalendar();
		item.setId(id);
		item.setName(date.toString("EEE", Locale.ITALIAN));
		item.setDay(date.toDate());
		item.setDayOfWeek(date.getDayOfWeek());
		item.setWeekOfWeekyear(date.getWeekOfWeekyear());
		item.setWeekyear(date.getWeekyear());
		item.setDetails(details);
		item.setTotalExpectedCalls(totalExpectedCallsFor(services));
		item.setTotalExpectedCallsDetail(totalExpectedCallsDetailFor(services));
		data.put(id, item);
		id++;
		return item;
	}

	private int totalExpectedCallsFor(Set<Service> services) {
		int totalExpectedCalls = 0;
		for (val service : services) {
			totalExpectedCalls += service.getDailyCalls();
		}
		return totalExpectedCalls;
	}

	private String totalExpectedCallsDetailFor(Set<Service> services) {
		String totalExpectedCallsDetail = "0,0,0,0,0,0";
		for (val service : services) {
			totalExpectedCallsDetail = csvadd(totalExpectedCallsDetail, service.getDailyCallsDetail());
		}
		return totalExpectedCallsDetail;
	}

}
