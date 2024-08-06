package org.springframework.beans.factory;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;

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
import org.jsoup.nodes.NodeUtil;

import com.google.common.collect.ImmutableMultimap;
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
		int index;
		//
		IValue0<Multimap<String, String>> iValue0 = null;
		//
		Element td0;
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
			if ((iValue0 = toMultimap(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
					ElementUtil.text(IterableUtils.get(tds,
							index = (NodeUtil.hasAttr(td0 = IterableUtils.get(tds, 0), "rowspan")
									|| NodeUtil.hasAttr(td0, "bgcolor")) ? 1 : 0)),
					ElementUtil.text(IterableUtils.get(tds, index + 1)))) != null) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						IValue0Util.getValue0(iValue0));
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	private static IValue0<Multimap<String, String>> toMultimap(final PatternMap patternMap, final String s1,
			final String s2) {
		//
		final boolean isCJKUnifiedIdeographs = Util
				.matches(Util.matcher(PatternMap.getPattern(patternMap, "^\\p{InCJKUnifiedIdeographs}+$"), s1));
		//
		Matcher m2 = Util.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InHiragana}+)）$"), s2);
		//
		if (Boolean.logicalAnd(isCJKUnifiedIdeographs, Util.matches(m2) && Util.groupCount(m2) > 0)) {
			//
			return Unit.with(ImmutableMultimap.of(s1, Util.group(m2, 1)));
			//
		} else if (isCJKUnifiedIdeographs && Util.matches(m2 = Util
				.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InHiragana}+)）\\s（(\\p{InHiragana}+)）$"), s2))) {
			//
			Multimap<String, String> multimap = null;
			//
			for (int j = 1; j <= Util.groupCount(m2); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
						Util.group(m2, j));
				//
			} // for
				//
			return Unit.with(multimap);
			//
		} // if
			//
		Matcher m1;
		//
		if (Boolean
				.logicalAnd(
						Util.matches(
								m1 = Util.matcher(
										PatternMap.getPattern(patternMap,
												"^(\\p{InCJKUnifiedIdeographs}+)\\p{InCJKSymbolsAndPunctuation}+$"),
										s1))
								&& Util.groupCount(m1) > 0,
						Util.matches(m2 = Util.matcher(PatternMap.getPattern(patternMap, "^（(\\p{InHiragana}+)）$"), s2))
								&& Util.groupCount(m2) > 0)) {
			//
			return Unit.with(ImmutableMultimap.of(Util.group(m1, 1), Util.group(m2, 1)));
			//
		} // if
			//
		return null;
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