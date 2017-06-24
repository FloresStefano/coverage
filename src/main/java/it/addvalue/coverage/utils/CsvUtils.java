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

}
