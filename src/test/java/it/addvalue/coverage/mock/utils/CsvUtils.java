package it.addvalue.coverage.mock.utils;

public class CsvUtils {

	public static int csvsum(String csv) {
		String[] a = csv.split(",");
		int out = 0;
		for (int i = 0; i < 5; i++) {
			out += Integer.parseInt(a[i]);

		}
		return out;
	}

	public static String csvadd(String csv1, String csv2) {
		String[] a = csv1.split(",");
		String[] b = csv2.split(",");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			int sum = Integer.parseInt(a[i]) + Integer.parseInt(b[i]);
			sb.append(sum).append(",");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

}
