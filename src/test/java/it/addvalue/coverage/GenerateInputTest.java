package it.addvalue.coverage;

import it.addvalue.coverage.mock.repositories.GlobalRepository;
import it.addvalue.coverage.utils.XmlUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class GenerateInputTest {

	private GlobalRepository globalRepository;

	@Before
	public void setup() {
		globalRepository = new GlobalRepository();
	}

	@Test
	public void randomDataForCoverageGeneratorTest()
	throws IOException {

		Input randomInput = new Input();
		randomInput.setCalendars(globalRepository.allCalendars());
		randomInput.setRules(globalRepository.allRules());
		randomInput.setStaffs(globalRepository.allStaffs());
		randomInput.setServices(globalRepository.allServices());
		randomInput.setWorkshifts(globalRepository.allWorkshifts());

		XmlUtil.serialize(randomInput, "simple_bean.xml");

		Input deserializedInput = XmlUtil.deserialize("simple_bean.xml", Input.class);

		assertThat(deserializedInput, is(notNullValue()));
	}

}