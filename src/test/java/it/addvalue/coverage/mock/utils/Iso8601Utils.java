package it.addvalue.coverage.mock.utils;

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;

public class Iso8601Utils {

	private Iso8601Utils() {
	}

	public static Date parse(String iso8601) {
		try {
			return ISO8601Utils.parse(iso8601, new ParsePosition(0));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
