package org.apache.pdfbox.pdmodel.graphics.image;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.ThrowingRunnableUtil;

import io.github.toolfactory.narcissus.Narcissus;

public interface PDImageUtil {

	static int getWidth(final PDImage instance) {
		//
		final Map<String, String> map = Map.of("org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject", "stream",
				"org.apache.pdfbox.pdmodel.graphics.image.PDInlineImage", "parameters");
		//
		final Iterable<Entry<String, String>> entrySet = entrySet(map);
		//
		if (iterator(entrySet) != null) {
			//
			final Class<?> clz = getClass(instance);
			//
			final String name = getName(clz);
			//
			List<Field> fs = null;
			//
			Field f = null;
			//
			for (final Entry<String, String> entry : entrySet) {
				//
				if (!Objects.equals(name, getKey(entry))) {
					//
					continue;
					//
				} // if
					//
				testAndRunThrows(IterableUtils.size(fs = toList(filter(stream(FieldUtils.getAllFieldsList(clz)),
						x -> Objects.equals(getName(x), getValue(entry))))) > 1, () -> {
							//
							throw new IllegalStateException();
							//
						});
				//
				if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
						&& Narcissus.getField(instance, f) == null) {
					//
					return 0;
					//
				} // if
					//
					//
			} // for
				//
		} // if
			//
		return instance != null ? instance.getWidth() : 0;
		//
	}

	static int getHeight(@Nullable final PDImage instance) {
		//
		final Map<String, String> map = Map.of("org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject", "stream",
				"org.apache.pdfbox.pdmodel.graphics.image.PDInlineImage", "parameters");
		//
		final Iterable<Entry<String, String>> entrySet = entrySet(map);
		//
		if (iterator(entrySet) != null) {
			//
			final Class<?> clz = getClass(instance);
			//
			final String name = getName(getClass(instance));
			//
			List<Field> fs = null;
			//
			Field f = null;
			//
			for (final Entry<String, String> entry : entrySet) {
				//
				if (!Objects.equals(name, getKey(entry))) {
					//
					continue;
					//
				} // if
					//
				testAndRunThrows(IterableUtils.size(fs = toList(filter(stream(FieldUtils.getAllFieldsList(clz)),
						x -> Objects.equals(getName(x), getValue(entry))))) > 1, () -> {
							//
							throw new IllegalStateException();
							//
						});
				//
				if ((f = testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) != null
						&& Narcissus.getField(instance, f) == null) {
					//
					return 0;
					//
				} // if
					//
					//
			} // for
				//
		} // if
			//
		return instance != null ? instance.getHeight() : 0;
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: instance;
		//
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			final Function<T, R> functionFalse) {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T, R> R apply(final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	private static <E extends Throwable> void testAndRunThrows(final boolean b,
			final ThrowingRunnable<E> throwingRunnable) throws E {
		if (b) {
			ThrowingRunnableUtil.runThrows(throwingRunnable);
		}
	}

	private static <K> K getKey(final Entry<K, ?> instance) {
		return instance != null ? instance.getKey() : null;
	}

	private static <V> V getValue(final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	private static String getName(final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <K, V> Set<Entry<K, V>> entrySet(final Map<K, V> instance) {
		return instance != null ? instance.entrySet() : null;
	}

	private static <T> Iterator<T> iterator(final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

}