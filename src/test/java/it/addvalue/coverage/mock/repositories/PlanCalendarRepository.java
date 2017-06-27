package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.mock.utils.Iso8601Utils;
import lombok.EqualsAndHashCode;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@EqualsAndHashCode
public class PlanCalendarRepository implements Serializable {

	public final Map<Long, PlanCalendar> data = new HashMap<Long, PlanCalendar>();

	private static final long serialVersionUID = 1L;

	private long id = 0;

	public Insert insert() {
		return new Insert();
	}

	public class Insert {

		private final PlanCalendar item = new PlanCalendar();

		public Insert withDay(String iso8601Date) {
			return withDay(new LocalDate(Iso8601Utils.parse(iso8601Date)));
		}

		public Insert withDay(LocalDate day) {
			item.setDay(day.toDate());
			item.setName(day.toString("EEE", Locale.ITALIAN));
			item.setDayOfWeek(day.getDayOfWeek());
			item.setWeekOfWeekyear(day.getWeekOfWeekyear());
			item.setWeekyear(day.getWeekyear());
			return this;
		}

		public Insert withTotalExpectedCalls(Integer totalExpectedCalls) {
			item.setTotalExpectedCalls(totalExpectedCalls);
			return this;
		}

		public Insert withTotalExpectedCallsDetail(String totalExpectedCallsDetail) {
			item.setTotalExpectedCallsDetail(totalExpectedCallsDetail);
			return this;
		}

		public Insert withDetail(PlanCalendarDetail planCalendarDetail) {
			item.getDetails().add(planCalendarDetail);
			return this;
		}

		public Insert withDetails(Iterable<PlanCalendarDetail> details) {
			for (PlanCalendarDetail detail : details) {
				item.getDetails().add(detail);
			}
			return this;
		}

		public PlanCalendar commit() {
			item.setId(id);
			data.put(id, item);
			id++;
			return item;
		}

	}

}
