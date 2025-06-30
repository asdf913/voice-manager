package org.apache.commons.text;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import io.github.toolfactory.narcissus.Narcissus;

public interface TextStringBuilderUtil {

	static TextStringBuilder clear(final TextStringBuilder instance) {
		return instance != null ? instance.clear() : instance;
	}

	static TextStringBuilder append(final TextStringBuilder instance, final String str) {
		//
		if (instance == null) {
			//
			return instance;
			//
		} // if
			//
		Iterable<Field> fs = toList(filter(stream(FieldUtils.getAllFieldsList(TextStringBuilder.class)),
				f -> Objects.equals(f.getName(), "buffer")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		if (f != null && Narcissus.getField(instance, f) == null) {
			//
			return instance;
			//
		} else if (IterableUtils.size(fs = toList(filter(stream(FieldUtils.getAllFieldsList(String.class)),
				x -> Objects.equals(getName(x), "value")))) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return testAndGet(x -> Narcissus.getField(str, x) != null,
				testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null),
				() -> instance.append(str), () -> instance);
		//
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <T, R> R testAndGet(final Predicate<T> predicate, final T value, final Supplier<R> supplierTrue,
			final Supplier<R> supplierFalse) {
		return test(predicate, value) ? get(supplierTrue) : get(supplierFalse);
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T> T get(final Supplier<T> instnace) {
		return instnace != null ? instnace.get() : null;
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			final Function<T, R> functionFalse) {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R> R apply(final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: instance;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

}