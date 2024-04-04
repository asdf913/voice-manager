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
		int childrenSize, size = 0;
		//
		final List<String> firstRowText = Arrays.asList("会社名など", "路線名", "読み方");
		//
		List<String> strings = null;
		//
		String s0, s1, s2, g1, g2, g3, cp = null;
		//
		String[] ss = null;
		//
		Multimap<String, String> multimap = null;
		//
		List<UnicodeBlock> ub1 = null;
		//
		Pattern p1 = null, p2 = null, p3 = null, p4 = null, p5 = null, p6 = null, p7 = null, p8 = null, p9 = null,
				p10 = null, p11 = null, p12 = null, p13 = null, p14 = null, p15 = null, p16 = null;
		//
		Matcher m1 = null, m2 = null;
		//
		for (int i = 0; es1 != null && i < es1.size(); i++) {
			//
			if ((e1 = es1.get(i)) == null || e1.childrenSize() != 1
					|| !Objects.equals(ElementUtil.tagName(e2 = e1.children().get(0)), "tbody")
					|| (childrenSize = ElementUtil.childrenSize(e2)) <= 1) {
				//
				continue;
				//
			} // if
				//
			for (int j = 0; j < childrenSize; j++) {
				//
				if (Objects.equals(firstRowText,
						strings = ElementUtil.children(IterableUtils.get(ElementUtil.children(e2), j)).stream()
								.map(ElementUtil::text).toList())) {
					//
					continue;
					//
				} // if
					//
				s0 = IterableUtils.get(strings, 0);
				//
				s1 = IterableUtils.get(strings, 1);
				//
				if ((size = IterableUtils.size(strings)) > 2) {
					//
					if (Util.matches(
							(m1 = Util
									.matcher(
											p7 = ObjectUtils.getIfNull(p7,
													() -> Pattern.compile(
															"^(\\p{InCJKUnifiedIdeographs}+)\\s（(\\p{InHiragana}+)）$")),
											s0)))
							&& Util.groupCount(m1) > 1
							&& Boolean.logicalAnd(
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(g2 = Util.group(m1, 2)),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, g1, g2);
						//
					} else if (Util
							.matches((m1 = Util.matcher(p16 = ObjectUtils.getIfNull(p16, () -> Pattern.compile(
									"^(\\p{InHiragana}+\\p{InCJKUnifiedIdeographs}+)\\s+（([\\p{InHiragana}|\\s]+)）$")),
									s0)))
							&& Util.groupCount(m1) > 1
							&& StringUtils.startsWith(g2 = StringUtils.trim(Util.group(m1, 2)),
									cp = StringUtils.getCommonPrefix(g1 = Util.group(m1, 1), g2))
							&& (multimap = ObjectUtils.getIfNull(multimap, ArrayListMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, StringUtils.substring(g1, StringUtils.length(cp)),
								StringUtils.substring(g2, StringUtils.length(cp)));
						//
					} // if
						//
					if (Boolean.logicalAnd(
							Objects.equals(getUnicodeBlocks(s1),
									Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
							Objects.equals(getUnicodeBlocks(s2 = IterableUtils.get(strings, 2)),
									Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, s1, s2);
						//
					} else if (Util.matches((m1 = Util.matcher(p13 = ObjectUtils.getIfNull(p13, () -> Pattern.compile(
							"^(\\p{InHiragana}+)?\\p{InCJK_Symbols_And_Punctuation}+\\p{InCJKUnifiedIdeographs}+\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$")),
							s2))) && Util.groupCount(m1) > 2
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						if (Boolean.logicalAnd(
								Objects.equals(getUnicodeBlocks(s1),
										Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
								Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
										Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
							//
							MultimapUtil.put(multimap, s1, g1);
							//
						} // if
							//
						if (Boolean.logicalAnd(
								Objects.equals(getUnicodeBlocks(g2 = Util.group(m1, 2)),
										Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
								Objects.equals(getUnicodeBlocks(g3 = Util.group(m1, 3)),
										Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
							//
							MultimapUtil.put(multimap, g2, g3);
							//
						} // if
							//
					} else if (Util.matches((m1 = Util.matcher(p14 = ObjectUtils.getIfNull(p14, () -> Pattern.compile(
							"^(\\p{InHiragana}+)\\p{InHALFWIDTH_AND_FULLWIDTH_FORMS}\\p{InCJKUnifiedIdeographs}+$")),
							s2)))
							&& Util.groupCount(m1) > 0
							&& Boolean.logicalAnd(
									Objects.equals(getUnicodeBlocks(s1),
											Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, s1, g1);
						//
					} else if (Util
							.matches(
									(m1 = Util
											.matcher(
													p3 = ObjectUtils.getIfNull(p3,
															() -> Pattern
																	.compile("^（(\\p{InCJKUnifiedIdeographs}+)）$")),
													s1)))
							&& Util.groupCount(
									m1) > 0
							&& Util.matches((m2 = Util.matcher(
									p4 = ObjectUtils.getIfNull(p4, () -> Pattern.compile("^（(\\p{InHiragana}+)）$")),
									s2)))
							&& Util.groupCount(m2) > 0
							&& Boolean.logicalAnd(
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(g2 = Util.group(m2, 1)),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, g1, g2);
						//
					} else if (Util.matches((m1 = Util.matcher(p2 = ObjectUtils.getIfNull(p2, () -> Pattern.compile(
							"^(\\p{InCJKUnifiedIdeographs}+)（[\\d|\\p{InHalfWidthAndFullWidthForms}|\\p{InCJKUnifiedIdeographs}]+）$")),
							s1)))
							&& Util.groupCount(m1) > 0
							&& Boolean.logicalAnd(
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(s2),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, g1, s2);
						//
					} else if (Util.matches((m1 = Util.matcher(p5 = ObjectUtils.getIfNull(p5, () -> Pattern.compile(
							"^[\\d|\\p{InHalfWidth_And_FullWidth_Forms}]+\\p{InCJKUnifiedIdeographs}+[\\s|\\p{InCjk_Symbols_And_Punctuation}]?(\\p{InCJKUnifiedIdeographs}+)$")),
							s1)))
							&& Util.groupCount(m1) > 0
							&& Boolean.logicalAnd(
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(s2),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& !Objects.equals(g1, "線")
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, g1, s2);
						//
					} else if (Util.matches((m1 = Util.matcher(p15 = ObjectUtils.getIfNull(p15, () -> Pattern.compile(
							"^(\\p{InHiragana}+)\\s+\\p{InHALFWIDTH_AND_FULLWIDTH_FORMS}+\\p{InCJKUnifiedIdeographs}+$")),
							s2)))
							&& Util.groupCount(m1) > 0
							&& Boolean.logicalAnd(
									Objects.equals(getUnicodeBlocks(s1),
											Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, s1, g1);
						//
					} else if (Objects.equals(getUnicodeBlocks(s1),
							Arrays.asList(UnicodeBlock.HIRAGANA, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS))
							&& Objects.equals(getUnicodeBlocks(s2), Collections.singletonList(UnicodeBlock.HIRAGANA))
							&& StringUtils.startsWith(s2, cp = StringUtils.getCommonPrefix(s1, s2))) {
						//
						MultimapUtil.put(multimap, StringUtils.substring(s1, StringUtils.length(cp)),
								StringUtils.substring(s2, StringUtils.length(cp)));
						//
					} else if (Objects.equals(getUnicodeBlocks(s1), Arrays.asList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS))
							&& (ss = StringUtils.split(s2, ' ')) != null && ss.length > 0) {
						//
						MultimapUtil.put(multimap, s1, ss[0]);
						//
					} else if (Boolean.logicalAnd(
							Objects.equals(getUnicodeBlocks(s1),
									Collections.singletonList(UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)),
							Objects.equals(getUnicodeBlocks(s2),
									Collections.singletonList(UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)))) {
						//
						continue;
						//
					} // if
						//
				} else if (size > 1) {
					//
					if (Boolean.logicalAnd(
							Objects.equals(ub1 = getUnicodeBlocks(s0),
									Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
							Objects.equals(getUnicodeBlocks(s1 = IterableUtils.get(strings, 1)),
									Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, s0, s1);
						//
						continue;
						//
					} // if
						//
					if (Util.matches((m1 = Util.matcher(p1 = ObjectUtils.getIfNull(p1, () -> Pattern.compile(
							"^(\\p{InHiragana}+)\\s?\\p{InHalfWidth_And_FullWidth_Forms}\\p{InCJKUnifiedIdeographs}+$")),
							s1)))
							&& Util.groupCount(m1) > 0
							&& Boolean.logicalAnd(
									Objects.equals(ub1, Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, s0, g1);
						//
						continue;
						//
					} // if
						//
					if (Util.matches((m1 = Util.matcher(p2 = ObjectUtils.getIfNull(p2, () -> Pattern.compile(
							"^(\\p{InCJKUnifiedIdeographs}+)（[\\d|\\p{InHalfWidthAndFullWidthForms}|\\p{InCJKUnifiedIdeographs}]+）$")),
							s0)))
							&& Util.groupCount(m1) > 0
							&& Boolean.logicalAnd(
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(s1),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, g1, s1);
						//
						continue;
						//
					} // if
						//
					if (Util.matches(
							(m1 = Util
									.matcher(
											p3 = ObjectUtils.getIfNull(p3,
													() -> Pattern.compile("^（(\\p{InCJKUnifiedIdeographs}+)）$")),
											s0)))
							&& Util.groupCount(
									m1) > 0
							&& Util.matches((m2 = Util.matcher(
									p4 = ObjectUtils.getIfNull(p4, () -> Pattern.compile("^（(\\p{InHiragana}+)）$")),
									s1)))
							&& Util.groupCount(m2) > 0
							&& Boolean.logicalAnd(
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(g2 = Util.group(m2, 1)),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, g1, s1);
						//
						continue;
						//
					} // if
						//
					if (Util.matches((m1 = Util.matcher(p5 = ObjectUtils.getIfNull(p5, () -> Pattern.compile(
							"^[\\d|\\p{InHalfWidth_And_FullWidth_Forms}]+\\p{InCJKUnifiedIdeographs}+[\\s|\\p{InCjk_Symbols_And_Punctuation}]?(\\p{InCJKUnifiedIdeographs}+)$")),
							s0)))
							&& Util.groupCount(m1) > 0
							&& Boolean.logicalAnd(
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(s1),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& !Objects.equals(g1, "線")
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, g1, s1);
						//
						continue;
						//
					} // if
						//
					if (Util.matches((m1 = Util.matcher(p6 = ObjectUtils.getIfNull(p6, () -> Pattern.compile(
							"^(\\p{InCJKUnifiedIdeographs}+)[\\s|\\p{InCjk_Symbols_And_Punctuation}]\\p{InHalfWidth_And_FullWidth_Forms}\\p{InCJKUnifiedIdeographs}+$")),
							s0)))
							&& Util.groupCount(m1) > 0
							&& Boolean.logicalAnd(
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(s1),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& !Objects.equals(g1, "線")
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, g1, s1);
						//
						continue;
						//
					} // if
						//
					if (Util.matches(
							(m1 = Util
									.matcher(
											p7 = ObjectUtils.getIfNull(p7,
													() -> Pattern.compile(
															"^(\\p{InCJKUnifiedIdeographs}+)\\s（(\\p{InHiragana}+)）$")),
											s0)))
							&& Util.groupCount(m1) > 1
							&& Boolean.logicalAnd(
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(g2 = Util.group(m1, 2)),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, g1, g2);
						//
						continue;
						//
					} // if
						//
					if (Util.find((m1 = Util.matcher(p8 = ObjectUtils.getIfNull(p8, () -> Pattern.compile(
							"(\\p{InHiragana}+)+\\p{InHalfWidth_And_FullWidth_Forms}(\\p{InCJKUnifiedIdeographs}+)+（(\\p{InHiragana}+)）")),
							s1))) && Util.groupCount(m1) > 2
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						if (Boolean.logicalAnd(
								Objects.equals(getUnicodeBlocks(s0),
										Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
								Objects.equals(getUnicodeBlocks(m1.group(1)),
										Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
							//
							MultimapUtil.put(multimap, s0, m1.group(1));
							//
						} // if
							//
						if (Boolean.logicalAnd(
								Objects.equals(getUnicodeBlocks(m1.group(2)),
										Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
								Objects.equals(getUnicodeBlocks(m1.group(3)),
										Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
							//
							MultimapUtil.put(multimap, m1.group(2), m1.group(3));
							//
						} // if
							//
						continue;
						//
					} // if
						//
					if (Util.matches((m1 = Util.matcher(
							p9 = ObjectUtils.getIfNull(p9,
									() -> Pattern.compile(
											"^(\\p{InCJKUnifiedIdeographs}+)\\s?（(\\p{InCJKUnifiedIdeographs}+)）$")),
							s0)))
							&& Util.groupCount(m1) > 1 && Util
									.matches(
											(m2 = Util.matcher(
													p10 = ObjectUtils.getIfNull(p10,
															() -> Pattern.compile(
																	"^(\\p{InHiragana}+)\\s?（(\\p{InHiragana}+)）$")),
													s1)))
							&& Util.groupCount(m2) > 1
							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						if (Boolean.logicalAnd(
								Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
										Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
								Objects.equals(getUnicodeBlocks(g2 = Util.group(m2, 1)),
										Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
							//
							MultimapUtil.put(multimap, g1, g2);
							//
						} // if
							//
						if (Boolean.logicalAnd(
								Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 2)),
										Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
								Objects.equals(getUnicodeBlocks(g2 = Util.group(m2, 2)),
										Collections.singletonList(UnicodeBlock.HIRAGANA)))) {
							//
							MultimapUtil.put(multimap, g1, g2);
							//
						} // if
							//
						continue;
						//
					} // if
						//
					if (Util.matches((m1 = Util.matcher(
							p11 = ObjectUtils.getIfNull(p11,
									() -> Pattern.compile(
											"^[\\p{InHiragana}|\\p{InKatakana}]+（(\\p{InCJKUnifiedIdeographs}+)）$")),
							s0)))
							&& Util.groupCount(m1) > 0
							&& Util.matches(
									(m2 = Util.matcher(
											p12 = ObjectUtils.getIfNull(p12,
													() -> Pattern.compile("^[\\p{InHiragana}]+（(\\p{InHiragana}+)）$")),
											s1)))
							&& Util.groupCount(m2) > 0
							&& Boolean.logicalAnd(
									Objects.equals(getUnicodeBlocks(g1 = Util.group(m1, 1)),
											Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
									Objects.equals(getUnicodeBlocks(g2 = Util.group(m2, 1)),
											Collections.singletonList(UnicodeBlock.HIRAGANA)))

							&& (multimap = ObjectUtils.getIfNull(multimap, TreeMultimap::create)) != null) {
						//
						MultimapUtil.put(multimap, g1, g2);
						//
						continue;
						//
					} // if
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
		if (Util.test(instance, t, u) && consumer != null) {
			consumer.accept(t, u);
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