package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;

/*
 * https://hiramatu-hifuka.com/onyak/kotoba-1/kisyo.html
 */
public class OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		Multimap<String, String> multimap = null;
		//
		final List<Element> es = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null), "tr");
		//
		Element e = null;
		//
		List<String> ss1, ss2 = null;
		//
		String s1, s2 = null;
		//
		List<UnicodeBlock> unicodeBlocks = null;
		//
		Matcher matcher = null;
		//
		final char[] cs = new char[] { '・', '、' };
		//
		String[] ss = null;
		//
		IValue0<String> iv0 = null, iv1 = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((e = es.get(i)) == null || e.childrenSize() < 3) {
				//
				continue;
				//
			} // if
				//
			ss2 = getStrings(s2 = StringUtils.deleteWhitespace(ElementUtil.text(IterableUtils.get(e.children(), 1))),
					UnicodeBlock.HIRAGANA);
			//
			if (StringUtils.containsAny(
					s1 = StringUtils.deleteWhitespace(ElementUtil.text(IterableUtils.get(e.children(), 0))), cs)) {
				//
				ss = null;
				//
				for (int j = 0; cs != null && j < cs.length; j++) {
					//
					if ((ss = StringUtils.split(s1, cs[j])) != null && ss.length > 1) {
						//
						break;
						//
					} // if
						//
				} // if
					//
				for (int j = 0; ss != null && j < ss.length; j++) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), ss[j],
							IterableUtils.size(ss2) == 1 ? IterableUtils.get(ss2, 0)
									: IterableUtils.get(ss2, Math.min(j, IterableUtils.size(ss2) - 1)));
					//
				} // for
					//
			} else if (matches(s1, "^(\\p{InCJKUnifiedIdeographs}|々|・|、|\\p{InHiragana})+$")) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
						IterableUtils.get(ss2, 0));
				//
			} else if (matches(s1, "^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InCJKUnifiedIdeographs}+)）$")) {
				//
				matcher = Util.matcher(
						Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)（(\\p{InCJKUnifiedIdeographs}+)）$"), s1);
				//
				while (matcher != null && matcher.find() && matcher.groupCount() > 1) {
					//
					for (int j = 1; j < matcher.groupCount() + 1; j++) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
								matcher.group(j), IterableUtils.get(ss2, 0));
						//
					} // for
						//
				} // while
					//
			} else if (Objects.equals(Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
					unicodeBlocks = getUnicodeBlocks(s1))
					|| Objects.equals(Arrays.asList(UnicodeBlock.KATAKANA, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
							unicodeBlocks)
					|| Objects.equals(Arrays.asList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS, UnicodeBlock.KATAKANA),
							unicodeBlocks)
					|| Objects.equals(Arrays.asList(UnicodeBlock.GREEK, UnicodeBlock.BASIC_LATIN,
							UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), unicodeBlocks)
					|| Objects.equals(Arrays.asList(UnicodeBlock.KATAKANA, UnicodeBlock.BASIC_LATIN,
							UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), unicodeBlocks)) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.collect(Util.filter(Util.stream(getStrings(s1, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)),
								StringUtils::isNotBlank), Collectors.joining()),
						Util.collect(Util.filter(Util.stream(ss2), StringUtils::isNotBlank), Collectors.joining()));
				//
			} else if (Objects
					.equals(Arrays.asList(UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS, UnicodeBlock.KATAKANA,
							UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), unicodeBlocks = getUnicodeBlocks(s1))
					|| Objects.equals(Arrays.asList(UnicodeBlock.BASIC_LATIN, UnicodeBlock.KATAKANA,
							UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS), unicodeBlocks = getUnicodeBlocks(s1))
					|| Objects.equals(Arrays.asList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS, UnicodeBlock.BASIC_LATIN),
							unicodeBlocks = getUnicodeBlocks(s1))
					|| Objects.equals("国際防災の10年", s1)) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
						Util.collect(Util.filter(Util.stream(ss2), StringUtils::isNotBlank), Collectors.joining()));
				//
			} else if (matches(s1, "[\\p{InKatakana}|A-Z]+の(\\p{InCJKUnifiedIdeographs}+)")) {
				//
				iv0 = iv1 = null;
				//
				matcher = Util.matcher(Pattern.compile("[\\p{InKatakana}|A-Z]+の(\\p{InCJKUnifiedIdeographs}+)"), s1);
				//
				while (matcher != null && matcher.find() && matcher.groupCount() > 0) {
					//
					iv0 = Unit.with(matcher.group(1));
					//
				} // while
					//
				matcher = Util.matcher(Pattern.compile("\\p{InKatakana}+の(\\p{InHiragana}+)"), s2);
				//
				while (matcher != null && matcher.find() && matcher.groupCount() > 0) {
					//
					iv1 = Unit.with(matcher.group(1));
					//
				} // while
					//
				if (iv0 != null && iv1 != null) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							IValue0Util.getValue0(iv0), IValue0Util.getValue0(iv1));
					//
				} // if
					//
			} else if (matches(s1, "^\\p{InKatakana}+の([\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+)")) {
				//
				iv0 = iv1 = null;
				//
				matcher = Util.matcher(
						Pattern.compile("^\\p{InKatakana}+の([\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+)"), s1);
				//
				while (matcher != null && matcher.find() && matcher.groupCount() > 0) {
					//
					iv0 = Unit.with(matcher.group(1));
					//
				} // while
					//
				matcher = Util.matcher(Pattern.compile("\\p{InKatakana}+の(\\p{InHiragana}+)"), s2);
				//
				while (matcher != null && matcher.find() && matcher.groupCount() > 0) {
					//
					iv1 = Unit.with(matcher.group(1));
					//
				} // while
					//
				if (iv0 != null && iv1 != null) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							IValue0Util.getValue0(iv0), IValue0Util.getValue0(iv1));
					//
				} // if
					//
			} else if (Objects.equals("凶暴な50度", s1)) {
				//
				ss1 = Util.toList(Util.filter(
						Util.stream(getStrings(s1, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS, UnicodeBlock.HIRAGANA)),
						StringUtils::isNotBlank));
				//
				ss2 = Util.toList(
						Util.filter(Util.stream(getStrings(s2, UnicodeBlock.HIRAGANA)), StringUtils::isNotBlank));
				//
				for (int j = 0; j < Math.min(IterableUtils.size(ss1), IterableUtils.size(ss2)); j++) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							IterableUtils.get(ss1, j), IterableUtils.get(ss2, j));
					//
				} // for
					//
			} else if (Objects.equals("荒天の40度", s1)) {
				//
				ss1 = ss2 = null;
				//
				matcher = Util.matcher(
						Pattern.compile("(\\p{InCJKUnifiedIdeographs}+)の\\d+(\\p{InCJKUnifiedIdeographs}+)"), s1);
				//
				while (matcher != null && matcher.find() && matcher.groupCount() > 1) {
					//
					for (int j = 1; j <= matcher.groupCount(); j++) {
						//
						Util.add(ss1 = ObjectUtils.getIfNull(ss1, ArrayList::new), matcher.group(j));
						//
					} // for
						//
				} // while
					//
				matcher = Util.matcher(Pattern.compile("(\\p{InHiragana}+)の\\d+(\\p{InHiragana}+)"), s2);
				//
				while (matcher != null && matcher.find() && matcher.groupCount() > 1) {
					//
					for (int j = 1; j <= matcher.groupCount(); j++) {
						//
						Util.add(ss2 = ObjectUtils.getIfNull(ss2, ArrayList::new), matcher.group(j));
						//
					} // if
						//
				} // while
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
		} // for
			//
		return multimap;
		//
	}

	private static boolean matches(@Nullable final String instance, @Nullable final String regex) {
		//
		try {
			//
			if (instance == null || Narcissus.getField(instance, String.class.getDeclaredField("value")) == null) {
				//
				return false;
				//
			} // if
				//
		} catch (final NoSuchFieldException e) {
			//
			LoggerUtil.error(
					LoggerFactory.getLogger(OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean.class),
					e.getMessage(), e);
			//
		} // try
			//
		return regex != null && instance.matches(regex);
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
		if (Util.test(instance, t, u) && consumer != null) {
			consumer.accept(t, u);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static List<String> getStrings(final String string, final UnicodeBlock ub, final UnicodeBlock... ubs) {
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