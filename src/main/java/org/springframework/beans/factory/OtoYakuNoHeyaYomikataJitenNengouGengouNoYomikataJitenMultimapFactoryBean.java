package org.springframework.beans.factory;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	@URL("https://hiramatu-hifuka.com/onyak/rekisi/nenngo.html")
	private String url = null;

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final List<Element> trs = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null), "tr");
		//
		Iterable<Element> tds;
		//
		PatternMap patternMap = null;
		//
		Multimap<String, String> multimap = null;
		//
		String s1;
		//
		Matcher matcher = null;
		//
		int index;
		//
		for (int i = 0; i < IterableUtils.size(trs); i++) {
			//
			if (!ArrayUtils.contains(new int[] { 4, 5 },
					IterableUtils.size(tds = ElementUtil.children(IterableUtils.get(trs, i))))) {
				//
				continue;
				//
			} // if
				//
			if (Util.matches(
					Util.matcher(
							PatternMap.getPattern(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
									"^\\p{InCJKUnifiedIdeographs}+$"),
							s1 = ElementUtil.text(IterableUtils.get(tds,
									index = (NodeUtil.hasAttr(IterableUtils.get(tds, 0), "rowspan")) ? 1 : 0))))
					&& Util.matches(matcher = Util.matcher(
							PatternMap.getPattern(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
									"^（(\\p{InHiragana}+)）$"),
							ElementUtil.text(IterableUtils.get(tds, index + 1))))
					&& Util.groupCount(matcher) > 0) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
						Util.group(matcher, 1));
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}