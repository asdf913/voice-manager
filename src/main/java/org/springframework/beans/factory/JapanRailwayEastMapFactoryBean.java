package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.validator.routines.UrlValidator;
import org.javatuples.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class JapanRailwayEastMapFactoryBean implements FactoryBean<Map<String, String>> {

	private String[] urls = null;

	public void setUrls(final String[] urls) {
		this.urls = urls;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		UrlValidator urlValidator = null;
		//
		Map<String, String> result = null, temp = null;
		//
		String key = null;
		//
		for (int i = 0; urls != null && i < urls.length; i++) {
			//
			if ((result = ObjectUtils.getIfNull(result, LinkedHashMap::new)) == null || (temp = createMap(urls[i],
					urlValidator = ObjectUtils.getIfNull(urlValidator, UrlValidator::getInstance))) == null) {
				//
				continue;
				//
			} // if
				//
			for (final Entry<String, String> entry : temp.entrySet()) {
				//
				if (entry == null) {
					//
					continue;
					//
				} // if
					//
				if (result.containsKey(key = entry.getKey())) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				result.put(key, entry.getValue());
				//
			} // for
				//
		} // for
			//
		return result;
		//
	}

	private static Map<String, String> createMap(final String url, final UrlValidator urlValidator)
			throws IOException, CsvValidationException {
		//
		Map<String, String> map = null;
		//
		try (final InputStream is = openStream(StringUtils.isNotBlank(url) ? new URL(url) : null)) {
			//
			map = createMap(is, urlValidator);
			//
		} // try
			//
		return map;
		//
	}

	@Nullable
	private static Map<String, String> createMap(final InputStream is, final UrlValidator urlValidator)
			throws IOException, CsvValidationException {
		//
		Map<String, String> map = null;
		//
		try (final Reader reader = testAndApply(Objects::nonNull, is, InputStreamReader::new, null);
				final CSVReader csvReader = testAndApply(Objects::nonNull, reader, CSVReader::new, null)) {
			//
			String[] line = null;
			//
			String u, key = null;
			//
			Pair<String, String> pair = null;
			//
			while ((line = csvReader != null ? csvReader.readNext() : null) != null) {
				//
				if (line == null || line.length < 6 || urlValidator == null || !urlValidator.isValid(u = line[5])
						|| (pair = createPair(u)) == null
						|| (map = ObjectUtils.getIfNull(map, LinkedHashMap::new)) == null) {
					//
					continue;
					//
				} // if
					//
				if (map.containsKey(key = pair.getValue0())) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				map.put(key, pair.getValue1());
				//
			} // while
				//
		} // try
			//
		return map;
		//
	}

	@Nullable
	private static InputStream openStream(@Nullable final URL instance) throws IOException {
		return instance != null ? instance.openStream() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Nullable
	private static Pair<String, String> createPair(final String urlString) throws IOException {
		//
		final Element element = testAndApply(Objects::nonNull,
				testAndApply(StringUtils::isNotBlank, urlString, URL::new, null), x -> Jsoup.parse(x, 0), null);
		//
		return createPair(element);
		//
	}

	@Nullable
	private static Pair<String, String> createPair(final Element document) {
		//
		Pair<String, String> pair = null;
		//
		// text
		//
		List<Element> elements = ElementUtil.select(document, ".station_name01");
		//
		Element element = IterableUtils.size(elements) == 1 ? IterableUtils.get(elements, 0) : null;
		//
		if (element != null) {
			//
			pair = new Pair<>(element.text(), null);
			//
		} // if
			//
			// hiragana
			//
		if ((element = IterableUtils.size(elements = ElementUtil.select(document, ".station_name02")) == 1
				? IterableUtils.get(elements, 0)
				: null) != null && (pair = ObjectUtils.getIfNull(pair, () -> new Pair<>(null, null))) != null) {
			//
			pair = pair.setAt1(element.text());
			//
		} // if
			//
		return pair;
		//
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

}