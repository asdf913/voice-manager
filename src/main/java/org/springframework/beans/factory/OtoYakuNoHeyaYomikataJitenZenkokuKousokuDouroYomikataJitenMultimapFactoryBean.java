package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.TextNodeUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean
		extends StringMultiMapFromResourceFactoryBean {

	@Nullable
	private Iterable<String> urls = null;

	public void setUrls(@Nullable final Object input) throws JsonProcessingException {
		//
		if (input == null) {
			//
			urls = null;
			//
			return;
			//
		} else if (input instanceof Number || input instanceof Boolean) {
			//
			urls = Collections.singleton(Util.toString(input));
			//
			return;
			//
		} else if (input instanceof Object[] os) {
			//
			urls = Util.toList(Util.map(Arrays.stream(os), Util::toString));
			//
			return;
			//
		} // if
			//
		final Iterable<?> iterable = Util.cast(Iterable.class, input);
		//
		if (iterable != null) {
			//
			urls = Util.toList(Util.map(StreamSupport.stream(iterable.spliterator(), false), Util::toString));
			//
			return;
			//
		} // if
			//
		final String string = Util.toString(input);
		//
		if (StringUtils.isEmpty(string)) {
			//
			urls = Collections.singleton(string);
			//
		} else {
			//
			final Object object = ObjectMapperUtil.readValue(new ObjectMapper(), Util.toString(input), Object.class);
			//
			if (object instanceof Iterable<?>) {
				//
				setUrls(object);
				//
				return;
				//
			} else if (object instanceof CharSequence || object instanceof Number) {
				//
				setUrls(Collections.singleton(object));
				//
				return;
				//
			} else if (object instanceof Map) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
		} // if
			//
		urls = Collections.singleton(Util.toString(input));
		//
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final IValue0<Multimap<String, String>> iValue0 = getIvalue0();
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		Multimap<String, String> multimap = null;
		//
		if (Util.iterator(urls) != null) {
			//
			for (final String url : urls) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						toMultimap(url));
				//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	private static Multimap<String, String> toMultimap(final String url) throws Exception {
		//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
		//
		final List<Element> es = ElementUtil.select(document, "td");
		//
		Element e = null;
		//
		String s1, s2;
		//
		List<Element> nextElementSiblings = null;
		//
		Multimap<String, String> multimap = null;
		//
		PatternMap patternMap = null;
		//
		Matcher matcher = null;
		//
		StringBuilder sb = null;
		//
		int nextElementSiblingsSize = 0, size = 0;
		//
		for (int i = 0; i < IterableUtils.size(es); i++) {
			//
			if ((e = IterableUtils.get(es, i)) == null) {
				//
				continue;
				//
			} // if
			//
			if (Boolean.logicalAnd(
					Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
							getUnicodeBlocks(s1 = ElementUtil.text(e))),
					(nextElementSiblingsSize = IterableUtils
							.size(nextElementSiblings = e.nextElementSiblings())) > 1)) {
				//
				if (Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA),
						getUnicodeBlocks(s2 = ElementUtil.text(IterableUtils.get(nextElementSiblings, 1))))) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
					//
				} else if (Boolean.logicalAnd(Util.matches(matcher = Util.matcher(
						PatternMap.getPattern(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
								"^(\\p{InHiragana}+).+"),
						s2)), Util.groupCount(matcher) > 0)) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
							Util.group(matcher, 1));
					//
				} // if
					//
			} else if (and(
					Util.matches(matcher = Util.matcher(
							PatternMap.getPattern(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new),
									"^(\\p{InCJKUnifiedIdeographs}+)([\\p{InKatakana}\\s]+)$"),
							s1)),
					Util.groupCount(matcher) > 1, nextElementSiblingsSize > 1)) {
				//
				sb = new StringBuilder(ElementUtil.text(IterableUtils.get(nextElementSiblings, 1)));
				//
				for (int j = 0; j < StringUtils.length(Util.group(matcher, 2)); j++) {
					//
					sb.deleteCharAt(StringUtils.length(sb) - 1);
					//
				} // for
					//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(matcher, 1), Objects.toString(sb));
				//
			} // if
				//
			size = MultimapUtil.size(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create));
			//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					toMultimap(patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), s1));
			//
			if (MultimapUtil.size(multimap) > size) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), toMultimap(
					patternMap = ObjectUtils.getIfNull(patternMap, PatternMapImpl::new), s1, nextElementSiblings));
			//
		} // for
			//
		MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
				toMultimap(Util.toList(Util.filter(
						Util.map(NodeUtil.nodeStream(document), x -> Util.cast(TextNode.class, x)), Objects::nonNull)),
						Pattern.compile("^（(\\p{InHIRAGANA}+)）$")));
		//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s1,
			final Iterable<Element> nextElementSiblings) {
		//
		Matcher matcher = null;
		//
		Multimap<String, String> multimap = null;
		//
		final String s2 = IterableUtils.size(nextElementSiblings) > 1
				? ElementUtil.text(IterableUtils.get(nextElementSiblings, 1))
				: null;
		//
		if (Util.matches(matcher = Util.matcher(
				PatternMap.getPattern(patternMap, "^(\\p{InCJKUnifiedIdeographs}+)\\s?（\\p{InCJKUnifiedIdeographs}+）$"),
				s1)) && Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA), getUnicodeBlocks(s2))) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), s2);
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String string) {
		//
		Matcher matcher = null;
		//
		Multimap<String, String> multimap = null;
		//
		if (Boolean
				.logicalAnd(Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
						"^\\p{InCJKUnifiedIdeographs}+\\s?（(\\p{InCJKUnifiedIdeographs}+)）\\s?（(\\p{InHiragana}+)）$"),
						string)), Util.groupCount(matcher) > 1)
				|| Boolean.logicalAnd(
						Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
								"^(\\p{InCJKUnifiedIdeographs}+)\\s?（(\\p{InHiragana}+)）$"), string)),
						Util.groupCount(matcher) > 1)
				|| Boolean.logicalAnd(
						Util.matches(matcher = Util.matcher(PatternMap.getPattern(patternMap,
								"^.+を(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)）$"), string)),
						Util.groupCount(matcher) > 1)) {
			//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
					Util.group(matcher, 1), Util.group(matcher, 2));
			//
		} // if
			//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Iterable<TextNode> textNodes, final Pattern pattern) {
		//
		Matcher matcher = null;
		//
		String s1 = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(textNodes); i++) {
			//
			if (and(Util.matches(matcher = Util.matcher(pattern, TextNodeUtil.text(IterableUtils.get(textNodes, i)))),
					Util.groupCount(matcher) > 0, i > 0)
					&& Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
							getUnicodeBlocks(s1 = TextNodeUtil.text(IterableUtils.get(textNodes, i - 1))))) {
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

	private static boolean and(final boolean a, final boolean b, @Nullable final boolean... bs) {
		//
		boolean result = a && b;
		//
		if (!result) {
			//
			return false;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (!(result &= bs[i])) {
				//
				return false;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
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
			final BiConsumer<T, U> consumer) {
		if (Util.test(instance, t, u)) {
			Util.accept(consumer, t, u);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}