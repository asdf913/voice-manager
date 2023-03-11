package com.google.common.collect;

import javax.annotation.Nullable;

public interface TableUtil {

	static <R, C, V> void put(final Table<R, C, V> instance, final R rowKey, final C columnKey,
			@Nullable final V value) {
		if (instance != null) {
			instance.put(rowKey, columnKey, value);
		}
	}

	static boolean contains(final Table<?, ?, ?> instance, final Object rowKey, final Object columnKey) {
		return instance != null && instance.contains(rowKey, columnKey);
	}

	static <V> V get(@Nullable final Table<?, ?, V> instance, final Object rowKey, final Object columnKey) {
		return instance != null ? instance.get(rowKey, columnKey) : null;
	}

}