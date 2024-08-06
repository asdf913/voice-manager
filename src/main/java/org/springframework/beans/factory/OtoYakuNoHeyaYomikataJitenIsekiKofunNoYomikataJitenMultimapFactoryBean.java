package org.springframework.beans.factory;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.TriFunction;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.mariten.kanatools.KanaConverter;

public class OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	@URL("https://hiramatu-hifuka.com/onyak/onyak2/isekindx.html")
	private String url = null;

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
		//
		final List<Element> tables = ElementUtil.select(document, "table");
		//
		Iterable<Element> children;
		//
		Element tbody;
		//
		PatternMap patternMap = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(tables); i++) {
			//
			if (Boolean.logicalOr(!StringUtils.equalsIgnoreCase(
					ElementUtil.tagName(tbody = testAndApply(x -> IterableUtils.size(x) > 0,
							ElementUtil.children(IterableUtils.get(tables, i)), x -> IterableUtils.get(x, 0), null)),
					"tbody"), IterableUtils.size(children = ElementUtil.children(tbody)) <= 0)) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), children));
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
		IValue0<Multimap<String, String>> iValue0 = null;
		//
		List<TriFunction<PatternMap, String, String, IValue0<Multimap<String, String>>>> functions = null;
		//
		for (final Element tr : trs) {
			//
			if (IterableUtils.size(tds = ElementUtil.children(tr)) != 3 || (functions = ObjectUtils.getIfNull(functions,
					() -> Arrays.asList(
							OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBean::toMultimap1,
							OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBean::toMultimap2,
							OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBean::toMultimap3,
							OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBean::toMultimap4))) == null) {
				//
				continue;
				//
			} // if
				//
			for (final TriFunction<PatternMap, String, String, IValue0<Multimap<String, String>>> function : functions) {
				//
				if (function == null) {
					//
					continue;
					//
				} // if
					//
				if ((iValue0 = function.apply(patternMap, ElementUtil.text(IterableUtils.get(tds, 0)),
						ElementUtil.text(IterableUtils.get(tds, 1)))) != null) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							IValue0Util.getValue0(iValue0));
					//
				} // if
					//
			} // for
				//
			if ((iValue0 = toMultimap(patternMap, ElementUtil.text(IterableUtils.get(tds, 2)))) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						IValue0Util.getValue0(iValue0));
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap(final PatternMap patternMap, final String s) {
		//
		Matcher matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+市(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InBasicLatin}|\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}]+$"),
				s);
		//
		if (Util.matches(matcher) && Util.groupCount(matcher) > 1) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(matcher, 1), Util.group(matcher, 2)));
			//
		} else if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\p{InCJKUnifiedIdeographs}|\\p{InBasicLatin}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\((\\p{InHiragana}+)\\)[\\p{InCJKSymbolsAndPunctuation}|\\p{InBasicLatin}]+[\\p{InCJKUnifiedIdeographs}+|\\p{InHiragana}]+子(\\p{InCJKUnifiedIdeographs}+)\\((\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(matcher) > 3) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(matcher, 1), Util.group(matcher, 2),
					Util.group(matcher, 3), Util.group(matcher, 4)));
			//
		} else if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\p{InCJKUnifiedIdeographs}|\\p{InBasicLatin}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
				s)) && Util.groupCount(matcher) > 3) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(matcher, 1), Util.group(matcher, 2),
					Util.group(matcher, 3), Util.group(matcher, 4)));
			//
		} // if
			//
		String[] ss;
		//
		if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\p{InCJKUnifiedIdeographs}|\\p{InBasicLatin}]+\\p{InCJKSymbolsAndPunctuation}+(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+"),
				s)) && Util.groupCount(matcher) > 3
				&& (ss = StringUtils.split(Util.group(matcher, 4), Util.group(matcher, 2))) != null && ss.length == 2) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(matcher, 1), ss[0], Util.group(matcher, 3), ss[1]));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap1(final PatternMap patternMap, final String s1,
			final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		boolean isCJKUnifiedIdeographs, isHiragana;
		//
		String g1;
		//
		Matcher matcher;
		//
		String[] ss = null;
		//
		if (Boolean.logicalAnd(
				isCJKUnifiedIdeographs = Util.matches(
						Util.matcher(PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)$"), s1)),
				isHiragana = Util
						.matches(Util.matcher(PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)$"), s2)))) {
			//
			return Unit.with(ImmutableMultimap.of(s1, s2));
			//
		} else if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKSymbolsAndPunctuation}+(\\p{InCJKUnifiedIdeographs}+)\\p{InCJKSymbolsAndPunctuation}+(\\p{InCJKUnifiedIdeographs}+)$"),
				s1)) && Util.groupCount(matcher) > 1 && isHiragana) {
			//
			return Unit
					.with(ImmutableMultimap.of(StringUtils.join(Util.group(matcher, 1), Util.group(matcher, 2)), s2));
			//
		} else if (Boolean.logicalAnd(isCJKUnifiedIdeographs,
				Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
						"^\\p{InHalfwidthAndFullwidthForms}?(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}?$"),
						s2)) && Util.groupCount(matcher) > 0)) {
			//
			return Unit.with(ImmutableMultimap.of(s1, Util.group(matcher, 1)));
			//
		} else if ((Util
				.matches(matcher = Util.matcher(
						PatternMap.getPattern(patternMap,
								"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)$"),
						s1))
				&& Util.groupCount(matcher) > 2 && isHiragana
				&& (ss = StringUtils.split(s2, Util.group(matcher, 2))) != null && ss.length == 2)
				|| (Util.matches(matcher = Util.matcher(
						PatternMap.getPattern(patternMap,
								"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)(\\p{InCJKUnifiedIdeographs}+)$"),
						s1)) && Util.groupCount(matcher) > 1 && isHiragana
						&& (ss = StringUtils.split(s2, KanaConverter.convertKana(Util.group(matcher, 2),
								KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA))) != null
						&& ss.length == 2)) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), ss[0]);
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 3), ss[1]);
			//
			return Unit.with(multimap);
			//
		} else if (Util
				.matches(matcher = Util.matcher(
						PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)$"), s1))
				&& Util.groupCount(matcher) > 1 && isHiragana
				&& StringUtils.startsWith(s2, g1 = Util.group(matcher, 1))) {
			//
			return Unit.with(
					ImmutableMultimap.of(Util.group(matcher, 2), StringUtils.substring(s2, StringUtils.length(g1))));
			//
		} else if (Util
				.matches(matcher = Util.matcher(
						PatternMap.getPattern(patternMap, "^(\\p{InKatakana}+)(\\p{InCJKUnifiedIdeographs}+)$"), s1))
				&& Util.groupCount(matcher) > 1 && isHiragana && StringUtils.startsWith(s2, KanaConverter
						.convertKana(g1 = Util.group(matcher, 1), KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA))) {
			//
			return Unit.with(
					ImmutableMultimap.of(Util.group(matcher, 2), StringUtils.substring(s2, StringUtils.length(g1))));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap2(final PatternMap patternMap, final String s1,
			final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m1;
		//
		boolean isCJKUnifiedIdeographs;
		//
		if (((isCJKUnifiedIdeographs = Util
				.matches(Util.matcher(PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)$"), s1)))
				&& Util.matches(m1 = Util
						.matcher(PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)\\s+(\\p{InHiragana}+)$"), s2)))
				|| (isCJKUnifiedIdeographs && Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InHiragana}+)[\\s|\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}]+(\\p{InHiragana}+)[\\s|\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}|\\p{InKatakana}]+$"),
						s2)))) {
			//
			for (int i = 1; i <= Util.groupCount(m1); i++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
						Util.group(m1, i));
				//
			} // for
				//
			return Unit.with(multimap);
			//
		} // if
			//
		Matcher m2;
		//
		int groupCount;
		//
		if (isCJKUnifiedIdeographs
				&& Util.matches(m2 = Util.matcher(
						PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)$"), s2))
				&& (groupCount = Util.groupCount(m2)) > 1) {
			//
			if (StringUtils.length(Util.group(m2, 1)) == StringUtils.length(Util.group(m2, 2))) {
				//
				for (int i = 1; i <= groupCount; i++) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
							Util.group(m2, i));
					//
				} // for
					//
			} else {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
						join(m2, 1, groupCount));
				//
			} // if
				//
			return Unit.with(multimap);
			//
		} // if
			//
		return null;
		//
	}

	private static String join(final Matcher instance, final int start, final int end) {
		//
		StringBuilder sb = null;
		//
		for (int i = start; i <= end; i++) {
			//
			if ((sb = ObjectUtils.getIfNull(sb, StringBuilder::new)) != null) {
				//
				sb.append(Util.group(instance, i));
				//
			} // if
				//
		} // for
			//
		return Util.toString(sb);
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap3(final PatternMap patternMap, final String s1,
			final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m1, m2;
		//
		String[] ss = null;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\s（(\\p{InCJKUnifiedIdeographs}+)）$"), s1))
				&& Util.matches(m2 = Util.matcher(
						PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)\\s（(\\p{InHiragana}+)）$"), s2))) {
			//
			for (int i = 1; i <= Math.min(Util.groupCount(m1), Util.groupCount(m2)); i++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m1, i), Util.group(m2, i));
				//
			} // for
				//
			return Unit.with(multimap);
			//
		} else if (Util
				.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)ヶ(\\p{InCJKUnifiedIdeographs}+)$"), s1))
				&& Util.groupCount(m1) > 1
				&& Util.matches(Util.matcher(PatternMap.getPattern(patternMap, "^\\p{InHiragana}+$"), s2))
				&& (ss = StringUtils.split(s2, 'が')) != null && ss.length == 2) {
			//
			for (int i = 0; i < ss.length; i++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m1, i + 1), ss[i]);
				//
			} // for
				//
			return Unit.with(multimap);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap4(final PatternMap patternMap, final String s1,
			final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m1;
		//
		String[] ss = null;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InCJKUnifiedIdeographs}+)）(\\p{InKatakana}+)(\\p{InCJKUnifiedIdeographs}+)$"),
				s1)) && Util.groupCount(m1) > 3
				&& (ss = StringUtils.split(s2,
						KanaConverter.convertKana(Util.group(m1, 3), KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA))) != null) {
			//
			for (int i = 0; i < ss.length; i++) {
				//
				if (i == 0) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, 1), ss[i]);
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, 2), ss[i]);
					//
				} else {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, 4), ss[i]);
					//
				} // if
					//
			} // for
				//
			return Unit.with(multimap);
			//
		} // if
			//
		Matcher m2;
		//
		if (Util.matches(
				m1 = Util.matcher(
						PatternMap.getPattern(patternMap,
								"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InBasicLatin}+)(\\p{InCJKUnifiedIdeographs}+)$"),
						s1))
				&& Util.matches(m2 = Util.matcher(
						PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)(\\p{InBasicLatin}+)(\\p{InHiragana}+)$"),
						s2))) {
			//
			String a, b;
			//
			for (int i = 1; i <= Math.min(Util.groupCount(m1), Util.groupCount(m2)); i++) {
				//
				if (Boolean.logicalAnd(
						Util.matches(Util.matcher(PatternMap.getPattern(patternMap, "^\\p{InCJKUnifiedIdeographs}+$"),
								a = Util.group(m1, i))),
						Util.matches(Util.matcher(PatternMap.getPattern(patternMap, "^\\p{InHiragana}+$"),
								b = Util.group(m2, i))))) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), a, b);
					//
				} // if
					//
			} // for
				//
			return Unit.with(multimap);
			//
		} // if
			//
		return null;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
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