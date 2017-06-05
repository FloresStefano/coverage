package coverage.mock;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import it.addvalue.coverage.bean.Workshift;
import it.addvalue.coverage.core.XmlUtil;

public class WorkshiftMock {

	
	public static final String CONTRACTNAME = "FullTime";
	public static Map<String, Workshift> workshiftMap;
	
	static {
		workshiftMap = new HashMap<String, Workshift>();
		workshiftMap.put("Workshift0", mockWorkshift(0L));
		workshiftMap.put("Workshift1", mockWorkshift(1L));
		workshiftMap.put("Workshift2", mockWorkshift(2L));
		workshiftMap.put("Workshift3", mockWorkshift(3L));
	}
	
	


	public static List<Long> mock() {
		List<Long> idsWorkshift = new ArrayList<Long>();
		for (long i = 0; i < random(1, 3); i++) {
			Random generator = new Random();
			Object[] values = workshiftMap.values().toArray();
			Workshift randomWorkshift = (Workshift) values[generator
					.nextInt(values.length)];
			
			idsWorkshift.add(randomWorkshift.getId());
		}
		

		return idsWorkshift;
	}

	private static Workshift mockWorkshift(long i) {

		Workshift e = new Workshift();
		e.setId(i);
		e.setName("Workshift" + i);
		e.setContractName(CONTRACTNAME);
		switch ((int) i) {
			case 0 :
				e.setDailySchedule(mockWorkshift(200, 800));
				break;
			case 1 :
				e.setDailySchedule(mockWorkshift(300, 900));
				break;
			case 2 :
				e.setDailySchedule(mockWorkshift(400, 1000));
				break;
			default:	
				e.setDailySchedule(mockWorkshift(400, 1000));
				break;
		}

		return e;
	}

	private static Map<String, String> mockWorkshift(int min, int max) {

		if ((min + 150) >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Map<String, String> dailyMap = new HashMap<String, String>();
		dailyMap.put("LUN",
				min + "," + (min + 150) + "," + (max - 150) + "," + max);
		dailyMap.put("MAR",
				min + "," + (min + 150) + "," + (max - 150) + "," + max);
		dailyMap.put("MER",
				min + "," + (min + 150) + "," + (max - 150) + "," + max);
		dailyMap.put("GIO",
				min + "," + (min + 150) + "," + (max - 150) + "," + max);
		dailyMap.put("VEN",
				min + "," + (min + 150) + "," + (max - 150) + "," + max);
		return dailyMap;
	}

	private static int random(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	@Test
	public void testmock() throws IOException {
		assertNotNull(XmlUtil.prettyPrint(mock()));
	}

}
