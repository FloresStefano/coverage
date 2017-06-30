package it.addvalue.coverage.utils;

public class CsvUtils {

	private CsvUtils() {
	}

	public static int[] toIntArray(String csv) {
		String[] parts = csv.split(",");
		int[] array = new int[parts.length];
		for (int i = 0; i < parts.length; i++) {
			array[i] = Integer.parseInt(parts[i]);
		}
		return array;
	}

	public static int csvSum(String csv) {
		int sum = 0;
		for (String part : csv.split(",")) {
			sum += Integer.parseInt(part);
		}
		return sum;
	}

}
