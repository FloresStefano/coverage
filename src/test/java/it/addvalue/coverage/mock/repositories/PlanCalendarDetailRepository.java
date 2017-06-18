package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Service;

import java.util.HashMap;
import java.util.Map;

public class PlanCalendarDetailRepository {

	public final Map<Long, PlanCalendarDetail> data = new HashMap<Long, PlanCalendarDetail>();
	private      long                          id   = 0;

	public PlanCalendarDetail newItem(Service service) {
		PlanCalendarDetail item = new PlanCalendarDetail();
		item.setId(id);
		item.setIdService(service.getId());
		item.setMarkerMultiplier("x1");
		data.put(id, item);
		id++;
		return item;
	}

}
