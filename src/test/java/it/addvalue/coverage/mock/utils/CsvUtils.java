package it.addvalue.coverage.mock.utils;

public class CsvUtils {

	private CsvUtils() {
	}

	public static int csvsum(String csv) {
		String[] a = csv.split(",");
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += Integer.parseInt(a[i]);
		}
		return sum;
	}

	public static void csvloop(String csv1, String csv2, Action action) {
		String[] a = csv1.split(",");
		String[] b = csv2.split(",");
		for (int i = 0; i < a.length; i++) {
			action.perform(Integer.parseInt(a[i]), Integer.parseInt(b[i]));
		}
	}

	public static String csvadd(String csv1, String csv2) {
		String[] a = csv1.split(",");
		String[] b = csv2.split(",");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			int sum = Integer.parseInt(a[i]) + Integer.parseInt(b[i]);
			sb.append(sum).append(",");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	public interface Action {
		void perform(int csv1Value, int csv2Value);
	}

}
