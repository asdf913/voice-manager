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

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean
		extends StringMultiMapFromResourceFactoryBean {

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
		final Document document = testAndApply(Objects::nonNull,
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
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if (Util.matches(Util.matcher(PatternMap
					.getPattern(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), "^Ｎｏ.\\d+$"),
					ElementUtil.text(e = es.get(i)))) && NodeUtil.hasAttr(e, "href")) {
				//
				MultimapUtil
						.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								toMultimapByDocument(testAndApply(
										Objects::nonNull, testAndApply(StringUtils::isNotBlank,
												NodeUtil.absUrl(e, "href"), x -> new URI(x).toURL(), null),
										x -> Jsoup.parse(x, 0), null)));
				//
				break;// TODO
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
		MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
				toMultimap(Util.toList(NodeUtil.nodeStream(document))));
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
		TextNode textNode = null;
		//
		Pattern pattern = null;
		//
		Matcher matcher = null;
		//
		String tagName, text, s = null;
		//
		Integer end = null;
		//
		Multimap<String, String> multimap = null;
		//
		char[] cs = null;
		//
		int size = 0, length;
		//
		for (final Node node : nodes) {
			//
			if (Objects.equals("建設中路線", Util.toString(node))) {
				//
				b = true;
				//
			} else if (Boolean.logicalAnd(b,
					Boolean.logicalOr(
							StringUtils.equalsIgnoreCase("img",
									tagName = ElementUtil.tagName(Util.cast(Element.class, node))),
							StringUtils.equalsIgnoreCase("p", tagName)))) {
				//
				b = false;
				//
			} // if
				//
			if (Boolean.logicalAnd(b, (textNode = Util.cast(TextNode.class, node)) != null)) {
				//
				matcher = Util.matcher(
						pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("（(\\p{InHIRAGANA}+)）")),
						text = textNode.text());
				//
				end = null;
				//
				while (Util.find(matcher)) {
					//
					size = MultimapUtil.size(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create));
					//
					MultimapUtil.putAll(multimap, toMultimap(
							cs = ObjectUtils.getIfNull(cs, () -> new char[] { '区', '\u3000' }), matcher, text, end));
					//
					testAndAccept(
							(x, y) -> Util.and(StringUtils.countMatches(x, '区') == 1,
									StringUtils.countMatches(x, '\u3000') == 1,
									StringUtils.indexOf(x, '区') > StringUtils.indexOf(x, '\u3000')),
							s = StringUtils.substring(text, Util.intValue(end, 0), start(matcher)), multimap,
							(x, y) -> removeAll(y, StringUtils.substringAfter(x, "\u3000")));
					//
					if (Boolean.logicalAnd(size == MultimapUtil.size(multimap),
							(length = StringUtils.length(s)) >= 2)) {
						//
						MultimapUtil.put(multimap, StringUtils.substring(s, length - 2, length),
								testAndApply(x -> Util.groupCount(x) > 0, matcher, x -> Util.group(x, 1), Util::group));
						//
					} // if
						//
					end = end(matcher);
					//
				} // while
					//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	private static void removeAll(@Nullable final Multimap<?, ?> instance, final Object key) {
		if (instance != null) {
			instance.removeAll(key);
		}
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
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean::toMultimap6);
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
					map = Collections.singletonMap(Pair.of("八幡東区枝光", "えだみつ"), Pair.of("枝光", "えだみつ"));
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

	private static Multimap<String, String> replaceMultimapEntries(final Multimap<String, String> mm,
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
				if (mm.containsEntry(k = Util.getKey(key = Util.getKey(entry)), v = Util.getValue(key))
						&& (value = Util.getValue(entry)) != null
						&& (result = ObjectUtils.getIfNull(result, () -> LinkedHashMultimap.create(mm))) != null) {
					//
					result.remove(k, v);
					//
					result.put(Util.getKey(value), Util.getValue(value));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return ObjectUtils.defaultIfNull(result, mm);
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
		int a, b, c;
		//
		if (((a = StringUtils.indexOf(s, '（')) < (b = StringUtils.indexOf(s, '）')) && b == StringUtils.length(s) - 1
				&& StringUtils.countMatches(s, '区') == 1 && (c = StringUtils.indexOf(s, '区')) < a)
				|| (StringUtils.endsWith(s, "目")
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