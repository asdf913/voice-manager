package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.mariten.kanatools.KanaConverter;

/*
 * https://hiramatu-hifuka.com/onyak/onyak2/yuryodo.html
 */
public class OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean
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
			if (MultimapUtil.size(multimap) == size && (mm = toMultimap4(s1, s2)) != null) {
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
		} // for
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
		} else if (Util
				.matches(m1 = Util.matcher(Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)$"), s1))
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
			for (int j = 1; j <= Stream.of(m1, m2).mapToInt(Util::groupCount).min().orElse(0); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m1, j), Util.group(m2, j));
				//
			} // for
				//
		} else if (Util
				.matches(m1 = Util.matcher(Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)$"), s1))
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
			for (int j = 1; j <= Stream.of(m1, m2).mapToInt(Util::groupCount).min().orElse(0); j++) {
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
			for (int j = 1; j <= Stream.of(m1, m2).mapToInt(Util::groupCount).min().orElse(0); j++) {
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
			for (int j = 1; j <= Stream.of(m1, m2).mapToInt(Util::groupCount).min().orElse(0); j++) {
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
			final Matcher m = Util.matcher(
					Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)$"),
					string);
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