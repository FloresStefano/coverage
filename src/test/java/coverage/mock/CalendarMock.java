package coverage.mock;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarMarker;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.engine.XmlUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.Assert.assertNotNull;

public class CalendarMock {

	private static final int CALENDAR_COUNT = 365;

	public static List<PlanCalendar> mock() {
		List<PlanCalendar> list = new ArrayList<PlanCalendar>();

		for (long i = 1; i < CALENDAR_COUNT; i++) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_YEAR, (int) i); // 0-based
			c.set(Calendar.YEAR, 2017);
			PlanCalendar e = new PlanCalendar();
			String displayName = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ITALIAN);
			e.setId(i);
			e.setName(displayName);
			e.setDay(c.getTime());
			e.setWeekOfYear(c.get(Calendar.WEEK_OF_YEAR));
			e.setDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
			e.setExpectedCalls(1000);
			e.setExpectedCallsDetail("100,200,200,200,200,100");
			e.setMarkerList(mockMarker(i));
			list.add(e);
		}
		return list;
	}

	@Test
	public void testmock()
	throws IOException {

		assertNotNull(XmlUtil.prettyPrint(mock()));
	}

	private static ArrayList<PlanCalendarMarker> mockMarker(long i) {

		ArrayList<PlanCalendarMarker> markerList = new ArrayList<PlanCalendarMarker>();
		Iterator<Entry<Integer, Service>> it = ServiceMock.serviceMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry map = (Map.Entry) it.next();
			Service service = (Service) map.getValue();

			PlanCalendarMarker m = new PlanCalendarMarker();
			m.setDailyCallsMarked(service.getDailyCalls());
			m.setDailyCallsDetailMarked(service.getDailyCallsDetail());
			m.setId(i);
			m.setIdService(service.getId());
			m.setValue("standard");
			markerList.add(m);
		}

		if (i > 350 && i < 360) {

		}

		return markerList;
	}

}
