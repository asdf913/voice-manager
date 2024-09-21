package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntConsumer;
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
import org.apache.commons.text.TextStringBuilder;
import org.apache.poi.util.IntList;
import org.apache.poi.util.IntListUtil;
import org.d2ab.function.ObjObjIntFunction;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.javatuples.valueintf.IValue2;
import org.javatuples.valueintf.IValue3;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.meeuw.functional.QuadriFunction;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriPredicate;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
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
		Multimap<String, String> multimap = null, key;
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
			if (!MultimapUtil.isEmpty(key = Util.getKey(entry = toMultimapAndIntList(patternMap, list, i)))) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), key);
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
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList8,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList9,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList10,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList11,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList12,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList13);
		//
		Entry<Multimap<String, String>, IntList> entry = null;
		//
		for (int j = 0; j < IterableUtils.size(functions); j++) {
			//
			if ((entry = apply(IterableUtils.get(functions, j), patternMap, list, i)) != null
					&& !MultimapUtil.isEmpty(Util.getKey(entry)) && !isEmpty(Util.getValue(entry))) {
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

	private static boolean isEmpty(@Nullable final IntList instance) {
		return instance == null || instance.isEmpty();
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
		Matcher m1;
		//
		if (!Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils
						.trim(testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null))))
				|| Util.groupCount(m1) <= 1) {
			//
			return null;
			//
		} // if
			//
		final String s2 = testAndApply(x -> IterableUtils.size(x) - 1 > i, list, x -> IterableUtils.get(x, i + 1),
				null);
		//
		final String g11 = Util.group(m1, 1);
		//
		final String g12 = Util.group(m1, 2);
		//
		final Entry<String, String> entry = Pair.of(g11, g12);
		//
		Entry<Multimap<String, String>, IntList> multimapIntList = toMultimapAndIntList4(patternMap, i, entry, s2);
		//
		if (multimapIntList != null) {
			//
			return multimapIntList;
			//
		} // if
			//
		final String s3 = testAndApply(x -> IterableUtils.size(x) - 2 > i, list, x -> IterableUtils.get(x, i + 2),
				null);
		//
		if ((multimapIntList = toMultimapAndIntList4(patternMap, i, entry, s2, s3)) != null) {
			//
			return multimapIntList;
			//
		} // if
			//
		Matcher m2, m3;
		//
		String g21;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}\\p{InHiragana}]+\\p{InBasicLatin}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}の\\p{InHalfwidthAndFullwidthForms}]+"),
				s2))
				&& Util.groupCount(m2) > 1
				&& Util.matches(m3 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
						s3))
				&& Util.groupCount(m3) > 1) {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(
					ImmutableMultimap.of(Util.group(m1, 1), Util.group(m1, 2), Util.group(m3, 1), Util.group(m3, 2)));
			//
			g21 = Util.group(m2, 1);
			//
			forEach(IntStream.rangeClosed(2, Util.groupCount(m2)),
					x -> MultimapUtil.put(multimap, g21, Util.group(m2, x)));
			//
			final Entry<String, String> commonPrefix = Pair.of(
					StringUtils.getCommonPrefix(toArray(MultimapUtil.keySet(multimap), new String[] {})),
					StringUtils.getCommonPrefix(toArray(MultimapUtil.values(multimap), new String[] {})));
			//
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
			//
			if (Util.iterator(entries) != null) {
				//
				String substringAfter1, substringAfter2;
				//
				for (final Entry<String, String> en : entries) {
					//
					if (!Boolean.logicalAnd(StringUtils.isNotEmpty(
							substringAfter1 = StringUtils.substringAfter(Util.getKey(en), Util.getKey(commonPrefix))),
							StringUtils.isNotEmpty(substringAfter2 = StringUtils.substringAfter(Util.getValue(en),
									Util.getValue(commonPrefix))))) {
						//
						continue;
						//
					} // if
						//
					MultimapUtil.put(multimap, substringAfter1, substringAfter2);
					//
				} // for
					//
			} // if
				//
			MultimapUtil.put(multimap, Util.getKey(commonPrefix), Util.getValue(commonPrefix));
			//
			return Pair.of(multimap, toIntList(i, IntStream.rangeClosed(0, 2)));
			//
		} // if
			//
		return null;
		//
	}

	private static void forEach(@Nullable final IntStream instance, @Nullable final IntConsumer action) {
		if (instance != null && action != null) {
			instance.forEach(action);
		}
	}

	private static interface IntTripleObjectFunction<A, B, C, R> {

		R apply(final int i, final A a, final B b, final C c);

	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList4(final PatternMap patternMap,
			final int i, final Entry<String, String> entry, final String s2) {
		//
		final List<IntTripleObjectFunction<PatternMap, Entry<String, String>, String, Entry<Multimap<String, String>, IntList>>> functions = Arrays
				.asList(OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList4a,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList4b,
						OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList4c);
		//
		Entry<Multimap<String, String>, IntList> multimapAndIntList = null;
		//
		IntTripleObjectFunction<PatternMap, Entry<String, String>, String, Entry<Multimap<String, String>, IntList>> function;
		//
		for (int j = 0; j < IterableUtils.size(functions); j++) {
			//
			if ((multimapAndIntList = (function = IterableUtils.get(functions, j)) != null
					? function.apply(i, patternMap, entry, s2)
					: null) != null) {
				//
				return multimapAndIntList;
				//
			} // if
				//
		} // for
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList4a(final int i,
			final PatternMap patternMap, final Entry<String, String> entry, final String s2) {
		//
		Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				s2);
		//
		final String g1 = Util.getKey(entry);
		//
		final String g2 = Util.getValue(entry);
		//
		String commonPrefix1;
		//
		if (Util.matches(m2) && Util.groupCount(m2) > 1
				&& StringUtils.isNotEmpty(commonPrefix1 = StringUtils.getCommonPrefix(g1, s2))) {
			//
			return Pair.of(
					ImmutableMultimap.of(Util.getKey(entry), g2, commonPrefix1,
							StringUtils.getCommonPrefix(g2, Util.group(m2, 2))),
					toIntList(i, IntStream.rangeClosed(0, 1)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}{2,}\\p{InHiragana}+\\p{InCJKUnifiedIdeographs}\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(s2))) && Util.groupCount(m2) > 1 && StringUtils.startsWith(Util.group(m2, 2), g2)) {
			//
			return Pair.of(
					ImmutableMultimap.of(g1, g2, StringUtils.substringAfter(Util.group(m2, 1), g1),
							StringUtils.substringAfter(Util.group(m2, 2), g2)),
					toIntList(i, IntStream.rangeClosed(0, 1)));
			//
		} // if
			//
		String commonSuffix1, g22;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}([\\p{InKatakana}\\p{InHiragana}]+)\\p{InHalfwidthAndFullwidthForms}$"),
				s2))
				&& Util.groupCount(m2) > 1
				&& StringUtils
						.isNotEmpty(commonSuffix1 = getCommonSuffix(g2, g22 = Util.group(m2, 2)))
				&& CollectionUtils
						.isEqualCollection(
								Collections.singleton(UnicodeBlock.KATAKANA), Util
										.collect(
												distinct(Util.mapToObj(
														Util.chars(StringUtils.substring(g22, 0,
																StringUtils.length(g22)
																		- StringUtils.length(commonSuffix1))),
														UnicodeBlock::of)),
												Collectors.toSet()))) {
			//
			return Pair.of(ImmutableMultimap.of(g1, g2, getCommonSuffix(g1, Util.group(m2, 1)), commonSuffix1),
					toIntList(i, IntStream.rangeClosed(0, 1)));
			//
		} // if
			//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InBasicLatin}\\p{InHalfwidthAndFullwidthForms}]+$"),
				s2)) && Util.groupCount(m2) > 1) {
			//
			final String g21 = Util.group(m2, 1);
			//
			final String commonSuffix2 = getCommonSuffix(g2, Util.group(m2, 2));
			//
			return Pair.of(ImmutableMultimap.of(g1, g2, commonSuffix1 = getCommonSuffix(g1, g21), commonSuffix2,
					StringUtils.substringBefore(g1, commonSuffix1), StringUtils.substringBefore(g2, commonSuffix2), g21,
					g22 = Util.group(m2, 2), StringUtils.substringBefore(g21, commonSuffix1),
					StringUtils.substringBefore(g22, commonSuffix2)), toIntList(i, IntStream.rangeClosed(0, 1)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static <X> X getValue2(@Nullable final IValue2<X> instance) {
		return instance != null ? instance.getValue2() : null;
	}

	@Nullable
	private static <X> X getValue3(@Nullable final IValue3<X> instance) {
		return instance != null ? instance.getValue3() : null;
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList4b(final int i,
			final PatternMap patternMap, final Entry<String, String> entry, final String s2) {
		//
		final Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}\\p{InBasicLatin}]+(\\p{InHiragana}+)[\\p{InKatakana}]+\\p{InHalfwidthAndFullwidthForms}$"),
				s2);
		//
		if (!Util.matches(m2) || Util.groupCount(m2) <= 1) {
			//
			return null;
			//
		} // if
			//
		final String g1 = Util.getKey(entry);
		//
		final String g2 = Util.getValue(entry);
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g1, g2));
		//
		final String g21 = Util.group(m2, 1);
		//
		final Matcher m = m2;
		//
		forEach(IntStream.rangeClosed(2, Util.groupCount(m2)), x -> MultimapUtil.put(multimap, g21, Util.group(m, x)));
		//
		final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
		//
		final Entry<String, String> keyValue = toEntryForToMultimapAndIntList4b(entries, entry);
		//
		if (keyValue != null) {
			//
			String key;
			//
			final String value = Util.getValue(keyValue);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(key = Util.getKey(keyValue), value));
			//
			String substringAfter2;
			//
			for (final Entry<String, String> en : entries) {
				//
				if (StringUtils.isEmpty(substringAfter2 = StringUtils.substringAfter(Util.getValue(en), value))) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(multimap, StringUtils.substringAfter(Util.getKey(en), key), substringAfter2);
				//
			} // for
				//
		} // if
			//
		MultimapUtil.remove(multimap, "緋", "きあけ");
		//
		return Pair.of(multimap, toIntList(i, IntStream.rangeClosed(0, 1)));
		//
	}

	@Nullable
	private static Entry<String, String> toEntryForToMultimapAndIntList4b(final Iterable<Entry<String, String>> entries,
			final Entry<String, String> entry) {
		//
		Entry<String, String> keyValue = null;
		//
		if (Util.iterator(entries) != null) {
			//
			String key, value, commonPrefix1, commonPrefix2;
			//
			final String g1 = Util.getKey(entry);
			//
			final String g2 = Util.getValue(entry);
			//
			for (final Entry<String, String> en : entries) {
				//
				if (Util.or(
						Boolean.logicalAnd(Objects.equals(key = Util.getKey(en), g1),
								Objects.equals(value = Util.getValue(en), g2)),
						StringUtils.isEmpty(commonPrefix1 = StringUtils.getCommonPrefix(g1, key)),
						StringUtils.isEmpty(commonPrefix2 = StringUtils.getCommonPrefix(g2, value)))) {
					//
					continue;
					//
				} // if
					//
				if (keyValue == null) {
					//
					keyValue = Pair.of(commonPrefix1, commonPrefix2);
					//
				} else if (Boolean.logicalOr(!Objects.equals(Util.getKey(keyValue), commonPrefix1),
						!Objects.equals(Util.getValue(keyValue), commonPrefix2))) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return keyValue;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList4c(final int i,
			final PatternMap patternMap, final Entry<String, String> entry, final String s2) {
		//
		final Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$"),
				s2);
		//
		final String g1 = Util.getKey(entry);
		//
		final String g2 = Util.getValue(entry);
		//
		int groupCount;
		//
		if (Util.matches(m2) && (groupCount = Util.groupCount(m2)) > 0) {
			//
			final String groupLast = Util.group(m2, groupCount);
			//
			Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g1, g2));
			//
			for (int j = 1; j < groupCount; j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m2, j), groupLast);
				//
				testAndAccept((a, b, c) -> Boolean.logicalAnd(StringUtils.isNoneEmpty(b), StringUtils.isNoneEmpty(c)),
						multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.getCommonPrefix(g1, Util.group(m2, j)), StringUtils.getCommonPrefix(g2, groupLast),
						MultimapUtil::put);
				//
			} // for
				//
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
			//
			final List<Quartet<String, String, String, String>> quartets = Util
					.toList(Util
							.map(Util.stream(Lists.cartesianProduct(IterableUtils.toList(entries),
									Util.collect(
											Util.filter(StreamSupport.stream(Util.spliterator(entries), false),
													x -> StringUtils.length(Util.getKey(x)) == 1),
											Collectors.toList()))),
									x -> {
										//
										final Entry<String, String> a = testAndApply(y -> IterableUtils.size(y) > 0, x,
												y -> IterableUtils.get(x, 0), null);
										//
										final Entry<String, String> b = testAndApply(y -> IterableUtils.size(y) > 1, x,
												y -> IterableUtils.get(x, 1), null);
										//
										return Quartet.with(Util.getKey(a), Util.getValue(a), Util.getKey(b),
												Util.getValue(b));
										//
									}));
			//
			Quartet<String, String, String, String> quartet = null;
			//
			String a, b, c, d;
			//
			for (int j = 0; j < IterableUtils.size(quartets); j++) {
				//
				if (Util.or(
						Boolean.logicalAnd(
								Objects.equals(a = IValue0Util.getValue0(quartet = IterableUtils.get(quartets, j)),
										c = getValue2(quartet)),
								Objects.equals(b = Util.getValue1(quartet), d = getValue3(quartet))),
						!StringUtils.startsWith(a, c), !StringUtils.startsWith(b, d))) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.substringAfter(a, c), StringUtils.substringAfter(b, d));
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
			final int i, final Entry<String, String> entry, final String s2, final String s3) {
		//
		Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(s2));
		//
		Matcher m3 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(s3));
		//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		String g21, g31, g22, commonPrefix1, commonPrefix2, g32;
		//
		if (Util.matches(m2) && Util.groupCount(m2) > 1 && StringUtils.startsWith(g21 = Util.group(m2, 1), g11)
				&& StringUtils.startsWith(g22 = Util.group(m2, 2), g12) && Util.matches(m3) && Util.groupCount(m3) > 1
				&& StringUtils
						.isNotEmpty(commonPrefix1 = StringUtils.getCommonPrefix(g11, g21, g31 = Util.group(m3, 1)))) {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12, g21, g22,
					g31, g32 = Util.group(m3, 2), StringUtils.substringAfter(g21, g11),
					StringUtils.substringAfter(g22, g12), StringUtils.substringAfter(g31, commonPrefix1),
					StringUtils.substringAfter(g32, commonPrefix2 = StringUtils.getCommonPrefix(g12, g22, g32))));
			//
			MultimapUtil.put(multimap, StringUtils.substringAfter(g11, commonPrefix1),
					StringUtils.substringAfter(g12, commonPrefix2));
			//
			return Pair.of(multimap, toIntList(i, IntStream.rangeClosed(0, 2)));
			//
		} // if
			//
		String commonSuffix1, commonSuffix2;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^([\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}]+$"),
				StringUtils.trim(s2)))
				&& Util.groupCount(m2) > 2
				&& Util.matches(m3 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
						StringUtils.trim(s3)))
				&& Util.groupCount(m3) > 1
				&& StringUtils.isNotEmpty(
						commonPrefix1 = StringUtils.getCommonPrefix(g11, Util.group(m2, 1), g31 = Util.group(m3, 1)))
				&& StringUtils.isNotEmpty(commonPrefix2 = StringUtils.getCommonPrefix(g12, Util.group(m2, 2),
						Util.group(m2, 3), g32 = Util.group(m3, 2)))) {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap
					.create(ImmutableMultimap.of(g11, g12, g31, g32, commonPrefix1, commonPrefix2,
							commonSuffix1 = getCommonSuffix(g11, g31), commonSuffix2 = getCommonSuffix(g12, g32)));
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(StringUtils.substringBefore(g11, commonSuffix1),
							StringUtils.substringBefore(g12, commonSuffix2),
							StringUtils.substringBetween(g31, commonPrefix1, commonSuffix1),
							StringUtils.substringBetween(g32, commonPrefix2, commonSuffix2)));
			//
			return Pair.of(multimap, toIntList(i, IntStream.rangeClosed(0, 2)));
			//
		} // if
			//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$"),
				s2))
				&& Util.groupCount(m2) > 1
				&& Util.matches(m3 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
						StringUtils.trim(s3)))
				&& Util.groupCount(m3) > 1
				&& StringUtils.isNotEmpty(commonSuffix1 = getCommonSuffix(g11, g31 = Util.group(m3, 1)))
				&& StringUtils.isNotEmpty(commonSuffix2 = getCommonSuffix(g12, Util.group(m3, 2)))) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, g31, Util.group(m3, 2), commonSuffix1, commonSuffix2,
					StringUtils.substringBefore(g11, commonSuffix1), StringUtils.substringBefore(g12, commonSuffix2)),
					toIntList(i, IntStream.rangeClosed(0, 2)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static <T> T[] toArray(@Nullable final Collection<T> instance, @Nullable final T[] a) {
		return instance != null && a != null ? instance.toArray(a) : null;
	}

	@Nullable
	private static <T> Stream<T> distinct(@Nullable final Stream<T> instance) {
		return instance != null ? instance.distinct() : null;
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList5(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		final String s1 = testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null);
		//
		Matcher m1, m2 = null;
		//
		String g1;
		//
		if (IterableUtils.size(list) - 1 > i && Boolean.logicalAnd(Util.matches(m1 = Util.matcher(PatternMap.getPattern(
				patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$"),
				StringUtils.trim(s1))) && Util.groupCount(m1) > 2,
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
		final String s2 = testAndApply(x -> IterableUtils.size(x) - 1 > i, list, x -> IterableUtils.get(x, i + 1),
				null);
		//
		String g6 = null, g4 = null;
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
		final String s1 = testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null);
		//
		final String s2 = testAndApply(x -> IterableUtils.size(x) - 1 > i, list, x -> IterableUtils.get(x, i + 1),
				null);
		//
		Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^([\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				s1);
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 1) {
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
		} // if
			//
		Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InBasicLatin}+$"),
				s2);
		//
		final Matcher m3 = Util.matcher(PatternMap.getPattern(patternMap,
				"^([\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$"),
				testAndApply(x -> IterableUtils.size(x) - 2 > i, list, x -> IterableUtils.get(x, i + 2), null));
		//
		String g21, g11, g22, g12, commonPrefix1, commonPrefix2;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InBasicLatin}+$"),
				s1)) && Util.groupCount(m1) > 1 && Util.matches(m2) && Util.groupCount(m2) > 1 && Util.matches(m3)
				&& Util.groupCount(m3) > 1
				&& StringUtils.isNotEmpty(commonPrefix1 = StringUtils.getCommonPrefix(g11 = Util.group(m1, 1),
						g21 = Util.group(m2, 1), Util.group(m3, 1)))
				&& StringUtils.isNotEmpty(commonPrefix2 = StringUtils.getCommonPrefix(g12 = Util.group(m1, 2),
						g22 = Util.group(m2, 2), Util.group(m3, 2)))
				&& StringUtils.startsWith(Util.group(m2, 1), Util.group(m1, 1)) && StringUtils.startsWith(g22, g12)) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, g21, g22, StringUtils.substringAfter(g21, g11),
					StringUtils.substringAfter(g22, g12), commonPrefix1, commonPrefix2,
					StringUtils.substringAfter(g11, commonPrefix1), StringUtils.substringAfter(g12, commonPrefix2)),
					toIntList(i, IntStream.rangeClosed(0, 2)));
			//
		} else if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$"),
				StringUtils.trim(s1)))
				&& Util.groupCount(m1) > 1
				&& Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}]+$"),
						StringUtils.trim(s2)))
				&& Util.groupCount(m2) > 3) {
			//
			final String g23 = Util.group(m2, 3);
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(
					g11 = Util.group(m1, 1), g12 = Util.group(m1, 2), g21 = Util.group(m2, 1), g23, Util.group(m2, 2),
					Util.group(m2, 4), commonPrefix1 = StringUtils.getCommonPrefix(g11, g21),
					commonPrefix2 = StringUtils.getCommonPrefix(g12, g23),
					StringUtils.substringAfter(g11, commonPrefix1), StringUtils.substringAfter(g12, commonPrefix2)));
			//
			MultimapUtil.put(multimap, StringUtils.substringAfter(g21, commonPrefix1),
					StringUtils.substringAfter(g23, commonPrefix2));
			//
			return Pair.of(multimap, toIntList(i, IntStream.rangeClosed(0, 1)));
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

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList9(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		final String s1 = testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null);
		//
		Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^([\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+)\\p{InKatakana}([\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}\\p{InKatakana}]+$"),
				s1);
		//
		final String s2 = testAndApply(x -> IterableUtils.size(x) - 1 > i, list, x -> IterableUtils.get(x, i + 1),
				null);
		//
		Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(s2));
		//
		int groupCount;
		//
		String g21, g22;
		//
		if (Util.matches(m1) && (groupCount = Util.groupCount(m1)) > 0 && Util.matches(m2) && Util.groupCount(m2) > 1) {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap
					.create(ImmutableMultimap.of(g21 = Util.group(m2, 1), g22 = Util.group(m2, 2)));
			//
			final String groupLast = Util.group(m1, groupCount);
			//
			for (int j = 1; j < groupCount; j++) {
				//
				MultimapUtil.put(multimap, Util.group(m1, j), groupLast);
				//
			} // for
				//
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
			//
			if (Util.iterator(entries) != null) {
				//
				String commonPrefix1, commonPrefix2;
				//
				for (final Entry<String, String> entry : entries) {
					//
					if (Boolean.logicalOr(
							StringUtils.isEmpty(commonPrefix1 = StringUtils.getCommonPrefix(g21, Util.getKey(entry))),
							StringUtils
									.isEmpty(commonPrefix2 = StringUtils.getCommonPrefix(g22, Util.getValue(entry))))) {
						//
						continue;
						//
					} // if
						//
					MultimapUtil.put(multimap, commonPrefix1, commonPrefix2);
					//
				} // for
					//
			} // if
				//
			return Pair.of(multimap, toIntList(i, IntStream.rangeClosed(0, 1)));
			//
		} // if
			//
		String g11, g12, commonPrefix;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^([\\p{InHiragana}\\p{InCJKUnifiedIdeographs}]+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				s1))
				&& Util.groupCount(m1) > 1
				&& StringUtils.isNotEmpty(
						commonPrefix = StringUtils.getCommonPrefix(g11 = Util.group(m1, 1), g12 = Util.group(m1, 2)))
				&& Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
						s2))
				&& Util.groupCount(m2) > 1) {
			//
			final String commonSuffix1 = getCommonSuffix(g21 = Util.group(m2, 1), g11);
			//
			final String commonSuffix2 = getCommonSuffix(g22 = Util.group(m2, 2), g12);
			//
			return Pair.of(ImmutableMultimap.of(StringUtils.substringAfter(g11, commonPrefix),
					StringUtils.substringAfter(g12, commonPrefix), g21, g22,
					StringUtils.substringBefore(g21, commonSuffix1), StringUtils.substringBefore(g22, commonSuffix2),
					commonSuffix1, commonSuffix2, StringUtils.substringBetween(g11, commonPrefix, commonSuffix1),
					StringUtils.substringBetween(g12, commonPrefix, commonSuffix2)),
					toIntList(i, IntStream.rangeClosed(0, 1)));
			//
		} // if
			//
		return null;
		//
	}

	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList10(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		final String s1 = testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null);
		//
		Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)$"),
				s1);
		//
		Multimap<String, String> multimap = null;
		//
		IntList intList;
		//
		Matcher m;
		//
		String g1, g2;
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 2 && Util.matches(Util.matcher(
				PatternMap.getPattern(patternMap,
						"^[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+$"),
				testAndApply(x -> IterableUtils.size(x) - 1 > i, list, x -> IterableUtils.get(x, i + 1), null)))) {
			//
			final String g12 = Util.group(m1, 2);
			//
			final String commonSuffix1 = g12;
			//
			final String g13 = Util.group(m1, 3);
			//
			final String commonSuffix2 = StringUtils.substringAfter(g13,
					StringUtils.getCommonPrefix(Util.group(m1, 1), g13));
			//
			intList = toIntList(i, IntStream.rangeClosed(0, 1));
			//
			String line;
			//
			for (int j = i + 1; j < IterableUtils.size(list); j++) {
				//
				if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$"),
						StringUtils.trim(line = IterableUtils.get(list, j))))
						&& StringUtils.endsWith(g1 = Util.group(m, 1), g12)) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(g1, g2 = Util.group(m, 2),
									StringUtils.substringBefore(g1, commonSuffix1),
									StringUtils.substringBefore(g2, commonSuffix2)));
					//
					IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), j);
					//
				} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
						line)) && StringUtils.startsWith(g1 = Util.group(m, 1), g12)) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(g1, g2 = Util.group(m, 2),
									StringUtils.substringAfter(g1, commonSuffix1),
									StringUtils.substringAfter(g2, commonSuffix2)));
					//
					IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), j);
					//
				} // if
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

	private static void replaceFirst(@Nullable final TextStringBuilder instance, final String searchStr,
			final String replaceStr) {
		if (instance != null) {
			instance.replaceFirst(searchStr, replaceStr);
		}
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList11(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		final Triplet<Multimap<String, String>, IntList, String> triplet = toMultimapAndIntListString11(Util.matcher(
				PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+[\\p{InCJKUnifiedIdeographs}\\p{InKatakana}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}]+$"),
				testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null)), i);
		//
		IntList intList = Util.getValue1(triplet);
		//
		Multimap<String, String> multimap = IValue0Util.getValue0(triplet);
		//
		final List<Entry<TextStringBuilder, TextStringBuilder>> tsbs = testAndApply(Objects::nonNull,
				Util.toList(Util.map(Util.stream(MultimapUtil.entries(multimap)),
						x -> Pair.of(new TextStringBuilder(Util.getKey(x)), new TextStringBuilder(Util.getValue(x))))),
				ArrayList::new, null);
		//
		final String g11 = getValue2(triplet);
		//
		final Entry<Multimap<String, String>, IntList> entry = toMultimapAndIntList11(patternMap, list, g11);
		//
		MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.getKey(entry));
		//
		addAll(intList = ObjectUtils.getIfNull(intList, IntList::new), Util.getValue(entry));
		//
		String s;
		//
		TextStringBuilder key;
		//
		for (int j = 0; Boolean.logicalAnd(j < StringUtils.length(g11), Util.iterator(tsbs) != null); j++) {
			//
			for (final Entry<TextStringBuilder, TextStringBuilder> en : tsbs) {
				//
				if (!StringUtils.startsWith(key = Util.getKey(en), s = StringUtils.substring(g11, j, j + 1))) {
					//
					continue;
					//
				} // if
					//
				testAndAccept((a, b) -> StringUtils.length(a) > 1, key, s, (a, b) -> replaceFirst(a, b, ""));
				//
				Util.forEach(MultimapUtil.get(multimap, s), x -> {
					//
					final TextStringBuilder tsb = Util.getValue(en);
					//
					if (StringUtils.startsWith(tsb, x)) {
						//
						replaceFirst(tsb, x, "");
						//
					} // if
						//
				});
				//
			} // for
				//
		} // for
			//
		Iterable<String> strings = null;
		//
		if (Util.and(
				StringUtils.isNotEmpty(s = testAndApply(x -> IterableUtils.size(x) == 1,
						strings = Util.collect(Util.map(Util.stream(tsbs), x -> Util.toString(Util.getKey(x))),
								Collectors.toSet()),
						x -> IterableUtils.get(x, 0), null)),
				IterableUtils.size(strings) == 1, IterableUtils.size(strings = Util
						.toList(Util.map(Util.stream(tsbs), x -> Util.toString(Util.getValue(x))))) == 2)) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s,
					getCommonSuffix(IterableUtils.get(strings, 0), IterableUtils.get(strings, 1)));
			//
		} // if
			//
		Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHiragana}$"),
				StringUtils
						.trim(testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null)));
		//
		final Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)$"), StringUtils
				.trim(testAndApply(x -> IterableUtils.size(x) > i + 1, list, x -> IterableUtils.get(x, i + 1), null)));
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 2 && Util.matches(m2)
				&& Util.matches(Util.matcher(PatternMap.getPattern(patternMap,
						"^\\p{InHiragana}+\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InHiragana}\\p{InCJKUnifiedIdeographs}+\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}$"),
						StringUtils.trim(testAndApply(x -> IterableUtils.size(x) > i + 2, list,
								x -> IterableUtils.get(x, i + 2), null))))) {
			//
			final Iterable<String> ss = Arrays.asList(Util.group(m1, 3), Util.group(m1, 4));
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(m1, 1), ss);
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(m1, 2), ss);
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.group(m1, 3),
					Util.group(m2, 0));
			//
			addAll(intList, toIntList(i, IntStream.rangeClosed(0, 2)));
			//
		} // if
			//
		MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
				toMultimap11(multimap, MultimapUtil.entries(multimap)));
		//
		return Pair.of(multimap, intList);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList12(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		Multimap<String, String> multimap = null;
		//
		IntList intList = null;
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+\\p{InHiragana})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InKatakana}\\p{InCJKSymbolsAndPunctuation}\\p{InHiragana}]+$"),
				testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null));
		//
		String g1;
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 2) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					g1 = Util.group(m1, 1), Util.group(m1, 3));
			//
			IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), i);
			//
			Entry<Multimap<String, String>, IntList> entry;
			//
			int[] ints;
			//
			for (int j = 0; j < StringUtils.length(g1); j++) {
				//
				if ((entry = toMultimapAndIntList12(patternMap, list, i, g1.charAt(j))) == null) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.getKey(entry));
				//
				if ((ints = IntListUtil.toArray(Util.getValue(entry))) != null) {
					//
					for (int k : ints) {
						//
						IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), k);
						//
					} // for
						//
				} // if
					//
			} // for
				//
			final List<Entry<String, String>> entries = testAndApply(Objects::nonNull, MultimapUtil.entries(multimap),
					ArrayList::new, null);
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap12(Util.toList(Util.map(Util.stream(Lists.cartesianProduct(entries, entries)), x -> {
						//
						final Entry<String, String> a = testAndApply(y -> IterableUtils.size(y) > 0, x,
								y -> IterableUtils.get(y, 0), null);
						//
						final Entry<String, String> b = testAndApply(y -> IterableUtils.size(y) > 1, x,
								y -> IterableUtils.get(y, 1), null);
						//
						return Quartet.with(Util.getKey(a), Util.getValue(a), Util.getKey(b), Util.getValue(b));
						//
					}))));
			//
			return Pair.of(multimap, intList);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList13(final PatternMap patternMap,
			final List<String> list, final int i) {
		//
		final String s1 = testAndApply(x -> IterableUtils.size(x) > i, list, x -> IterableUtils.get(x, i), null);
		//
		Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InCJKSymbolsAndPunctuation}]+\\p{InHalfwidthAndFullwidthForms}+(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}]+$"),
				s1);
		//
		String g1;
		//
		int groupCount;
		//
		IntList intList = null;
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(m1) && (groupCount = Util.groupCount(m1)) > 4
				&& StringUtils.length(g1 = Util.group(m1, 1)) == 1) {
			//
			IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), i);
			//
			for (int k = 2; k <= groupCount; k++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g1,
						Util.group(m1, k));
				//
			} // for
				//
			Entry<Multimap<String, String>, IntList> entry = null;
			//
			final List<QuadriFunction<PatternMap, Iterable<String>, Integer, String, Entry<Multimap<String, String>, IntList>>> functions = Arrays
					.asList(OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList13a,
							OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList13b,
							OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimapAndIntList13c);
			//
			for (final QuadriFunction<PatternMap, Iterable<String>, Integer, String, Entry<Multimap<String, String>, IntList>> function : functions) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.getKey(entry = function.apply(patternMap, list, Integer.valueOf(i), g1)));
				//
				addAll(intList = ObjectUtils.getIfNull(intList, IntList::new), Util.getValue(entry));
				//
			} // for
				//
			final List<Entry<String, String>> entries = testAndApply(Objects::nonNull, MultimapUtil.entries(multimap),
					ArrayList::new, null);
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap13(Util.toList(Util.map(Util.stream(Lists.cartesianProduct(entries, entries)), x -> {
						//
						final Entry<String, String> a = testAndApply(y -> IterableUtils.size(y) > 0, x,
								y -> IterableUtils.get(y, 0), null);
						//
						final Entry<String, String> b = testAndApply(y -> IterableUtils.size(y) > 1, x,
								y -> IterableUtils.get(y, 1), null);
						//
						return testAndApply(
								(ka, kb) -> Boolean.logicalAnd(StringUtils.length(ka) == 1,
										StringUtils.length(kb) != 1),
								Util.getKey(a), Util.getKey(b),
								(ka, kb) -> Quartet.with(ka, Util.getValue(a), kb, Util.getValue(b)), null);
						//
					}))));
			//
			testAndAccept(MultimapUtil::containsEntry,
					multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), "土", "に",
					MultimapUtil::remove);
			//
			return Pair.of(multimap, intList);
			//
		} // if
			//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}$"),
				s1)) && Util.groupCount(m1) > 3 && StringUtils.length(g1 = Util.group(m1, 1)) == 1) {
			//
			IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), i);
			//
			final String g2 = Util.group(m1, 2);
			//
			Matcher m;
			//
			String s, g11, g12, cs;
			//
			final Iterable<String> gs = Arrays.asList(Util.group(m1, 3), Util.group(m1, 4));
			//
			Collection<String> ss = null;
			//
			for (int k = 0; k < IterableUtils.size(list); k++) {
				//
				if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, String.format(
						"^(%1$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
						g1)), s = IterableUtils.get(list, k))) && Util.groupCount(m) > 1) {
					//
					IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), k);
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m, 1), Util.group(m, 2));
					//
				} else if (StringUtils.length(g2) > 1 && Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
						String.format(
								"^(\\p{InCJKUnifiedIdeographs}%1$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}+",
								StringUtils.substring(g2, 1, 2))),
						s)) && Util.groupCount(m) > 1) {
					//
					IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), k);
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							g11 = Util.group(m, 1), g12 = Util.group(m, 2));
					//
					if (StringUtils.isNotBlank(cs = getCommonSuffix(g11, g2)) && gs != null) {
						//
						clear(ss = ObjectUtils.getIfNull(ss, ArrayList::new));
						//
						for (final String g : gs) {
							//
							Util.add(ss = ObjectUtils.getIfNull(ss, ArrayList::new), getCommonSuffix(g12, g));
							//
						} // for
							//
						if (CollectionUtils.isNotEmpty(ss)) {
							//
							MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), cs,
									orElseThrow(max(Util.stream(ss),
											(a, b) -> Integer.compare(StringUtils.length(a), StringUtils.length(b)))));
							//
						} // if
							//
					} // if
						//
				} // if
					//
					//
			} // for
				//
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
			//
			if (Util.iterator(entries) != null) {
				//
				Collection<Entry<String, String>> collection = null;
				//
				for (final Entry<String, String> entry : entries) {
					//
					if (entry == null) {
						//
						continue;
						//
					} // if
						//
					if (StringUtils.startsWith(Util.getKey(entry), g1)) {
						//
						Util.add(collection = ObjectUtils.getIfNull(collection, ArrayList::new), entry);
						//
					} // if
						//
				} // for
					//
				String cpk = null, cpv = null;
				//
				if (CollectionUtils.isNotEmpty(collection)) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							cpk = StringUtils.getCommonPrefix(
									toArray(Util.map(Util.stream(collection), Util::getKey), String[]::new)),
							cpv = StringUtils.getCommonPrefix(
									toArray(Util.map(Util.stream(collection), Util::getValue), String[]::new)));
					//
				} // if
					//
				String k, v;
				//
				for (final Entry<String, String> entry : entries) {
					//
					if (entry == null) {
						//
						continue;
						//
					} // if
						//
					if (StringUtils.length(k = Util.getKey(entry)) == 2 && StringUtils.startsWith(k, cpk)
							&& StringUtils.startsWith(v = Util.getValue(entry), cpv)) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								StringUtils.substringAfter(k, cpk), StringUtils.substringAfter(v, cpv));
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

	private static void clear(@Nullable final Collection<?> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	private static <T> T orElseThrow(@Nullable final Optional<T> instance) {
		return instance != null ? instance.orElseThrow() : null;
	}

	private static <T> Optional<T> max(@Nullable final Stream<T> instance,
			@Nullable final Comparator<? super T> comparator) {
		return instance != null && comparator != null ? instance.max(comparator) : null;
	}

	private static <A> A[] toArray(@Nullable final Stream<?> instance, @Nullable final IntFunction<A[]> generator) {
		return instance != null && generator != null ? instance.toArray(generator) : null;
	}

	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList13a(final PatternMap patternMap,
			final Iterable<String> list, final int i, final String g1) {
		//
		Multimap<String, String> multimap = null;
		//
		IntList intList = null;
		//
		String s, g, g11;
		//
		Matcher m;
		//
		int groupCount;
		//
		Collection<String> ss = null;
		//
		for (int k = 0; k < IterableUtils.size(list); k++) {
			//
			if (i == k || !StringUtils.contains(s = IterableUtils.get(list, k), g1)) {
				//
				continue;
				//
			} // if
				//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
					s)) && Util.groupCount(m) > 1) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m, 1), Util.group(m, 2));
				//
				IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), k);
				//
			} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$"),
					s)) && (groupCount = Util.groupCount(m)) > 1 && StringUtils.isNotBlank(g11 = Util.group(m, 1))) {
				//
				IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), k);
				//
				for (int x = 2; x <= groupCount; x++) {
					//
					Util.add(ss = ObjectUtils.getIfNull(ss, ArrayList::new), g = Util.group(m, x));
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g11, g);
					//
				} // for
					//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intList);
		//
	}

	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList13b(final PatternMap patternMap,
			final Iterable<String> list, final int i, final String g1) {
		//
		Multimap<String, String> multimap = null;
		//
		IntList intList = null;
		//
		String s, g11;
		//
		Matcher m;
		//
		int groupCount;
		//
		for (int k = 0; k < IterableUtils.size(list); k++) {
			//
			if (i == k || !StringUtils.contains(s = IterableUtils.get(list, k), g1)) {
				//
				continue;
				//
			} // if
				//
			if ((Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$"),
					s)) && (groupCount = Util.groupCount(m)) > 1 && StringUtils.isNotBlank(g11 = Util.group(m, 1)))
					|| (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
							"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
							StringUtils.trim(s))) && (groupCount = Util.groupCount(m)) > 1
							&& StringUtils.isNotBlank(g11 = Util.group(m, 1)))) {
				//
				IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), k);
				//
				for (int x = 2; x <= groupCount; x++) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g11,
							Util.group(m, x));
					//
				} // for
					//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intList);
		//
	}

	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList13c(final PatternMap patternMap,
			final Iterable<String> list, final int i, final String g1) {
		//
		Multimap<String, String> multimap = null;
		//
		IntList intList = null;
		//
		String s, gLast;
		//
		Matcher m;
		//
		int groupCount;
		//
		for (int k = 0; k < IterableUtils.size(list); k++) {
			//
			if (i == k || !StringUtils.contains(s = IterableUtils.get(list, k), g1)) {
				//
				continue;
				//
			} // if
				//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$"),
					StringUtils.trim(s))) && (groupCount = Util.groupCount(m)) > 2
					&& StringUtils.isNotBlank(gLast = Util.group(m, groupCount))) {
				//
				IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), k);
				//
				for (int x = 1; x < groupCount; x++) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m, x), gLast);
					//
				} // for
					//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intList);
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap13(
			final Iterable<Quartet<String, String, String, String>> quartets) {
		//
		Multimap<String, String> multimap = null;
		//
		Quartet<String, String, String, String> quartet;
		//
		String k1, k2, v1, v2;
		//
		String[] ssk, ssv;
		//
		for (int j = 0; j < IterableUtils.size(quartets); j++) {
			//
			if ((quartet = IterableUtils.get(quartets, j)) == null) {
				//
				continue;
				//
			} // if
				//
			if (Boolean.logicalAnd(StringUtils.startsWith(k2 = getValue2(quartet), k1 = IValue0Util.getValue0(quartet)),
					StringUtils.startsWith(v2 = getValue3(quartet), v1 = Util.getValue1(quartet)))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.substringAfter(k2, k1), StringUtils.substringAfter(v2, v1));
				//
			} else if (StringUtils.length(k2) > 2 && StringUtils.indexOf(k2, k1) > 0 && StringUtils.indexOf(v2, v1) > 0
					&& Util.length(ssk = StringUtils.splitByWholeSeparator(k2, k1)) == 2
					&& Util.length(ssv = StringUtils.splitByWholeSeparator(v2, v1)) == 2) {
				//
				for (int x = 0; x < Math.min(Util.length(ssk), Util.length(ssv)); x++) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), ssk[x],
							ssv[x]);
					//
				} // for
					//
			} // for
				//
		} // for
			//
		return multimap;
		//
	}

	private static <T, U, R> R testAndApply(@Nullable final BiPredicate<T, U> predicate, final T t, final U u,
			final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse) {
		return predicate != null && predicate.test(t, u) ? Util.apply(functionTrue, t, u)
				: Util.apply(functionFalse, t, u);
	}

	private static Pair<Multimap<String, String>, IntList> toMultimapAndIntList12(final PatternMap patternMap,
			final List<String> list, final int i, final char c) {
		//
		Multimap<String, String> multimap = null;
		//
		IntList intList = null;
		//
		String s;
		//
		Matcher m;
		//
		for (int k = i + 1; k < IterableUtils.size(list); k++) {
			//
			if (!StringUtils.contains(s = IterableUtils.get(list, k), c)) {
				//
				continue;
				//
			} // if
				//
			if ((Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$"),
					s)) && Util.groupCount(m) > 1)
					|| (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
							"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
							s)) && Util.groupCount(m) > 1)) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m, 1), Util.group(m, 2));
				//
				IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), k);
				//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intList);
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap12(
			final Iterable<Quartet<String, String, String, String>> quartets) {
		//
		Multimap<String, String> multimap = null;
		//
		Quartet<String, String, String, String> quartet = null;
		//
		String k1, v1, v2, lcsk, lcsv;
		//
		for (int j = 0; j < IterableUtils.size(quartets); j++) {
			//
			if (Objects.equals(v1 = Util.getValue1(quartet = IterableUtils.get(quartets, j)),
					v2 = getValue3(quartet))) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					lcsk = longestCommonSubstring(k1 = IValue0Util.getValue0(quartet), getValue2(quartet)),
					lcsv = longestCommonSubstring(v1, v2));
			//
			if (Boolean.logicalAnd(StringUtils.endsWith(k1, lcsk), StringUtils.endsWith(v1, lcsv))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.substringBefore(k1, lcsk), StringUtils.substringBefore(v1, lcsv));
				//
			} else if (Boolean.logicalAnd(StringUtils.startsWith(k1, lcsk), StringUtils.startsWith(v1, lcsv))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.substringAfter(k1, lcsk), StringUtils.substringAfter(v1, lcsv));
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	private static String longestCommonSubstring(final String a, @Nullable final String b) {
		//
		int start = 0, max = 0;
		//
		for (int i = 0; i < StringUtils.length(a); i++) {
			//
			for (int j = 0; j < StringUtils.length(b); j++) {
				//
				int x = 0;
				//
				while (a.charAt(i + x) == b.charAt(j + x)) {
					//
					x++;
					//
					if (((i + x) >= a.length()) || ((j + x) >= b.length())) {
						//
						break;
						//
					} // if
						//
				} // while
					//
				if (x > max) {
					//
					max = x;
					//
					start = i;
					//
				} // if
					//
			} // for
				//
		} // for
			//
		return StringUtils.substring(a, start, start + max);
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap11(final Multimap<String, String> mm,
			final Iterable<Entry<String, String>> entries) {
		//
		Multimap<String, String> result = null;
		//
		if (Util.iterator(entries) != null) {
			//
			String k;
			//
			Collection<String> ss1, ss2;
			//
			int length;
			//
			for (final Entry<String, String> en : entries) {
				//
				if (Util.or(StringUtils.length(k = Util.getKey(en)) < 2,
						IterableUtils.size(ss1 = MultimapUtil.get(mm, StringUtils.substring(k, 0, 1))) != 1,
						IterableUtils.size(ss2 = MultimapUtil.get(mm,
								StringUtils.substring(k, (length = StringUtils.length(k)) - 1, length))) != 1)) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(result = ObjectUtils.getIfNull(result, LinkedHashMultimap::create),
						StringUtils.substring(k, 1, length - 1), StringUtils.substringBetween(Util.getValue(en),
								IterableUtils.get(ss1, 0), IterableUtils.get(ss2, 0)));
				//
			} // for
				//
		} // if
			//
		return result;
		//
	}

	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList11(final PatternMap patternMap,
			final List<String> list, @Nullable final String string) {
		//
		Multimap<String, String> multimap = null;
		//
		IntList intList = null;
		//
		Matcher m;
		//
		String g1;
		//
		for (int j = 0; j < IterableUtils.size(list); j++) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
					IterableUtils.get(list, j))) && Util.groupCount(m) > 1
					&& StringUtils.contains(string, g1 = Util.group(m, 1))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g1,
						Util.group(m, 2));
				//
				IntListUtil.add(intList = ObjectUtils.getIfNull(intList, IntList::new), j);
				//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intList);
		//
	}

	@Nullable
	private static Triplet<Multimap<String, String>, IntList, String> toMultimapAndIntListString11(final Matcher m,
			final int i) {
		//
		int groupCount;
		//
		if (!Util.matches(m) || (groupCount = Util.groupCount(m)) < 1) {
			//
			return null;
			//
		} // if
			//
		final IntList intList = toIntList(i, IntStream.rangeClosed(0, 0));
		//
		final String g11 = Util.group(m, 1);
		//
		Multimap<String, String> multimap = null;
		//
		for (int j = 2; j < groupCount; j++) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g11,
					Util.group(m, j));
			//
		} // for
			//
		return Triplet.with(multimap, intList, g11);
		//
	}

	private static <A, B> void testAndAccept(final BiPredicate<A, B> predicate, final A a, final B b,
			final BiConsumer<A, B> consumer) {
		if (Util.test(predicate, a, b)) {
			Util.accept(consumer, a, b);
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
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap10,
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap11,
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean::toMultimap12);
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
					if (MultimapUtil.containsEntry(multimap, k = Util.getKey(entry), v = Util.getValue(entry))) {
						//
						MultimapUtil.remove(multimap = LinkedHashMultimap.create(multimap), k, v);
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
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKSymbolsAndPunctuation}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+\\p{InHalfwidthAndFullwidthForms}+(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InKatakana}\\p{InCJKSymbolsAndPunctuation}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}]+$"),
				input)) && Util.groupCount(m) > 5) {
			//
			final String g6 = Util.group(m, 6);
			//
			final String g4 = Util.group(m, 4);
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
	private static IValue0<Multimap<String, String>> toMultimap11(final PatternMap patternMap, final String input) {
		//
		Matcher m = Util.matcher(PatternMap.getPattern(patternMap,
				"^([\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InKatakana}\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}]+(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}\\p{InKatakana}]+$"),
				input);
		//
		int groupCount;
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(m) && (groupCount = Util.groupCount(m)) > 0) {
			//
			final String g1 = Util.group(m, 1);
			//
			String commonPrefix, g;
			//
			for (int i = 2; i <= groupCount; i++) {
				//
				if (StringUtils.isNotEmpty(commonPrefix = StringUtils.getCommonPrefix(g1, g = Util.group(m, i)))) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							StringUtils.substringAfter(g1, commonPrefix), StringUtils.substringAfter(g, commonPrefix));
					//
				} // if
					//
			} // for
				//
			return Unit.with(multimap);
			//
		} // if
			//
		String g3, g4, g2;
		//
		int l3;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InHiragana}\\p{InCJKUnifiedIdeographs}+\\p{InBasicLatin}(\\p{InCJKUnifiedIdeographs}+)\\p{InHiragana}\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}+$"),
				input)) && Util.groupCount(m) > 3 && Objects.equals(Util.group(m, 1), g3 = Util.group(m, 3))
				&& (l3 = StringUtils.length(g3)) == 2
				&& StringUtils.startsWith(g4 = Util.group(m, 4), g2 = Util.group(m, 2))) {
			//
			return Unit.with(
					ImmutableMultimap.of(StringUtils.substring(g3, l3 - 1, l3), StringUtils.substringAfter(g4, g2)));
			//
		} // if
			//
		int index;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana}+)\\p{InCJKUnifiedIdeographs}\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InHiragana}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InBasicLatin}\\p{InHalfwidthAndFullwidthForms}]+$"),
				input)) && Util.groupCount(m) > 2
				&& (index = StringUtils.indexOf(g3 = Util.group(m, 3), Util.group(m, 2))) >= 0) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 1), StringUtils.substring(g3, 0, index)));
			//
		} // if
			//
		String g1, lcs;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^([\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InCJKSymbolsAndPunctuation}\\p{InHiragana}\\p{InKatakana}]+$"),
				input)) && Util.groupCount(m) > 2
				&& StringUtils.isNotEmpty(lcs = longestCommonSubstring(g3 = Util.group(m, 3), g1 = Util.group(m, 1)))) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m, 2), g3, StringUtils.substringAfter(g1, lcs),
					StringUtils.substringAfter(g3, lcs)));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap12(final PatternMap patternMap, final String input) {
		//
		final Matcher m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}\\p{Inkatakana}\\p{InHalfwidthAndFullwidthForms}]+(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}\\p{InBasicLatin}]+(\\p{InHiragana}+)[\\p{InKatakana}\\p{InHalfwidthAndFullwidthForms}]+$"),
				input);
		//
		int groupCount;
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(m) && (groupCount = Util.groupCount(m)) > 2) {
			//
			final String g1 = Util.group(m, 1);
			//
			for (int j = 2; j <= groupCount; j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g1,
						Util.group(m, j));
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