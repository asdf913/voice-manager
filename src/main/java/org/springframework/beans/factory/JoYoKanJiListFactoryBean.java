package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
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
import org.jsoup.select.Elements;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;

public class JoYoKanJiListFactoryBean implements FactoryBean<List<String>> {

	private String url = null;

	private Duration timeout = null;

	private Resource resource = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	@Override
	public List<String> getObject() throws Exception {
		//
		final IValue0<List<String>> iValue0 = getObject(resource);
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, URL::new, null),
				x -> Jsoup.parse(x, intValue(toMillis(timeout), 0)), null);
		//
		List<Element> trs = testAndApply(x -> IterableUtils.size(x) == 1,
				ElementUtil.selectXpath(document, "//h3/span[text()=\"本表\"]/../following-sibling::table[1]/tbody"),
				x -> children(IterableUtils.get(x, 0)), null);
		//
		Integer columnIndex = null;
		//
		List<Element> elements = null;
		//
		Element element = null;
		//
		List<String> list = null;
		//
		final int size = IterableUtils.size(trs);
		//
		for (int i = 0; i < size; i++) {
			//
			elements = children(trs.get(i));
			//
			if (columnIndex == null) {
				//
				for (int j = 0; j < IterableUtils.size(elements); j++) {
					//
					if (Boolean.logicalAnd(Objects.equals("th", tagName(element = IterableUtils.get(elements, j))),
							Objects.equals("通用字体", ElementUtil.text(element)))) {
						//
						columnIndex = Integer.valueOf(j);
						//
						break;
						//
					} // if
						//
				} // for
					//
			} else {
				//
				for (int j = 0; j < IterableUtils.size(elements); j++) {
					//
					if ((element = IterableUtils.get(elements, j)) == null || Boolean.logicalAnd(
							columnIndex.intValue() - 1 == j, Objects.equals("0", ElementUtil.text(element)))) {
						//
						break;
						//
					} // if
						//
					if (columnIndex.intValue() == j) {
						//
						add(list = ObjectUtils.getIfNull(list, ArrayList::new),
								StringUtils.substring(ElementUtil.text(element), 0, 1));
						//
					} // if
						//
				} // for
					//
			} // if
				//
		} // for
			//
		return list;
		//
	}

	private static IValue0<List<String>> getObject(final Resource resource) throws IOException {
		//
		if (ResourceUtil.exists(resource)) {
			//
			try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
				//
				final String string = testAndApply(Objects::nonNull, is, x -> IOUtils.toString(x, "utf-8"), null);
				//
				if (string != null) {
					//
					return Unit.with(string.chars().mapToObj(c -> String.valueOf((char) c)).toList());
					//
				} // if
					//
			} // if
				//
		} // if
			//
		return null;
		//
	}

	private static String tagName(final Element instance) {
		return instance != null ? instance.tagName() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static Long toMillis(final Duration instance) {
		return instance != null ? Long.valueOf(instance.toMillis()) : null;
	}

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static Elements children(final org.jsoup.nodes.Element instance) {
		return instance != null ? instance.children() : null;
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

}