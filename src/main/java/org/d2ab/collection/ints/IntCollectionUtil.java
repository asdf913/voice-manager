package org.d2ab.collection.ints;

public interface IntCollectionUtil {

	static void addInt(final IntCollection a, final int i) {
		if (a != null) {
			a.addInt(i);
		}
	}

	static int[] toIntArray(final IntCollection instance) {
		return instance != null ? instance.toIntArray() : null;
	}

}