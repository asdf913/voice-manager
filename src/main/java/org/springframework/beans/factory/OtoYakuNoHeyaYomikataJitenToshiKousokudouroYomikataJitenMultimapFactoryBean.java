package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
		final List<Element> es1 = ElementUtil.select(document, "table[border=\"1\"] tr td[colspan=\"3\"]");
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
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						toMultimap(pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("\\p{InHiragana}")),
								s1,
								IterableUtils.size(nextElementSiblings = ElementUtil.nextElementSiblings(e)) > 1
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
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean::toMultimap3);
		//
		IValue0<Multimap<String, String>> iValue0 = null;
		//
		PatternMap patternMap = null;
		//
		for (int i = 0; i < IterableUtils.size(functions); i++) {
			//
			if ((iValue0 = Util.apply(IterableUtils.get(functions, i),
					patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), s)) != null) {
				//
				return IValue0Util.getValue0(iValue0);
				//
			} // for
				//
		} // for
			//
		return null;
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
		return Unit.with(multimap);
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
		if (StringUtils.countMatches(s, '）') <= 0) {
			//
			return null;
			//
		} // if
			//
		int a = StringUtils.indexOf(s, '市');
		//
		int b = StringUtils.indexOf(s, '（');
		//
		int c = StringUtils.indexOf(s, "く）");
		//
		if (Boolean.logicalAnd(a < b, b < c)) {
			//
			return Unit.with(ImmutableMultimap.of(StringUtils.substring(s, a + 1, b),
					StringUtils.substring(s, b + 1, Math.min(c + 1, StringUtils.length(s)))));
			//
		} else if ((a = StringUtils.indexOf(s, '（')) < (b = StringUtils.indexOf(s, '）'))
				&& b == StringUtils.length(s) - 1 && StringUtils.countMatches(s, '区') == 1
				&& (c = StringUtils.indexOf(s, '区')) < a) {
			//
			return Unit
					.with(ImmutableMultimap.of(StringUtils.substring(s, c + 1, a), StringUtils.substring(s, a + 1, b)));
			//
		} else if (StringUtils.endsWith(s, "目") && (a = StringUtils.indexOf(s, '（')) < (b = StringUtils.indexOf(s, '）'))
				&& StringUtils.countMatches(s, '区') == 1 && (c = StringUtils.indexOf(s, '区')) < a) {
			//
			return Unit
					.with(ImmutableMultimap.of(StringUtils.substring(s, c + 1, a), StringUtils.substring(s, a + 1, b)));
			//
		} else if ((a = StringUtils.indexOf(s, '（')) < (b = StringUtils.indexOf(s, '）'))
				&& StringUtils.countMatches(s, '市') == 1 && (c = StringUtils.indexOf(s, '市')) < a) {
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