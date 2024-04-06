package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.collect.TreeMultimap;
import com.google.common.reflect.Reflection;

/*
 * https://hiramatu-hifuka.com/onyak/onyak2/tetu-min.html
 */
public class OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
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
					patternMap = ObjectUtils.getIfNull(patternMap,
							() -> Reflection.newProxy(PatternMap.class, new IH())))) != null) {
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

	private static class IH implements InvocationHandler {

		private Map<Object, Pattern> patternMap = null;

		private Map<Object, Pattern> getPatternMap() {
			if (patternMap == null) {
				patternMap = new LinkedHashMap<>();
			}
			return patternMap;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, @Nullable final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof PatternMap && Objects.equals(methodName, "getPattern") && args != null
					&& args.length > 0) {
				//
				final Object arg0 = args[0];
				//
				if (!Util.containsKey(getPatternMap(), arg0)) {
					//
					Util.put(getPatternMap(), arg0,
							testAndApply(x -> x != null, Util.toString(arg0), Pattern::compile, null));
					//
				} // if
					//
				return Util.get(getPatternMap(), arg0);
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static interface PatternMap {

		Pattern getPattern(final String pattern);

		@Nullable
		private static Pattern getPattern(@Nullable final PatternMap instance, final String pattern) {
			return instance != null ? instance.getPattern(pattern) : null;
		}

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
		} else if (and(
				Objects.equals(getUnicodeBlocks(s1),
						Arrays.asList(UnicodeBlock.HIRAGANA, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
				Objects.equals(getUnicodeBlocks(s2), Collections.singletonList(UnicodeBlock.HIRAGANA)),
				StringUtils.startsWith(s2, cp = StringUtils.getCommonPrefix(s1, s2)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create),
					StringUtils.substring(s1, StringUtils.length(cp)),
					StringUtils.substring(s2, StringUtils.length(cp)));
			//
		} else if (and(Objects.equals(getUnicodeBlocks(s1), Arrays.asList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
				(ss = StringUtils.split(s2, ' ')) != null, length(ss) > 0)) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), s1, ss[0]);
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

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static boolean and(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		boolean result = a && b;
		//
		if (!result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			result &= bs[i];
			//
		} // for
			//
		return result;
		//
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
				&& Util.groupCount(m1) > 1 && StringUtils.startsWith(g2 = StringUtils.trim(Util.group(m1, 2)),
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
		Matcher m2 = null;
		//
		String g2 = null;
		//
		if (Util.matches(
				(m1 = Util.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InCJKUnifiedIdeographs}+)）$"), s0)))
				&& Util.groupCount(m1) > 0
				&& Util.matches((m2 = Util.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InHiragana}+)）$"), s1)))
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
		if (Util.find((m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"(\\p{InHiragana}+)+\\p{InHalfWidth_And_FullWidth_Forms}(\\p{InCJKUnifiedIdeographs}+)+（(\\p{InHiragana}+)）"),
				s1))) && Util.groupCount(m1) > 2) {
			//
			final Multimap<String, String> mm = multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create);
			//
			testAndAccept(
					biPredicate = ObjectUtils.getIfNull(biPredicate,
							OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean::createBiPredicate),
					s0, m1.group(1), biConsumer = ObjectUtils.getIfNull(biConsumer, () -> createBiConsumer(mm)));
			//
			testAndAccept(biPredicate, m1.group(2), m1.group(3), biConsumer);
			//
		} // if
			//
		if (Util.matches((m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\s?（(\\p{InCJKUnifiedIdeographs}+)）$"), s0)))
				&& Util.groupCount(m1) > 1
				&& Util.matches((m2 = Util.matcher(
						PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)\\s?（(\\p{InHiragana}+)）$"), s1)))
				&& Util.groupCount(m2) > 1) {
			//
			final Multimap<String, String> mm = multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create);
			//
			testAndAccept(
					biPredicate = ObjectUtils.getIfNull(biPredicate,
							OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean::createBiPredicate),
					Util.group(m1, 1), Util.group(m2, 1),
					biConsumer = ObjectUtils.getIfNull(biConsumer, () -> createBiConsumer(mm)));
			//
			testAndAccept(biPredicate, Util.group(m1, 2), Util.group(m2, 2), biConsumer);
			//
		} // if
			//
		final Multimap<String, String> mm = toMultimap2(patternMap, s0, s1);
		//
		if (mm != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create), mm);
			//
		} // if
			//
		return multimap;
		//
	}

	private static Multimap<String, String> toMultimap2(final PatternMap patternMap, final String s0, final String s1) {
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^[\\p{InHiragana}|\\p{InKatakana}]+（(\\p{InCJKUnifiedIdeographs}+)）$"), s0);
		//
		Matcher m2 = null;
		//
		String g1, g2 = null;
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 0 && Util.matches(
				(m2 = Util.matcher(PatternMap.getPattern(patternMap, "^[\\p{InHiragana}]+（(\\p{InHiragana}+)）$"), s1)))
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