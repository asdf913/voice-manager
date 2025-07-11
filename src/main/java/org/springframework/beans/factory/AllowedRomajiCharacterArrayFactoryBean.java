package org.springframework.beans.factory;

import java.net.URI;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;

public class AllowedRomajiCharacterArrayFactoryBean implements FactoryBean<char[]> {

	private String url = null;

	private IValue0<char[]> allowedRomajiCharacters = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setAllowedRomajiCharacters(final char[] allowedRomajiCharacters) {
		this.allowedRomajiCharacters = Unit.with(allowedRomajiCharacters);
	}

	@Override
	public char[] getObject() throws Exception {
		//
		if (allowedRomajiCharacters != null) {
			//
			return IValue0Util.getValue0(allowedRomajiCharacters);
			//
		} // if
			//
		Element element = testAndApply(CollectionUtils::isNotEmpty,
				ElementUtil.select(testAndApply(Objects::nonNull,
						testAndApply(Objects::nonNull, url, x -> new URI(x).toURL(), null), x -> Jsoup.parse(x, 0),
						null), ".mw-selflink.selflink"),
				x -> IterableUtils.get(x, 0), null);
		//
		while (element != null && (element.nextElementSibling()) == null) {
			//
			element = ElementUtil.parent(element);
			//
		} // while
			//
		element = ObjectUtils.getIfNull(ElementUtil.nextElementSibling(element), element);
		//
		StringBuilder sb = null;
		//
		do {
			//
			if (ElementUtil.children(element) != null) {
				//
				for (final Element c : ElementUtil.children(element)) {
					//
					if ((sb = ObjectUtils.getIfNull(sb, StringBuilder::new)) == null) {
						//
						continue;
						//
					} // if
						//
					sb.append(ElementUtil.text(c));
					//
				} // for
					//
			} // if
				//
			element = ElementUtil.nextElementSibling(element);
			//
		} while (element != null && ElementUtil.nextElementSibling(element) == null);
		//
		return Util.toCharArray(Util.toString(sb));
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
		return char[].class;
	}

}