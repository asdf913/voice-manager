package org.d2ab.collection.ints;

public interface IntIterableUtil {

	static boolean containsInt(final IntIterable instance, final int o) {
		return instance != null && instance.containsInt(o);
	}

}