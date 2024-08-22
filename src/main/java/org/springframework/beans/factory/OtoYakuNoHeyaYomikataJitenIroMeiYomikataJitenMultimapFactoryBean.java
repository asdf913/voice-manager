package org.springframework.beans.factory;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.regex.Matcher;

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
		if (Util.iterator(nodes) != null) {
			//
			String[] ss = null;
			//
			PatternMap patternMap = null;
			//
			for (final Node node : nodes) {
				//
				if (!(node instanceof TextNode) || (ss = StringUtils.split(Util.toString(node), "\u3000")) == null) {
					//
					continue;
					//
				} // if
					//
				for (final String s : ss) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							toMultimap(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
									StringUtils.trim(s)));
					//
				} // for
					//
			} // for
				//
		} // if
			//
		return multimap;
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
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap5);
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
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$");
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
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+");
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
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InBasicLatin}|\\p{InHiragana}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}+$");
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
		Matcher m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(input));
		//
		String g2, g6;
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(m) && Util.groupCount(m) > 5 && Objects.equals(g2 = Util.group(m, 2), Util.group(m, 4))
				&& StringUtils.countMatches(g6 = Util.group(m, 6), g2) == 2) {
			//
			final String[] ss = StringUtils.split(g6, g2);
			//
			String s;
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
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)(の)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$"),
				StringUtils.trim(input))) && Util.groupCount(m) > 5
				&& Objects.equals(g2 = Util.group(m, 2), Util.group(m, 4))
				&& StringUtils.countMatches(g6 = Util.group(m, 6), g2) == 2) {
			//
			final String[] ss = StringUtils.split(g6, g2);
			//
			String s;
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