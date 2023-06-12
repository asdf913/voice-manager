package com.google.common.collect;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Table.Cell;

public interface TableUtil {

	static <R, C, V> void put(@Nullable final Table<R, C, V> instance, @Nullable final R rowKey, final C columnKey,
			@Nullable final V value) {
		if (instance != null) {
			instance.put(rowKey, columnKey, value);
		}
	}

	static boolean contains(@Nullable final Table<?, ?, ?> instance, final Object rowKey, final Object columnKey) {
		return instance != null && instance.contains(rowKey, columnKey);
	}

	static <V> V get(@Nullable final Table<?, ?, V> instance, final Object rowKey, final Object columnKey) {
		return instance != null ? instance.get(rowKey, columnKey) : null;
	}

	static <R, C, V> Set<Cell<R, C, V>> cellSet(@Nullable final Table<R, C, V> instance) {
		return instance != null ? instance.cellSet() : null;
	}

}