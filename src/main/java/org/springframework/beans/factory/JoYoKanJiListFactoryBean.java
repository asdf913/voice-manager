package org.springframework.beans.factory;

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
		if (ResourceUtil.exists(resource)) {
			//
			try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
				//
				final String string = testAndApply(Objects::nonNull, is, x -> IOUtils.toString(x, "utf-8"), null);
				//
				if (string != null) {
					//
					return string.chars().mapToObj(c -> String.valueOf((char) c)).toList();
					//
				} // if
					//
			} // if
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
		Element tr = null;
		//
		Integer columnIndex = null;
		//
		List<Element> elements = null;
		//
		Element element = null;
		//
		List<String> list = null;
		//
		List<Element> tds = null;
		//
		Element td = null;
		//
		final int size = IterableUtils.size(trs);
		//
		for (int i = 0; i < size; i++) {
			//
			if ((tr = trs.get(i)) == null) {
				//
				continue;
				//
			} // if
				//
			elements = children(tr);
			//
			if (columnIndex == null && elements != null) {
				//
				for (int j = 0; j < elements.size(); j++) {
					//
					if ((element = elements.get(j)) == null || !Objects.equals("th", element.tagName())) {
						//
						continue;
						//
					} // if
						//
					if (Objects.equals("通用字体", ElementUtil.text(element))) {
						//
						columnIndex = Integer.valueOf(j);
						//
						break;
						//
					} // if
						//
				} // for
					//
			} else if (columnIndex != null && (tds = children(tr)) != null) {
				//
				for (int j = 0; j < tds.size(); j++) {
					//
					if ((td = tds.get(j)) == null
							|| (columnIndex.intValue() - 1 == j && Objects.equals("0", ElementUtil.text(td)))) {
						//
						break;
						//
					} // if
						//
					if (columnIndex.intValue() == j) {
						//
						add(list = ObjectUtils.getIfNull(list, ArrayList::new),
								StringUtils.substring(ElementUtil.text(td), 0, 1));
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