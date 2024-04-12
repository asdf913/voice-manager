package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.mariten.kanatools.KanaConverter;

/*
 * https://hiramatu-hifuka.com/onyak/onyak2/yuryodo.html
 */
public class OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private static final String PATTERN_CJK_UNIFIED_IDEOGRAPHS_AND_KATAKANA = "^(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)$";

	private static final String PATTERN_HIRAGANA_GA_HIRAGANA = "^(\\p{InHiragana}+)(が)(\\p{InHiragana}+)$";

	private static final String PATTERN_CJK_UNIFIED_IDEOGRAPHS_HIRAGANA_CJK_UNIFIED_IDEOGRAPHS = "^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)$";

	private String url = null;

	private Iterable<Link> links = null;

	private IValue0<String> text = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setLinks(final Iterable<Link> links) {
		this.links = links;
	}

	public void setText(final String text) {
		this.text = Unit.with(text);
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final List<Link> ls = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.spliterator(links), x -> StreamSupport.stream(x, false), null),
				x -> text != null && x != null && Objects.equals(x.getText(), IValue0Util.getValue0(text))));
		//
		final int size = IterableUtils.size(ls);
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
		Multimap<String, String> multimap = null, mm;
		//
		final List<Element> es = ElementUtil.select(document, "table[border=\"1\"] tr");
		//
		Element e = null;
		//
		int size;
		//
		List<Element> children;
		//
		Entry<Integer, Integer> entry;
		//
		final AtomicBoolean first = new AtomicBoolean(true);
		//
		String s1, s2;
		//
		final int maxChildrenSize = orElse(max(mapToInt(Util.stream(es), ElementUtil::childrenSize)), 0);
		//
		for (int i = 0; i < IterableUtils.size(es); i++) {
			//
			if (Boolean.logicalOr(first.getAndSet(false),
					(entry = toEntry(ElementUtil.childrenSize(e = IterableUtils.get(es, i)),
							maxChildrenSize)) == null)) {
				//
				continue;
				//
			} // if
				//
			size = MultimapUtil.size(multimap);
			//
			if ((mm = toMultimap1(
					s1 = ElementUtil.text(IterableUtils.get(children = ElementUtil.children(e), Util.getKey(entry))),
					s2 = ElementUtil.text(IterableUtils.get(children, Util.getValue(entry))))) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
				//
			} // if
				//
			if (MultimapUtil.size(multimap) == size && (mm = toMultimap2(s1, s2)) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
				//
			} // if
				//
			if (MultimapUtil.size(multimap) == size && (mm = toMultimap3(s1, s2)) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
				//
			} // if
				//
			if ((mm = createMultimap1(s1, s2)) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
				//
			} // if
				//
			if ((mm = createMultimap2(s1, s2)) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimap1(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null, mm;
		//
		final int size = MultimapUtil.size(multimap);
		//
		if ((mm = toMultimap4(s1, s2)) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		if (MultimapUtil.size(multimap) == size && (mm = toMultimap5(s1, s2)) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		if (MultimapUtil.size(multimap) == size && (mm = toMultimap6(s1, s2)) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		if (MultimapUtil.size(multimap) == size && (mm = toMultimap7(s1, s2)) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		if (MultimapUtil.size(multimap) == size && (mm = toMultimap8(s1, s2)) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		if (MultimapUtil.size(multimap) == size && (mm = toMultimap9(s1, s2)) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		if (MultimapUtil.size(multimap) == size && (mm = toMultimap10(s1, s2)) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		if (MultimapUtil.size(multimap) == size && (mm = toMultimap11(s1, s2)) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> createMultimap2(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null, mm;
		//
		final int size = MultimapUtil.size(multimap);
		//
		if ((mm = toMultimap12(s1, s2)) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		if (MultimapUtil.size(multimap) == size
				&& (mm = toMultimap13(s1, s2, Arrays.asList("美", "八", "久須夜"))) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		if (MultimapUtil.size(multimap) == size && (mm = toMultimap14(s1, s2, Collections.singleton("根山"))) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		if (MultimapUtil.size(multimap) == size && (mm = toMultimap15(s1, s2, Arrays.asList("鬼押", "鬼押出"))) != null) {
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Entry<Integer, Integer> toEntry(final int childrenSize, final int maxChildrenSize) {
		//
		Entry<Integer, Integer> entry = null;
		//
		if (childrenSize == maxChildrenSize) {
			//
			entry = Pair.of(Integer.valueOf(1), Integer.valueOf(2));
			//
		} else if (childrenSize > 1) {
			//
			entry = Pair.of(Integer.valueOf(0), Integer.valueOf(1));
			//
		} // if
			//
		return entry;
		//
	}

	private static int orElse(@Nullable final OptionalInt instance, final int other) {
		return instance != null ? instance.orElse(other) : other;
	}

	@Nullable
	private static OptionalInt max(@Nullable final IntStream instance) {
		return instance != null ? instance.max() : null;
	}

	@Nullable
	private static OptionalInt min(@Nullable final IntStream instance) {
		return instance != null ? instance.min() : null;
	}

	@Nullable
	private static <T> IntStream mapToInt(@Nullable final Stream<T> instance,
			@Nullable final ToIntFunction<? super T> mapper) {
		//
		final Class<?> clz = Util.getClass(instance);
		//
		if (Objects.equals(Util.getName(clz), "java.util.stream.ReferencePipeline$Head") && mapper == null) {
			//
			return null;
			//
		} else if (clz != null && Proxy.isProxyClass(clz)) {
			//
			return instance != null ? instance.mapToInt(mapper) : null;
			//
		} // if
			//
		return instance != null ? instance.mapToInt(mapper) : null;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap1(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		final List<UnicodeBlock> ub = getUnicodeBlocks(s1);
		//
		String commonPrefix;
		//
		Matcher m1;
		//
		if (Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), ub)
				&& Objects.equals(Collections.singletonList(UnicodeBlock.BASIC_LATIN), getUnicodeBlocks(s2))) {
			//
			return multimap;
			//
		} // if
			// if
		if (Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), ub)
				&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
			//
		} else if (Objects.equals(Arrays.asList(UnicodeBlock.HIRAGANA, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), ub)
				&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))
				&& StringUtils.isNotBlank(commonPrefix = StringUtils.getCommonPrefix(s1, s2))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					StringUtils.substring(s1, StringUtils.length(commonPrefix)),
					StringUtils.substring(s2, StringUtils.length(commonPrefix)));
			//
		} else if (Util.matches(m1 = Util.matcher(Pattern.compile(PATTERN_CJK_UNIFIED_IDEOGRAPHS_AND_KATAKANA), s1))
				&& Util.groupCount(m1) > 1
				&& Objects.equals(Arrays.asList(UnicodeBlock.HIRAGANA, UnicodeBlock.KATAKANA), getUnicodeBlocks(s2))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.group(m1, 1),
					StringUtils.substring(s2, 0, StringUtils.length(s2) - StringUtils.length(Util.group(m1, 2))));
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap2(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		if (Boolean.logicalAnd(
				Objects.equals(Arrays.asList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS, UnicodeBlock.HIRAGANA),
						getUnicodeBlocks(s1)),
				Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2)))) {
			//
			final Matcher m = Util.matcher(Pattern.compile("(\\p{InHiragana})+"), s1);
			//
			String[] ss1, ss2;
			//
			String group;
			//
			while (Util.find(m)) {
				//
				ss1 = StringUtils.split(s1, group = m.group());
				//
				ss2 = StringUtils.split(s2, group);
				//
				for (int j = 0; j < Math.min(length(ss1), length(ss2)); j++) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							ss1 != null ? ss1[j] : null, ss2 != null ? ss2[j] : null);
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
	private static Multimap<String, String> toMultimap3(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m1, m2;
		//
		String g2;
		//
		if (Util.matches(m1 = Util
				.matcher(Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+) （(\\p{InCJKUnifiedIdeographs}+)）$"), s1))
				&& Util.matches(m2 = Util.matcher(Pattern.compile("^(\\p{InHiragana}+) （(\\p{InHiragana}+)）$"), s2))) {
			//
			for (int j = 1; j <= orElse(min(mapToInt(Stream.of(m1, m2), Util::groupCount)), 0); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m1, j), Util.group(m2, j));
				//
			} // for
				//
		} else if (Util.matches(m1 = Util.matcher(Pattern.compile(PATTERN_CJK_UNIFIED_IDEOGRAPHS_AND_KATAKANA), s1))
				&& Util.groupCount(m1) > 1
				&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					StringUtils.substring(s1, 0, StringUtils.length(s1) - StringUtils.length(g2 = Util.group(m1, 2))),
					StringUtils.substring(s2, 0, StringUtils.length(s2) - StringUtils.length(g2)));
			//
		} else if (Util
				.matches(m1 = Util.matcher(Pattern.compile(
						"^(\\p{InCJKUnifiedIdeographs}+)\\s?（((\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+))）$"),
						s1))
				&& Util.groupCount(m1) > 2 && Util.matches(
						m2 = Util.matcher(Pattern.compile("^(\\p{InHiragana}+)\\s?（(\\p{InHiragana}+)）$"), s2))) {
			//
			int length;
			//
			String g1;
			//
			for (int j = 1; j <= orElse(min(mapToInt(Stream.of(m1, m2), Util::groupCount)), 0); j++) {
				//
				if (j == 1) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, j), Util.group(m2, j));
					//
				} else {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							StringUtils.substring(g1 = Util.group(m1, j), 0,
									StringUtils.length(g1) - (length = StringUtils.length(Util.group(m1, j + 2)))),
							StringUtils.substring(g2 = Util.group(m2, j), 0, StringUtils.length(g2) - length));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap4(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m1, m2;
		//
		if (Util.matches(m1 = Util.matcher(
				Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)-(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)$"),
				s1))
				&& Util.groupCount(m1) > 2
				&& Util.matches(m2 = Util
						.matcher(Pattern.compile("^(\\p{InHiragana}+)-([\\p{InHiragana}|\\p{InKatakana}]+)"), s2))
				&& Util.groupCount(m2) > 1) {
			//
			String g2;
			//
			for (int j = 1; j <= orElse(min(mapToInt(Stream.of(m1, m2), Util::groupCount)), 0); j++) {
				//
				if (j == 1) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, j), Util.group(m2, j));
					//
				} else {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, j), StringUtils.substring(g2 = Util.group(m2, j), 0,
									StringUtils.length(g2) - StringUtils.length(Util.group(m1, j + 1))));
					//
				} // if
					//
			} // for
				//
		} else if (Util
				.matches(m1 = Util.matcher(
						Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)（\\p{InHiragana}+\\p{InKatakana}+）$"), s1))
				&& Util.groupCount(m1) > 0
				&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.group(m1, 1),
					s2);
			//
		} else if (Util
				.matches(m1 = Util.matcher(
						Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)\\s?（([\\p{InHiragana}|\\p{InKatakana}]+)）$"),
						s1))
				&& Util.groupCount(m1) > 0
				&& Objects.equals(Arrays.asList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.group(m1, 1),
					s2);
			//
		} else if (Util
				.matches(m1 = Util.matcher(
						Pattern.compile("^(\\p{InKatakana}+)\\s?（(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)）"),
						s1))
				&& Util.groupCount(m1) > 2
				&& Util.matches(m2 = Util
						.matcher(Pattern.compile("^([\\p{InKatakana}|\\p{InHiragana}]+)\\s?（(\\p{InHiragana}+)）$"), s2))
				&& Util.groupCount(m2) > 1) {
			//
			final String m22 = Util.group(m2, 2);
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), Util.group(m1, 2),
					StringUtils.substring(m22, 0, StringUtils.length(m22) - StringUtils.length(Util.group(m1, 3))));
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap5(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m1, m2;
		//
		if (Util.matches(m1 = Util.matcher(Pattern.compile(
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)\\s?（(\\p{InCJKUnifiedIdeographs}+)〜(\\p{InCJKUnifiedIdeographs}+)）$"),
				s1))
				&& Util.groupCount(m1) > 3
				&& Util.matches(m2 = Util.matcher(
						Pattern.compile("^(\\p{InHiragana}+)\\s?（(\\p{InHiragana}+)〜(\\p{InHiragana}+)）$"), s2))
				&& Util.groupCount(m2) > 2) {
			//
			for (int j = 1; j <= orElse(min(mapToInt(Stream.of(m1, m2), Util::groupCount)), 0); j++) {
				//
				if (j == 1) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, 1),
							StringUtils.substring(Util.group(m2, j), 0, StringUtils.length(Util.group(m2, j))
									- StringUtils.length(Util.group(m1, Math.min(Util.groupCount(m1), j + 1)))));
					//
				} else {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, Math.min(Util.groupCount(m1), j + 1)), Util.group(m2, j));
					//
				} // if
					//
			} // for
				//
		} else if (Util
				.matches(Util.matcher(
						Pattern.compile(
								"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)(\\p{InCJKUnifiedIdeographs}+)$"),
						s1))
				&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))) {
			//
			final String string = KanaConverter.convertKana(s1, KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA);
			//
			final Matcher m = Util
					.matcher(Pattern.compile(PATTERN_CJK_UNIFIED_IDEOGRAPHS_HIRAGANA_CJK_UNIFIED_IDEOGRAPHS), string);
			//
			String[] ss = null;
			//
			if (!Objects.equals(string, s1) && Util.matches(m) && Util.groupCount(m) > 2
					&& StringUtils.countMatches(s2, Util.group(m, 2)) == 1
					&& (ss = StringUtils.splitByWholeSeparator(s2, Util.group(m, 2))) != null && ss.length > 1) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m, 1), ss[0]);
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m, 3), ss[1]);
				//
			} // if
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap6(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.matches(Util.matcher(Pattern.compile(
				"^((\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+))(\\s?（((\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+))）)+"),
				s1))
				&& Util.matches(Util.matcher(
						Pattern.compile("^(\\p{InHiragana}+)(\\s?（([\\p{InHiragana}|\\p{InKatakana}]+)）)+$"), s2))) {
			//
			final String[] ss1 = StringUtils.split(s1, " ");
			//
			final String[] ss2 = StringUtils.split(s2, " ");
			//
			String sk, sh, g2;
			//
			Pattern p = null;
			//
			Matcher m;
			//
			for (int i = 0; i < Math.min(length(ss1), length(ss2)); i++) {
				//
				if (!Util.matches(m = Util.matcher(
						p = ObjectUtils.getIfNull(p,
								() -> Pattern.compile(PATTERN_CJK_UNIFIED_IDEOGRAPHS_AND_KATAKANA)),
						sk = substringBetween(ss1 != null ? ss1[i] : null, "（", "）"))) || Util.groupCount(m) < 2) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.substring(sk, 0,
								StringUtils.length(sk) - StringUtils.length(g2 = Util.group(m, 2))),
						StringUtils.substring(sh = substringBetween(ss2 != null ? ss2[i] : null, "（", "）"), 0,
								StringUtils.length(sh) - StringUtils.length(g2)));
				//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap7(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		final Matcher m1 = Util.matcher(Pattern.compile(
				"^(\\p{InCJKUnifiedIdeographs}+)([\\p{InKatakana}|\\p{InHiragana}]+)(\\p{InCJKUnifiedIdeographs}+)$"),
				testAndApply(Objects::nonNull, s1,
						x -> KanaConverter.convertKana(s1, KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA), null));
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 2) {
			//
			final String[] ss = StringUtils.splitByWholeSeparator(s2, Util.group(m1, 2));
			//
			final int length = length(ss);
			//
			final int groupCount = Util.groupCount(m1);
			//
			for (int i = 0; i < length && groupCount - length == 1; i++) {
				//
				if (i == 0) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, Math.min(groupCount, i + 1)), ss[i]);
					//
				} else if (i == 1) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, Math.min(groupCount, i + 2)), ss[i]);
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap8(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m1, m2 = null;
		//
		if (Util.matches(m1 = Util.matcher(Pattern.compile(
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)\\s（(\\p{InCJKUnifiedIdeographs}+)）（(\\p{InCJKUnifiedIdeographs}+)）$"),
				s1))
				&& Util.groupCount(m1) > 3
				&& Util.matches(m2 = Util.matcher(
						Pattern.compile(
								"^([\\p{InKatakana}|\\p{InHiragana}]+)\\s（(\\p{InHiragana}+)）（(\\p{InHiragana}+)）$"),
						s2))
				&& Util.groupCount(m2) > 2) {
			//
			final int groupCount = Util.groupCount(m1);
			//
			for (int i = 1; i <= orElse(min(mapToInt(Stream.of(m1, m2), Util::groupCount)), 0); i++) {
				//
				if (i == 1) {
					//
					final String s21 = Util.group(m2, 1);
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, i), StringUtils.substring(s21, 0,
									StringUtils.length(s21) - StringUtils.length(Util.group(m1, i + 1))));
					//
				} else {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, Math.min(groupCount, i + 1)), Util.group(m2, i));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap9(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m1, m2 = null;
		//
		if (Util.matches(m1 = Util.matcher(Pattern.compile(
				"^(\\p{InCJKUnifiedIdeographs}+)\\s（(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)）$"),
				s1)) && Util.groupCount(m1) > 3
				&& Util.matches(m2 = Util.matcher(Pattern.compile("^(\\p{InHiragana}+)\\s（(\\p{InHiragana}+)）$"), s2))
				&& Util.groupCount(m2) > 1) {
			//
			final int groupCount = Util.groupCount(m1);
			//
			for (int i = 1; i <= orElse(min(mapToInt(Stream.of(m1, m2), Util::groupCount)), 0); i++) {
				//
				if (i == 1) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, i), Util.group(m2, i));
					//
				} else if (i == 2) {
					//
					final Multimap<String, String> mm = toMultimap91(
							StringUtils.splitByWholeSeparator(Util.group(m2, i),
									Util.group(m1, Math.min(Util.groupCount(m1), i + 1))),
							i, groupCount, m1);
					//
					if (mm != null) {
						//
						MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap91(final String[] ss, final int i, final int groupCount,
			final Matcher m) {
		//
		Multimap<String, String> multimap = null;
		//
		for (int j = 0; j < length(ss); j++) {
			//
			if (j == 0) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m, i), ss[j]);
				//
			} else if (j == 1) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m, Math.min(groupCount, i + 2)), ss[j]);
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap10(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m1, m2 = null;
		//
		if (Util.matches(m1 = Util.matcher(Pattern.compile(
				"^(\\p{InCJKUnifiedIdeographs}+)\\s（(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)(\\p{InCJKUnifiedIdeographs}+)）$"),
				s1)) && Util.groupCount(m1) > 0
				&& Util.matches(m2 = Util.matcher(Pattern.compile("^(\\p{InHiragana}+)\\s（(\\p{InHiragana}+)）$"), s2))
				&& Util.groupCount(m2) > 1) {
			//
			final int groupCount = Util.groupCount(m1);
			//
			for (int i = 1; i <= orElse(min(mapToInt(Stream.of(m1, m2), Util::groupCount)), 0); i++) {
				//
				if (i == 1) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, i), Util.group(m2, i));
					//
				} else {
					//
					final Multimap<String, String> mm = toMultimap101(StringUtils.splitByWholeSeparator(
							Util.group(m2, i), KanaConverter.convertKana(Util.group(m1, Math.min(groupCount, i + 1)),
									KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA)),
							i, groupCount, m1);
					//
					if (mm != null) {
						//
						MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap101(final String[] ss, final int i, final int groupCount,
			final Matcher m) {
		//
		Multimap<String, String> multimap = null;
		//
		for (int j = 0; j < length(ss); j++) {
			//
			if (j == 0) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m, Math.min(groupCount, i)), ss[j]);
				//
			} else {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m, Math.min(groupCount, i + 2)), ss[j]);
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap11(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m1, m2 = null;
		//
		String separator;
		//
		String[] ss1, ss2;
		//
		if (Util.matches(m1 = Util.matcher(Pattern.compile(
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)$"),
				s1)) && Util.groupCount(m1) > 3
				&& Util.matches(m2 = Util.matcher(Pattern.compile("^([\\p{InHiragana}|\\p{InKatakana}]+)$"), s2))
				&& Util.groupCount(m2) > 0) {
			//
			final String lastGroup = Util.group(m1, Util.groupCount(m1));
			//
			final String sk = StringUtils.substring(s1, 0, StringUtils.length(s1) - StringUtils.length(lastGroup));
			//
			final Matcher m = Util
					.matcher(Pattern.compile(PATTERN_CJK_UNIFIED_IDEOGRAPHS_HIRAGANA_CJK_UNIFIED_IDEOGRAPHS), sk);
			//
			if (Util.matches(m) && Util.groupCount(m) > 2) {
				//
				ss1 = StringUtils.split(sk, separator = Util.group(m, 2));
				//
				ss2 = StringUtils.split(
						StringUtils.substring(s2, 0, StringUtils.length(s2) - StringUtils.length(lastGroup)),
						separator);
				//
				for (int i = 0; i < orElse(min(mapToInt(Stream.of(ss1, ss2),
						OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean::length)), 0); i++) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), ss1[i],
							ss2[i]);
					//
				} // for
					//
			} // if
				//
		} else if (Util.matches(m1 = Util.matcher(Pattern.compile(
				"^((\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+))(\\p{InHiragana}+)$"),
				testAndApply(Objects::nonNull, s1,
						x -> KanaConverter.convertKana(x, KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA), null)))
				&& Util.groupCount(m1) > 4 && Util.matches(Util.matcher(Pattern.compile("^\\p{InHiragana}+$"), s2))) {
			//
			ss1 = StringUtils.split(Util.group(m1, 1), separator = Util.group(m1, 3));
			//
			ss2 = StringUtils.split(
					StringUtils.substring(s2, 0,
							StringUtils.length(s2) - StringUtils.length(Util.group(m1, Util.groupCount(m1)))),
					separator);
			//
			for (int i = 0; i < orElse(min(mapToInt(Stream.of(ss1, ss2),
					OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean::length)), 0); i++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), ss1[i],
						ss2[i]);
				//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap12(final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		final Matcher m1 = Util.matcher(Pattern.compile(
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)\\s?（((\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+))）$"),
				s1);
		//
		final Matcher m2 = Util.matcher(Pattern.compile("^(\\p{InHiragana}+)\\s?（(\\p{InHiragana}+)）$"), s2);
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 5 && Util.matches(m2) && Util.groupCount(m2) > 1) {
			//
			final int gc1 = Util.groupCount(m1);
			//
			String m2i = null;
			//
			for (int i = 1; i <= Util.groupCount(m2); i++) {
				//
				m2i = Util.group(m2, i);
				//
				if (i == 1) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							Util.group(m1, i), StringUtils.substring(m2i, 0, StringUtils.length(m2i)
									- StringUtils.length(Util.group(m1, Math.min(gc1, i + 1)))));
					//
				} else {
					//
					final String[] ss1 = StringUtils.split(Util.group(m1, Math.min(gc1, i + 1)),
							Util.group(m1, Math.min(gc1, i + 3)));
					//
					final String[] ss2 = StringUtils.split(m2i, Util.group(m1, Math.min(gc1, i + 3)));
					//
					for (int j = 0; j < orElse(
							min(mapToInt(Stream.of(ss1, ss2),
									OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean::length)),
							0); j++) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), ss1[j],
								ss2[j]);
						//
					} // for
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

	@Nullable
	private static Multimap<String, String> toMultimap13(final String s1, final String s2,
			final Iterable<String> kanjiExcluded) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m1, m2;
		//
		String m1i;
		//
		if (Util.matches(m1 = Util
				.matcher(Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)(ヶ)(\\p{InCJKUnifiedIdeographs}+)$"), s1))
				&& Util.groupCount(m1) > 2
				&& Util.matches(m2 = Util.matcher(Pattern.compile(PATTERN_HIRAGANA_GA_HIRAGANA, Pattern.CANON_EQ), s2))
				&& Util.groupCount(m2) > 2) {
			//
			for (int i = 1; i <= orElse(min(mapToInt(Stream.of(m1, m2), Util::groupCount)), 0); i++) {
				//
				if (Boolean.logicalOr(
						!Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
								getUnicodeBlocks(m1i = Util.group(m1, i))),
						IterableUtils.contains(kanjiExcluded, m1i))) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), m1i,
						Util.group(m2, i));
				//
			} // for
				//
		} else if (Util
				.matches(m1 = Util.matcher(Pattern.compile(
						"^(\\p{InCJKUnifiedIdeographs}+)(ケ)(\\p{InCJKUnifiedIdeographs}+)\\s?（\\p{InKatakana}+）*$"),
						s1))
				&& Util.groupCount(m1) > 2
				&& Util.matches(m2 = Util.matcher(Pattern.compile(PATTERN_HIRAGANA_GA_HIRAGANA, Pattern.CANON_EQ), s2))
				&& Util.groupCount(m2) > 2) {
			//
			for (int i = 0; i <= orElse(min(mapToInt(Stream.of(m1, m2), Util::groupCount)), 0); i++) {
				//
				if (!Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
						getUnicodeBlocks(m1i = Util.group(m1, i)))) {
					//
					continue;
					//
				} // if
					//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), m1i,
						Util.group(m2, i));
				//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap14(final String s1, final String s2,
			final Iterable<String> kanjiExcluded) {
		//
		Multimap<String, String> multimap = null;
		//
		Matcher m1, m2;
		//
		String m2i;
		//
		if (Util.matches(m1 = Util.matcher(
				Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)(ヶ)(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)$"),
				s1)) && Util.groupCount(m1) > 3
				&& Util.matches(m2 = Util.matcher(Pattern.compile(PATTERN_HIRAGANA_GA_HIRAGANA, Pattern.CANON_EQ), s2))
				&& Util.groupCount(m2) > 2) {
			//
			String m1i;
			//
			for (int i = 1; i <= orElse(min(mapToInt(Stream.of(m1, m2), Util::groupCount)), 0); i++) {
				//
				if (Boolean.logicalOr(
						!Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
								getUnicodeBlocks(m1i = Util.group(m1, i))),
						IterableUtils.contains(kanjiExcluded, m1i))) {
					//
					continue;
					//
				} // if
					//
				m2i = Util.group(m2, i);
				//
				if (i == 1) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), m1i, m2i);
					//
				} else {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), m1i,
							StringUtils.substring(m2i, 0,
									StringUtils.length(m2i) - StringUtils.length(Util.group(m1, Util.groupCount(m1)))));
					//
				} // if
					//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap15(final String s1, final String s2,
			final Iterable<String> kanjiExcluded) {
		//
		Multimap<String, String> multimap = null;
		//
		final Matcher m1 = Util.matcher(Pattern.compile(
				"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)\\s?（(\\p{InCJKUnifiedIdeographs}+)〜([\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+)）\\s?（([\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+)〜([\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+)）$"),
				s1);
		//
		final Matcher m2 = Util.matcher(Pattern.compile(
				"^([\\p{InHiragana}|\\p{InKatakana}]+)\\s?（(\\p{InHiragana}+)〜(\\p{InHiragana}+)）\\s?（(\\p{InHiragana}+)〜(\\p{InHiragana}+)）$"),
				s2);
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 0 && Util.matches(m2) && Util.groupCount(m2) > 2) {
			//
			final Multimap<String, String> mm = toMultimap15(m1, m2, kanjiExcluded);
			//
			if (mm != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), mm);
				//
			} //
				// if
				//
		} // if
			//
		return multimap;
		//
	}

	private static Multimap<String, String> toMultimap15(final Matcher m1, final Matcher m2,
			final Iterable<String> kanjiExcluded) {
		//
		Multimap<String, String> multimap = null;
		//
		final int gc1 = Util.groupCount(m1);
		//
		Matcher m = null;
		//
		Entry<String, String> entry = null;
		//
		String m2i;
		//
		for (int i = 1; i <= orElse(min(mapToInt(Stream.of(m1, m2), Util::groupCount)), 0); i++) {
			//
			m2i = Util.group(m2, i);
			//
			if (i == 1) {
				//
				if (!IterableUtils.contains(kanjiExcluded,
						Util.getKey(entry = Pair.of(Util.group(m1, i),
								StringUtils.substring(Util.group(m2, i), 0, StringUtils.length(m2i)
										- StringUtils.length(Util.group(m1, Math.min(gc1, i + 1)))))))) {
					//
					put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), entry);
					//
				} // if
					//
			} else if (i == 2) {
				//
				if (!IterableUtils.contains(kanjiExcluded,
						Util.getKey(entry = Pair.of(Util.group(m1, Math.min(gc1, i + 1)), Util.group(m2, i))))) {
					//
					put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), entry);
					//
				} // if
					//
			} else if (Boolean.logicalOr(i == 3, i == 4)) {
				//
				if (Util.matches(m = Util.matcher(Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)$"),
						Util.group(m1, Math.min(gc1, i + 1))))
						&& Util.groupCount(m) > 1
						&& !IterableUtils.contains(kanjiExcluded,
								Util.getKey(entry = Pair.of(Util.group(m, 1), StringUtils.substring(m2i, 0,
										StringUtils.length(m2i) - StringUtils.length(Util.group(m, 2))))))) {
					//
					put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), entry);
					//
				} // if
					//
			} else {
				//
				if (Util.matches(m = Util.matcher(
						Pattern.compile(PATTERN_CJK_UNIFIED_IDEOGRAPHS_HIRAGANA_CJK_UNIFIED_IDEOGRAPHS),
						Util.group(m1, Math.min(gc1, i + 1)))) && Util.groupCount(m) > 2) {
					//
					final String g2 = Util.group(m, 2);
					//
					final String[] ss1 = StringUtils.split(Util.group(m1, Math.min(gc1, i + 1)), g2);
					//
					final String[] ss2 = StringUtils.split(m2i, g2);
					//
					for (int j = 0; j < orElse(
							min(mapToInt(Stream.of(ss1, ss2),
									OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean::length)),
							0); j++) {
						//
						if (!IterableUtils.contains(kanjiExcluded, Util.getKey(entry = Pair.of(ss1[j], ss2[j])))) {
							//
							put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), entry);
							//
						} // if
							//
					} // for
						//
				} // if
					//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	private static <K, V> void put(@Nullable final Multimap<K, V> instance, @Nullable final Entry<K, V> entry) {
		if (instance != null && entry != null) {
			instance.put(Util.getKey(entry), Util.getValue(entry));
		}
	}

	@Nullable
	private static String substringBetween(@Nullable final String str, final String open, final String close) {
		//
		return Boolean.logicalAnd(StringUtils.startsWith(str, open), StringUtils.endsWith(str, close))
				? StringUtils.substringBetween(str, open, close)
				: str;
		//
	}

	private static int length(@Nullable final Object[] instance) {
		return instance != null ? instance.length : 0;
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