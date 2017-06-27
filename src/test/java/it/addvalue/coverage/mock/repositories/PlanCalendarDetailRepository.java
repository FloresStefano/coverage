package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Service;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class PlanCalendarDetailRepository implements Serializable {

	public final Map<Long, PlanCalendarDetail> data = new HashMap<Long, PlanCalendarDetail>();

	private static final int  NEUTRAL_MARKER_MULTIPLIER = 100;
	private static final long serialVersionUID          = 1L;

	private long id = 0;

	public Insert insert() {
		return new Insert();
	}

	public class Insert {

		private final PlanCalendarDetail item = new PlanCalendarDetail();

		public Insert() {
			item.setMarkerMultiplier(NEUTRAL_MARKER_MULTIPLIER);
		}

		public Insert withService(Service service) {
			item.setIdService(service.getId());
			return this;
		}

		public Insert withMarkerMultiplier(Integer markerMultiplier) {
			item.setMarkerMultiplier(markerMultiplier);
			return this;
		}

		public PlanCalendarDetail commit() {
			item.setId(id);
			data.put(id, item);
			id++;
			return item;
		}

	}

}
