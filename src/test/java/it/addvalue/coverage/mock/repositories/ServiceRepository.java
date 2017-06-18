package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Service;

import java.util.HashMap;
import java.util.Map;

public class ServiceRepository {

	public final Map<Long, Service> data = new HashMap<Long, Service>();
	private      long               id   = 0;

	public Service newItem() {
		Service item = new Service();
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
