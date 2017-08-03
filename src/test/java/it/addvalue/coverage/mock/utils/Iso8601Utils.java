package it.addvalue.coverage.mock.utils;

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.TimeZone;

public class Iso8601Utils {

	public static DateFormat DATE_FORMAT = new DateFormat() {

		public final TimeZone italianTimeZone = TimeZone.getTimeZone("Europe/Rome");

		public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
			String value = ISO8601Utils.format(date, false, italianTimeZone);
			toAppendTo.append(value);
			return toAppendTo;
		}

		public Date parse(String source)
		throws ParseException {
			return ISO8601Utils.parse(source, new ParsePosition(0));
		}

		public Date parse(String source, ParsePosition pos) {
			try {
				return ISO8601Utils.parse(source, pos);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}

		public Object clone() {
			return this;
		}

		public String toString() {
			return this.getClass().getName();
		}

	};

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
