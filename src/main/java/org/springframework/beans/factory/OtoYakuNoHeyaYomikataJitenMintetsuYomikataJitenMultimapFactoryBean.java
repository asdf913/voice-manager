package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.collect.TreeMultimap;

/*
 * https://hiramatu-hifuka.com/onyak/onyak2/tetu-min.html
 */
public class OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean
		extends StringMultiMapFromResourceFactoryBean {

	@URL("https://hiramatu-hifuka.com/onyak/onyak2/tetu-min.html")
	private String url = null;

	private Iterable<Link> links = null;

	@Note("Text")
	private IValue0<String> text = null;

	@Note("Description")
	private IValue0<String> description = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setLinks(final Iterable<Link> links) {
		this.links = links;
	}

	public void setText(final String text) {
		this.text = Unit.with(text);
	}

	public void setDescription(final String description) {
		this.description = Unit.with(description);
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final IValue0<Multimap<String, String>> multimap = getIvalue0();
		//
		if (multimap != null) {
			//
			return IValue0Util.getValue0(multimap);
			//
		} // if
			//
		List<Link> ls = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.spliterator(links), x -> StreamSupport.stream(x, false), null),
				x -> text != null && x != null && Objects.equals(x.getText(), IValue0Util.getValue0(text))));
		//
		int size = IterableUtils.size(ls);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (size == 1) {
			//
			return createMultimap(Link.getUrl(IterableUtils.get(ls, 0)));
			//
		} // if
			//
		if ((size = IterableUtils.size(ls = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.spliterator(links), x -> StreamSupport.stream(x, false), null),
				x -> description != null && x != null
						&& Objects.equals(x.getDescription(), IValue0Util.getValue0(description)))))) > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (size == 1) {
			//
			return createMultimap(Link.getUrl(IterableUtils.get(ls, 0)));
			//
		} // if
			//
		return createMultimap(url);
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimap(final String url) throws Exception {
		//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
		//
		final List<Element> es1 = ElementUtil.select(document, "table[border='1']");
		//
		Element e1, e2 = null;
		//
		List<String> firstRowTexts = null;
		//
		Multimap<String, String> multimap = null, mm = null;
		//
		PatternMap patternMap = null;
		//
		for (int i = 0; i < IterableUtils.size(es1); i++) {
			//
			if (ElementUtil.childrenSize(e1 = IterableUtils.get(es1, i)) != 1 || !Objects
					.equals(ElementUtil.tagName(e2 = IterableUtils.get(ElementUtil.children(e1), 0)), "tbody")
					|| ElementUtil.childrenSize(e2) <= 1) {
				//
				continue;
				//
			} // if
				//
			if ((mm = toMultimap(e2,
					firstRowTexts = ObjectUtils.getIfNull(firstRowTexts, () -> Arrays.asList("会社名など", "路線名", "読み方")),
					patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new))) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), mm);
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Element e, final Object firstRowTexts,
			final PatternMap patternMap) {
		//
		Multimap<String, String> multimap = null, mm = null;
		//
		List<String> strings = null;
		//
		String s0, s1 = null;
		//
		int size = 0;
		//
		for (int j = 0; j < ElementUtil.childrenSize(e); j++) {
			//
			if (Objects
					.equals(firstRowTexts,
							strings = Util.toList(Util.map(
									Util.stream(ElementUtil.children(IterableUtils.get(ElementUtil.children(e), j))),
									ElementUtil::text)))) {
				//
				continue;
				//
			} // if
				//
			s0 = IterableUtils.get(strings, 0);
			//
			s1 = IterableUtils.get(strings, 1);
			//
			if (((size = IterableUtils.size(strings)) > 2
					&& (mm = toMultimap(patternMap, s0, s1, IterableUtils.get(strings, 2))) != null)
					|| (size > 1 && (mm = toMultimap(patternMap, s0, s1)) != null)) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), mm);
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s0, final String s1,
			final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		final Multimap<String, String> mm = toMultimap(patternMap, s0);
		//
		if (mm != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), mm);
			//
		} // if
			//
		Matcher m1, m2 = null;
		//
		String g1, g2, cp;
		//
		String[] ss = null;
		//
		if (Boolean.logicalAnd(
				Objects.equals(getUnicodeBlocks(s1), Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
				Objects.equals(getUnicodeBlocks(s2), Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), s1, s2);
			//
		} else if (Boolean.logicalAnd(Util.matches((m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InHiragana}+)?\\p{InCJK_Symbols_And_Punctuation}+\\p{InCJKUnifiedIdeographs}+\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"),
				s2))), Util.groupCount(m1) > 2)) {
			//
			final BiPredicate<String, String> biPredicate = createBiPredicate();
			//
			final BiConsumer<String, String> biConsumer = createBiConsumer(
					multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create));
			//
			testAndAccept(biPredicate, s1, Util.group(m1, 1), biConsumer);
			//
			testAndAccept(biPredicate, Util.group(m1, 2), Util.group(m1, 3), biConsumer);
			//
		} else if (Util
				.matches((m1 = Util.matcher(
						PatternMap.getPattern(patternMap,
								"^(\\p{InHiragana}+)\\p{InHALFWIDTH_AND_FULLWIDTH_FORMS}\\p{InCJKUnifiedIdeographs}+$"),
						s2)))
				&& Util.groupCount(m1) > 0
				&& Boolean.logicalAnd(
						Objects.equals(getUnicodeBlocks(s1),
								Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), s1, g1);
			//
		} else if (Util.matches(
				(m1 = Util.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InCJKUnifiedIdeographs}+)）$"), s1)))
				&& Util.groupCount(m1) > 0
				&& Util.matches((m2 = Util.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InHiragana}+)）$"), s2)))
				&& Util.groupCount(m2) > 0
				&& Boolean.logicalAnd(
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(g2 = Util.group(m2, 1)),
								Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), g1, g2);
			//
		} else if (Util.matches((m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)（[\\d|\\p{InHalfWidthAndFullWidthForms}|\\p{InCJKUnifiedIdeographs}]+）$"),
				s1)))
				&& Util.groupCount(m1) > 0
				&& Boolean.logicalAnd(
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(s2), Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), g1, s2);
			//
		} else if (Util.matches((m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\d|\\p{InHalfWidth_And_FullWidth_Forms}]+\\p{InCJKUnifiedIdeographs}+[\\s|\\p{InCjk_Symbols_And_Punctuation}]?(\\p{InCJKUnifiedIdeographs}+)$"),
				s1)))
				&& Util.groupCount(m1) > 0
				&& Boolean.logicalAnd(
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(s2), Collections.singletonList(UnicodeBlock.HIRAGANA)))
				&& !Objects.equals(g1, "線")) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), g1, s2);
			//
		} else if (Util
				.matches((m1 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InHiragana}+)\\s+\\p{InHALFWIDTH_AND_FULLWIDTH_FORMS}+\\p{InCJKUnifiedIdeographs}+$"),
						s2)))
				&& Util.groupCount(m1) > 0
				&& Boolean.logicalAnd(
						Objects.equals(getUnicodeBlocks(s1),
								Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), s1, g1);
			//
		} else if (Util.and(
				Objects.equals(getUnicodeBlocks(s1),
						Arrays.asList(UnicodeBlock.HIRAGANA, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
				Objects.equals(getUnicodeBlocks(s2), Collections.singletonList(UnicodeBlock.HIRAGANA)),
				StringsUtil.startsWith(Strings.CS, s2, cp = StringUtils.getCommonPrefix(s1, s2)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create),
					StringUtils.substring(s1, StringUtils.length(cp)),
					StringUtils.substring(s2, StringUtils.length(cp)));
			//
		} else if (Util.and(Objects.equals(getUnicodeBlocks(s1), Arrays.asList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
				(ss = StringUtils.split(s2, ' ')) != null, Util.length(ss) > 0)) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), s1,
					ArrayUtils.get(ss, 0));
			//
		} // if
			//
		return multimap;
		//
	}

	private static BiPredicate<String, String> createBiPredicate() {
		//
		return (a, b) -> Boolean.logicalAnd(
				Objects.equals(getUnicodeBlocks(a), Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
				Objects.equals(getUnicodeBlocks(b), Collections.singletonList(UnicodeBlock.HIRAGANA)));
		//
	}

	private static <T, U> BiConsumer<T, U> createBiConsumer(final Multimap<T, U> multimap) {
		return (t, u) -> MultimapUtil.put(multimap, t, u);
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s) {
		//
		Matcher m1 = null;
		//
		Multimap<String, String> multimap = null;
		//
		String g1, g2, cp;
		//
		if (Util.matches((m1 = Util.matcher(
				PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)\\s（(\\p{InHiragana}+)）$"), s)))
				&& Util.groupCount(m1) > 1
				&& Boolean.logicalAnd(
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(g2 = Util.group(m1, 2)),
								Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), g1, g2);
			//
		} else if (Util
				.matches((m1 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InHiragana}+\\p{InCJKUnifiedIdeographs}+)\\s+（([\\p{InHiragana}|\\s]+)）$"), s)))
				&& Util.groupCount(m1) > 1
				&& StringsUtil.startsWith(Strings.CS, g2 = StringUtils.trim(Util.group(m1, 2)),
						cp = StringUtils.getCommonPrefix(g1 = Util.group(m1, 1), g2))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, ArrayListMultimap::create),
					StringUtils.substring(g1, StringUtils.length(cp)),
					StringUtils.substring(g2, StringUtils.length(cp)));
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s0, final String s1) {
		//
		final List<UnicodeBlock> ub0 = getUnicodeBlocks(s0);
		//
		Multimap<String, String> multimap = null;
		//
		if (Boolean.logicalAnd(Objects.equals(ub0, Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
				Objects.equals(getUnicodeBlocks(s1), Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), s0, s1);
			//
		} // if
			//
		Matcher m1 = null;
		//
		String g1 = null;
		//
		if (Util.matches((m1 = Util.matcher(
				PatternMap.getPattern(patternMap,
						"^(\\p{InHiragana}+)\\s?\\p{InHalfWidth_And_FullWidth_Forms}\\p{InCJKUnifiedIdeographs}+$"),
				s1)))
				&& Util.groupCount(m1) > 0
				&& Boolean.logicalAnd(
						Objects.equals(ub0, Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), s0, g1);
			//
		} // if
			//
		if (Util.matches((m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)（[\\d|\\p{InHalfWidthAndFullWidthForms}|\\p{InCJKUnifiedIdeographs}]+）$"),
				s0)))
				&& Util.groupCount(m1) > 0
				&& Boolean.logicalAnd(
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(s1), Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), g1, s1);
			//
		} // if
			//
		final Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InHiragana}+)）$"), s1);
		//
		String g2 = null;
		//
		if (Util.matches(
				(m1 = Util.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InCJKUnifiedIdeographs}+)）$"), s0)))
				&& Util.groupCount(m1) > 0 && Util.matches(m2) && Util.groupCount(m2) > 0
				&& Boolean.logicalAnd(
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(g2 = Util.group(m2, 1)),
								Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), g1, g2);
			//
		} // if
			//
		if (Util.matches((m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\d|\\p{InHalfWidth_And_FullWidth_Forms}]+\\p{InCJKUnifiedIdeographs}+[\\s|\\p{InCjk_Symbols_And_Punctuation}]?(\\p{InCJKUnifiedIdeographs}+)$"),
				s0)))
				&& Util.groupCount(m1) > 0
				&& Boolean.logicalAnd(
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(s1), Collections.singletonList(UnicodeBlock.HIRAGANA)))
				&& !Objects.equals(g1, "線")) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), g1, s1);
			//
		} // if
			//
		if (Util.matches((m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)[\\s|\\p{InCjk_Symbols_And_Punctuation}]\\p{InHalfWidth_And_FullWidth_Forms}\\p{InCJKUnifiedIdeographs}+$"),
				s0)))
				&& Util.groupCount(m1) > 0
				&& Boolean.logicalAnd(
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(s1), Collections.singletonList(UnicodeBlock.HIRAGANA)))
				&& !Objects.equals(g1, "線")) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), g1, s1);
			//
		} // if
			//
		if (Util.matches((m1 = Util.matcher(
				PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)\\s（(\\p{InHiragana}+)）$"), s0)))
				&& Util.groupCount(m1) > 1
				&& Boolean.logicalAnd(
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(g2 = Util.group(m1, 2)),
								Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), g1, g2);
			//
		} // if
			//
		BiPredicate<String, String> biPredicate = null;
		//
		BiConsumer<String, String> biConsumer = null;
		//
		final Multimap<String, String> mm1 = multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create);
		//
		final Multimap<String, String> mm2 = toMultimap2(patternMap, s0, s1,
				ObjectUtils.getIfNull(biPredicate,
						OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean::createBiPredicate),
				ObjectUtils.getIfNull(biConsumer, () -> createBiConsumer(mm1)));
		//
		if (mm2 != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), mm2);
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap2(final PatternMap patternMap, final String s0, final String s1,
			final BiPredicate<String, String> biPredicate, final BiConsumer<String, String> biConsumer) {
		//
		Matcher m1, m2;
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.find((m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"(\\p{InHiragana}+)+\\p{InHalfWidth_And_FullWidth_Forms}(\\p{InCJKUnifiedIdeographs}+)+（(\\p{InHiragana}+)）"),
				s1))) && Util.groupCount(m1) > 2) {
			//
			testAndAccept(biPredicate, s0, m1.group(1), biConsumer);
			//
			testAndAccept(biPredicate, m1.group(2), m1.group(3), biConsumer);
			//
		} else if (Util
				.matches((m1 = Util.matcher(PatternMap.getPattern(patternMap,
						"^(\\p{InCJKUnifiedIdeographs}+)\\s?（(\\p{InCJKUnifiedIdeographs}+)）$"), s0)))
				&& Util.groupCount(m1) > 1
				&& Util.matches((m2 = Util.matcher(
						PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)\\s?（(\\p{InHiragana}+)）$"), s1)))
				&& Util.groupCount(m2) > 1) {
			//
			testAndAccept(biPredicate, Util.group(m1, 1), Util.group(m2, 1), biConsumer);
			//
			testAndAccept(biPredicate, Util.group(m1, 2), Util.group(m2, 2), biConsumer);
			//
		} // if
			//
		String g1, g2;
		//
		if (Util.matches(m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\p{InHiragana}|\\p{InKatakana}]+（(\\p{InCJKUnifiedIdeographs}+)）$"), s0))
				&& Util.groupCount(m1) > 0
				&& Util.matches((m2 = Util
						.matcher(PatternMap.getPattern(patternMap, "^[\\p{InHiragana}]+（(\\p{InHiragana}+)）$"), s1)))
				&& Util.groupCount(m2) > 0
				&& Boolean.logicalAnd(
						Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
								Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
						Objects.equals(getUnicodeBlocks(g2 = Util.group(m2, 1)),
								Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), g1, g2);
			//
		} // if
			//
		return multimap;
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
			@Nullable final BiConsumer<T, U> consumer) {
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