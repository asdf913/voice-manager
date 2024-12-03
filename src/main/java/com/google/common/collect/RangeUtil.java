package com.google.common.collect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

public abstract class RangeUtil {

	private static Logger LOG = LoggerFactory.getLogger(RangeUtil.class);

	private RangeUtil() {
	}

	public static boolean hasLowerBound(@Nullable final Range<?> instance) {
		return instance != null && instance.hasLowerBound();
	}

	public static boolean hasUpperBound(final Range<?> instance) {
		return instance != null && instance.hasUpperBound();
	}

	public static <C extends Comparable<C>> C upperEndpoint(@Nullable final Range<C> instance) {
		return instance != null ? instance.upperEndpoint() : null;
	}

	public static <C extends Comparable<C>> C lowerEndpoint(@Nullable final Range<C> instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			final Field lowerBound = getDeclaredField(Range.class, "lowerBound");
			//
			setAccessible(lowerBound, true);
			//
			if (get(lowerBound, instance) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException | IllegalAccessException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.lowerEndpoint();
		//
	}

	private static Object get(@Nullable final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static void setAccessible(@Nullable final AccessibleObject instance, final boolean flag) {
		if (instance != null) {
			instance.setAccessible(flag);
		}
	}

	private static Field getDeclaredField(@Nullable final Class<?> instance, final String name)
			throws NoSuchFieldException {
		return instance != null ? instance.getDeclaredField(name) : null;
	}

}