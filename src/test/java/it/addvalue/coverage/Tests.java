package it.addvalue.coverage;

import it.addvalue.coverage.core.CoverageGenerator;
import it.addvalue.coverage.mock.CalendarMock;
import it.addvalue.coverage.mock.RuleMock;
import it.addvalue.coverage.mock.StaffMock;
import it.addvalue.coverage.utils.XmlUtil;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class Tests {

	@Test
	public void coverageGeneratorTest()
	throws IOException {

		Input input = (Input) XmlUtil.deserializedToXmlFile(Input.class, "coverage_data.xml");

		CoverageGenerator generator = new CoverageGenerator();
		Output output = generator.generate(input);
		assertNotNull(output);
		assertNotNull(input);
	}

	@Test
	public void randomDataForCoverageGeneratorTest()
	throws IOException {

		Input originalInput = buildInput();
		Input deserializedInput = null;

		XmlUtil.serializedToXmlFile(originalInput, "simple_bean.xml");
		deserializedInput = (Input) XmlUtil.deserializedToXmlFile(Input.class, "simple_bean.xml");
		assertNotNull(deserializedInput);
	}

	private Input buildInput() {
		Input input = new Input();
		input.setCalendarList(CalendarMock.mock());
		input.setRuleList(RuleMock.mock());
		input.setStaffList(StaffMock.mock());
		return input;
	}

}