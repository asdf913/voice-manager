package org.springframework.beans.factory;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;

/**
 * @see java.awt.image.BufferedImage
 */
public class BufferedImageTypeFactoryBean implements FactoryBean<Integer> {

	private Object value = null;

	public void setValue(final Object value) {
		this.value = value;
	}

	/**
	 * The return value should be one of the below.
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_CUSTOM
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_INT_ARGB
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_INT_ARGB_PRE
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_INT_BGR
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_3BYTE_BGR
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_4BYTE_ABGR
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_4BYTE_ABGR_PRE
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_USHORT_565_RGB
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_USHORT_555_RGB
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_BYTE_GRAY
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_USHORT_GRAY
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_BYTE_BINARY
	 * 
	 * @see java.awt.image.BufferedImage#TYPE_BYTE_INDEXED
	 */
	@Override
	public Integer getObject() throws Exception {
		//
		IValue0<Integer> result = null;
		//
		if (value == null) {
			//
			result = Unit.with(null);
			//
		} else if (value instanceof Number number) {
			//
			result = Unit.with(Integer.valueOf(intValue(number, 0)));
			//
		} else if (value instanceof CharSequence cs) {
			//
			result = getImageType(cs);
			//
		} else if (value instanceof char[]) {
			//
			result = getImageType(new String((char[]) value));
			//
		} else if (value instanceof byte[]) {
			//
			result = getImageType(new String((byte[]) value));
			//
		} // if
			//
		if (result != null) {
			//
			return IValue0Util.getValue0(result);
			//
		} // if
			//
		throw new IllegalStateException(toString(getClass(value)));
		//
	}

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static IValue0<Integer> getImageType(final CharSequence cs) {
		//
		final String string = toString(cs);
		//
		if (StringUtils.isEmpty(string)) {
			//
			return Unit.with(null);
			//
		} // if
			//
		NumberFormatException nfe = null;
		//
		try {
			//
			return Unit.with(Integer.valueOf(string));
			//
		} catch (final NumberFormatException e) {
			//
			nfe = e;
			//
		} // try
			//
		final List<Field> fs = toList(filter(
				testAndApply(Objects::nonNull, BufferedImage.class.getDeclaredFields(), Arrays::stream, null), f -> {
					//
					final Class<?> type = getType(f);
					//
					return and(isStatic(f),
							Boolean.logicalOr(isAssignableFrom(Number.class, type),
									(isPrimitive(type) && ArrayUtils.contains(new Class<?>[] { Byte.TYPE, Short.TYPE,
											Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE }, type))),
							Objects.equals(getName(f), string));
					//
				}));
		//
		final int size = IterableUtils.size(fs);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		try {
			//
			final Object obj = testAndApply(BufferedImageTypeFactoryBean::isStatic,
					testAndApply(x -> size == 1, fs, x -> IterableUtils.get(x, 0), null), x -> get(x, null), null);
			//
			if (obj instanceof Number number) {
				//
				return Unit.with(Integer.valueOf(intValue(number, 0)));
				//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} // try
			//
		throw nfe;
		//
	}

	@Nullable
	private static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static <T> Stream<T> filter(@Nullable final Stream<T> instance,
			@Nullable final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	@Nullable
	private static <T> List<T> toList(@Nullable final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static boolean isStatic(@Nullable final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	@Nullable
	private static Object get(@Nullable final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	@Nullable
	private static Class<?> getType(@Nullable final Field instance) {
		return instance != null ? instance.getType() : null;
	}

	private static boolean isAssignableFrom(@Nullable final Class<?> a, @Nullable final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
	}

	private static boolean isPrimitive(@Nullable final Class<?> instance) {
		return instance != null && instance.isPrimitive();
	}

	@Nullable
	private static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static boolean and(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		boolean result = a && b;
		//
		if (!result) {
			//
			return false;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (!(result &= bs[i])) {
				//
				return false;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	@Override
	public Class<?> getObjectType() {
		return Integer.class;
	}

}
