package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.TextNodeUtil;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean
		extends StringMultiMapFromResourceFactoryBean {

	@URL("https://hiramatu-hifuka.com/onyak/onyak2/kosoku01.html")
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
		Multimap<String, String> multimap = null;
		//
		PatternMap patternMap = null;
		//
		MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
				toMultimap(document, patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new)));
		//
		final List<Element> es = ElementUtil.select(document, "a");
		//
		Element e = null;
		//
		List<String> urls = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if (Util.matches(Util.matcher(PatternMap
					.getPattern(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), "^Ｎｏ.\\d+$"),
					ElementUtil.text(e = es.get(i))))) {
				//
				Util.add(urls = ObjectUtils.getIfNull(urls, ArrayList::new), e.absUrl("href"));
				//
			} // if
				//
		} // for
			//
		for (int i = 0; i < IterableUtils.size(urls); i++) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(
							testAndApply(Objects::nonNull,
									testAndApply(StringUtils::isNotBlank, IterableUtils.get(urls, i),
											x -> new URI(x).toURL(), null),
									x -> Jsoup.parse(x, 0), null),
							patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new)));
			//
		} // for
			//
		return multimap;
		//
	}

	private static Multimap<String, String> toMultimap(final Document document, final PatternMap patternMap) {
		//
		final List<Element> es = ElementUtil.select(document, "td");
		//
		Element e = null;
		//
		String s1, s2;
		//
		List<Element> nextElementSiblings = null;
		//
		Multimap<String, String> multimap = null, excluded = null;
		//
		Matcher matcher = null;
		//
		int size = 0;
		//
		String[] ss = null;
		//
		for (int i = 0; i < IterableUtils.size(es); i++) {
			//
			if ((e = IterableUtils.get(es, i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (Boolean.logicalAnd(
					Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
							getUnicodeBlocks(s1 = ElementUtil.text(e))),
					IterableUtils.size(nextElementSiblings = e.nextElementSiblings()) > 1)) {
				//
				if (Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA),
						getUnicodeBlocks(s2 = ElementUtil.text(IterableUtils.get(nextElementSiblings, 1))))) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
					//
				} else if (Boolean.logicalAnd(
						Util.matches(
								matcher = Util.matcher(PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+).+"), s2)),
						Util.groupCount(matcher) > 0)) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
							Util.group(matcher, 1));
					//
				} // if
					//
			} else if (and(
					Util.matches(matcher = Util
							.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InCJKUnifiedIdeographs}+)）$"), s1)),
					Util.groupCount(matcher) > 0, IterableUtils.size(nextElementSiblings) > 1)
					&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA),
							getUnicodeBlocks(s2 = ElementUtil.text(IterableUtils.get(nextElementSiblings, 1))))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 1), s2);
				//
			} else if (and(
					Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
							"^(\\p{InCJKUnifiedIdeographs}+)の(\\p{InCJKUnifiedIdeographs}+)$"), s1)),
					Util.groupCount(matcher) > 0, IterableUtils.size(nextElementSiblings) > 1)
					&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA),
							getUnicodeBlocks(s2 = ElementUtil.text(IterableUtils.get(nextElementSiblings, 1))))
					&& StringUtils.countMatches(s2, 'の') == 1 && (ss = StringUtils.split(s2, 'の')) != null
					&& ss.length > 1) {
				//
				for (int j = 0; j < Math.min(ss.length, Util.groupCount(matcher)); j++) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(matcher, j + 1), ss[j]);
					//
				} // for
					//
			} // if
				//
			size = MultimapUtil.size(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create));
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(patternMap, s1));
			//
			if (MultimapUtil.size(multimap) > size) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(patternMap, s1, nextElementSiblings));
			//
			if (MultimapUtil.size(multimap) == size) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						toMultimap(patternMap, s1, excluded = ObjectUtils.getIfNull(excluded,
								() -> ImmutableMultimap.of("警察署", "まいばら", "現在", "まいはら"))));
				//
			} // if
				//
		} // for
			//
		MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
				toMultimap(Util.toList(Util.filter(
						Util.map(NodeUtil.nodeStream(document), x -> Util.cast(TextNode.class, x)), Objects::nonNull)),
						Pattern.compile("^（(\\p{InHIRAGANA}+)）$")));
		//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String string,
			final Multimap<?, ?> excluded) {
		//
		final Matcher matcher = Util.matcher(
				PatternMap.getPattern(patternMap, "((\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）)"), string);
		//
		Multimap<String, String> multimap = null;
		//
		String k, v;
		//
		while (Util.find(matcher) && Util.groupCount(matcher) > 2) {
			//
			if (containsEntry(excluded, k = Util.group(matcher, 2), v = Util.group(matcher, 3))) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), k, v);
			//
		} // while
			//
		return multimap;
		//
	}

	private static boolean containsEntry(@Nullable final Multimap<?, ?> instance, final Object key,
			final Object value) {
		return instance != null && instance.containsEntry(key, value);
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s1,
			final Iterable<Element> nextElementSiblings) {
		//
		Matcher matcher = null;
		//
		Multimap<String, String> multimap = null;
		//
		final String s2 = IterableUtils.size(nextElementSiblings) > 1
				? ElementUtil.text(IterableUtils.get(nextElementSiblings, 1))
				: null;
		//
		String g1, g2;
		//
		String[] ss = null;
		//
		if (Boolean
				.logicalAnd(
						Util.matches(
								matcher = Util.matcher(
										PatternMap.getPattern(patternMap,
												"^(\\p{InCJKUnifiedIdeographs}+)\\s?（\\p{InCJKUnifiedIdeographs}+）$"),
										s1)),
						Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), s2);
			//
		} else if (Boolean.logicalAnd(
				Util.matches(matcher = Util.matcher(
						PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)$"), s1)),
				Util.groupCount(matcher) > 1)
				&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))
				&& StringUtils.endsWith(s2, g2 = Util.group(matcher, 2))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1),
					StringUtils.substring(s2, 0, StringUtils.length(s2) - StringUtils.length(g2)));
			//
		} else if (Boolean.logicalAnd(
				Util.matches(matcher = Util.matcher(
						PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)$"), s1)),
				Util.groupCount(matcher) > 1)
				&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))
				&& StringUtils.startsWith(s2, g1 = Util.group(matcher, 1))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 2), StringUtils.substring(s2, StringUtils.length(g1), StringUtils.length(s2)));
			//
		} else if (Boolean.logicalAnd(
				Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)ノ(\\p{InCJKUnifiedIdeographs}+)$"), s1)),
				Util.groupCount(matcher) > 1) && (ss = StringUtils.split(s2, 'の')) != null && ss.length == 2) {
			//
			for (int j = 0; j < ss.length; j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, j + 1), ss[j]);
				//
			} // for
				//
		} else if (and(
				Util.matches(matcher = Util.matcher(
						PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)([\\p{InKatakana}\\s]+)$"),
						s1)),
				Util.groupCount(matcher) > 1,
				!Util.contains(getUnicodeBlocks(s2), UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS))) {
			//
			final StringBuilder sb = new StringBuilder(s2);
			//
			for (int j = 0; j < StringUtils.length(Util.group(matcher, 2)); j++) {
				//
				sb.deleteCharAt(StringUtils.length(sb) - 1);
				//
			} // for
				//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Objects.toString(sb));
			//
		} else if (and(Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\([\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}]+）$"),
				s1)), Util.groupCount(matcher) > 0,
				Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), s2);
			//
		} // if
			//
		if (!MultimapUtil.isEmpty(multimap)) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(patternMap, s1, s2));
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s1,
			@Nullable final String s2) {
		//
		Matcher matcher = null;
		//
		Multimap<String, String> multimap = null;
		//
		if (Boolean.logicalAnd(Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\([\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}]+）$"),
				s1)), Util.groupCount(matcher) > 0)
				&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), s2);
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String string) {
		//
		Matcher matcher = null;
		//
		Multimap<String, String> multimap = null;
		//
		if (Boolean
				.logicalAnd(Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
						"^\\p{InCJKUnifiedIdeographs}+\\s?（(\\p{InCJKUnifiedIdeographs}+)）\\s?（(\\p{InHiragana}+)）$"),
						string)), Util.groupCount(matcher) > 1)
				|| Boolean.logicalAnd(
						Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
								"^(\\p{InCJKUnifiedIdeographs}+)\\s?（(\\p{InHiragana}+)）$"), string)),
						Util.groupCount(matcher) > 1)
				|| Boolean.logicalAnd(
						Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
								"^.+を(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"), string)),
						Util.groupCount(matcher) > 1)) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Iterable<TextNode> textNodes, final Pattern pattern) {
		//
		Matcher matcher = null;
		//
		String s1 = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(textNodes); i++) {
			//
			if (and(Util.matches(matcher = Util.matcher(pattern, TextNodeUtil.text(IterableUtils.get(textNodes, i)))),
					Util.groupCount(matcher) > 0, i > 0)
					&& Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
							getUnicodeBlocks(s1 = TextNodeUtil.text(IterableUtils.get(textNodes, i - 1))))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
						Util.group(matcher, 1));
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	private static boolean and(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		boolean result = a && b;
		//
		if (!result) {
			//
			return false;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (!(result &= bs[i])) {
				//
				return false;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static List<UnicodeBlock> getUnicodeBlocks(@Nullable final String string) {
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

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}