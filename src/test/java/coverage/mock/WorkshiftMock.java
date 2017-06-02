package coverage.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import it.addvalue.coverage.bean.Workshift;

public class WorkshiftMock {

	public static final String CONTRACTNAME = "FullTime";
	private static final int WORKSHIFT_COUNT = 3;

	public static List<Workshift> mock() {
		List<Workshift> list = new ArrayList<Workshift>();
		for (long i = 0; i < WORKSHIFT_COUNT; i++) {
			Workshift e = new Workshift();
			e.setId(i);
			e.setName("workshift_" + i);
			e.setContractName(CONTRACTNAME);
			switch ((int)i) {
				case 0 :
					e.setDailySchedule(mockOne(200, 800));
					break;
				case 1 :
					e.setDailySchedule(mockOne(300, 900));
					break;
				case 2 :
					e.setDailySchedule(mockOne(400, 1000));
					break;
			}
			list.add(e);
		}
		return list;
	}

	private static Map<Integer, int[]> mockOne(int min, int max) {

		if ((min + 150) >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Map<Integer, int[]> dailyMap = new HashMap<Integer, int[]>();
		dailyMap.put(0, new int[]{min, min + 150, max - 150, max});
		dailyMap.put(1, new int[]{min, min + 150, max - 150, max});
		dailyMap.put(2, new int[]{min, min + 150, max - 150, max});
		dailyMap.put(3, new int[]{min, min + 150, max - 150, max});
		dailyMap.put(4, new int[]{min, min + 150, max - 150, max});
		dailyMap.put(5, new int[]{0});
		dailyMap.put(6, new int[]{0});
		return dailyMap;
	}
	

    @Test
    public void testmock() throws IOException
    {
        XmlMapper xmlMapper = new XmlMapper();
        // xmlMapper.writeValue(new File("mock.xml"), mock());
        System.out.println(xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mock()));
    }

}
