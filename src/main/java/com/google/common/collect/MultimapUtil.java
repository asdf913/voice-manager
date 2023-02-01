package com.google.common.collect;

import java.util.Collection;

public interface MultimapUtil {

	static <K, V> Collection<V> get(final Multimap<K, V> instance, final K key) {
		return instance != null ? instance.get(key) : null;
	}

	static <K, V> void putAll(final Multimap<K, V> instance, final K key, final Iterable<? extends V> values) {
		if (instance != null) {
			instance.putAll(key, values);
		}
	}

}