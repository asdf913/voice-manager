package org.springframework.beans.factory;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.function.TriFunctionUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.d2ab.collection.ints.IntCollection;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntIterableUtil;
import org.d2ab.collection.ints.IntList;
import org.d2ab.function.ObjIntPredicate;
import org.javatuples.Quartet;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import it.unimi.dsi.fastutil.PairUtil;
import it.unimi.dsi.fastutil.ints.IntObjectPair;

public class OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

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

		final List<TriFunction<PatternMap, IntObjectPair<String>, Iterable<String>, Entry<Multimap<String, String>, IntCollection>>> functions = Arrays
				.asList(OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection1,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection2,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection3,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection4);
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
			} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
					"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
					line)) && Util.groupCount(m2) > 1
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
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines,
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
			final PatternMap patternMap, final IntObjectPair<String> iop1, final IntObjectPair<String> iop2,
			final Quartet<String, String, String, String> quartet) {
		//
		IntCollection intCollection = null;
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m2;
		//
		final String g11 = IValue0Util.getValue0(quartet);
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
		if (StringUtils.startsWith(line, g11) && Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
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
			if (StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21))
					&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g14, g22))) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(cpk, cpv, StringUtils.substringAfter(g21, cpk),
								StringUtils.substringAfter(g22, cpv)));
				//
			} // if
				//
		} else if (StringUtils.isNotBlank(StringUtils.getCommonPrefix(line, g13)) && Util.matches(m2 = Util.matcher(
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

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
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