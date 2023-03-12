package com.google.common.collect;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nullable;

import java.util.Map.Entry;

public interface MultimapUtil {

	static <K, V> void put(@Nullable final Multimap<K, V> instance, @Nullable final K key, @Nullable final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	static <K, V> void putAll(@Nullable final Multimap<K, V> instance, @Nullable final K key,
			@Nullable final Iterable<? extends V> values) {
		if (instance != null && values != null) {
			instance.putAll(key, values);
		}
	}

	static <K, V> void putAll(@Nullable final Multimap<K, V> a, @Nullable final Multimap<? extends K, ? extends V> b) {
		if (a != null && b != null) {
			a.putAll(b);
		}
	}

	static <K, V> Collection<V> get(@Nullable final Multimap<K, V> instance, @Nullable final K key) {
		return instance != null ? instance.get(key) : null;
	}

	static int size(@Nullable final Multimap<?, ?> instance) {
		return instance != null ? instance.size() : 0;
	}

	@Nullable
	static <K, V> Collection<Entry<K, V>> entries(@Nullable final Multimap<K, V> instance) {
		return instance != null ? instance.entries() : null;
	}

	static <K> Set<K> keySet(@Nullable final Multimap<K, ?> instance) {
		return instance != null ? instance.keySet() : null;
	}

	static <V> Collection<V> values(@Nullable final Multimap<?, V> instance) {
		return instance != null ? instance.values() : null;
	}

}