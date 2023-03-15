package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.poi.ss.usermodel.CellUtil;
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
import org.springframework.core.io.XlsxUtil;
import org.xml.sax.SAXException;

import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderUtil;
import com.opencsv.exceptions.CsvValidationException;

public class EastJapanRailwayKanjiHiraganaMapFactoryBean implements FactoryBean<Map<String, String>> {

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
			final IValue0<Map<String, String>> result = createMap(resource);
			//
			if (result != null) {
				//
				return IValue0Util.getValue0(result);
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

	private static interface ObjectIntMap<K> {

		void put(final K key, final int value);

		int get(final K key);

		boolean containsKey(final K key);

	}

	private static class IH implements InvocationHandler {

		private Map<Object, Object> map = null;

		private Map<Object, Object> getMap() {
			if (map == null) {
				map = new LinkedHashMap<>();
			}
			return map;
		}

		@Override
		@Nullable
		public Object invoke(final Object proxy, @Nullable final Method method, @Nullable final Object[] args)
				throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof ObjectIntMap) {
				//
				if (Objects.equals(methodName, "put") && args != null && args.length > 1) {
					//
					put(getMap(), args[0], args[1]);
					//
					return null;
					//
				} else if (Objects.equals(methodName, "get") && args != null && args.length > 0) {
					//
					final Object key = args[0];
					//
					if (!getMap().containsKey(key)) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
					return getMap().get(key);
					//
				} else if (Objects.equals(methodName, "containsKey") && args != null && args.length > 0) {
					//
					return getMap().containsKey(args[0]);
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Nullable
	private static IValue0<Map<String, String>> createMap(final Resource resource)
			throws IOException, SAXException, ParserConfigurationException {
		//
		final byte[] bs = ResourceUtil.getContentAsByteArray(resource);
		//
		IValue0<Map<String, String>> result = null;
		//
		final ContentInfo ci = new ContentInfoUtil().findMatch(bs);
		//
		final String mimeType = getMimeType(ci);
		//
		if (Objects.equals("application/vnd.openxmlformats-officedocument", mimeType)
				|| Objects.equals("OLE 2 Compound Document", getMessage(ci))
				|| Boolean.logicalAnd(Objects.equals("application/zip", mimeType), XlsxUtil.isXlsx(resource))) {
			//
			try (final InputStream is = new ByteArrayInputStream(bs);
					final Workbook wb = testAndApply(Objects::nonNull, is, WorkbookFactory::create, null)) {
				//
				final Sheet sheet = wb != null && wb.getNumberOfSheets() == 1 ? wb.getSheetAt(0) : null;
				//
				ObjectIntMap<String> objectIntMap = null;
				//
				if (sheet != null) {
					//
					final AtomicBoolean first = new AtomicBoolean(true);
					//
					for (final Row row : sheet) {
						//
						if (row == null) {
							//
							continue;
							//
						} // if
							//
						if (first.getAndSet(false)) {
							//
							if (objectIntMap == null
									&& (objectIntMap = Reflection.newProxy(ObjectIntMap.class, new IH())) != null) {
								//
								for (int i = 0; i < IterableUtils.size(row); i++) {
									//
									objectIntMap.put(CellUtil.getStringCellValue(row.getCell(i)), i);
									//
								} // for
									//
							} // if
								//
							continue;
							//
						} // if
							//
						if (objectIntMap == null || (result = ObjectUtils.getIfNull(result,
								() -> Unit.with(new LinkedHashMap<>()))) == null
						//
						// TODO
						//
								|| !objectIntMap.containsKey("kanji") || !objectIntMap.containsKey("hiragana")) {
							//
							continue;
							//
						} // if
							//
						put(IValue0Util.getValue0(result),
								CellUtil.getStringCellValue(row.getCell(objectIntMap.get("kanji"))),
								CellUtil.getStringCellValue(row.getCell(objectIntMap.get("hiragana"))));
						//
					} // for
						//
				} // if
					//
			} // try
				//
		} // if
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
				put(a, key, entry.getValue());
				//
			} // for
				//
		} // if
			//
	}

	@Nullable
	private static String getMimeType(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	@Nullable
	private static String getMessage(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static <K, V> void put(@Nullable final Map<K, V> instance, @Nullable final K key, @Nullable final V value) {
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