package org.apache.poi.util;

public interface IntListUtil {

	static void add(final IntList instance, final int value) {
		if (instance != null) {
			instance.add(value);
		}
	}

	static int[] toArray(final IntList instance) {
		return instance != null ? instance.toArray() : null;
	}

}