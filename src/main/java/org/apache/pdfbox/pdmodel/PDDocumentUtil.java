package org.apache.pdfbox.pdmodel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import io.github.toolfactory.narcissus.Narcissus;

public interface PDDocumentUtil {

	static void save(final PDDocument instance, final File file) throws IOException {
		//
		// java.io.File.path
		//
		Iterable<?> iterable = toList(
				map(filter(stream(testAndApply(Objects::nonNull, getClass(file), FieldUtils::getAllFieldsList, null)),
						x -> Objects.equals(getName(x), "path")), x -> Narcissus.getField(file, x)));
		//
		if (IterableUtils.size(iterable) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final boolean a = IterableUtils.size(iterable) == 1 && IterableUtils.get(iterable, 0) != null;
		//
		// org.apache.pdfbox.pdmodel.PDDocument.document
		//
		if (IterableUtils.size(iterable = toList(map(
				filter(stream(testAndApply(Objects::nonNull, getClass(instance), FieldUtils::getAllFieldsList, null)),
						x -> Objects.equals(getName(x), "document")),
				x -> Narcissus.getField(instance, x)))) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		if (a && IterableUtils.size(iterable) == 1 && IterableUtils.get(iterable, 0) != null && isFile(file)
				&& instance != null) {
			//
			instance.save(file);
			//
		} // if
			//
	}

	private static boolean isFile(final File instance) {
		return instance != null && instance.isFile();
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static <T, R> Stream<R> map(final Stream<T> instance, final Function<? super T, ? extends R> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null) ? instance.map(mapper)
				: null;
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			final Function<T, R> functionFalse) {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R> R apply(final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	static int getNumberOfPages(final PDDocument instance) {
		//
		if (instance == null) {
			//
			return 0;
			//
		} // if
			//
		final Iterable<Field> fs = toList(
				filter(stream(testAndApply(Objects::nonNull, getClass(instance), FieldUtils::getAllFieldsList, null)),
						f -> Objects.equals(getName(f), "document")));
		//
		if (IterableUtils.size(fs) > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Field f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null);
		//
		return f == null || Narcissus.getField(instance, f) != null ? instance.getNumberOfPages() : 0;
		//
	}

}