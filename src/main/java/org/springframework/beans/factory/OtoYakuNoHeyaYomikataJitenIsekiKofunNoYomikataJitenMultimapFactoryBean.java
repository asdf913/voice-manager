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
		Element table;
		//
		Iterable<Element> children, tds;
		//
		Element tbody;
		//
		PatternMap patternMap = null;
		//
		String s1, s2;
		//
		Multimap<String, String> multimap = null;
		//
		boolean isCJKUnifiedIdeographs, isHiragana;
		//
		Matcher matcher = null;
		//
		for (int i = 0; tables != null && i < tables.size(); i++) {
			//
			if ((table = tables.get(i)) == null
					|| IterableUtils.size(children = ElementUtil.children(table)) <= 0 || !StringUtils
							.equalsIgnoreCase(ElementUtil.tagName(tbody = IterableUtils.get(children, 0)), "tbody")
					|| IterableUtils.size(children = ElementUtil.children(tbody)) <= 0) {
				//
				continue;
				//
			} // if
				//
			for (final Element tr : children) {
				//
				if (IterableUtils.size(tds = ElementUtil.children(tr)) != 3) {
					//
					continue;
					//
				} // if
					//
				if (Boolean
						.logicalAnd(
								isCJKUnifiedIdeographs = Util
										.matches(Util.matcher(
												PatternMap.getPattern(
														patternMap = ObjectUtils.getIfNull(patternMap,
																PatternMapImpl::new),
														"^(\\p{InCJKUnifiedIdeographs}+)$"),
												s1 = ElementUtil.text(IterableUtils.get(tds, 0)))),
								isHiragana = Util
										.matches(
												Util.matcher(
														PatternMap
																.getPattern(
																		patternMap = ObjectUtils.getIfNull(patternMap,
																				PatternMapImpl::new),
																		"^(\\p{InHiragana}+)$"),
														s2 = ElementUtil.text(IterableUtils.get(tds, 1)))))) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
					//
				} else if (Util.matches(matcher = Util.matcher(PatternMap.getPattern(
						patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
						"^\\p{InCJKSymbolsAndPunctuation}+(\\p{InCJKUnifiedIdeographs}+)\\p{InCJKSymbolsAndPunctuation}+(\\p{InCJKUnifiedIdeographs}+)$"),
						s1)) && Util.groupCount(matcher) > 1 && isHiragana) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							StringUtils.join(Util.group(matcher, 1), Util.group(matcher, 2)), s2);
					//
				} else if (isCJKUnifiedIdeographs && Util.matches(matcher = Util.matcher(PatternMap.getPattern(
						patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
						"^\\p{InHalfwidthAndFullwidthForms}?(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}?$"),
						s2)) && Util.groupCount(matcher) > 0) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
							Util.group(matcher, 1));
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