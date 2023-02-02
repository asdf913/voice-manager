package com.google.common.collect;

import java.util.Collection;

public interface MultimapUtil {

	static <K, V> void put(final Multimap<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	static <K, V> void putAll(final Multimap<K, V> instance, final K key, final Iterable<? extends V> values) {
		if (instance != null) {
			instance.putAll(key, values);
		}
	}

	static <K, V> Collection<V> get(final Multimap<K, V> instance, final K key) {
		return instance != null ? instance.get(key) : null;
	}

	static int size(final Multimap<?, ?> instance) {
		return instance != null ? instance.size() : 0;
	}

}