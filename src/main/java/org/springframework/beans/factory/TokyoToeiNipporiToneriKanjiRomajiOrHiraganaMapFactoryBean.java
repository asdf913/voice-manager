package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Proxy;
import java.net.URL;
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

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.CellUtil;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.TableUtil;

public class TokyoToeiNipporiToneriKanjiRomajiOrHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

	private String url = null;

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
			final IValue0<UnicodeBlock> iValue0 = Util.getUnicodeBlock(string);
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

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		final Set<Cell<String, UnicodeBlock, String>> cells = TableUtil.cellSet(createTable(url));
		//
		return cells != null
				? collect(stream(cells).filter(c -> Objects.equals(CellUtil.getColumnKey(c), unicodeBlock)),
						Collectors.toMap(CellUtil::getRowKey, CellUtil::getValue))
				: null;
		//
	}

	private static Table<String, UnicodeBlock, String> createTable(final String url) throws IOException {
		//
		final List<Element> es = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, URL::new, null), x -> Jsoup.parse(x, 0), null),
				"ul.routeList li a");
		//
		Element e = null;
		//
		Table<String, UnicodeBlock, String> table = null;
		//
		Map<UnicodeBlock, String> map = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((map = createMap(NodeUtil.absUrl(e = es.get(i), "href"))) == null) {
				//
				continue;
				//
			} // if
			for (final Entry<UnicodeBlock, String> entry : map.entrySet()) {
				//
				if (entry == null) {
					//
					continue;
					//
				} // if
					//
				TableUtil.put(table = ObjectUtils.getIfNull(table, HashBasedTable::create),
						getKanji(ElementUtil.text(e)), entry.getKey(), entry.getValue());
				//
			} // for
				//
		} // for
			//
		return table;
		//
	}

	private static String getKanji(final String string) {
		//
		return collect(
				stream(MultimapUtil.get(createUnicodeBlockCharacterMultimap(string),
						UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
				Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append,
						StringBuilder::toString));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
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
		List<Element> es = ElementUtil.select(document, ".name-kana");
		//
		if (es != null) {
			//
			final int size = es.size();
			//
			if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} else if (es.size() == 1) {
				//
				put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), UnicodeBlock.HIRAGANA,
						getHiragana(!es.isEmpty() ? IterableUtils.get(es, 0) : null));
				//
			} // if
				//
		} // if
			//
			// romaji
			//
		if ((es = ElementUtil.select(document, ".name-eng")) != null) {
			//
			final int size = es.size();
			//
			if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} else if (size == 1) {
				//
				put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), UnicodeBlock.BASIC_LATIN,
						getRomaji(!es.isEmpty() ? IterableUtils.get(es, 0) : null));
				//
			} // if
				//
		} // if
			//
		return map;
		//
	}

	private static <K, V> void put(@Nullable final Map<K, V> instance, final K key, @Nullable final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
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

	private static <E> Stream<E> stream(@Nullable final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T, R, A> R collect(@Nullable final Stream<T> instance,
			@Nullable final Collector<? super T, A, R> collector) {
		//
		return instance != null && (collector != null || Proxy.isProxyClass(getClass(instance)))
				? instance.collect(collector)
				: null;
		//
	}

	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static String getRomaji(final Element element) {
		//
		return collect(
				stream(MultimapUtil.get(createUnicodeBlockCharacterMultimap(ElementUtil.text(element)),
						UnicodeBlock.BASIC_LATIN)),
				Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append,
						StringBuilder::toString));
		//
	}

	@Nullable
	private static Multimap<UnicodeBlock, Character> createUnicodeBlockCharacterMultimap(
			@Nullable final String string) {
		//
		char c;
		//
		Multimap<UnicodeBlock, Character> chars = null;
		//
		for (int i = 0; string != null && i < string.length(); i++) {
			//
			MultimapUtil.put(chars = ObjectUtils.getIfNull(chars, ArrayListMultimap::create),
					UnicodeBlock.of(c = string.charAt(i)), c);
			//
		} // for
			//
		return chars;
		//
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}