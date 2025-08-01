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
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.function.TriFunctionUtil;
import org.apache.commons.validator.routines.IntegerValidator;
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
				Util.add(urls = ObjectUtils.getIfNull(urls, ArrayList::new), NodeUtil.absUrl(e, "href"));
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
		Multimap<String, String> multimap = null, excluded = null;
		//
		int size;
		//
		Matcher matcher = null;
		//
		Iterable<Element> nextElementSiblings = null;
		//
		for (int i = 0; i < IterableUtils.size(es); i++) {
			//
			if ((e = IterableUtils.get(es, i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (Util.matches(matcher = Util.matcher(
					PatternMap.getPattern(patternMap,
							"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)（\\p{InCJKUnifiedIdeographs}+）$"),
					s1 = ElementUtil.text(e))) && Util.groupCount(matcher) > 1
					&& IterableUtils.size(nextElementSiblings = ElementUtil.nextElementSiblings(e)) > 1
					&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA),
							getUnicodeBlocks(s2 = ElementUtil.text(IterableUtils.get(nextElementSiblings, 1))))
					&& StringsUtil.endsWith(Strings.CS, s2, Util.group(matcher, 2))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 1), StringUtils.substring(s2, 0,
								StringUtils.length(s2) - StringUtils.length(Util.group(matcher, 2))));
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
					toMultimap(patternMap, e));
			//
			if (MultimapUtil.size(multimap) > size) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(patternMap, s1, ElementUtil.nextElementSiblings(e)));
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
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, @Nullable final Element element) {
		//
		final Matcher matcher = Util.matcher(
				PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\s+（(\\p{InCJKUnifiedIdeographs}+).+）$"),
				ElementUtil.text(element));
		//
		String s2, group;
		//
		final Iterable<Element> nextElementSiblings = ElementUtil.nextElementSiblings(element);
		//
		int rowspan;
		//
		Multimap<String, String> multimap = null;
		//
		Iterable<Element> children = null;
		//
		if (Boolean.logicalAnd(Util.matches(matcher), IterableUtils.size(nextElementSiblings) > 1)
				&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA),
						getUnicodeBlocks(s2 = ElementUtil.text(IterableUtils.get(nextElementSiblings, 1))))
				&& (rowspan = Util.intValue(validate(IntegerValidator.getInstance(),
						testAndApply(NodeUtil::hasAttr, element, "rowspan", NodeUtil::attr, null)), 0)) > 1) {
			//
			for (int j = 0; j < Math.min(Util.groupCount(matcher), rowspan); j++) {
				//
				group = Util.group(matcher, j + 1);
				//
				if (j == 0) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), group, s2);
					//
				} else {
					//
					if (element != null && !IterableUtils.isEmpty(children = ElementUtil
							.children(ElementUtil.nextElementSibling(ElementUtil.parent(element))))) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), group,
								ElementUtil.text(IterableUtils.get(children, element.elementSiblingIndex() + 1)));
						//
					} // if
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
	private static <T, U, R> R testAndApply(@Nullable final BiPredicate<T, U> predicate, @Nullable final T t, final U u,
			final BiFunction<T, U, R> functionTrue, @Nullable final BiFunction<T, U, R> functionFalse) {
		return Util.test(predicate, t, u) ? Util.apply(functionTrue, t, u) : Util.apply(functionFalse, t, u);
	}

	@Nullable
	private static Integer validate(@Nullable final IntegerValidator instance, @Nullable final String value) {
		return instance != null ? instance.validate(value) : null;
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
			if (MultimapUtil.containsEntry(excluded, k = Util.group(matcher, 2), v = Util.group(matcher, 3))) {
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

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s1,
			final Iterable<Element> nextElementSiblings) {
		//
		Matcher matcher;
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
		final Strings strings = Strings.CS;
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
				&& StringsUtil.endsWith(strings, s2, g2 = Util.group(matcher, 2))) {
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
				&& StringsUtil.startsWith(strings, s2, g1 = Util.group(matcher, 1))) {
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
		} // if
			//
		final List<TriFunction<PatternMap, String, String, Multimap<String, String>>> functions = Arrays.asList(
				OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean::toMultimap1,
				OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean::toMultimap2);
		//
		for (int i = 0; i < IterableUtils.size(functions); i++) {
			//
			if (!MultimapUtil.isEmpty(multimap)) {
				//
				break;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					TriFunctionUtil.apply(IterableUtils.get(functions, i), patternMap, s1, s2));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap1(final PatternMap patternMap, final String s1,
			@Nullable final String s2) {
		//
		Matcher m1, m2;
		//
		Multimap<String, String> multimap = null;
		//
		String[] ss = null;
		//
		if (Boolean.logicalAnd(Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\([\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}]+）$"),
				s1)), Util.groupCount(m1) > 0)
				&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.group(m1, 1),
					s2);
			//
		} else if (Util.and(
				Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)の(\\p{InCJKUnifiedIdeographs}+)$"), s1)),
				Util.groupCount(m1) > 0,
				Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2)),
				StringUtils.countMatches(s2, 'の') == 1) && (ss = StringUtils.split(s2, 'の')) != null) {
			//
			for (int j = 0; j < Math.min(ss.length, Util.groupCount(m1)); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m1, j + 1), ss[j]);
				//
			} // for
				//
		} else if (Util
				.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\s+(\\p{InCJKUnifiedIdeographs}+)$"), s1))
				&& Util.matches(m2 = Util.matcher(
						PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)\\s+(\\p{InHiragana}+)$"), s2))) {
			//
			for (int j = 1; j <= Math.min(Util.groupCount(m1), Util.groupCount(m2)); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m1, j), Util.group(m2, j));
				//
			} // for
				//
		} else if (Boolean.logicalAnd(
				Util.matches(
						m1 = Util.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InCJKUnifiedIdeographs}+)）$"), s1)),
				Util.groupCount(m1) > 0)
				&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.group(m1, 1),
					s2);
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap2(final PatternMap patternMap, final String s1, final String s2) {
		//
		Matcher matcher;
		//
		Multimap<String, String> multimap = null;
		//
		if (Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), getUnicodeBlocks(s1))) {
			//
			if (Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))) {
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
		} else if (Util.and(Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\([\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}]+）$"),
				s1)), Util.groupCount(matcher) > 0,
				Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), s2);
			//
		} else if (Util.and(
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
			if (Util.and(
					Util.matches(matcher = Util.matcher(pattern, TextNodeUtil.text(IterableUtils.get(textNodes, i)))),
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