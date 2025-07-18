package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
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
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.TextNodeUtil;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.mariten.kanatools.KanaConverter;

public class OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean
		extends StringMultiMapFromResourceFactoryBean {

	private static final String PATTERN_HIRAGANA = "^\\p{InHiragana}+$";

	@URL("https://hiramatu-hifuka.com/onyak/onyak2/tosiko01.html")
	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final IValue0<Multimap<String, String>> iValue0 = getIvalue0();
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
		//
		final List<Element> es = ElementUtil.select(document, "a");
		//
		Element e = null;
		//
		PatternMap patternMap = null;
		//
		Multimap<String, String> multimap = toMultimapByDocument(document);
		//
		int size;
		//
		for (int i = 0; i < IterableUtils.size(es); i++) {
			//
			if (Util.matches(Util.matcher(PatternMap
					.getPattern(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), "^Ｎｏ.\\d+$"),
					ElementUtil.text(e = IterableUtils.get(es, i)))) && NodeUtil.hasAttr(e, "href")) {
				//
				document = testAndApply(Objects::nonNull, testAndApply(StringUtils::isNotBlank,
						NodeUtil.absUrl(e, "href"), x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0), null);
				//
				size = MultimapUtil.size(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create));
				//
				// 路線呼称 or 出入口 or ジャンクション名
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						toMultimap(document, patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
								Arrays.asList("路線呼称", "出入口", "ジャンクション名"), Arrays.asList("よみ", "英語表記")));
				//
				if (MultimapUtil.size(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create)) == size) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							toMultimapByDocument(document));
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Element element, final PatternMap patternMap,
			final Iterable<String> names, final Iterable<String> nextElementSiblingNames) {
		//
		if (Util.iterator(names) == null) {
			//
			return null;
			//
		} // if
			//
		Collection<Element> tds;
		//
		Element td = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (final String string : names) {
			//
			if (IterableUtils
					.size(tds = Util.toList(Util.filter(Util.stream(ElementUtil.select(element, "td")),
							x -> Objects.equals(ElementUtil.text(x), string)))) <= 0
					|| !Iterables.elementsEqual(nextElementSiblingNames,
							Util.toList(Util.map(
									Util.stream(ElementUtil.nextElementSiblings(td = IterableUtils.get(tds, 0))),
									ElementUtil::text)))) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(patternMap, ElementUtil.nextElementSiblings(ElementUtil.parent(td))));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final Iterable<Element> trs) {
		//
		if (Util.iterator(trs) == null) {
			//
			return null;
			//
		} // if
			//
		Multimap<String, String> multimap = null;
		//
		Iterable<Element> tds = null;
		//
		String s1, s2;
		//
		Matcher m1, m2;
		//
		String[] ss = null;
		//
		for (final Element tr : trs) {
			//
			if (IterableUtils.size(tds = ElementUtil.children(tr)) < 2) {
				//
				continue;
				//
			} // if
				//
			if (Util.matches(Util.matcher(PatternMap.getPattern(patternMap, "^\\p{InCJKUnifiedIdeographs}+$"),
					s1 = ElementUtil.text(IterableUtils.get(tds, 0))))
					&& Util.matches(Util.matcher(PatternMap.getPattern(patternMap, PATTERN_HIRAGANA),
							s2 = ElementUtil.text(IterableUtils.get(tds, 1))))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
				//
			} else if (Util
					.matches(m1 = Util.matcher(
							PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}+$"),
							ElementUtil.text(IterableUtils.get(tds, 0))))
					&& Util.groupCount(m1) > 0
					&& Util.matches(
							m2 = Util.matcher(PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)\\p{InKatakana}+$"),
									ElementUtil.text(IterableUtils.get(tds, 1))))
					&& Util.groupCount(m2) > 0) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m1, 1), Util.group(m2, 1));
				//
			} else if (Util
					.matches(m1 = Util.matcher(
							PatternMap.getPattern(patternMap,
									"^(\\p{InCJKUnifiedIdeographs}+)（[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+）$"),
							ElementUtil.text(IterableUtils.get(tds, 0))))
					&& Util.groupCount(m1) > 0
					&& Util.matches(Util.matcher(PatternMap.getPattern(patternMap, PATTERN_HIRAGANA),
							s2 = ElementUtil.text(IterableUtils.get(tds, 1))))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m1, 1), s2);
				//
			} else if (Util
					.matches(m1 = Util.matcher(
							PatternMap.getPattern(patternMap,
									"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)$"),
							ElementUtil.text(IterableUtils.get(tds, 0))))
					&& Util.groupCount(m1) > 2
					&& Util.matches(Util.matcher(PatternMap.getPattern(patternMap, PATTERN_HIRAGANA),
							s2 = ElementUtil.text(IterableUtils.get(tds, 1))))
					&& (ss = StringUtils.split(s2, Util.group(m1, 2))) != null && ss.length == 2) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m1, 1), ss[0]);
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m1, 3), ss[1]);
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	private static Multimap<String, String> toMultimapByDocument(final Document document) {
		//
		final List<Element> es1 = ElementUtil.select(document, "table[border=\"1\"] tr td");
		//
		Element e = null;
		//
		List<Element> nextElementSiblings = null;
		//
		String s1, s2 = null;
		//
		Multimap<String, String> multimap = null;
		//
		Pattern pattern = null;
		//
		int size = 0;
		//
		for (int i = 0; i < IterableUtils.size(es1); i++) {
			//
			if ((e = IterableUtils.get(es1, i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
					getUnicodeBlocks(s1 = ElementUtil.text(e)))
					&& (size = IterableUtils.size(nextElementSiblings = ElementUtil.nextElementSiblings(e))) > 1
					&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA),
							getUnicodeBlocks(s2 = ElementUtil.text(IterableUtils.get(nextElementSiblings, 1))))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
				//
			} else {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), toMultimap(
						pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("\\p{InHiragana}")), s1,
						(size = IterableUtils.size(nextElementSiblings = ElementUtil.nextElementSiblings(e))) > 1
								? ElementUtil.text(IterableUtils.get(nextElementSiblings, 1))
								: null));
				//
			} // if
				//
			if (size > 2) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						toMultimap(ElementUtil.text(IterableUtils.get(nextElementSiblings, 2))));
				//
			} // if
				//
		} // for
			//
		final Map<Entry<String, String>, Entry<String, String>> map = new LinkedHashMap<>(
				Collections.singletonMap(Pair.of("川向町", "つづきくかわむこうちょう"), Pair.of("川向町", "かわむこうちょう")));
		//
		Util.put(map, Pair.of("生麦", "つるみくなまむぎ"), Pair.of("生麦", "なまむぎ"));
		//
		MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
				replaceMultimapEntries(toMultimap(Util.toList(NodeUtil.nodeStream(document))), map));
		//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Iterable<Node> nodes) {
		//
		if (Util.iterator(nodes) == null) {
			//
			return null;
			//
		} // if
			//
		boolean b = false;
		//
		String tagName, kanji = null, toString;
		//
		Multimap<String, String> multimap = null;
		//
		PatternMap patternMap = null;
		//
		Matcher matcher = null;
		//
		final Strings strings = Strings.CI;
		//
		for (final Node node : nodes) {
			//
			if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(
					patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
					"^[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}]+\\p{InCJKSymbolsAndPunctuation}(\\p{InCJKUnifiedIdeographs}+)$"),
					toString = Util.toString(node))) && Util.groupCount(matcher) > 0) {
				//
				kanji = Util.group(matcher, 1);
				//
			} // if
				//
			if (Util.matches(matcher = Util
					.matcher(PatternMap.getPattern(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
							"^（(\\p{InHiragana}+)）"), toString))
					&& Util.groupCount(matcher) > 0) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), kanji,
						Util.group(matcher, 1));
				//
				continue;
				//
			} // if
				//
			if (Objects.equals("建設中路線", Util.toString(node))) {
				//
				b = true;
				//
			} else if (Boolean.logicalAnd(b,
					Boolean.logicalOr(
							StringsUtil.equals(strings, "img",
									tagName = ElementUtil.tagName(Util.cast(Element.class, node))),
							StringsUtil.equals(strings, "p", tagName)))) {
				//
				b = false;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(TextNodeUtil.text(Util.cast(TextNode.class, node))));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(@Nullable final char[] cs, final Matcher matcher,
			final String text, @Nullable final Number end) {
		//
		Multimap<String, String> multimap = null;
		//
		if (cs != null) {
			//
			String s = null;
			//
			for (final char c : cs) {
				//
				if (StringUtils.countMatches(s = StringUtils.substring(text, Util.intValue(end, 0), start(matcher)),
						c) == 1) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							StringUtils.substringAfter(s, c),
							testAndApply(x -> Util.groupCount(x) > 0, matcher, x -> Util.group(x, 1), Util::group));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Integer start(@Nullable final MatchResult instance) {
		return instance != null ? Integer.valueOf(instance.start()) : null;
	}

	@Nullable
	private static Integer end(@Nullable final MatchResult instance) {
		return instance != null ? Integer.valueOf(instance.end()) : null;
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final String s) {
		//
		final List<BiFunction<PatternMap, String, IValue0<Multimap<String, String>>>> functions = Arrays.asList(
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean::toMultimap1,
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean::toMultimap2,
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean::toMultimap3,
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean::toMultimap4,
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean::toMultimap5,
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean::toMultimap6,
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean::toMultimap7,
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean::toMultimap8,
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean::toMultimap9,
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean::toMultimap10);
		//
		IValue0<Multimap<String, String>> iValue0 = null;
		//
		PatternMap patternMap = null;
		//
		Map<Entry<String, String>, Entry<String, String>> map = null;
		//
		for (int i = 0; i < IterableUtils.size(functions); i++) {
			//
			if ((iValue0 = Util.apply(IterableUtils.get(functions, i),
					patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), s)) != null) {
				//
				if (map == null) {
					//
					Util.put(
							map = new LinkedHashMap<>(
									Collections.singletonMap(Pair.of("八幡東区枝光", "えだみつ"), Pair.of("枝光", "えだみつ"))),
							Pair.of("台東区北上野", "たいとうくうえの"), Pair.of("台東区上野", "たいとうくうえの"));
					//
				} // if
					//
				return replaceMultimapEntries(IValue0Util.getValue0(iValue0), map);
				//
			} // for
				//
		} // for
			//
		return null;
		//
	}

	private static Multimap<String, String> replaceMultimapEntries(@Nullable final Multimap<String, String> mm,
			final Map<Entry<String, String>, Entry<String, String>> map) {
		//
		final Collection<Entry<Entry<String, String>, Entry<String, String>>> entrySet = Util.entrySet(map);
		//
		Multimap<String, String> result = null;
		//
		if (mm != null && Util.iterator(entrySet) != null) {
			//
			String k, v;
			//
			Entry<String, String> key, value;
			//
			for (final Entry<Entry<String, String>, Entry<String, String>> entry : entrySet) {
				//
				if (MultimapUtil.containsEntry(mm, k = Util.getKey(key = Util.getKey(entry)), v = Util.getValue(key))
						&& (value = Util.getValue(entry)) != null) {
					//
					MultimapUtil.remove(result = ObjectUtils.getIfNull(result, () -> LinkedHashMultimap.create(mm)), k,
							v);
					//
					MultimapUtil.put(result, Util.getKey(value), Util.getValue(value));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return ObjectUtils.getIfNull(result, mm);
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap1(final PatternMap patternMap, final String s) {
		//
		Matcher matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^高速(\\p{InCJKUnifiedIdeographs}+)（\\p{InHalfwidthAndFullwidthForms}）へ分岐\\s+（(\\p{InHiragana}+)）$"), s);
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(matcher) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^高速\\p{InHalfwidthAndFullwidthForms}号(\\p{InCJKUnifiedIdeographs}+)（\\p{InHalfwidthAndFullwidthForms}）へ分岐\\s+（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+市(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InCJKUnifiedIdeographs}+$"),
				s)) && Util.groupCount(matcher) > 3) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 3), Util.group(matcher, 4));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
		if (StringUtils.countMatches(s, '）') <= 1) {
			//
			return null;
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap2(final PatternMap patternMap, final String s) {
		//
		// 福岡前原道路（ふくおかまえばるどうろ）へ
		//
		Matcher matcher = Util
				.matcher(PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）へ$"), s);
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(matcher) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 福岡市博多区千代（ちよ）六丁目
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+区(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InCJKUnifiedIdeographs}丁目$"),
				s)) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 北九州市戸畑区大字戸畑（とばたくとばた）
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+市(\\p{InCJKUnifiedIdeographs}+)大字(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(matcher) > 2) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					StringUtils.join(Util.group(matcher, 1), Util.group(matcher, 2)), Util.group(matcher, 3));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 北九州市八幡区西区茶屋の原（やはたにしくちゃやのはる）二丁目 九州自動車道八幡ＩＣへ
			//
		String g3 = null;
		//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+市(\\p{InCJKUnifiedIdeographs}+)の(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）[\\p{InCJKUnifiedIdeographs}|\\s|\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}]+$"),
				s)) && Util.groupCount(matcher) > 2
				&& StringUtils.countMatches(g3 = Util.group(matcher, 3), 'の') == 1) {
			//
			final String[] ss = StringUtils.split(g3, 'の');
			//
			for (int i = 0; ss != null && i < ss.length; i++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, i + 1), ss[i]);
				//
			} // for
				//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap3(final PatternMap patternMap, final String s) {
		//
		// 並木（なみき）横浜横須賀道路に接続 金沢支線（かなざわしせん）へ続く
		//
		Matcher matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\s+(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$"),
				s);
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(matcher) && Util.groupCount(matcher) > 3) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 3), Util.group(matcher, 4));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 北九州市小倉南区（こくらみなみく）長野二丁目
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+市(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InCJKUnifiedIdeographs}+$"),
				s)) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 北九州市小倉北区許斐町（このみまち）
			//
		if (Util.matches(
				matcher = Util.matcher(
						PatternMap.getPattern(patternMap,
								"^\\p{InCJKUnifiedIdeographs}+区(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
						s))
				&& Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 高速６号向島線（６）へ分岐 （むこうじません） 高速１号上野線（１）へ分岐 （うえのせん）
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^高速\\p{InHalfwidthAndFullwidthForms}号(\\p{InCJKUnifiedIdeographs}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}|\\s]+（(\\p{InHiragana}+)）\\s?高速\\p{InHalfwidthAndFullwidthForms}号(\\p{InCJKUnifiedIdeographs}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}|\\s]+（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(matcher) > 3) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 3), Util.group(matcher, 4));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 高速川口線（s１）に接続 （かわぐちせん）
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^高速(\\p{InCJKUnifiedIdeographs}+)（[\\p{InHalfwidthAndFullwidthForms}|\\p{InBasicLatin}]+[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\s（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap4(final PatternMap patternMap, final String s) {
		//
		// 高速１１号台場線（１１）と分岐 （だいばせん）
		//
		Matcher matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^高速\\p{InHalfwidthAndFullwidthForms}+号(\\p{InCJKUnifiedIdeographs}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+[ ]（(\\p{InHiragana}+)）$"),
				s);
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(matcher) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 高速神奈川１号横羽線（ｋ１）に接続 （よこはねせん）・環状八号線に出入
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^高速[\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}]+号(\\p{InCJKUnifiedIdeographs}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+\\s（(\\p{InHiragana}+)）[\\p{Inkatakana}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$"),
				s)) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 江北橋（こうほくばし）付近で 高速中央環状線（Ｃ２）と接続。将来は 江北橋付近で高速中央環状王子線に接続
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\s[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}|\\p{InHiragana}|\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKSymbolsAndPunctuation}]+\\s[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$"),
				s)) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 谷河内で京葉道路に接続 （やごうち）（けいようどうろ）
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHiragana}(\\p{InCJKUnifiedIdeographs}+)[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+\\s（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(matcher) > 3) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 3));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 2), Util.group(matcher, 4));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 常磐自動車道に接続 （じょうばんじどうしゃどう） 東京外環自動車道に接続
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+\\s（(\\p{InHiragana}+)）\\s[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$"),
				s)) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap5(final PatternMap patternMap, final String s) {
		//
		// 狩場（かりば）料金所で 横浜横須賀道路に接続 （よこはまよこすかどうろ）
		//
		Matcher matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\s+(\\p{InCJKUnifiedIdeographs}+)\\p{InHiragana}\\p{InCJKUnifiedIdeographs}+\\s+（(\\p{InHiragana}+)）$"),
				s);
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(matcher) && Util.groupCount(matcher) > 3) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 3), Util.group(matcher, 4));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 市川料金所 市川市高谷（いちかわしこうや）で 東関東自動車道に接続
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+\\s+(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InHiragana}\\s+[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$"),
				s)) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 高速神奈川５号大黒線（ｋ５）が分岐 （だいこくせん）
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}号(\\p{InCJKUnifiedIdeographs}+)[\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}|\\p{InHiragana}]+\\s（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 第三京浜道路・横浜新道に接続 （だいさんけいひんどうろ）（よこはましんどう）
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+\\s（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(matcher) > 3) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 3));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 2), Util.group(matcher, 4));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 高速湾岸線（Ｂ）（わんがんせん）から分岐
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^高速(\\p{InCJKUnifiedIdeographs}+)[\\p{InHalfwidthAndFullwidthForms}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+$"),
				s)) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap6(final PatternMap patternMap, final String s) {
		//
		// 市川市高谷（いちかわしこうや）で
		//
		Matcher matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InHiragana}$"), StringUtils.trim(s));
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(matcher) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 広島高速道路 ひろしまこうそくどうろ
			//
		if (Util.matches(matcher = Util.matcher(
				PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InCJKSymbolsAndPunctuation}(\\p{InHiragana}+)$"),
				StringUtils.trim(s))) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 福岡高速４号線 糟屋郡粕屋町（かすやぐんかすやまち）大字戸原（とばら）〜福岡市東区蒲田（かまた）三丁目
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}]+\\p{InCJKSymbolsAndPunctuation}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）大字(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InCJKSymbolsAndPunctuation}\\p{InCJKUnifiedIdeographs}+区(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InCJKUnifiedIdeographs}+$"),
				StringUtils.trim(s))) && Util.groupCount(matcher) > 5) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 3), Util.group(matcher, 4));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 5), Util.group(matcher, 6));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 仁保、広島呉道路（ひろしまくれどうろ）、
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+\\p{InCJKSymbolsAndPunctuation}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InCJKSymbolsAndPunctuation}$"),
				StringUtils.trim(s))) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 仁保、広島呉道路（ひろしまくれどうろ）、 海田大橋（かいたおおはし）へ
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+\\p{InCJKSymbolsAndPunctuation}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）[\\p{InCJKSymbolsAndPunctuation}|\\s]+(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InHiragana}$"),
				s)) && Util.groupCount(matcher) > 3) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 3), Util.group(matcher, 4));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap7(final PatternMap patternMap, final String s) {
		//
		// 谷河内・やごうち）
		//
		Matcher matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				s);
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(matcher) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 埼玉県さいたま市大字三浦（みうら）〜埼玉県さいたま市円阿弥（えんなみ）
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+大字(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）〜[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+市(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(matcher) > 3) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 3), Util.group(matcher, 4));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 神奈川県川崎市都筑区川向町（つづきくかわむこうちょう）〜
			//
		if ((Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+(都筑区\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InCJKSymbolsAndPunctuation}$"),
				s)) && Util.groupCount(matcher) > 1)
				|| (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
						"^\\p{InCJKUnifiedIdeographs}+区(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InCJKSymbolsAndPunctuation}$"),
						s)) && Util.groupCount(matcher) > 1)) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// [BASIC_LATIN,CJK_UNIFIED_IDEOGRAPHS,HALFWIDTH_AND_FULLWIDTH_FORMS,HIRAGANA,CJK_SYMBOLS_AND_PUNCTUATION]
			//
			// 湊町（みなとまち） 四つ橋（よつばし） 信濃橋（しなのばし） 土佐堀（とさぼり） 堂島（どうじま） 北浜（きたはま） 高麗橋（こうらいばし）
			// 本町（ほんまち） 長堀（ながほり） 道頓堀（どうとんぼり） 高津（こうづ） 夕陽丘（ゆうひがおか） えびす町（えびすちょう） なんば（なんば）＊難波
			// 湊町（みなとまち）
			// 天保山（てんぽうざん）JCT 天保山（てんぽうざん） 南港北（なんこうきた） 南港中（なんこうなか） 南港南（なんこうみなみ） 三宝（さんぼう）
			// 大浜（おおはま） 出島（でじま） 石津（いしづ） 浜寺（はまでら） 高石（たかいし） 助松（すけまつ） 助松（すけまつ）JCT 泉大津（いずみおおつ）
			// 泉大津（いずみおおつ）PA 岸和田北（きしわだきた） 岸和田南（きしわだみなみ） 貝塚（かいづか） 泉佐野北（いずみさのきた）
			// 泉佐野南（いずみさのみなみ） りんくうJCT
			// 名谷（みょうだに）JCT 垂水（たるみ）JCT 垂水（たるみ）IC
			// 伊川谷（いかわだに）JCT 永井谷（ながいたに）JCT 永井谷（ながいたに） 前開（ぜんかい）PA 前開（ぜんかい） 布施畑（ふせはた）JCT
			// 布施畑西（ふせはたにし） 布施畑東（ふせはたひがし） しあわせの村（しあわせのむら） 白川（しらかわ）PA 藍那（あいな） 箕谷（みのたに）
			// からと西（からとにし） 有馬口（ありまぐち）JCT 有馬口（ありまぐち） 五社（ごしゃ） 柳谷（やなぎだに）JCT
			//
			// [BASIC_LATIN, CJK_UNIFIED_IDEOGRAPHS, HALFWIDTH_AND_FULLWIDTH_FORMS,
			// HIRAGANA, CJK_SYMBOLS_AND_PUNCTUATION, KATAKANA]
			//
			// 南港北（なんこうきた） 天保山（てんぽうざん）JCT 北港（ほっこう）JCT 北港西（ほっこうにし） 中島（なかじま）PA 中島（なかじま）
			// 尼崎東海岸（あまがさきひがしかいがん） 尼崎末広（あまがさきすえひろ） 鳴尾浜（なるおはま） 甲子園浜（こうしえんはま） 西宮浜（にしのみやはま）
			// 南芦屋浜（みなみあしやはま） 深江浜（ふかえはま） 住吉浜（すみよしはま） 魚崎浜（うおざきはま） 六甲アイランド北（ろっこうあいらんどきた）
			//
			// [BASIC_LATIN, CJK_UNIFIED_IDEOGRAPHS, CJK_SYMBOLS_AND_PUNCTUATION,
			// HALFWIDTH_AND_FULLWIDTH_FORMS, HIRAGANA, KATAKANA]
			//
			// 環状線分岐 西長堀（にしながほり） 中之島西（なかのしまにし） 海老江（えびえ） 姫島（ひめじま） 大和田（おおわだ） 尼崎東（あまがさきひがし）
			// 尼崎（あまがさき）ミニPA 尼崎西（あまがさきにし） 武庫川（むこがわ） 西宮（にしのみや）IC 西宮（にしのみや） 芦屋（あしや） 深江（ふかえ）
			// 魚崎（うおざき） 摩耶（まや） 生田川（いくたがわ） 京橋（きょうばし） 京橋（きょうばし）PA 柳原（やなぎはら） 湊川（みなとがわ） 若宮（わかみや）
			// 月見山（つきみやま）
			//
			// [BASIC_LATIN, CJK_UNIFIED_IDEOGRAPHS, CJK_SYMBOLS_AND_PUNCTUATION,
			// HALFWIDTH_AND_FULLWIDTH_FORMS, HIRAGANA]
			//
			// 環状線分岐 中之島（なかのしま） 出入橋（でいりばし） 梅田（うめだ） 福島（ふくしま） 塚本（つかもと） 加島（かしま） 豊中南（とよなかみなみ）
			// 豊中北（とよなかきた） 大阪空港（おおさかくうこう） 池田（いけだ） 神田（こうだ） 川西小花（かわにしおばな） 池田木部（いけだきべ）
			//
		final Iterable<UnicodeBlock> ubs = getUnicodeBlocks(s);
		//
		if (Util.or(
				Objects.equals(ubs,
						Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS,
								UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS, UnicodeBlock.HIRAGANA,
								UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION)),
				Objects.equals(ubs,
						Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS,
								UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS, UnicodeBlock.HIRAGANA,
								UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION, UnicodeBlock.KATAKANA)),
				Objects.equals(ubs,
						Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS,
								UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION, UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS,
								UnicodeBlock.HIRAGANA, UnicodeBlock.KATAKANA)),
				Objects.equals(ubs,
						Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS,
								UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION, UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS,
								UnicodeBlock.HIRAGANA)))) {
			//
			multimap = toMultimap(patternMap, StringUtils.split(s, "\u3000\u3000"));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap8(final PatternMap patternMap, final String s) {
		//
		// 並木（なみき）横浜横須賀道路に接続
		//
		Matcher matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$"),
				s);
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(matcher) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 金沢支線（かなざわしせん）へ続く
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+$"),
				StringUtils.trim(s))) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 東京都台東区北上野（たいとうくうえの）〜東京都足立区本木（あだちくもとき）
			//
		if ((Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+(台東区\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InCJKSymbolsAndPunctuation}\\p{InCJKUnifiedIdeographs}+(足立区\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(matcher) > 3) ||
		// 東京都中央区晴海（はるみ）〜東京都江東区有明（ありあけ）
				(Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
						"^\\p{InCJKUnifiedIdeographs}+区(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InCJKSymbolsAndPunctuation}\\p{InCJKUnifiedIdeographs}+区(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
						s)) && Util.groupCount(matcher) > 3)) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 3), Util.group(matcher, 4));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 広島市東区福田町（ふくだちょう）〜馬木町（うまきちょう）
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+区(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InCJKSymbolsAndPunctuation}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(matcher) > 3) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 3), Util.group(matcher, 4));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap9(final PatternMap patternMap, final String s) {
		//
		// 高速神奈川２号三ツ沢線（ｋ２）が分岐 （みつざわせん）
		//
		Matcher matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}号(\\p{InCJKUnifiedIdeographs}+)ツ(\\p{InCJKUnifiedIdeographs}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+\\s+（(\\p{InHiragana}+)）$"),
				s);
		//
		Multimap<String, String> multimap = null;
		//
		String[] ss = null;
		//
		if (Util.matches(matcher) && Util.groupCount(matcher) > 2
				&& (ss = StringUtils.split(Util.group(matcher, 3), 'つ')) != null && ss.length == 2) {
			//
			for (int i = 0; i < ss.length; i++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, i + 1), ss[i]);
				//
			} // for
				//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 広島高速１号線（安芸府中道路） ひろしまこうそくいちごうせん（あきふちゅうどうろ）
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}]+（(\\p{InCJKUnifiedIdeographs}+)）[\\p{InCJKSymbolsAndPunctuation}|\\p{InHiragana}]+（(\\p{InHiragana}+)）"),
				StringUtils.trim(s))) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// 福岡高速５号線 福岡市博多区西月隈（にしつきぐま）四丁目〜福岡市西区福重（ふくしげ）三丁目
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKSymbolsAndPunctuation}]+区(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）[\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKSymbolsAndPunctuation}]+区(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\p{InCJKUnifiedIdeographs}*$"),
				StringUtils.trim(s))) && Util.groupCount(matcher) > 3) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 3), Util.group(matcher, 4));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
			// （江北・こうほく〜
			//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}+(\\p{InHiragana}+)\\p{InCJKSymbolsAndPunctuation}$"),
				s)) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) > 0) {
			//
			return Unit.with(multimap);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap10(final PatternMap patternMap, final String s) {
		//
		int a, b, c;
		//
		if (((a = StringUtils.indexOf(s, '（')) < (b = StringUtils.indexOf(s, '）')) && b == StringUtils.length(s) - 1
				&& StringUtils.countMatches(s, '区') == 1 && (c = StringUtils.indexOf(s, '区')) < a)
				|| (StringsUtil.endsWith(Strings.CS, s, "目")
						&& (a = StringUtils.indexOf(s, '（')) < (b = StringUtils.indexOf(s, '）'))
						&& StringUtils.countMatches(s, '区') == 1 && (c = StringUtils.indexOf(s, '区')) < a)) {
			//
			return Unit
					.with(ImmutableMultimap.of(StringUtils.substring(s, c + 1, a), StringUtils.substring(s, a + 1, b)));
			//
		} // if
			//
		return null;
		//
	}

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String[] ss) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher matcher = null;
		//
		String g1, g2, g3;
		//
		String[] ss2 = null;
		//
		for (int i = 0; ss != null && i < ss.length; i++) {
			//
			if (Util.matches(matcher = Util.matcher(
					PatternMap.getPattern(patternMap,
							"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）[\\w*|\\p{InKatakana}]*$"),
					StringUtils.trim(ss[i])))) {
				//
				// 湊町（みなとまち）
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 1), Util.group(matcher, 2));
				//
			} else if (Util
					.matches(
							matcher = Util.matcher(
									PatternMap.getPattern(patternMap,
											"^(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
									ss[i]))
					&& Util.groupCount(matcher) > 2
					&& StringsUtil.startsWith(Strings.CS, g3 = Util.group(matcher, 3), g1 = Util.group(matcher, 1))) {
				//
				// えびす町（えびすちょう）
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 2), StringUtils.substring(g3, StringUtils.length(g1)));
				//
			} else if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InHiragana}+)（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)"),
					ss[i])) && Util.groupCount(matcher) > 2
					&& Objects.equals(g1 = Util.group(matcher, 1), Util.group(matcher, 2))) {
				//
				// なんば（なんば）＊難波
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 3), g1);
				//
			} else if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}]+$"),
					ss[i])) && Util.groupCount(matcher) > 1) {
				//
				// 島屋東（しまやひがし）＊（仮）
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 1), Util.group(matcher, 2));
				//
			} else if ((Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
					ss[i])) && Util.groupCount(matcher) > 3
					&& (ss2 = StringUtils.split(Util.group(matcher, 4), Util.group(matcher, 2))) != null
					&& ss2.length == 2)
					|| (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
							"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)(\\p{InCJKUnifiedIdeographs}+)（([(\\p{InHiragana}]+)）$"),
							ss[i]))
							&& Util.groupCount(matcher) > 3
							&& Objects.equals(getUnicodeBlocks(g2 = Util.group(matcher, 2)),
									Collections.singletonList(UnicodeBlock.KATAKANA))
							&& (ss2 = StringUtils.split(Util.group(matcher, 4),
									KanaConverter.convertKana(g2, KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA))) != null
							&& ss2.length == 2)) {
				//
				// 四つ橋（よつばし）
				//
				// 六甲アイランド北（ろっこうあいらんどきた）
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 1), ss2[0]);
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 3), ss2[1]);
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final boolean first, @Nullable final String s1,
			@Nullable final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		if (Boolean.logicalAnd(StringUtils.isNotBlank(s1), StringUtils.isNotBlank(s2))) {
			//
			if (first) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.substring(s1, 4), s2);
				//
			} else {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
				//
			} // if
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Pattern pattern, final String s1,
			@Nullable final String s2) {
		//
		final StringBuilder sb = new StringBuilder();
		//
		final Matcher matcher = Util.matcher(pattern, s1);
		//
		while (matcher != null && matcher.find()) {
			//
			sb.append(Util.group(matcher));
			//
		} // while
			//
		Multimap<String, String> multimap = null;
		//
		if (StringUtils.length(sb) == 1 && StringUtils.countMatches(s2, sb) == 1) {
			//
			final String s = Objects.toString(sb);
			//
			final String[] ss1 = StringUtils.split(s1, s);
			//
			final String[] ss2 = StringUtils.split(s2, s);
			//
			for (int j = 0; ss1 != null && ss2 != null && j < Math.min(ss1.length, ss2.length); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), ss1[j],
						ss2[j]);
				//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static List<UnicodeBlock> getUnicodeBlocks(final String string) {
		//
		final char[] cs = Util.toCharArray(string);
		//
		if (cs != null) {
			//
			List<UnicodeBlock> unicodeBlocks = null;
			//
			for (final char c : cs) {
				//
				testAndAccept((a, b) -> b != null && !Util.contains(a, b),
						unicodeBlocks = ObjectUtils.getIfNull(unicodeBlocks, ArrayList::new), UnicodeBlock.of(c),
						Util::add);
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
		if (Util.test(instance, t, u)) {
			Util.accept(consumer, t, u);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}