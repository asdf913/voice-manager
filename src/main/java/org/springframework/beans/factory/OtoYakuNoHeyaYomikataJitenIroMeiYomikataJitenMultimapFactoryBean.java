package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.util.IntList;
import org.apache.poi.util.IntListUtil;
import org.d2ab.function.ObjObjIntFunction;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriPredicate;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.mariten.kanatools.KanaConverter;

public class OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private static final Pattern PATTERN_CJK_UNIFIED_IDEOGRAPHS_ONLY = Pattern
			.compile("^\\p{InCJKUnifiedIdeographs}+$");

	private static final Pattern PATTERN_MULTIPLE_CJK_UNIFIED_IDEOGRAPHS_AND_ENDS_WITH_HALFWIDTH_AND_FULL_WIDTH_FORMS = Pattern
			.compile("^\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}$");

	private static final Pattern PATTERN_MULTIPLE_CJK_UNIFIED_IDEOGRAPHS_AND_ENDS_WITH_HIRAGANA = Pattern
			.compile("^\\p{InCJKUnifiedIdeographs}+\\p{InHiragana}$");

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
				.toList(flatMap(
						Util.map(
								Util.map(
										Util.map(Util.filter(
												testAndApply(Objects::nonNull, Util.spliterator(nodes),
														x -> StreamSupport.stream(x, false), null),
												TextNode.class::isInstance), Util::toString),
										x -> StringUtils.split(x, "\u3000")),
								Arrays::asList),
						List::stream));
		//
		PatternMap patternMap = null;
		//
		String s;
		//
		IValue0<Multimap<String, String>> iValue0;
		//
		IntList intList = null;
		//
		Entry<Multimap<String, String>, IntList> entry;
		//
		for (int i = 0; i < IterableUtils.size(list); i++) {
			//
			if (contains(intList = ObjectUtils.getIfNull(intList, IntList::new), i)) {
				//
				removeValue(intList, i);
				//
				continue;
				//
			} // if
				//
			if ((entry = toMultimapAndIntList(patternMap, list, i)) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.getKey(entry));
				//
				addAll(intList = ObjectUtils.getIfNull(intList, IntList::new), Util.getValue(entry));
				//
				continue;
				//
			} // if
				//
			s = IterableUtils.get(list, i);
			//
			if (i < IterableUtils.size(list) - 1
					&& (iValue0 = toMultimap(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), s,
							IterableUtils.get(list, i + 1))) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						IValue0Util.getValue0(iValue0));
				//
				IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), i + 1);
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
		return multimap;
		//
	}

	private static void addAll(@Nullable final IntList a, @Nullable final IntList b) {
		if (a != null && b != null) {
			a.addAll(b);
		}
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList(@Nullable final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		final List<ObjObjIntFunction<PatternMap, List<String>, Entry<Multimap<String, String>, IntList>>> functions = Arrays
				.asList(OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList1,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList2,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList3,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList4,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList5,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList6,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList7,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList8);
		//
		Entry<Multimap<String, String>, IntList> entry = null;
		//
		for (int j = 0; j < IterableUtils.size(functions); j++) {
			//
			if ((entry = apply(IterableUtils.get(functions, j), patternMap, list, i)) != null) {
				//
				return entry;
				//
			} // if
				//
		} // for
			//
		return null;
		//
	}

	@Nullable
	private static <T, U, R> R apply(@Nullable final ObjObjIntFunction<T, U, R> instance, @Nullable final T t,
			final U u, final int i) {
		return instance != null ? instance.apply(t, u, i) : null;
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList1(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		Matcher m1;
		//
		final String s = testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null);
		//
		String g4, g2;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)(つ)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(s))) && Util.groupCount(m1) > 3
				&& StringUtils.countMatches(g4 = Util.group(m1, 4), g2 = Util.group(m1, 2)) == 1
				&& i < IterableUtils.size(list) - 2
				&& Util.matches(Util.matcher(PATTERN_MULTIPLE_CJK_UNIFIED_IDEOGRAPHS_AND_ENDS_WITH_HIRAGANA,
						IterableUtils.get(list, i + 1)))
				&& Util.matches(Util.matcher(PATTERN_CJK_UNIFIED_IDEOGRAPHS_ONLY, IterableUtils.get(list, i + 2)))) {
			//
			return Pair.of(ImmutableMultimap.of(Util.group(m1, 3), StringUtils.substringAfter(g4, g2)),
					toIntList(i, IntStream.rangeClosed(0, 2)));
			//
		} else if (i < IterableUtils.size(list) - 2
				&& Util.matches(Util.matcher(PATTERN_MULTIPLE_CJK_UNIFIED_IDEOGRAPHS_AND_ENDS_WITH_HIRAGANA,
						IterableUtils.get(list, i + 1)))
				&& ((Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
						"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
						StringUtils.trim(s)))
						&& Util.groupCount(m1) > 1
						&& Util.matches(Util.matcher(
								PATTERN_MULTIPLE_CJK_UNIFIED_IDEOGRAPHS_AND_ENDS_WITH_HALFWIDTH_AND_FULL_WIDTH_FORMS,
								IterableUtils.get(list, i + 2))))
						|| (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
								"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
								StringUtils.trim(s))) && Util.groupCount(m1) > 1
								&& Util.matches(Util.matcher(PATTERN_CJK_UNIFIED_IDEOGRAPHS_ONLY,
										IterableUtils.get(list, i + 2)))))) {
			//
			return Pair.of(ImmutableMultimap.of(Util.group(m1, 1), Util.group(m1, 2)),
					toIntList(i, IntStream.rangeClosed(0, 2)));
			//
		} // if
			//
		final Iterable<String> patterns = Arrays.asList(
				"^\\p{InCJKUnifiedIdeographs}+(\\p{InHiragana}{2,})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^\\p{InCJKUnifiedIdeographs}(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$");
		//
		String g3, g1;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(s)))
					&& Util.groupCount(m1) > 2
					&& StringUtils.countMatches(g3 = Util.group(m1, 3), g1 = Util.group(m1, 1)) == 1
					&& i < IterableUtils.size(list) - 2
					&& Util.matches(Util.matcher(PATTERN_MULTIPLE_CJK_UNIFIED_IDEOGRAPHS_AND_ENDS_WITH_HIRAGANA,
							IterableUtils.get(list, i + 1)))
					&& Util.matches(
							Util.matcher(PATTERN_CJK_UNIFIED_IDEOGRAPHS_ONLY, IterableUtils.get(list, i + 2)))) {
				//
				return Pair.of(ImmutableMultimap.of(Util.group(m1, 2), StringUtils.substringAfter(g3, g1)),
						toIntList(i, IntStream.rangeClosed(0, 2)));
				//
			} // if
				//
		} // for
			//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				s)) && Util.groupCount(m1) > 3
				&& StringUtils.countMatches(g4 = Util.group(m1, 4), g2 = Util.group(m1, 2)) == 1
				&& i < IterableUtils.size(list) - 2
				&& Util.matches(Util.matcher(PATTERN_MULTIPLE_CJK_UNIFIED_IDEOGRAPHS_AND_ENDS_WITH_HIRAGANA,
						IterableUtils.get(list, i + 1)))
				&& Util.matches(Util.matcher(
						PATTERN_MULTIPLE_CJK_UNIFIED_IDEOGRAPHS_AND_ENDS_WITH_HALFWIDTH_AND_FULL_WIDTH_FORMS,
						IterableUtils.get(list, i + 2)))) {
			//
			return Pair.of(ImmutableMultimap.of(Util.group(m1, 1), StringUtils.substringBefore(g4, g2),
					Util.group(m1, 3), StringUtils.substringAfter(g4, g2)), toIntList(i, IntStream.rangeClosed(0, 2)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList2(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		final String s = testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null);
		//
		Matcher m1, m2;
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}]+"),
				StringUtils.trim(s)))
				&& Util.groupCount(m1) > 0 && i < IterableUtils.size(list) - 1
				&& Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
						"^\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}]+$"),
						IterableUtils.get(list, i + 1)))) {
			//
			final String kanji = Util.group(m1, 1);
			//
			for (int j = 2; j <= Util.groupCount(m1); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), kanji,
						Util.group(m1, j));
				//
			} // for
				//
			for (int j = 1; j <= Util.groupCount(m2); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), kanji,
						Util.group(m2, j));
				//
			} // for
				//
			return Pair.of(multimap, toIntList(i, IntStream.rangeClosed(0, 1)));
			//
		} // if
			//
		Matcher m3;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}]+$"),
				s))
				&& Util.groupCount(m1) > 1 && i < IterableUtils.size(list) - 2
				&& Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InBasicLatin}]+$"),
						IterableUtils.get(list, i + 1)))
				&& Util.groupCount(m2) > 1
				&& Util.matches(m3 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InBasicLatin}]+$"),
						IterableUtils.get(list, i + 2)))
				&& Util.groupCount(m3) > 1) {
			//
			return toMultimapAndIntList(i, m1, m2, m3);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList3(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		final String s = testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null);
		//
		Matcher m1, m2;
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}]+"),
				s))
				&& Util.groupCount(m1) > 1 && i < IterableUtils.size(list) - 3
				&& Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
						"^\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}]+$"),
						IterableUtils.get(list, i + 1)))
				&& Util.groupCount(m2) > 0
				&& Util.matches(Util.matcher(PatternMap.getPattern(patternMap, "^\\p{InHiragana}+$"),
						IterableUtils.get(list, i + 2)))
				&& Util.matches(Util.matcher(
						PatternMap.getPattern(patternMap,
								"^[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+$"),
						IterableUtils.get(list, i + 3)))) {
			//
			final int groupCount = Util.groupCount(m1);
			//
			String hiragana = Util.group(m1, groupCount);
			//
			for (int j = 1; j < groupCount; j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m1, j), hiragana);
				//
			} // for
				//
			final Iterable<String> keySet = MultimapUtil
					.keySet(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create));
			//
			if (Util.iterator(keySet) != null) {
				//
				hiragana = Util.group(m2, 1);
				//
				for (final String key : keySet) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), key,
							hiragana);
					//
				} // for
					//
			} // if
				//
			return Pair.of(multimap, toIntList(i, IntStream.rangeClosed(1, 3)));
			//
		} // if
			//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}]+$"),
				s))
				&& Util.groupCount(m1) > 1 && i < IterableUtils.size(list) - 1
				&& Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
						"^\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InBasicLatin}\\p{InHalfwidthAndFullwidthForms}]+$"),
						IterableUtils.get(list, i + 1)))) {
			//
			final String kanji = Util.group(m1, 1);
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), kanji,
					Util.group(m1, 2));
			//
			for (int j = 1; j <= Util.groupCount(m2); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), kanji,
						Util.group(m2, j));
				//
			} // for
				//
			return Pair.of(multimap, toIntList(i, IntStream.rangeClosed(0, 1)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList4(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		final String s1 = testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null);
		//
		Matcher m1;
		//
		String g6 = null, g4 = null;
		//
		final String s2 = testAndApply(x -> IterableUtils.size(x) - 1 > i, list, x -> IterableUtils.get(x, i + 1),
				null);
		//
		if (Boolean.logicalAnd(Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InBasicLatin}]+$"),
				s1)) && Util.groupCount(m1) > 5
				&& StringUtils.countMatches(g6 = Util.group(m1, 6), g4 = Util.group(m1, 4)) == 1,
				Util.matches(Util.matcher(
						PatternMap.getPattern(patternMap,
								"^\\p{InHalfwidthAndFullwidthForms}[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}]+$"),
						s2)))) {
			//
			return Pair.of(
					ImmutableMultimap.of(Util.group(m1, 1), Util.group(m1, 2), Util.group(m1, 3),
							StringUtils.substringBefore(g6, g4), Util.group(m1, 5), StringUtils.substringAfter(g6, g4)),
					toIntList(i, IntStream.rangeClosed(0, 1)));
			//
		} // if
			//
		String commonxPrefix;
		//
		Matcher m2;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(s1))) && Util.groupCount(m1) > 1) {
			//
			if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
					s2)) && Util.groupCount(m2) > 1
					&& StringUtils.isNotEmpty(commonxPrefix = StringUtils.getCommonPrefix(s1, s2))) {
				//
				return Pair.of(
						ImmutableMultimap.of(Util.group(m1, 1), Util.group(m1, 2), commonxPrefix,
								StringUtils.getCommonPrefix(Util.group(m1, 2), Util.group(m2, 2))),
						toIntList(i, IntStream.rangeClosed(0, 1)));
				//
			} // if
				//
			if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
					"^\\p{InCJKUnifiedIdeographs}{2,}\\p{InHiragana}+\\p{InCJKUnifiedIdeographs}\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
					StringUtils.trim(s2))) && Util.groupCount(m2) > 1
					&& StringUtils.startsWith(Util.group(m2, 2), Util.group(m1, 2))) {
				//
				final String g1 = Util.group(m1, 1);
				//
				final String g2 = Util.group(m1, 2);
				//
				return Pair.of(
						ImmutableMultimap.of(g1, g2, StringUtils.substringAfter(Util.group(m2, 1), g1),
								StringUtils.substringAfter(Util.group(m2, 2), g2)),
						toIntList(i, IntStream.rangeClosed(0, 1)));
				//
			} // if
				//
			String commonSuffix, g22;
			//
			if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}([\\p{InKatakana}\\p{InHiragana}]+)\\p{InHalfwidthAndFullwidthForms}$"),
					s2)) && Util
							.groupCount(m2) > 1
					&& StringUtils
							.isNotEmpty(commonSuffix = getCommonSuffix(Util.group(m1, 2), g22 = Util.group(m2, 2)))
					&& CollectionUtils
							.isEqualCollection(
									Collections.singleton(UnicodeBlock.KATAKANA), Util
											.collect(
													distinct(
															mapToObj(
																	chars(StringUtils.substring(g22, 0,
																			StringUtils.length(g22) - StringUtils
																					.length(commonSuffix))),
																	x -> UnicodeBlock.of(x))),
													Collectors.toSet()))) {
				//
				final String g11 = Util.group(m1, 1);
				//
				return Pair.of(ImmutableMultimap.of(g11, Util.group(m1, 2), getCommonSuffix(g11, Util.group(m2, 1)),
						commonSuffix), toIntList(i, IntStream.rangeClosed(0, 1)));
				//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	private static <T> Stream<T> distinct(final Stream<T> instance) {
		return instance != null ? instance.distinct() : null;
	}

	private static IntStream chars(final CharSequence instance) {
		return instance != null ? instance.chars() : null;
	}

	private static <U> Stream<U> mapToObj(final IntStream instance, final IntFunction<? extends U> mapper) {
		return instance != null && mapper != null ? instance.mapToObj(mapper) : null;
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList5(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		Matcher m1, m2 = null;
		//
		String g1;
		//
		if (IterableUtils.size(list) - 1 > i && Boolean.logicalAnd(Util.matches(m1 = Util.matcher(PatternMap.getPattern(
				patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$"),
				StringUtils.trim(IterableUtils.get(list, i)))) && Util.groupCount(m1) > 2,
				IterableUtils.size(list) - 1 > i && Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
						IterableUtils.get(list, i + 1))) && Util.groupCount(m2) > 1)) {
			//
			final String g3 = Util.group(m1, 3);
			//
			Multimap<String, String> multimap = LinkedHashMultimap
					.create(ImmutableMultimap.of(Util.group(m1, 1), g3, Util.group(m1, 2), g3));
			//
			final IntList intList = new IntList();
			//
			IntListUtil.add(intList, i);
			//
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
			//
			if (Util.iterator(entries) != null) {
				//
				String key;
				//
				for (final Entry<String, String> entry : entries) {
					//
					if (StringUtils.isNotBlank(
							StringUtils.getCommonPrefix(key = Util.getKey(entry), g1 = Util.group(m2, 1)))) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								StringUtils.getCommonPrefix(key, g1),
								StringUtils.getCommonPrefix(Util.getValue(entry), Util.group(m2, 2)));
						//
						IntListUtil.add(intList, Util.max(Arrays.stream(IntListUtil.toArray(intList))).getAsInt() + 1);
						//
					} // if
						//
				} // for
					//
			} // if
				//
			return Pair.of(multimap, intList);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList6(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		Matcher m1, m2 = null;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null)))
				&& Util.groupCount(m1) > 1 && i < IterableUtils.size(list) - 1
				&& Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$"),
						IterableUtils.get(list, i + 1)))
				&& Util.groupCount(m2) > 1) {
			//
			Multimap<String, String> multimap = LinkedHashMultimap
					.create(ImmutableMultimap.of(Util.group(m1, 1), Util.group(m1, 2)));
			//
			final IntList intList = new IntList();
			//
			IntListUtil.add(intList, i);
			//
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
			//
			if (Util.iterator(entries) != null) {
				//
				String g1, key;
				//
				for (final Entry<String, String> entry : entries) {
					//
					if (StringUtils.isNotBlank(
							StringUtils.getCommonPrefix(key = Util.getKey(entry), g1 = Util.group(m2, 1)))) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								StringUtils.getCommonPrefix(key, g1),
								StringUtils.getCommonPrefix(Util.getValue(entry), Util.group(m2, 2)));
						//
						IntListUtil.add(intList, Util.max(Arrays.stream(IntListUtil.toArray(intList))).getAsInt() + 1);
						//
					} // if
						//
				} // for
					//
			} // if
				//
			return Pair.of(multimap, intList);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList7(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		final String s1 = testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null);
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InHalfwidthAndFullwidthForms}([\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+$"),
				s1);
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 1) {
			//
			Multimap<String, String> multimap = null;
			//
			IntList intList = null;
			//
			Matcher m;
			//
			boolean found = false;
			//
			for (int j = i + 1; j < IterableUtils.size(list); j++) {
				//
				if (!Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
						"^\\p{InHalfwidthAndFullwidthForms}([\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+$"),
						IterableUtils.get(list, j))) || Util.groupCount(m) <= 1) {
					//
					continue;
					//
				} // if
					//
				if (found) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.getCommonPrefix(Util.group(m1, 1), Util.group(m, 1)),
						StringUtils.getCommonPrefix(Util.group(m1, 2), Util.group(m, 2)));
				//
				IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), i);
				//
				IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), j);
				//
				found = true;
				//
			} // for
				//
			return Pair.of(multimap, intList);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList8(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^([\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null));
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 1) {
			//
			return null;
			//
		} // if
			//
		final String s2 = testAndApply(x -> IterableUtils.size(x) - 1 > i, list, x -> IterableUtils.get(x, i + 1),
				null);
		//
		final Entry<String, String> entry = Pair.of(Util.group(m1, 1), Util.group(m1, 2));
		//
		Entry<Multimap<String, String>, IntList> result = toMultimapAndIntList8(patternMap, s2, entry, i);
		//
		if (result != null || (result = toMultimapAndIntList8(patternMap, s2, entry, list, i)) != null) {
			//
			return result;
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList8(final PatternMap patternMap,
			final String s2, final Entry<String, String> entry, final int i) {
		//
		final Matcher m = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHalfwidthAndFullwidthForms}$"),
				s2);
		//
		final String commonPrefix1, commonPrefix2, g21, g22;
		//
		final String g1 = Util.getKey(entry);
		//
		final String g2 = Util.getValue(entry);
		//
		if (Util.matches(m) && Util.groupCount(m) > 1
				&& StringUtils.isNotEmpty(commonPrefix1 = StringUtils.getCommonPrefix(g1, g21 = Util.group(m, 1)))
				&& StringUtils.isNotEmpty(commonPrefix2 = StringUtils.getCommonPrefix(g2, g22 = Util.group(m, 2)))
				&& StringUtils.isNotEmpty(g1) && StringUtils.isNotEmpty(g2)
				&& StringUtils.equals(StringUtils.substring(g1, StringUtils.length(g1) - 1),
						StringUtils.substring(g2, StringUtils.length(g2) - 1))) {
			//
			return Pair.of(ImmutableMultimap.of(g21, g22, commonPrefix1, commonPrefix2),
					toIntList(i, IntStream.rangeClosed(0, 1)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList8(final PatternMap patternMap,
			final String s2, final Entry<String, String> entry, final List<String> list, final int i) {
		//
		final Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^([\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				s2);
		//
		Matcher m3, m4;
		//
		int groupCount;
		//
		String g21, g22, commonPrefix1, commonPrefix2, commonSuffix1, commonSuffix2;
		//
		final String g1 = Util.getKey(entry);
		//
		final String g2 = Util.getValue(entry);
		//
		if (!(Util.matches(m2) && Util.groupCount(m2) > 1
				&& StringUtils.isNotEmpty(commonSuffix1 = getCommonSuffix(g1, g2))
				&& StringUtils
						.isNotEmpty(commonSuffix2 = getCommonSuffix(g21 = Util.group(m2, 1), g22 = Util.group(m2, 2)))
				&& Util.matches(m3 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}]+$"),
						testAndApply(x -> IterableUtils.size(x) - 2 > i, list, x -> IterableUtils.get(x, i + 2), null)))
				&& (groupCount = Util.groupCount(m3)) > 2
				&& Util.matches(m4 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$"),
						StringUtils.trim(testAndApply(x -> IterableUtils.size(x) - 3 > i, list,
								x -> IterableUtils.get(x, i + 3), null))))
				&& Util.groupCount(m4) > 1)) {
			//
			return null;
			//
		} // if
			//
		final String hiragana = Util.group(m3, groupCount);
		//
		Multimap<String, String> multimap = null;
		//
		for (int j = 1; j < groupCount; j++) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.group(m3, j),
					hiragana);
			//
		} // for
			//
		final String g41 = Util.group(m4, 1);
		//
		final String g42 = Util.group(m4, 2);
		//
		MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g41, g42);
		//
		Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
		//
		if (Util.iterator(entries) != null) {
			//
			for (final Entry<String, String> en : entries) {
				//
				if (StringUtils.isEmpty(commonPrefix1 = StringUtils.getCommonPrefix(g1, g21, Util.getKey(en), g41))) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), commonPrefix1,
						commonPrefix2 = StringUtils.getCommonPrefix(g2, g22, Util.getValue(en), g42));
				//
				testAndAccept((a, b, c) -> StringUtils.length(b) == 1,
						multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.substringAfter(g41, commonPrefix1), StringUtils.substringAfter(g42, commonPrefix2),
						MultimapUtil::put);
				//
			} // for
				//
			String key, value;
			//
			for (final Entry<String, String> en : entries) {
				//
				testAndAccept((a, b, c) -> Boolean.logicalAnd(StringUtils.isNotBlank(b), StringUtils.isNotBlank(c)),
						multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.substringBetween(g1, key = Util.getKey(en), commonSuffix1),
						StringUtils.substringBetween(g2, value = Util.getValue(en), commonSuffix1), MultimapUtil::put);
				//
				testAndAccept((a, b, c) -> Boolean.logicalAnd(StringUtils.isNotBlank(b), StringUtils.isNotBlank(c)),
						multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.substringBetween(g21, key, commonSuffix2),
						StringUtils.substringBetween(g22, value, commonSuffix2), MultimapUtil::put);
				//
			} // for
				//
		} // if
			//
		return Pair.of(multimap, toIntList(i, IntStream.rangeClosed(0, 3)));
		//
	}

	private static <A, B, C> void testAndAccept(@Nullable final TriPredicate<A, B, C> predicate, final A a, final B b,
			final C c, @Nullable final TriConsumer<A, B, C> consumer) {
		if (predicate != null && predicate.test(a, b, c) && consumer != null) {
			consumer.accept(a, b, c);
		}
	}

	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList(final int i, final Matcher m1,
			final Matcher m2, final Matcher m3) {
		//
		Multimap<String, String> multimap = null;
		//
		final int groupCount = Util.groupCount(m1);
		//
		final String hiragana = Util.group(m1, groupCount);
		//
		for (int j = 1; j < groupCount; j++) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.group(m1, j),
					hiragana);
			//
		} // for
			//
		MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.group(m2, 1),
				Util.group(m2, 2));
		//
		final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
		//
		Entry<String, String> entry = null;
		//
		if (entries != null) {
			//
			final String g1 = Util.group(m3, 1);
			//
			final String g2 = Util.group(m3, 2);
			//
			for (final Entry<String, String> en : entries) {
				//
				if (StringUtils.countMatches(g1, Util.getKey(en)) != 1) {
					//
					continue;
					//
				} // if
					//
				if (entry == null) {
					//
					entry = Pair.of(StringUtils.substringAfter(g1, Util.getKey(en)),
							StringUtils.substringAfter(g2, Util.getValue(en)));
					//
				} else {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // for
				//
		} // if
			//
		if (entry != null) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.getKey(entry),
					Util.getValue(entry));
			//
		} // if
			//
		return Pair.of(multimap, toIntList(i, IntStream.rangeClosed(0, 2)));
		//
	}

	@Nullable
	private static IntList toIntList(final int initial, final IntStream is) {
		return collect(map(is, x -> x + initial), IntList::new, IntListUtil::add, (a, b) -> {
		});
	}

	@Nullable
	private static <R> R collect(@Nullable final IntStream instance, @Nullable final Supplier<R> supplier,
			@Nullable final ObjIntConsumer<R> accumulator, @Nullable final BiConsumer<R, R> combiner) {
		return instance != null && combiner != null && supplier != null && accumulator != null
				? instance.collect(supplier, accumulator, combiner)
				: null;
	}

	@Nullable
	private static IntStream map(@Nullable final IntStream instance, @Nullable final IntUnaryOperator mapper) {
		return instance != null && mapper != null ? instance.map(mapper) : instance;
	}

	@Nullable
	private static <T, R> Stream<R> flatMap(@Nullable final Stream<T> instance,
			@Nullable final Function<? super T, ? extends Stream<? extends R>> mapper) {
		return instance != null && mapper != null ? instance.flatMap(mapper) : null;
	}

	private static boolean contains(@Nullable final IntList instance, final int o) {
		return instance != null && instance.contains(o);
	}

	private static void removeValue(@Nullable final IntList instance, final int o) {
		if (instance != null) {
			instance.removeValue(o);
		}
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
						PATTERN_MULTIPLE_CJK_UNIFIED_IDEOGRAPHS_AND_ENDS_WITH_HALFWIDTH_AND_FULL_WIDTH_FORMS,
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
						PATTERN_MULTIPLE_CJK_UNIFIED_IDEOGRAPHS_AND_ENDS_WITH_HALFWIDTH_AND_FULL_WIDTH_FORMS,
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
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap8,
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap9,
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap10);
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
				"^(\\p{InCJKUnifiedIdeographs}+)\\((\\p{InHiragana}+)\\)$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InHiragana}\\p{InCJKUnifiedIdeographs}+\\p{InBasicLatin}[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InBasicLatin}+$");
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
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+",
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKUnifiedIdeographs}]+",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$");
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
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}]+(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}]+",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}\\p{InEnclosedAlphanumerics}(\\p{InHiragana}+)\\p{InKatakana}\\p{InEnclosedAlphanumerics}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InKatakana}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InHiragana}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}+(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InBasicLatin}]+",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}\\p{InCJKUnifiedIdeographs}+[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+$",
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}\\p{InCJKUnifiedIdeographs}+\\p{InKatakana}+[\\p{InCJKUnifiedIdeographs}\\p{InBasicLatin}]+(\\p{InHiragana}+)\\p{InKatakana}[\\p{InBasicLatin}\\p{InHalfwidthAndFullwidthForms}]+",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}]+");
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
		Iterable<String> patterns = Arrays.asList(
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
		patterns = Arrays.asList(
				"^(\\p{InCJKUnifiedIdeographs}+)([きじ])(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}+[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}\\p{InHalfwidthAndFullwidthForms}]+$");
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(input)))
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
		} // if
			//
		Iterable<String> patterns = Arrays.asList(
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}{2,})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+$",
				"^(\\p{InCJKUnifiedIdeographs})([の])\\p{InCJKUnifiedIdeographs}+\\p{InKatakana}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$");
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(input)))
					&& Util.groupCount(m) > 2
					&& StringUtils.countMatches(g3 = Util.group(m, 3), g2 = Util.group(m, 2)) == 1) {
				//
				return Unit.with(ImmutableMultimap.of(Util.group(m, 1), StringUtils.substringBefore(g3, g2)));
				//
			} // if
				//
		} // for
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap8(final PatternMap patternMap, final String input) {
		//
		Iterable<String> patterns = Arrays.asList(
				"^(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InBasicLatin}\\p{InHalfwidthAndFullwidthForms}]+",
				"^(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$");
		//
		Matcher m;
		//
		String g3, g1;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(input)))
					&& Util.groupCount(m) > 2 && StringUtils.startsWith(g3 = Util.group(m, 3), g1 = Util.group(m, 1))) {
				//
				return Unit.with(ImmutableMultimap.of(Util.group(m, 2), StringUtils.substringAfter(g3, g1)));
				//
			} // if
				//
		} // for
			//
		patterns = Arrays.asList(
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+$",
				"^(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InKatakana}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+$",
				"^(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}+[\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InBasicLatin}]+$",
				"^(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}]+$");
		//
		String g4, g2;
		//
		Multimap<String, String> multimap = null;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(input)))
					&& Util.groupCount(m) > 3
					&& StringUtils.countMatches(g4 = Util.group(m, 4), g2 = Util.group(m, 2)) == 1) {
				//
				multimap = LinkedHashMultimap
						.create(ImmutableMultimap.of(Util.group(m, 1), StringUtils.substringBefore(g4, g2),
								g3 = Util.group(m, 3), StringUtils.substringAfter(g4, g2)));
				//
				final int length = StringUtils.length(g3);
				//
				if (length == 2
						&& Objects.equals(StringUtils.substring(g3, 0, 1), StringUtils.substring(g3, 1, length))) {
					//
					MultimapUtil.put(multimap, StringUtils.substring(g3, 0, 1),
							testAndApply(x -> IterableUtils.size(x) == 1, getRepeatedStrings(g4),
									x -> IterableUtils.get(x, 0), null));
					//
				} // if
					//
				return Unit.with(multimap);
				//
			} // if
				//
		} // for
			//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}]+\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}+(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InBasicLatin}]+\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(input))) && Util.groupCount(m) > 2) {
			//
			return Unit.with(ImmutableMultimap.of(g1 = Util.group(m, 1), Util.group(m, 2), g1, Util.group(m, 3)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap9(final PatternMap patternMap, final String input) {
		//
		Matcher m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InKatakana}+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(input));
		//
		String g3, g1;
		//
		if (Util.matches(m) && Util.groupCount(m) > 1 && StringUtils.countMatches(g3 = Util.group(m, 3),
				g1 = KanaConverter.convertKana(Util.group(m, 1), KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA)) == 1) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 2), StringUtils.substringAfter(g3, g1)));
			//
		} // if
			//
		Iterable<String> repeatedStrings = null;
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
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)(\\p{InKatakana}+)\\p{InHalfwidthAndFullwidthForms}"),
				input)) && Util.groupCount(m) > 3 && Objects.equals(Util.group(m, 2), Util.group(m, 4))) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 3)));
			//
		} // if
			//
		final Iterable<String> patterns = Arrays.asList(
				"^\\p{InCJKUnifiedIdeographs}+(\\p{InHiragana}{2,})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}\\p{InKatakana}]+$",
				"^\\p{InCJKUnifiedIdeographs}+([れ])(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$",
				"^\\p{InCJKUnifiedIdeographs}+(\\p{InHiragana}{2})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InHiragana}\\p{InCJKUnifiedIdeographs}+$",
				"^\\p{InCJKUnifiedIdeographs}+(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}+",
				"^\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+(\\p{InHiragana}{2})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}$",
				"^\\p{InKatakana}+(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^\\p{InCJKUnifiedIdeographs}(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$",
				"^\\p{InCJKUnifiedIdeographs}([え])(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InHiragana}{2,})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^\\p{InCJKUnifiedIdeographs}{2,}([け])(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}]+$",
				"^\\p{InCJKUnifiedIdeographs}(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs})[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$");
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(input)))
					&& Util.groupCount(m) > 2
					&& StringUtils.countMatches(g3 = Util.group(m, 3), g1 = Util.group(m, 1)) == 1) {
				//
				return Unit.with(ImmutableMultimap.of(Util.group(m, 2), StringUtils.substringAfter(g3, g1)));
				//
			} // if
				//
		} // for
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap10(final PatternMap patternMap, final String input) {
		//
		Matcher m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}]+$"),
				input);
		//
		String g3, g5;
		//
		if (Util.matches(m) && Util.groupCount(m) > 4
				&& StringUtils.countMatches(g5 = Util.group(m, 5), g3 = Util.group(m, 3)) == 1) {
			//
			final StringBuilder sb1 = new StringBuilder(Util.group(m, 1));
			//
			final StringBuilder sb5 = new StringBuilder(Util.group(m, 5));
			//
			Multimap<String, String> multimap = LinkedHashMultimap
					.create(ImmutableMultimap.of(Util.group(m, 4), StringUtils.substringAfter(g5, g3)));
			//
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
			//
			if (Util.iterator(entries) != null) {
				//
				String key, value;
				//
				for (final Entry<String, String> entry : entries) {
					//
					if (Boolean.logicalAnd(StringUtils.countMatches(sb1, key = Util.getKey(entry)) == 1,
							StringUtils.countMatches(sb5, value = Util.getValue(entry)) == 1)) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								Util.toString(sb1.delete(StringUtils.indexOf(sb1, key),
										StringUtils.indexOf(sb1, key) + StringUtils.length(key))),
								Util.toString(sb5.delete(StringUtils.indexOf(sb5, value),
										StringUtils.indexOf(sb5, value) + StringUtils.length(value))));
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
		String g1, g2, commonSuffix;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^([\\p{InCJKUnifiedIdeographs}(\\p{InKatakana})]+)\\p{InHalfwidthAndFullwidthForms}([\\p{InHiragana}\\p{InKatakana}]+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InKatakana}]+$"),
				input))
				&& Util.groupCount(m) > 1
				&& StringUtils.isNotEmpty(commonSuffix = getCommonSuffix(
						KanaConverter.convertKana(g1 = Util.group(m, 1), KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA),
						g2 = Util.group(m, 2)))) {
			//
			return Unit.with(ImmutableMultimap.of(
					StringUtils.substring(g1, 0, StringUtils.length(g1) - StringUtils.length(commonSuffix)),
					StringUtils.substringBefore(g2, commonSuffix)));
			//
		} // if
			//
		return null;
		//
	}

	// http://www.java2s.com/example/java-utility-method/collection-element-get/getcommonsuffix-collection-string-c-85a78.html
	private static String getCommonSuffix(@Nullable final String s1, @Nullable final String s2) {
		//
		int i = 0;
		//
		if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
			//
			return "";
			//
		} // if
			//
		while (i < s1.length() && i < s2.length() && s1.charAt(s1.length() - 1 - i) == s2.charAt(s2.length() - 1 - i)) {
			//
			i++;
			//
		} // if
			//
		return StringUtils.substring(s1, StringUtils.length(s1) - i);
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