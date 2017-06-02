package coverage;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import coverage.mock.CalendarMock;
import coverage.mock.RuleMock;
import coverage.mock.StaffMock;
import it.addvalue.coverage.Input;

public class Tests {

	private Input buildInput() {
		Input input = new Input();
		input.setCalendarList(CalendarMock.mock());
		input.setRuleList(RuleMock.mock());
		input.setStaffList(StaffMock.mock());
		return input;
	}

	@Test
	public void firstTest() throws IOException {

		Input input = buildInput();

		XmlMapper xmlMapper = new XmlMapper();
		String writeValueAsString = xmlMapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(input);
		System.out.println(writeValueAsString);
		assertNotNull(writeValueAsString);
	}

	@Test
	public void serializedToXmlFileTest() throws IOException {

		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.writeValue(new File("simple_bean.xml"), buildInput());
	}

	@Test
	public void deserializedToXmlFileTest() throws IOException {
		File file = new File("simple_bean.xml");
		XmlMapper xmlMapper = new XmlMapper();
		String xml = inputStreamToString(new FileInputStream(file));
		Input value = xmlMapper.readValue(xml, Input.class);
	}

	public static String inputStreamToString(InputStream is)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}

}