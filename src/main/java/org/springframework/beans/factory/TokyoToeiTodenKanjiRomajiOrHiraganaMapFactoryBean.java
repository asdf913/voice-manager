package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import com.google.common.collect.TableUtil;

public class TokyoToeiTodenKanjiRomajiOrHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

	private String url = null;

	@Nullable
	private UnicodeBlock unicodeBlock = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setUnicodeBlock(@Nullable final Object instance) throws IllegalAccessException {
		//
		Util.setUnicodeBlock(instance, x -> this.unicodeBlock = x);
		//
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		final IValue0<Map<String, String>> iValue0 = getIvalue0();
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		return Util.collect(
				Util.filter(Util.stream(TableUtil.cellSet(createTable(url))),
						c -> Objects.equals(CellUtil.getColumnKey(c), unicodeBlock)),
				Collectors.toMap(CellUtil::getRowKey, CellUtil::getValue));
		//
	}

	@Nullable
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
			for (final Entry<UnicodeBlock, String> entry : Util.entrySet(map)) {
				//
				if (entry == null) {
					//
					continue;
					//
				} // if
					//
				TableUtil.put(table = ObjectUtils.getIfNull(table, HashBasedTable::create),
						getKanji(ElementUtil.text(e)), Util.getKey(entry), Util.getValue(entry));
				//
			} // for
				//
		} // for
			//
		return table;
		//
	}

	@Nullable
	private static String getKanji(final String string) {
		//
		return Util.collect(
				Util.stream(MultimapUtil.get(createUnicodeBlockCharacterMultimap(string),
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
				Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), UnicodeBlock.HIRAGANA,
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
				Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), UnicodeBlock.BASIC_LATIN,
						getRomaji(!es.isEmpty() ? IterableUtils.get(es, 0) : null));
				//
			} // if
				//
		} // if
			//
		return map;
		//
	}

	@Nullable
	private static String getHiragana(@Nullable final Element element) {
		//
		return Util.collect(
				Util.stream(MultimapUtil.get(createUnicodeBlockCharacterMultimap(ElementUtil.text(element)),
						UnicodeBlock.HIRAGANA)),
				Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append,
						StringBuilder::toString));
		//
	}

	@Nullable
	private static String getRomaji(@Nullable final Element element) {
		//
		return Util.collect(
				Util.stream(MultimapUtil.get(createUnicodeBlockCharacterMultimap(ElementUtil.text(element)),
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