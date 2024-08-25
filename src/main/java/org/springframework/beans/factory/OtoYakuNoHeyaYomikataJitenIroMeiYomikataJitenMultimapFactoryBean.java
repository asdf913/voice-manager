package org.springframework.beans.factory;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	@URL("https://hiramatu-hifuka.com/onyak/sikime01.html")
	private String url = null;

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final Iterable<Node> nodes = Util.toList(NodeUtil.nodeStream(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null)));
		//
		Multimap<String, String> multimap = null;
		//
		final List<String> list = Util
				.toList(Util.map(
						Util.filter(testAndApply(Objects::nonNull, Util.spliterator(nodes),
								x -> StreamSupport.stream(x, false), null), TextNode.class::isInstance),
						Util::toString));
		//
		String[] ss = null;
		//
		PatternMap patternMap = null;
		//
		String s;
		//
		IValue0<Multimap<String, String>> iValue0;
		//
		for (int i = 0; list != null && i < list.size(); i++) {
			//
			if ((ss = StringUtils.split(list.get(i), "\u3000")) == null) {
				//
				continue;
				//
			} // if
				//
			for (int j = 0; j < ss.length; j++) {
				//
				s = ss[j];
				//
				if (j < ss.length - 1
						&& (iValue0 = toMultimap(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), s,
								ss[j + 1])) != null) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							IValue0Util.getValue0(iValue0));
					//
					j++;
					//
					continue;
					//
				} // if
					//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), toMultimap(
						patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), StringUtils.trim(s)));
				//
			} // for
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap(final PatternMap patternMap, final String s1,
			final String s2) {
		//
		Matcher m1 = Util.matcher(
				PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)"),
				StringUtils.trim(s1));
		//
		Matcher m2 = Util.matcher(
				PatternMap.getPattern(patternMap,
						"^(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$"),
				StringUtils.trim(s2));
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 1 && Util.matches(m2) && Util.groupCount(m2) > 0) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m1, 1), Util.group(m1, 2) + Util.group(m2, 1)));
			//
		} // if
			//
		String g4, g2;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}{2,})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}"),
				StringUtils.trim(s1))) && Util.groupCount(m1) > 3
				&& StringUtils.countMatches(g4 = Util.group(m1, 4), g2 = Util.group(m1, 2)) == 1
				&& Util.matches(Util.matcher(
						PatternMap.getPattern(patternMap,
								"^\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}$"),
						StringUtils.trim(s2)))) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m1, 1), StringUtils.substringBefore(g4, g2),
					Util.group(m1, 3), StringUtils.substringAfter(g4, g2)));
			//
		} else if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$"),
				StringUtils.trim(s1)))
				&& Util.groupCount(m1) > 1
				&& Util.matches(Util.matcher(
						PatternMap.getPattern(patternMap,
								"^\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}$"),
						StringUtils.trim(s2)))) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m1, 1), Util.group(m1, 2)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s) {
		//
		final List<BiFunction<PatternMap, String, IValue0<Multimap<String, String>>>> functions = Arrays.asList(
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap1,
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap2,
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap3,
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap4,
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap5,
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap6,
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap7,
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap8);
		//
		IValue0<Multimap<String, String>> iValue0 = null;
		//
		for (int i = 0; i < IterableUtils.size(functions); i++) {
			//
			if ((iValue0 = Util.apply(IterableUtils.get(functions, i), patternMap, s)) != null) {
				//
				return IValue0Util.getValue0(iValue0);
				//
			} // if
				//
		} // for
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap1(final PatternMap patternMap, final String s) {
		//
		Iterable<String> patterns = Arrays.asList(
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InBasicLatin}+",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InKatakana}+",
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}|\\p{InKatakana}]+",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InBasicLatin}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InKatakana}|\\p{InCJKSymbolsAndPunctuation}|\\p{InHiragana}]+$",
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\d\\p{InHiragana}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}+\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\((\\p{InHiragana}+)\\)$");
		//
		Matcher m = null;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(s)))
					&& Util.groupCount(m) > 1) {
				//
				return Unit.with(ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 2)));
				//
			} // if
				//
		} // for
			//
		patterns = Arrays.asList(
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\p{InBasicLatin}(\\p{InHiragana}+)\\p{InKatakana}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InBasicLatin}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InBasicLatin}(\\p{InHiragana}+)\\p{InKatakana}\\p{InBasicLatin}+\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{Inkatakana}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}$");
		//
		String g1;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(s)))
					&& Util.groupCount(m) > 2) {
				//
				return Unit.with(ImmutableMultimap.of(g1 = Util.group(m, 1), Util.group(m, 2), g1, Util.group(m, 3)));
				//
			} // if
				//
		} // for
			//
		patterns = Arrays.asList(
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$");
		//
		String g3;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(s)))
					&& Util.groupCount(m) > 2) {
				//
				return Unit.with(ImmutableMultimap.of(Util.group(m, 1), g3 = Util.group(m, 3), Util.group(m, 2), g3));
				//
			} // if
				//
		} // for
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap2(final PatternMap patternMap, final String s) {
		//
		Iterable<String> patterns = Arrays.asList(
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$",
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+",
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}]+");
		//
		Matcher m;
		//
		int groupCount;
		//
		String groupLast = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(s)))) {
				//
				groupLast = Util.group(m, groupCount = Util.groupCount(m));
				//
				for (int i = 1; i < groupCount; i++) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m, i), groupLast);
					//
				} // for
					//
				return Unit.with(multimap);
				//
			} // if
				//
		} // for
			//
		patterns = Arrays.asList(
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InCJKSymbolsAndPunctuation}|\\p{InHiragana}]+$",
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}|\\p{InHalfwidthAndFullwidthForms}]+$");
		//
		String g4, g2;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(s)))
					&& Util.groupCount(m) > 3
					&& StringUtils.countMatches(g4 = Util.group(m, 4), g2 = Util.group(m, 2)) == 1) {
				//
				return Unit.with(ImmutableMultimap.of(Util.group(m, 1), StringUtils.substringBefore(g4, g2),
						Util.group(m, 3), StringUtils.substringAfter(g4, g2)));
				//
			} // if
				//
		} // for
			//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(s))) && Util.groupCount(m) > 3) {
			//
			return Unit
					.with(ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 3), Util.group(m, 2), Util.group(m, 4)));
			//
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$"),
				StringUtils.trim(s))) && Util.groupCount(m) > 3) {
			//
			return Unit
					.with(ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 2), Util.group(m, 3), Util.group(m, 4)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap3(final PatternMap patternMap, final String s) {
		//
		final Iterable<String> patterns = Arrays.asList(
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}|\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}]+\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\p{InBasicLatin}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}|\\p{InHalfwidthAndFullwidthForms}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}|\\p{InHalfwidthAndFullwidthForms}]+",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InBasicLatin}]+",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}|\\p{InCJKSymbolsAndPunctuation}|\\p{InHiragana}|\\p{InKatakana}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\p{InHalfwidthAndFullwidthForms}+(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}+",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InKatakana}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}|\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}]+(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InKatakana}|\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}\\p{InEnclosedAlphanumerics}(\\p{InHiragana}+)\\p{InEnclosedAlphanumerics}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}|\\p{InCJKUnifiedIdeographs}|\\p{InCJKSymbolsAndPunctuation}|\\p{InHiragana}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}|\\p{InHalfwidthAndFullwidthForms}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InBasicLatin}|\\p{InHiragana}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}|\\p{InBasicLatin}|\\p{InHalfwidthAndFullwidthForms}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}]+(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}]+");
		//
		Matcher m;
		//
		List<String> list = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(s)))) {
				//
				for (int i = 2; i <= Util.groupCount(m); i++) {
					//
					Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), Util.group(m, i));
					//
				} // for
					//
				MultimapUtil.putAll(multimap = LinkedHashMultimap.create(), Util.group(m, 1), list);
				//
				return Unit.with(multimap);
				//
			} // if
				//
		} // for
			//
		String g6, g4;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InKatakana}]+(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHalfwidthAndFullwidthForms}]+$"),
				StringUtils.trim(s))) && Util.groupCount(m) > 5
				&& StringUtils.countMatches(g6 = Util.group(m, 6), g4 = Util.group(m, 4)) == 1) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 2), Util.group(m, 3),
					StringUtils.substringBefore(g6, g4), Util.group(m, 5), StringUtils.substringAfter(g6, g4)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap4(final PatternMap patternMap, final String input) {
		//
		final Matcher m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)\\（(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(input));
		//
		Object k, v;
		//
		String g4, g2;
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(m) && Util.groupCount(m) > 3
				&& StringUtils.countMatches(g4 = Util.group(m, 4), g2 = Util.group(m, 2)) == 1) {
			//
			final Iterable<Entry<String, String>> unwantedEntries = MultimapUtil
					.entries(ImmutableMultimap.of("根摺", "ねずり", "葡萄", "えび", "襲", "かさねめ"));
			//
			if ((multimap = ImmutableMultimap.of(Util.group(m, 1), StringUtils.substringBefore(g4, g2),
					Util.group(m, 3), StringUtils.substringAfter(g4, g2))) != null
					&& Util.iterator(unwantedEntries) != null) {
				//
				for (final Entry<?, ?> entry : unwantedEntries) {
					//
					if (MultimapUtil.containsEntry(multimap, k = Util.getKey(entry), v = Util.getValue(entry))
							&& (multimap = LinkedHashMultimap.create(multimap)) != null) {
						//
						multimap.remove(k, v);
						//
					} // if
						//
				} // for
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

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap5(final PatternMap patternMap, final String input) {
		//
		final Iterable<String> patterns = Arrays.asList(
				"^(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$");
		//
		Matcher m;
		//
		String g2, g6, s;
		//
		String[] ss;
		//
		Multimap<String, String> multimap = null;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(input)))
					&& Util.groupCount(m) > 5 && Objects.equals(g2 = Util.group(m, 2), Util.group(m, 4))
					&& StringUtils.countMatches(g6 = Util.group(m, 6), g2) == 2) {
				//
				ss = StringUtils.split(g6, g2);
				//
				for (int i = 0; i < Util.length(ss); i++) {
					//
					s = ArrayUtils.get(ss, i);
					//
					if (i == 0) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								Util.group(m, 1), s);
						//
					} else if (i == 1) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								Util.group(m, 3), s);
						//
					} else if (i == 2) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								Util.group(m, 5), s);
						//
					} // if
						//
				} // for
					//
				return Unit.with(multimap);
				//
			} // if
				//
		} // for
			//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InBasicLatin}|\\p{InKatakana}]+(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InHiragana}|\\p{InBasicLatin}]+$"),
				StringUtils.trim(input))) && Util.groupCount(m) > 4) {
			//
			final String g3 = Util.group(m, 3);
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 1), g3, Util.group(m, 2), g3, Util.group(m, 4),
					Util.group(m, 5)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap6(final PatternMap patternMap, final String input) {
		//
		final Iterable<String> patterns = Arrays.asList(
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)([きれきけ]+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}{2,})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}{2,})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$",
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}{2,})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InKatakana}+[\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}]+$");
		//
		Matcher m;
		//
		String g4, g2;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(input)))
					&& Util.groupCount(m) > 3
					&& StringUtils.countMatches(g4 = Util.group(m, 4), g2 = Util.group(m, 2)) == 1) {
				//
				return Unit.with(ImmutableMultimap.of(Util.group(m, 3), StringUtils.substringAfter(g4, g2)));
				//
			} // if
				//
		} // for
			//
		String g1, g3;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)(つ)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(input))) && Util.groupCount(m) > 3
				&& StringUtils.countMatches(g4 = Util.group(m, 4), g2 = Util.group(m, 2)) == 1) {
			//
			g3 = Util.group(m, 3);
			//
			if (Objects.equals(g1 = Util.group(m, 1), "二")) {
				//
				return Unit.with(ImmutableMultimap.of(g1, StringUtils.substringBefore(g4, g2), g3,
						StringUtils.substringAfter(g4, g2)));
				//
			} else {
				//
				return Unit.with(ImmutableMultimap.of(g3, StringUtils.substringAfter(g4, g2)));
				//
			} // if
				//
		} // if
			//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)([きじ])(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$"),
				StringUtils.trim(input))) && Util.groupCount(m) > 3
				&& StringUtils.countMatches(g4 = Util.group(m, 4), g2 = Util.group(m, 2)) == 1) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 1), StringUtils.substringBefore(g4, g2),
					Util.group(m, 3), StringUtils.substringAfter(g4, g2)));
			//
		} // if
			//
		Iterable<String> repeatedStrings = null;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs})(々)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(input))) && Util.groupCount(m) > 3
				&& IterableUtils.size(repeatedStrings = getRepeatedStrings(g4 = Util.group(m, 4))) == 1) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 3),
					StringUtils.substringAfterLast(g4, IterableUtils.get(repeatedStrings, 0))));
			//
		} // if
			//
		String g5;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)(\\p{InCJKSymbolsAndPunctuation})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(input)))
				&& Util.groupCount(m) > 4 && Objects.equals(Util.group(m, 3), "々")
				&& StringUtils.startsWith(Util.group(m, 1),
						StringUtils.repeat(Util.group(m, 2), StringUtils.length(Util.group(m, 2))))
				&& IterableUtils.size(repeatedStrings = getRepeatedStrings(g5 = Util.group(m, 5))) == 1) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 4),
					StringUtils.substringAfterLast(g5, IterableUtils.get(repeatedStrings, 0))));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap7(final PatternMap patternMap, final String input) {
		//
		Matcher m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InBasicLatin}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+\\p{InCJKSymbolsAndPunctuation}(\\p{InHiragana}+)\\p{InCJKSymbolsAndPunctuation}[\\p{InCJKSymbolsAndPunctuation}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+"),
				StringUtils.trim(input));
		//
		String g1, g2, g3;
		//
		if (Util.matches(m) && Util.groupCount(m) > 2 && StringUtils.length(g1 = Util.group(m, 1)) == 2
				&& StringUtils.startsWith(g2 = Util.group(m, 2), g3 = Util.group(m, 3))) {
			//
			return Unit.with(ImmutableMultimap.of(g1, g2, StringUtils.substring(g1, 0, 1), g3,
					StringUtils.substring(g1, 1), StringUtils.substringAfter(g2, g3)));
			//
		} // if
			//
		String g4;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}+[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$"),
				StringUtils.trim(input))) && Util.groupCount(m) > 2
				&& StringUtils.countMatches(g4 = Util.group(m, 4), g2 = Util.group(m, 2)) > 0
				&& StringUtils.endsWith(g4, g2)) {
			//
			for (int i = StringUtils.length(g4) - 1 - StringUtils.length(g2); i >= 0; i--) {
				//
				if (!StringUtils.equals(StringUtils.substring(g4, i - 1, i), g2)) {
					//
					continue;
					//
				} // if
					//
				return Unit.with(
						ImmutableMultimap.of(Util.group(m, 1), StringUtils.substring(g4, 0, i - StringUtils.length(g2)),
								Util.group(m, 3), StringUtils.substring(g4, i)));
				//
			} // for
				//
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+(\\p{InHiragana}{2,})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}\\p{InKatakana}]+$"),
				StringUtils.trim(input))) && Util.groupCount(m) > 2
				&& StringUtils.countMatches(g3 = Util.group(m, 3), g1 = Util.group(m, 1)) == 1) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 2), StringUtils.substringAfter(g3, g1)));
			//
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}{2,})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+$"),
				StringUtils.trim(input))) && Util.groupCount(m) > 2
				&& StringUtils.endsWith(g3 = Util.group(m, 3), g2 = Util.group(m, 2))) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 1), StringUtils.substringBefore(g3, g2)));
			//
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InBasicLatin}\\p{InHalfwidthAndFullwidthForms}]+"),
				StringUtils.trim(input))) && Util.groupCount(m) > 2
				&& StringUtils.startsWith(g3 = Util.group(m, 3), g1 = Util.group(m, 1))) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 2), StringUtils.substringAfter(g3, g1)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap8(final PatternMap patternMap, final String input) {
		//
		final Matcher m = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+$"),
				StringUtils.trim(input));
		//
		String g4, g2;
		//
		if (Util.matches(m) && Util.groupCount(m) > 3
				&& StringUtils.countMatches(g4 = Util.group(m, 4), g2 = Util.group(m, 2)) == 1) {
			//
			final String g3 = Util.group(m, 3);
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(Util.group(m, 1),
					StringUtils.substringBefore(g4, g2), g3, StringUtils.substringAfter(g4, g2)));
			//
			final int length = StringUtils.length(g3);
			//
			if (length == 2 && Objects.equals(StringUtils.substring(g3, 0, 1), StringUtils.substring(g3, 1, length))) {
				//
				MultimapUtil.put(multimap, StringUtils.substring(g3, 0, 1), testAndApply(
						x -> IterableUtils.size(x) == 1, getRepeatedStrings(g4), x -> IterableUtils.get(x, 0), null));
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

	// https://gist.github.com/EasyG0ing1/f6e1d2c18d41850f80fa42bad469c9eb
	@Nullable
	private static Iterable<String> getRepeatedStrings(final String userString) {
		//
		final int size = StringUtils.length(userString);
		//
		final String[] coreString = new String[size];
		//
		for (int x = 0; x < size; x++) {
			//
			coreString[x] = userString.substring(x, x + 1);
			//
		} // for
			//
		List<String> patterns = null;
		//
		String buildString;
		//
		for (int index = 0; index < size - 1; index++) {
			//
			buildString = coreString[index];
			//
			for (int x = index + 1; x < size; x++) {
				//
				Util.add(patterns = ObjectUtils.getIfNull(patterns, ArrayList::new),
						StringUtils.join(buildString, coreString[x]));
				//
			} // for
				//
		} // for
			//
		Map<String, Integer> hitCountMap = null;
		//
		if (Util.iterator(patterns) != null) {
			//
			for (final String pattern : patterns) {
				//
				if (StringUtils.contains(userString.replaceFirst(pattern, ""), pattern)) {
					//
					Util.put(hitCountMap = ObjectUtils.getIfNull(hitCountMap, LinkedHashMap::new), pattern,
							(size - userString.replaceAll(pattern, "").length()) / testAndApplyAsInt(x -> x == 0,
									StringUtils.length(pattern), x -> 1, IntUnaryOperator.identity(), 1));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return hitCountMap != null ? hitCountMap.keySet() : null;
		//
	}

	private static int testAndApplyAsInt(@Nullable final IntPredicate predicate, final int value,
			final IntUnaryOperator t, final IntUnaryOperator f, final int defaultValue) {
		return predicate != null && predicate.test(value) ? Util.applyAsInt(t, value, defaultValue)
				: Util.applyAsInt(f, value, defaultValue);
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