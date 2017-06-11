package it.addvalue.coverage.mock;

import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.utils.XmlUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class StaffMock {

	private static final int STAFF_COUNT = 15;

	@Test
	public void testmock()
	throws IOException {
		assertNotNull(XmlUtil.prettyPrint(mock()));
	}

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
}
