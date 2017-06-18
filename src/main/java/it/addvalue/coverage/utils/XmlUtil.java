package it.addvalue.coverage.utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class XmlUtil {

	public static void serialize(Object object, String xmlFile)
	throws IOException {
		xmlMapper().writeValue(new File(xmlFile), object);
	}

	private static XmlMapper xmlMapper() {
		return new XmlMapper();
	}

	public static <T> T deserialize(String xmlFile, Class<T> clazz)
	throws IOException {
		return xmlMapper().readValue(new File(xmlFile), clazz);
	}

	public static String prettyPrint(Object object)
	throws IOException {
		String writeValueAsString = xmlMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
		System.out.println(writeValueAsString);
		return writeValueAsString;
	}

}
