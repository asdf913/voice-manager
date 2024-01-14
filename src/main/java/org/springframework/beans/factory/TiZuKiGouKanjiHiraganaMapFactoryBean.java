package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

/**
 * https://web.archive.org/web/20211126172558/http://www.gsi.go.jp/KIDS/map-sign-tizukigou-h14kigou-itiran.htm
 */
public class TiZuKiGouKanjiHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

	private String url = null;

	private Iterable<Link> links = null;

	private IValue0<String> text, description = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setLinks(final Iterable<Link> links) {
		this.links = links;
	}

	public void setText(final String text) {
		this.text = Unit.with(text);
	}

	public void setDescription(final String description) {
		this.description = Unit.with(description);
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
		List<Link> ls = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, links != null ? links.spliterator() : null,
						x -> StreamSupport.stream(x, false), null),
				x -> text != null && x != null && Objects.equals(x.getText(), IValue0Util.getValue0(text))));
		//
		int size = IterableUtils.size(ls);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (size == 1) {
			//
			final Link link = IterableUtils.get(ls, 0);
			//
			return toMap(link != null ? link.getUrl() : null);
			//
		} // if
			//
		if ((size = IterableUtils.size(ls = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, links != null ? links.spliterator() : null,
						x -> StreamSupport.stream(x, false), null),
				x -> description != null && x != null
						&& Objects.equals(x.getDescription(), IValue0Util.getValue0(description)))))) > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (size == 1) {
			//
			final Link link = IterableUtils.get(ls, 0);
			//
			return toMap(link != null ? link.getUrl() : null);
			//
		} // if
			//
		return toMap(url);
		//
	}

	private static Map<String, String> toMap(final String url) throws Exception {
		//
		final Document document = testAndApply(
				Objects::nonNull, testAndApply(x -> isAbsolute(x),
						testAndApply(StringUtils::isNotBlank, url, URI::new, null), x -> toURL(x), null),
				x -> Jsoup.parse(x, 0), null);
		//
		final Map<String, String> map = Util.collect(
				Util.map(Util.stream(ElementUtil.select(document, "ruby")),
						x -> Pair.of(text(ElementUtil.select(x, "rb")), text(ElementUtil.select(x, "rt")))),
				LinkedHashMap::new, (a, b) -> Util.put(a, Util.getKey(b), Util.getValue(b)), Util::putAll);
		//
		Util.putAll(map, toMap(ElementUtil.select(document, ".aly_tx_xxs")));
		//
		return map;
		//
	}

	@Nullable
	private static URL toURL(@Nullable final URI instance) throws MalformedURLException {
		//
		if (instance == null || !isAbsolute(instance)) {
			//
			return null;
			//
		} // if
			//
		return instance.toURL();
		//
	}

	private static boolean isAbsolute(@Nullable final URI instance) {
		return instance != null && instance.isAbsolute();
	}

	@Nullable
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
		Node previousSibling = null;
		//
		TextNode textNode = null;
		//
		Map<String, String> m = null;
		//
		for (final Element e : es) {
			//
			if ((m = toMap(e, StringUtils.trim(Util.toString(previousSibling(e))))) != null) {
				//
				Util.putAll(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), m);
				//
				continue;
				//
			} // if
				//
			previousSibling = e;
			//
			textNode = null;
			//
			while ((previousSibling = previousSibling(previousSibling)) != null) {
				//
				if (previousSibling instanceof TextNode tn && StringUtils.isNotBlank(Util.toString(previousSibling))) {
					//
					textNode = tn;
					//
					break;
					//
				} // if
					//
			} // while
				//
			Util.putAll(map, toMap(e, Util.toString(textNode),
					getStringByUnicodeBlock(ElementUtil.text(e), UnicodeBlock.HIRAGANA)));
			//
		} // for
			//
		return map;
		//
	}

	@Nullable
	private static Map<String, String> toMap(@Nullable final Element e, final String kanji) {
		//
		if (StringUtils.isNotBlank(kanji)) {
			//
			if (StringUtils.contains(kanji, '（')) {
				//
				return Collections.singletonMap(StringUtils.substringAfter(kanji, '（'),
						getStringByUnicodeBlock(ElementUtil.text(e), UnicodeBlock.HIRAGANA));
				//
			} else if (StringUtils.contains(kanji, '・')) {
				//
				return Collections.singletonMap(
						getStringByUnicodeBlock(StringUtils.substringAfter(kanji, '・'),
								UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
						getStringByUnicodeBlock(ElementUtil.text(e), UnicodeBlock.HIRAGANA));
				//
			} else if (allMatch(kanji, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)) {
				//
				return Collections.singletonMap(kanji,
						getStringByUnicodeBlock(ElementUtil.text(e), UnicodeBlock.HIRAGANA));
				//
			} else {
				//
				final String string = getStringByUnicodeBlock(kanji, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
				//
				final String hiragana = getStringByUnicodeBlock(ElementUtil.text(e), UnicodeBlock.HIRAGANA);
				//
				if (Objects.equals(string, "車線")) {
					//
					return Collections.singletonMap(string, hiragana);
					//
				} else if (StringUtils.endsWith(string, "以外")) {
					//
					return Collections.singletonMap("以外", hiragana);
					//
				} else if (StringUtils.endsWith(string, "支庁界")) {
					//
					return Collections.singletonMap("支庁界", hiragana);
					//
				} else if (StringUtils.endsWith(string, "科樹林")) {
					//
					return Collections.singletonMap(kanji, getStringByUnicodeBlock(kanji, UnicodeBlock.KATAKANA)
							+ getStringByUnicodeBlock(ElementUtil.text(e), UnicodeBlock.HIRAGANA));
					//
				} // if
					//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Map<String, String> toMap(@Nullable final Element e, final String kanji, final String hiragana) {
		//
		if (allMatch(kanji, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)) {
			//
			return Collections.singletonMap(kanji, hiragana);
			//
		} else if (StringUtils.contains(kanji, '・')) {
			//
			return toMap(testAndApply(Objects::nonNull, StringUtils.split(kanji, '・'), Arrays::asList, null),
					testAndApply(Objects::nonNull, StringUtils.split(ElementUtil.text(e), '・'), Arrays::asList, null),
					x -> getStringByUnicodeBlock(x, UnicodeBlock.HIRAGANA));
			//
		} // if
			//
		return toMap(
				Util.toList(Util.filter(
						testAndApply(Objects::nonNull, split(kanji, "\\p{InHiragana}"), Arrays::stream, null),
						StringUtils::isNotEmpty)),
				Util.toList(Util.filter(testAndApply(Objects::nonNull, split(ElementUtil.text(e), "\\P{InHiragana}"),
						Arrays::stream, null), StringUtils::isNotEmpty)),
				UnaryOperator.identity());
		//
	}

	@Nullable
	private static Map<String, String> toMap(final Iterable<String> ss1, final Iterable<String> ss2,
			@Nullable final UnaryOperator<String> function) {
		//
		if (Util.iterator(ss1) == null) {
			//
			return null;
			//
		} // if
			//
		Map<String, String> map = null;
		//
		for (int i = 0; i < Math.min(IterableUtils.size(ss1), IterableUtils.size(ss2)); i++) {
			//
			if (i < IterableUtils.size(ss2)) {
				//
				Util.put(map = ObjectUtils.getIfNull(map, LinkedHashMap::new), IterableUtils.get(ss1, i),
						function != null ? function.apply(IterableUtils.get(ss2, i)) : IterableUtils.get(ss2, i));
				//
			} // if
				//
		} // for
			//
		return map;
		//
	}

	@Nullable
	private static String[] split(@Nullable final String a, @Nullable final String b) {
		return a != null && b != null ? a.split(b) : null;
	}

	private static boolean allMatch(@Nullable final String string, final UnicodeBlock unicodeBlock) {
		//
		return string != null && string.chars().allMatch(
				x -> Boolean.logicalOr(unicodeBlock == null, Objects.equals(unicodeBlock, UnicodeBlock.of(x))));
		//
	}

	private static String getStringByUnicodeBlock(@Nullable final String string, final UnicodeBlock unicodeBlock) {
		//
		return Util.toString(string != null ? string.chars()
				.filter(x -> Boolean.logicalOr(unicodeBlock == null, Objects.equals(unicodeBlock, UnicodeBlock.of(x))))
				.collect(StringBuilder::new, (a, b) -> Util.append(a, (char) b), StringBuilder::append) : null);
		//
	}

	@Nullable
	private static Node previousSibling(@Nullable final Node instance) {
		return instance != null ? instance.previousSibling() : null;
	}

	@Nullable
	private static String text(@Nullable final Elements instance) {
		return instance != null ? instance.text() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate,
			@Nullable final T value, final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}