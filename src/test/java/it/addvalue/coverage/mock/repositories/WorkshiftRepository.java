package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Workshift;

import java.util.HashMap;
import java.util.Map;

public class WorkshiftRepository {

	public static final String CONTRACTNAME = "FullTime";

	public final Map<Long, Workshift> data = new HashMap<Long, Workshift>();
	private      long                 id   = 0;

	public Workshift newItem() {
		Workshift item = new Workshift();
		item.setId(id);
		item.setName("Workshift" + id);
		item.setContractName(CONTRACTNAME);
		item.setDailySchedule(dailySchedule(id));
		data.put(id, item);
		id++;
		return item;
	}

	private Map<String, String> dailySchedule(long idWorkshift) {
		int[] min = {200, 300, 400, 400};
		int[] max = {800, 900, 1000, 1000};
		int mod = (int) (idWorkshift % 4);
		return mockDailySchedule(min[mod], max[mod]);
	}

	private static Map<String, String> mockDailySchedule(int min, int max) {
		if ((min + 150) >= max) {
			throw new IllegalArgumentException("max must be <= min+150");
		}
		Map<String, String> dailyMap = new HashMap<String, String>();
		dailyMap.put("LUN", min + "," + (min + 150) + "," + (max - 150) + "," + max);
		dailyMap.put("MAR", min + "," + (min + 150) + "," + (max - 150) + "," + max);
		dailyMap.put("MER", min + "," + (min + 150) + "," + (max - 150) + "," + max);
		dailyMap.put("GIO", min + "," + (min + 150) + "," + (max - 150) + "," + max);
		dailyMap.put("VEN", min + "," + (min + 150) + "," + (max - 150) + "," + max);
		return dailyMap;
	}

}