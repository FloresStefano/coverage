package it.addvalue.coverage;

import it.addvalue.coverage.mock.repositories.GlobalRepository;
import it.addvalue.coverage.mock.utils.BinaryUtils;
import it.addvalue.coverage.mock.utils.XmlUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GenerateInputTest {

	private GlobalRepository globalRepository;

	@Before
	public void setup() {
		globalRepository = new GlobalRepository();
	}

	@Test
	public void randomDataForCoverageGeneratorTest() {
		globalRepository.populate();

		Input randomInput = new Input();
		randomInput.setCalendars(globalRepository.allCalendars());
		randomInput.setRules(globalRepository.allRules());
		randomInput.setStaffs(globalRepository.allStaffs());
		randomInput.setServices(globalRepository.allServices());
		randomInput.setWorkshifts(globalRepository.allWorkshifts());

		XmlUtils.serialize(randomInput, "coverage_data.xml");
		BinaryUtils.serialize(globalRepository, "repository_data.bin");

		Input deserializedInput = XmlUtils.deserialize("coverage_data.xml", Input.class);
		GlobalRepository deserializedRepository =
				BinaryUtils.deserialize("repository_data.bin", GlobalRepository.class);

		assertThat(deserializedInput, is(equalTo(randomInput)));
		assertThat(deserializedRepository, is(equalTo(globalRepository)));
	}

}