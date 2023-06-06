package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.TableUtil;

public class ShikokuJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

	private String url = null;

	private UnicodeBlock unicodeBlock = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setUnicodeBlock(final Object instance) throws IllegalAccessException {
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

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		return collect(filter(stream(cellSet(createTable(url))), c -> Objects.equals(getColumnKey(c), unicodeBlock)),
				Collectors.toMap(c -> getRowKey(c), c -> getValue(c)));
		//
	}

	private static Table<String, UnicodeBlock, String> createTable(final String url)
			throws MalformedURLException, IOException {
		//
		return createTable(ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, URL::new, null), x -> Jsoup.parse(x, 0), null), "li dl dd a"));
		//
	}

	private static Table<String, UnicodeBlock, String> createTable(final Iterable<Element> es)
			throws MalformedURLException, IOException {
		//
		Table<String, UnicodeBlock, String> table = null;
		//
		if (es != null) {
			//
			Map<UnicodeBlock, String> map = null;
			//
			for (final Element e : es) {
				//
				if (e == null || (map = createMap(e.absUrl("href"))) == null) {
					//
					continue;
					//
				} // if
					//
				for (final Entry<UnicodeBlock, String> entry : map.entrySet()) {
					//
					if (entry == null) {
						//
						continue;
						//
					} // if
						//
					TableUtil.put(table = ObjectUtils.getIfNull(table, HashBasedTable::create), ElementUtil.text(e),
							entry.getKey(), entry.getValue());
					//
				} // for
					//
			} // for
				//
		} // if
			//
		return table;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static Map<UnicodeBlock, String> createMap(final String url) throws MalformedURLException, IOException {
		//
		Map<UnicodeBlock, String> map = null;
		//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, URL::new, null), x -> Jsoup.parse(x, 0), null);
		//
		// hiragana
		//
		List<Element> es = ElementUtil.select(document, "div.explain h3");
		//
		if (es != null && es.size() > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (CollectionUtils.isNotEmpty(es)) {
			//
			put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), UnicodeBlock.HIRAGANA,
					getHiragana(IterableUtils.get(es, 0)));
			//
		} // if
			//
			// romaji
			//
		if ((es = ElementUtil.select(document, "em img")) != null && es.size() > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (CollectionUtils.isNotEmpty(es)) {
			//
			put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), UnicodeBlock.BASIC_LATIN,
					getRomaji(IterableUtils.get(es, 0)));
			//
		} // if
			//
		return map;
		//
	}

	private static String getHiragana(final Element element) {
		//
		return collect(
				stream(MultimapUtil.get(createUnicodeBlockCharacterMultimap(ElementUtil.text(element)),
						UnicodeBlock.HIRAGANA)),
				Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append,
						StringBuilder::toString));
		//
	}

	private static String getRomaji(final Element element) {
		//
		return collect(
				filter(stream(MultimapUtil.get(createUnicodeBlockCharacterMultimap(ElementUtil.attr(element, "alt")),
						UnicodeBlock.BASIC_LATIN)), c -> !Character.isWhitespace(c)),
				Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append,
						StringBuilder::toString));
		//
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		//
		return instance != null && (predicate != null || Proxy.isProxyClass(getClass(instance)))
				? instance.filter(predicate)
				: null;
		//
	}

	private static <T, R, A> R collect(final Stream<T> instance, @Nullable final Collector<? super T, A, R> collector) {
		//
		return instance != null && (collector != null || Proxy.isProxyClass(getClass(instance)))
				? instance.collect(collector)
				: null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static Multimap<UnicodeBlock, Character> createUnicodeBlockCharacterMultimap(final CharSequence cs) {
		//
		char c;
		//
		Multimap<UnicodeBlock, Character> chars = null;
		//
		for (int i = 0; cs != null && i < cs.length(); i++) {
			//
			MultimapUtil.put(chars = ObjectUtils.getIfNull(chars, ArrayListMultimap::create),
					UnicodeBlock.of(c = cs.charAt(i)), c);
			//
		} // for
			//
		return chars;
		//
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static <R, C, V> Set<Cell<R, C, V>> cellSet(final Table<R, C, V> instance) {
		return instance != null ? instance.cellSet() : null;
	}

	private static <R> R getRowKey(final Cell<R, ?, ?> instance) {
		return instance != null ? instance.getRowKey() : null;
	}

	private static <C> C getColumnKey(final Cell<?, C, ?> instance) {
		return instance != null ? instance.getColumnKey() : null;
	}

	private static <V> V getValue(final Cell<?, ?, V> instance) {
		return instance != null ? instance.getValue() : null;
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}