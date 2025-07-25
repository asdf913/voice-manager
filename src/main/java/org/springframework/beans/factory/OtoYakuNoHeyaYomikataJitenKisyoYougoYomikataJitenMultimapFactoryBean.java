package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriConsumerUtil;
import org.meeuw.functional.TriPredicate;
import org.meeuw.functional.TriPredicateUtil;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

/*
 * https://hiramatu-hifuka.com/onyak/kotoba-1/kisyo.html
 */
public class OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean
		extends StringMultiMapFromResourceFactoryBean {

	private static final Pattern PATTERN_FULLWIDTH_LEFT_PARENTHESIS_HIRAGANA_FULLWIDTH_RIGHT_PARENTHESIS = Pattern
			.compile("（(\\p{InHiragana}+)）+");

	private static final Pattern PATTERN_CJK_UNIFIED_IDEOGRAPHS_FULLWIDTH_LEFT_PARENTHESIS_HIRAGANA_FULLWIDTH_RIGHT_PARENTHESIS = Pattern
			.compile("(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）");

	@URL("https://hiramatu-hifuka.com/onyak/kotoba-1/kisyo.html")
	private String url = null;

	private Iterable<Link> links = null;

	@Note("Text")
	private IValue0<String> text = null;

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
		Multimap<String, String> multimap = null;
		//
		final List<Element> es = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null), "tr");
		//
		Element e = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((e = es.get(i)) == null || e.childrenSize() < 3) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					createMultimap(e.children()));

		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimap(final Iterable<Element> es) {
		//
		Multimap<String, String> multimap = null;
		//
		final String s1 = StringUtils.deleteWhitespace(ElementUtil
				.text(testAndApply(x -> IterableUtils.size(es) > 0, es, x -> IterableUtils.get(x, 0), null)));
		//
		final String s2 = StringUtils.deleteWhitespace(ElementUtil
				.text(testAndApply(x -> IterableUtils.size(es) > 1, es, x -> IterableUtils.get(x, 1), null)));
		//
		List<String> ss1 = null;
		//
		List<String> ss2 = getStrings(s2, UnicodeBlock.HIRAGANA);
		//
		final char[] cs = new char[] { '・', '、' };
		//
		IValue0<Multimap<String, String>> ivmm = createMultimap(s1, cs, ss2);
		//
		if (ivmm != null || (ivmm = createMultimap1(s1, ss2)) != null || (ivmm = createMultimap2(s1, s2)) != null
				|| (ivmm = createMultimap4(s1, s2)) != null || (ivmm = createMultimap5(s1, ss2)) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					IValue0Util.getValue0(ivmm));
			//
		} // if
			//
		if (Objects.equals("凶暴な50度", s1)) {
			//
			ss1 = Util.toList(
					Util.filter(Util.stream(getStrings(s1, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS, UnicodeBlock.HIRAGANA)),
							StringUtils::isNotBlank));
			//
			ss2 = Util.toList(Util.filter(Util.stream(getStrings(s2, UnicodeBlock.HIRAGANA)), StringUtils::isNotBlank));
			//
			for (int j = 0; j < Math.min(IterableUtils.size(ss1), IterableUtils.size(ss2)); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						IterableUtils.get(ss1, j), IterableUtils.get(ss2, j));
				//
			} // for
				//
		} // if
			//
		final String s3 = ElementUtil
				.text(testAndApply(x -> IterableUtils.size(es) > 2, es, x -> IterableUtils.get(x, 2), null));
		//
		Matcher matcher = Util.matcher(Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"), s3);
		//
		int size = MultimapUtil.size(multimap);
		//
		while (Util.find(matcher) && Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(multimap, Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // while
			//
		if (MultimapUtil.size(multimap) != size) {
			//
			return multimap;
			//
		} // if
			//
		size = MultimapUtil.size(multimap);
		//
		if ((ivmm = createMultimap6(s1, s3)) != null || (ivmm = createMultimap7(s1, s3)) != null
				|| (ivmm = createMultimap8(s1, s3)) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					IValue0Util.getValue0(ivmm));
			//
		} // if
			//
		if (MultimapUtil.size(multimap) != size) {
			//
			return multimap;
			//
		} // if
			//
		size = MultimapUtil.size(multimap);
		//
		if (Util.contains(Arrays.asList("寒気湖", "時雨", "南風", "初霜", "氷点", "盆地霧", "御神渡り", "茅花流し", "閉そく", "雪迎え"), s1)) {
			//
			matcher = Util.matcher(
					PATTERN_CJK_UNIFIED_IDEOGRAPHS_FULLWIDTH_LEFT_PARENTHESIS_HIRAGANA_FULLWIDTH_RIGHT_PARENTHESIS, s3);
			//
			while (Util.find(matcher)) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 1), Util.group(matcher, 2));
				//
			} // while
				//
		} // if
			//
		if (MultimapUtil.size(multimap) != size) {
			//
			return multimap;
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap(final String string, final char[] cs,
			@Nullable final Iterable<String> iterable) {
		//
		if (!StringUtils.containsAny(string, cs)) {
			//
			return null;
			//
		} // if
			//
		String[] ss = null;
		//
		for (int j = 0; cs != null && j < cs.length; j++) {
			//
			if ((ss = StringUtils.split(string, cs[j])) != null && ss.length > 1) {
				//
				break;
				//
			} // if
				//
		} // if
			//
		IValue0<Multimap<String, String>> multimap = null;
		//
		int size = 0;
		//
		String s = null;
		//
		for (int j = 0; ss != null && j < ss.length; j++) {
			//
			if ((size = IterableUtils.size(iterable)) == 1) {
				//
				s = IterableUtils.get(iterable, 0);
				//
			} else if (size > 0) {
				//
				s = IterableUtils.get(iterable, Math.min(j, IterableUtils.size(iterable) - 1));
				//
			} else {
				//
				s = null;
				//
			} // if
				//
			MultimapUtil.put(
					IValue0Util.getValue0(
							multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))),
					ss[j], s);
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap1(final String s,
			@Nullable final Iterable<String> iterable) {
		//
		IValue0<Multimap<String, String>> multimap = null;
		//
		if (Util.matches(s, "^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InCJKUnifiedIdeographs}+)）$")) {
			//
			final Matcher matcher = Util
					.matcher(Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InCJKUnifiedIdeographs}+)）$"), s);
			//
			while (Util.find(matcher) && Util.groupCount(matcher) > 1) {
				//
				for (int j = 1; j < Util.groupCount(matcher) + 1; j++) {
					//
					MultimapUtil.put(IValue0Util.getValue0(
							multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))),
							Util.group(matcher, j),
							testAndApply(x -> IterableUtils.size(x) > 0, iterable, x -> IterableUtils.get(x, 0), null));
					//
				} // for
					//
			} // while
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap2(final String s1, final String s2) {
		//
		IValue0<Multimap<String, String>> multimap = null;
		//
		if (Util.matches(s1, "[\\p{InKatakana}|A-Z]+の(\\p{InCJKUnifiedIdeographs}+)")) {
			//
			IValue0<String> iv0 = null;
			//
			Matcher matcher = Util.matcher(Pattern.compile("[\\p{InKatakana}|A-Z]+の(\\p{InCJKUnifiedIdeographs}+)"),
					s1);
			//
			while (Util.find(matcher) && Util.groupCount(matcher) > 0) {
				//
				iv0 = Unit.with(Util.group(matcher, 1));
				//
			} // while
				//
			matcher = Util.matcher(Pattern.compile("\\p{InKatakana}+の(\\p{InHiragana}+)"), s2);
			//
			IValue0<String> iv1 = null;
			//
			while (Util.find(matcher) && Util.groupCount(matcher) > 0) {
				//
				iv1 = Unit.with(Util.group(matcher, 1));
				//
			} // while
				//
			testAndAccept((a, b, c) -> and(Objects::nonNull, b, c),
					multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create())), iv0, iv1,
					(a, b, c) -> MultimapUtil.put(IValue0Util.getValue0(a), IValue0Util.getValue0(b),
							IValue0Util.getValue0(c)));
			//
		} else if (Util.matches(s1, "^\\p{InKatakana}+の([\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+)")) {
			//
			IValue0<String> iv0 = null;
			//
			Matcher matcher = Util
					.matcher(Pattern.compile("^\\p{InKatakana}+の([\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+)"), s1);
			//
			while (Util.find(matcher) && Util.groupCount(matcher) > 0) {
				//
				iv0 = Unit.with(Util.group(matcher, 1));
				//
			} // while
				//
			matcher = Util.matcher(Pattern.compile("\\p{InKatakana}+の(\\p{InHiragana}+)"), s2);
			//
			IValue0<String> iv1 = null;
			//
			while (Util.find(matcher) && Util.groupCount(matcher) > 0) {
				//
				iv1 = Unit.with(Util.group(matcher, 1));
				//
			} // while
				//
			testAndAccept((a, b, c) -> and(Objects::nonNull, b, c),
					multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create())), iv0, iv1,
					(a, b, c) -> MultimapUtil.put(IValue0Util.getValue0(a), IValue0Util.getValue0(b),
							IValue0Util.getValue0(c)));
			//
		} // if
			//
		return multimap;
		//
	}

	private static <T, U, V> void testAndAccept(final TriPredicate<T, U, V> predicate, final T t, @Nullable final U u,
			@Nullable final V v, @Nullable final TriConsumer<T, U, V> consumer) {
		if (TriPredicateUtil.test(predicate, t, u, v)) {
			TriConsumerUtil.accept(consumer, t, u, v);
		}
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap4(final String s1, final String s2) {
		//
		IValue0<Multimap<String, String>> multimap = null;
		//
		if (Objects.equals("荒天の40度", s1)) {
			//
			List<String> ss1 = null;
			//
			Matcher matcher = Util
					.matcher(Pattern.compile("(\\p{InCJKUnifiedIdeographs}+)の\\d+(\\p{InCJKUnifiedIdeographs}+)"), s1);
			//
			while (Util.find(matcher) && Util.groupCount(matcher) > 1) {
				//
				for (int j = 1; j <= Util.groupCount(matcher); j++) {
					//
					Util.add(ss1 = ObjectUtils.getIfNull(ss1, ArrayList::new), Util.group(matcher, j));
					//
				} // for
					//
			} // while
				//
			List<String> ss2 = null;
			//
			matcher = Util.matcher(Pattern.compile("(\\p{InHiragana}+)の\\d+(\\p{InHiragana}+)"), s2);
			//
			while (Util.find(matcher) && Util.groupCount(matcher) > 1) {
				//
				for (int j = 1; j <= Util.groupCount(matcher); j++) {
					//
					Util.add(ss2 = ObjectUtils.getIfNull(ss2, ArrayList::new), Util.group(matcher, j));
					//
				} // if
					//
			} // while
				//
			for (int j = 0; j < Math.min(IterableUtils.size(ss1), IterableUtils.size(ss2)); j++) {
				//
				MultimapUtil.put(IValue0Util.getValue0(
						multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))),
						IterableUtils.get(ss1, j), IterableUtils.get(ss2, j));
				//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap5(final String s1, final Collection<String> ss2) {
		//
		IValue0<Multimap<String, String>> multimap = null;
		//
		Iterable<UnicodeBlock> unicodeBlocks = null;
		//
		if (Util.matches(s1, "^(\\p{InCJKUnifiedIdeographs}|々|・|、|\\p{InHiragana})+$")) {
			//
			if (Util.iterator(ss2) != null) {
				//
				for (final String s : ss2) {
					//
					if (StringUtils.isBlank(s)) {
						//
						continue;
						//
					} // if
						//
					MultimapUtil.put(IValue0Util.getValue0(
							multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))),
							s1, s);
					//
				} // for
					//
			} // if
				//
		} else if (Util.or(
				Objects.equals(Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
						unicodeBlocks = getUnicodeBlocks(s1)),
				Objects.equals(Arrays.asList(UnicodeBlock.KATAKANA, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
						unicodeBlocks),
				Objects.equals(Arrays.asList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS, UnicodeBlock.KATAKANA),
						unicodeBlocks),
				Objects.equals(Arrays.asList(UnicodeBlock.GREEK, UnicodeBlock.BASIC_LATIN,
						UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), unicodeBlocks),
				Objects.equals(Arrays.asList(UnicodeBlock.KATAKANA, UnicodeBlock.BASIC_LATIN,
						UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), unicodeBlocks))) {
			//
			MultimapUtil.put(
					IValue0Util.getValue0(
							multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))),
					Util.collect(Util.filter(Util.stream(getStrings(s1, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
							StringUtils::isNotBlank), Collectors.joining()),
					Util.collect(Util.filter(Util.stream(ss2), StringUtils::isNotBlank), Collectors.joining()));
			//
		} else if (Util.or(
				Objects.equals(Arrays.asList(UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS, UnicodeBlock.KATAKANA,
						UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), unicodeBlocks),
				Objects.equals(Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.KATAKANA,
						UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), unicodeBlocks),
				Objects.equals(Arrays.asList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS, UnicodeBlock.BASIC_LATIN),
						unicodeBlocks),
				Objects.equals("国際防災の10年", s1))) {
			//
			MultimapUtil.put(
					IValue0Util.getValue0(
							multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))),
					s1, Util.collect(Util.filter(Util.stream(ss2), StringUtils::isNotBlank), Collectors.joining()));
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap6(final String s1, final String s3) {
		//
		IValue0<Multimap<String, String>> multimap = null;
		//
		Matcher matcher = null;
		//
		if (Objects.equals(s1, "較差")) {
			//
			matcher = Util.matcher(PATTERN_FULLWIDTH_LEFT_PARENTHESIS_HIRAGANA_FULLWIDTH_RIGHT_PARENTHESIS, s3);
			//
			while (Util.find(matcher)) {
				//
				MultimapUtil.put(IValue0Util.getValue0(
						multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))), s1,
						Util.group(matcher, 1));
				//
			} // while
				//
		} else if (Util.contains(Arrays.asList("風花", "梅雨"), s1)) {
			//
			matcher = Util.matcher(
					PATTERN_CJK_UNIFIED_IDEOGRAPHS_FULLWIDTH_LEFT_PARENTHESIS_HIRAGANA_FULLWIDTH_RIGHT_PARENTHESIS, s3);
			//
			while (Util.find(matcher)) {
				//
				MultimapUtil.put(IValue0Util.getValue0(
						multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))),
						Util.group(matcher, 1), Util.group(matcher, 2));
				//
			} // while
				//
			String g1 = null;
			//
			matcher = Util.matcher(PATTERN_FULLWIDTH_LEFT_PARENTHESIS_HIRAGANA_FULLWIDTH_RIGHT_PARENTHESIS, s3);
			//
			while (Util.find(matcher)) {
				//
				if (Boolean.logicalAnd(Objects.equals(g1 = Util.group(matcher, 1), "ふっこし"), Objects.equals(s1, "風花"))) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(IValue0Util.getValue0(
						multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))), s1,
						g1);
				//
			} // while
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap7(final String s1, final String s3) {
		//
		IValue0<Multimap<String, String>> multimap = null;
		//
		Matcher matcher = null;
		//
		if (Objects.equals("雨脚・雨足", s1)) {
			//
			matcher = Util.matcher(
					PATTERN_CJK_UNIFIED_IDEOGRAPHS_FULLWIDTH_LEFT_PARENTHESIS_HIRAGANA_FULLWIDTH_RIGHT_PARENTHESIS, s3);
			//
			String g1 = null;
			//
			final Strings strings = Strings.CS;
			//
			while (Util.find(matcher)) {
				//
				MultimapUtil.put(IValue0Util.getValue0(
						multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))),
						g1 = Util.group(matcher, 1),
						testAndApply(
								(a, b) -> Boolean.logicalAnd(Objects.equals(a, "雨脚"),
										StringsUtil.endsWith(strings, b, "とも")),
								g1, Util.group(matcher, 2), (a, b) -> removeEnd(Strings.CS, b, "とも"), (a, b) -> b));
				//
			} // while
				//
			matcher = Util.matcher(PATTERN_FULLWIDTH_LEFT_PARENTHESIS_HIRAGANA_FULLWIDTH_RIGHT_PARENTHESIS, s3);
			//
			String[] ss = null;
			//
			while (Util.find(matcher)) {
				//
				ss = StringUtils.split(s1, '・');
				//
				for (int i = 0; ss != null && i < ss.length; i++) {
					//
					if (Boolean.logicalAnd(Objects.equals(ss[i], "雨足"),
							Objects.equals(
									g1 = testAndApply(x -> StringsUtil.endsWith(strings, x, "とも"),
											Util.group(matcher, 1), x -> removeEnd(Strings.CS, x, "とも"), x -> x),
									"うきゃく"))) {
						//
						continue;
						//
					} // if
						//
					MultimapUtil.put(IValue0Util.getValue0(
							multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))),
							ss[i], g1);
					//
				} // for
					//
			} // while
				//
		} else if (Util
				.matches(matcher = Util.matcher(
						Pattern.compile("^([\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+)（(\\p{InHiragana}+)）$"), s3))
				&& Util.groupCount(matcher) > 1) {
			//
			MultimapUtil.put(
					IValue0Util.getValue0(
							multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static String removeEnd(@Nullable final Strings instance, final String str, final CharSequence remove) {
		return instance != null ? instance.removeEnd(str, remove) : null;
	}

	@Nullable
	private static IValue0<Multimap<String, String>> createMultimap8(final String s1, final String s3) {
		//
		IValue0<Multimap<String, String>> multimap = null;
		//
		final Matcher matcher = Util.matcher(Pattern.compile("（(\\p{InHiragana}+)）"), s3);
		//
		while (Util.find(matcher)) {
			//
			if (Util.contains(Arrays.asList("雨水", "極渦", "降灰", "紅葉前線", "日較差", "白夜", "百葉箱", "雷雲", "黄道光"), s1)
					&& Util.groupCount(matcher) > 0) {
				//
				MultimapUtil.put(IValue0Util.getValue0(
						multimap = ObjectUtils.getIfNull(multimap, () -> Unit.with(LinkedHashMultimap.create()))), s1,
						Util.group(matcher, 1));
				//
			} // if
				//
		} // while
			//
		return multimap;
		//
	}

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse) {
		return Util.test(predicate, t, u) ? Util.apply(functionTrue, t, u) : Util.apply(functionFalse, t, u);
	}

	private static <T> boolean and(final Predicate<T> predicate, @Nullable final T a, @Nullable final T b) {
		//
		return Boolean.logicalAnd(Util.test(predicate, a), Util.test(predicate, b));
		//
	}

	@Nullable
	private static List<UnicodeBlock> getUnicodeBlocks(@Nullable final String string) {
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
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static List<String> getStrings(@Nullable final String string, final UnicodeBlock ub,
			final UnicodeBlock... ubs) {
		//
		final char[] cs = Util.toCharArray(string);
		//
		StringBuilder sb = null;
		//
		char c = ' ';
		//
		List<String> list = null;
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if (!Objects.equals(UnicodeBlock.of(c = cs[i]), ub)
					&& !ArrayUtils.contains(ubs, UnicodeBlock.of(c = cs[i]))) {
				//
				if (i > 0) {
					//
					Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), Util.toString(sb));
					//
				} // if
					//
				clear(sb = ObjectUtils.getIfNull(sb, StringBuilder::new));
				//
			} else {
				//
				Util.append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
				//
			} // if
				//
		} // for
			//
		if (StringUtils.isNotEmpty(sb)) {
			//
			Util.add(list = ObjectUtils.getIfNull(list, ArrayList::new), Util.toString(sb));
			//
		} // if
			//
		return list;
		//
	}

	private static void clear(@Nullable final StringBuilder instance) {
		if (instance != null) {
			instance.delete(0, instance.length());
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}