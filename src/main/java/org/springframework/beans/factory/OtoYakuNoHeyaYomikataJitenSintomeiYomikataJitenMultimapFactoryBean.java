package org.springframework.beans.factory;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

/*
 * https://hiramatu-hifuka.com/onyak/kotoba-1/sintomei.html
 */
public class OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean
		implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null);
		//
		final List<Element> es = ElementUtil.select(document, "table[border=\"1\"] tr");
		//
		Element e = null;
		//
		boolean hasAttrRowspan = false;
		//
		List<Element> children = null;
		//
		Integer rowspan = null;
		//
		int offset = 0;
		//
		Pattern p1 = null, p2 = null;
		//
		Matcher m1, m2;
		//
		String s1, s, s2;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; es != null && i < es.size(); i++) {
			//
			if ((e = es.get(i)) == null) {
				//
				continue;
				//
			} // if
				//
			if (hasAttrRowspan = hasAttr(IterableUtils.get(children = e.children(), 0), "rowspan")) {
				//
				rowspan = valueOf(NodeUtil.attr(IterableUtils.get(children, 0), "rowspan"));
				//
			} // if
				//
			if (Util.matches(m1 = Util.matcher(
					p1 = ObjectUtils.getIfNull(p1,
							() -> Pattern.compile("^(\\p{InCJKUnifiedIdeographs}+)(\\p{InHiragana}+)?$")),
					ElementUtil.text(IterableUtils.get(children,
							0 + (offset = rowspan == null || hasAttrRowspan || intValue(rowspan, 0) <= 0 ? 1 : 0)))))
					&& Util.groupCount(m1) > 0
					&& Util.matches(m2 = Util.matcher(
							p2 = ObjectUtils.getIfNull(p2, () -> Pattern.compile("^\\p{InHiragana}+?$")),
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
			if (rowspan != null) {
				//
				rowspan = Integer.valueOf(rowspan.intValue() - 1);
				//
			} // if
				//
		} // for
			//
		return multimap;
		//
	}

	private static boolean hasAttr(@Nullable final Node instance, final String attributeKey) {
		return instance != null && instance.hasAttr(attributeKey);
	}

	private static int intValue(@Nullable final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

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