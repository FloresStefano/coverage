package coverage.mock;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.core.XmlUtil;

public class StaffMock {

	private static final int STAFF_COUNT = 15;

	public static List<Staff> mock() {
		List<Staff> list = new ArrayList<Staff>();
		for (long i = 0; i < STAFF_COUNT; i++) {
			Staff e = new Staff();
			e.setId(i);
			e.setName("staff_" + i);
			e.setContractName(WorkshiftMock.CONTRACTNAME);
			e.setIdTeam(0L);
			e.setSkillList(ServiceMock.skillMock());
			e.setIdsWorkshift(WorkshiftMock.mock());
			list.add(e);
		}
		return list;
	}

	@Test
	public void testmock() throws IOException {
		assertNotNull(XmlUtil.prettyPrint(mock()));
	}
}
