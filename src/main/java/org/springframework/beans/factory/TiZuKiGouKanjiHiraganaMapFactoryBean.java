package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 * https://web.archive.org/web/20211126172558/http://www.gsi.go.jp/KIDS/map-sign-tizukigou-h14kigou-itiran.htm
 */
public class TiZuKiGouKanjiHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0), null);
		//
		final List<Element> es = ElementUtil.select(document, "ruby");
		//
		final Map<String, String> map = Util.collect(
				Util.map(Util.stream(es),
						x -> Pair.of(text(ElementUtil.select(x, "rb")), text(ElementUtil.select(x, "rt")))),
				LinkedHashMap::new, (a, b) -> Util.put(a, Util.getKey(b), Util.getValue(b)), Util::putAll);
		//
		Util.putAll(map, toMap(ElementUtil.select(document, ".aly_tx_xxs")));
		//
		return map;
		//
	}

	private static Map<String, String> toMap(final Iterable<Element> es) {
		//
		if (Util.iterator(es) == null) {
			//
			return null;
			//
		} // if
			//
		Map<String, String> map = null;
		//
		String kanji, string, hiragana = null;
		//
		Node previousSibling = null;
		//
		TextNode textNode = null;
		//
		for (final Element e : es) {
			//
			if (StringUtils.isNotBlank(kanji = StringUtils.trim(Util.toString(previousSibling(e))))) {
				//
				if (StringUtils.contains(kanji, '（')) {
					//
					Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new),
							StringUtils.substringAfter(kanji, '（'),
							getStringByUnicodeBlock(ElementUtil.text(e), UnicodeBlock.HIRAGANA));
					//
					continue;
					//
				} else if (StringUtils.contains(kanji, '・')) {
					//
					Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new),
							getStringByUnicodeBlock(StringUtils.substringAfter(kanji, '・'),
									UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
							getStringByUnicodeBlock(ElementUtil.text(e), UnicodeBlock.HIRAGANA));
					//
					continue;
					//
				} else if (allMatch(kanji, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)) {
					//
					Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), kanji,
							getStringByUnicodeBlock(ElementUtil.text(e), UnicodeBlock.HIRAGANA));
					//
					continue;
					//
				} else {
					//
					hiragana = getStringByUnicodeBlock(ElementUtil.text(e), UnicodeBlock.HIRAGANA);
					//
					if (Objects.equals(string = getStringByUnicodeBlock(kanji, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
							"車線")) {
						//
						Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), string, hiragana);
						//
						continue;
						//
					} else if (StringUtils.endsWith(string, "以外")) {
						//
						Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), "以外", hiragana);
						//
						continue;
						//
					} else if (StringUtils.endsWith(string, "支庁界")) {
						//
						Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), "支庁界", hiragana);
						//
						continue;
						//
					} else if (StringUtils.endsWith(string, "科樹林")) {
						//
						Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), kanji,
								getStringByUnicodeBlock(kanji, UnicodeBlock.KATAKANA)
										+ getStringByUnicodeBlock(ElementUtil.text(e), UnicodeBlock.HIRAGANA));
						//
						continue;
						//
					} // if
						//
				} // if
					//
			} // if
				//
			previousSibling = e;
			//
			textNode = null;
			//
			while ((previousSibling = previousSibling(previousSibling)) != null) {
				//
				if (previousSibling instanceof TextNode && StringUtils.isNotBlank(Util.toString(previousSibling))) {
					//
					textNode = (TextNode) previousSibling;
					//
					continue;
					//
				} // if
					//
			} // while
				//
			hiragana = getStringByUnicodeBlock(ElementUtil.text(e), UnicodeBlock.HIRAGANA);
			//
			if (allMatch(kanji = Util.toString(textNode), UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)) {
				//
				Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), kanji, hiragana);
				//
			} else if (StringUtils.contains(kanji, '・')) {
				//
				final String[] ss1 = StringUtils.split(kanji, '・');
				//
				final String[] ss2 = StringUtils.split(ElementUtil.text(e), '・');
				//
				for (int i = 0; ss1 != null && i < ss1.length; i++) {
					//
					for (int j = 0; ss2 != null && j < Math.min(ss1.length, ss2.length); j++) {
						//
						Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), ss1[i],
								getStringByUnicodeBlock(ss2[i], UnicodeBlock.HIRAGANA));
						//
					} // for
						//
				} // for
					//
			} else {
				//
				final List<String> ss1 = Util.toList(Util.filter(
						testAndApply(Objects::nonNull, split(kanji, "\\p{InHiragana}"), Arrays::stream, null),
						StringUtils::isNotEmpty));
				//
				final List<String> ss2 = Util.toList(Util.filter(testAndApply(Objects::nonNull,
						split(ElementUtil.text(e), "\\P{InHiragana}"), Arrays::stream, null), StringUtils::isNotEmpty));
				//
				for (int i = 0; ss1 != null && i < ss1.size(); i++) {
					//
					for (int j = 0; ss2 != null && j < Math.min(ss1.size(), ss2.size()); j++) {
						//
						Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), ss1.get(i), ss2.get(i));
						//
					} // for
						//
				} // for
					//
			} // if
				//
		} // for
			//
		return map;
		//
	}

	private static String[] split(final String a, final String b) {
		return a != null && b != null ? a.split(b) : null;
	}

	private static boolean allMatch(final String string, final UnicodeBlock unicodeBlock) {
		//
		return string != null && string.chars().allMatch(
				x -> Boolean.logicalOr(unicodeBlock == null, Objects.equals(unicodeBlock, UnicodeBlock.of(x))));
		//
	}

	private static String getStringByUnicodeBlock(final String string, final UnicodeBlock unicodeBlock) {
		//
		return Util.toString(string != null ? string.chars()
				.filter(x -> Boolean.logicalOr(unicodeBlock == null, Objects.equals(unicodeBlock, UnicodeBlock.of(x))))
				.collect(StringBuilder::new, (a, b) -> append(a, (char) b), StringBuilder::append) : null);
		//
	}

	private static void append(final StringBuilder instance, final char c) {
		if (instance != null) {
			instance.append(c);
		}
	}

	private static Node previousSibling(final Node instance) {
		return instance != null ? instance.previousSibling() : null;
	}

	@Nullable
	private static String text(@Nullable final Elements instance) {
		return instance != null ? instance.text() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}