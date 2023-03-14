package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.javatuples.Pair;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderUtil;
import com.opencsv.exceptions.CsvValidationException;

public class JapanRailwayEastMapFactoryBean implements FactoryBean<Map<String, String>> {

	private Resource resource = null;

	private String[] urls = null;

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	public void setUrls(final String[] urls) {
		this.urls = urls;
	}

	@Override
	public Map<String, String> getObject() throws Exception {
		//
		if (ResourceUtil.exists(resource)) {
			//
			final byte[] bs = ResourceUtil.getContentAsByteArray(resource);
			//
			IValue0<Map<String, String>> result = null;
			//
			if (Objects.equals("application/vnd.openxmlformats-officedocument",
					getMimeType(new ContentInfoUtil().findMatch(bs)))) {
				//
				try (final InputStream is = new ByteArrayInputStream(bs);
						final Workbook wb = testAndApply(Objects::nonNull, is, WorkbookFactory::create, null)) {
					//
					final Sheet sheet = wb != null && wb.getNumberOfSheets() == 1 ? wb.getSheetAt(0) : null;
					//
					if (sheet != null) {
						//
						final AtomicBoolean first = new AtomicBoolean(true);
						//
						for (final Row row : sheet) {
							//
							if (row == null || first.getAndSet(false) || row.getPhysicalNumberOfCells() < 2
									|| (result = ObjectUtils.getIfNull(result,
											() -> Unit.with(new LinkedHashMap<>()))) == null) {
								//
								continue;
								//
							} // if
								//
							put(IValue0Util.getValue0(result), getStringCellValue(row.getCell(0)),
									getStringCellValue(row.getCell(1)));
							//
						} // for
							//
					} // if
						//
				} // try
					//
				if (result != null) {
					//
					return IValue0Util.getValue0(result);
					//
				} // if
					//
			} // if
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

	private static <K, V> void merge(final Map<K, V> a, final Map<K, V> b) {
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
				put(a, key, entry.getValue());
				//
			} // for
				//
		} // if
			//
	}

	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	private static String getStringCellValue(@Nullable final Cell instance) {
		return instance != null ? instance.getStringCellValue() : null;
	}

	private static <K, V> void put(@Nullable final Map<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
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
				if (map.containsKey(key = pair.getValue0())) {
					//
					throw new IllegalStateException();
					//
				} // if
					//
				put(map, key, pair.getValue1());
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