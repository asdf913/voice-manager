package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
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
import com.google.common.collect.CellUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.collect.Table;
import com.google.common.collect.TableUtil;

public class ShikokuJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

	private String url = null;

	@Nullable
	private UnicodeBlock unicodeBlock = null;

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
		return collect(
				filter(stream(TableUtil.cellSet(createTable(url))),
						c -> Objects.equals(CellUtil.getColumnKey(c), unicodeBlock)),
				Collectors.toMap(CellUtil::getRowKey, CellUtil::getValue));
		//
	}

	@Nullable
	private static Table<String, UnicodeBlock, String> createTable(final String url) throws IOException {
		//
		return createTable(ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, URL::new, null), x -> Jsoup.parse(x, 0), null), "li dl dd a"));
		//
	}

	@Nullable
	private static Table<String, UnicodeBlock, String> createTable(@Nullable final Iterable<Element> es)
			throws IOException {
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

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static Map<UnicodeBlock, String> createMap(final String url) throws IOException {
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

	@Nullable
	private static String getHiragana(final Element element) {
		//
		return collect(
				stream(MultimapUtil.get(createUnicodeBlockCharacterMultimap(ElementUtil.text(element)),
						UnicodeBlock.HIRAGANA)),
				Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append,
						StringBuilder::toString));
		//
	}

	@Nullable
	private static String getRomaji(final Element element) {
		//
		return collect(
				filter(stream(MultimapUtil.get(createUnicodeBlockCharacterMultimap(ElementUtil.attr(element, "alt")),
						UnicodeBlock.BASIC_LATIN)), c -> !Character.isWhitespace(c)),
				Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append,
						StringBuilder::toString));
		//
	}

	@Nullable
	private static <E> Stream<E> stream(@Nullable final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
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
	private static <T, R, A> R collect(@Nullable final Stream<T> instance,
			@Nullable final Collector<? super T, A, R> collector) {
		//
		return instance != null && (collector != null || Proxy.isProxyClass(getClass(instance)))
				? instance.collect(collector)
				: null;
		//
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	private static Multimap<UnicodeBlock, Character> createUnicodeBlockCharacterMultimap(
			@Nullable final CharSequence cs) {
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

	private static <K, V> void put(@Nullable final Map<K, V> instance, final K key, @Nullable final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}