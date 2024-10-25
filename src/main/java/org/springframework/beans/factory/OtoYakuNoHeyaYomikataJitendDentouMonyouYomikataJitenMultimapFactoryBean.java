package org.springframework.beans.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.function.TriFunctionUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.TextStringBuilder;
import org.apache.commons.text.TextStringBuilderUtil;
import org.d2ab.collection.ints.IntCollection;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntIterableUtil;
import org.d2ab.collection.ints.IntList;
import org.d2ab.function.ObjIntPredicate;
import org.javatuples.Quartet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.meeuw.functional.TriPredicate;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import it.unimi.dsi.fastutil.PairUtil;
import it.unimi.dsi.fastutil.ints.IntObjectPair;

public class OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private static final Pattern PATTERN_KANJI_HIRAGANA = Pattern.compile(
			"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$");

	@URL("https://hiramatu-hifuka.com/onyak/monyo.html")
	private String url = null;

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final List<String> lines = Util
				.toList(Util.flatMap(Util.map(Util.map(
						Util.map(
								Util.filter(testAndApply(Objects::nonNull,
										Util.spliterator(Util.toList(NodeUtil.nodeStream(testAndApply(Objects::nonNull,
												testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(),
														null),
												x -> Jsoup.parse(x, 0), null)))),
										x -> StreamSupport.stream(x, false), null), TextNode.class::isInstance),
								Util::toString),
						x -> StringUtils.split(x, "\u3000")), Arrays::asList), List::stream));
		//
		PatternMap patternMap = null;
		//
		Multimap<String, String> multimap = null;
		//
		final int size = IterableUtils.size(lines);
		//
		Entry<Multimap<String, String>, IntCollection> entry = null;
		//
		IntCollection intCollection = null;
		//
		for (int i = 0; i < size; i++) {
			//
			if (IntIterableUtil.containsInt(intCollection, i)) {
				//
				IntIterableUtil.removeInt(intCollection, i);
				//
				continue;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.getKey(entry = toMultimapAndIntCollection(
							patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
							IntObjectPair.of(i, IterableUtils.get(lines, i)), lines)));
			//
			IntCollectionUtil.addAllInts(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
					Util.getValue(entry));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final List<TriFunction<PatternMap, IntObjectPair<String>, Iterable<String>, Entry<Multimap<String, String>, IntCollection>>> functions = Arrays
				.asList(OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection1,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection2,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection3,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection4,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection5,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection6,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection7,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection8,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection9,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection10,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11);
		//
		Entry<Multimap<String, String>, IntCollection> entry = null;
		//
		for (int j = 0; j < IterableUtils.size(functions); j++) {
			//
			if ((entry = TriFunctionUtil.apply(IterableUtils.get(functions, j), patternMap, iop, lines)) != null
					&& !MultimapUtil.isEmpty(Util.getKey(entry)) && CollectionUtils.isNotEmpty(Util.getValue(entry))) {
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

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection1(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+\\p{InHiragana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InCJKSymbolsAndPunctuation}\\p{InHalfwidthAndFullwidthForms}]+$"),
				PairUtil.right(iop));
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 1) {
			//
			IntCollection intCollection = null;
			//
			if (iop != null) {
				//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
						iop.keyInt());
				//
			} // if
				//
			final String g12 = Util.group(m1, 2);
			//
			String g21, g22, csk, csv;
			//
			Matcher m2 = null;
			//
			Multimap<String, String> multimap = null;
			//
			for (int j = 0; j < IterableUtils.size(lines); j++) {
				//
				if (iop != null && iop.keyInt() == j) {
					//
					continue;
					//
				} // if
					//
				if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+%1$s)\\p{InHalfwidthAndFullwidthForms}$",
						g12)), IterableUtils.get(lines, j))) && Util.groupCount(m2) > 1) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(g21 = Util.group(m2, 1), g22 = Util.group(m2, 2),
									csk = getCommonSuffix(Util.group(m1, 1), g21), csv = getCommonSuffix(g12, g22),
									StringUtils.substringBefore(g21, csk), StringUtils.substringBefore(g22, csv)));
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), j);
					//
				} // if
					//
			} // for
				//
			MultimapUtil.remove(multimap, "入替", "いれかわり");
			//
			return Pair.of(multimap, intCollection);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection2(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$"),
				PairUtil.right(iop));
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 3) {
			//
			return null;
			//
		} // if
			//
		IntCollection intCollection = null;
		//
		if (iop != null) {
			//
			IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
					iop.keyInt());
			//
		} // if
			//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		MultimapUtil.put(multimap, Util.group(m1, 1), Util.group(m1, 2));
		//
		final String g13 = Util.group(m1, 3);
		//
		final String g14 = Util.group(m1, 4);
		//
		MultimapUtil.put(multimap, g13, g14);
		//
		final Entry<Multimap<String, String>, IntCollection> entry = toMultimapAndIntCollection2(patternMap, lines,
				Pair.of(g13, g14));
		//
		MultimapUtil.putAll(multimap, Util.getKey(entry));
		//
		IntCollectionUtil.addAllInts(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
				Util.getValue(entry));
		//
		final List<Entry<String, String>> list = testAndApply(Objects::nonNull, MultimapUtil.entries(multimap),
				ArrayList::new, null);
		//
		final List<Quartet<String, String, String, String>> quartets = Util
				.toList(Util.map(Util.stream(Lists.cartesianProduct(list, list)), x -> toQuartet(x)));
		//
		Quartet<String, String, String, String> quartet = null;
		//
		String g21, g22, csk, csv, cpk, cpv, s1, s2, s3, s4;
		//
		Matcher m2;
		//
		for (int i = 0; i < IterableUtils.size(quartets); i++) {
			//
			if (Boolean.logicalAnd(
					Objects.equals(s1 = IValue0Util.getValue0(quartet = IterableUtils.get(quartets, i)),
							s3 = Util.getValue2(quartet)),
					Objects.equals(s2 = Util.getValue1(quartet), s4 = Util.getValue3(quartet)))) {
				//
				continue;
				//
			} // if
				//
			for (int j = 0; j < IterableUtils.size(lines); j++) {
				//
				if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}{6})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
						IterableUtils.get(lines, j))) && Util.groupCount(m2) > 1
						&& StringUtils.isNotBlank(csk = getCommonSuffix(g21 = Util.group(m2, 1), s1))
						&& StringUtils.isNotBlank(csv = getCommonSuffix(g22 = Util.group(m2, 2), s2))) {
					//
					MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g22, csk, csv));
					//
					testAndAccept((a, b) -> !IntIterableUtil.containsInt(a, b),
							intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), j,
							IntCollectionUtil::addInt);
					//
					break;
					//
				} // if
					//
			} // for
				//
			if (Boolean.logicalAnd(StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(s1, s3)),
					StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(s2, s4)))) {
				//
				MultimapUtil.put(multimap, cpk, cpv);
				//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intCollection);
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection2(
			final PatternMap patternMap, final Iterable<String> lines, final Entry<String, String> entry) {
		//
		Matcher m2;
		//
		String line, lcsk, lcsv, g21, g22;
		//
		Multimap<String, String> multimap = null;
		//
		IntCollection intCollection = null;
		//
		final String g13 = Util.getKey(entry);
		//
		final String g14 = Util.getValue(entry);
		//
		for (int j = 0; j < IterableUtils.size(lines); j++) {
			//
			if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
					line = IterableUtils.get(lines, j))) && Util.groupCount(m2) > 3) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(Util.group(m2, 1), Util.group(m2, 2), Util.group(m2, 3),
								Util.group(m2, 4)));
				//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), j);
				//
			} else if (Util.matches(m2 = Util.matcher(PATTERN_KANJI_HIRAGANA, line)) && Util.groupCount(m2) > 1
					&& StringUtils.isNotBlank(lcsk = longestCommonSubstring(g21 = Util.group(m2, 1), g13))
					&& StringUtils.isNotBlank(lcsv = longestCommonSubstring(g22 = Util.group(m2, 2), g14))) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(g21, g22, lcsk, lcsv));
				//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), j);
				//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection3(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				PairUtil.right(iop));
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 3) {
			//
			return null;
			//
		} // if
			//
		final String g11 = Util.group(m1, 1);
		//
		String line, cpk, cpv, g12, g13, g14, g24;
		//
		Matcher m2;
		//
		IntCollection intCollection = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(lines); i++) {
			//
			if (iop != null && iop.keyInt() == i) {
				//
				continue;
				//
			} // if
				//
			if (StringUtils.startsWith(line = IterableUtils.get(lines, i), g11)
					&& Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
							"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
							line))
					&& Util.groupCount(m2) > 3
					&& StringUtils
							.isNotBlank(cpk = StringUtils.getCommonPrefix(g13 = Util.group(m1, 3), Util.group(m2, 3)))
					&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(
							StringUtils.substringAfter(g24 = Util.group(m2, 4), Util.group(m2, 2)),
							StringUtils.substringAfter(g14 = Util.group(m1, 4), g12 = Util.group(m1, 2))))) {
				//
				if (iop != null) {
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
							iop.keyInt());
					//
				} // if
					//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), i);
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(Util.group(m1, 1), StringUtils.substringBefore(g14, g12),
								StringUtils.substringAfter(g13, cpk), StringUtils.substringAfter(g14, cpv), cpk, cpv));
				//
				testAndAccept((a, b) -> StringUtils.length(IValue0Util.getValue0(b)) == 2,
						multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Quartet.with(Util.group(m2, 3), g24, cpk, cpv),
						(a, b) -> MultimapUtil.put(a,
								StringUtils.substringAfter(IValue0Util.getValue0(b), Util.getValue2(b)),
								StringUtils.substringAfter(Util.getValue1(b), Util.getValue3(b))));
				//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection4(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				PairUtil.right(iop));
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 3) {
			//
			return null;
			//
		} // if
			//
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		// A
		//
		Entry<Multimap<String, String>, IntCollection> entry = toMultimapAndIntCollection4A(patternMap, iop, lines,
				Quartet.with(Util.group(m1, 1), Util.group(m1, 2), Util.group(m1, 3), Util.group(m1, 4)));
		//
		IntCollectionUtil.addAllInts(intCollection, Util.getValue(entry));
		//
		MultimapUtil.putAll(multimap, Util.getKey(entry));
		//
		// B
		//
		IntCollectionUtil.addAllInts(intCollection,
				Util.getValue(entry = toMultimapAndIntCollection4B(patternMap, iop, lines, multimap)));
		//
		MultimapUtil.putAll(multimap, Util.getKey(entry));
		//
		return Pair.of(multimap, intCollection);
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection4A(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines,
			final Quartet<String, String, String, String> quartet) {
		//
		IntCollection intCollection = null;
		//
		Multimap<String, String> multimap = null;
		//
		Entry<Multimap<String, String>, IntCollection> entry;
		//
		int[] ints;
		//
		for (int i = 0; i < IterableUtils.size(lines); i++) {
			//
			if (iop != null && iop.keyInt() == i) {
				//
				continue;
				//
			} // if
				//
			if ((ints = IntCollectionUtil.toIntArray(Util.getValue(entry = toMultimapAndIntCollection4A1(patternMap,
					iop, IntObjectPair.of(i, IterableUtils.get(lines, i)), quartet)))) != null) {
				//
				for (final int j : ints) {
					//
					if (!IntIterableUtil
							.containsInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), j)) {
						//
						IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
								j);
						//
					} // if
						//
				} // for
					//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.getKey(entry));
			//
		} // for
			//
		return Pair.of(multimap, intCollection);
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection4A1(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop1,
			@Nullable final IntObjectPair<String> iop2, final Quartet<String, String, String, String> quartet) {
		//
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		final Entry<Multimap<String, String>, IntCollection> entry = toMultimapAndIntCollection4A11(patternMap, iop1,
				iop2, quartet);
		//
		IntCollectionUtil.addAllInts(intCollection, Util.getValue(entry));
		//
		MultimapUtil.putAll(multimap, Util.getKey(entry));
		//
		Matcher m2;
		//
		final String g11 = IValue0Util.getValue0(quartet);
		//
		final String g14 = Util.getValue3(quartet);
		//
		String g21, g22, cpk, cpv;
		//
		final String line = PairUtil.right(iop2);
		//
		if (StringUtils.startsWith(line, g11) && Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				line)) && Util.groupCount(m2) > 1) {
			//
			if (iop1 != null && !IntIterableUtil.containsInt(intCollection, iop1.keyInt())) {
				//
				IntCollectionUtil.addInt(intCollection, iop1.keyInt());
				//
			} // if
				//
			if (iop2 != null && !IntIterableUtil.containsInt(intCollection, iop2.keyInt())) {
				//
				IntCollectionUtil.addInt(intCollection, iop2.keyInt());
				//
			} // if
				//
			MultimapUtil.put(multimap, g21 = Util.group(m2, 1), g22 = StringUtils.trim(Util.group(m2, 2)));
			//
			if (StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21))
					&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g14, g22))) {
				//
				MultimapUtil.putAll(multimap, ImmutableMultimap.of(cpk, cpv, StringUtils.substringAfter(g21, cpk),
						StringUtils.substringAfter(g22, cpv)));
				//
			} // if
				//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection4A11(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop1,
			@Nullable final IntObjectPair<String> iop2, final Quartet<String, String, String, String> quartet) {
		//
		IntCollection intCollection = null;
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m2;
		//
		final String g12 = Util.getValue1(quartet);
		//
		final String g13 = Util.getValue2(quartet);
		//
		final String g14 = Util.getValue3(quartet);
		//
		String g21, g22, cpk, cpv, csk, csv;
		//
		final String line = PairUtil.right(iop2);
		//
		if (StringUtils.isNotBlank(StringUtils.getCommonPrefix(line, g13)) && Util.matches(m2 = Util.matcher(
				PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				line)) && Util.groupCount(m2) > 1) {
			//
			if (iop1 != null && !IntIterableUtil.containsInt(
					intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), iop1.keyInt())) {
				//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
						iop1.keyInt());
				//
			} // if
				//
			if (iop2 != null && !IntIterableUtil.containsInt(
					intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), iop2.keyInt())) {
				//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
						iop2.keyInt());
				//
			} // if
				//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					g21 = Util.group(m2, 1), g22 = StringUtils.trim(Util.group(m2, 2)));
			//
			if (Boolean.logicalAnd(Objects.equals(g21, cpk = StringUtils.getCommonPrefix(g13, g21)), Objects.equals(g22,
					cpv = StringUtils.getCommonPrefix(StringUtils.substringAfter(g14, g12), g22)))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.substringAfter(g13, cpk), StringUtils.substringAfter(g14, cpv));
				//
			} else if (Boolean.logicalAnd(StringUtils.isNotBlank(cpk), StringUtils.isNotBlank(cpv))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), cpk, cpv);
				//
			} else if (Util.and(StringUtils.isNotBlank(cpk), StringUtils.isNotBlank(csk = getCommonSuffix(g13, g21)),
					StringUtils.isNotBlank(csv = getCommonSuffix(g14, g22)))) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(csk, csv, StringUtils.substringBefore(g21, csk),
								StringUtils.substringBefore(g22, csv)));
				//
			} // if
				//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection4B(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines,
			final Multimap<String, String> multimap) {
		//
		Matcher m2;
		//
		String sb, g25;
		//
		Iterable<Entry<String, String>> entries;
		//
		IntCollection intCollection = null;
		//
		for (int i = 0; i < IterableUtils.size(lines); i++) {
			//
			if (iop != null && iop.keyInt() == i) {
				//
				continue;
				//
			} // if
				//
			if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
					"^\\p{InKatakana}+(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}\\p{InBasicLatin}+(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
					IterableUtils.get(lines, i)))
					&& Util.groupCount(m2) > 4
					&& StringUtils.isNotBlank(sb = StringUtils.substringBetween(g25 = Util.group(m2, 5),
							Util.group(m2, 1), Util.group(m2, 3)))
					&& Util.iterator(entries = MultimapUtil.entries(multimap)) != null) {
				//
				for (final Entry<String, String> en : entries) {
					// //
					if (StringUtils.endsWith(g25, Util.getValue(en))) {
						//
						IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
								i);
						//
						MultimapUtil.putAll(multimap, ImmutableMultimap.of(Util.group(m2, 2), sb,
								StringUtils.substringBefore(Util.group(m2, 4), Util.getKey(en)),
								StringUtils.substringBetween(Util.group(m2, 5), Util.group(m2, 3), Util.getValue(en))));
						//
					} // if
						//
				} // for
					//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection5(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}{5})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				PairUtil.right(iop));
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 5) {
			//
			return null;
			//
		} // if
			//
		final IntCollection intCollection = ObjectUtils.getIfNull(createIntCollection(iop), IntList::create);
		//
		final String g12 = Util.group(m1, 2);
		//
		final String g14 = Util.group(m1, 4);
		//
		final String g16 = Util.group(m1, 6);
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(Util.group(m1, 1),
				StringUtils.substringBefore(g16, g12), Util.group(m1, 3), StringUtils.substringBetween(g16, g12, g14)));
		//
		final String g15 = Util.group(m1, 5);
		//
		// A
		//
		final Entry<Multimap<String, String>, IntCollection> entry = toMultimapAndIntCollection5A(patternMap, iop,
				lines, Sextet.with(Util.group(m1, 1), g12, Util.group(m1, 3), g14, g15, g16));
		//
		IntCollectionUtil.addAllInts(intCollection, Util.getValue(entry));
		//
		MultimapUtil.putAll(multimap, Util.getKey(entry));
		//
		// B
		//
		final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
		//
		MultimapUtil.putAll(multimap,
				toMultimap5B(
						Util.toList(Util.filter(
								testAndApply(Objects::nonNull, Util.spliterator(entries),
										x -> StreamSupport.stream(x, false), null),
								x -> StringUtils.length(Util.getKey(x)) == 1)),
						Util.toList(Util.filter(
								testAndApply(Objects::nonNull, Util.spliterator(entries),
										x -> StreamSupport.stream(x, false), null),
								x -> StringUtils.length(Util.getKey(x)) == 2))));
		//
		// C
		//
		MultimapUtil
				.putAll(multimap,
						toMultimap5C(Util.toList(Util.filter(
								testAndApply(Objects::nonNull, Util.spliterator(entries),
										x -> StreamSupport.stream(x, false), null),
								x -> StringUtils.length(Util.getKey(x)) == 3)), multimap));
		//
		// D
		//
		MultimapUtil
				.putAll(multimap,
						toMultimap5D(Util.toList(Util.filter(
								testAndApply(Objects::nonNull, Util.spliterator(entries),
										x -> StreamSupport.stream(x, false), null),
								x -> StringUtils.length(Util.getKey(x)) == 4))));
		//
		final List<Entry<String, String>> es1 = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.spliterator(entries), x -> StreamSupport.stream(x, false), null),
				x -> StringUtils.length(Util.getKey(x)) == 1));
		//
		String k1, v1;
		//
		Entry<String, String> e1;
		//
		final TextStringBuilder tsbk = new TextStringBuilder(g15);
		//
		final TextStringBuilder tsbv = new TextStringBuilder(StringUtils.substringAfter(g16, g14));
		//
		int lk, lv;
		//
		for (int i = 0; i < IterableUtils.size(es1); i++) {
			//
			k1 = Util.getKey(e1 = IterableUtils.get(es1, i));
			//
			if (StringUtils.startsWith(tsbv, v1 = Util.getValue(e1))) {
				//
				delete(tsbk, 0, StringUtils.length(k1));
				//
				delete(tsbv, 0, StringUtils.length(v1));
				//
			} else if (StringUtils.endsWith(tsbv, v1)) {
				//
				delete(tsbk, (lk = StringUtils.length(tsbk)) - StringUtils.length(k1), lk);
				//
				delete(tsbv, (lv = StringUtils.length(tsbv)) - StringUtils.length(v1), lv);
				//
			} // if
				//
		} // for
			//
		Util.forEach(MultimapUtil.entries(ImmutableMultimap.of("車", "ぐるま", "手", "で", "天宮", "てんぐう")),
				x -> MultimapUtil.remove(multimap, Util.getKey(x), Util.getValue(x)));
		//
		testAndAccept((a, b, c) -> Boolean.logicalAnd(StringUtils.isNotBlank(b), StringUtils.isNotBlank(c)), multimap,
				tsbk, tsbv, (a, b, c) -> MultimapUtil.put(a, Util.toString(b), Util.toString(c)));
		//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static IntCollection createIntCollection(@Nullable final IntObjectPair<?> instance) {
		return instance != null ? IntList.create(instance.keyInt()) : null;
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection5A(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines,
			final Sextet<String, String, String, String, String, String> sextet) {
		//
		final String g14 = Util.getValue3(sextet);
		//
		final String g15 = sextet != null ? sextet.getValue4() : null;
		//
		final String g16 = sextet != null ? sextet.getValue5() : null;
		//
		IntCollection intCollection = null;
		//
		Multimap<String, String> multimap = null;
		//
		String line, cpk, g22, g24;
		//
		Matcher m2;
		//
		for (int i = 0; i < IterableUtils.size(lines); i++) {
			//
			if (iop != null && iop.keyInt() == i) {
				//
				continue;
				//
			} // if
				//
			if (StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g15, line = IterableUtils.get(lines, i)))) {
				//
				if (Util.matches(m2 = Util.matcher(PATTERN_KANJI_HIRAGANA, line)) && Util.groupCount(m2) > 1) {
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), i);
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m2, 1), g22 = Util.group(m2, 2));
					//
					testAndAccept((a, b, c) -> StringUtils.isNotBlank(c), multimap, cpk,
							StringUtils.getCommonPrefix(StringUtils.substringAfter(g16, g14), g22), MultimapUtil::put);
					//
				} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
						line)) && Util.groupCount(m2) > 3) {
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), i);
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(Util.group(m2, 1),
									StringUtils.substringBefore(g24 = Util.group(m2, 4), g22 = Util.group(m2, 2)),
									Util.group(m2, 3), StringUtils.substringAfter(g24, g22)));
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap5B(final Iterable<Entry<String, String>> es1,
			final Iterable<Entry<String, String>> es2) {
		//
		Multimap<String, String> multimap = null;
		//
		Entry<String, String> e1, e2;
		//
		String k2, v2;
		//
		for (int i = 0; i < IterableUtils.size(es2); i++) {
			//
			for (int j = 0; j < IterableUtils.size(es1); j++) {
				//
				testAndAccept(
						(a, b, c) -> Boolean.logicalAnd(StringUtils.isNotBlank(Util.getKey(c)),
								StringUtils.isNotBlank(Util.getValue(c))),
						multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Pair.of(k2 = Util.getKey(e2 = IterableUtils.get(es2, i)), v2 = Util.getValue(e2)),
						Pair.of(StringUtils.getCommonPrefix(Util.getKey(e1 = IterableUtils.get(es1, j)), k2),
								StringUtils.getCommonPrefix(Util.getValue(e1), v2)),
						(a, b, c) -> MultimapUtil.put(a, StringUtils.substringAfter(Util.getKey(b), Util.getKey(c)),
								StringUtils.substringAfter(Util.getValue(b), Util.getValue(c))));
				//
			} // for
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap5C(final Iterable<Entry<String, String>> es3,
			final Multimap<String, String> mm) {
		//
		Multimap<String, String> multimap = null;
		//
		Entry<String, String> e1;
		//
		String k1, cpk, cpv, v2, v3, v4;
		//
		final List<Entry<String, String>> list = testAndApply(Objects::nonNull, es3, IterableUtils::toList, null);
		//
		final List<Quartet<String, String, String, String>> quartets = Util.toList(
				Util.map(Util.stream(testAndApply(Objects::nonNull, list, x -> Lists.cartesianProduct(x, x), null)),
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toQuartet));
		//
		Quartet<String, String, String, String> quartet = null;
		//
		for (int i = 0; i < IterableUtils.size(quartets); i++) {
			//
			if (Util.or(
					Boolean.logicalAnd(
							Objects.equals(k1 = IValue0Util.getValue0(quartet = IterableUtils.get(quartets, i)),
									v3 = Util.getValue2(quartet)),
							Objects.equals(v2 = Util.getValue1(quartet), v4 = Util.getValue3(quartet))),
					StringUtils.isBlank(cpk = StringUtils.getCommonPrefix(k1, v3)),
					StringUtils.isBlank(cpv = StringUtils.getCommonPrefix(v2, v4)))) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), cpk, cpv);
			//
			testAndAccept((a, b, c) -> StringUtils.length(b) == 1,
					multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					StringUtils.substringAfter(k1, cpk), Pair.of(v2, cpv),
					(a, b, c) -> MultimapUtil.put(a, b, StringUtils.substringAfter(Util.getKey(c), Util.getValue(c))));
			//
		} // for
			//
		int lk1, ltsb;
		//
		Iterable<String> ik, iv;
		//
		TextStringBuilder tsbv = null;
		//
		for (int i = 0; i < IterableUtils.size(es3); i++) {
			//
			if (Boolean
					.logicalOr(
							IterableUtils
									.isEmpty(
											ik = Util
													.toList(Stream.concat(
															Util.stream(MultimapUtil.get(mm,
																	StringUtils.substring(
																			k1 = Util.getKey(
																					e1 = IterableUtils.get(es3, i)),
																			0, 1))),
															Util.stream(MultimapUtil.get(multimap,
																	StringUtils.substring(k1, 0, 1)))))),
							IterableUtils
									.isEmpty(iv = Util.toList(Stream.concat(
											Util.stream(MultimapUtil.get(mm,
													StringUtils.substring(k1, (lk1 = StringUtils.length(k1)) - 1,
															lk1))),
											Util.stream(MultimapUtil.get(multimap,
													StringUtils.substring(k1, lk1 - 1, lk1)))))))) {
				//
				continue;
				//
			} // if
				//
			ltsb = StringUtils.length(
					tsbv = append(TextStringBuilderUtil.clear(ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)),
							Util.getValue(e1)));
			//
			for (final String s : ik) {
				//
				testAndAccept(StringUtils::startsWith, tsbv, s, (a, b) -> delete(a, 0, StringUtils.length(b)));
				//
			} // for
				//
			if (ltsb == StringUtils.length(tsbv)) {
				//
				continue;
				//
			} // if
				//
			ltsb = StringUtils.length(tsbv);
			//
			for (final String s : iv) {
				//
				testAndAccept((a, b) -> StringUtils.endsWith(Util.getKey(a), Util.getValue(a)), Pair.of(tsbv, s), ltsb,
						(a, b) -> delete(Util.getKey(a), b - StringUtils.length(Util.getValue(a)), b));
				//
			} // for
				//
			if (ltsb == StringUtils.length(tsbv)) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.put(multimap, StringUtils.substring(k1, lk1 - 2, lk1 - 1), Util.toString(tsbv));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap5D(final Iterable<Entry<String, String>> es4) {
		//
		Multimap<String, String> multimap = null;
		//
		Entry<String, String> e1, e2;
		//
		String k1, k2, v1, v2, csk, csv;
		//
		for (int i = 0; i < IterableUtils.size(es4); i++) {
			//
			for (int j = 0; j < IterableUtils.size(es4); j++) {
				//
				if (i == j) {
					//
					continue;
					//
				} // if
					//
				if (Boolean.logicalAnd(
						StringUtils.isNotBlank(csk = getCommonSuffix(k1 = Util.getKey(e1 = IterableUtils.get(es4, i)),
								k2 = Util.getKey(e2 = IterableUtils.get(es4, j)))),
						StringUtils
								.isNotBlank(csv = getCommonSuffix(v1 = Util.getValue(e1), v2 = Util.getValue(e2))))) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							StringUtils.substringBefore(k1, csk), StringUtils.substringBefore(v1, csv));
					//
					testAndAccept(
							(a, b) -> Boolean.logicalAnd(StringUtils.isNotBlank(Util.getKey(Util.getValue1(b))),
									StringUtils.isNotBlank(Util.getValue(Util.getValue1(b)))),
							multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Triplet.with(Pair.of(k1, v1),
									Pair.of(StringUtils.getCommonPrefix(k1, k2), StringUtils.getCommonPrefix(v1, v2)),
									Pair.of(csk, csv)),
							(a, b) -> MultimapUtil.put(a,
									StringUtils.substringBetween(Util.getKey(IValue0Util.getValue0(b)),
											Util.getKey(Util.getValue1(b)), Util.getKey(Util.getValue2(b))),
									StringUtils.substringBetween(Util.getValue(IValue0Util.getValue0(b)),
											Util.getValue(Util.getValue1(b)), Util.getValue(Util.getValue2(b)))));
					//
				} // if
					//
			} // for
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection6(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InKatakana}+(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}\\p{InBasicLatin}+(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				PairUtil.right(iop));
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 2) {
			//
			return null;
			//
		} // if
			//
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		// A
		//
		final String g11 = Util.group(m1, 1);
		//
		final String g13 = Util.group(m1, 3);
		//
		final String hiragana = StringUtils.substringAfter(g13, g11);
		//
		final Triplet<Multimap<String, String>, IntCollection, Entry<String, String>> triplet = toMultimapAndIntCollectionAndTriplet6A(
				iop, lines, Triplet.with(g11, Util.group(m1, 2), g13), hiragana);
		//
		IntCollectionUtil.addAllInts(intCollection, Util.getValue1(triplet));
		//
		MultimapUtil.putAll(multimap, IValue0Util.getValue0(triplet));
		//
		final Entry<String, String> entry = Util.getValue2(triplet);
		//
		final String tk = Util.getKey(entry);
		//
		final String tv = Util.getValue(entry);
		//
		String g21, g22, cpk, cpv;
		//
		Matcher m2;
		//
		for (int i = 0; i < IterableUtils.size(lines); i++) {
			//
			if (iop != null && iop.keyInt() == i) {
				//
				continue;
				//
			} // if
				//
			if (Util.matches(m2 = Util.matcher(PATTERN_KANJI_HIRAGANA, IterableUtils.get(lines, i)))
					&& Util.groupCount(m2) > 1
					&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g21 = Util.group(m2, 1), tk))
					&& StringUtils.length(g21) == 2 && !Objects.equals(g21, tk)
					&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g22 = Util.group(m2, 2), hiragana))) {
				//
				IntCollectionUtil.addInt(intCollection, i);
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(g21, g22, cpk, cpv, StringUtils.substringAfter(g21, cpk),
								StringUtils.substringAfter(g22, cpv), StringUtils.substringAfter(tk, cpk),
								StringUtils.substringAfter(tv, cpv)));
				//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intCollection);
		//
	}

	private static Triplet<Multimap<String, String>, IntCollection, Entry<String, String>> toMultimapAndIntCollectionAndTriplet6A(
			@Nullable final IntObjectPair<String> iop, final Iterable<String> lines,
			final Triplet<String, String, String> triplet, final String hiragana) {
		//
		final String g12 = Util.getValue1(triplet);
		//
		String g21, g22, csk, csv, tk = null, tv = null;
		//
		Matcher m2;
		//
		IntCollection intCollection = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(lines); i++) {
			//
			if ((iop == null || iop.keyInt() != i)
					&& Util.matches(m2 = Util.matcher(PATTERN_KANJI_HIRAGANA, IterableUtils.get(lines, i)))
					&& Util.groupCount(m2) > 1
					&& StringUtils.isNotBlank(csk = getCommonSuffix(g21 = Util.group(m2, 1), g12))
					&& StringUtils.length(g21) == 2
					&& StringUtils.isNotBlank(csv = getCommonSuffix(g22 = Util.group(m2, 2), hiragana))) {
				//
				if (iop != null) {
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
							iop.keyInt());
					//
				} // if
					//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), i);
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(tk = StringUtils.substringBefore(g12, csk),
								tv = StringUtils.substringBefore(hiragana, csv), g21, g22,
								StringUtils.substringBefore(g21, csk), StringUtils.substringBefore(g22, csv), csk,
								csv));
				//
				break;
				//
			} // if
				//
		} // for
			//
		return Triplet.with(multimap, intCollection, Pair.of(tk, tv));
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection7(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{3}$"),
				PairUtil.right(iop));
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 1) {
			//
			final IntCollection intCollection = ObjectUtils.getIfNull(createIntCollection(iop), IntList::create);
			//
			final String g11 = Util.group(m1, 1);
			//
			final String g12 = Util.group(m1, 2);
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12));
			//
			String line, g21, g22, csk, csv;
			//
			int l;
			//
			Matcher m2;
			//
			for (int i = 0; i < IterableUtils.size(lines); i++) {
				//
				if (iop != null && iop.keyInt() == i) {
					//
					continue;
					//
				} // if
					//
				if ((l = StringUtils.length(g11)) == 2
						&& StringUtils.contains(line = IterableUtils.get(lines, i), StringUtils.substring(g11, 1, l))
						&& Util.matches(m2 = Util.matcher(PATTERN_KANJI_HIRAGANA, line)) && Util.groupCount(m2) > 1
						&& StringUtils.isNotBlank(csk = getCommonSuffix(g21 = Util.group(m2, 1), g11))
						&& StringUtils.isNotBlank(csv = getCommonSuffix(g22 = Util.group(m2, 2), g12))
						&& StringUtils.length(g21) == 2) {
					//
					IntCollectionUtil.addInt(intCollection, i);
					//
					MultimapUtil.putAll(multimap,
							ImmutableMultimap.of(g21, g22, StringUtils.substringBefore(g21, csk),
									StringUtils.substringBefore(g22, csv), csk, csv,
									StringUtils.substringBefore(g11, csk), StringUtils.substringBefore(g12, csv)));
					//
				} // if
					//
			} // for
				//
			return Pair.of(multimap, intCollection);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection8(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}+\\p{InHiragana}$"),
				PairUtil.right(iop));
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 1) {
			//
			return null;
			//
		} // if
			//
		final IntCollection intCollection = ObjectUtils.getIfNull(createIntCollection(iop), IntList::create);
		//
		final String g11 = Util.group(m1, 1);
		//
		final String g12 = Util.group(m1, 2);
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12));
		//
		// A
		//
		Entry<Multimap<String, String>, IntCollection> entry = toMultimapAndIntCollection8A(patternMap, iop, lines,
				Pair.of(g11, g12));
		//
		IntCollectionUtil.addAllInts(intCollection, Util.getValue(entry));
		//
		MultimapUtil.putAll(multimap, Util.getKey(entry));
		//
		// B
		//
		final Iterable<Entry<String, String>> entries = Util.toList(
				Util.filter(Util.stream(MultimapUtil.entries(multimap)), x -> StringUtils.length(Util.getKey(x)) == 3));
		//
		IntCollectionUtil.addAllInts(intCollection,
				Util.getValue(entry = toMultimapAndIntCollection8B(iop, lines, entries)));
		//
		MultimapUtil.putAll(multimap, Util.getKey(entry));
		//
		// C
		//
		MultimapUtil.putAll(multimap, toMultimapAnd8C(entries, multimap));
		//
		return Pair.of(multimap, intCollection);
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection8A(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines,
			final Entry<String, String> entry) {
		//
		IntCollection intCollection = null;
		//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		Multimap<String, String> multimap = null;
		//
		String line, g21, g22, cpk, cpv;
		//
		Matcher m2;
		//
		for (int i = 0; i < IterableUtils.size(lines); i++) {
			//
			if (iop != null && iop.keyInt() == i) {
				//
				continue;
				//
			} // if
				//
			if (StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(line = IterableUtils.get(lines, i), g11))) {
				//
				if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}+\\p{InCJKSymbolsAndPunctuation}\\p{InCJKUnifiedIdeographs}+$"),
						line)) && Util.groupCount(m2) > 1
						&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g22 = Util.group(m2, 2), g12))) {
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), i);
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(g21 = Util.group(m2, 1), g22, cpk, cpv,
									StringUtils.substringAfter(g21, cpk), StringUtils.substringAfter(g22, cpv),
									StringUtils.substringAfter(g11, cpk), StringUtils.substringAfter(g12, cpv)));
					//
				} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}{3,}$"),
						line)) && Util.groupCount(m2) > 1) {
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), i);
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m2, 1), Util.group(m2, 2));
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intCollection);
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection8B(
			@Nullable final IntObjectPair<String> iop, final Iterable<String> lines,
			final Iterable<Entry<String, String>> entries) {
		//
		IntCollection intCollection = null;
		//
		Multimap<String, String> multimap = null;
		//
		String line, g21, g22, k, v, kLast, csk, csv;
		//
		Matcher m2;
		//
		int lk;
		//
		Entry<String, String> e;
		//
		for (int i = 0; i < IterableUtils.size(entries); i++) {
			//
			if ((multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create)) == null || multimap
					.containsValue(kLast = StringUtils.substring(k = Util.getKey(e = IterableUtils.get(entries, i)),
							(lk = StringUtils.length(k)) - 1, lk))) {
				//
				continue;
				//
			} // if
				//
			for (int j = 0; j < IterableUtils.size(lines); j++) {
				//
				if ((iop != null && iop.keyInt() == j)
						|| !StringUtils.contains(line = IterableUtils.get(lines, j), kLast)
						|| !Util.matches(m2 = Util.matcher(PATTERN_KANJI_HIRAGANA, line)) || Util.groupCount(m2) <= 1
						|| StringUtils.length(g21 = Util.group(m2, 1)) != 2) {
					//
					continue;
					//
				} // if
					//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), j);
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g21,
						g22 = Util.group(m2, 2));
				//
				if (Boolean.logicalAnd(StringUtils.isNotBlank(csk = getCommonSuffix(g21, k)),
						StringUtils.isNotBlank(csv = getCommonSuffix(g22, v = Util.getValue(e))))) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(StringUtils.substringBefore(g21, csk),
									StringUtils.substringBefore(g22, csv), csk, csv,
									StringUtils.substringBefore(k, csk), StringUtils.substringBefore(v, csv)));
					//
				} // if
					//
				break;
				//
			} // for
				//
		} // for
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimapAnd8C(final Iterable<Entry<String, String>> entries,
			final Multimap<String, String> mm) {
		//
		Multimap<String, String> multimap = null;
		//
		IValue0<Multimap<String, String>> iValue0;
		//
		for (int i = 0; i < IterableUtils.size(entries); i++) {
			//
			if ((iValue0 = toMultimap8C(IterableUtils.get(entries, i), mm)) == null) {
				//
				return null;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					IValue0Util.getValue0(iValue0));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap8C(final Entry<String, String> entry,
			final Multimap<String, String> mm) {
		//
		final String key = Util.getKey(entry);
		//
		if (StringUtils.length(key) != 3) {
			//
			return null;
			//
		} // if
			//
		final String kFirst = StringUtils.substring(key, 0, 1);
		//
		if (!MultimapUtil.containsKey(mm, kFirst)) {
			//
			return null;
			//
		} // if
			//
		final int lk = StringUtils.length(key);
		//
		final String kLast = StringUtils.substring(key, lk - 1, lk);
		//
		if (!MultimapUtil.containsKey(mm, kLast)) {
			//
			return null;
			//
		} // if
			//
		final TextStringBuilder tsbk = new TextStringBuilder(key);
		//
		delete(delete(tsbk, lk - 1, lk), 0, 1);
		//
		final TextStringBuilder tsbv = new TextStringBuilder(Util.getValue(entry));
		//
		int lengthBefore = StringUtils.length(tsbv);
		//
		Iterable<String> cs = MultimapUtil.get(mm, kFirst);
		//
		if (Util.iterator(cs) != null) {
			//
			for (final String s : cs) {
				//
				testAndAccept(StringUtils::startsWith, tsbv, s, (a, b) -> delete(a, 0, StringUtils.length(b)));
				//
			} // for
				//
		} // if
			//
		if (lengthBefore == StringUtils.length(tsbv)) {
			//
			return null;
			//
		} // if
			//
		lengthBefore = StringUtils.length(tsbv);
		//
		if (Util.iterator(cs = MultimapUtil.get(mm, kLast)) != null) {
			//
			for (final String s : cs) {
				//
				testAndAccept((a, b) -> StringUtils.endsWith(Util.getKey(a), Util.getValue(a)), Pair.of(tsbv, s),
						StringUtils.length(tsbv),
						(a, b) -> delete(Util.getKey(a), b - StringUtils.length(Util.getValue(a)), b));
				//
			} // for
				//
		} // if
			//
		if (lengthBefore == StringUtils.length(tsbv)) {
			//
			return null;
			//
		} // if
			//
		return Unit.with(LinkedHashMultimap.create(ImmutableMultimap.of(Util.toString(tsbk), Util.toString(tsbv))));
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection9(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs})(の)(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				PairUtil.right(iop));
		//
		String g12, g14;
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 2
				&& StringUtils.countMatches(g14 = Util.group(m1, 4), g12 = Util.group(m1, 2)) == 1) {
			//
			return Pair.of(
					LinkedHashMultimap
							.create(ImmutableMultimap.of(Util.group(m1, 1), StringUtils.substringBefore(g14, g12),
									Util.group(m1, 3), StringUtils.substringAfter(g14, g12))),
					createIntCollection(iop));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection10(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final String right = PairUtil.right(iop);
		//
		if (StringUtils.equals(StringUtils.trim(right), right)) {
			//
			return null;
			//
		} // if
			//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(right));
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 1) {
			//
			final String g11 = Util.group(m1, 1);
			//
			final String g12 = Util.group(m1, 2);
			//
			final IntCollection intCollection = ObjectUtils.getIfNull(createIntCollection(iop), IntList::create);
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12));
			//
			// A
			//
			final Entry<Multimap<String, String>, IntCollection> entry = toMultimapAndIntCollection10A(patternMap, iop,
					lines, Pair.of(g11, g12));
			//
			IntCollectionUtil.addAllInts(intCollection, Util.getValue(entry));
			//
			MultimapUtil.putAll(multimap, Util.getKey(entry));
			//
			if (MultimapUtil.size(multimap) == 1) {
				//
				final String kLast = StringUtils.substring(g11, 1, 2);
				//
				String g21, g22, csv;
				//
				Matcher m2;
				//
				for (int i = 0; i < IterableUtils.size(lines); i++) {
					//
					if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
							"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
							IterableUtils.get(lines, i))) && Util.groupCount(m2) > 1
							&& StringUtils.endsWith(g21 = Util.group(m2, 1), kLast) && StringUtils.length(g21) == 2) {
						//
						IntCollectionUtil.addInt(intCollection, i);
						//
						MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g22 = Util.group(m2, 2),
								StringUtils.substringBefore(g21, kLast),
								StringUtils.substringBefore(g22, csv = getCommonSuffix(g12, g22)), kLast, csv,
								StringUtils.substringBefore(g11, kLast), StringUtils.substringBefore(g12, csv)));
						//
						break;
						//
					} // if
						//
				} // for
					//
			} // if
				//
			testAndAccept(MultimapUtil::containsEntry, multimap, "中", "ちゅうが", (a, b, c) -> {
				//
				MultimapUtil.remove(a, b, c);
				//
				MultimapUtil.put(a, b, StringUtils.substringBefore(c, "が"));
				//
				testAndAccept(MultimapUtil::containsEntry, multimap, "形", "た", (x, y, z) -> {
					//
					MultimapUtil.remove(x, y, z);
					//
					MultimapUtil.put(x, y, StringUtils.join("が", z));
					//
				});
				//
				testAndAccept(MultimapUtil::containsEntry, multimap, "柄", "ら", (x, y, z) -> {
					//
					MultimapUtil.remove(x, y, z);
					//
					MultimapUtil.put(x, y, StringUtils.join("が", z));
					//
				});
				//
			});
			//
			testAndAccept(MultimapUtil::containsEntry, multimap, "達", "だる", MultimapUtil::remove);
			//
			testAndAccept(MultimapUtil::containsEntry, multimap, "空", "あき", MultimapUtil::remove);
			//
			return Pair.of(multimap, intCollection);
			//
		} // if
			//
		return null;
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection10A(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines,
			final Entry<String, String> entry) {
		//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		final String kFirst = testAndApply(StringUtils::isNotEmpty, g11, x -> StringUtils.substring(x, 0, 1), null);
		//
		String line, g21, g22, cpk, cpv, csk, csv;
		//
		Matcher m2;
		//
		IntCollection intCollection = null;
		//
		Multimap<String, String> multimap = null;
		//
		int length;
		//
		for (int i = 0; i < IterableUtils.size(lines); i++) {
			//
			if (iop != null && iop.keyInt() == i) {
				//
				continue;
				//
			} // if
				//
			if (StringUtils.startsWith(line = IterableUtils.get(lines, i), kFirst) && Util.matches(m2 = Util.matcher(
					PatternMap.getPattern(patternMap,
							"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
					line)) && Util.groupCount(m2) > 1) {
				//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), i);
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						g21 = Util.group(m2, 1), g22 = Util.group(m2, 2));
				//
				if (Boolean.logicalAnd(StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21)),
						StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22)))) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), cpk, cpv);
					//
					testAndAccept((a, b, c) -> Boolean.logicalAnd(StringUtils.isNotBlank(b), StringUtils.isNotBlank(c)),
							multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							StringUtils.substringAfter(g11, cpk), StringUtils.substringAfter(g12, cpv),
							MultimapUtil::put);
					//
					testAndAccept((a, b, c) -> Boolean.logicalAnd(StringUtils.isNotBlank(b), StringUtils.isNotBlank(c)),
							multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							StringUtils.substringAfter(g21, cpk), StringUtils.substringAfter(g22, cpv),
							MultimapUtil::put);
					//
				} else if (Boolean.logicalAnd(StringUtils.isNotBlank(csk = getCommonSuffix(g11, g21)),
						StringUtils.isNotBlank(csv = getCommonSuffix(g12, g22)))) {
					//
					if (Boolean.logicalAnd(StringUtils.equals(csk, g11), StringUtils.length(csk) == 2)) {
						//
						MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								ImmutableMultimap.of(
										csk = StringUtils.substring(csk, (length = StringUtils.length(csk)) - 1,
												length),
										csv, StringUtils.substringBefore(g11, csk),
										StringUtils.substringBefore(g12, csv), StringUtils.substringBefore(g21, csk),
										StringUtils.substringBefore(g22, csv)));
						//
					} // if
						//
				} // if
					//
			} // if
				//
		} // for
			//
		return Pair.of(multimap, intCollection);
		//
	}

	private static class IH implements InvocationHandler {

		private Map<Object, Object> map = null;

		private Map<Object, Object> getMap() {
			return map = ObjectUtils.getIfNull(map, LinkedHashMap::new);
		}

		@Nullable
		@Override
		public Object invoke(final Object proxy, final Method method, @Nullable final Object[] args) throws Throwable {
			//
			final String name = Util.getName(method);
			//
			if (proxy instanceof Map) {
				//
				if (Objects.equals(name, "putAll") && args != null && args.length > 0) {
					//
					Util.putAll(getMap(), (Map) args[0]);
					//
					return null;
					//
				} else if (Objects.equals(name, "get") && args != null && args.length > 0) {
					//
					final Object arg = args[0];
					//
					if (!Util.containsKey(getMap(), arg)) {
						//
						throw new Throwable(Util.toString(arg));
						//
					} // if
						//
					return MapUtils.getObject(getMap(), args[0]);
					//
				} // if
					//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private static interface ObjObjIntObjObjFunction<A, B, D, E, R> {

		R apply(final A a, final B b, final int c, final D d, final E e);
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final String right = PairUtil.right(iop);
		//
		if (StringUtils.equals(StringUtils.trim(right), right)) {
			//
			return null;
			//
		} // if
			//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(right));
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 1) {
			//
			return null;
			//
		} // if
			//
		final String g11 = Util.group(m1, 1);
		//
		final String g12 = Util.group(m1, 2);
		//
		final IntCollection intCollection = ObjectUtils.getIfNull(createIntCollection(iop), IntList::create);
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12));
		//
		// A
		//
		Entry<Multimap<String, String>, IntCollection> entry = null;
		//
		final Map<String, String> map = Reflection.newProxy(Map.class, new IH());
		//
		Util.putAll(map, Map.of(
				//
				"kFirst", testAndApply(StringUtils::isNotEmpty, g11, x -> StringUtils.substring(x, 0, 1), null)
				//
				, "kLast", testAndApply(StringUtils::isNotEmpty, g11, x -> {
					//
					final int length = StringUtils.length(x);
					//
					return StringUtils.substring(x, length - 1, length);
					//
				}, null)
				//
				, "g11", g11, "g12", g12
		//
		));
		//
		final List<ObjObjIntObjObjFunction<PatternMap, IntObjectPair<String>, String, Map<String, String>, Entry<Multimap<String, String>, IntCollection>>> functions = Arrays
				.asList(OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A1,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A2);
		//
		Multimap<String, String> mm = null;
		//
		IntCollection ic = null;
		//
		for (int i = 0; i < IterableUtils.size(lines) && Util.iterator(functions) != null; i++) {
			//
			for (final ObjObjIntObjObjFunction<PatternMap, IntObjectPair<String>, String, Map<String, String>, Entry<Multimap<String, String>, IntCollection>> function : functions) {
				//
				if (function != null
						&& (entry = function.apply(patternMap, iop, i, IterableUtils.get(lines, i), map)) != null
						&& !MultimapUtil.isEmpty(mm = Util.getKey(entry)) && (ic = Util.getValue(entry)) != null
						&& !ic.isEmpty()) {
					//
					IntCollectionUtil.addAllInts(intCollection, ic);
					//
					MultimapUtil.putAll(multimap, mm);
					//
					break;
					//
				} // if
					//
			} // for
				//
		} // for
			//
		Util.forEach(
				Arrays.asList(Triplet.with("絣", "がすり", "かすり"), Triplet.with("甲", "っこう", "こう"),
						Triplet.with("賦", "ぶ", "ふ"), Triplet.with("葉", "ば", "は"), Triplet.with("菱", "びし", "ひし"),
						Triplet.with("飛", "び", "ひ"), Triplet.with("鳥", "どり", "とり"), Triplet.with("箔", "ぱく", "はく"),
						Triplet.with("木", "も", "もく"), Triplet.with("士", "じ", "し"), Triplet.with("珠", "じゅ", "しゅ"),
						Triplet.with("嵌", "がん", "かん"), Triplet.with("本", "ぼん", "ほん"), Triplet.with("手", "で", "て"),
						Triplet.with("珠", "じゅ", "しゅ")),
				//
				a -> testAndAccept(
						b -> b != null
								&& MultimapUtil.containsEntry(multimap, IValue0Util.getValue0(b), Util.getValue1(b)),
						a, b -> {
							//
							final String s1 = IValue0Util.getValue0(b);
							//
							MultimapUtil.remove(multimap, s1, Util.getValue1(b));
							//
							MultimapUtil.put(multimap, s1, Util.getValue2(b));
							//
						})
		//
		);
		//
		testAndAccept(x -> Util.and(MultimapUtil.containsEntry(x, "網", "あ"), MultimapUtil.containsEntry(x, "代", "じろ"),
				MultimapUtil.containsEntry(x, "目", "みめ")), multimap, x -> {
					//
					MultimapUtil.remove(x, "網", "あ");
					//
					MultimapUtil.remove(x, "代", "じろ");
					//
					MultimapUtil.putAll(x, ImmutableMultimap.of("網", "あみ", "代", "しろ", "目", "め", "網代", "あじろ"));
					//
				});
		//
		testAndAccept(x -> Boolean.logicalAnd(MultimapUtil.containsEntry(x, "小", "こと"),
				MultimapUtil.containsEntry(x, "鳥", "り")), multimap, x -> {
					//
					MultimapUtil.remove(x, "小", "こと");
					//
					MultimapUtil.remove(x, "鳥", "り");
					//
					MultimapUtil.putAll(x, ImmutableMultimap.of("小", "こ", "鳥", "とり"));
					//
				});
		//
		if (Util.and(
				Boolean.logicalOr(MultimapUtil.containsEntry(multimap, "網", "あ"),
						MultimapUtil.containsEntry(multimap, "網", "あみ")),
				MultimapUtil.containsEntry(multimap, "干", "ぼし"), MultimapUtil.containsEntry(multimap, "目", "みめ"))) {
			//
			if (MultimapUtil.containsEntry(multimap, "網", "あ")) {
				//
				MultimapUtil.remove(multimap, "網", "あ");
				//
			} // if
				//
			MultimapUtil.remove(multimap, "干", "ぼし");
			//
			MultimapUtil.remove(multimap, "目", "みめ");
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of("網", "あみ", "干", "ほし", "目", "め", "網干", "あぼし"));
			//
		} // if
			//
		testAndAccept(a -> MultimapUtil.containsEntry(a, "金", "か"), multimap, a -> {
			//
			MultimapUtil.remove(a, "金", "か");
			//
			testAndAccept(b -> MultimapUtil.containsEntry(b, "函", "ねばこ"), multimap, b -> {
				//
				MultimapUtil.remove(b, "函", "ねばこ");
				//
				MultimapUtil.putAll(b, ImmutableMultimap.of("金", "かね", "函", "はこ", "金函", "かねばこ"));
				//
			});
			//
			testAndAccept(b -> MultimapUtil.containsEntry(b, "輪", "なわ"), multimap, b -> {
				//
				MultimapUtil.remove(b, "輪", "なわ");
				//
				MultimapUtil.putAll(b, ImmutableMultimap.of("金", "かな", "輪", "わ"));
				//
			});
			//
		});
		//
		testAndAccept(x -> MultimapUtil.containsEntry(x, "花字", "かじ"), multimap,
				x -> MultimapUtil.putAll(x, ImmutableMultimap.of("花", "か", "字", "じ")));
		//
		clear(mm);
		//
		MultimapUtil.putAll(mm, ImmutableMultimap.of("曝", "ざらし", "包", "づつみ", "重", "がさね", "小草", "こくさ", "寓生", "ほや"));
		//
		MultimapUtil.putAll(mm,
				ImmutableMultimap.of("花字", "かじ", "亀蛇", "かめへび", "木葉", "このは", "梅丸", "うめまる", "梅汀", "うめなぎさ"));
		//
		MultimapUtil.putAll(mm, ImmutableMultimap.of("繋", "つな"));
		//
		Util.forEach(IterableUtils.toList(MultimapUtil.entries(mm)),
				x -> testAndAccept(
						(a, b) -> b != null && MultimapUtil.containsEntry(multimap, Util.getKey(b), Util.getValue(b)),
						multimap, x, (a, b) -> MultimapUtil.remove(a, Util.getKey(b), Util.getValue(b))));
		//
		return Pair.of(multimap, intCollection);
		//
	}

	private static void clear(@Nullable final Multimap<String, String> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A1(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((iop != null && iop.keyInt() == i),
				StringUtils.equals(line, StringUtils.trim(PairUtil.right(iop))))) {
			//
			return null;
			//
		} // if
			//
		Matcher m2;
		//
		final String kFirst = MapUtils.getObject(map, "kFirst");
		//
		final String kLast = MapUtils.getObject(map, "kLast");
		//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		String g21, g22, cpk, csk, cpv, csv;
		//
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 1 && StringUtils.length(g21 = Util.group(m2, 1)) == 2
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21))
				&& StringUtils.isNotBlank(csk = getCommonSuffix(g11, g21))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2)))
				&& StringUtils.isNotBlank(csv = getCommonSuffix(g12, g22))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g22, cpk, cpv, csk, csv,
					StringUtils.substringBetween(g11, cpk, csk), StringUtils.substringBetween(g12, cpv, csv)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 1 && StringUtils.length(g21 = Util.group(m2, 1)) == 2
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g22, cpk, cpv));
			//
			testAndAccept((a, b) -> Boolean.logicalAnd(StringUtils.isNotBlank(a), StringUtils.isNotBlank(b)),
					 StringUtils.substringAfter(g21, cpk),  StringUtils.substringAfter(g22, cpv), (a, b) -> {
						MultimapUtil.put(multimap, a, b);
					});
			//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A2(
			final PatternMap patternMap, final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((iop != null && iop.keyInt() == i),
				StringUtils.equals(line, StringUtils.trim(PairUtil.right(iop))))) {
			//
			return null;
			//
		} // if
			//
		Matcher m2;
		//
		final String kFirst = MapUtils.getObject(map, "kFirst");
		//
		final String kLast = MapUtils.getObject(map, "kLast");
		//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		String g21, g22, cpk, csk, cpv, csv;
		//
		String sak, sav;
		//
		int countMatches, indexOf;
		//
		TextStringBuilder tsbk = null, tsbv = null;
		//
		int lastIndexOf, length;
		//
		String g23, g24;
		//
		String g25, g26, g27, g28;
		//
		String lcsk, lcsv;
		//
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 1 && StringUtils.length(g21 = Util.group(m2, 1)) == 3) {
			//
			if (Boolean.logicalAnd(StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21)),
					StringUtils.isNotBlank(csk = getCommonSuffix(g11, g21)))
					&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2)))
					&& StringUtils.isNotBlank(csv = getCommonSuffix(g12, g22))) {
				//
				IntCollectionUtil.addInt(intCollection, i);
				//
				MultimapUtil.putAll(multimap, ImmutableMultimap.of(g11, g12, cpk, cpv,
						StringUtils.substringBetween(g21, cpk, csk), StringUtils.substringBetween(g22, cpv, csv), csk,
						csv, StringUtils.substringBetween(g11, cpk, csk), StringUtils.substringBetween(g12, cpv, csv)));
				//
			} else if (Util.and(StringUtils.isNotBlank(cpk), StringUtils.isNotBlank(csk = getCommonSuffix(g11, g21)),
					StringUtils.isBlank(StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2))),
					StringUtils.isNotBlank(csv = getCommonSuffix(g12, g22)))) {
				//
				if (StringUtils.length(g21) == (countMatches = StringUtils.countMatches(g22, 'ん'))) {
					//
					IntCollectionUtil.addInt(intCollection, i);
					//
					MultimapUtil.put(multimap, g21, g22);
					//
					for (int j = 0; j < countMatches; j++) {
						//
						MultimapUtil.put(multimap, StringUtils.substring(g21, j, j + 1),
								StringUtils.substring(g22, j * 2, j * 2 + 2));
						//
					} // for
						//
				} else if (countMatches == 2) {
					//
					if ((indexOf = StringUtils.indexOf(g22, 'ん')) == 2) {
						//
						IntCollectionUtil.addInt(intCollection, i);
						//
						MultimapUtil.putAll(multimap,
								ImmutableMultimap.of(StringUtils.substring(g21, 0, 1),
										StringUtils.substring(g22, 0, indexOf - 1), StringUtils.substring(g21, 1, 2),
										StringUtils.substring(g22, indexOf - 1, indexOf + 1)));
						//
					} else if (indexOf == 1) {
						//
						IntCollectionUtil.addInt(intCollection, i);
						//
						MultimapUtil.putAll(multimap,
								ImmutableMultimap.of(StringUtils.substring(g21, 0, 1),
										StringUtils.substring(g22, 0, indexOf + 1), StringUtils.substring(g21, 1, 2),
										StringUtils.substring(g22, indexOf + 1,
												StringUtils.length(g22) - StringUtils.length(csv))));
						//
					} // if
						//
					MultimapUtil.put(multimap, csk, csv);
					//
				} else if (countMatches == 1) {
					//
					IntCollectionUtil.addInt(intCollection, i);
					//
					MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g22, StringUtils.substringBefore(g21, csk),
							StringUtils.substringBefore(g22, csv), csk, csv));
					//
				} // if
					//
			} else if (StringUtils.countMatches(g22 = Util.group(m2, 2), 'ん') == 2) {
				//
				IntCollectionUtil.addInt(intCollection, i);
				//
				append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)), g21);
				//
				append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)), g22);
				//
				if ((lastIndexOf = StringUtils.lastIndexOf(tsbv, 'ん')) == StringUtils.length(tsbv) - 1) {
					//
					MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
							substring(tsbv, lastIndexOf - 1, StringUtils.length(tsbv)));
					//
					delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
					//
					delete(tsbv, lastIndexOf - 1, StringUtils.length(tsbv));
					//
				} // if
					//
				if ((indexOf = StringUtils.indexOf(tsbv, 'ん')) > 0) {
					//
					if (lastIndexOf - indexOf == 2) {
						//
						MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
								substring(tsbv, indexOf - 1, StringUtils.length(tsbv)));
						//
						delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
						//
						if (StringUtils.length(tsbk) == 1) {
							//
							delete(tsbv, (length = StringUtils.length(tsbv)) - 2, length);
							//
						} // if
							//
						MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
						//
					} else if (lastIndexOf - indexOf == 3) {
						//
						MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
								substring(tsbv, (length = StringUtils.length(tsbv)) - 1, length));
						//
						delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
						//
						delete(tsbv, (length = StringUtils.length(tsbv)) - 1, length);
						//
						MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
						//
					} // if
						//
				} // if
					//
			} // if
				//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 3) {
			//
			if (Boolean.logicalAnd(StringUtils.isNotBlank(csk = getCommonSuffix(g11, g23 = Util.group(m2, 3))),
					StringUtils.isNotBlank(csv = getCommonSuffix(g12, g24 = Util.group(m2, 4))))) {
				//
				IntCollectionUtil.addInt(intCollection, i);
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(Util.group(m2, 1),
								StringUtils.substringBefore(g24, g22 = Util.group(m2, 2)),
								StringUtils.substringBefore(g23, csk),
								StringUtils.substringBefore(StringUtils.substringAfter(g24, g22), csv), csk, csv));
				//
			} else if (Boolean.logicalAnd(
					StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, Util.group(m2, 1))),
					StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g24)))) {
				//
				IntCollectionUtil.addInt(intCollection, i);
				//
				MultimapUtil.put(multimap, cpk, cpv);
				//
			} // if
				//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}%2$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g24 = Util.group(m2, 4)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(Util.group(m2, 1), cpv,
					lcsk = longestCommonSubstring(g11, g23 = Util.group(m2, 3)),
					lcsv = longestCommonSubstring(g12, sav = StringUtils.substringAfter(g24, Util.group(m2, 2))),
					StringUtils.substringBefore(g23, lcsk), StringUtils.substringBefore(sav, lcsv),
					StringUtils.substringAfter(g23, lcsk), StringUtils.substringAfter(sav, lcsv)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 1 && StringUtils.length(g21 = Util.group(m2, 1)) == 2
				&& StringUtils.isNotBlank(csk = getCommonSuffix(g11, Util.group(m2, 1)))
				&& StringUtils.isNotBlank(csv = getCommonSuffix(g12, g22 = Util.group(m2, 2)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(StringUtils.substringBefore(g21, csk),
					StringUtils.substringBefore(g22, csv), csk, csv));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InKatakana}\\p{InHiragana}]+\\p{InBasicLatin}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2)))
				&& StringUtils.isNotBlank(csk = getCommonSuffix(g11, g21))
				&& StringUtils.isNotBlank(csv = getCommonSuffix(g12, g22))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g21, g22, StringUtils.substringBefore(g21, csk),
							StringUtils.substringBefore(g22, csv), g23 = Util.group(m2, 3), g24 = Util.group(m2, 4),
							StringUtils.substringBetween(g21, cpk, csk), StringUtils.substringBetween(g24, cpv, csv),
							StringUtils.substringAfter(g23, csk), StringUtils.substringAfter(g24, csv)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana}{3})(\\p{InCJKUnifiedIdeographs}+%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g24 = Util.group(m2, 4)))
				&& StringUtils.isNotBlank(csk = getCommonSuffix(g11, g23 = Util.group(m2, 3)))
				&& StringUtils.isNotBlank(csv = getCommonSuffix(g12, g24))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(cpk, cpv, StringUtils.substringBefore(g23, csk),
					StringUtils.substringBetween(g24, Util.group(m2, 2), csv), csk, csv));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs})(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g24 = Util.group(m2, 4)))
				&& StringUtils.isNotBlank(csk = getCommonSuffix(g11, g23 = Util.group(m2, 3)))
				&& StringUtils.isNotBlank(csv = getCommonSuffix(g12, g24))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(cpk, cpv, StringUtils.substringBefore(g23, csk),
					StringUtils.substringBetween(g24, Util.group(m2, 2), csv), csk, csv,
					StringUtils.substringAfter(g21, cpk), StringUtils.substringBetween(g24, cpv, Util.group(m2, 2))));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				line)) && Util.groupCount(m2) > 1
				&& StringUtils.length(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1))) == 2
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			if (StringUtils.isNotBlank(csk = getCommonSuffix(g11, g21))) {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(g21, g22, cpk, cpv, StringUtils.substringBetween(g21, cpk, csk),
								StringUtils.substringBetween(g22, cpv, csv = getCommonSuffix(g12, g22)), csk, csv));
				//
			} else {
				//
				MultimapUtil.putAll(multimap, ImmutableMultimap.of(cpk, cpv, StringUtils.substringAfter(g11, cpk),
						StringUtils.substringAfter(g12, cpv)));
				//
				if (StringUtils.length(g21) - StringUtils.length(cpk) == 1) {
					//
					MultimapUtil.put(multimap, StringUtils.substringAfter(g21, cpk),
							StringUtils.substringAfter(g22, cpv));
					//
				} // if
					//
			} // if
				//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 2
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(csk = getCommonSuffix(g11, g21))
				&& StringUtils.isNotBlank(csv = getCommonSuffix(getCommonSuffix(g12, g22 = Util.group(m2, 2)),
						g23 = Util.group(m2, 3)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g22, g21, g23, csk, csv,
					StringUtils.substringBefore(g21, csk), StringUtils.substringBefore(g23, csv)));
			//
			if (StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22))) {
				//
				MultimapUtil.putAll(multimap, ImmutableMultimap.of(cpk, cpv,
						StringUtils.substringBetween(g21, cpk, csk), StringUtils.substringBetween(g22, cpv, csv)));
				//
			} // if
				//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}%2$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.indexOf(g22 = Util.group(m2, 2), lcsv = longestCommonSubstring(g12, g22)) > 0) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(StringUtils.substringBefore(g11, kLast),
					StringUtils.substringBefore(g12, lcsv), g21 = Util.group(m2, 1), g22,
					StringUtils.substringBefore(g21, kLast), StringUtils.substringBefore(g22, lcsv), kLast, lcsv,
					sak = StringUtils.substringAfter(g21, kLast), sav = StringUtils.substringAfter(g22, lcsv)));
			//
			MultimapUtil.put(multimap, StringUtils.join(kLast, sak), StringUtils.join(lcsv, sav));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{3}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}{2}$",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(cpk, cpv, StringUtils.substringAfter(g11, cpk),
							StringUtils.substringAfter(g12, cpv), g21, g22, StringUtils.substringAfter(g21, cpk),
							StringUtils.substringAfter(g22, cpv)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InBasicLatin}+(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line))
				&& Util.groupCount(m2) > 7
				&& StringUtils
						.isNotBlank(csk = getCommonSuffix(
								getCommonSuffix(getCommonSuffix(g21 = Util.group(m2, 1), g23 = Util.group(m2, 3)),
										g25 = Util.group(m2, 5)),
								g27 = Util.group(m2, 7)))
				&& StringUtils.isNotBlank(csv = getCommonSuffix(
						getCommonSuffix(getCommonSuffix(g22 = Util.group(m2, 2), g24 = Util.group(m2, 4)),
								g26 = Util.group(m2, 6)),
						g28 = Util.group(m2, 8)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g21, g22, StringUtils.substringBefore(g21, csk),
							StringUtils.substringBefore(g22, csv), csk, csv, g23, g24,
							StringUtils.substringBefore(g23, csk), StringUtils.substringBefore(g24, csv)));
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g25, g26, StringUtils.substringBefore(g25, csk),
							StringUtils.substringBefore(g26, csv), g27, g28, StringUtils.substringBefore(g27, csk),
							StringUtils.substringBefore(g28, csv)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}{2}%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(csk = getCommonSuffix(g11, Util.group(m2, 3)))
				&& StringUtils.isNotBlank(csv = getCommonSuffix(g12, g24 = Util.group(m2, 4)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			if (Objects.equals(g22 = Util.group(m2, 2), "び")) {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(g23 = Util.group(m2, 3), StringUtils.substringAfter(g24, g22),
								StringUtils.substringBefore(g23, csk), StringUtils.substringBetween(g24, g22, csv), csk,
								csv));
				//
			} else {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(Util.group(m2, 1), StringUtils.substringBefore(g24, g22),
								g23 = Util.group(m2, 3), StringUtils.substringAfter(g24, g22),
								StringUtils.substringBefore(g23, csk), StringUtils.substringBetween(g24, g22, csv), csk,
								csv));
				//
			} // if
				//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(csk = getCommonSuffix(g11, Util.group(m2, 3)))
				&& StringUtils.isNotBlank(csv = getCommonSuffix(g12, Util.group(m2, 4)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.put(multimap, csk, csv);
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{2}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}{2}$",
				kFirst)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g24 = Util.group(m2, 4)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(cpk, cpv, Util.group(m2, 3),
					StringUtils.substringAfter(g24, Util.group(m2, 2))));
			//
		} else if ((Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{2}\\p{InHiragana}$",
				kFirst)), line))
				|| Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
						"^(%1$s\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}{6}$",
						kFirst)), line))
				|| Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
						"^(%1$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{3}\\p{InCJKSymbolsAndPunctuation}\\p{InCJKUnifiedIdeographs}{2}+",
						kFirst)), line))
				|| Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
						"^(%1$s\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{2}$",
						kFirst)), line))
				|| Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
						"^(%1$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{3}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}{2}+$",
						kFirst)), line))
				|| Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
						"^(%1$s\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}{4}$",
						kFirst)), line))
				|| Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
						"^(%1$s\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{4}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}+$",
						kFirst)), line)))
				&& Util.groupCount(m2) > 1) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.put(multimap, Util.group(m2, 1), Util.group(m2, 2));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}{2}\\p{InHiragana}+\\p{InCJKUnifiedIdeographs}{2}$",
				kFirst)), line)) && Util.groupCount(m2) > 1 && StringUtils.length(g21 = Util.group(m2, 1)) == 2
				&& StringUtils.countMatches(g22 = Util.group(m2, 2), 'ん') == 2) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
							StringUtils.substring(g22, 0, (indexOf = StringUtils.indexOf(g22, 'ん')) + 1),
							StringUtils.substring(g21, 1, 2),
							StringUtils.substring(g22, indexOf + 1, StringUtils.length(g22))));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}{3}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g22, cpk, cpv, StringUtils.substringAfter(g21, cpk),
					StringUtils.substringAfter(g22, cpv)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}{2})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 5) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(Util.group(m2, 1),
							StringUtils.substringBefore(g26 = Util.group(m2, 6), Util.group(m2, 2)), Util.group(m2, 5),
							StringUtils.substringAfterLast(g26, Util.group(m2, 4))));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs})\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}+$",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& Stream.of(g21 = Util.group(m2, 1), g22 = Util.group(m2, 2), g23 = Util.group(m2, 3))
						.mapToInt(StringUtils::length).distinct().max().orElse(0) == 2) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g21, g23, StringUtils.substring(g21, 0, 1), StringUtils.substring(g23, 0, 1),
							StringUtils.substring(g21, 1, 2), StringUtils.substring(g23, 1, 2), g22, g23,
							StringUtils.substring(g22, 0, 1), StringUtils.substring(g23, 0, 1)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 1 && StringUtils.length(g21 = Util.group(m2, 1)) == 2
				&& (indexOf = StringUtils.indexOf(g22 = Util.group(m2, 2), 'ん')) > 0
				&& indexOf < (countMatches = StringUtils.length(g22)) - 1 && StringUtils.countMatches(g22, 'ん') == 1) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
							StringUtils.substring(g22, 0, indexOf + 1), StringUtils.substring(g21, 1, 2),
							StringUtils.substring(g22, indexOf + 1, countMatches)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana}+)(%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{3}\\p{InHiragana}{2}[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 2) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)),
					StringUtils.substringBefore(g24 = Util.group(m2, 4), g22 = Util.group(m2, 2)));
			//
			for (int j = StringUtils.length(tsbv) - 1; j >= 0; j--) {
				//
				testAndAccept((a, b) -> StringUtils.startsWith(Character.getName(a.charAt(b)), "HIRAGANA LETTER SMALL"),
						tsbv, j, (a, b) -> a.deleteCharAt(b));
				//
			} // for
				//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(Util.group(m2, 1), Util.toString(tsbv),
					Util.group(m2, 3), StringUtils.substringAfter(g24, g22)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.countMatches(Util.group(m2, 2), 'ん') == 2
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2)))) {
			//
			if ((lastIndexOf = StringUtils.lastIndexOf(g22, 'ん')) - (indexOf = StringUtils.indexOf(g22, 'ん')) == 2
					&& lastIndexOf == StringUtils.length(g22) - 1) {
				//
				IntCollectionUtil.addInt(intCollection, i);
				//
				append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)), g21);
				//
				append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)), g22);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
				//
				delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
				//
				delete(tsbv, (length = StringUtils.length(tsbv)) - 2, length);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
				//
				delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
				//
				delete(tsbv, (length = StringUtils.length(tsbv)) - 2, length);
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(cpk, cpv,
								StringUtils.substring(g21, StringUtils.indexOf(g21, cpk) + StringUtils.length(cpk),
										StringUtils.indexOf(g21, cpk) + StringUtils.length(cpk) + 1),
								StringUtils.substring(g22, StringUtils.indexOf(g22, cpv) + StringUtils.length(cpv),
										indexOf - 1)));
				//
			} else if ((lastIndexOf = StringUtils.lastIndexOf(g22, 'ん')) - StringUtils.indexOf(g22, 'ん') == 3
					&& lastIndexOf == StringUtils.length(g22) - 1) {
				//
				IntCollectionUtil.addInt(intCollection, i);
				//
				append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)), g21);
				//
				append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)), g22);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
				//
				delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
				//
				delete(tsbv, (length = StringUtils.length(tsbv)) - 2, length);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 1, length));
				//
				delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
				//
				delete(tsbv, (length = StringUtils.length(tsbv)) - 1, length);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
				//
				delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
				//
				delete(tsbv, (length = StringUtils.length(tsbv)) - 2, length);
				//
				MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
				//
			} else if ((lastIndexOf = StringUtils.lastIndexOf(g22, 'ん')) - StringUtils.indexOf(g22, 'ん') == 4
					&& lastIndexOf == StringUtils.length(g22) - 1) {
				//
				IntCollectionUtil.addInt(intCollection, i);
				//
				append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)), g21);
				//
				append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)), g22);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
				//
				delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
				//
				delete(tsbv, (length = StringUtils.length(tsbv)) - 2, length);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
				//
				delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
				//
				delete(tsbv, (length = StringUtils.length(tsbv)) - 2, length);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
				//
				delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
				//
				delete(tsbv, (length = StringUtils.length(tsbv)) - 2, length);
				//
				MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
				//
			} // if
				//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.length(g21) == 2 && StringUtils.endsWith(g22 = Util.group(m2, 2), "ん")) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
							StringUtils.substring(g22, 0, indexOf = StringUtils.indexOf(g22, 'ん') - 1),
							StringUtils.substring(g21, 1, 2), StringUtils.substring(g22, indexOf)));

			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{3}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}{2}$",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.countMatches(g22 = Util.group(m2, 2), 'ん') == 2
				&& (lastIndexOf = StringUtils.lastIndexOf(g22, 'ん')) == StringUtils.length(g22) - 1
				&& lastIndexOf - StringUtils.indexOf(g22, 'ん') == 2) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.put(multimap, g21 = Util.group(m2, 1), g22);
			//
			append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)), g21);
			//
			append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)), g22);
			//
			MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
					substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
			//
			delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
			//
			delete(tsbv, (length = StringUtils.length(tsbv)) - 2, length);
			//
			MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
					substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
			//
			delete(tsbk, (length = StringUtils.length(tsbk)) - 1, length);
			//
			delete(tsbv, (length = StringUtils.length(tsbv)) - 2, length);
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.countMatches(g22 = Util.group(m2, 2), 'ん') == 2
				&& (lastIndexOf = StringUtils.lastIndexOf(g22, 'ん')) == StringUtils.length(g22) - 1
				&& (indexOf = StringUtils.indexOf(g22, 'ん')) == 1) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g21 = Util.group(m2, 1), g22, StringUtils.substring(g21, 0, 1),
							StringUtils.substring(g22, 0, indexOf + 1),
							StringUtils.substring(g21, (length = StringUtils.length(g21)) - 1, length),
							StringUtils.substring(g22, lastIndexOf - 1)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}+)\\p{Inkatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{Inkatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line))
				&& Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(csk = getCommonSuffix(
						getCommonSuffix(g21 = Util.group(m2, 1), g22 = Util.group(m2, 2)), g23 = Util.group(m2, 3)))
				&& StringUtils.isNotBlank(csv = getCommonSuffix(g12, g24 = Util.group(m2, 4)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g24, csk, csv, g22, g24, g23, g24));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}+)\\p{Inkatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 2
				&& StringUtils.isNotBlank(csk = getCommonSuffix(g21 = Util.group(m2, 1), g22 = Util.group(m2, 2)))
				&& StringUtils.isNotBlank(csv = getCommonSuffix(g12, g23 = Util.group(m2, 3)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g23, StringUtils.substringBefore(g21, csk),
					StringUtils.substringBefore(g23, csv), csk, csv, g22, g23));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{2}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}+[\\p{InHiragana}\\p{InCJKSymbolsAndPunctuation}]+$",
				kFirst)), line))
				&& Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(
						cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1), g23 = Util.group(m2, 3)))
				&& StringUtils.isNotBlank(
						cpv = StringUtils.getCommonPrefix(g22 = Util.group(m2, 2), g24 = Util.group(m2, 4)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g22, cpk, cpv));
			//
			testAndAccept((a, b) -> StringUtils.length(Util.getKey(a)) == 2, Pair.of(g21, g22), Pair.of(cpk, cpv),
					(a, b) -> MultimapUtil.put(multimap, StringUtils.substringAfter(Util.getKey(a), Util.getKey(b)),
							StringUtils.substringAfter(Util.getValue(a), Util.getValue(b))));
			//
			MultimapUtil.put(multimap, g23, g24);
			//
			testAndAccept((a, b) -> {
				//
				final String v = Util.getValue(a);
				//
				return StringUtils.countMatches(v, 'ん') == 1
						&& StringUtils.indexOf(v, 'ん') == StringUtils.length(v) - 1;
				//
			}, Pair.of(g23, g24), cpv, (a, b) -> {
				//
				final String k = Util.getKey(a);
				//
				final int lk = StringUtils.length(k);
				//
				final String v = Util.getValue(a);
				//
				final int iv = StringUtils.indexOf(v, 'ん');
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(StringUtils.substring(k, 1, 2),
								StringUtils.substring(v, StringUtils.length(b), iv - 1),
								StringUtils.substring(k, lk - 1, lk), StringUtils.substring(v, iv - 1)));
				//
			});
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}{4}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}+$",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.countMatches(g22 = Util.group(m2, 2), 'ん') == 1
				&& (indexOf = StringUtils.indexOf(g22, 'ん')) == 1) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g21, g22, cpk, StringUtils.substring(g22, 0, indexOf + 1)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$",
				kFirst)), line)) && Util.groupCount(m2) > 5
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g26 = Util.group(m2, 6)))
				&& StringUtils.length(g25 = Util.group(m2, 5)) == 2
				&& (indexOf = StringUtils.indexOf(g26, "ん")) == StringUtils.length(g26) - 1) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(cpk, cpv, StringUtils.substring(g25, 0, 1),
							StringUtils.substring(g26, StringUtils.indexOf(g26, Util.group(m2, 4)) + 1, indexOf - 1),
							StringUtils.substring(g25, 1, 2), StringUtils.substring(g26, indexOf - 1)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{4})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.countMatches(g22 = Util.group(m2, 2), "ん") == 3) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.put(multimap, g21, g22);
			//
			append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)), g21);
			//
			append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)), g22);
			//
			testAndAccept((a, b) -> StringUtils.length(a) > 0 && StringUtils.length(b) > 1 && b.charAt(1) == 'ん', tsbk,
					tsbv, (a, b) -> {
						//
						MultimapUtil.put(multimap, substring(a, 0, 1), substring(b, 0, 2));
						//
						delete(a, 0, 1);
						//
						delete(b, 0, 2);
						//
					});
			//
			testAndAccept((a, b) -> StringUtils.length(a) > 0 && StringUtils.length(b) > 1 && b.charAt(1) == 'ん', tsbk,
					tsbv, (a, b) -> {
						//
						MultimapUtil.put(multimap, substring(a, 0, 1), substring(b, 0, 2));
						//
						delete(a, 0, 1);
						//
						delete(b, 0, 2);
						//
					});
			//
			if (StringUtils.endsWith(tsbv, "ん")) {
				//
				MultimapUtil.put(multimap, StringUtils.substring(g21, (length = StringUtils.length(g21)) - 1, length),
						StringUtils.substring(g22, (length = StringUtils.length(g22)) - 2, length));
				//
			}
			//
			testAndAccept(
					(a, b, c) -> Boolean.logicalAnd(StringUtils.isNotBlank(Util.getKey(c)),
							StringUtils.isNotBlank(Util.getValue(c))),
					tsbk, tsbv, Pair.of(longestCommonSubstring(g11, Util.toString(tsbk)),
							longestCommonSubstring(g12, Util.toString(tsbv))),
					(a, b, c) -> {
						//
						final String k = Util.getKey(c);
						//
						final String v = Util.getValue(c);
						//
						MultimapUtil.putAll(multimap,
								ImmutableMultimap.of(StringUtils.substringBefore(Util.toString(a), k),
										StringUtils.substringBefore(Util.toString(b), v), k, v,
										StringUtils.substringAfter(Util.toString(a), k),
										StringUtils.substringAfter(Util.toString(b), v)));
						//
					});
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{2})(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$",
				kFirst)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(csk = getCommonSuffix(g11, g23 = Util.group(m2, 3)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g24 = Util.group(m2, 4)))
				&& StringUtils.isNotBlank(csv = getCommonSuffix(g12, g24))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g21, StringUtils.substringBefore(g24, Util.group(m2, 2)), cpk, cpv, csk, csv,
							StringUtils.substringAfter(g21, csk),
							StringUtils.substringBetween(g24, csv, g22 = Util.group(m2, 2))));
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g23, StringUtils.substringAfter(g24, g22),
					StringUtils.substringBefore(g23, csk), StringUtils.substringBetween(g24, g22, csv)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{6})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.countMatches(g22 = Util.group(m2, 2), 'ん') == 4) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.put(multimap, g21, g22);
			//
			append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)), g21);
			//
			append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)), g22);
			//
			testAndAccept((a, b) -> StringUtils.length(a) > 1 && StringUtils.length(b) > 1 && b.charAt(1) == 'ん', tsbk,
					tsbv, (a, b) -> {
						//
						MultimapUtil.put(multimap, substring(a, 0, 1), substring(b, 0, 2));
						//
						delete(a, 0, 1);
						//
						delete(b, 0, 2);
						//
					});
			//
			testAndAccept((a, b) -> StringUtils.length(a) > 1 && StringUtils.length(b) > 1 && b.charAt(1) == 'ん', tsbk,
					tsbv, (a, b) -> {
						//
						MultimapUtil.put(multimap, substring(a, 0, 1), substring(b, 0, 2));
						//
						delete(a, 0, 1);
						//
						delete(b, 0, 2);
						//
					});
			//
			testAndAccept((a, b) -> StringUtils.length(a) > 1 && StringUtils.length(b) > 2 && b.charAt(2) == 'ん', tsbk,
					tsbv, (a, b) -> {
						//
						MultimapUtil.put(multimap, substring(a, 0, 2), substring(b, 0, 3));
						//
						delete(a, 0, 2);
						//
						delete(b, 0, 3);
						//
					});
			//
			MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) > 0 && l > 0 && b.charAt(l - 1) == 'ん';
				//
			}, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
			});
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$",
				kFirst)), line)) && Util.groupCount(m2) > 1 && StringUtils.length(g21 = Util.group(m2, 1)) == 2
				&& StringUtils.countMatches(g22 = Util.group(m2, 2), 'ん') == 1
				&& (indexOf = StringUtils.indexOf(g22, "ん")) == 1) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
							StringUtils.substring(g22, 0, indexOf + 1), StringUtils.substring(g21, 1, 2),
							StringUtils.substring(g22, indexOf + 1)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.countMatches(g22 = Util.group(m2, 2), 'ん') == 2) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.put(multimap, g21, g22);
			//
			append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)), g21);
			//
			append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)), g22);
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) > 0 && l > 0 && b.charAt(l - 1) == 'ん';
				//
			}, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 2, l);
				//
			});
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) > 0 && l > 1 && b.charAt(l - 2) == 'ん';
				//
			}, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 1, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 1, l);
				//
			});
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) > 0 && l > 0 && b.charAt(l - 1) == 'ん';
				//
			}, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 2, l);
				//
			});
			//
			MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{3})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 1) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)),
					Util.group(m2, 3));
			//
			append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)),
					g24 = Util.group(m2, 4));
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) > 0 && l > 0 && b.charAt(l - 1) == 'ん';
				//
			}, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 2, l);
				//
			});
			//
			append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)),
					Util.group(m2, 1));
			//
			tsbv.deleteCharAt(StringUtils.indexOf(g24, Util.group(m2, 2)));
			//
			testAndAccept((a, b) -> StringUtils.length(b) - StringUtils.lastIndexOf(b, "ん") > 2, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 2, l);
				//
			});
			//
			testAndAccept((a, b) -> StringUtils.length(b) - StringUtils.lastIndexOf(b, "ん") == 1, tsbk, tsbv,
					(a, b) -> {
						//
						int l;
						//
						MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
								substring(b, (l = StringUtils.length(b)) - 2, l));
						//
						delete(a, (l = StringUtils.length(a)) - 1, l);
						//
						delete(b, (l = StringUtils.length(b)) - 2, l);
						//
					});
			//
			testAndAccept((a, b) -> StringUtils.length(b) - StringUtils.lastIndexOf(b, "ん") == 1, tsbk, tsbv,
					(a, b) -> {
						//
						int l;
						//
						MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
								substring(b, (l = StringUtils.length(b)) - 2, l));
						//
						delete(a, (l = StringUtils.length(a)) - 1, l);
						//
						delete(b, (l = StringUtils.length(b)) - 2, l);
						//
					});
			//
			MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(の)(\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 1) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.put(multimap, Util.group(m2, 1),
					StringUtils.substringBefore(g24 = Util.group(m2, 4), g22 = Util.group(m2, 2)));
			//
			append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)),
					Util.group(m2, 3));
			//
			append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)),
					StringUtils.substringAfter(g24, g22));
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) > 0 && l > 0 && b.charAt(l - 1) == 'ん';
				//
			}, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 2, l);
				//
			});
			//
			MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 3) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(Util.group(m2, 1),
							StringUtils.substringBefore(g24 = Util.group(m2, 4), g22 = Util.group(m2, 2)),
							Util.group(m2, 3), StringUtils.substringAfter(g24, g22)));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{4})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}{3}\\p{InHiragana}+\\p{InCJKUnifiedIdeographs}{2}$",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g22, cpk, cpv));
			//
			append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)),
					StringUtils.substringAfter(g21, cpk));
			//
			append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)),
					StringUtils.substringAfter(g22, cpv));
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) > 0 && l > 0 && b.charAt(l - 1) == 'ん';
				//
			}, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 2, l);
				//
			});
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) > 0 && l > 0 && b.charAt(l - 1) == 'ん';
				//
			}, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 2, l);
				//
			});
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) > 0 && l > 0 && b.charAt(l - 1) == 'ん';
				//
			}, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 2, l);
				//
			});
			//
			MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$",
				kFirst)), line)) && Util.groupCount(m2) > 1
				&& StringUtils.countMatches(g22 = Util.group(m2, 2), "ん") > 1) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21 = Util.group(m2, 1), g22));
			//
			append(TextStringBuilderUtil.clear(tsbk = ObjectUtils.getIfNull(tsbk, TextStringBuilder::new)), g21);
			//
			append(TextStringBuilderUtil.clear(tsbv = ObjectUtils.getIfNull(tsbv, TextStringBuilder::new)), g22);
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) > 0 && l > 0 && b.charAt(l - 1) == 'ん';
				//
			}, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 2, l);
				//
			});
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) > 0 && l > 0 && b.charAt(l - 1) == 'ん';
				//
			}, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 2, l);
				//
			});
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) > 1 && l > 2 && b.charAt(l - 3) == 'ん';
				//
			}, tsbk, tsbv, (a, b) -> {
				//
				int l;
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 2, l);
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				delete(a, (l = StringUtils.length(a)) - 1, l);
				//
				delete(b, (l = StringUtils.length(b)) - 2, l);
				//
				MultimapUtil.put(multimap, Util.toString(a), Util.toString(b));
				//
			});
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{3})(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$",
				kFirst)), line)) && Util.groupCount(m2) > 5
				&& StringUtils.isNotBlank(lcsk = longestCommonSubstring(g21 = Util.group(m2, 1), Util.group(m2, 3)))
				&& StringUtils.isNotBlank(lcsv = longestCommonSubstring(
						StringUtils.substringBefore(g26 = Util.group(m2, 6), g22 = Util.group(m2, 2)),
						StringUtils.substringBetween(g26, g22, Util.group(m2, 4))))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, StringUtils.substringBefore(g26, g22),
					StringUtils.substringBefore(g21, lcsk), StringUtils.substringBefore(g26, lcsv), lcsk, lcsv));
			//
			testAndAccept((a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				return StringUtils.length(a) == 1 && l > 0 && Objects.equals(StringUtils.substring(b, l - 1, l), "ん");
				//
			}, Util.group(m2, 5), g26, (a, b) -> {
				//
				final int l = StringUtils.length(b);
				//
				MultimapUtil.put(multimap, a, StringUtils.substring(b, l - 2, l));
				//
			});
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana}{3})(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$",
				kFirst)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g24 = Util.group(m2, 4)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.put(multimap, cpk, cpv);
			//
			if ((length = StringUtils.length(g24)) > 0
					&& Objects.equals(StringUtils.substring(g24, length - 1, length), "ん")
					&& StringUtils.length(g23 = Util.group(m2, 3)) == 2) {
				//
				MultimapUtil
						.putAll(multimap,
								ImmutableMultimap.of(StringUtils.substring(g23, 0, 1),
										StringUtils.substring(g24,
												StringUtils.indexOf(g24, g22 = Util.group(m2, 2))
														+ StringUtils.length(g22),
												StringUtils.length(g24) - 2),
										StringUtils.substring(g23, 1, 2),
										StringUtils.substring(g24, length - 2, length)));
				//
			} // if
				//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	private static <T> void testAndAccept(@Nullable final Predicate<T> predicate, final T value,
			@Nullable final Consumer<T> consumer) {
		if (predicate != null && predicate.test(value) && consumer != null) {
			consumer.accept(value);
		}
	}

	@Nullable
	private static TextStringBuilder delete(@Nullable final TextStringBuilder instance, final int startIndex,
			final int endIndex) {
		return instance != null ? instance.delete(startIndex, endIndex) : instance;
	}

	@Nullable
	private static TextStringBuilder append(@Nullable final TextStringBuilder instance, final String str) {
		//
		try {
			//
			if (instance == null || Narcissus.getField(str, String.class.getDeclaredField("value")) == null) {
				//
				return instance;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		return instance.append(str);
		//
	}

	@Nullable
	private static String substring(@Nullable final TextStringBuilder instance, final int startIndex,
			final int endIndex) {
		//
		try {
			//
			if (instance == null
					|| Narcissus.getField(instance, TextStringBuilder.class.getDeclaredField("buffer")) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		return instance.substring(startIndex, endIndex);
		//
	}

	private static <T, U, V> void testAndAccept(@Nullable final TriPredicate<T, U, V> instance, final T t, final U u,
			final V v, @Nullable final TriConsumer<T, U, V> consumer) {
		if (instance != null && instance.test(t, u, v) && consumer != null) {
			consumer.accept(t, u, v);
		} // if
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, @Nullable final T t, final U u,
			final BiConsumer<T, U> consumer) {
		if (Util.test(instance, t, u)) {
			Util.accept(consumer, t, u);
		} // if
	}

	private static <K, V> Quartet<K, V, K, V> toQuartet(final Iterable<Entry<K, V>> entries) {
		//
		if (IterableUtils.size(entries) != 2) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		final Entry<K, V> e1 = IterableUtils.get(entries, 0);
		//
		final Entry<K, V> e2 = IterableUtils.get(entries, 1);
		//
		return Quartet.with(Util.getKey(e1), Util.getValue(e1), Util.getKey(e2), Util.getValue(e2));
		//
	}

	private static <T> void testAndAccept(@Nullable final ObjIntPredicate<T> predicate, final T a, final int b,
			@Nullable final ObjIntConsumer<T> consumer) {
		if (predicate != null && predicate.test(a, b)) {
			Util.accept(consumer, a, b);
		}
	}

	private static String longestCommonSubstring(final String a, final String b) {
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

	// http://www.java2s.com/example/java-utility-method/collection-element-get/getcommonsuffix-collection-string-c-85a78.html

	private static String getCommonSuffix(@Nullable final String s1, @Nullable final String s2) {
		//
		if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
			//
			return "";
			//
		} // if
			//
		int i = 0;
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