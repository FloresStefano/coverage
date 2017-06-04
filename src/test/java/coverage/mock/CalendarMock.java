package coverage.mock;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.core.XmlUtil;

public class CalendarMock {

	private static final int CALENDAR_COUNT = 365;

	public static List<PlanCalendar> mock() {
		List<PlanCalendar> list = new ArrayList<PlanCalendar>();

		for (long i = 0; i < CALENDAR_COUNT; i++) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_YEAR, (int) i); // 0-based
			c.set(Calendar.YEAR, 2017);
			PlanCalendar e = new PlanCalendar();
			String displayName = c.getDisplayName(Calendar.DAY_OF_WEEK,
					Calendar.SHORT, Locale.ITALIAN);
			e.setId(i);
			e.setName(displayName);
			e.setDay(c.getTime());
			e.setWeekOfYear(c.get(Calendar.WEEK_OF_YEAR));
			e.setDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
			e.setExpectedCalls(1000);
			e.setExpectedCallsDetail("100,200,200,200,200,100");
			e.setMarkerList(mockPlanCalendarDetail(i));
			list.add(e);
		}
		return list;
	}

	private static List<PlanCalendarDetail> mockPlanCalendarDetail(long i) {

		ArrayList<PlanCalendarDetail> detailList = new ArrayList<PlanCalendarDetail>();

		Set<Map.Entry<Integer, Service>> entrySet = ServiceMock.serviceMap
				.entrySet();
		for (Map.Entry<Integer, Service> entry : entrySet) {
			Service service = entry.getValue();

			PlanCalendarDetail d = new PlanCalendarDetail();
			d.setDailyCallsMarked(service.getDailyCalls());
			d.setDailyCallsDetailMarked(service.getDailyCallsDetail());
			d.setMarkerName("standard");
			d.setId(i);
			d.setService(service);
			if (i > 350 && i < 360) {
				d.setDailyCallsMarked(service.getDailyCalls() * 2);
				d.setDailyCallsDetailMarked(service.getDailyCallsDetail());
				d.setMarkerName("special");
			}
			detailList.add(d);
		}

		return detailList;
	}

	@Test
	public void testmock() throws IOException {

		assertNotNull(XmlUtil.prettyPrint(mock()));
	}

}
