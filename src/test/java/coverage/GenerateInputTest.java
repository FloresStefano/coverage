package coverage;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import coverage.mock.CalendarMock;
import coverage.mock.RuleMock;
import coverage.mock.ServiceMock;
import coverage.mock.StaffMock;
import coverage.mock.WorkshiftMock;
import it.addvalue.coverage.Input;
import it.addvalue.coverage.core.XmlUtil;

public class GenerateInputTest {

	@Test
	public void randomDataForCoverageGeneratorTest() throws IOException {

		Input randomInput = new Input();
		randomInput.setCalendarList(CalendarMock.mock());
		randomInput.setRuleList(RuleMock.mock());
		randomInput.setStaffList(StaffMock.mock());
		randomInput.setServiceMap(ServiceMock.serviceMap);
		randomInput.setWorkshiftMap(WorkshiftMock.workshiftMap);

		XmlUtil.serializedToXmlFile(randomInput, "simple_bean.xml");

		Input deserializedInput = (Input) XmlUtil
				.deserializedToXmlFile(Input.class, "simple_bean.xml");

		assertNotNull(deserializedInput);
	}

}