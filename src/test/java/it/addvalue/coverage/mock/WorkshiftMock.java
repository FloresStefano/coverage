package it.addvalue.coverage.mock;

import it.addvalue.coverage.bean.Workshift;
import it.addvalue.coverage.utils.XmlUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

public class WorkshiftMock {

	public static final  String CONTRACTNAME    = "FullTime";
	private static final int    WORKSHIFT_COUNT = 4;

	@Test
	public void testmock()
	throws IOException {
		assertNotNull(XmlUtil.prettyPrint(mock()));
	}

	public static List<Workshift> mock() {

		Random random = new Random();
		int randomNumber = random.nextInt(WORKSHIFT_COUNT) + 1;

		List<Workshift> list = new ArrayList<Workshift>();
		for (long i = 0; i < randomNumber; i++) {
			Workshift e = mockOne(i);
			list.add(e);
		}
		return list;
	}

	private static Workshift mockOne(long i) {

		Workshift e = new Workshift();
		e.setId(i);
		e.setName("workshift_" + i);
		e.setContractName(CONTRACTNAME);
		switch ((int) i) {
		case 0:
			e.setDailySchedule(mockOne(200, 800));
			break;
		case 1:
			e.setDailySchedule(mockOne(300, 900));
			break;
		case 2:
			e.setDailySchedule(mockOne(400, 1000));
			break;
		}

		return e;
	}

	private static Map<String, String> mockOne(int min, int max) {

		if ((min + 150) >= max) {
			throw new IllegalArgumentException("max must be greater than min");
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
