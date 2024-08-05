package org.springframework.beans.factory;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;

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

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.mariten.kanatools.KanaConverter;

public class OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	@URL("https://hiramatu-hifuka.com/onyak/onyak2/isekindx.html")
	private String url = null;

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
		//
		final List<Element> tables = ElementUtil.select(document, "table");
		//
		Iterable<Element> children;
		//
		Element tbody;
		//
		PatternMap patternMap = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(tables); i++) {
			//
			if (Boolean.logicalOr(!StringUtils.equalsIgnoreCase(
					ElementUtil.tagName(tbody = testAndApply(x -> IterableUtils.size(x) > 0,
							ElementUtil.children(IterableUtils.get(tables, i)), x -> IterableUtils.get(x, 0), null)),
					"tbody"), IterableUtils.size(children = ElementUtil.children(tbody)) <= 0)) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), children));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final Iterable<Element> trs) {
		//
		if (Util.iterator(trs) == null) {
			//
			return null;
			//
		} // if
			//
		Multimap<String, String> multimap = null;
		//
		Iterable<Element> tds = null;
		//
		for (final Element tr : trs) {
			//
			if (IterableUtils.size(tds = ElementUtil.children(tr)) != 3) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(patternMap, ElementUtil.text(IterableUtils.get(tds, 0)),
							ElementUtil.text(IterableUtils.get(tds, 1))));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s1, final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		boolean isCJKUnifiedIdeographs, isHiragana;
		//
		String g1;
		//
		Matcher matcher;
		//
		String[] ss = null;
		//
		if (Boolean.logicalAnd(
				isCJKUnifiedIdeographs = Util.matches(
						Util.matcher(PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)$"), s1)),
				isHiragana = Util
						.matches(Util.matcher(PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)$"), s2)))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
			//
		} else if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
				"^\\p{InCJKSymbolsAndPunctuation}+(\\p{InCJKUnifiedIdeographs}+)\\p{InCJKSymbolsAndPunctuation}+(\\p{InCJKUnifiedIdeographs}+)$"),
				s1)) && Util.groupCount(matcher) > 1 && isHiragana) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					StringUtils.join(Util.group(matcher, 1), Util.group(matcher, 2)), s2);
			//
		} else if (Boolean.logicalAnd(isCJKUnifiedIdeographs,
				Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
						"^\\p{InHalfwidthAndFullwidthForms}?(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}?$"),
						s2)) && Util.groupCount(matcher) > 0)) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
					Util.group(matcher, 1));
			//
		} else if ((Util
				.matches(matcher = Util.matcher(
						PatternMap.getPattern(patternMap,
								"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)$"),
						s1))
				&& Util.groupCount(matcher) > 2 && isHiragana
				&& (ss = StringUtils.split(s2, Util.group(matcher, 2))) != null && ss.length == 2)
				|| (Util.matches(matcher = Util.matcher(
						PatternMap.getPattern(patternMap,
								"^(\\p{InCJKUnifiedIdeographs}+)(\\p{InKatakana}+)(\\p{InCJKUnifiedIdeographs}+)$"),
						s1)) && Util.groupCount(matcher) > 1 && isHiragana
						&& (ss = StringUtils.split(s2, KanaConverter.convertKana(Util.group(matcher, 2),
								KanaConverter.OP_ZEN_KATA_TO_ZEN_HIRA))) != null
						&& ss.length == 2)) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), ss[0]);
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 3), ss[1]);
			//
		} else if (Util
				.matches(matcher = Util.matcher(
						PatternMap.getPattern(patternMap, "^(\\p{InHiragana}+)(\\p{InCJKUnifiedIdeographs}+)$"), s1))
				&& Util.groupCount(matcher) > 1 && isHiragana
				&& StringUtils.startsWith(s2, g1 = Util.group(matcher, 1))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 2), StringUtils.substring(s2, StringUtils.length(g1)));
			//
		} // if
			//
		return multimap;
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