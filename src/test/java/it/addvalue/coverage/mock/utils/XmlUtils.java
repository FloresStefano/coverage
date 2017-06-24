package it.addvalue.coverage.mock.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class XmlUtils {

	private XmlUtils() {
	}

	public static void serialize(Object object, String file) {
		try {
			xmlMapper().writeValue(new File(file), object);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static XmlMapper xmlMapper() {
		return new XmlMapper();
	}

	public static <T> T deserialize(String file, Class<T> clazz) {
		try {
			return xmlMapper().readValue(new File(file), clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String prettyPrint(Object object) {
		try {
			String xml = xmlMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
			System.out.println(xml);
			return xml;
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
