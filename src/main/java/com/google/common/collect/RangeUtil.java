package com.google.common.collect;

public interface RangeUtil {

	static <C extends Comparable<C>> C upperEndpoint(final Range<C> instance) {
		return instance != null ? instance.upperEndpoint() : null;
	}

}