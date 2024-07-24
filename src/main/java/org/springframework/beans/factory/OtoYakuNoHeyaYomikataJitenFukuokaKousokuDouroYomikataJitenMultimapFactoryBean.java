package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.text.TextStringBuilder;
import org.apache.commons.text.TextStringBuilderUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

public class OtoYakuNoHeyaYomikataJitenFukuokaKousokuDouroYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	@URL("https://hiramatu-hifuka.com/onyak/onyak2/tosiko01.html")
	private String url = null;

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
		//
		final List<Element> es1 = ElementUtil.select(document, "table[border=\"1\"] tr td[colspan=\"3\"]");
		//
		Element e = null;
		//
		List<Element> nextElementSiblings = null;
		//
		String s1, s2 = null;
		//
		Multimap<String, String> multimap = null;
		//
		Pattern pattern = null;
		//
		int size = 0;
		//
		for (int i = 0; i < IterableUtils.size(es1); i++) {
			//
			if ((e = IterableUtils.get(es1, i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
					getUnicodeBlocks(s1 = ElementUtil.text(e)))
					&& (size = IterableUtils.size(nextElementSiblings = e.nextElementSiblings())) > 1
					&& Objects.equals(Collections.singletonList(UnicodeBlock.HIRAGANA),
							getUnicodeBlocks(s2 = ElementUtil.text(IterableUtils.get(nextElementSiblings, 1))))) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
				//
			} else {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						toMultimap(pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("\\p{InHiragana}")),
								s1,
								IterableUtils.size(nextElementSiblings = e.nextElementSiblings()) > 1
										? ElementUtil.text(IterableUtils.get(nextElementSiblings, 1))
										: null));
				//
			} // if
				//
			if (size > 2) {
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						toMultimap(ElementUtil.text(IterableUtils.get(nextElementSiblings, 2))));
				//
			} // if
				//
		} // for
			//
		MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
				toMultimap(document != null ? Util.toList(document.nodeStream()) : null));
		//
		return multimap;
		//
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Iterable<Node> nodes) {
		//
		if (Util.iterator(nodes) == null) {
			//
			return null;
			//
		} // if
			//
		boolean b = false;
		//
		TextNode textNode = null;
		//
		Pattern pattern = null;
		//
		Matcher matcher = null;
		//
		String tagName, text, s = null;
		//
		Integer end = null;
		//
		Multimap<String, String> multimap = null;
		//
		char[] cs = null;
		//
		int size = 0, length;
		//
		for (final Node node : nodes) {
			//
			if (Objects.equals("建設中路線", Util.toString(node))) {
				//
				b = true;
				//
			} else if (Boolean.logicalAnd(b,
					Boolean.logicalOr(
							StringUtils.equalsIgnoreCase("img",
									tagName = ElementUtil.tagName(Util.cast(Element.class, node))),
							StringUtils.equalsIgnoreCase("p", tagName)))) {
				//
				b = false;
				//
			} // if
				//
			if (Boolean.logicalAnd(b, (textNode = Util.cast(TextNode.class, node)) != null)) {
				//
				matcher = Util.matcher(
						pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("（(\\p{InHIRAGANA}+)）")),
						text = textNode.text());
				//
				end = null;
				//
				while (Util.find(matcher)) {
					//
					size = MultimapUtil.size(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create));
					//
					MultimapUtil.putAll(multimap, toMultimap(
							cs = ObjectUtils.getIfNull(cs, () -> new char[] { '区', '\u3000' }), matcher, text, end));
					//
					if (Util.and(
							StringUtils.countMatches(
									s = StringUtils.substring(text, Util.intValue(end, 0), start(matcher)), '区') == 1,
							StringUtils.countMatches(s, '\u3000') == 1,
							StringUtils.indexOf(s, '区') > StringUtils.indexOf(s, '\u3000'))) {
						//
						removeAll(multimap, StringUtils.substringAfter(s, "\u3000"));
						//
					} // if
						//
					if (Boolean.logicalAnd(size == MultimapUtil.size(multimap), (length = StringUtils
							.length(s = StringUtils.substring(text, Util.intValue(end, 0), start(matcher)))) >= 2)) {
						//
						MultimapUtil.put(multimap, StringUtils.substring(s, length - 2, length),
								testAndApply(x -> Util.groupCount(x) > 0, matcher, x -> Util.group(x, 1), Util::group));
						//
					} // if
						//
					end = end(matcher);
					//
				} // while
					//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	private static void removeAll(final Multimap<?, ?> instance, final Object key) {
		if (instance != null) {
			instance.removeAll(key);
		}
	}

	@Nullable
	private static Multimap<String, String> toMultimap(@Nullable final char[] cs, final Matcher matcher,
			final String text, @Nullable final Number end) {
		//
		Multimap<String, String> multimap = null;
		//
		if (cs != null) {
			//
			String s = null;
			//
			for (final char c : cs) {
				//
				if (StringUtils.countMatches(s = StringUtils.substring(text, Util.intValue(end, 0), start(matcher)),
						c) == 1) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
							StringUtils.substringAfter(s, c),
							testAndApply(x -> Util.groupCount(x) > 0, matcher, x -> Util.group(x, 1), Util::group));
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
	private static Integer start(@Nullable final MatchResult instance) {
		return instance != null ? Integer.valueOf(instance.start()) : null;
	}

	@Nullable
	private static Integer end(@Nullable final MatchResult instance) {
		return instance != null ? Integer.valueOf(instance.end()) : null;
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final String s) throws IOException {
		//
		final List<UnicodeBlock> ubs = getUnicodeBlocks(s);
		//
		final char[] cs = Util.toCharArray(s);
		//
		if (!Util.and(Util.contains(ubs, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
				Util.contains(ubs, UnicodeBlock.HIRAGANA),
				Util.contains(ubs, UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS), cs != null)) {
			//
			return null;
			//
		} // if
			//
		final List<FailableFunction<String, IValue0<Multimap<String, String>>, IOException>> functions = Arrays.asList(
				OtoYakuNoHeyaYomikataJitenFukuokaKousokuDouroYomikataJitenMultimapFactoryBean::toMultimap1,
				OtoYakuNoHeyaYomikataJitenFukuokaKousokuDouroYomikataJitenMultimapFactoryBean::toMultimap2);
		//
		IValue0<Multimap<String, String>> iValue0 = null;
		//
		for (int i = 0; i < IterableUtils.size(functions); i++) {
			//
			if ((iValue0 = FailableFunctionUtil.apply(IterableUtils.get(functions, i), s)) != null) {
				//
				return IValue0Util.getValue0(iValue0);
				//
			} // for
				//
		} // for
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap1(final String s) throws IOException {
		//
		if (StringUtils.countMatches(s, '）') <= 1) {
			//
			return null;
			//
		} // if
			//
		String s1 = null, s2 = null;
		//
		char c;
		//
		TextStringBuilder tsb = null;
		//
		Multimap<String, String> multimap = null;
		//
		final char[] cs = Util.toCharArray(s);
		//
		for (int j = 0; j < length(cs); j++) {
			//
			if (Objects.equals(UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS, UnicodeBlock.of(c = cs[j]))) {
				//
				if (c == '（') {
					//
					s1 = Objects.toString(tsb);
					//
					s2 = null;
					//
				} else if (c == '）') {
					//
					s2 = Objects.toString(tsb);
					//
				} // if
					//
				TextStringBuilderUtil.clear(tsb = ObjectUtils.getIfNull(tsb, TextStringBuilder::new));
				//
				MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						toMultimap(MultimapUtil.isEmpty(multimap), s1, s2));
				//
			} else {
				//
				append(tsb = ObjectUtils.getIfNull(tsb, TextStringBuilder::new), c);
				//
			} // if
				//
		} // for
			//
		return Unit.with(multimap);
		//
	}

	@Nullable
	private static IValue0<Multimap<String, String>> toMultimap2(final String s) {
		//
		if (StringUtils.countMatches(s, '）') <= 0) {
			//
			return null;
			//
		} // if
			//
		int a = StringUtils.indexOf(s, '市');
		//
		int b = StringUtils.indexOf(s, '（');
		//
		int c = StringUtils.indexOf(s, "く）");
		//
		if (Boolean.logicalAnd(a < b, b < c)) {
			//
			return Unit.with(ImmutableMultimap.of(StringUtils.substring(s, a + 1, b),
					StringUtils.substring(s, b + 1, Math.min(c + 1, StringUtils.length(s)))));
			//
		} else if (StringUtils.endsWith(s, "）へ")
				&& (a = StringUtils.indexOf(s, '（')) < (b = StringUtils.indexOf(s, '）'))) {
			//
			return Unit.with(ImmutableMultimap.of(StringUtils.substring(s, 0, a), StringUtils.substring(s, a + 1, b)));
			//
		} else if ((a = StringUtils.indexOf(s, '（')) < (b = StringUtils.indexOf(s, '）'))
				&& b == StringUtils.length(s) - 1 && StringUtils.countMatches(s, '区') == 1
				&& (c = StringUtils.indexOf(s, '区')) < a) {
			//
			return Unit
					.with(ImmutableMultimap.of(StringUtils.substring(s, c + 1, a), StringUtils.substring(s, a + 1, b)));
			//
		} else if (StringUtils.endsWith(s, "目") && (a = StringUtils.indexOf(s, '（')) < (b = StringUtils.indexOf(s, '）'))
				&& StringUtils.countMatches(s, '区') == 1 && (c = StringUtils.indexOf(s, '区')) < a) {
			//
			return Unit
					.with(ImmutableMultimap.of(StringUtils.substring(s, c + 1, a), StringUtils.substring(s, a + 1, b)));
			//
		} else if ((a = StringUtils.indexOf(s, '（')) < (b = StringUtils.indexOf(s, '）'))
				&& StringUtils.countMatches(s, '市') == 1 && (c = StringUtils.indexOf(s, '市')) < a) {
			//
			return Unit
					.with(ImmutableMultimap.of(StringUtils.substring(s, c + 1, a), StringUtils.substring(s, a + 1, b)));
			//
		} // if
			//
		return null;
		//
	}

	private static int length(@Nullable final char[] instance) {
		return instance != null ? instance.length : 0;
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final boolean first, @Nullable final String s1,
			@Nullable final String s2) {
		//
		Multimap<String, String> multimap = null;
		//
		if (Boolean.logicalAnd(StringUtils.isNotBlank(s1), StringUtils.isNotBlank(s2))) {
			//
			if (first) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						StringUtils.substring(s1, 4), s2);
				//
			} else {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
				//
			} // if
				//
		} // if
			//
		return multimap;
		//
	}

	private static void append(@Nullable final Appendable instance, final char c) throws IOException {
		if (instance != null) {
			instance.append(c);
		}
	}

	@Nullable
	private static Multimap<String, String> toMultimap(final Pattern pattern, final String s1,
			@Nullable final String s2) {
		//
		final StringBuilder sb = new StringBuilder();
		//
		final Matcher matcher = Util.matcher(pattern, s1);
		//
		while (matcher != null && matcher.find()) {
			//
			sb.append(Util.group(matcher));
			//
		} // while
			//
		Multimap<String, String> multimap = null;
		//
		if (StringUtils.length(sb) == 1 && StringUtils.countMatches(s2, sb) == 1) {
			//
			final String s = Objects.toString(sb);
			//
			final String[] ss1 = StringUtils.split(s1, s);
			//
			final String[] ss2 = StringUtils.split(s2, s);
			//
			for (int j = 0; ss1 != null && ss2 != null && j < Math.min(ss1.length, ss2.length); j++) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), ss1[j],
						ss2[j]);
				//
			} // for
				//
		} // if
			//
		return multimap;
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
			final BiConsumer<T, U> consumer) {
		if (Util.test(instance, t, u)) {
			Util.accept(consumer, t, u);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
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