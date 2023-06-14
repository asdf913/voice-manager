package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;

abstract class Util {

	private Util() {
	}

	@Nullable
	static IValue0<UnicodeBlock> getUnicodeBlock(final String string) throws IllegalAccessException {
		//
		if (StringUtils.isBlank(string)) {
			//
			return Unit.with(null);
			//
		} else {
			//
			final List<Field> fs = Arrays.stream(UnicodeBlock.class.getDeclaredFields())
					.filter(f -> StringUtils.startsWithIgnoreCase(getName(f), string)).toList();
			//
			final int size = IterableUtils.size(fs);
			//
			if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} else if (size == 0) {
				//
				return null;
				//
			} // if
				//
			final Field f = IterableUtils.get(fs, 0);
			//
			if (f == null || !Modifier.isStatic(f.getModifiers())) {
				//
				return null;
				//
			} else if (!isAssignableFrom(f.getType(), UnicodeBlock.class)) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			return Unit.with(cast(UnicodeBlock.class, f.get(0)));
			//
		} // if
			//
	}

	@Nullable
	private static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static boolean isAssignableFrom(@Nullable final Class<?> a, @Nullable final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
	}

	@Nullable
	private static <T> T cast(@Nullable final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	static void setUnicodeBlock(@Nullable final Object instance, final Consumer<UnicodeBlock> consumer)
			throws IllegalAccessException {
		//
		if (instance == null) {
			//
			accept(consumer, null);
			//
		} else if (instance instanceof UnicodeBlock ub) {
			//
			accept(consumer, ub);
			//
		} else if (instance instanceof String string) {
			//
			final IValue0<UnicodeBlock> iValue0 = getUnicodeBlock(string);
			//
			if (iValue0 != null) {
				//
				setUnicodeBlock(IValue0Util.getValue0(iValue0), consumer);
				//
			} // if
				//
		} else if (instance instanceof char[] cs) {
			//
			setUnicodeBlock(new String(cs), consumer);
			//
		} else if (instance instanceof byte[] bs) {
			//
			setUnicodeBlock(new String(bs), consumer);
			//
		} else {
			//
			throw new IllegalArgumentException(toString(instance));
			//
		} // if
			//
	}

	private static <T> void accept(@Nullable final Consumer<T> instance, @Nullable final T value) {
		if (instance != null) {
			instance.accept(value);
		}
	}

	@Nullable
	static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Nullable
	static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	static <K, V> void put(@Nullable final Map<K, V> instance, @Nullable final K key, @Nullable final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

}