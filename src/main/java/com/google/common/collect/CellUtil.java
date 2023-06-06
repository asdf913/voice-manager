package com.google.common.collect;

import javax.annotation.Nullable;

import com.google.common.collect.Table.Cell;

public interface CellUtil {

	static <R> R getRowKey(@Nullable final Cell<R, ?, ?> instance) {
		return instance != null ? instance.getRowKey() : null;
	}

	static <C> C getColumnKey(@Nullable final Cell<?, C, ?> instance) {
		return instance != null ? instance.getColumnKey() : null;
	}

	static <V> V getValue(@Nullable final Cell<?, ?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

}