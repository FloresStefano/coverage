package it.addvalue.coverage;

import it.addvalue.coverage.mock.CalendarMock;
import it.addvalue.coverage.mock.RuleMock;
import it.addvalue.coverage.mock.ServiceMock;
import it.addvalue.coverage.mock.StaffMock;
import it.addvalue.coverage.mock.WorkshiftMock;
import it.addvalue.coverage.utils.XmlUtil;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class GenerateInputTest {

	@Test
	public void randomDataForCoverageGeneratorTest()
	throws IOException {

		Input randomInput = new Input();
		randomInput.setCalendarList(CalendarMock.mock());
		randomInput.setRuleList(RuleMock.mock());
		randomInput.setStaffList(StaffMock.mock());
		randomInput.setServiceMap(ServiceMock.serviceMap);
		randomInput.setWorkshiftMap(WorkshiftMock.workshiftMap);

		XmlUtil.serializedToXmlFile(randomInput, "simple_bean.xml");

		Input deserializedInput = (Input) XmlUtil.deserializedToXmlFile(Input.class, "simple_bean.xml");

		assertNotNull(deserializedInput);
	}

}