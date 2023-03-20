package org.springframework.beans.factory;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;

public class AllowedRomajiCharacterArrayFactoryBean implements FactoryBean<char[]> {

	private String url = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public char[] getObject() throws Exception {
		//
		final Document document = testAndApply(Objects::nonNull, testAndApply(Objects::nonNull, url, URL::new, null),
				x -> Jsoup.parse(x, 0), null);
		//
		final List<Element> elements = ElementUtil.select(document, ".mw-selflink.selflink");
		//
		Element element = CollectionUtils.isNotEmpty(elements) ? IterableUtils.get(elements, 0) : null;
		//
		while (element != null && (element.nextElementSibling()) == null) {
			//
			element = element.parent();
			//
		} // while
			//
		element = ObjectUtils.defaultIfNull(ElementUtil.nextElementSibling(element), element);
		//
		StringBuilder sb = null;
		//
		do {
			//
			if (element != null && element.children() != null) {
				//
				for (final Element c : element.children()) {
					//
					if (c == null || (sb = ObjectUtils.getIfNull(sb, StringBuilder::new)) == null) {
						//
						continue;
						//
					} // if
						//
					sb.append(c.text());
					//
				} // for
					//
			} // if
				//
			element = ElementUtil.nextElementSibling(element);
			//
		} while (ElementUtil.nextElementSibling(element) == null);
		//
		final String string = sb != null ? sb.toString() : null;
		//
		return string != null ? string.toCharArray() : null;
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	@Override
	public Class<?> getObjectType() {
		return char[].class;
	}

}