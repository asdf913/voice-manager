package com.google.common.collect;

import com.google.common.collect.Table.Cell;

public interface CellUtil {

	static <R> R getRowKey(final Cell<R, ?, ?> instance) {
		return instance != null ? instance.getRowKey() : null;
	}

	static <C> C getColumnKey(final Cell<?, C, ?> instance) {
		return instance != null ? instance.getColumnKey() : null;
	}

	static <V> V getValue(final Cell<?, ?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

}