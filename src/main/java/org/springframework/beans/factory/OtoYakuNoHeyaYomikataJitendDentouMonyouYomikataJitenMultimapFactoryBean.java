package org.springframework.beans.factory;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.d2ab.collection.ints.IntCollection;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntIterableUtil;
import org.d2ab.collection.ints.IntList;
import org.d2ab.function.ObjIntPredicate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
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
					Util.getKey(entry = toMultimap(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
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
	private static Entry<Multimap<String, String>, IntCollection> toMultimap(final PatternMap patternMap,
			final IntObjectPair<String> iop, final Iterable<String> lines) {
		//
		Multimap<String, String> multimap = null;
		//
		IntCollection intCollection = null;
		//
		final String right = PairUtil.right(iop);
		//
		Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+\\p{InHiragana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InCJKSymbolsAndPunctuation}\\p{InHalfwidthAndFullwidthForms}]+$"),
				right);
		//
		String g12, g21, g22, csk, csv;
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 1) {
			//
			if (iop != null) {
				//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
						iop.keyInt());
				//
			} // if
				//
			g12 = Util.group(m1, 2);
			//
			Matcher m2 = null;
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
		} else if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$"),
				right)) && Util.groupCount(m1) > 3) {
			//
			if (iop != null) {
				//
				IntCollectionUtil.addInt(intCollection = ObjectUtils.getIfNull(intCollection, IntList::create),
						iop.keyInt());
				//
			} // if
				//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.group(m1, 1),
					Util.group(m1, 2));
			//
			final String g13 = Util.group(m1, 3);
			//
			final String g14 = Util.group(m1, 4);
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), g13, g14);
			//
			Matcher m2;
			//
			String line, lcsk, lcsv;
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
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
			//
			if (Util.iterator(entries) != null) {
				//
				String cpk, cpv;
				//
				for (final Entry<String, String> e1 : entries) {
					//
					for (int j = 0; j < IterableUtils.size(lines); j++) {
						//
						if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
								"^(\\p{InCJKUnifiedIdeographs}{6})\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
								IterableUtils.get(lines, j)))
								&& Util.groupCount(m2) > 1
								&& StringUtils
										.isNotBlank(csk = getCommonSuffix(g21 = Util.group(m2, 1), Util.getKey(e1)))
								&& StringUtils.isNotBlank(
										csv = getCommonSuffix(g22 = Util.group(m2, 2), Util.getValue(e1)))) {
							//
							MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
									ImmutableMultimap.of(g21, g22, csk, csv));
							//
							testAndAccept((a, b) -> !IntIterableUtil.containsInt(a, b),
									intCollection = ObjectUtils.getIfNull(intCollection, IntList::create), j,
									(a, b) -> IntCollectionUtil.addInt(a, b));
							//
							break;
							//
						} // if
							//
					} // for
						//
					for (final Entry<String, String> e2 : entries) {
						//
						if (Objects.equals(e1, e2)) {
							//
							continue;
							//
						} // if
							//
						if (Boolean.logicalAnd(
								StringUtils.isNotBlank(
										cpk = StringUtils.getCommonPrefix(Util.getKey(e1), Util.getKey(e2))),
								StringUtils.isNotBlank(
										cpv = StringUtils.getCommonPrefix(Util.getValue(e1), Util.getValue(e2))))) {
							//
							MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
									cpk, cpv);
							//
						} // if
							//
					} // for
						//
				} // for
					//
			} // if
				//
		} // if
			//
		MultimapUtil.remove(multimap, "入替", "いれかわり");
		//
		return Pair.of(multimap, intCollection);
		//
	}

	private static <T> void testAndAccept(@Nullable final ObjIntPredicate<T> predicate, final T a, final int b,
			@Nullable final ObjIntConsumer<T> consumer) {
		if (predicate != null && predicate.test(a, b) && consumer != null) {
			consumer.accept(a, b);
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