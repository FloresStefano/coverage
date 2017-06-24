package it.addvalue.coverage.mock.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BinaryUtils {

	private BinaryUtils() {
	}

	public static void serialize(Serializable object, String file) {
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(file));
			os.writeObject(object);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static <T extends Serializable> T deserialize(String file, Class<T> clazz) {
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(new FileInputStream(file));
			return clazz.cast(is.readObject());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
