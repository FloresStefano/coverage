package it.addvalue.coverage;

import it.addvalue.coverage.bean.Input;
import it.addvalue.coverage.mock.repositories.Database;
import it.addvalue.coverage.mock.utils.BinaryUtils;
import it.addvalue.coverage.mock.utils.XmlUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GenerateInputTest {

	private Database db;

	@Before
	public void setupRepository() {
		db = new Database();
	}

	@Test
	public void prepareDataAndEnsureSerializability() {
		TestSet.insertSimpleScenario(db);

		persistRepositoriesToXml("coverage_data.xml");
		persistRepositoriesToBinary("repository_data.bin");
	}

	private void persistRepositoriesToXml(String xmlFilename) {
		Input input = db.toInput();

		XmlUtils.serialize(input, xmlFilename);

		assertThat(XmlUtils.deserialize(xmlFilename, Input.class), is(equalTo(input)));
	}

	private void persistRepositoriesToBinary(String binFilename) {
		BinaryUtils.serialize(db, binFilename);

		assertThat(BinaryUtils.deserialize(binFilename, Database.class), is(equalTo(db)));
	}

}