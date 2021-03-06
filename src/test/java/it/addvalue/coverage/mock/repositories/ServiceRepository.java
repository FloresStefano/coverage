package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Service;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static it.addvalue.coverage.mock.utils.CsvUtils.csvsum;

@EqualsAndHashCode
public class ServiceRepository implements Serializable {

	public final Map<Long, Service> data = new HashMap<Long, Service>();

	private static final long serialVersionUID = 1L;

	private long id = 0;

	public Insert insert() {
		return new Insert();
	}

	public class Insert {

		private final Service item = new Service();

		public Insert() {
			item.setName("Service" + id);
		}

		public Insert withName(String name) {
			item.setName(name);
			return this;
		}

		public Insert withDailyCalls(int dailyCalls) {
			item.setDailyCalls(dailyCalls);
			return this;
		}

		public Insert withDailyCallsAsSumOfDetails() {
			item.setDailyCalls(csvsum(item.getDailyCallsDetail()));
			return this;
		}

		public Insert withDailyCallsDetail(String dailyCallsDetail) {
			item.setDailyCallsDetail(dailyCallsDetail);
			return this;
		}

		public Insert withCoverageFrom(int coverageFrom) {
			item.setCoverageFrom(coverageFrom);
			return this;
		}

		public Insert withCoverageTo(int coverageTo) {
			item.setCoverageTo(coverageTo);
			return this;
		}

		public Service commit() {
			item.setId(id);
			data.put(id, item);
			id++;
			return item;
		}
	}

}
