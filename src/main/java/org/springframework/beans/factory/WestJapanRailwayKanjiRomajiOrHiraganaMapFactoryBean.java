package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;

import com.google.common.collect.CellUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.TableUtil;

import io.github.toolfactory.narcissus.Narcissus;

public class WestJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

	private String url = null;

	@Nullable
	private UnicodeBlock unicodeBlock = null;

	private Resource resourceJs = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setUnicodeBlock(@Nullable final Object instance) throws IllegalAccessException {
		//
		if (instance == null) {
			//
			this.unicodeBlock = null;
			//
		} else if (instance instanceof UnicodeBlock ub) {
			//
			this.unicodeBlock = ub;
			//
		} else if (instance instanceof String string) {
			//
			final IValue0<UnicodeBlock> iValue0 = getUnicodeBlock(string);
			//
			if (iValue0 != null) {
				//
				setUnicodeBlock(IValue0Util.getValue0(iValue0));
				//
			} // if
				//
		} else if (instance instanceof char[] cs) {
			//
			setUnicodeBlock(new String(cs));
			//
		} else if (instance instanceof byte[] bs) {
			//
			setUnicodeBlock(new String(bs));
			//
		} else {
			//
			throw new IllegalArgumentException(instance.toString());
			//
		} // if
			//
	}

	public void setResourceJs(final Resource resourceJs) {
		this.resourceJs = resourceJs;
	}

	@Nullable
	private static IValue0<UnicodeBlock> getUnicodeBlock(final String string) throws IllegalAccessException {
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

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		IValue0<Map<String, String>> iValue0 = getIvalue0();
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		try (final InputStream is = testAndApply(ResourceUtil::exists, resourceJs,
				InputStreamSourceUtil::getInputStream, null)) {
			//
			iValue0 = getObject(is, unicodeBlock);
			//
		} // try
			//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		try (final InputStream is = openStream(testAndApply(StringUtils::isNotBlank, this.url, URL::new, null))) {
			//
			return IValue0Util.getValue0(getObject(is, unicodeBlock));
		} // try
			//
	}

	@Nullable
	private static IValue0<Map<String, String>> getObject(@Nullable final InputStream is,
			final UnicodeBlock unicodeBlock) throws IOException, ScriptException, IllegalAccessException {
		//
		Table<String, UnicodeBlock, String> table = null;
		//
		try (final Reader r = testAndApply(Objects::nonNull, is, InputStreamReader::new, null)) {
			//
			final ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");
			//
			if (se != null && r != null) {
				//
				se.eval(r);
				//
			} // if
				//
			table = createTable(cast(Object[].class,
					testAndApply(
							Objects::nonNull, testAndApply(Objects::nonNull, get(se, "d"),
									x -> FieldUtils.readField(x, "sobj", true), null),
							x -> FieldUtils.readField(x, "objectSpill", true), null)));
			//
		} // try
			//
		final Set<Cell<String, UnicodeBlock, String>> cells = TableUtil.cellSet(table);
		//
		return cells != null
				? Unit.with(cells.stream().filter(c -> Objects.equals(CellUtil.getColumnKey(c), unicodeBlock))
						.collect(Collectors.toMap(CellUtil::getRowKey, CellUtil::getValue)))
				: null;
		//
	}

	@Nullable
	private static Object get(@Nullable final ScriptEngine instance, final String key) {
		return instance != null ? instance.get(key) : null;
	}

	@Nullable
	private static InputStream openStream(@Nullable final URL instance) throws IOException {
		return instance != null ? instance.openStream() : null;
	}

	@Nullable
	private static Table<String, UnicodeBlock, String> createTable(@Nullable final Object[] os)
			throws IllegalAccessException {
		//
		Table<String, UnicodeBlock, String> table = null;
		//
		Object o = null;
		//
		Field[] fs = null;
		//
		List<Triple<String, UnicodeBlock, String>> triples = null;
		//
		for (int i = 0; os != null && i < os.length; i++) {
			//
			if ((o = os[i]) == null || o.getClass() == null || (fs = o.getClass().getDeclaredFields()) == null
					|| (triples = getTriples(fs, o)) == null || triples.isEmpty()) {
				//
				continue;
				//
			} // if
				//
			for (final Triple<String, UnicodeBlock, String> triple : triples) {
				//
				if (triple == null) {
					//
					continue;
					//
				} // if
					//
				TableUtil.put(table = ObjectUtils.getIfNull(table, HashBasedTable::create), triple.getLeft(),
						triple.getMiddle(), triple.getRight());
				//
			} // for
				//
		} // for
			//
		return table;
		//
	}

	@Nullable
	private static List<Triple<String, UnicodeBlock, String>> getTriples(@Nullable final Field[] fs,
			final Object instance) throws IllegalArgumentException, IllegalAccessException {
		//
		Field f = null;
		//
		Object temp = null;
		//
		List<UnicodeBlock> unicodeBlocks = null;
		//
		String s = null;
		//
		Map<UnicodeBlock, String> map = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			if ((f = fs[i]) == null) {
				//
				continue;
				//
			} // if
				//
			if (contains(Arrays.asList("java.base", "java.sql", "java.desktop", "java.logging"),
					getName(getModule(f.getDeclaringClass())))) {
				//
				temp = Modifier.isStatic(f.getModifiers()) ? Narcissus.getStaticField(f)
						: testAndApply((a, b) -> a != null, instance, f, Narcissus::getField, null);
				//
			} else {
				//
				f.setAccessible(true);
				//
				temp = f.get(instance);
				//
			} // if
				//
			if (!isInstance(CharSequence.class, temp) || (unicodeBlocks = getUnicodeBlocks(s = toString(temp))) == null
					|| unicodeBlocks.isEmpty()) {
				//
				continue;
				//
			} // if
				//
			if (unicodeBlocks.size() > 1) {
				//
				return null;
				//
			} // if
				//
			put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), unicodeBlocks.get(0), s);
			//
		} // for
			//
		return getTriples(map);
		//
	}

	@Nullable
	private static Module getModule(@Nullable final Class<?> instance) {
		return instance != null ? instance.getModule() : null;
	}

	@Nullable
	private static String getName(@Nullable final Module instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static List<Triple<String, UnicodeBlock, String>> getTriples(
			@Nullable final Map<UnicodeBlock, String> map) {
		//
		List<Triple<String, UnicodeBlock, String>> triples = null;
		//
		if (map != null) {
			//
			if (!map.containsKey(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)) {
				//
				return null;
				//
			} // if
				//
			final String kanji = map.get(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
			//
			UnicodeBlock key = null;
			//
			for (final Entry<UnicodeBlock, String> en : map.entrySet()) {
				//
				if (en == null || Objects.equals(key = en.getKey(), UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)) {
					//
					continue;
					//
				} // if
					//
				if (!contains(Arrays.asList(UnicodeBlock.HIRAGANA, UnicodeBlock.BASIC_LATIN), key)) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				add(triples = ObjectUtils.getIfNull(triples, ArrayList::new),
						new ImmutableTriple<>(kanji, key, en.getValue()));
				//
			} // for
				//
		} // if
			//
		return triples;
		//
	}

	@Nullable
	private static List<UnicodeBlock> getUnicodeBlocks(@Nullable final String string) {
		//
		final char[] cs = string != null ? string.toCharArray() : null;
		//
		if (cs != null) {
			//
			List<UnicodeBlock> unicodeBlocks = null;
			//
			for (final char c : cs) {
				//
				testAndAccept((a, b) -> b != null && !contains(a, b),
						unicodeBlocks = ObjectUtils.getIfNull(unicodeBlocks, ArrayList::new), UnicodeBlock.of(c),
						WestJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean::add);
				//
			} // for
				//
			return unicodeBlocks;
			//
		} // if
			//
		return null;
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (test(instance, t, u)) {
			accept(consumer, t, u);
		} // if
	}

	private static <T, U> boolean test(@Nullable final BiPredicate<T, U> instance, final T t, final U u) {
		return instance != null && instance.test(t, u);
	}

	private static <T, U> void accept(@Nullable final BiConsumer<T, U> instance, final T t, final U u) {
		if (instance != null) {
			instance.accept(t, u);
		}
	}

	@Nullable
	private static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static boolean isInstance(@Nullable final Class<?> clz, final Object instance) {
		return clz != null && clz.isInstance(instance);
	}

	private static <K, V> void put(@Nullable final Map<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static boolean contains(@Nullable final Collection<?> items, @Nullable final Object item) {
		return items != null && items.contains(item);
	}

	private static <E> void add(@Nullable final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
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

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse) throws E {
		return predicate != null && predicate.test(t, u) ? apply(functionTrue, t, u) : apply(functionFalse, t, u);
	}

	private static <R, T, U> R apply(@Nullable final BiFunction<T, U, R> instance, final T t, final U u) {
		return instance != null ? instance.apply(t, u) : null;
	}

}