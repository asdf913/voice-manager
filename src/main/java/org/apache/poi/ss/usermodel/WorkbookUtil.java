package org.apache.poi.ss.usermodel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.meeuw.functional.ThrowingRunnable;
import org.meeuw.functional.ThrowingRunnableUtil;

import io.github.toolfactory.narcissus.Narcissus;

public interface WorkbookUtil {

	static Sheet createSheet(final Workbook instance) {
		return instance != null ? instance.createSheet() : null;
	}

	static Sheet createSheet(final Workbook instance, final String sheetname) {
		return instance != null ? instance.createSheet(sheetname) : null;
	}

	static CellStyle createCellStyle(final Workbook instance) {
		return instance != null ? instance.createCellStyle() : null;
	}

	static Sheet getSheetAt(final Workbook instance, final int index) {
		return instance != null ? instance.getSheetAt(index) : null;
	}

	static Sheet getSheet(final Workbook instance, final String name) {
		//
		if (instance == null
				|| (Objects.equals("org.apache.poi.xssf.usermodel.XSSFWorkbook", getName(getClass(instance)))
						&& name == null)) {
			//
			return null;
			//
		} // if
			//
		return instance.getSheet(name);
		//
	}

	private static String getName(final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	static CreationHelper getCreationHelper(final Workbook instance) {
		return instance != null ? instance.getCreationHelper() : null;
	}

	static void write(final Workbook instance, final OutputStream stream) throws IOException {
		//
		if (instance != null && (stream != null || Proxy.isProxyClass(getClass(instance)))) {
			//
			instance.write(stream);
			//
		} // if
			//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	static int getNumberOfSheets(final Workbook instance) {
		//
		final Map<String, String> map = new LinkedHashMap<>(Map.of("org.apache.poi.hssf.usermodel.HSSFWorkbook",
				"_sheets", "org.apache.poi.xssf.usermodel.XSSFWorkbook", "sheets"));
		//
		putAll(map,
				collect(Stream.of("org.apache.poi.xssf.streaming.SXSSFWorkbook",
						"org.apache.poi.xssf.streaming.DeferredSXSSFSheet",
						"org.apache.poi.poifs.crypt.temp.SXSSFWorkbookWithCustomZipEntrySource"),
						Collectors.toMap(Function.identity(), x -> "_wb")));
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
			} // for
				//
		} // if
			//
		return instance != null ? instance.getNumberOfSheets() : 0;
		//
	}

	private static <E extends Throwable> void testAndRunThrows(final boolean b,
			final ThrowingRunnable<E> throwingRunnable) throws E {
		if (b) {
			ThrowingRunnableUtil.runThrows(throwingRunnable);
		}
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
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

	private static <K> K getKey(final Entry<K, ?> instance) {
		return instance != null ? instance.getKey() : null;
	}

	private static <V> V getValue(final Entry<?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	private static <T> Iterator<T> iterator(final Iterable<T> instance) {
		return instance != null ? instance.iterator() : null;
	}

	private static <K, V> Set<Entry<K, V>> entrySet(final Map<K, V> instance) {
		return instance != null ? instance.entrySet() : null;
	}

	private static <K, V> void putAll(final Map<K, V> a, final Map<? extends K, ? extends V> b) {
		if (a != null && (b != null || Proxy.isProxyClass(getClass(a)))) {
			a.putAll(b);
		}
	}

	private static <T, R, A> R collect(final Stream<T> instance, final Collector<? super T, A, R> collector) {
		//
		return instance != null && (collector != null || Proxy.isProxyClass(getClass(instance)))
				? instance.collect(collector)
				: null;
		//
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

}