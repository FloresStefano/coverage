package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Service;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class PlanCalendarDetailRepository implements Serializable {

	public final Map<Long, PlanCalendarDetail> data = new HashMap<Long, PlanCalendarDetail>();

	private static final BigDecimal NEUTRAL          = BigDecimal.valueOf(100L);
	private static final long       serialVersionUID = 1L;

	private long id = 0;

	public PlanCalendarDetail newItem(Service service) {
		PlanCalendarDetail item = new PlanCalendarDetail();
		item.setId(id);
		item.setIdService(service.getId());
		item.setMarkerMultiplier(NEUTRAL);
		data.put(id, item);
		id++;
		return item;
	}

}
