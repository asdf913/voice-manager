package org.springframework.beans.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import org.d2ab.function.CharPredicate;
import org.d2ab.function.ObjIntPredicate;
import org.d2ab.function.ObjIntPredicateUtil;
import org.d2ab.function.QuaternaryFunction;
import org.d2ab.function.ToCharFunction;
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
import org.meeuw.functional.TriPredicateUtil;

import com.google.common.base.Strings;
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

	private static final String HIRAGANA_LETTER_SMALL = "HIRAGANA LETTER SMALL";

	private static final String LENGTH = "length";

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
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection12,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection13,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection14,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection15,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection16,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection17,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection18,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection19,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection20,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection21,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection22,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection23,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection24,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection25,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection26);
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

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection1(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines) {
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
				if (keyIntEquals(iop, j)) {
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
									csk = Strings.commonSuffix(Util.group(m1, 1), g21),
									csv = Strings.commonSuffix(g12, g22), StringUtils.substringBefore(g21, csk),
									StringUtils.substringBefore(g22, csv)));
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

	private static boolean keyIntEquals(@Nullable final IntObjectPair<?> iop, final int i) {
		return iop != null && iop.keyInt() == i;
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
						&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g21 = Util.group(m2, 1), s1))
						&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g22 = Util.group(m2, 2), s2))) {
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
			if (keyIntEquals(iop, i)) {
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
			if (keyIntEquals(iop, i)) {
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
			} else if (Util.and(StringUtils.isNotBlank(cpk),
					StringUtils.isNotBlank(csk = Strings.commonSuffix(g13, g21)),
					StringUtils.isNotBlank(csv = Strings.commonSuffix(g14, g22)))) {
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
			if (keyIntEquals(iop, i)) {
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
			if (keyIntEquals(iop, i)) {
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
						StringUtils
								.isNotBlank(csk = Strings.commonSuffix(k1 = Util.getKey(e1 = IterableUtils.get(es4, i)),
										k2 = Util.getKey(e2 = IterableUtils.get(es4, j)))),
						StringUtils.isNotBlank(
								csv = Strings.commonSuffix(v1 = Util.getValue(e1), v2 = Util.getValue(e2))))) {
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
			if (keyIntEquals(iop, i)) {
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
			if (!keyIntEquals(iop, i)
					&& Util.matches(m2 = Util.matcher(PATTERN_KANJI_HIRAGANA, IterableUtils.get(lines, i)))
					&& Util.groupCount(m2) > 1
					&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g21 = Util.group(m2, 1), g12))
					&& StringUtils.length(g21) == 2
					&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g22 = Util.group(m2, 2), hiragana))) {
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
				if (keyIntEquals(iop, i)) {
					//
					continue;
					//
				} // if
					//
				if ((l = StringUtils.length(g11)) == 2
						&& StringUtils.contains(line = IterableUtils.get(lines, i), StringUtils.substring(g11, 1, l))
						&& Util.matches(m2 = Util.matcher(PATTERN_KANJI_HIRAGANA, line)) && Util.groupCount(m2) > 1
						&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g21 = Util.group(m2, 1), g11))
						&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g22 = Util.group(m2, 2), g12))
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
			if (keyIntEquals(iop, i)) {
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
				if (keyIntEquals(iop, j) || !StringUtils.contains(line = IterableUtils.get(lines, j), kLast)
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
				if (Boolean.logicalAnd(StringUtils.isNotBlank(csk = Strings.commonSuffix(g21, k)),
						StringUtils.isNotBlank(csv = Strings.commonSuffix(g22, v = Util.getValue(e))))) {
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
								StringUtils.substringBefore(g22, csv = Strings.commonSuffix(g12, g22)), kLast, csv,
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
			if (keyIntEquals(iop, i)) {
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
				} else if (Boolean.logicalAnd(StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g21)),
						StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g22)))) {
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

		private Map<Object, Object> intMap = null;

		private Map<Object, Object> getMap() {
			return map = ObjectUtils.getIfNull(map, LinkedHashMap::new);
		}

		private Map<Object, Object> getIntMap() {
			return intMap = ObjectUtils.getIfNull(intMap, LinkedHashMap::new);
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
				} else if (Objects.equals(name, "put") && args != null && args.length > 1) {
					//
					Util.put(getMap(), args[0], args[1]);
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
			final IValue0<Object> iValue0 = invokeIntMap(proxy, name, args);
			//
			if (iValue0 != null) {
				//
				return IValue0Util.getValue0(iValue0);
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

		@Nullable
		private IValue0<Object> invokeIntMap(final Object proxy, final String name, @Nullable final Object[] args)
				throws Throwable {
			//
			if (proxy instanceof IntMap) {
				//
				if (Objects.equals(name, "get") && args != null && args.length > 0) {
					//
					final Object arg = args[0];
					//
					if (!Util.containsKey(getIntMap(), arg)) {
						//
						throw new Throwable(Util.toString(arg));
						//
					} // if
						//
					return Unit.with(MapUtils.getObject(getIntMap(), args[0]));
					//
				} else if (Objects.equals(name, "put") && args != null && args.length > 1) {
					//
					Util.put(getIntMap(), args[0], args[1]);
					//
					return Unit.with(null);
					//
				} // if
					//
			} // if
				//
			return null;
			//
		}

	}

	private static interface ObjObjIntObjObjFunction<A, B, D, E, R> {

		R apply(final A a, final B b, final int c, final D d, final E e);

		@Nullable
		private static <A, B, D, E, R> R apply(@Nullable final ObjObjIntObjObjFunction<A, B, D, E, R> instance,
				final A a, final B b, final int c, final D d, final E e) {
			return instance != null ? instance.apply(a, b, c, d, e) : null;
		}

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
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A2,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A3,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A4,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A5,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A6,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A7,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A8,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A9,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A10,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A11,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A12,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A13,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection11A14);
		//
		Multimap<String, String> mm = null;
		//
		IntCollection ic = null;
		//
		for (int i = 0; i < IterableUtils.size(lines) && Util.iterator(functions) != null; i++) {
			//
			for (final ObjObjIntObjObjFunction<PatternMap, IntObjectPair<String>, String, Map<String, String>, Entry<Multimap<String, String>, IntCollection>> function : functions) {
				//
				if (Util.and(
						(entry = ObjObjIntObjObjFunction.apply(function, patternMap, iop, i,
								IterableUtils.get(lines, i), map)) != null,
						!MultimapUtil.isEmpty(mm = Util.getKey(entry)),
						!IterableUtils.isEmpty(ic = Util.getValue(entry)))) {
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
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g21))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2)))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g22))) {
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
					StringUtils.substringAfter(g21, cpk), StringUtils.substringAfter(g22, cpv),
					(a, b) -> MultimapUtil.put(multimap, a, b));
			//
		} // if
			//
		String g23, g24, lcsk, lcsv, sav;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
		} //
			//
		int indexOf;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
				"^(%1$s%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 1 && StringUtils.length(g21 = Util.group(m2, 1)) == 2
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, Util.group(m2, 1)))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g22 = Util.group(m2, 2)))) {
			//
			testAndAccept((a, b) -> !IntIterableUtil.containsInt(a, b), intCollection, i,
					(a, b) -> IntCollectionUtil.addInt(intCollection, i));
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(StringUtils.substringBefore(g21, csk),
					StringUtils.substringBefore(g22, csv), csk, csv));
			//
		} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}\\p{InKatakana}\\p{InHiragana}]+\\p{InBasicLatin}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2)))
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g21))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g22))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g21, g22, StringUtils.substringBefore(g21, csk),
							StringUtils.substringBefore(g22, csv), g23 = Util.group(m2, 3), g24 = Util.group(m2, 4),
							StringUtils.substringBetween(g21, cpk, csk), StringUtils.substringBetween(g24, cpv, csv),
							StringUtils.substringAfter(g23, csk), StringUtils.substringAfter(g24, csv)));
			//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A2(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
		String g23, g24, cpk, csk, cpv, csv;
		//
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana}{3})(\\p{InCJKUnifiedIdeographs}+%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g24 = Util.group(m2, 4)))
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g23 = Util.group(m2, 3)))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g24))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(cpk, cpv, StringUtils.substringBefore(g23, csk),
					StringUtils.substringBetween(g24, Util.group(m2, 2), csv), csk, csv));
			//
		} // if
			//
		String g21;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs})(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g24 = Util.group(m2, 4)))
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g23 = Util.group(m2, 3)))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g24))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(cpk, cpv, StringUtils.substringBefore(g23, csk),
					StringUtils.substringBetween(g24, Util.group(m2, 2), csv), csk, csv,
					StringUtils.substringAfter(g21, cpk), StringUtils.substringBetween(g24, cpv, Util.group(m2, 2))));
			//
		} // if
			//
		String g22, lcsv, sak, sav;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
				"^(%1$s)(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, Util.group(m2, 3)))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, Util.group(m2, 4)))) {
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
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A3(
			final PatternMap patternMap, final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
		String g23, g24, cpk, csk, cpv, csv;
		//
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana}{3})(\\p{InCJKUnifiedIdeographs}+%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g24 = Util.group(m2, 4)))
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g23 = Util.group(m2, 3)))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g24))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(cpk, cpv, StringUtils.substringBefore(g23, csk),
					StringUtils.substringBetween(g24, Util.group(m2, 2), csv), csk, csv));
			//
		} // if
			//
		String g21, g22, g25, g26, g27, g28;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InBasicLatin}+(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util
						.groupCount(m2) > 7
				&& StringUtils
						.isNotBlank(
								csk = Strings
										.commonSuffix(
												Strings.commonSuffix(Strings.commonSuffix(g21 = Util.group(m2, 1),
														g23 = Util.group(m2, 3)), g25 = Util.group(m2, 5)),
												g27 = Util.group(m2, 7)))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(
						Strings.commonSuffix(Strings.commonSuffix(g22 = Util.group(m2, 2), g24 = Util.group(m2, 4)),
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
				"^(%1$s\\p{InCJKUnifiedIdeographs}%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 2
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g21)) && StringUtils.isNotBlank(csv = Strings
						.commonSuffix(Strings.commonSuffix(g12, g22 = Util.group(m2, 2)), g23 = Util.group(m2, 3)))) {
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
		} // if
			//
		int indexOf;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A4(
			final PatternMap patternMap, final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		String g21, g22, g23, g26;
		//
		int indexOf;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
		} // if
			//
		final int countMatches;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
		} // if
			//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
		} // if
			//
		final int lastIndexOf, length;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
		} // if
			//
		final String g24;
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		String csk, csv;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}+)\\p{Inkatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{Inkatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line))
				&& Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(
						Strings.commonSuffix(g21 = Util.group(m2, 1), g22 = Util.group(m2, 2)),
						g23 = Util.group(m2, 3)))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g24 = Util.group(m2, 4)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g24, csk, csv, g22, g24, g23, g24));
			//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A5(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		String g21, g22, g23, csk, csv;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}+)\\p{Inkatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 2
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g21 = Util.group(m2, 1), g22 = Util.group(m2, 2)))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g23 = Util.group(m2, 3)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(g21, g23, StringUtils.substringBefore(g21, csk),
					StringUtils.substringBefore(g23, csv), csk, csv, g22, g23));
			//
		} // if
			//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		String cpk;
		//
		int indexOf;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
		} // if
			//
		final String g25;
		//
		String g26, cpv;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
		} // if
			//
		String g24;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{2})(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$",
				kFirst)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g23 = Util.group(m2, 3)))
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g24 = Util.group(m2, 4)))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g24))) {
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
		} // if
			//
		final String lcsk, lcsv;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A6(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		String g22, g23, g24, cpk, cpv;
		//
		final int length;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
		final String kLast = MapUtils.getObject(map, "kLast");
		//
		TextStringBuilder tsbv = null;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
				testAndAccept((a, b) -> StringUtils.startsWith(Character.getName(a.charAt(b)), HIRAGANA_LETTER_SMALL),
						tsbv, j, (a, b) -> a.deleteCharAt(b));
				//
			} // for
				//
			MultimapUtil.putAll(multimap, ImmutableMultimap.of(Util.group(m2, 1), Util.toString(tsbv),
					Util.group(m2, 3), StringUtils.substringAfter(g24, g22)));
			//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A7(
			final PatternMap patternMap, final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		String g22, g23, g24, csk, csv;
		//
		final String kLast = MapUtils.getObject(map, "kLast");
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}{2}%2$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst, kLast)), line)) && Util.groupCount(m2) > 3
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, Util.group(m2, 3)))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g24 = Util.group(m2, 4)))) {
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
		} // if
			//
		final String cpk, cpv;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line)) && Util.groupCount(m2) > 3) {
			//
			if (Boolean.logicalAnd(StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g23 = Util.group(m2, 3))),
					StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g24 = Util.group(m2, 4))))) {
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
		} // if
			//
		final int lastIndexOf;
		//
		int length;
		//
		final String g21;
		//
		TextStringBuilder tsbk = null, tsbv = null;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
			deleteLastCharacter(tsbk);
			//
			deleteLast2Characters(tsbv);
			//
			MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
					substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
			//
			deleteLastCharacter(tsbk);
			//
			deleteLast2Characters(tsbv);
			//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A8(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		String g22, g24;
		//
		TextStringBuilder tsbk = null, tsbv = null;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
				deleteLastCharacter(a);
				//
				deleteLast2Characters(b);
				//
			});
			//
			MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
			//
		} // if
			//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		final String csk, csv;
		//
		String g21, cpk, cpv;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				line)) && Util.groupCount(m2) > 1
				&& StringUtils.length(cpk = StringUtils.getCommonPrefix(g11, g21 = Util.group(m2, 1))) == 2
				&& StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			if (StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g21))) {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(g21, g22, cpk, cpv, StringUtils.substringBetween(g21, cpk, csk),
								StringUtils.substringBetween(g22, cpv, csv = Strings.commonSuffix(g12, g22)), csk,
								csv));
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
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A9(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		String g21, g22, cpk, cpv;
		//
		TextStringBuilder tsbk = null, tsbv = null;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
				deleteLastCharacter(a);
				//
				deleteLast2Characters(b);
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
				deleteLastCharacter(a);
				//
				deleteLast2Characters(b);
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
				deleteLastCharacter(a);
				//
				deleteLast2Characters(b);
				//
			});
			//
			MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
			//
		} // if
			//
		final String g23, g24;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
			testAndAccept((a, b) -> StringUtils.endsWith(a, "ん"), tsbv, Pair.of(g21, g22), (a, b) -> {
				//
				final String k = Util.getKey(b);
				//
				final String v = Util.getValue(b);
				//
				int l;
				//
				MultimapUtil.put(multimap, StringUtils.substring(k, (l = StringUtils.length(k)) - 1, l),
						StringUtils.substring(v, (l = StringUtils.length(v)) - 2, l));
				//
			});
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
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A10(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		String g21, g22;
		//
		TextStringBuilder tsbk = null, tsbv = null;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
		} // if
			//
		final String g24;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
				deleteLastCharacter(a);
				//
				deleteLast2Characters(b);
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
				deleteLastCharacter(a);
				//
				deleteLast2Characters(b);
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
						deleteLastCharacter(a);
						//
						deleteLast2Characters(b);
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
						deleteLastCharacter(a);
						//
						deleteLast2Characters(b);
						//
					});
			//
			MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
			//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A11(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		String g21, g22, cpk, cpv;
		//
		TextStringBuilder tsbk = null, tsbv = null;
		//
		int lastIndexOf, indexOf, length;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line))
				&& Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(
						cpk = StringUtils.getCommonPrefix(MapUtils.getObject(map, "g11"), g21 = Util.group(m2, 1)))
				&& StringUtils.countMatches(Util.group(m2, 2), 'ん') == 2 && StringUtils.isNotBlank(
						cpv = StringUtils.getCommonPrefix(MapUtils.getObject(map, "g12"), g22 = Util.group(m2, 2)))) {
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
				deleteLastCharacter(tsbk);
				//
				deleteLast2Characters(tsbv);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
				//
				deleteLastCharacter(tsbk);
				//
				deleteLast2Characters(tsbv);
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
				deleteLastCharacter(tsbk);
				//
				deleteLast2Characters(tsbv);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 1, length));
				//
				deleteLastCharacter(tsbk);
				//
				deleteLastCharacter(tsbv);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
				//
				deleteLastCharacter(tsbk);
				//
				deleteLast2Characters(tsbv);
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
				deleteLastCharacter(tsbk);
				//
				deleteLast2Characters(tsbv);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
				//
				deleteLastCharacter(tsbk);
				//
				deleteLast2Characters(tsbv);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
				//
				deleteLastCharacter(tsbk);
				//
				deleteLast2Characters(tsbv);
				//
				MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
				//
			} // if
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
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A12(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		String g21, g22;
		//
		TextStringBuilder tsbk = null, tsbv = null;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				kFirst)), line))
				&& Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(
						StringUtils.getCommonPrefix(MapUtils.getObject(map, "g11"), g21 = Util.group(m2, 1)))
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
				deleteLastCharacter(a);
				//
				deleteLast2Characters(b);
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
				deleteLastCharacter(a);
				//
				deleteLastCharacter(b);
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
				deleteLastCharacter(a);
				//
				deleteLast2Characters(b);
				//
			});
			//
			MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
			//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A13(
			final PatternMap patternMap, final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		Matcher m2;
		//
		final String kFirst = MapUtils.getObject(map, "kFirst");
		//
		final String g21;
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)), StringUtils.equals(line, StringUtils.trim(PairUtil.right(iop))))
				|| !Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
						"^(%1$s\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
						kFirst)), line))
				|| Util.groupCount(m2) <= 1 || StringUtils.length(g21 = Util.group(m2, 1)) != 3) {
			//
			return null;
			//
		} // if
			//
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		String g22, cpk, cpv, csk, csv;
		//
		TextStringBuilder tsbk = null, tsbv = null;
		//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		int indexOf;
		//
		if (Util.and(StringUtils.isNotBlank(cpk = StringUtils.getCommonPrefix(g11, g21)),
				StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g21)),
				StringUtils.isNotBlank(cpv = StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2))),
				StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g22)))) {
			//
			IntCollectionUtil.addInt(intCollection, i);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(g11, g12, cpk, cpv, StringUtils.substringBetween(g21, cpk, csk),
							StringUtils.substringBetween(g22, cpv, csv), csk, csv,
							StringUtils.substringBetween(g11, cpk, csk), StringUtils.substringBetween(g12, cpv, csv)));
			//
		} else if (Util.and(StringUtils.isNotBlank(cpk), StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g21)),
				StringUtils.isBlank(StringUtils.getCommonPrefix(g12, g22 = Util.group(m2, 2))),
				StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g22)))) {
			//
			int countMatches;
			//
			if (StringUtils.length(g21) == (countMatches = StringUtils.countMatches(g22, 'ん'))) {
				//
				IntCollectionUtil.addInt(intCollection, i);
				//
				MultimapUtil.put(multimap, g21, g22);
				//
				MultimapUtil.putAll(multimap, toMultimap11A13(countMatches, Pair.of(g21, g22)));
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
					MultimapUtil.putAll(multimap, ImmutableMultimap.of(StringUtils.substring(g21, 0, 1),
							StringUtils.substring(g22, 0, indexOf + 1), StringUtils.substring(g21, 1, 2), StringUtils
									.substring(g22, indexOf + 1, StringUtils.length(g22) - StringUtils.length(csv))));
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
			int lastIndexOf;
			//
			lastIndexOf = StringUtils.lastIndexOf(tsbv, 'ん');
			//
			testAndAccept((k, v) -> StringUtils.lastIndexOf(v, 'ん') == StringUtils.length(v) - 1, tsbk, tsbv,
					(k, v) -> {
						//
						int lk = StringUtils.length(k);
						//
						final int liov = StringUtils.lastIndexOf(v, 'ん');
						//
						MultimapUtil.put(multimap, substring(k, lk - 1, lk),
								substring(v, liov - 1, StringUtils.length(v)));
						//
						deleteLastCharacter(k);
						//
						delete(v, liov - 1, StringUtils.length(v));
						//
					});
			//
			if ((indexOf = StringUtils.indexOf(tsbv, 'ん')) > 0) {
				//
				testAndAccept((a, b) -> b == 2, Pair.of(tsbk, tsbv), lastIndexOf - indexOf, (a, b) -> {
					//
					final TextStringBuilder k = Util.getKey(a);
					//
					final TextStringBuilder v = Util.getValue(a);
					//
					int l;
					//
					MultimapUtil.put(multimap, substring(k, (l = StringUtils.length(k)) - 1, l),
							substring(v, indexOf - 1, StringUtils.length(v)));
					//
					deleteLastCharacter(k);
					//
					testAndAccept((x, y) -> StringUtils.length(x) == 1, k, v, (x, y) -> {
						//
						final int ly = StringUtils.length(y);
						//
						delete(y, ly - 2, ly);
						//
					});
					//
					MultimapUtil.put(multimap, Util.toString(k), Util.toString(v));
					//
				});
				//
				testAndAccept((a, b) -> b == 3, Pair.of(tsbk, tsbv), lastIndexOf - indexOf, (a, b) -> {
					//
					final TextStringBuilder k = Util.getKey(a);
					//
					final TextStringBuilder v = Util.getValue(a);
					//
					int l;
					//
					MultimapUtil.put(multimap, substring(k, (l = StringUtils.length(k)) - 1, l),
							substring(v, (l = StringUtils.length(v)) - 1, l));
					//
					deleteLastCharacter(k);
					//
					deleteLastCharacter(v);
					//
					MultimapUtil.put(multimap, Util.toString(k), Util.toString(v));
					//
				});
				//
			} // if
				//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap11A13(final int countMatches, final Entry<String, String> entry) {
		//
		Multimap<String, String> multimap = null;
		//
		final String g21 = Util.getKey(entry);
		//
		final String g22 = Util.getValue(entry);
		//
		for (int i = 0; i < countMatches; i++) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					StringUtils.substring(g21, i, i + 1), StringUtils.substring(g22, i * 2, i * 2 + 2));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection11A14(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final int i, final String line,
			final Map<String, String> map) {
		//
		if (Boolean.logicalOr((keyIntEquals(iop, i)),
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
		String g21, g22;
		//
		TextStringBuilder tsbk = null, tsbv = null;
		//
		final IntCollection intCollection = IntList.create();
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
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
				deleteLastCharacter(a);
				//
				deleteLast2Characters(b);
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
				deleteLastCharacter(a);
				//
				deleteLast2Characters(b);
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
				deleteLastCharacter(a);
				//
				deleteLast2Characters(b);
				//
				MultimapUtil.put(multimap, substring(a, (l = StringUtils.length(a)) - 1, l),
						substring(b, (l = StringUtils.length(b)) - 2, l));
				//
				deleteLastCharacter(a);
				//
				deleteLast2Characters(b);
				//
				MultimapUtil.put(multimap, Util.toString(a), Util.toString(b));
				//
			});
			//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection12(
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
				"^(\\p{InCJKUnifiedIdeographs}{4})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(right));
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 1) {
			//
			return null;
			//
		} // if
			//
		final IntCollection intCollection = createIntCollection(iop);
		//
		final String g11 = Util.group(m1, 1);
		//
		final String g12 = Util.group(m1, 2);
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12));
		//
		// A
		//
		MultimapUtil.putAll(multimap, toMultimap12A(g11, g12));
		//
		Matcher m2;
		//
		String cpk, cpv, g22;
		//
		// B
		//
		Entry<Multimap<String, String>, IntCollection> mi = toMultimapAndIntCollection12B(patternMap, iop, lines,
				Pair.of(g11, g12));
		//
		MultimapUtil.putAll(multimap, Util.getKey(mi));
		//
		IntCollectionUtil.addAllInts(intCollection, Util.getValue(mi));
		//
		// C
		//
		MultimapUtil.putAll(multimap, Util.getKey(mi = toMultimapAndIntCollection12C(lines, g11)));
		//
		IntCollectionUtil.addAllInts(intCollection, Util.getValue(mi));
		//
		final Entry<String, String> entry = testAndApply(x -> IterableUtils.size(x) == 1,
				Util.toList(Util.filter(StreamSupport.stream(Util.spliterator(MultimapUtil.entries(multimap)), false),
						x -> StringUtils.length(Util.getKey(x)) == 2)),
				x -> IterableUtils.get(x, 0), null);
		//
		String g21, key, value;
		//
		for (int i = 0; i < IterableUtils.size(lines) && entry != null; i++) {
			//
			if (Util.matches(m2 = Util.matcher(PATTERN_KANJI_HIRAGANA, IterableUtils.get(lines, i)))
					&& Util.groupCount(m2) > 1
					&& StringUtils.isNotBlank(
							cpk = StringUtils.getCommonPrefix(g21 = Util.group(m2, 1), key = Util.getKey(entry)))
					&& !Objects.equals(g21, key) && StringUtils.length(g21) == 2
					&& StringUtils.isNotBlank(
							cpv = StringUtils.getCommonPrefix(g22 = Util.group(m2, 2), value = Util.getValue(entry)))
					&& !Objects.equals(g22, value)) {
				//
				IntCollectionUtil.addInt(intCollection, i);
				//
				MultimapUtil.putAll(multimap, ImmutableMultimap.of(cpk, cpv, StringUtils.substringAfter(g21, cpk),
						StringUtils.substringAfter(g22, cpv)));
				//
			} // if
				//
		} // for
			//
		Util.forEach(
				Arrays.asList(Triplet.with("寸", "ずん", "すん"), Triplet.with("高", "だか", "たか"),
						Triplet.with("絣", "がすり", "かすり"), Triplet.with("縞", "じま", "しま"), Triplet.with("木", "ぎ", "き")),
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
		final Multimap<String, String> mm = LinkedHashMultimap
				.create(ImmutableMultimap.of("祝", "いわい", "唐", "う", "唐", "からくさ", "大", "う", "大", "き"));
		//
		MultimapUtil.putAll(mm, ImmutableMultimap.of("大", "ま", "大", "ょう", "持", "もち", "間", "ょう", "縫", "ぬい"));
		//
		MultimapUtil.putAll(mm, ImmutableMultimap.of("蛉", "ぼ", "卍", "まん", "鳳", "おう", "珠", "じゅ"));
		//
		Util.forEach(IterableUtils.toList(MultimapUtil.entries(mm)),
				x -> testAndAccept(
						(a, b) -> b != null && MultimapUtil.containsEntry(multimap, Util.getKey(b), Util.getValue(b)),
						multimap, x, (a, b) -> MultimapUtil.remove(a, Util.getKey(b), Util.getValue(b))));
		//
		return Pair.of(multimap, intCollection);
		//
	}

	private static Multimap<String, String> toMultimap12A(final String g1, final String g2) {
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		final int lastIndexOf = StringUtils.lastIndexOf(g2, 'ん');
		//
		final int indexOf = StringUtils.indexOf(g2, 'ん');
		//
		final int lk = StringUtils.length(g1);
		//
		if (Boolean.logicalAnd(lk == 4, StringUtils.countMatches(g2, 'ん') == 2)) {
			//
			final int lv = StringUtils.length(g2);
			//
			testAndRun(lastIndexOf - indexOf == 2, () -> {
				//
				testAndRun(lastIndexOf == lv - 1,
						() -> MultimapUtil.putAll(multimap, ImmutableMultimap.of(StringUtils.substring(g1, 0, lk - 2),
								StringUtils.substring(g2, 0, indexOf - 1), StringUtils.substring(g1, lk - 2, lk - 1),
								StringUtils.substring(g2, indexOf - 1, indexOf + 1),
								StringUtils.substring(g1, lk - 1, lk),
								StringUtils.substring(g2, lastIndexOf - 1, lastIndexOf + 1))));
				//
				testAndRun(indexOf == 1,
						() -> MultimapUtil.putAll(multimap,
								ImmutableMultimap.of(StringUtils.substring(g1, 0, 1),
										StringUtils.substring(g2, 0, indexOf + 1), StringUtils.substring(g1, 1, 2),
										StringUtils.substring(g2, indexOf + 1, lastIndexOf + 1),
										StringUtils.substring(g1, 2), StringUtils.substring(g2, lastIndexOf + 1))));
				//
			});
			//
			if (lastIndexOf - indexOf != 2) {
				//
				testAndRun(Boolean.logicalAnd(indexOf == 2, lastIndexOf == lv - 1),
						() -> MultimapUtil.putAll(multimap, ImmutableMultimap.of(StringUtils.substring(g1, 0, 1),
								StringUtils.substring(g2, 0, 1), StringUtils.substring(g1, 1, 2),
								StringUtils.substring(g2, indexOf - 1, indexOf + 1), StringUtils.substring(g1, 2, 3),
								StringUtils.substring(g2, indexOf + 1, lastIndexOf - 1), StringUtils.substring(g1, 3),
								StringUtils.substring(g2, lastIndexOf - 1, lastIndexOf + 1))));
				//
				if (Boolean.logicalAnd(indexOf == 1, lastIndexOf == lv - 1)) {
					//
					MultimapUtil.putAll(multimap,
							ImmutableMultimap.of(StringUtils.substring(g1, 0, 1),
									StringUtils.substring(g2, indexOf - 1, indexOf + 1), StringUtils.substring(g1, 3),
									StringUtils.substring(g2, lastIndexOf - 1, lastIndexOf + 1)));
					//
					if (lastIndexOf - indexOf == lk) {
						//
						MultimapUtil.putAll(multimap, ImmutableMultimap.of(StringUtils.substring(g1, 1, 2),
								StringUtils.substring(g2, indexOf + 1, indexOf + 2), StringUtils.substring(g1, 2, 3),
								StringUtils.substring(g2, indexOf + 2, indexOf + 3)));
						//
					} // if
						//
				} else if (Boolean.logicalAnd(lastIndexOf == lv - 1, lastIndexOf - indexOf == 4)) {
					//
					MultimapUtil.putAll(multimap, ImmutableMultimap.of(StringUtils.substring(g1, 0, 1),
							StringUtils.substring(g2, 0, indexOf - 1), StringUtils.substring(g1, 1, 2),
							StringUtils.substring(g2, indexOf - 1, indexOf + 1), StringUtils.substring(g1, 2, lk - 1),
							StringUtils.substring(g2, indexOf + 1, lastIndexOf - 1), StringUtils.substring(g1, lk - 1),
							StringUtils.substring(g2, lastIndexOf - 1, lastIndexOf + 1)));
					//
				} // if
					//
			} // if
				//
		} // if
			//
		return multimap;
		//
	}

	private static interface ObjIntObjObjFunction<A, C, D, R> {

		R apply(final A a, final int b, final C c, final D d);

		@Nullable
		private static <A, C, D, R> R apply(@Nullable final ObjIntObjObjFunction<A, C, D, R> instance, final A a,
				final int b, final C c, final D d) {
			return instance != null ? instance.apply(a, b, c, d) : null;
		}

	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection12B(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines,
			final Entry<String, String> entry) {
		//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		if (!Boolean.logicalAnd(StringUtils.length(g11) == 4, StringUtils.countMatches(g12, 'ん') == 0)) {
			//
			return null;
			//
		} // if
			//
		final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12));
		//
		final IntCollection intCollection = IntList.create();
		//
		Entry<Multimap<String, String>, IntCollection> temp = null;
		//
		Map<String, String> map = null;
		//
		final List<ObjIntObjObjFunction<PatternMap, String, Map<String, String>, Entry<Multimap<String, String>, IntCollection>>> functions = Arrays
				.asList(OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection12B1,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection12B2,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection12B3,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection12B4);
		//
		int length;
		//
		final Iterable<Entry<String, IntObjectPair<String>>> siops = toIterable12B(lines, g11);
		//
		Entry<String, IntObjectPair<String>> siop = null;
		//
		IntObjectPair<String> value = null;
		//
		for (int i = 0; Boolean.logicalAnd(i < IterableUtils.size(siops), Util.iterator(functions) != null); i++) {
			//
			if ((value = Util.getValue(siop = IterableUtils.get(siops, i))) == null) {
				//
				continue;
				//
			} // if
				//
			if (map == null) {
				//
				Util.putAll(map = Reflection.newProxy(Map.class, new IH()), Map.of("g11", g11, "g12", g12));
				//
			} // if
				//
			Util.put(map, "s", Util.getKey(siop));
			//
			length = MultimapUtil.size(multimap);
			//
			for (final ObjIntObjObjFunction<PatternMap, String, Map<String, String>, Entry<Multimap<String, String>, IntCollection>> function : functions) {
				//
				MultimapUtil.putAll(multimap, Util.getKey(temp = ObjIntObjObjFunction.apply(function, patternMap,
						value.keyInt(), PairUtil.right(value), map)));
				//
				Util.forEach(
						Util.cast(IntStream.class,
								testAndApply(Objects::nonNull, IntCollectionUtil.toIntArray(Util.getValue(temp)),
										IntStream::of, null)),
						x -> testAndAccept((a, b) -> !IntIterableUtil.containsInt(a, b), intCollection, x,
								IntCollectionUtil::addInt));
				//
				if (length != MultimapUtil.size(multimap)) {
					//
					break;
					//
				} // if
			} // for
				//
		} // for
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Iterable<Entry<String, IntObjectPair<String>>> toIterable12B(final Iterable<String> lines,
			final String g11) {
		//
		Collection<Entry<String, IntObjectPair<String>>> entries = null;
		//
		for (int i = StringUtils.length(g11) - 1; i >= 0; i--) {
			//
			for (int j = 0; j < IterableUtils.size(lines); j++) {
				//
				Util.add(entries = ObjectUtils.getIfNull(entries, ArrayList::new), Pair
						.of(StringUtils.substring(g11, i, i + 1), IntObjectPair.of(j, IterableUtils.get(lines, j))));
				//
			} // for
				//
		} // for
			//
		return entries;
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection12B1(
			final PatternMap patternMap, final int index, final String line, final Map<String, String> map) {
		//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		final IntCollection intCollection = IntList.create();
		//
		final String s = MapUtils.getObject(map, "s");
		//
		Matcher m2;
		//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$", s)),
				line)) && Util.groupCount(m2) > 1) {
			//
			IntCollectionUtil.addInt(intCollection, index);
			//
			MultimapUtil.put(multimap, Util.group(m2, 1), Util.group(m2, 2));
			//
		} else if (Util.matches(m2 = toMatcher12B1(s, patternMap, line)) && Util.groupCount(m2) > 1) {
			//
			String g21, g22, cpk, cpv, lcsk, lcsv;
			//
			if (Boolean.logicalAnd(StringUtils.length(cpk = Strings.commonPrefix(g11, g21 = Util.group(m2, 1))) == 1,
					StringUtils.isNotBlank(cpv = Strings.commonPrefix(g12, g22 = Util.group(m2, 2))))) {
				//
				MultimapUtil.put(multimap, cpk, cpv);
				//
				testAndAccept((a, b) -> StringUtils.length(MapUtils.getObject(a, "g21")) == 2,
						Map.of("g21", g21, "g22", g22, "cpk", cpk, "cpv", cpv), index, (a, b) -> {
							//
							IntCollectionUtil.addInt(intCollection, b);
							//
							MultimapUtil.putAll(multimap,
									ImmutableMultimap.of(MapUtils.getObject(a, "g21"), MapUtils.getObject(a, "g22"),
											StringUtils.substringAfter(MapUtils.getObject(a, "g21"),
													MapUtils.getObject(a, "cpk")),
											StringUtils.substringAfter(MapUtils.getObject(a, "g22"),
													MapUtils.getObject(a, "cpv"))));
							//
						});
				//
			} else if (StringUtils.length(g21) == 2) {
				//
				IntCollectionUtil.addInt(intCollection, index);
				//
				MultimapUtil.put(multimap, g21, g22);
				//
			} else if (Boolean.logicalAnd(StringUtils.length(lcsk = longestCommonSubstring(g11, g21)) == 1,
					StringUtils.isNotBlank(lcsv = longestCommonSubstring(g12, g22)))) {
				//
				MultimapUtil.put(multimap, lcsk, lcsv);
				//
			} // if
				//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	private static Matcher toMatcher12B1(final String s, final PatternMap patternMap, final String line) {
		//
		return Util.matcher(PatternMap.getPattern(patternMap, String.format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				s)), line);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection12B2(
			final PatternMap patternMap, final int index, final String line, final Map<String, String> map) {
		//
		final Matcher m2 = toMatcher12B1(MapUtils.getObject(map, "s"), patternMap, line);
		//
		if (!Util.matches(m2) || Util.groupCount(m2) <= 1) {
			//
			return null;
			//
		} // if
			//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		final IntCollection intCollection = IntList.create();
		//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		final String g21 = Util.group(m2, 1);
		//
		final String g22 = Util.group(m2, 2);
		//
		String lcsk, lcsv;
		//
		int length;
		//
		if (Boolean.logicalAnd(StringUtils.endsWith(g22, "ん"),
				StringUtils.isNotBlank(lcsv = longestCommonSubstring(g12, g22)))) {
			//
			final TextStringBuilder tsbk = new TextStringBuilder(
					StringUtils.substringAfter(g21, longestCommonSubstring(g11, g21)));
			//
			final TextStringBuilder tsbv = new TextStringBuilder(StringUtils.substringAfter(g22, lcsv));
			//
			if (Boolean.logicalAnd(StringUtils.length(tsbk) == 2, (length = StringUtils.length(tsbv)) > 1)) {
				//
				MultimapUtil.put(multimap, substring(tsbk, 1, 2), substring(tsbv, length - 2, length));
				//
				deleteLastCharacter(tsbk);
				//
				deleteLast2Characters(tsbv);
				//
				MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
				//
			} else if (StringUtils.length(tsbk) == 1) {
				//
				if (Boolean.logicalAnd(StringUtils.length(tsbv) == 1, (length = StringUtils.length(g22)) > 1)) {
					//
					MultimapUtil.put(multimap, Util.toString(tsbk), StringUtils.substring(g22, length - 2, length));
					//
				} else if (StringUtils.length(tsbv) == 2) {
					//
					MultimapUtil.put(multimap, Util.toString(tsbk), Util.toString(tsbv));
					//
				} // if
					//
			} // if
				//
		} else if (Boolean.logicalAnd(StringUtils.length(lcsk = longestCommonSubstring(g11, Util.group(m2, 1))) == 1,
				StringUtils.isNotBlank(lcsv))) {
			//
			MultimapUtil.put(multimap, lcsk, lcsv);
			//
		} else if (StringUtils.length(g21) == StringUtils.length(g22)) {
			//
			MultimapUtil.put(multimap, g21, g22);
			//
		} else if (Boolean.logicalAnd((length = StringUtils.length(g22)) > 1,
				StringUtils.startsWith(getCharacterName(g22, length - 2), HIRAGANA_LETTER_SMALL))) {
			//
			MultimapUtil.put(multimap, StringUtils.substring(g21, StringUtils.length(g21) - 1),
					StringUtils.substring(g22, length - 3));
			//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection12B3(
			final PatternMap patternMap, final int index, final String line, final Map<String, String> map) {
		//
		final Matcher m2 = toMatcher12B1(MapUtils.getObject(map, "s"), patternMap, line);
		//
		if (!Util.matches(m2) || Util.groupCount(m2) <= 1) {
			//
			return null;
			//
		} // if
			//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		final IntCollection intCollection = IntList.create();
		//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		final String g21 = Util.group(m2, 1);
		//
		final String g22 = Util.group(m2, 2);
		//
		int length, lastIndexOf;
		//
		int[] ints;
		//
		final String cpk = Strings.commonPrefix(g11, g21);
		//
		TextStringBuilder tsbk = null, tsbv = null;
		//
		if (StringUtils.length(g11) == StringUtils.length(g21)) {
			//
			final String csk = Strings.commonSuffix(g11, g21);
			//
			if (Boolean.logicalAnd(StringUtils.length(cpk) == 1, StringUtils.length(csk) == 2)) {
				//
				final String csv = Strings.commonSuffix(g12, g22);
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(g21, g22, StringUtils.substringBetween(g21, cpk, csk),
								StringUtils.substringBetween(g22, Strings.commonPrefix(g12, g22), csv), csk, csv));
				//
				IntCollectionUtil.addInt(intCollection, index);
				//
			} else if ((lastIndexOf = StringUtils.lastIndexOf(g22, "ん")) == StringUtils.length(g22) - 1
					&& lastIndexOf - StringUtils.indexOf(g22, "ん") == 2) {
				//
				MultimapUtil.put(multimap,
						substring(tsbk = new TextStringBuilder(g21), (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv = new TextStringBuilder(g22), (length = StringUtils.length(tsbv)) - 2, length));
				//
				deleteLastCharacter(tsbk);
				//
				deleteLast2Characters(tsbv);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv, (length = StringUtils.length(tsbv)) - 2, length));
				//
			} // if
				//
		} else if (StringUtils.length(g21) == 3) {
			//
			String csk;
			//
			if (length(ints = toArray(filter(IntStream.range(0, StringUtils.length(g22)),
					x -> StringUtils.startsWith(getCharacterName(g22, x), HIRAGANA_LETTER_SMALL)))) == 1) {
				//
				final int i = ints[0];
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
								StringUtils.substring(g22, 0, i - 1), StringUtils.substring(g21, 1, 2),
								StringUtils.substring(g22, i - 1, i + 2), StringUtils.substring(g21, 2),
								StringUtils.substring(g22, i + 2)));
				//
				IntCollectionUtil.addInt(intCollection, index);
				//
			} else if (Boolean.logicalAnd(StringUtils.countMatches(g22, "ん") == 1,
					(lastIndexOf = StringUtils.lastIndexOf(g22, "ん")) == StringUtils.length(g22) - 3)) {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
								StringUtils.substring(g22, 0, lastIndexOf - 1), StringUtils.substring(g21, 1),
								StringUtils.substring(g22, lastIndexOf - 1), StringUtils.substring(g21, 1, 2),
								StringUtils.substring(g22, lastIndexOf - 1, lastIndexOf + 1),
								StringUtils.substring(g21, 2), StringUtils.substring(g22, lastIndexOf + 1)));
				//
				IntCollectionUtil.addInt(intCollection, index);
				//
			} else if (Boolean.logicalAnd(StringUtils.length(cpk) == 1,
					StringUtils.length(csk = Strings.commonSuffix(g11, g21)) == 1)) {
				//
				final String cpv = Strings.commonPrefix(g12, g22);
				//
				final String csv = Strings.commonSuffix(g12, g22);
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(g21, g22, cpk, cpv, StringUtils.substringBetween(g21, cpk, csk),
								StringUtils.substringBetween(g22, cpv, csv), csk, csv));
				//
				IntCollectionUtil.addInt(intCollection, index);
				//
			} else if (Util.and((lastIndexOf = StringUtils.lastIndexOf(g22, "ん")) == StringUtils.length(g22) - 1,
					StringUtils.indexOf(g22, "ん") == lastIndexOf,
					StringUtils.length(g22) - StringUtils.length(g21) == 2)) {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 2),
								StringUtils.substring(g22, 0, lastIndexOf - 1), StringUtils.substring(g21, 2),
								StringUtils.substring(g22, lastIndexOf - 1)));
				//
				IntCollectionUtil.addInt(intCollection, index);
				//
			} // if
				//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection12B4(
			final PatternMap patternMap, final int index, final String line, final Map<String, String> map) {
		//
		final Matcher m2 = toMatcher12B1(MapUtils.getObject(map, "s"), patternMap, line);
		//
		if (!Util.matches(m2) || Util.groupCount(m2) <= 1) {
			//
			return null;
			//
		} // if
			//
		final Multimap<String, String> multimap = LinkedHashMultimap.create();
		//
		final IntCollection intCollection = IntList.create();
		//
		final String g11 = MapUtils.getObject(map, "g11");
		//
		final String g12 = MapUtils.getObject(map, "g12");
		//
		final String g21 = Util.group(m2, 1);
		//
		final String g22 = Util.group(m2, 2);
		//
		int length, lastIndexOf;
		//
		int[] ints;
		//
		final String cpk = Strings.commonPrefix(g11, g21);
		//
		TextStringBuilder tsbk = null, tsbv = null;
		//
		if (Boolean.logicalAnd(StringUtils.isNotBlank(cpk), StringUtils.endsWith(g22, "ん"))) {
			//
			final String cpv = Strings.commonPrefix(g12, g22);
			//
			if (length(ints = toArray(filter(IntStream.range(0, StringUtils.length(g22)),
					x -> StringUtils.startsWith(getCharacterName(g22, x), HIRAGANA_LETTER_SMALL)))) == 1) {
				//
				MultimapUtil.putAll(multimap, ImmutableMultimap.of(cpk, cpv,
						substring(tsbk = new TextStringBuilder(g21), (length = StringUtils.length(tsbk)) - 1, length),
						substring(tsbv = new TextStringBuilder(g22), (length = StringUtils.length(tsbv)) - 2, length)));
				//
				deleteLastCharacter(tsbk);
				//
				deleteLast2Characters(tsbv);
				//
				final String string = substring(tsbv, (lastIndexOf = ints[0]) + 2, StringUtils.length(tsbv));
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length), string);
				//
				deleteLastCharacter(tsbk);
				//
				delete(tsbv, (length = StringUtils.length(tsbv)) - StringUtils.length(string), length + 1);
				//
				MultimapUtil.put(multimap, substring(tsbk, (length = StringUtils.length(tsbk)) - 1, length),
						tsbv.substring(lastIndexOf - 1));
				//
			} else if (StringUtils.length(g21) == 5) {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(cpk, cpv,
								StringUtils.substring(g21, length = StringUtils.length(cpk), length + 2),
								StringUtils.substring(g22, StringUtils.length(cpv), StringUtils.length(g22) - 2),
								StringUtils.substring(g21, StringUtils.length(g21) - 1),
								StringUtils.substring(g22, StringUtils.length(g22) - 2)));
				//
				IntCollectionUtil.addInt(intCollection, index);
				//
			} else {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(cpk, cpv, StringUtils.substring(g21, StringUtils.length(g21) - 1),
								StringUtils.substring(g22, StringUtils.length(g22) - 2)));
				//
			} // if
				//
		} // if
			//
		final String lcsk = longestCommonSubstring(g11, g21);
		//
		final String lcsv = longestCommonSubstring(g12, g22);
		//
		if (StringUtils.length(lcsk) == 1 && StringUtils.isNotBlank(lcsv)
				&& StringUtils.indexOf(g11, lcsk) == StringUtils.length(g11) - 2
				&& length(ints = toArray(filter(IntStream.range(0, StringUtils.length(g22)),
						x -> StringUtils.startsWith(getCharacterName(g22, x), HIRAGANA_LETTER_SMALL)))) == 1
				&& StringUtils.startsWith(g21, lcsk) && StringUtils.startsWith(g22, lcsv)
				&& StringUtils.endsWith(g22, "ん")) {
			//
			final int i = ints[0];
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(lcsk, lcsv, StringUtils.substringAfter(g11, lcsk),
							StringUtils.substringAfter(g12, lcsv), StringUtils.substring(g21, 1, 2),
							StringUtils.substring(g22, i - 1, i + 2),
							StringUtils.substring(g21, 2, StringUtils.length(g21) - 1),
							StringUtils.substring(g22, i + 2, StringUtils.length(g22) - 2),
							StringUtils.substring(g21, StringUtils.length(g21) - 1),
							StringUtils.substring(g22, StringUtils.length(g22) - 2)));
			//
			IntCollectionUtil.addInt(intCollection, index);
			//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	private static interface IntObjFunction<B, R> {

		R apply(final int a, final B b);

		@Nullable
		private static <B, R> R apply(@Nullable final IntObjFunction<B, R> instance, final int a, final B b) {
			return instance != null ? instance.apply(a, b) : null;
		}

	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection12C(
			final Iterable<String> lines, final String g11) {
		//
		Matcher m2;
		//
		Multimap<String, String> multimap = null;
		//
		IntCollection intCollection = null;
		//
		Entry<Multimap<String, String>, IntCollection> entry;
		//
		String g21;
		//
		final Iterable<IntObjFunction<Entry<String, String>, Entry<Multimap<String, String>, IntCollection>>> functions = Arrays
				.asList(OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection12C1,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection12C2);
		//
		int size;
		//
		for (int i = 0; i < IterableUtils.size(lines) && Util.iterator(functions) != null; i++) {
			//
			if (!Util.matches(m2 = Util.matcher(PATTERN_KANJI_HIRAGANA, IterableUtils.get(lines, i)))
					|| Util.groupCount(m2) <= 1
					|| StringUtils.isBlank(longestCommonSubstring(g11, g21 = Util.group(m2, 1)))) {
				//
				continue;
				//
			} // if
				//
			size = MultimapUtil.size(multimap);
			//
			for (final IntObjFunction<Entry<String, String>, Entry<Multimap<String, String>, IntCollection>> function : functions) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.getKey(entry = IntObjFunction.apply(function, i, Pair.of(g21, Util.group(m2, 2)))));
				//
				IntCollectionUtil.addAllInts(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
						Util.getValue(entry));
				//
				if (size != MultimapUtil.size(multimap)) {
					//
					break;
					//
				} // if
					//
			} // for
				//
		} // for
			//
		return testAndApply((a, b) -> Boolean.logicalOr(a != null, b != null), multimap, intCollection, Pair::of, null);
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection12C1(final int index,
			final Entry<String, String> entry) {
		//
		final String g21 = Util.getKey(entry);
		//
		final String g22 = Util.getValue(entry);
		//
		int indexOf, lastIndexOf;
		//
		long length;
		//
		Multimap<String, String> multimap = null;
		//
		IntCollection intCollection = null;
		//
		int[] ints;
		//
		if ((indexOf = StringUtils.indexOf(g22, "ん")) < (lastIndexOf = StringUtils.lastIndexOf(g22, "ん"))) {
			//
			if ((length = StringUtils.length(g21)) == 2) {
				//
				if (Boolean.logicalOr(StringUtils.length(g22) == 4, lastIndexOf - indexOf == 2)) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
									StringUtils.substring(g22, 0, indexOf + 1), StringUtils.substring(g21, 1),
									StringUtils.substring(g22, indexOf + 1)));
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
							index);
					//
				} else if (lastIndexOf - indexOf == 3) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
									StringUtils.substring(g22, 0, indexOf + 2), StringUtils.substring(g21, 1),
									StringUtils.substring(g22, indexOf + 2)));
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
							index);
					//
				} // if
					//
			} else if (Boolean.logicalAnd(length == 3, length(ints = toArray(indexOf(g22, c -> c == 'ん'))) == 3)) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
								StringUtils.substring(g22, 0, get(ints, 0, -1) + 1), StringUtils.substring(g21, 1, 2),
								StringUtils.substring(g22, get(ints, 0, -1) + 1, get(ints, 1, -1) + 1),
								StringUtils.substring(g21, 2), StringUtils.substring(g22, get(ints, 2, -1) - 1)));
				//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), index);
				//
			} // if
				//
		} // if
			//
		return testAndApply((a, b) -> Boolean.logicalOr(a != null, b != null), multimap, intCollection, Pair::of, null);
		//
	}

	private static int get(@Nullable final int[] instance, final int index, final int defaultValue) {
		return instance != null ? instance[index] : defaultValue;
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection12C2(final int index,
			final Entry<String, String> entry) {
		//
		final String g21 = Util.getKey(entry);
		//
		final String g22 = Util.getValue(entry);
		//
		int indexOf, lastIndexOf, length;
		//
		Multimap<String, String> multimap = null;
		//
		IntCollection intCollection = null;
		//
		int[] ints;
		//
		if (StringUtils.indexOf(g22, "ん") < StringUtils.lastIndexOf(g22, "ん")) {
			//
			if (Boolean.logicalAnd(StringUtils.length(g21) == 3,
					(length(ints = toArray(indexOf(g22, c -> c == 'ん')))) == 2)) {
				//
				if (Boolean.logicalAnd(get(ints, 0, -1) == 1, get(ints, 1, -1) == StringUtils.length(g22) - 1)) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
									StringUtils.substring(g22, 0, get(ints, 0, -1) + 1),
									StringUtils.substring(g21, 1, 2),
									StringUtils.substring(g22, get(ints, 0, -1) + 1, get(ints, 1, -1) - 1),
									StringUtils.substring(g21, 2), StringUtils.substring(g22, get(ints, 1, -1) - 1)));
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
							index);
					//
				} else if (Boolean.logicalAnd(get(ints, 0, -1) == 2, get(ints, 1, -1) == StringUtils.length(g22) - 1)) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
									StringUtils.substring(g22, 0, 1), StringUtils.substring(g21, 1, 2),
									StringUtils.substring(g22, get(ints, 0, -1) - 1, get(ints, 1, -1) - 1),
									StringUtils.substring(g21, 2), StringUtils.substring(g22, get(ints, 1, -1) - 1)));
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
							index);
					//
				} else {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
									StringUtils.substring(g22, 0, get(ints, 0, -1) - 1),
									StringUtils.substring(g21, 1, 2),
									StringUtils.substring(g22, get(ints, 0, -1) - 1, get(ints, 1, -1) - 1),
									StringUtils.substring(g21, 2), StringUtils.substring(g22, get(ints, 1, -1) - 1)));
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
							index);
					//
				} // if
					//
			} // if
				//
		} else if (StringUtils.length(g21) == 2) {
			//
			if (Boolean.logicalAnd(
					(indexOf = StringUtils.indexOf(g22, "ん")) == (lastIndexOf = StringUtils.lastIndexOf(g22, "ん")),
					Objects.equals(StringUtils.substring(g22, lastIndexOf), "ん"))) {
				//
				if (Boolean.logicalOr((length = StringUtils.length(g22)) == 3, length == 4)) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
									StringUtils.substring(g22, 0, indexOf - 1), StringUtils.substring(g21, 1),
									StringUtils.substring(g22, indexOf - 1)));
					//
					IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
							index);
					//
				} // if
					//
			} else if (Boolean.logicalAnd(
					(indexOf = StringUtils.indexOf(g22, "ん")) == (StringUtils.lastIndexOf(g22, "ん")), indexOf == 1)) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(g21, g22, StringUtils.substring(g21, 0, 1),
								StringUtils.substring(g22, 0, indexOf + 1), StringUtils.substring(g21, 1),
								StringUtils.substring(g22, indexOf + 1)));
				//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), index);
				//
			} // if
				//
		} // if
			//
		return testAndApply((a, b) -> Boolean.logicalOr(a != null, b != null), multimap, intCollection, Pair::of, null);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection13(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$"),
				PairUtil.right(iop));
		//
		Multimap<String, String> multimap = null;
		//
		IntCollection intCollection = null;
		//
		if (Util.matches(m1) && Util.groupCount(m1) >= 3) {
			//
			final String g11 = Util.group(m1, 1);
			//
			final String g12 = Util.group(m1, 2);
			//
			final String g13 = Util.group(m1, 3);
			//
			final String g14 = Util.group(m1, 4);
			//
			final int[] ints = toArray(filter(IntStream.range(0, StringUtils.length(g14)),
					x -> StringUtils.startsWith(getCharacterName(g14, x), HIRAGANA_LETTER_SMALL)));
			//
			if (length(ints) == 1) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(g11, StringUtils.substringBefore(g14, g12), g13,
								StringUtils.substringAfter(g14, g12)));
				//
				intCollection = createIntCollection(iop);
				//
			} else if (StringUtils.endsWith(g14, "ん")) {
				//
				if (StringUtils.equals(g12, "つ")) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ImmutableMultimap.of(g11, StringUtils.substringBefore(g14, g12), g13,
									StringUtils.substringAfter(g14, g12)));
					//
					intCollection = createIntCollection(iop);
					//
				} else {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g13,
							StringUtils.substringAfter(g14, g12));
					//
					intCollection = createIntCollection(iop);
					//
				} // if
					//
			} else {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g13,
						StringUtils.substringAfter(g14, g12));
				//
				intCollection = createIntCollection(iop);
				//
			} // if
				//
		} // if
			//
		final Iterable<Triplet<String, String, String>> triplets = Arrays.asList(Triplet.with("鮫", "ざめ", "さめ"),
				Triplet.with("縞", "じま", "しま"));
		//
		Triplet<String, String, String> triplet = null;
		//
		String value0, value1;
		//
		for (int i = 0; i < IterableUtils.size(triplets); i++) {
			//
			if (MultimapUtil.containsEntry(multimap,
					value0 = IValue0Util.getValue0(triplet = IterableUtils.get(triplets, i)),
					value1 = Util.getValue1(triplet))) {
				//
				MultimapUtil.remove(multimap, value0, value1);
				//
				MultimapUtil.put(multimap, value0, Util.getValue2(triplet));
				//
			} // if
				//
		} // for
			//
		return testAndApply((a, b) -> Boolean.logicalOr(a != null, b != null), multimap, intCollection, Pair::of, null);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection14(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$"),
				PairUtil.right(iop));
		//
		Multimap<String, String> multimap = null;
		//
		IntCollection intCollection = null;
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 3) {
			//
			return null;
			//
		} // if
			//
		final String g11 = Util.group(m1, 1);
		//
		final String g12 = Util.group(m1, 2);
		//
		final String g13 = Util.group(m1, 3);
		//
		final String g14 = Util.group(m1, 4);
		//
		final int length = StringUtils.length(g14);
		//
		final boolean g14EndsWithHiraganaLetterN = length > 0 && equals(g14, length - 1, 'ん');
		//
		if (StringUtils.equals(g12, "の")) {
			//
			if (Boolean.logicalAnd(g14EndsWithHiraganaLetterN, StringUtils.length(g13) > 1)) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(g11, StringUtils.substringBefore(g14, g12),
								StringUtils.substring(g13, 0, 1),
								StringUtils.substring(g14, StringUtils.indexOf(g14, g12) + 1, length - 2),
								StringUtils.substring(g13, 1), StringUtils.substring(g14, length - 2, length + 1)));
				//
				intCollection = createIntCollection(iop);
				//
			} else {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g11,
						StringUtils.substringBefore(g14, g12));
				//
				intCollection = createIntCollection(iop);
				//
			} // if
				//
		} else if (Util.and(Util.contains(Arrays.asList("つ", "と"), g12), StringUtils.length(g13) > 1,
				g14EndsWithHiraganaLetterN)) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					ImmutableMultimap.of(g11, StringUtils.substringBefore(g14, g12), StringUtils.substring(g13, 0, 1),
							StringUtils.substring(g14, StringUtils.indexOf(g14, g12) + 1, length - 2),
							StringUtils.substring(g13, 1), StringUtils.substring(g14, length - 2, length + 1)));
			//
			intCollection = createIntCollection(iop);
			//
		} else if (Util.and(StringUtils.equals(g12, "く"), StringUtils.length(g13) > 1, g14EndsWithHiraganaLetterN)) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					ImmutableMultimap.of(StringUtils.substring(g13, 0, 1),
							StringUtils.substring(g14, StringUtils.indexOf(g14, g12) + 1, length - 2),
							StringUtils.substring(g13, 1), StringUtils.substring(g14, length - 2, length + 1)));
			//
			intCollection = createIntCollection(iop);
			//
		} else if (Boolean.logicalAnd(Util.contains(Arrays.asList("し", "る", "り", "ね", "き", "れ", "い"), g12),
				StringUtils.length(g13) > 1)) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g13,
					StringUtils.substringAfter(g14, g12));
			//
			intCollection = createIntCollection(iop);
			//
			if (g14EndsWithHiraganaLetterN) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(StringUtils.substring(g13, 0, 1),
								StringUtils.substring(g14, StringUtils.indexOf(g14, g12) + 1, length - 2),
								StringUtils.substring(g13, 1), StringUtils.substring(g14, length - 2)));
				//
			} // if
				//
		} else if (Util.and(Objects.equals(g12, "に"), StringUtils.length(g13) > 1, g14EndsWithHiraganaLetterN)) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					ImmutableMultimap.of(g11, StringUtils.substringBefore(g14, g12), g13,
							StringUtils.substringAfter(g14, g12), StringUtils.substring(g13, 0, 1),
							StringUtils.substring(g14, StringUtils.indexOf(g14, g12) + 1, length - 2),
							StringUtils.substring(g13, 1), StringUtils.substring(g14, length - 2, length + 1)));
			//
			intCollection = createIntCollection(iop);
			//
		} else if (Objects.equals(g12, "び")) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g13,
					StringUtils.substringAfter(g14, g12));
			//
			intCollection = createIntCollection(iop);
			//
		} // if
			//
		final Multimap<String, String> mm = LinkedHashMultimap
				.create(ImmutableMultimap.of("住", "すみ", "鹿", "か", "波文", "なみもん", "猪文", "いのししもん", "子文", "こもん"));
		//
		MultimapUtil.putAll(mm,
				ImmutableMultimap.of("桐文", "きりもん", "松文", "まつもん", "雲文", "くももん", "鶴文", "つるもん", "雁文", "がんもん"));
		//
		MultimapUtil.putAll(mm, ImmutableMultimap.of("魚文", "うおもん", "舟文", "ふねもん", "鳥文", "とりもん"));
		//
		final Iterable<Entry<String, String>> entries = MultimapUtil.entries(mm);
		//
		Entry<String, String> entry;
		//
		for (int i = 0; i < IterableUtils.size(entries); i++) {
			//
			testAndAccept(MultimapUtil::containsEntry,
					multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.getKey(entry = IterableUtils.get(entries, i)), Util.getValue(entry), MultimapUtil::remove);
			//
		} // for
			//
		return testAndApply((a, b) -> Boolean.logicalOr(a != null, b != null), multimap, intCollection, Pair::of, null);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection15(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final String right = PairUtil.right(iop);
		//
		Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}{2})\\p{InHalfwidthAndFullwidthForms}"),
				right);
		//
		String g11, g12;
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 1 && StringUtils.length(g11 = Util.group(m1, 1)) == 2
				&& StringUtils.length(g12 = Util.group(m1, 2)) == 2) {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(
					ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 1),
							StringUtils.substring(g11, 1), StringUtils.substring(g12, 1)));
			//
			final Iterable<Entry<String, String>> entries = MultimapUtil
					.entries(ImmutableMultimap.of("章", "た", "魚", "こ"));
			//
			Entry<String, String> entry;
			//
			for (int i = 0; i < IterableUtils.size(entries); i++) {
				//
				testAndAccept(MultimapUtil::containsEntry, multimap, Util.getKey(entry = IterableUtils.get(entries, i)),
						Util.getValue(entry), MultimapUtil::remove);
				//
			} // for
				//
			Util.forEach(Arrays.asList(Triplet.with("羽", "ば", "は")),
					//
					a -> testAndAccept(
							b -> MultimapUtil.containsEntry(multimap, IValue0Util.getValue0(b), Util.getValue1(b)), a,
							b -> {
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
			return Pair.of(multimap, createIntCollection(iop));
			//
		} else if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}{3})\\p{InHalfwidthAndFullwidthForms}"),
				right)) && Util.groupCount(m1) > 1 && StringUtils.length(g11 = Util.group(m1, 1)) == 3
				&& StringUtils.length(g12 = Util.group(m1, 2)) == 3) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 1), StringUtils.substring(g11, 2), StringUtils.substring(g12, 2)),
					createIntCollection(iop));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection16(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Pattern pattern = PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}{3})\\p{InHalfwidthAndFullwidthForms}");
		//
		Matcher m1 = Util.matcher(pattern, PairUtil.right(iop));
		//
		String g11, g12;
		//
		Multimap<String, String> multimap = null;
		//
		IntCollection intCollection = null;
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 1 || StringUtils.length(g11 = Util.group(m1, 1)) != 2
				|| StringUtils.length(g12 = Util.group(m1, 2)) != 3) {
			//
			return null;
			//
		} // if
			//
		if (Util.contains(Arrays.asList("ん", "ょ"), StringUtils.substring(g12, 1, 2))) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 2), StringUtils.substring(g11, 1), StringUtils.substring(g12, 2)),
					createIntCollection(iop));
			//
		} else if (StringUtils.endsWith(g12, "ん")) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 1), StringUtils.substring(g11, 1), StringUtils.substring(g12, 1)),
					createIntCollection(iop));
			//
		} else {
			//
			multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12));
			//
			intCollection = createIntCollection(iop);
			//
			Matcher m2;
			//
			String g21, g22, csk, csv;
			//
			for (int i = 0; i < IterableUtils.size(lines); i++) {
				//
				if (keyIntEquals(iop, i) || !Util.matches(m2 = Util.matcher(pattern, IterableUtils.get(lines, i)))
						|| Util.groupCount(m2) <= 1
						|| StringUtils.isBlank(csk = Strings.commonSuffix(g11, g21 = Util.group(m2, 1)))
						|| StringUtils.isBlank(csv = Strings.commonSuffix(g12, g22 = Util.group(m2, 2)))) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substringBefore(g12, csv),
								csk, csv, g21, g22, StringUtils.substring(g21, 0, 1),
								StringUtils.substringBefore(g22, csv)));
				//
				IntCollectionUtil.addInt(intCollection, i);
				//
			} // for
				//
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(ImmutableMultimap.of("厚", "あつ"));
			//
			Entry<String, String> entry = null;
			//
			for (int i = 0; i < IterableUtils.size(entries); i++) {
				//
				testAndAccept(MultimapUtil::containsEntry, multimap, Util.getKey(entry = IterableUtils.get(entries, i)),
						Util.getValue(entry), MultimapUtil::remove);
				//
			} // for
				//
			return Pair.of(multimap, intCollection);
			//
		} // if
			//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection17(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Pattern pattern = PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}{4})\\p{InHalfwidthAndFullwidthForms}");
		//
		final Matcher m1 = Util.matcher(pattern, PairUtil.right(iop));
		//
		String g11, g12;
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 1 || StringUtils.length(g11 = Util.group(m1, 1)) != 2
				|| StringUtils.length(g12 = Util.group(m1, 2)) != 4) {
			//
			return null;
			//
		} // if
			//
		final Entry<Multimap<String, String>, IntCollection> entry = toMultimapAndIntCollection17(
				iop != null ? IntObjectPair.of(iop.keyInt(), Pair.of(g11, g12)) : null, lines, pattern);
		//
		return Pair.of(Util.getKey(entry), Util.getValue(entry));
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection17(
			@Nullable final IntObjectPair<Entry<String, String>> iop, final Iterable<String> lines,
			final Pattern pattern) {
		//
		final Entry<Multimap<String, String>, IntCollection> mi = toMultimapAndIntCollection17A(iop);
		//
		final Multimap<String, String> multimap = Util.getKey(mi);
		//
		final IntCollection intCollection = Util.getValue(mi);
		//
		if (MultimapUtil.size(multimap) == 1) {
			//
			final Entry<String, String> entry = PairUtil.right(iop);
			//
			final String g11 = Util.getKey(entry);
			//
			final String g12 = Util.getValue(entry);
			//
			Matcher m2 = null;
			//
			String g21, g22, cpk, cpv;
			//
			for (int i = 0; i < IterableUtils.size(lines); i++) {
				//
				if (keyIntEquals(iop, i) || !Util.matches(m2 = Util.matcher(pattern, IterableUtils.get(lines, i)))
						|| Util.groupCount(m2) <= 1
						|| StringUtils.isBlank(cpk = Strings.commonPrefix(g11, g21 = Util.group(m2, 1)))
						|| StringUtils.isBlank(cpv = Strings.commonPrefix(g12, g22 = Util.group(m2, 2)))) {
					//
					continue;
					//
				} // if
					//
				if (StringUtils.length(cpv) > 1) {
					//
					MultimapUtil.putAll(multimap,
							ImmutableMultimap.of(g21, g22, cpk, cpv, StringUtils.substringAfter(g11, cpk),
									StringUtils.substringAfter(g12, cpv), StringUtils.substringAfter(g21, cpk),
									StringUtils.substringAfter(g22, cpv)));
					//
				} else if (StringUtils.endsWith(g22, "ん")) {
					//
					MultimapUtil.put(multimap, StringUtils.substring(g11, 1), StringUtils.substring(g22, 2));
					//
					IntCollectionUtil.addInt(intCollection, i);
					//
				} else if (StringUtils.startsWith(g22, "え")) {
					//
					MultimapUtil.putAll(multimap,
							ImmutableMultimap.of(cpk, cpv, StringUtils.substringAfter(g11, cpk),
									StringUtils.substringAfter(g12, cpv), StringUtils.substringAfter(g21, cpk),
									StringUtils.substringAfter(g22, cpv)));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		Util.forEach(MultimapUtil.entries(ImmutableMultimap.of("目", "も", "八", "は")),
				x -> testAndAccept(MultimapUtil::containsEntry, multimap, Util.getKey(x), Util.getValue(x),
						MultimapUtil::remove));
		//
		Util.forEach(Arrays.asList(Triplet.with("春", "ぱる", "はる"), Triplet.with("風", "ぷう", "ふう"),
				Triplet.with("樽", "だる", "たる"), Triplet.with("絣", "がすり", "かすり")), x ->
		//
		testAndAccept((a, b) -> MultimapUtil.containsEntry(a, IValue0Util.getValue0(b), Util.getValue1(b)), multimap, x,
				(a, b) -> {
					//
					MultimapUtil.remove(a, IValue0Util.getValue0(b), Util.getValue1(b));
					//
					MultimapUtil.put(a, IValue0Util.getValue0(b), Util.getValue2(b));
					//
				})
		//
		);
		//
		return Pair.of(multimap, intCollection);
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection17A(
			@Nullable final IntObjectPair<Entry<String, String>> iop) {
		//
		final Entry<String, String> entry = PairUtil.right(iop);
		//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		final Multimap<String, String> multimap = testAndApply((a, b) -> a != null, g11, g12,
				(a, b) -> LinkedHashMultimap.create(ImmutableMultimap.of(a, b)), null);
		//
		int indexOf = StringUtils.indexOf(g12, "ん");
		//
		final int lastIndexOf = StringUtils.lastIndexOf(g12, "ん");
		//
		final int[] ints = toArray(filter(IntStream.range(0, StringUtils.length(g12)),
				x -> StringUtils.startsWith(getCharacterName(g12, x), HIRAGANA_LETTER_SMALL)));
		//
		if (lastIndexOf > indexOf) {
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, indexOf + 1),
							StringUtils.substring(g11, 1), StringUtils.substring(g12, indexOf + 1)));
			//
		} else if (Boolean.logicalAnd(indexOf == lastIndexOf, lastIndexOf >= 0)) {
			//
			if (lastIndexOf == StringUtils.length(g12) - 1) {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 2),
								StringUtils.substring(g11, 1), StringUtils.substring(g12, 2)));
				//
			} else {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(StringUtils.substring(g11, 0, 1),
								StringUtils.substring(g12, 0, indexOf + 1), StringUtils.substring(g11, 1),
								StringUtils.substring(g12, indexOf + 1)));
				//
			} // if
				//
		} else if (Boolean.logicalAnd(length(ints) == 1, g12 != null)) {
			//
			if (Boolean.logicalAnd((indexOf = get(ints, 0, 0)) == StringUtils.length(g12) - 2,
					ArrayUtils.contains(new char[] { 'ょ', 'ゅ' }, charAt(g12, indexOf, ' ')))) {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(StringUtils.substring(g11, 0, 1),
								StringUtils.substring(g12, 0, indexOf - 1), StringUtils.substring(g11, 1),
								StringUtils.substring(g12, indexOf - 1)));
				//
			} else if (indexOf == 1) {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, indexOf),
								StringUtils.substring(g11, 1), StringUtils.substring(g12, indexOf + 1)));
				//
			} // if
				//
		} // if
			//
		return Pair.of(multimap, createIntCollection(iop));
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection18(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PATTERN_KANJI_HIRAGANA, PairUtil.right(iop));
		//
		String g11, g12;
		//
		int indexOf;
		//
		int[] ints;
		//
		long length;
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 1 || StringUtils.isBlank(g11 = Util.group(m1, 1))
				|| (indexOf = StringUtils.indexOf(g12 = Util.group(m1, 2), "ん")) != StringUtils.length(g12) - 1
				|| (indexOf != StringUtils.lastIndexOf(g12, "ん"))
				|| (ints = toArray(filter(IntStream.range(0, StringUtils.length(g12)),
						x -> StringUtils.startsWith(getCharacterName(g12, x), HIRAGANA_LETTER_SMALL)))) == null
				|| (length = length(ints)) < 1) {
			//
			return null;
			//
		} // if
			//
		final Iterable<QuaternaryFunction<IntObjectPair<String>, Entry<String, String>, IntMap<String>, int[], Entry<Multimap<String, String>, IntCollection>>> functions = Arrays
				.asList(OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection18A,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection18B);
		//
		Multimap<String, String> multimap = null;
		//
		IntCollection intCollection = null;
		//
		if (Util.iterator(functions) != null) {
			//
			Entry<Multimap<String, String>, IntCollection> entry;
			//
			IntMap<String> intMap = null;
			//
			Multimap<String, String> mm = null;
			//
			int[] is = null;
			//
			for (final QuaternaryFunction<IntObjectPair<String>, Entry<String, String>, IntMap<String>, int[], Entry<Multimap<String, String>, IntCollection>> function : functions) {
				//
				IntMap.put(intMap = ObjectUtils.getIfNull(intMap, () -> Reflection.newProxy(IntMap.class, new IH())),
						LENGTH, (int) length);
				//
				IntMap.put(intMap, "indexOf", indexOf);
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						mm = Util.getKey(entry = apply(function, iop, Pair.of(g11, g12), intMap, ints)));
				//
				if ((is = IntCollectionUtil.toIntArray(Util.getValue(entry))) != null) {
					//
					for (final int i : is) {
						//
						testAndAccept((a, b) -> !IntIterableUtil.containsInt(a, b),
								intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), i,
								IntCollectionUtil::addInt);
						//
					} // for
						//
				} // if
					//
				if (MultimapUtil.size(mm) > 1) {
					//
					break;
					//
				} // if
					//
			} // for
				//
		} // if
			//
		final Multimap<String, String> mm = multimap;
		//
		Util.forEach(Arrays.asList(Triplet.with("珠", "じゅ", "しゅ"), Triplet.with("稜", "せん", "りょう"),
				Triplet.with("線", "りょう", "せん"), Triplet.with("尺", "じゃく", "しゃく"), Triplet.with("車", "ぐるま", "くるま")), x ->
		//
		testAndAccept((a, b) -> MultimapUtil.containsEntry(a, IValue0Util.getValue0(b), Util.getValue1(b)), mm, x,
				(a, b) -> {
					//
					MultimapUtil.remove(a, IValue0Util.getValue0(b), Util.getValue1(b));
					//
					MultimapUtil.put(a, IValue0Util.getValue0(b), Util.getValue2(b));
					//
				})
		//
		);
		//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static <T, U, V, W, R> R apply(@Nullable final QuaternaryFunction<T, U, V, W, R> instance, final T t,
			final U u, @Nullable final V v, final W w) {
		return instance != null ? instance.apply(t, u, v, w) : null;
	}

	private static interface IntMap<K> {

		int get(final K key);

		void put(final K key, final int i);

		private static <K> int get(@Nullable final IntMap<K> instance, final K key, final int defaultValue) {
			return instance != null ? instance.get(key) : defaultValue;
		}

		private static <K> void put(@Nullable final IntMap<K> instance, final K key, final int value) {
			if (instance != null) {
				instance.put(key, value);
			}
		}

	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection18A(
			final IntObjectPair<String> iop, final Entry<String, String> entry, @Nullable final IntMap<String> intMap,
			final int[] ints) {
		//
		if (IntMap.get(intMap, LENGTH, 0) != 1) {
			//
			return null;
			//
		} // if
			//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		final int indexOf = IntMap.get(intMap, "indexOf", 0);
		//
		final Multimap<String, String> multimap = testAndApply(Objects::nonNull,
				testAndApply((a, b) -> Objects.nonNull(a), g11, g12, ImmutableMultimap::of, null),
				LinkedHashMultimap::create, null);
		//
		final int size = MultimapUtil.size(multimap);
		//
		final Multimap<String, String> mm = LinkedHashMultimap
				.create(ImmutableMultimap.of("銀杏", "いちょう", "桔梗", "ききょう", "亀甲", "きっこう", "八宝", "はっぽう", "鹿鶴", "ろっかく"));
		//
		MultimapUtil.putAll(mm,
				ImmutableMultimap.of("丁子", "ちょうじ", "茗荷", "みょうが", "種子", "しゅじ", "雪花", "せっか", "独鈷", "とっこ"));
		//
		MultimapUtil.putAll(mm, ImmutableMultimap.of("八掛", "はっけ", "鋸歯", "きょし"));
		//
		final Iterable<Entry<String, String>> entries = MultimapUtil.entries(mm);
		//
		final IntCollection intCollection = createIntCollection(iop);
		//
		testAndAccept((a, b) -> b != null, multimap, toEntry18A1(entries, Pair.of(g11, g12)), (a, b) -> {
			//
			final String key = Util.getKey(b);
			//
			final String value = Util.getValue(b);
			//
			MultimapUtil.putAll(a, ImmutableMultimap.of(key, value, StringUtils.substringAfter(g11, key),
					StringUtils.substringAfter(g12, value)));
			//
		});
		//
		if (Util.and(StringUtils.length(g11) == 3, StringUtils.length(g12) == 6, MultimapUtil.size(multimap) == size)) {
			//
			final int indexOfSubtractInts0 = indexOf - ints[0];
			//
			char c;
			//
			if (indexOfSubtractInts0 == 2) {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 2),
								StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, ints[0] - 1, ints[0] + 1),
								StringUtils.substring(g11, 2), StringUtils.substring(g12, indexOf - 1)));
				//
			} else if (indexOfSubtractInts0 == 3) {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 1),
								StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, ints[0] - 1, ints[0] + 2),
								StringUtils.substring(g11, 2), StringUtils.substring(g12, indexOf - 1)));
				//
			} else if ((c = charAt(g12, ints[0], ' ')) == 'ょ') {
				//
				MultimapUtil.putAll(multimap, toMultimap18A2(IntObjectPair.of(ints[0], Pair.of(g11, g12)), indexOf));
				//
			} else if (c == 'ゃ') {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(StringUtils.substring(g11, 0, 1),
								StringUtils.substring(g12, 0, ints[0] + 1), StringUtils.substring(g11, 1, 2),
								StringUtils.substring(g12, ints[0] + 1, ints[0] + 3), StringUtils.substring(g11, 2),
								StringUtils.substring(g12, indexOf - 1)));
				//
			} else {
				//
				MultimapUtil.putAll(multimap, toMultimap18A2(IntObjectPair.of(ints[0], Pair.of(g11, g12)), indexOf));
				//
			} // if
				//
		} else if (StringUtils.length(g11) == 2) {
			//
			testAndAccept((a, b, c) -> StringUtils.length(c) == 5, multimap, g11, g12,
					(a, b, c) -> MultimapUtil.putAll(a,
							ImmutableMultimap.of(StringUtils.substring(b, 0, 1),
									StringUtils.substring(c, 0, ints[0] + 2), StringUtils.substring(b, 1),
									StringUtils.substring(c, ints[0] + 2))));
			//
			testAndAccept((a, b, c) -> StringUtils.length(c) != 5, multimap, g11, g12, (a, b, c) -> {
				//
				final int l = StringUtils.length(c);
				//
				MultimapUtil.putAll(a,
						ImmutableMultimap.of(StringUtils.substring(b, 0, 1), StringUtils.substring(c, 0, l - 2),
								StringUtils.substring(b, 1), StringUtils.substring(c, l - 2)));
				//
			});
			//
		} else if (Util.and(StringUtils.length(g11) == 3, StringUtils.length(g12) == 5,
				MultimapUtil.size(multimap) == size)) {
			//
			testAndAccept((a, b) -> b == 2, multimap, ints[0],
					(a, b) -> MultimapUtil.putAll(a,
							ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 1),
									StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, b - 1, b + 1),
									StringUtils.substring(g11, 2), StringUtils.substring(g12, b + 1))));
			//
			if (charAt(g12, 1, ' ') == 'ゅ') {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(StringUtils.substring(g11, 0, 1),
								StringUtils.substring(g12, 0, ints[0] + 1), StringUtils.substring(g11, 1, 2),
								StringUtils.substring(g12, ints[0] + 1, ints[0] + 2), StringUtils.substring(g11, 2),
								StringUtils.substring(g12, ints[0] + 2)));
				//
			} // if
				//
		} else if (Boolean.logicalAnd(StringUtils.length(g12) == 7, StringUtils.length(g12) - get(ints, 0, 0) == 4)) {
			//
			final boolean b = StringUtils.length(g11) == 3;
			//
			testAndRun(b, () -> MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, ints[0] - 1),
							StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, ints[0] - 1, ints[0] + 2),
							StringUtils.substring(g11, 2), StringUtils.substring(g12, ints[0] + 2))));
			//
			testAndRun(Boolean.logicalAnd(!b, charAt(g12, ints[0], ' ') == 'ょ'), () -> MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(StringUtils.substring(g11, 0, 2), StringUtils.substring(g12, 0, 2),
							StringUtils.substring(g11, 2, 3), StringUtils.substring(g12, ints[0] - 1, ints[0] + 2),
							StringUtils.substring(g11, 3), StringUtils.substring(g12, ints[0] + 2))));
			//
		} else if (Boolean.logicalAnd(StringUtils.length(g12) == 7, charAt(g12, get(ints, 0, 0), ' ') == 'っ')) {
			//
			testAndAccept(MultimapUtil::containsEntry, multimap, "花文", "かもん", MultimapUtil::remove);
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(StringUtils.substring(g11, 0, 2), StringUtils.substring(g12, 0, ints[0] + 3),
							StringUtils.substring(g11, 2, 3), StringUtils.substring(g12, ints[0] + 3, ints[0] + 4),
							StringUtils.substring(g11, 3), StringUtils.substring(g12, ints[0] + 4)));
			//
		} else if (Boolean.logicalAnd(StringUtils.length(g12) == 7, charAt(g12, get(ints, 0, 0), ' ') == 'ょ')) {
			//
			testAndRun(ArrayUtils.contains(new char[] { 'う', 'く' }, charAt(g12, ints[0] + 1, ' ')),
					() -> MultimapUtil.putAll(multimap,
							ImmutableMultimap.of(StringUtils.substring(g11, 0, 1),
									StringUtils.substring(g12, 0, ints[0] + 2), StringUtils.substring(g11, 1, 2),
									StringUtils.substring(g12, ints[0] + 2, ints[0] + 4), StringUtils.substring(g11, 2),
									StringUtils.substring(g12, ints[0] + 4))));
			//
			testAndRun(
					Boolean.logicalAnd(StringUtils.length(g11) == 4,
							!StringUtils.equals(getCharacterName(g12, ints[0] + 1), HIRAGANA_LETTER_SMALL)),
					() -> MultimapUtil.putAll(multimap, ImmutableMultimap.of(StringUtils.substring(g11, 0, 3),
							StringUtils.substring(g12, 0, StringUtils.length(g12) - 2),
							StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 1),
							StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, ints[0] - 1, ints[0] + 1),
							StringUtils.substring(g11, 3), StringUtils.substring(g12, ints[0] + 3))));
			//
		} // if
			//
		return Pair.of(multimap, intCollection);
		//
	}

	@Nullable
	private static Entry<String, String> toEntry18A1(final Iterable<Entry<String, String>> entries,
			final Entry<String, String> kv) {
		//
		Entry<String, String> entry = null;
		//
		if (Util.iterator(entries) != null) {
			//
			final String k = Util.getKey(kv);
			//
			final String v = Util.getValue(kv);
			//
			for (final Entry<String, String> e : entries) {
				//
				if (Boolean.logicalAnd(StringUtils.startsWith(k, Util.getKey(e)),
						StringUtils.startsWith(v, Util.getValue(e)))) {
					//
					if (entry != null) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					entry = e;
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return entry;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap18A2(@Nullable final IntObjectPair<Entry<String, String>> iop,
			final int indexOf) {
		//
		Multimap<String, String> multimap = null;
		//
		final Entry<String, String> entry = PairUtil.right(iop);
		//
		final String k = Util.getKey(entry);
		//
		final String v = Util.getValue(entry);
		//
		if (iop != null) {
			//
			final int keyInt = iop.keyInt();
			//
			if (charAt(v, keyInt + 1, ' ') == 'う') {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(StringUtils.substring(k, 0, 1),
								StringUtils.substring(v, keyInt - 1, keyInt + 2), StringUtils.substring(k, 1, 2),
								StringUtils.substring(v, keyInt + 2, keyInt + 3), StringUtils.substring(k, 2),
								StringUtils.substring(v, indexOf - 1)));
				//
			} else {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						ImmutableMultimap.of(StringUtils.substring(k, 0, 1),
								StringUtils.substring(v, keyInt - 1, keyInt + 1), StringUtils.substring(k, 1, 2),
								StringUtils.substring(v, keyInt + 1, keyInt + 3), StringUtils.substring(k, 2),
								StringUtils.substring(v, indexOf - 1)));
				//
			} // if
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection18B(
			final IntObjectPair<String> iop, final Entry<String, String> entry, final IntMap<String> intMap,
			final int[] ints) {
		//
		if (IntMap.get(intMap, LENGTH, 0) != 1) {
			//
			return null;
			//
		} // if
			//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		final Multimap<String, String> multimap = testAndApply(Objects::nonNull,
				testAndApply((a, b) -> Objects.nonNull(a), g11, g12, ImmutableMultimap::of, null),
				LinkedHashMultimap::create, null);
		//
		if (StringUtils.length(g11) == 3 && length(ints) > 0 && charAt(g12, ints[0], ' ') == 'ゅ'
				&& StringUtils.length(g12) > 6 && charAt(g12, 6, ' ') == 'ん') {
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, ints[0] + 2),
							StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, ints[0] + 2, ints[0] + 4),
							StringUtils.substring(g11, 2), StringUtils.substring(g12, ints[0] + 4)));
			//
		} else if (StringUtils.length(g11) == 3 && StringUtils.length(g12) > get(ints, 0, 0)
				&& charAt(g12, ints[0], ' ') == 'ょ') {
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 3),
							StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, ints[0] - 1, ints[0] + 2),
							StringUtils.substring(g11, 2), StringUtils.substring(g12, ints[0] + 2)));
			//
		} else if (StringUtils.length(g11) == 4 && StringUtils.length(g12) > 3 && charAt(g12, 2, ' ') == 'ょ') {
			//
			final int l = StringUtils.length(g12);
			//
			if (charAt(g12, 3, ' ') == 'う') {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 1),
								StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, ints[0] - 1, ints[0] + 2),
								StringUtils.substring(g11, 2, 3), StringUtils.substring(g12, ints[0] + 2, l - 2),
								StringUtils.substring(g11, 3), StringUtils.substring(g12, l - 2, l + 1)));
				//
			} else {
				//
				MultimapUtil.putAll(multimap,
						ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 1),
								StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, ints[0] - 1, ints[0] + 1),
								StringUtils.substring(g11, 2, 3), StringUtils.substring(g12, ints[0] + 1, l - 2),
								StringUtils.substring(g11, 3), StringUtils.substring(g12, l - 2, l + 1)));
				//
			} // if
				//
		} else if (StringUtils.length(g11) == 4 && StringUtils.length(g12) == 7 && StringUtils.length(g12) > ints[0] + 2
				&& charAt(g12, ints[0], ' ') == 'ゅ' && charAt(g12, ints[0] + 1, ' ') == 'う') {
			//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, ints[0] + 2),
							StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, ints[0] + 2, ints[0] + 3),
							StringUtils.substring(g11, 2, 3), StringUtils.substring(g12, ints[0] + 3, ints[0] + 4),
							StringUtils.substring(g11, 3), StringUtils.substring(g12, ints[0] + 4)));
			//
		} // if
			//
		return Pair.of(multimap, createIntCollection(iop));
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection19(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Pattern pattern = PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$");
		//
		final Matcher m1 = Util.matcher(pattern, PairUtil.right(iop));
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
		final char space = ' ';
		//
		if (Boolean.logicalAnd(StringUtils.length(g12) > 3, testAndApplyAsChar(x -> StringUtils.length(x) > 3, g12,
				space, x -> charAt(g12, 3, space), null) == 'ゅ')) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 2), StringUtils.substring(g11, 1), StringUtils.substring(g12, 2)),
					createIntCollection(iop));
			//
		} else if (Boolean.logicalAnd(StringUtils.length(g12) > 1, testAndApplyAsChar(x -> StringUtils.length(x) > 1,
				g12, space, x -> charAt(x, 1, space), null) == 'ん')) {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(
					ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 2),
							StringUtils.substring(g11, 1), StringUtils.substring(g12, 2)));
			//
			Util.forEach(Arrays.asList(Triplet.with("象", "じょう", "しょう"), Triplet.with("絣", "がすり", "かすり")), x ->
			//
			testAndAccept((a, b) -> MultimapUtil.containsEntry(a, IValue0Util.getValue0(b), Util.getValue1(b)),
					multimap, x, (a, b) -> {
						//
						MultimapUtil.remove(a, IValue0Util.getValue0(b), Util.getValue1(b));
						//
						MultimapUtil.put(a, IValue0Util.getValue0(b), Util.getValue2(b));
						//
					})
			//
			);
			//
			return Pair.of(multimap, createIntCollection(iop));
			//
		} else if (StringUtils.endsWith(g12, "ん")) {
			//
			final int length = StringUtils.length(g12);
			//
			if (length >= 6) {
				//
				return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
						StringUtils.substring(g12, 0, length - 2), StringUtils.substring(g11, 1),
						StringUtils.substring(g12, length - 2)), createIntCollection(iop));
				//
			} else if (length >= 5) {
				//
				final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12,
						StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, length - 2),
						StringUtils.substring(g11, 1), StringUtils.substring(g12, length - 2)));
				//
				testAndAccept(MultimapUtil::containsEntry, multimap, "隠", "かくし", MultimapUtil::remove);
				//
				testAndAccept(MultimapUtil::containsEntry, multimap, "竝", "ならべ", MultimapUtil::remove);
				//
				testAndAccept(MultimapUtil::containsEntry, multimap, "滕", "ちきり", MultimapUtil::remove);
				//
				testAndAccept(MultimapUtil::containsEntry, multimap, "鼓", "つずみ", MultimapUtil::remove);
				//
				return Pair.of(multimap, createIntCollection(iop));
				//
			} // if
				//
		} else {
			//
			return toMultimapAndIntCollection19A(pattern, keyInt(iop, 0), Pair.of(g11, g12), lines);
			//
		} // if
			//
		return null;
		//
	}

	private static int keyInt(@Nullable final IntObjectPair<?> instance, final int defaultValue) {
		return instance != null ? instance.keyInt() : defaultValue;
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection19A(final Pattern pattern,
			final int index, final Entry<String, String> entry, final Iterable<String> lines) {
		//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		Multimap<String, String> multimap = null;
		//
		IntCollection intCollection = null;
		//
		Matcher m2;
		//
		String g21, g22, cpk, cpv, csk, csv;
		//
		for (int i = 0; i < IterableUtils.size(lines); i++) {
			//
			if (index == i || !Util.matches(m2 = Util.matcher(pattern, IterableUtils.get(lines, i)))
					|| Util.groupCount(m2) <= 1) {
				//
				continue;
				//
			} // if
				//
			if (Util.and(StringUtils.isNotBlank(cpk = Strings.commonPrefix(g11, g21 = Util.group(m2, 1))),
					StringUtils.isNotBlank(cpv = Strings.commonPrefix(g12, g22 = Util.group(m2, 2))),
					StringUtils.endsWith(g22, "ん"))) {
				//
				multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12, cpk, cpv,
						StringUtils.substringAfter(g11, cpk), StringUtils.substringAfter(g12, cpv), g21, g22,
						StringUtils.substringAfter(g21, cpk), StringUtils.substringAfter(g22, cpv)));
				//
				testAndAccept((a, b) -> !IntIterableUtil.containsInt(a, b), intCollection = IntList.create(index), i,
						IntCollectionUtil::addInt);
				//
				break;
				//
			} else if (StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g21))
					&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g22)) && StringUtils.length(g22) == 4) {
				//
				multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12,
						StringUtils.substringBefore(g11, csk), StringUtils.substringBefore(g12, csv), csk, csv, g21,
						g22, StringUtils.substringBefore(g21, csk), StringUtils.substringBefore(g22, csv)));
				//
				testAndAccept((a, b) -> !IntIterableUtil.containsInt(a, b), intCollection = IntList.create(index), i,
						IntCollectionUtil::addInt);
				//
				break;
				//
			} // if
				//
		} // for
			//
		testAndAccept(MultimapUtil::containsEntry, multimap, "入", "いり", MultimapUtil::remove);
		//
		testAndAccept(MultimapUtil::containsEntry, multimap, "経", "たて", MultimapUtil::remove);
		//
		testAndAccept(MultimapUtil::containsEntry, multimap, "抜", "ぬき", MultimapUtil::remove);
		//
		testAndAccept(MultimapUtil::containsEntry, multimap, "緯", "よこ", MultimapUtil::remove);
		//
		testAndAccept(MultimapUtil::containsEntry, multimap, "盲", "めくら", MultimapUtil::remove);
		//
		final Multimap<String, String> mm = multimap;
		//
		Util.forEach(Arrays.asList(Triplet.with("絣", "がすり", "かすり"), Triplet.with("標", "じるし", "しるし"),
				Triplet.with("縞", "じま", "しま")), x ->
		//
		testAndAccept((a, b) -> MultimapUtil.containsEntry(a, IValue0Util.getValue0(b), Util.getValue1(b)), mm, x,
				(a, b) -> {
					//
					MultimapUtil.remove(a, IValue0Util.getValue0(b), Util.getValue1(b));
					//
					MultimapUtil.put(a, IValue0Util.getValue0(b), Util.getValue2(b));
					//
				})
		//
		);
		//
		return testAndApply((a, b) -> Boolean.logicalAnd(a != null, b != null), multimap, intCollection, Pair::of,
				null);
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection20(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{2}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}{2}$"),
				PairUtil.right(iop));
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
		final char space = ' ';
		//
		if (testAndApplyAsChar(x -> StringUtils.length(x) > 1, g12, ' ', x -> charAt(x, 1, space), null) == 'ん') {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(
					ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 2),
							StringUtils.substring(g11, 1), StringUtils.substring(g12, 2)));
			//
			Util.forEach(Arrays.asList(Triplet.with("絣", "がすり", "かすり")),
					//
					a -> testAndAccept(
							b -> MultimapUtil.containsEntry(multimap, IValue0Util.getValue0(b), Util.getValue1(b)), a,
							b -> {
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
			return Pair.of(multimap, createIntCollection(iop));
			//
		} else if (testAndApplyAsChar(x -> StringUtils.length(x) > 2, g12, ' ', x -> charAt(x, 2, space),
				null) == 'ょ') {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 1), StringUtils.substring(g11, 1), StringUtils.substring(g12, 1)),
					createIntCollection(iop));
			//
		} // if
			//
		final Entry<Multimap<String, String>, IntCollection> entry = toMultimapAndIntCollection20A(keyInt(iop, 0),
				Pair.of(g11, g12), lines);
		//
		Util.forEach(
				Arrays.asList(Triplet.with("柏", "がしわ", "かしわ"), Triplet.with("絣", "がすり", "かすり"),
						Triplet.with("杵", "ぎね", "きね"), Triplet.with("車", "ぐるま", "くるま"), Triplet.with("縞", "じま", "しま")),
				a ->
				//
				testAndAccept((b, c) -> MultimapUtil.containsEntry(b, IValue0Util.getValue0(c), Util.getValue1(c)),
						Util.getKey(entry), a, (b, c) -> {
							//
							final String s1 = IValue0Util.getValue0(c);
							//
							MultimapUtil.remove(b, s1, Util.getValue1(c));
							//
							MultimapUtil.put(b, s1, Util.getValue2(c));
							//
						}));
		//
		return entry;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection20A(final int index,
			final Entry<String, String> entry, final Iterable<String> lines) {
		//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		Matcher m2;
		//
		String g21, g22, cpk, cpv, csk, csv;
		//
		Multimap<String, String> multimap = null;
		//
		IntCollection intCollection = null;
		//
		for (int i = 0; i < IterableUtils.size(lines); i++) {
			//
			if (index == i) {
				//
				continue;
				//
			} // if
				//
			if (Util.matches(m2 = Util.matcher(PATTERN_KANJI_HIRAGANA, IterableUtils.get(lines, i)))
					&& Util.groupCount(m2) > 1) {
				//
				if (Boolean.logicalAnd(StringUtils.isNotBlank(cpk = Strings.commonPrefix(g11, g21 = Util.group(m2, 1))),
						StringUtils.isNotBlank(cpv = Strings.commonPrefix(g12, g22 = Util.group(m2, 2))))) {
					//
					multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12, cpk, cpv,
							StringUtils.substringAfter(g11, cpk), StringUtils.substringAfter(g12, cpv), g21, g22));
					//
					if (Util.and(StringUtils.length(g21) > 2, StringUtils.length(g22) > 2,
							StringUtils.endsWith(g22, "ん"))) {
						//
						MultimapUtil.put(multimap, StringUtils.substring(g21, 1, 2),
								StringUtils.substring(g22, StringUtils.length(cpk), StringUtils.length(g22) - 2));
						//
						MultimapUtil.put(multimap, StringUtils.substring(g21, 2),
								StringUtils.substring(g22, StringUtils.length(g22) - 2));
						//
					} // if
						//
					IntCollectionUtil.addInt(intCollection = IntList.create(index), i);
					//
					return Pair.of(multimap, intCollection);
					//
				} else if (StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g21))
						&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g22))
						&& StringUtils.length(g21) == 2) {
					//
					IntCollectionUtil.addInt(intCollection = IntList.create(index), i);
					//
					return Pair.of(LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12,
							StringUtils.substringBefore(g11, csk), StringUtils.substringBefore(g12, csv), csk, csv, g21,
							g22, StringUtils.substringBefore(g21, csk), StringUtils.substringBefore(g22, csv))),
							intCollection);
					//
				} // if
					//
			} // if
				//
		} // for
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection21(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Pattern pattern = PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{4}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}{2}$");
		//
		final Matcher m1 = Util.matcher(pattern, PairUtil.right(iop));
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
		final char space = ' ';
		//
		Matcher m2;
		//
		String g21, g22;
		//
		IntCollection intCollection = null;
		//
		if (ArrayUtils.contains(new char[] {
				testAndApplyAsChar(x -> StringUtils.length(x) > 1, g12, space, x -> charAt(x, 1, space), null),
				testAndApplyAsChar(x -> StringUtils.length(x) > 3, g12, space, x -> charAt(x, 3, space), null) },
				'ん')) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 2), StringUtils.substring(g11, 1), StringUtils.substring(g12, 2)),
					createIntCollection(iop));
			//
		} else if (testAndApplyAsChar(x -> StringUtils.length(x) > 2, g12, space, x -> charAt(x, 2, space),
				null) == 'ん') {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 1), StringUtils.substring(g11, 1), StringUtils.substring(g12, 1)),
					createIntCollection(iop));
			//
		} else if (StringUtils.length(g12) == 3) {
			//
			String csk, csv;
			//
			Multimap<String, String> multimap = null;
			//
			for (int i = 0; i < IterableUtils.size(lines); i++) {
				//
				if (!keyIntEquals(iop, i) && Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, format(
						"^(\\p{InCJKUnifiedIdeographs}%1$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}]+$",
						testAndApply(x -> StringUtils.length(x) > 1, g11, x -> StringUtils.substring(x, 1), null))),
						IterableUtils.get(lines, i))) && Util.groupCount(m2) > 1
						&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g21 = Util.group(m2, 1)))
						&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g22 = Util.group(m2, 2)))) {
					//
					testAndAccept(MultimapUtil::containsEntry,
							multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12,
									StringUtils.substringBefore(g11, csk), StringUtils.substringBefore(g12, csv), csk,
									csv, g21, g22, StringUtils.substringBefore(g21, csk),
									StringUtils.substringBefore(g22, csv))),
							"蒔", "まき", MultimapUtil::remove);
					//
					IntCollectionUtil.addInt(intCollection = createIntCollection(iop), i);
					//
					return Pair.of(multimap, intCollection);
					//
				} // if
					//
			} // for
				//
		} else {
			//
			return toMultimapAndIntCollection21A(patternMap, keyInt(iop, 0), Pair.of(g11, g12), lines);
			//
		} // if
			//
		return null;
		//
	}

	private static String format(final String a, final Object b) {
		return testAndApply((x, y) -> x != null, a, b, String::format, null);
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection21A(
			final PatternMap patternMap, final int index, final Entry<String, String> entry,
			final Iterable<String> lines) {
		//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		Matcher m2;
		//
		String g21, g22;
		//
		IntCollection intCollection = null;
		//
		String cpk, cpv;
		//
		for (int i = 0; i < IterableUtils.size(lines); i++) {
			//
			if (i != index && Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, format(
					"^(%1$s\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}{4}[\\p{InHiragana}\\p{InCJKUnifiedIdeographs}]+$",
					testAndApply(x -> StringUtils.length(x) > 0, g11, x -> StringUtils.substring(x, 0, 1), null))),
					StringUtils.trim(IterableUtils.get(lines, i)))) && Util.groupCount(m2) > 1
					&& StringUtils.isNotBlank(cpk = Strings.commonPrefix(g11, g21 = Util.group(m2, 1)))
					&& StringUtils.isNotBlank(cpv = Strings.commonPrefix(g12, g22 = Util.group(m2, 2)))) {
				//
				IntCollectionUtil.addInt(intCollection = IntList.create(index), i);
				//
				return Pair.of(ImmutableMultimap.of(g11, g12, cpk, cpv, StringUtils.substringAfter(g11, cpk),
						StringUtils.substringAfter(g12, cpv), g21, g22, StringUtils.substringAfter(g21, cpk),
						StringUtils.substringAfter(g22, cpv)), intCollection);
				//
			} // if
				//
		} // for
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection22(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final String right = PairUtil.right(iop);
		//
		Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}\\p{InCJKUnifiedIdeographs}]+$"),
				right);
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 2) {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(Util.group(m1, 2),
					StringUtils.substringAfter(Util.group(m1, 3), Util.group(m1, 1))));
			//
			Util.forEach(Arrays.asList(Triplet.with("縞", "じま", "しま")),
					//
					a -> testAndAccept(
							b -> MultimapUtil.containsEntry(multimap, IValue0Util.getValue0(b), Util.getValue1(b)), a,
							b -> {
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
			return Pair.of(multimap, createIntCollection(iop));
			//
		} // if
			//
		String g11, g12;
		//
		int indexOf;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}+\\p{InCJKSymbolsAndPunctuation}\\p{InCJKUnifiedIdeographs}+$"),
				right)) && Util.groupCount(m1) > 1 && StringUtils.length(g11 = Util.group(m1, 1)) == 2
				&& StringUtils.lastIndexOf(g12 = Util.group(m1, 2), "ん")
						- (indexOf = StringUtils.indexOf(g12, "ん")) == 2) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, indexOf + 1), StringUtils.substring(g11, 1),
					StringUtils.substring(g12, indexOf + 1)), createIntCollection(iop));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection23(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{2})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{2}$"),
				PairUtil.right(iop));
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 1) {
			//
			return null;
			//
		} // if
			//
		final char space = ' ';
		//
		final String g11 = Util.group(m1, 1);
		//
		final String g12 = Util.group(m1, 2);
		//
		if (StringUtils.length(g12) == 3) {
			//
			if (testAndApplyAsChar(x -> StringUtils.length(x) > 0, g12, space, x -> charAt(x, 1, space), null) == 'ん') {
				//
				return Pair.of(ImmutableMultimap.of(g11, g12), createIntCollection(iop));
				//
			} else if (testAndApplyAsChar(x -> StringUtils.length(x) > 2, g12, space, x -> charAt(x, 2, space),
					null) == 'ょ') {
				//
				return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
						StringUtils.substring(g12, 0, 1), StringUtils.substring(g11, 1), StringUtils.substring(g12, 1)),
						createIntCollection(iop));
				//
			} // if
				//
		} else if (testAndApplyAsChar(x -> StringUtils.length(x) > 0, g12, space, x -> charAt(x, 1, space),
				null) == 'ょ') {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 3), StringUtils.substring(g11, 1), StringUtils.substring(g12, 3)),
					createIntCollection(iop));
			//
		} else if (StringUtils.endsWith(g12, "ん")) {
			//
			final int length = StringUtils.length(g12);
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, length - 2), StringUtils.substring(g11, 1),
					StringUtils.substring(g12, length - 2)), createIntCollection(iop));
			//
		} // if
			//
		IntMap<String> intMap = null;
		//
		Entry<Multimap<String, String>, IntCollection> entry = null;
		//
		for (int i = 0; i < IterableUtils.size(lines); i++) {
			//
			if (keyIntEquals(iop, i)) {
				//
				continue;
				//
			} // if
				//
			IntMap.put(intMap = ObjectUtils.getIfNull(intMap, () -> Reflection.newProxy(IntMap.class, new IH())),
					"index1", keyInt(iop, 0));
			//
			IntMap.put(intMap, "index2", i);
			//
			if ((entry = toMultimapAndIntCollection23A(patternMap, Pair.of(g11, g12), intMap,
					IterableUtils.get(lines, i))) != null) {
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
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection23A(
			final PatternMap patternMap, final Entry<String, String> entry, final IntMap<String> intMap,
			final String line) {
		//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap, format(
				"^(%1$s\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+$",
				testAndApply(x -> StringUtils.length(x) > 0, g11, x -> StringUtils.substring(x, 0, 1), null))), line);
		//
		String g21, g22, cpk, cpv;
		//
		final int index1 = IntMap.get(intMap, "index1", 0);
		//
		final int index2 = IntMap.get(intMap, "index2", 0);
		//
		if (Util.matches(m2) && Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(cpk = Strings.commonPrefix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(cpv = Strings.commonPrefix(g12, g22 = Util.group(m2, 2)))) {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12, cpk, cpv,
					StringUtils.substringAfter(g11, cpk), StringUtils.substringAfter(g12, cpv), g21, g22,
					StringUtils.substringAfter(g21, cpk), StringUtils.substringAfter(g22, cpv)));
			//
			testAndAccept(MultimapUtil::containsEntry, multimap, "摺", "すり", MultimapUtil::remove);
			//
			return Pair.of(multimap, IntList.create(index1, index2));
			//
		} // if
			//
		String lcsk, lcsv;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$"),
				line)) && Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(lcsk = longestCommonSubstring(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(lcsv = longestCommonSubstring(g12, g22 = Util.group(m2, 2)))
				&& StringUtils.endsWith(g22, "ん")
				&& !StringUtils.contains(g21,
						testAndApply(x -> StringUtils.length(x) > 0, g11, x -> StringUtils.substring(x, 0, 1), null))
				&& StringUtils.contains(g21,
						testAndApply(x -> StringUtils.length(x) > 1, g11, x -> StringUtils.substring(x, 1), null))) {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12));
			//
			testAndAccept((a, b) -> Boolean.logicalAnd(StringUtils.endsWith(g11, a), StringUtils.endsWith(g12, b)),
					lcsk, lcsv, (a, b) -> MultimapUtil.putAll(multimap, ImmutableMultimap
							.of(StringUtils.substringBefore(g11, a), StringUtils.substringBefore(g12, b), a, b)));
			//
			testAndAccept(
					(a, b) -> Boolean.logicalAnd(StringUtils.indexOf(Util.getKey(a), Util.getKey(b)) == 1,
							StringUtils.indexOf(Util.getValue(a), Util.getValue(b)) > 0),
					Pair.of(g21, g22), Pair.of(lcsk, lcsv), (a, b) -> {
						//
						final String va = Util.getValue(a);
						//
						final String vb = Util.getValue(b);
						//
						final int indexOf = StringUtils.indexOf(va, vb);
						//
						final String ka = Util.getKey(a);
						//
						MultimapUtil.putAll(multimap,
								ImmutableMultimap.of(ka, va, StringUtils.substring(ka, 0, 1),
										StringUtils.substring(va, 0, indexOf),
										StringUtils.substring(ka, StringUtils.length(ka) - 1),
										StringUtils.substring(va, indexOf + StringUtils.length(vb))));

						//
					});
			//
			return Pair.of(multimap, IntList.create(index1, index2));
			//
		} // if
			//
		String csk, csv;
		//
		if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, format(
				"^(\\p{InCJKUnifiedIdeographs}+%1$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}+$",
				testAndApply(x -> StringUtils.length(x) > 0, g11, x -> StringUtils.substring(x, 1), null))), line))
				&& Util.groupCount(m2) > 1
				&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g21 = Util.group(m2, 1)))
				&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g22 = Util.group(m2, 2)))) {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12));
			//
			if (StringUtils.endsWith(StringUtils.substringBefore(g22, csv), "ょ") && StringUtils.length(csv) > 1) {
				//
				csv = StringUtils.substring(csv, 1);
				//
			} // if
				//
			MultimapUtil.putAll(multimap,
					ImmutableMultimap.of(StringUtils.substringBefore(g11, csk), StringUtils.substringBefore(g12, csv),
							csk, csv, g21, g22, StringUtils.substringBefore(g21, csk),
							StringUtils.substringBefore(g22, csv)));
			//
			return Pair.of(multimap, IntList.create(index1, index2));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection24(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+\\p{InCJKUnifiedIdeographs}+\\p{InHiragana}\\p{InCJKUnifiedIdeographs}\\p{InHiragana}$"),
				PairUtil.right(iop));
		//
		String g11;
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 1 && StringUtils.length(g11 = Util.group(m1, 1)) == 2) {
			//
			final String g12 = Util.group(m1, 2);
			//
			if (StringUtils.endsWith(g12, "ん")) {
				//
				final int length = StringUtils.length(g12);
				//
				final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12,
						StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, length - 2),
						StringUtils.substring(g11, 1), StringUtils.substring(g12, length - 2)));
				//
				Util.forEach(Arrays.asList(Triplet.with("浮", "うき", "う")),
						//
						a -> testAndAccept(
								b -> MultimapUtil.containsEntry(multimap, IValue0Util.getValue0(b), Util.getValue1(b)),
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
				return Pair.of(multimap, createIntCollection(iop));
				//
			} // if
				//
			Matcher m2;
			//
			String g21, g22, csk, csv;
			//
			for (int i = 0; i < IterableUtils.size(lines); i++) {
				//
				if (!keyIntEquals(iop, i) && Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, format(
						"^(\\p{InCJKUnifiedIdeographs}%1$s)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}]+",
						testAndApply(x -> StringUtils.length(x) > 0, g11,
								x -> StringUtils.substring(x, StringUtils.length(x) - 1), null))),
						IterableUtils.get(lines, i))) && Util.groupCount(m2) > 1
						&& StringUtils.isNotBlank(csk = Strings.commonSuffix(g11, g21 = Util.group(m2, 1)))
						&& StringUtils.isNotBlank(csv = Strings.commonSuffix(g12, g22 = Util.group(m2, 2)))) {
					//
					return Pair.of(
							ImmutableMultimap.of(g11, g12, StringUtils.substringBefore(g11, csk),
									StringUtils.substringBefore(g12, csv), csk, csv, g21, g22,
									StringUtils.substringBefore(g21, csk), StringUtils.substringBefore(g22, csv)),
							createIntCollection(iop));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection25(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final String right = PairUtil.right(iop);
		//
		Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}{2}\\p{InCJKUnifiedIdeographs}{2}$"),
				right);
		//
		String g11, g12;
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 1 && StringUtils.length(g11 = Util.group(m1, 1)) == 3) {
			//
			final int[] ints = toArray(indexOf(g12 = Util.group(m1, 2), c -> c == 'ん'));
			//
			if (length(ints) == 2) {
				//
				final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12,
						StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, ints[0] + 1),
						StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, ints[0] + 1, ints[1] + 1),
						StringUtils.substring(g11, 2), StringUtils.substring(g12, ints[1] + 1)));
				//
				Util.forEach(Arrays.asList(Triplet.with("革", "がわ", "かわ")),
						//
						a -> testAndAccept(
								b -> MultimapUtil.containsEntry(multimap, IValue0Util.getValue0(b), Util.getValue1(b)),
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
				return Pair.of(multimap, createIntCollection(iop));
				//
			} // if
				//
			final char space = ' ';
			//
			if (Boolean.logicalAnd(
					testAndApplyAsChar(x -> StringUtils.length(x) > 1, g12, space, x -> charAt(x, 1, space),
							null) == 'ょ',
					testAndApplyAsChar(x -> StringUtils.length(x) > 5, g12, space, x -> charAt(x, 5, space),
							null) == 'ん')) {
				//
				return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
						StringUtils.substring(g12, 0, 3), StringUtils.substring(g11, 1, 2),
						StringUtils.substring(g12, 3, 4), StringUtils.substring(g11, 2), StringUtils.substring(g12, 4)),
						createIntCollection(iop));
				//
			} // if
				//
		} else if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs})(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$"),
				right)) && Util.groupCount(m1) > 3) {
			//
			final String g14 = Util.group(m1, 4);
			//
			return Pair.of(
					ImmutableMultimap.of(Util.group(m1, 1), StringUtils.substringBefore(g14, g12 = Util.group(m1, 2)),
							Util.group(m1, 3), StringUtils.substringAfter(g14, g12)),
					createIntCollection(iop));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection26(
			final PatternMap patternMap, @Nullable final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}{3})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}+$"),
				PairUtil.right(iop));
		//
		String g11;
		//
		if (!Util.matches(m1) || Util.groupCount(m1) <= 1 || StringUtils.length(g11 = Util.group(m1, 1)) != 3) {
			//
			return null;
			//
		} // if
			//
		final String g12 = Util.group(m1, 2);
		//
		final int[] ints = toArray(indexOf(g12, c -> c == 'ん'));
		//
		if (length(ints) == 2) {
			//
			if (StringUtils.endsWith(g12, "ん")) {
				//
				final int indexOf = StringUtils.indexOf(g12, "ゅ");
				//
				if (indexOf > 0) {
					//
					return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
							StringUtils.substring(g12, 0, ints[0] + 1), StringUtils.substring(g11, 1, 2),
							StringUtils.substring(g12, indexOf - 1, indexOf + 2), StringUtils.substring(g11, 2),
							StringUtils.substring(g12, indexOf + 2)), createIntCollection(iop));
					//
				} else if (ints[0] == 1) {
					//
					return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
							StringUtils.substring(g12, 0, ints[0] + 1), StringUtils.substring(g11, 1, 2),
							StringUtils.substring(g12, ints[0] + 1, ints[1] - 1), StringUtils.substring(g11, 2),
							StringUtils.substring(g12, ints[1] - 1)), createIntCollection(iop));
					//
				} else if (ints[1] - ints[0] == 2) {
					//
					return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
							StringUtils.substring(g12, 0, ints[0] - 1), StringUtils.substring(g11, 1, 2),
							StringUtils.substring(g12, ints[0] - 1, ints[0] + 1), StringUtils.substring(g11, 2),
							StringUtils.substring(g12, ints[1] - 1)), createIntCollection(iop));
					//
				} // if
					//
			} else if (ints[1] - ints[0] == 2) {
				//
				final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12,
						StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, ints[0] + 1),
						StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, ints[0] + 1, ints[1] + 1),
						StringUtils.substring(g11, 2), StringUtils.substring(g12, ints[1] + 1)));
				//
				Util.forEach(Arrays.asList(Triplet.with("手", "で", "て")),
						//
						a -> testAndAccept(
								b -> MultimapUtil.containsEntry(multimap, IValue0Util.getValue0(b), Util.getValue1(b)),
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
				return Pair.of(multimap, createIntCollection(iop));
				//
			} // if
				//
		} // if
			//
		final char space = ' ';
		//
		int indexOf;
		//
		if (Util.and(
				testAndApplyAsChar(x -> StringUtils.length(x) > 0, g12, space,
						x -> charAt(x, StringUtils.length(x) - 1, space), null) == 'ん',
				(indexOf = StringUtils.indexOf(g12, "ゅ")) > 0, StringUtils.lastIndexOf(g12, "ゅ") == indexOf)) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, indexOf - 1), StringUtils.substring(g11, 1, 2),
					StringUtils.substring(g12, indexOf - 1, indexOf + 1), StringUtils.substring(g11, 2),
					StringUtils.substring(g12, indexOf + 1)), createIntCollection(iop));
			//
		} // if
			//
		final Iterable<IntObjFunction<Entry<String, String>, Entry<Multimap<String, String>, IntCollection>>> functions = Arrays
				.asList(OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection26A,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection26B,
						OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean::toMultimapAndIntCollection26C);
		//
		Entry<Multimap<String, String>, IntCollection> result;
		//
		for (int i = 0; i < IterableUtils.size(functions); i++) {
			//
			if ((result = IntObjFunction.apply(IterableUtils.get(functions, i), keyInt(iop, 0),
					Pair.of(g11, g12))) == null) {
				//
				continue;
				//
			} // if
				//
			return result;
			//
		} // for
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection26A(final int index,
			final Entry<String, String> entry) {
		//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		final char space = ' ';
		//
		final int[] ints = toArray(indexOf(g12, c -> c == 'ゅ'));
		//
		if (length(ints) == 2) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, ints[0] + 2), StringUtils.substring(g11, 1, 2),
					StringUtils.substring(g12, ints[1] - 1, ints[1] + 2), StringUtils.substring(g11, 2),
					StringUtils.substring(g12, ints[1] + 2)), IntList.create(index));
			//
		} else if (testAndApplyAsChar(x -> StringUtils.length(x) > 3, g12, space, x -> charAt(x, 3, space),
				null) == 'ゅ') {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(
					ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 2),
							StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, 2, 5),
							StringUtils.substring(g11, 2), StringUtils.substring(g12, 5)));
			//
			Util.forEach(Arrays.asList(Triplet.with("絣", "がすり", "かすり")),
					//
					a -> testAndAccept(
							b -> MultimapUtil.containsEntry(multimap, IValue0Util.getValue0(b), Util.getValue1(b)), a,
							b -> {
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
			return Pair.of(multimap, IntList.create(index));
			//
		} else if (Boolean.logicalAnd(
				testAndApplyAsChar(x -> StringUtils.length(x) > 1, g12, space, x -> charAt(x, 1, space), null) == 'ん',
				testAndApplyAsChar(x -> StringUtils.length(x) > 4, g12, space, x -> charAt(x, 4, space),
						null) == 'ょ')) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 2), StringUtils.substring(g11, 1, 2),
					StringUtils.substring(g12, 2, 3), StringUtils.substring(g11, 2), StringUtils.substring(g12, 3)),
					IntList.create(index));
			//
		} else if (Boolean.logicalAnd(
				testAndApplyAsChar(x -> StringUtils.length(x) > 2, g12, space, x -> charAt(x, 2, space), null) == 'ん',
				testAndApplyAsChar(x -> StringUtils.length(x) > 4, g12, space, x -> charAt(x, 4, space),
						null) == 'ょ')) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 1), StringUtils.substring(g11, 1, 2),
					StringUtils.substring(g12, 1, 3), StringUtils.substring(g11, 2), StringUtils.substring(g12, 3)),
					IntList.create(index));
			//
		} else if (testAndApplyAsChar(x -> StringUtils.length(x) > 4, g12, space, x -> charAt(x, 4, space),
				null) == 'ょ') {
			//
			if (testAndApplyAsChar(x -> StringUtils.length(x) > 1, g12, space, x -> charAt(x, 1, space), null) == 'ゃ') {
				//
				return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 2),
						StringUtils.substring(g12, 0, 6), StringUtils.substring(g11, 1, 2),
						StringUtils.substring(g12, 3, 6), StringUtils.substring(g11, 2), StringUtils.substring(g12, 6)),
						IntList.create(index));
				//
			} // if
				//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 2),
					StringUtils.substring(g12, 0, 6), StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 3), StringUtils.substring(g11, 2), StringUtils.substring(g12, 6)),
					IntList.create(index));
			//
		} else if (testAndApplyAsChar(x -> StringUtils.length(x) > 2, g12, space, x -> charAt(x, 2, space),
				null) == 'ょ') {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 2),
					StringUtils.substring(g12, 0, 4), StringUtils.substring(g11, 1, 2),
					StringUtils.substring(g12, 1, 4), StringUtils.substring(g11, 2), StringUtils.substring(g12, 4)),
					IntList.create(index));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection26B(final int index,
			final Entry<String, String> entry) {
		//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		final char space = ' ';
		//
		if (testAndApplyAsChar(x -> StringUtils.length(x) > 3, g12, space, x -> charAt(x, 3, space), null) == 'ょ') {
			//
			if (Boolean.logicalAnd(StringUtils.length(g12) == 6, testAndApplyAsChar(x -> StringUtils.length(x) > 1, g12,
					space, x -> charAt(x, 1, space), null) == 'ん')) {
				//
				if (testAndApplyAsChar(x -> StringUtils.length(x) > 4, g12, space, x -> charAt(x, 4, space),
						null) == 'う') {
					//
					final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12,
							StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 2),
							StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, 2, 5),
							StringUtils.substring(g11, 2), StringUtils.substring(g12, 5)));
					//
					Util.forEach(Arrays.asList(Triplet.with("手", "で", "て")),
							//
							a -> testAndAccept(b -> MultimapUtil.containsEntry(multimap, IValue0Util.getValue0(b),
									Util.getValue1(b)), a, b -> {
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
					return Pair.of(multimap, IntList.create(index));
					//
				} // if
					//
				return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
						StringUtils.substring(g12, 0, 2), StringUtils.substring(g11, 1, 2),
						StringUtils.substring(g12, 2, 4), StringUtils.substring(g11, 2), StringUtils.substring(g12, 4)),
						IntList.create(index));
				//
			} else if (StringUtils.length(g12) == 7) {
				//
				if (testAndApplyAsChar(x -> StringUtils.length(x) > 4, g12, space, x -> charAt(x, 4, space),
						null) == 'く') {
					//
					final Multimap<String, String> multimap = LinkedHashMultimap.create(ImmutableMultimap.of(g11, g12,
							StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 2),
							StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, 2, 5),
							StringUtils.substring(g11, 2), StringUtils.substring(g12, 5)));
					//
					Util.forEach(Arrays.asList(Triplet.with("縞", "じま", "しま")),
							//
							a -> testAndAccept(b -> MultimapUtil.containsEntry(multimap, IValue0Util.getValue0(b),
									Util.getValue1(b)), a, b -> {
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
					return Pair.of(multimap, IntList.create(index));
					//
				} // if
					//
				return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 2),
						StringUtils.substring(g12, 0, 5), StringUtils.substring(g11, 1, 2),
						StringUtils.substring(g12, 2, 5), StringUtils.substring(g11, 2), StringUtils.substring(g12, 5)),
						IntList.create(index));
				//
			} else if (StringUtils.length(g12) == 8) {
				//
				return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 1, 2),
						StringUtils.substring(g12, 2, 5), StringUtils.substring(g11, 2), StringUtils.substring(g12, 5)),
						IntList.create(index));
				//
			} else if (StringUtils.length(g12) == 5) {
				//
				return Pair.of(
						ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 2), StringUtils.substring(g12, 2)),
						IntList.create(index));
				//
			} // if
				//
		} else if (Boolean.logicalAnd(
				testAndApplyAsChar(x -> StringUtils.length(x) > 2, g12, space, x -> charAt(x, 2, space), null) == 'ん',
				testAndApplyAsChar(x -> StringUtils.length(x) > 4, g12, space, x -> charAt(x, 4, space),
						null) == 'ゅ')) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 1), StringUtils.substring(g11, 1, 2),
					StringUtils.substring(g12, 1, 3), StringUtils.substring(g11, 2), StringUtils.substring(g12, 3)),
					IntList.create(index));
			//
		} else if (testAndApplyAsChar(x -> StringUtils.length(x) > 2, g12, space, x -> charAt(x, 2, space),
				null) == 'ゅ') {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 1), StringUtils.substring(g11, 1, 2),
					StringUtils.substring(g12, 1, 4), StringUtils.substring(g11, 2), StringUtils.substring(g12, 4)),
					IntList.create(index));
			//
		} // if
			//
		final int[] ints = toArray(indexOf(g12, c -> c == 'ん'));
		//
		if (length(ints) > 1) {
			//
			return Pair.of(
					ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
							StringUtils.substring(g12, 0, get(ints, 0, 0) + 1), StringUtils.substring(g11, 1, 2),
							StringUtils.substring(g12, get(ints, 0, 0) + 1, get(ints, 1, 0) + 1),
							StringUtils.substring(g11, 2), StringUtils.substring(g12, get(ints, 1, 0) + 1)),
					IntList.create(index));
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection26C(final int index,
			final Entry<String, String> entry) {
		//
		final String g11 = Util.getKey(entry);
		//
		final String g12 = Util.getValue(entry);
		//
		final char space = ' ';
		//
		if (testAndApplyAsChar(x -> StringUtils.length(x) > 3, g12, space, x -> charAt(x, 3, space), null) != 'ん') {
			//
			return null;
			//
		} // if
			//
		final int length = StringUtils.length(g12);
		//
		if (length == 7) {
			//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(
					ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 2), StringUtils.substring(g12, 0, 4),
							StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, 2, 4),
							StringUtils.substring(g11, 2), StringUtils.substring(g12, 4)));
			//
			Util.forEach(Arrays.asList(Triplet.with("絣", "がすり", "かすり")),
					//
					a -> testAndAccept(
							b -> MultimapUtil.containsEntry(multimap, IValue0Util.getValue0(b), Util.getValue1(b)), a,
							b -> {
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
			return Pair.of(multimap, IntList.create(index));
			//
		} else if (length == 6) {
			//
			if (testAndApplyAsChar(x -> StringUtils.length(x) > 0, g12, space, x -> charAt(x, 0, space), null) == 'ゆ') {
				//
				return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
						StringUtils.substring(g12, 0, 2), StringUtils.substring(g11, 1, 2),
						StringUtils.substring(g12, 2, 4)), IntList.create(index));
				//
			} // if
				//
			final Multimap<String, String> multimap = LinkedHashMultimap.create(
					ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1), StringUtils.substring(g12, 0, 2),
							StringUtils.substring(g11, 1, 2), StringUtils.substring(g12, 2, 4),
							StringUtils.substring(g11, 2), StringUtils.substring(g12, 4)));
			//
			Util.forEach(Arrays.asList(Triplet.with("菊", "ぎく", "きく")),
					//
					a -> testAndAccept(
							b -> MultimapUtil.containsEntry(multimap, IValue0Util.getValue0(b), Util.getValue1(b)), a,
							b -> {
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
			return Pair.of(multimap, IntList.create(index));
			//
		} else if (testAndApplyAsChar(x -> StringUtils.length(x) > 0, g12, space, x -> charAt(x, 0, space),
				null) == testAndApplyAsChar(x -> StringUtils.length(x) > 1, g12, space, x -> charAt(x, 1, space),
						null)) {
			//
			if (testAndApplyAsChar(x -> StringUtils.length(x) > 1, g11, space, x -> charAt(x, 1, space), null) == '子') {
				//
				return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 2),
						StringUtils.substring(g12, 0, 2), StringUtils.substring(g11, 1, 2),
						StringUtils.substring(g12, 1, 2), StringUtils.substring(g11, 2), StringUtils.substring(g12, 2)),
						IntList.create(index));
				//
			} // if
				//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 2),
					StringUtils.substring(g12, 0, 2), StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 1), StringUtils.substring(g11, 1, 2),
					StringUtils.substring(g12, 1, 2), StringUtils.substring(g11, 2), StringUtils.substring(g12, 2)),
					IntList.create(index));
			//
		} else if (testAndApplyAsChar(x -> StringUtils.length(x) > 1, g11, space, x -> charAt(x, 1, space),
				null) == '子') {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 2),
					StringUtils.substring(g12, 0, 2), StringUtils.substring(g11, 2), StringUtils.substring(g12, 2)),
					IntList.create(index));
			//
		} else if (testAndApplyAsChar(x -> StringUtils.length(x) > 0, g11, space, x -> charAt(x, 0, space),
				null) == testAndApplyAsChar(x -> StringUtils.length(x) > 2, g11, space, x -> charAt(x, 2, space),
						null)) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 2),
					StringUtils.substring(g12, 0, 2), StringUtils.substring(g11, 1, 2),
					StringUtils.substring(g12, 1, 2), StringUtils.substring(g11, 2), StringUtils.substring(g12, 2)),
					IntList.create(index));
			//
		} else if (testAndApplyAsChar(x -> StringUtils.length(x) > 1, g11, space, x -> charAt(x, 1, space),
				null) == '字') {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 1), StringUtils.substring(g11, 1, 2),
					StringUtils.substring(g12, 1, 2), StringUtils.substring(g11, 2), StringUtils.substring(g12, 2)),
					IntList.create(index));
			//
		} else if (Util.or(testAndApplyAsChar(x -> StringUtils.length(x) > 1, g12, space, x -> charAt(x, 1, space),
				null) == testAndApplyAsChar(x -> StringUtils.length(x) > 2, g12, space, x -> charAt(x, 2, space), null),
				ArrayUtils.contains(new char[] { 'え', 'の', 'だ', 'こ', 'ぐ', 'び' },
						testAndApplyAsChar(x -> StringUtils.length(x) > 0, g12, space, x -> charAt(x, 0, space), null)),
				testAndApplyAsChar(x -> StringUtils.length(x) > 0, g11, space, x -> charAt(x, 0, space),
						null) == '鉸')) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 2),
					StringUtils.substring(g12, 0, 2), StringUtils.substring(g11, 2), StringUtils.substring(g12, 2)),
					IntList.create(index));
			//
		} else if (Boolean
				.logicalOr(
						ArrayUtils.contains(new char[] { '幾', '真' },
								testAndApplyAsChar(x -> StringUtils.length(x) > 0, g11, space, x -> charAt(x, 0, space),
										null)),
						testAndApplyAsChar(x -> StringUtils.length(x) > 0, g12, space, x -> charAt(x, 0, space),
								null) == 'は')) {
			//
			return Pair.of(ImmutableMultimap.of(g11, g12, StringUtils.substring(g11, 0, 2),
					StringUtils.substring(g12, 0, 2), StringUtils.substring(g11, 0, 1),
					StringUtils.substring(g12, 0, 1), StringUtils.substring(g11, 1, 2),
					StringUtils.substring(g12, 1, 2), StringUtils.substring(g11, 2), StringUtils.substring(g12, 2)),
					IntList.create(index));
			//
		} // if
			//
		return null;
		//
	}

	private static <T> char testAndApplyAsChar(final Predicate<T> predicate, @Nullable final T value,
			final char defaultValue, final ToCharFunction<T> functionTrue,
			@Nullable final ToCharFunction<T> functionFalse) {
		return Util.test(predicate, value) ? applyAsChar(functionTrue, value, defaultValue)
				: applyAsChar(functionFalse, value, defaultValue);
	}

	private static <T> char applyAsChar(@Nullable final ToCharFunction<T> instance, @Nullable final T value,
			final char defaultValue) {
		return instance != null ? instance.applyAsChar(value) : defaultValue;
	}

	private static char charAt(@Nullable final String string, final int index, final char c) {
		return string != null ? string.charAt(index) : c;
	}

	private static boolean equals(@Nullable final String string, final int index, final char c) {
		return string != null && string.charAt(index) == c;
	}

	@Nullable
	private static IntStream indexOf(@Nullable final String string, final CharPredicate charPredicate) {
		//
		return filter(IntStream.range(0, StringUtils.length(string)),
				i -> string != null && test(charPredicate, string.charAt(i)));
		//
	}

	private static boolean test(@Nullable final CharPredicate instance, final char c) {
		return instance != null && instance.test(c);
	}

	@Nullable
	private static String getCharacterName(@Nullable final String instance, final int index) {
		return instance != null ? Character.getName(instance.charAt(index)) : null;
	}

	@Nullable
	private static int[] toArray(@Nullable final IntStream instance) {
		return instance != null ? instance.toArray() : null;
	}

	private static long length(@Nullable final int[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Nullable
	private static IntStream filter(@Nullable final IntStream intStream, final IntPredicate intPredicate) {
		return intStream != null ? intStream.filter(intPredicate) : intStream;
	}

	private static void testAndRun(final boolean b, @Nullable final Runnable runnable) {
		if (b && runnable != null) {
			runnable.run();
		}
	}

	private static <T> void testAndAccept(@Nullable final Predicate<T> predicate, final T value,
			@Nullable final Consumer<T> consumer) {
		if (predicate != null && predicate.test(value) && consumer != null) {
			consumer.accept(value);
		}
	}

	@Nullable
	private static TextStringBuilder deleteLastCharacter(@Nullable final TextStringBuilder instance) {
		//
		final int length = StringUtils.length(instance);
		//
		return length > 0 ? delete(instance, length - 1, length) : instance;
		//
	}

	@Nullable
	private static TextStringBuilder deleteLast2Characters(@Nullable final TextStringBuilder instance) {
		//
		final int length = StringUtils.length(instance);
		//
		return length > 1 ? delete(instance, length - 2, length) : instance;
		//
	}

	@Nullable
	private static TextStringBuilder delete(@Nullable final TextStringBuilder instance, final int startIndex,
			final int endIndex) {
		return instance != null ? instance.delete(startIndex, endIndex) : instance;
	}

	@Nullable
	private static TextStringBuilder append(@Nullable final TextStringBuilder instance, @Nullable final String str) {
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

	private static <T, U, V> void testAndAccept(final TriPredicate<T, U, V> instance, @Nullable final T t, final U u,
			@Nullable final V v, @Nullable final TriConsumer<T, U, V> consumer) {
		if (TriPredicateUtil.test(instance, t, u, v) && consumer != null) {
			consumer.accept(t, u, v);
		} // if
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, @Nullable final T t, @Nullable final U u,
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

	private static <T> void testAndAccept(final ObjIntPredicate<T> predicate, @Nullable final T a, final int b,
			@Nullable final ObjIntConsumer<T> consumer) {
		if (ObjIntPredicateUtil.test(predicate, a, b)) {
			Util.accept(consumer, a, b);
		}
	}

	private static String longestCommonSubstring(@Nullable final String a, @Nullable final String b) {
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

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, @Nullable final T t, @Nullable final U u,
			final BiFunction<T, U, R> functionTrue, @Nullable final BiFunction<T, U, R> functionFalse) {
		return Util.test(predicate, t, u) ? Util.apply(functionTrue, t, u) : Util.apply(functionFalse, t, u);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}