package it.addvalue.coverage.core;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Service;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static it.addvalue.coverage.utils.CsvUtils.toIntArray;

@Data
@EqualsAndHashCode(of = "planCalendar")
public class PlanDay {

	private final PlanCalendar planCalendar;
	private final Map<Service, int[]> markedDailyCallsDetailByService = new HashMap<Service, int[]>();

	public int[] markedDailyCallsDetailFor(Service service) {
		return markedDailyCallsDetailByService.get(service);
	}

	public void computeMarkedDailyCallsDetailFor(Service service, PlanCalendarDetail planCalendarDetail) {
		int[] dailyCallsDetail = toIntArray(service.getDailyCallsDetail());
		int[] markedDailyCallsDetail = multiply(dailyCallsDetail, planCalendarDetail.getMarkerMultiplier());
		markedDailyCallsDetailByService.put(service, markedDailyCallsDetail);
	}

	private static int[] multiply(int[] serviceDailyCallsDetail, BigDecimal markerMultiplier) {
		int[] results = new int[serviceDailyCallsDetail.length];
		for (int i = 0; i < serviceDailyCallsDetail.length; i++) {
			results[i] = new BigDecimal(serviceDailyCallsDetail[i]).multiply(markerMultiplier)
			                                                       .divide(new BigDecimal(100),
			                                                               BigDecimal.ROUND_HALF_UP)
			                                                       .intValue();
		}
		return results;
	}

}
