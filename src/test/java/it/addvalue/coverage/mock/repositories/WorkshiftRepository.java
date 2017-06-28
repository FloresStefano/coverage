package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Workshift;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class WorkshiftRepository implements Serializable {

	public static final String CONTRACTNAME = "YouWork";

	public final Map<Long, Workshift> data = new HashMap<Long, Workshift>();

	private static final long serialVersionUID = 1L;

	private long id = 0;

	public Insert insert() {
		return new Insert();
	}

	public class Insert {

		private final Workshift item = new Workshift();

		public Insert() {
			item.setContractName(CONTRACTNAME);
			item.setName("Workshift" + id);
		}

		public Insert withName(String name) {
			item.setName(name);
			return this;
		}

		public Insert withContractName(String contractName) {
			item.setContractName(contractName);
			return this;
		}

		public Insert withDailySchedule(String dayOfWeek, String schedule) {
			item.getDailySchedule().put(dayOfWeek, schedule);
			return this;
		}

		public Workshift commit() {
			item.setId(id);
			data.put(id, item);
			id++;
			return item;
		}

	}

}
