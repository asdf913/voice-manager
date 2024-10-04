package org.springframework.beans.factory;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

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
		IntObj<String> intObj = null;
		//
		for (int i = 0; i < size; i++) {
			//
			(intObj = new IntObj<>()).integer = i;
			//
			intObj.value = IterableUtils.get(lines, i);
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), intObj, lines));
			//
		} // for
			//
		return multimap;
		//
	}

	private static class IntObj<V> {

		private int integer;

		private V value;

	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final IntObj<String> intObj,
			final Iterable<String> lines) {
		//
		Multimap<String, String> multimap = null;
		//
		final Matcher m1 = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKUnifiedIdeographs}+\\p{InHiragana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InHiragana}[\\p{InCJKUnifiedIdeographs}\\p{InHiragana}\\p{InCJKSymbolsAndPunctuation}\\p{InHalfwidthAndFullwidthForms}]+$"),
				intObj != null ? intObj.value : null);
		//
		if (Util.matches(m1) && Util.groupCount(m1) > 1) {
			//
			final String g12 = Util.group(m1, 2);
			//
			Matcher m2 = null;
			//
			String g21, g22, csk, csv;
			//
			for (int j = 0; j < IterableUtils.size(lines); j++) {
				//
				if (intObj != null && intObj.integer == j) {
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
				} // if
					//
			} // for
				//
		} // if
			//
		MultimapUtil.remove(multimap, "入替", "いれかわり");
		//
		return multimap;
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