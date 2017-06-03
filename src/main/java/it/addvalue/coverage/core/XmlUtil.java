package it.addvalue.coverage.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlUtil {

	public static void serializedToXmlFile(Object object, String namefile)
			throws IOException {

		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.writeValue(new File(namefile), object);
	}

	public static Object deserializedToXmlFile(Class clazz, String namefile)
			throws IOException {
		File file = new File(namefile);
		XmlMapper xmlMapper = new XmlMapper();
		String xml = inputStreamToString(new FileInputStream(file));
		return xmlMapper.readValue(xml, clazz);
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

	public static String prettyPrint(Object object) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		String writeValueAsString = xmlMapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(object);
		System.out.println(writeValueAsString);
		return writeValueAsString;
	}

}
