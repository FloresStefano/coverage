package coverage.mock;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
			String displayName = c.getDisplayName(Calendar.DAY_OF_WEEK,
					Calendar.SHORT, Locale.ITALIAN);

			PlanCalendar e = new PlanCalendar();
			e.setId(i);
			e.setName(displayName);
			e.setDay(c.getTime());
			e.setWeekOfYear(c.get(Calendar.WEEK_OF_YEAR));
			e.setDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
			e.setDetailList(new ArrayList<PlanCalendarDetail>());

			int totalExpectedCalls = 0;
			String totalExpectedCallsDetail = "0,0,0,0,0,0";

			for (String key : ServiceMock.serviceMap.keySet()) {
				Service service = ServiceMock.serviceMap.get(key);

				// PlanCalendarDetail
				PlanCalendarDetail det = new PlanCalendarDetail();
				det.setId(i);
				det.setIdService(service.getId());
				det.setMarkerMultiplier("x1");

				// PlanCalendar
				e.getDetailList().add(det);
				totalExpectedCalls += service.getDailyCalls();
				totalExpectedCallsDetail = csvadd(totalExpectedCallsDetail,
						service.getDailyCallsDetail());
			}

			e.setTotalExpectedCalls(totalExpectedCalls);
			e.setTotalExpectedCallsDetail(totalExpectedCallsDetail);
			list.add(e);
		}
		return list;
	}

	public static String csvadd(String csv1, String csv2) {
		String[] a=csv1.split(",");
		String[] b=csv2.split(",");
		String out="";
		for (int i = 0; i < 5; i++) {
			out+=Integer.parseInt(a[i])+Integer.parseInt(b[i])+",";
			
		}
		return out.substring(0, out.length()-1);
	}
	
	public static int csvsum(String csv) {
		String[] a=csv.split(",");
		int out=0;
		for (int i = 0; i < 5; i++) {
			out+=Integer.parseInt(a[i]);
			
		}
		return out;
	}

	@Test
	public void testmock() throws IOException {

		assertNotNull(XmlUtil.prettyPrint(mock()));
	}

}
