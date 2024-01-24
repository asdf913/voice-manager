package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.apache.commons.collections.CollectionUtils;
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

public class ShikokuJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

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
		IValue0<Map<String, String>> iValue0 = getIvalue0();
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
	private static Table<String, UnicodeBlock, String> createTable(final String url) throws Exception {
		//
		return createTable(ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0), null),
				"li dl dd a"));
		//
	}

	@Nullable
	private static Table<String, UnicodeBlock, String> createTable(@Nullable final Iterable<Element> es)
			throws Exception {
		//
		Table<String, UnicodeBlock, String> table = null;
		//
		if (es != null) {
			//
			Map<UnicodeBlock, String> map = null;
			//
			for (final Element e : es) {
				//
				if ((map = createMap(NodeUtil.absUrl(e, "href"))) == null) {
					//
					continue;
					//
				} // if
					//
				for (final Entry<UnicodeBlock, String> entry : Util.entrySet(map)) {
					//
					if (entry == null) {
						//
						continue;
						//
					} // if
						//
					TableUtil.put(table = ObjectUtils.getIfNull(table, HashBasedTable::create), ElementUtil.text(e),
							Util.getKey(entry), Util.getValue(entry));
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
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static Map<UnicodeBlock, String> createMap(final String url) throws Exception {
		//
		Map<UnicodeBlock, String> map = null;
		//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
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
			Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), UnicodeBlock.HIRAGANA,
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
			Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), UnicodeBlock.BASIC_LATIN,
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
		return Util.collect(
				Util.stream(MultimapUtil.get(createUnicodeBlockCharacterMultimap(ElementUtil.text(element)),
						UnicodeBlock.HIRAGANA)),
				Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append,
						StringBuilder::toString));
		//
	}

	@Nullable
	private static String getRomaji(final Element element) {
		//
		return Util.collect(
				Util.filter(
						Util.stream(MultimapUtil.get(createUnicodeBlockCharacterMultimap(NodeUtil.attr(element, "alt")),
								UnicodeBlock.BASIC_LATIN)),
						c -> !Character.isWhitespace(c)),
				Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append,
						StringBuilder::toString));
		//
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

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}