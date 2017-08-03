package it.addvalue.coverage.mock.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtils {

	private JsonUtils() {
	}

	public static void serialize(Object object, String file) {
		try {
			jsonMapper().writeValue(new File(file), object);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void prettySerialize(Object object, String file) {
		try {
			jsonMapper().writerWithDefaultPrettyPrinter().writeValue(new File(file), object);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T deserialize(String file, Class<T> clazz) {
		try {
			return jsonMapper().readValue(new File(file), clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String print(Object object) {
		try {
			String json = jsonMapper().writer().writeValueAsString(object);
			System.out.println(json);
			return json;
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String prettyPrint(Object object) {
		try {
			String json = jsonMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
			System.out.println(json);
			return json;
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private static ObjectMapper jsonMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(Iso8601Utils.DATE_FORMAT);
		return objectMapper;
	}

}
