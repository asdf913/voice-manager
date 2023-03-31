package org.apache.poi.util;

public interface IntListUtil {

	static void add(final IntList instance, final int value) {
		if (instance != null) {
			instance.add(value);
		}
	}

}