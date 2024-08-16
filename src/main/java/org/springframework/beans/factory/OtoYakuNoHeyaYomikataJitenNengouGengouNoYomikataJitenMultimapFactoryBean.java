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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.function.TriFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean
		extends StringMultiMapFromResourceFactoryBean {

	private static final Pattern PATTERN_IN_CJK_UNIFIED_IDEOGRAPHS = Pattern.compile("^\\p{InCJKUnifiedIdeographs}+$");

	@URL("https://hiramatu-hifuka.com/onyak/rekisi/nenngo.html")
	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		IValue0<Multimap<String, String>> iValue0 = getIvalue0();
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		final Element document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
		//
		final List<Element> trs = ElementUtil.select(document, "tr");
		//
		Iterable<Element> tds;
		//
		PatternMap patternMap = null;
		//
		Multimap<String, String> multimap = null;
		//
		int size, index;
		//
		Element td0;
		//
		String s;
		//
		for (int i = 0; i < IterableUtils.size(trs); i++) {
			//
			if (!ArrayUtils.contains(new int[] { 4, 5 },
					size = IterableUtils.size(tds = ElementUtil.children(IterableUtils.get(trs, i))))) {
				//
				continue;
				//
			} // if
				//
			if ((iValue0 = toMultimap(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
					s = ElementUtil.text(IterableUtils.get(tds,
							index = (NodeUtil.hasAttr(td0 = IterableUtils.get(tds, 0), "rowspan")
									|| NodeUtil.hasAttr(td0, "bgcolor")) ? 1 : 0)),
					ElementUtil.text(IterableUtils.get(tds, index + 1)))) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						IValue0Util.getValue0(iValue0));
				//
			} // if
				//
			if ((iValue0 = toMultimap(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), s,
					ElementUtil.text(IterableUtils.get(tds, size - 1)))) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						IValue0Util.getValue0(iValue0));
				//
			} // if
				//
		} // for
			//
		MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
				toMultimap(ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), ElementUtil.select(document, "p")));
		//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final Iterable<Element> es) {
		//
		Matcher matcher = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(es); i++) {
			//
			if ((matcher = Util.matcher(
					PatternMap.getPattern(patternMap, "(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）"),
					ElementUtil.text(IterableUtils.get(es, i)))) == null) {
				//
				continue;
				//
			} // if
				//
			while (Util.find(matcher) && Util.groupCount(matcher) > 1) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 1), Util.group(matcher, 2));
				//
			} // while
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
		final List<TriFunction<PatternMap, String, String, IValue0<Multimap<String, String>>>> functions = Arrays
				.asList(OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean::toMultimap1,
						OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean::toMultimap2,
						OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean::toMultimap3,
						OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean::toMultimap4);
		//
		IValue0<Multimap<String, String>> iValue0 = null;
		//
		for (int i = 0; i < IterableUtils.size(functions); i++) {
			//
			if ((iValue0 = TriFunctionUtil.apply(IterableUtils.get(functions, i), patternMap, s1, s2)) != null) {
				//
				return iValue0;
				//
			} // if
				//
		} // for
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap1(final PatternMap patternMap, final String s1,
			final String s2) {
		//
		final boolean isCJKUnifiedIdeographs = Util.matches(Util.matcher(PATTERN_IN_CJK_UNIFIED_IDEOGRAPHS, s1));
		//
		Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InHiragana}+)）$"), s2);
		//
		Multimap<String, String> multimap = null;
		//
		if (Boolean.logicalAnd(isCJKUnifiedIdeographs, Util.matches(m2) && Util.groupCount(m2) > 0)) {
			//
			return Unit.with(ImmutableMultimap.of(s1, Util.group(m2, 1)));
			//
		} else if (isCJKUnifiedIdeographs && Util.matches(m2 = Util
				.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InHiragana}+)）\\s（(\\p{InHiragana}+)）$"), s2))) {
			//
			for (int j = 1; j <= Util.groupCount(m2); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
						Util.group(m2, j));
				//
			} // for
				//
			return Unit.with(multimap);
			//
		} // if
			//
		Matcher m1;
		//
		if (Util.matches(m1 = Util.matcher(
				PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)\\p{InCJKSymbolsAndPunctuation}+$"),
				s1)) && Util.groupCount(m1) > 0) {
			//
			if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InHiragana}+)）$"), s2))
					&& Util.groupCount(m2) > 0) {
				//
				return Unit.with(ImmutableMultimap.of(Util.group(m1, 1), Util.group(m2, 1)));
				//
			} else if (Util.matches(m2 = Util.matcher(
					PatternMap.getPattern(patternMap, "^（(\\p{InHiragana}+)）\\s+（(\\p{InHiragana}+)）$"), s2))) {
				//
				for (int j = 1; j <= Util.groupCount(m2); j++) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, 1), Util.group(m2, j));
					//
				} // for
					//
				return Unit.with(multimap);
				//
			} // if
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
		if (Util.matches(Util.matcher(PATTERN_IN_CJK_UNIFIED_IDEOGRAPHS, s1))) {
			//
			Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap,
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+$"),
					s2);
			//
			if (Util.matches(m2) && Util.groupCount(m2) > 0) {
				//
				return Unit.with(ImmutableMultimap.of(s1, Util.group(m2, 1)));
				//
			} // if
				//
			final List<String> patterns = Arrays.asList(
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\s|\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）[\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\s|\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）[\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}|\\p{InCJKSymbolsAndPunctuation}]+（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\s|\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+[\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）$",
					"^[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}|\\p{InHalfwidthAndFullwidthForms}|\\p{InBasicLatin}|〜]+\\s\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKSymbolsAndPunctuation}||\\p{InCJKUnifiedIdeographs}]+$",
					"^\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+[\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\((\\p{InHiragana}+)）[\\s|\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}|\\p{InCJKSymbolsAndPunctuation}|\\s]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}|\\s]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\((\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\s|\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+』（(\\p{InHiragana}+)）[\\s]+\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）[\\s]+\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\((\\p{InHiragana}+)）\\s[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）\\s+\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\s\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）[\\p{InHalfwidthAndFullwidthForms}|\\p{InBasicLatin}|\\p{InCJKSymbolsAndPunctuation}]+（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\((\\p{InHiragana}+)）\\s[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）[\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s+|\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InBasicLatin}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\((\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\s]+（(\\p{InHiragana}+)）\\s+\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\s+\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\((\\p{InHiragana}+)）\\s+[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}||\\p{InHiragana}]+（(\\p{InHiragana}+)）\\s+[\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\s[\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\((\\p{InHiragana}+)）\\s+[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）\\s+\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InHalfwidthAndFullwidthForms}]+[\\s|\\p{InCJKSymbolsAndPunctuation}]+（(\\p{InHiragana}+)）\\p{InArrows}（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}|\\s]+\\((\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）[\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）[\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）[\\p{InHiragana}|\\s|\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）\\s[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）\\s\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InArrows}+（(\\p{InHiragana}+)）\\p{InHiragana}+\\s\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+$",
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InArrows}+（(\\p{InHiragana}+)）\\p{InHiragana}+\\s+\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+$");
			//
			for (final String pattern : patterns) {
				//
				if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, pattern), s2))) {
					//
					for (int j = 1; j <= Util.groupCount(m2); j++) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
								Util.group(m2, j));
						//
					} // for
						//
					return Unit.with(multimap);
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
	private static IValue0<Multimap<String, String>> toMultimap3(final PatternMap patternMap, final String s1,
			final String s2) {
		//
		Matcher m2 = null;
		//
		if (Util.matches(Util.matcher(PATTERN_IN_CJK_UNIFIED_IDEOGRAPHS, s1))) {
			//
			if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\s+\\p{InCJKUnifiedIdeographs}+（\\p{InCJKUnifiedIdeographs}+）\\p{InCJKUnifiedIdeographs}+[\\s|\\p{InCJKSymbolsAndPunctuation}]+\\p{InCJKUnifiedIdeographs}+：(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）\\s+\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+$"),
					s2)) && Util.groupCount(m2) > 3) {
				//
				return Unit.with(ImmutableMultimap.of(Util.group(m2, 1), Util.group(m2, 2), s1, Util.group(m2, 3), s1,
						Util.group(m2, 4)));
				//
			} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}|\\p{InHalfwidthAndFullwidthForms}|\\s]+50[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）\\p{InHiragana}+$"),
					s2)) && Util.groupCount(m2) > 0) {
				//
				return Unit.with(ImmutableMultimap.of(s1, Util.group(m2, 1)));
				//
			} else if (Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap,
					"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）[\\p{InHiragana}]+\\s+[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+(暦)[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+（(\\p{InHiragana}+)）[\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+[\\s|\\p{InCJKSymbolsAndPunctuation}]+\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}([\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+)（(\\p{InHiragana}+)）\\s\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）\\p{InHiragana}+$"),
					s2)) && Util.groupCount(m2) > 7) {
				//
				return toMultimap(patternMap, s1, m2);
				//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	private static IValue0<Multimap<String, String>> toMultimap(final PatternMap patternMap, final String s1,
			final Matcher m2) {
		//
		Multimap<String, String> multimap = null;
		//
		String group;
		//
		IValue0<String> hiragana = null;
		//
		List<String> ss1 = null;
		//
		String[] ss2;
		//
		Matcher m;
		//
		List<UnicodeBlock> ubs;
		//
		for (int i = 1; i <= Util.groupCount(m2); i++) {
			//
			group = Util.group(m2, i);
			//
			if (ArrayUtils.contains(new int[] { 1, 6, 7, 8 }, i)) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, group);
				//
			} else if (i == 3) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m2, i - 1), group);
				//
			} else if (i == 4) {
				//
				if (Util.matches(m = Util.matcher(
						PatternMap.getPattern(patternMap,
								"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana})(\\p{InCJKUnifiedIdeographs}+)$"),
						group))) {
					//
					for (int j = 1; j <= Util.groupCount(m); j++) {
						//
						if (CollectionUtils.isEqualCollection(
								Collections.singleton(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
								ubs = getUnicodeBlocks(group = Util.group(m, j)))) {
							//
							Util.add(ss1 = ObjectUtils.getIfNull(ss1, ArrayList::new), group);
							//
						} else if (CollectionUtils.isEqualCollection(Collections.singleton(UnicodeBlock.HIRAGANA),
								ubs)) {
							//
							if (hiragana != null) {
								//
								throw new IllegalStateException();
								//
							} else {
								//
								hiragana = Unit.with(group);
								//
							} // if
								//
						} // if
					} // for
						//
				} // if
					//
			} else if (i == 5) {
				//
				ss2 = testAndApply((a, b) -> b != null, group, hiragana,
						(a, b) -> StringUtils.split(a, IValue0Util.getValue0(b)), null);
				//
				for (int j = 0; ss2 != null && j < ss2.length; j++) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							IterableUtils.get(ss1, Math.min(j, IterableUtils.size(ss1))), ss2[j]);
					//
				} // for
					//
			} // if
				//
		} // for
			//
		return Unit.with(multimap);
		//
	}

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, @Nullable final U u,
			final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse) {
		return Util.test(predicate, t, u) ? Util.apply(functionTrue, t, u) : Util.apply(functionFalse, t, u);
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap4(final PatternMap patternMap, final String s1,
			final String s2) {
		//
		Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\p{InCJKUnifiedIdeographs}]+\\p{InCJKSymbolsAndPunctuation}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)[\\p{InHalfwidthAndFullwidthForms}|\\p{InCJKSymbolsAndPunctuation}|\\p{InHiragana}|\\p{InCJKUnifiedIdeographs}]+$"),
				s2);
		//
		if (Util.matches(m2) && Util.groupCount(m2) > 1) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m2, 1), Util.group(m2, 2)));
			//
		} // if
			//
		final Matcher m1 = Util.matcher(
				PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)[^\\p{InCJKUnifiedIdeographs}]+$"),
				s1);
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 0 && Util.matches(m2 = Util.matcher(PatternMap.getPattern(
				patternMap,
				"^[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）\\s+[\\p{InCJKSymbolsAndPunctuation}|\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+（(\\p{InHiragana}+)）\\s\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InArrows}（(\\p{InHiragana}+)）\\p{InHiragana}+\\s\\p{InCJKUnifiedIdeographs}+（(\\p{InHiragana}+)）\\p{InHalfwidthAndFullwidthForms}（(\\p{InHiragana}+)）\\p{InHiragana}+$"),
				s2))) {
			//
			Multimap<String, String> multimap = null;
			//
			for (int j = 1; j <= Util.groupCount(m2); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m1, 1), Util.group(m2, j));
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
	private static List<UnicodeBlock> getUnicodeBlocks(final String string) {
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
		} // if
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