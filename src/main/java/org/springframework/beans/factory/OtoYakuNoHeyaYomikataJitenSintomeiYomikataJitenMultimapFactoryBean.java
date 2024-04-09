package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.jsoup.nodes.TextNode;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

/*
 * https://hiramatu-hifuka.com/onyak/kotoba-1/sintomei.html
 */
public class OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	private Iterable<Link> links = null;

	private IValue0<String> text = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setLinks(final Iterable<Link> links) {
		this.links = links;
	}

	public void setText(final String text) {
		this.text = Unit.with(text);
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final List<Link> ls = Util.toList(Util.filter(
				testAndApply(Objects::nonNull, Util.spliterator(links), x -> StreamSupport.stream(x, false), null),
				x -> text != null && x != null && Objects.equals(x.getText(), IValue0Util.getValue0(text))));
		//
		final int size = IterableUtils.size(ls);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} else if (size == 1) {
			//
			return toMultimap(Link.getUrl(IterableUtils.get(ls, 0)));
			//
		} // if
			//
		return toMultimap(url);
		//
	}

	private static Multimap<String, String> toMultimap(final String url) throws Exception {
		//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
		//
		final List<Element> es = ElementUtil.select(document, "table[border=\"1\"] tr");
		//
		boolean hasAttrRowspan = false;
		//
		List<Element> children = null;
		//
		Integer rowspan = null;
		//
		int offset = 0;
		//
		Pattern p1 = null, p2 = null, p3 = null, p4 = null;
		//
		Matcher m1, m2, m3, m4;
		//
		String s1, s, s2, s3, s41;
		//
		Element e3;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(es); i++) {
			//
			if (hasAttrRowspan = NodeUtil.hasAttr(
					IterableUtils.get(children = ElementUtil.children(IterableUtils.get(es, i)), 0), "rowspan")) {
				//
				rowspan = valueOf(NodeUtil.attr(IterableUtils.get(children, 0), "rowspan"));
				//
			} // if
				//
			if (Util.matches(m1 = Util.matcher(
					p1 = ObjectUtils.getIfNull(p1,
							() -> Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)?$")),
					ElementUtil.text(IterableUtils.get(children,
							0 + (offset = iif(Util.or(rowspan == null, hasAttrRowspan, intValue(rowspan, 0) <= 0), 1,
									0))))))
					&& Util.groupCount(m1) > 0
					&& Util.matches(m2 = Util.matcher(
							p2 = ObjectUtils.getIfNull(p2, () -> Pattern.compile("^\\p{InHiragana}+$")),
							ElementUtil.text(IterableUtils.get(children, 2 + offset))))) {
				//
				s1 = Util.group(m1, 1);
				//
				s2 = Util.group(m2, 0);
				//
				if (StringUtils.endsWith(s2, s = Util.group(m1, 2))) {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1,
							StringUtils.substring(s2, 0, StringUtils.length(s2) - StringUtils.length(s)));
					//
				} else {
					//
					MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s1, s2);
					//
				} // if
					//
			} // if
				//
			if (Util.matches(
					m3 = Util
							.matcher(
									p3 = ObjectUtils.getIfNull(p3,
											() -> Pattern.compile(
													"(\\p{InCJKUnifiedIdeographs}+)（(\\p{InHiragana}+)[\\)）]")),
									s3 = ElementUtil.text(e3 = IterableUtils.get(children, 3 + offset))))
					&& Util.groupCount(m3) > 1) {
				//
				MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create),
						Util.group(m3, 1), Util.group(m3, 2));
				//
			} else if (e3 != null && e3.childNodeSize() == 1
					&& IterableUtils.get(NodeUtil.childNodes(e3), 0) instanceof TextNode
					&& (m4 = Util.matcher(
							p4 = ObjectUtils.getIfNull(p4, () -> Pattern.compile(
									"([\\p{InCJKUnifiedIdeographs}|\\p{InKatakana}]+)（(\\p{InHiragana}+)[\\)）]")),
							s3)) != null) {
				//
				while (Util.find(m4) && Util.groupCount(m4) > 1) {
					//
					if (Objects.equals(Collections.singletonList(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS),
							getUnicodeBlocks(s41 = Util.group(m4, 1)))) {
						//
						MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, LinkedHashMultimap::create), s41,
								Util.group(m4, 2));
						//
					} // if
						//
				} // while
					//
			} // if
				//
			rowspan = decrease(rowspan, 1);
			//
		} // for
			//
		return multimap;
		//
	}

	private static Integer decrease(final Integer instance, final int i) {
		return Integer.valueOf(intValue(instance, 0) - i);
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

	private static int iif(final boolean condition, final int trueValue, final int falseValue) {
		return condition ? trueValue : falseValue;
	}

	private static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	@Nullable
	private static Integer valueOf(final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Integer.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
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