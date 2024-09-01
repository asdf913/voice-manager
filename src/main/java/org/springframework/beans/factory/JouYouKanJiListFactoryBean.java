package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

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
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;

public class JouYouKanJiListFactoryBean implements FactoryBean<List<String>> {

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
		IValue0<List<String>> iValue0 = getObject(resource);
		//
		if (iValue0 != null || (iValue0 = getObjectByUrl(url, timeout)) != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<List<String>> getObject(final Resource resource) throws IOException {
		//
		if (ResourceUtil.exists(resource)) {
			//
			try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
				//
				final Object object = testAndApply(Objects::nonNull, is,
						x -> ObjectMapperUtil.readValue(new ObjectMapper(), x, Object.class), null);
				//
				if (object instanceof Map) {
					//
					throw new IllegalArgumentException();
					//
				} // if
					//
				if (object instanceof Iterable<?> iterable) {
					//
					return Unit.with(Util
							.toList(Util.map(StreamSupport.stream(Util.spliterator(iterable), false), Util::toString)));
					//
				} else if (is != null) {
					//
					return Unit.with(object != null ? Collections.singletonList(Util.toString(object)) : null);
					//
				} // if
					//
			} catch (final JsonProcessingException e) {
				//
			} // try
				//
			try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
				//
				final String string = testAndApply(Objects::nonNull, is,
						x -> IOUtils.toString(x, StandardCharsets.UTF_8), null);
				//
				if (string != null) {
					//
					return Unit.with(Util.toList(Util.chars(string).mapToObj(c -> String.valueOf((char) c))));
					//
				} // if
					//
			} // try
				//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static IValue0<List<String>> getObjectByUrl(final String url, final Duration timeout) throws Exception {
		//
		final Document document = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null),
				x -> Jsoup.parse(x, Util.intValue(toMillis(timeout), 0)), null);
		//
		List<Element> trs = testAndApply(x -> IterableUtils.size(x) == 1,
				ElementUtil.selectXpath(document, "//h3/span[text()=\"本表\"]/../following-sibling::table[1]/tbody"),
				x -> ElementUtil.children(IterableUtils.get(x, 0)), null);
		//
		Integer columnIndex = null;
		//
		List<Element> elements = null;
		//
		Element element = null;
		//
		IValue0<List<String>> list = null;
		//
		final int size = IterableUtils.size(trs);
		//
		for (int i = 0; i < size; i++) {
			//
			elements = ElementUtil.children(trs.get(i));
			//
			if (columnIndex == null) {
				//
				columnIndex = getColumnIndex(elements);
				//
			} else {
				//
				for (int j = 0; j < IterableUtils.size(elements); j++) {
					//
					if (Boolean.logicalAnd(columnIndex.intValue() - 1 == j,
							Objects.equals("0", ElementUtil.text(element = IterableUtils.get(elements, j))))) {
						//
						break;
						//
					} // if
						//
					if (columnIndex.intValue() == j) {
						//
						Util.add(
								IValue0Util.getValue0(
										list = ObjectUtils.getIfNull(list, () -> Unit.with(new ArrayList<String>()))),
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

	@Nullable
	private static Integer getColumnIndex(final List<Element> elements) {
		//
		Element element = null;
		//
		for (int j = 0; j < IterableUtils.size(elements); j++) {
			//
			if (Boolean.logicalAnd(Objects.equals("th", ElementUtil.tagName(element = IterableUtils.get(elements, j))),
					Objects.equals("通用字体", ElementUtil.text(element)))) {
				//
				return Integer.valueOf(j);
				//
			} // if
				//
		} // for
			//
		return null;
		//
	}

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return Util.test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static Long toMillis(@Nullable final Duration instance) {
		return instance != null ? Long.valueOf(instance.toMillis()) : null;
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

}