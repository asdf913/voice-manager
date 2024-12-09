package org.d2ab.function;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.util.LocaleID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import io.github.toolfactory.narcissus.Narcissus;

public class LanguageCodeToTextObjIntFunction implements ObjIntFunction<String, String> {

	private static final Logger LOG = LoggerFactory.getLogger(LanguageCodeToTextObjIntFunction.class);

	@Override
	public String apply(final String instance, final int base) {
		//
		return StringUtils.defaultIfBlank(apply(LocaleID.values(), valueOf(instance, base)), instance);
		//
	}

	private static String apply(final LocaleID[] enums, final Integer value) {
		//
		final List<LocaleID> localeIds = toList(filter(testAndApply(Objects::nonNull, enums, Arrays::stream, null),
				a -> a != null && Objects.equals(Integer.valueOf(a.getLcid()), value)));
		//
		if (localeIds != null && !localeIds.isEmpty()) {
			//
			if (IterableUtils.size(localeIds) == 1) {
				//
				final LocaleID localeId = IterableUtils.get(localeIds, 0);
				//
				return localeId != null ? localeId.getDescription() : null;
				//
			} else {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			// java.util.stream.AbstractPipeline.sourceStage
			//
			if (isAssignableFrom(Class.forName("java.util.stream.AbstractPipeline"), getClass(instance))) {
				//
				final Stream<Field> s = filter(stream(FieldUtils.getAllFieldsList(getClass(instance))),
						f -> Objects.equals(getName(f), "sourceStage"));
				//
				final List<Field> fs = s != null ? s.toList() : null;
				//
				final int size = IterableUtils.size(fs);
				//
				if (size > 1) {
					//
					throw new IllegalStateException();
					//
				} else if (testAndApply(x -> !isStatic(x),
						testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null),
						x -> Narcissus.getField(instance, x), null) == null) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
		} catch (final ClassNotFoundException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return instance.toList();
		//
	}

	private static boolean isStatic(final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static Integer valueOf(final String instance, final int base) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance, base) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

}