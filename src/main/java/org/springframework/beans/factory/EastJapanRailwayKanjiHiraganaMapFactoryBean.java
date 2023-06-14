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
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderUtil;
import com.opencsv.exceptions.CsvValidationException;

public class EastJapanRailwayKanjiHiraganaMapFactoryBean extends StringMapFromResourceFactoryBean {

	private String[] urls = null;

	public void setUrls(final String[] urls) {
		this.urls = urls;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		final IValue0<Map<String, String>> iValue0 = getIvalue0();
		//
		if (iValue0 != null) {
			//
			return IValue0Util.getValue0(iValue0);
			//
		} // if
			//
		UrlValidator urlValidator = null;
		//
		Map<String, String> result = null;
		//
		for (int i = 0; urls != null && i < urls.length; i++) {
			//
			merge(result = ObjectUtils.getIfNull(result, LinkedHashMap::new),
					createMap(urls[i], urlValidator = ObjectUtils.getIfNull(urlValidator, UrlValidator::getInstance)));
			//
		} // for
			//
		return result;
		//
	}

	private static <K, V> void merge(@Nullable final Map<K, V> a, @Nullable final Map<K, V> b) {
		//
		K key = null;
		//
		if (b != null && b.entrySet() != null && b.entrySet().iterator() != null) {
			//
			for (final Entry<K, V> entry : b.entrySet()) {
				//
				if (entry == null) {
					//
					continue;
					//
				} // if
					//
				if (a != null && a.containsKey(key = entry.getKey()) && !Objects.equals(a.get(key), entry.getValue())) {
					//
					throw new IllegalStateException(entry.toString());
					//
				} // if
					//
				Util.put(a, key, entry.getValue());
				//
			} // for
				//
		} // if
			//
	}

	private static boolean containsKey(@Nullable final Map<?, ?> instance, final Object key) {
		return instance != null && instance.containsKey(key);
	}

	@Nullable
	private static Map<String, String> createMap(@Nullable final String url, final UrlValidator urlValidator)
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
	private static Map<String, String> createMap(@Nullable final InputStream is,
			@Nullable final UrlValidator urlValidator) throws IOException, CsvValidationException {
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
			while ((line = CSVReaderUtil.readNext(csvReader)) != null) {
				//
				if (line.length < 6 || urlValidator == null || !urlValidator.isValid(u = line[5])
						|| (pair = createPair(u)) == null
						|| (map = ObjectUtils.getIfNull(map, LinkedHashMap::new)) == null) {
					//
					continue;
					//
				} // if
					//
				if (containsKey(map, key = pair.getValue0())) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				Util.put(map, key, pair.getValue1());
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

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate,
			@Nullable final T value, final FailableFunction<T, R, E> functionTrue,
			@Nullable final FailableFunction<T, R, E> functionFalse) throws E {
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
			pair = new Pair<>(ElementUtil.text(element), null);
			//
		} // if
			//
			// hiragana
			//
		if ((element = IterableUtils.size(elements = ElementUtil.select(document, ".station_name02")) == 1
				? IterableUtils.get(elements, 0)
				: null) != null && (pair = ObjectUtils.getIfNull(pair, () -> new Pair<>(null, null))) != null) {
			//
			pair = pair.setAt1(ElementUtil.text(element));
			//
		} // if
			//
		return pair;
		//
	}

}