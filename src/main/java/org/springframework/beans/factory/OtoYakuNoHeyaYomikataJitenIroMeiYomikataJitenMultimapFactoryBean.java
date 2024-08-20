package org.springframework.beans.factory;

import java.net.URI;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	@URL("https://hiramatu-hifuka.com/onyak/sikime01.html")
	private String url = null;

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final Iterable<Node> nodes = Util.toList(NodeUtil.nodeStream(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null)));
		//
		Multimap<String, String> multimap = null;
		//
		if (Util.iterator(nodes) != null) {
			//
			String[] ss = null;
			//
			PatternMap patternMap = null;
			//
			for (final Node node : nodes) {
				//
				if (!(node instanceof TextNode) || (ss = StringUtils.split(Util.toString(node), "\u3000")) == null) {
					//
					continue;
					//
				} // if
					//
				for (final String s : ss) {
					//
					MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							toMultimap(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
									StringUtils.trim(s)));
					//
				} // for
					//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s) {
		//
		Iterable<String> patterns = Arrays.asList(
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InBasicLatin}+",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InKatakana}+",
				"^\\p{InHalfwidthAndFullwidthForms}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}$");
		//
		Matcher m = null;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(s)))
					&& Util.groupCount(m) > 1) {
				//
				return ImmutableMultimap.of(Util.group(m, 1), Util.group(m, 2));
				//
			} // if
				//
		} // for
			//
		patterns = Arrays.asList(
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+\\p{InBasicLatin}(\\p{InHiragana}+)\\p{InKatakana}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InBasicLatin}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InKatakana}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InBasicLatin}(\\p{InHiragana}+)\\p{InKatakana}\\p{InBasicLatin}+\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$",
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{Inkatakana}\\p{InCJKUnifiedIdeographs}+\\p{InHalfwidthAndFullwidthForms}$");
		//
		String g1;
		//
		for (final String pattern : patterns) {
			//
			if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap, pattern), StringUtils.trim(s)))
					&& Util.groupCount(m) > 2) {
				//
				return ImmutableMultimap.of(g1 = Util.group(m, 1), Util.group(m, 2), g1, Util.group(m, 3));
				//
			} // if
				//
		} // for
			//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}[\\p{InCJKUnifiedIdeographs}|\\p{InHiragana}]+$"),
				StringUtils.trim(s))) && Util.groupCount(m) > 2) {
			//
			final String g3 = Util.group(m, 3);
			//
			return ImmutableMultimap.of(Util.group(m, 1), g3, Util.group(m, 2), g3);
			//
		} // if
			//
		int groupCount;
		//
		String groupLast = null;
		//
		if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}$"),
				StringUtils.trim(s))) && (groupCount = Util.groupCount(m)) > 4) {
			//
			Multimap<String, String> multimap = null;
			//
			groupLast = Util.group(m, groupCount);
			//
			for (int i = 1; i < groupCount; i++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m, i), groupLast);
				//
			} // for
				//
			return multimap;
			//
		} else if (Util.matches(m = Util.matcher(PatternMap.getPattern(patternMap,
				"^(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InKatakana}(\\p{InCJKUnifiedIdeographs}+)\\p{InHalfwidthAndFullwidthForms}(\\p{InHiragana}+)\\p{InHalfwidthAndFullwidthForms}\\p{InCJKUnifiedIdeographs}+$"),
				StringUtils.trim(s))) && (groupCount = Util.groupCount(m)) > 3) {
			//
			Multimap<String, String> multimap = null;
			//
			groupLast = Util.group(m, groupCount);
			//
			for (int i = 1; i < groupCount; i++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m, i), groupLast);
				//
			} // for
				//
			return multimap;
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