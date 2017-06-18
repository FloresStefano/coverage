package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Service;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class ServiceRepository implements Serializable {

	public final Map<Long, Service> data = new HashMap<Long, Service>();

	private static final long serialVersionUID = 1L;

	private long id = 0;

	public Service newItem() {
		val item = new Service();
		item.setId(id);
		item.setName("Service" + id);
		item.setCoverageFrom(400);
		item.setCoverageTo(900);
		item.setDailyCalls(1000);
		item.setDailyCallsDetail("200,400,400,400,400,200");
		data.put(id, item);
		id++;
		return item;
	}

}
