package com.google.common.collect;

public interface TableUtil {

	static boolean contains(final Table<?, ?, ?> instance, final Object rowKey, final Object columnKey) {
		return instance != null && instance.contains(rowKey, columnKey);
	}

	static <V> V get(final Table<?, ?, V> instance, final Object rowKey, final Object columnKey) {
		return instance != null ? instance.get(rowKey, columnKey) : null;
	}

}