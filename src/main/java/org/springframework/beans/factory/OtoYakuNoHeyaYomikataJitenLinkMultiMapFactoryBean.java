package org.springframework.beans.factory;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

/*
 * https://hiramatu-hifuka.com/onyak/onyindx.html
 */
public class OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBean implements FactoryBean<Multimap<String, String>> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		return getMultimap(getElement(url));
		//
	}

	private static Multimap<String, String> getMultimap(final Element element) {
		//
		return Util.collect(Util.filter(Util.stream(ElementUtil.select(element, "td[align=\"center\"]")),
				x -> ElementUtil.text(x).matches("\\d+")), ArrayListMultimap::create, (m, v) -> {
					//
					final String text = ElementUtil.text(v);
					//
					final List<Element> as = ElementUtil.select(ElementUtil.nextElementSibling(v), "a");
					//
					Element a = null;
					//
					String href = null;
					//
					for (int i = 0; as != null && i < as.size(); i++) {
						//
						if ((a = as.get(i)) == null) {
							//
							continue;
							//
						} // if
							//
						MultimapUtil.put(m, ElementUtil.text(a), href = NodeUtil.absUrl(a, "href"));
						//
						MultimapUtil.put(m, text, href);
						//
					} // for
						//
				}, Multimap::putAll);
		//
	}

	private static Element getParentByNodeName(@Nullable final Element element, final String nodeName) {
		//
		return orElse(findFirst(
				Util.filter(Util.stream(parents(element)), x -> Objects.equals(nodeName, NodeUtil.nodeName(x)))), null);
		//
	}

	private static Element getElement(final String url) throws Exception {
		//
		final List<Element> bs = ElementUtil.select(testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
				null), "b");
		//
		final List<Element> es = Util.collect(
				Util.filter(Util.stream(bs), x -> StringUtils.equals("音訳の部屋読み方辞典", trim(ElementUtil.text(x)))),
				Collectors.toList());
		//
		final int size = IterableUtils.size(es);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return getParentByNodeName(size == 1 ? IterableUtils.get(es, 0) : null, "table");
		//
	}

	private static <T> T orElse(final Optional<T> instance, @Nullable final T value) {
		return instance != null ? instance.orElse(value) : value;
	}

	private static <T> Optional<T> findFirst(final Stream<T> instance) {
		return instance != null ? instance.findFirst() : null;
	}

	private static List<Element> parents(final Element instance) {
		return instance != null ? instance.parents() : null;
	}

	private static String trim(final String string) {
		//
		if (StringUtils.isEmpty(string)) {
			//
			return string;
			//
		} // if
			//
		final char[] cs = string != null ? string.toCharArray() : null;
		//
		StringBuilder sb = null;
		//
		char c = ' ';
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if (Character.isWhitespace(c = cs[i])) {
				//
				continue;
				//
			} // if
				//
			append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
			//
		} // for
			//
		return StringUtils.defaultString(Util.toString(sb));
		//
	}

	private static void append(@Nullable final StringBuilder instance, final char c) {
		if (instance != null) {
			instance.append(c);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}