package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.select.Elements;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.zeroturnaround.zip.ZipUtil;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

public class GaKuNenBeTsuKanJiMultimapFactoryBean implements FactoryBean<Multimap<String, String>> {

	private Resource resource = null;

	private String url = null;

	private Duration timeout = null;

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setTimeout(final Object timeout) {
		//
		Unit<Duration> value = null;
		//
		if (timeout == null) {
			//
			value = Unit.with(null);
			//
		} else if (timeout instanceof Duration) {
			//
			value = Unit.with((Duration) timeout);
			//
		} else if (timeout instanceof Number) {
			//
			value = Unit.with(Duration.ofMillis(((Number) timeout).longValue()));
			//
		} // if
			//
		if (value != null) {
			//
			this.timeout = IValue0Util.getValue0(value);
			//
			return;
			//
		} // if
			//
		final String string = toString(timeout);
		//
		final Long l = valueOf(string);
		//
		if (l != null) {
			//
			setTimeout(l);
			//
		} else if (StringUtils.isNotBlank(string)) {
			//
			setTimeout(Duration.parse(string));
			//
		} // if
			//
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static Long valueOf(final String instance) {
		try {
			return StringUtils.isNotBlank(instance) ? Long.valueOf(instance) : null;
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		if (ResourceUtil.exists(resource)) {
			//
			Unit<Multimap<String, String>> mm = null;
			//
			ContentInfo ci = null;
			//
			try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
				//
				ci = testAndApply(Objects::nonNull, testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null),
						new ContentInfoUtil()::findMatch, null);
				//
			} // for
				//
			final String mimeType = ci != null ? ci.getMimeType() : null;
			//
			if (Objects.equals("application/vnd.openxmlformats-officedocument", mimeType)) {
				//
				try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
					//
					mm = createMulitmapUnit(WorkbookFactory.create(is));
					//
				} // try
					//
			} else if (Objects.equals("application/zip", mimeType)) {
				//
				boolean contentTypeXmlFound = false;
				//
				try (final InputStream is = InputStreamSourceUtil.getInputStream(resource);
						final ZipInputStream zis = new ZipInputStream(is)) {
					//
					ZipEntry ze = null;
					//
					while ((ze = zis.getNextEntry()) != null) {
						//
						if (contentTypeXmlFound = Objects.equals("[Content_Types].xml", ze.getName())) {
							//
							break;
							//
						} // if
							//
					} // while
						//
				} // try
					//
				boolean isXlsx = false;
				//
				if (contentTypeXmlFound) {
					//
					try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
						//
						try (final InputStream bais = testAndApply(Objects::nonNull,
								ZipUtil.unpackEntry(is, "[Content_Types].xml"), ByteArrayInputStream::new, null)) {
							final NodeList childNodes = getChildNodes(getDocumentElement(
									parse(newDocumentBuilder(DocumentBuilderFactory.newDefaultInstance()), bais)));
							//
							for (int i = 0; i < getLength(childNodes); i++) {
								//
								if (Objects.equals(
										"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml",
										getTextContent(getNamedItem(getAttributes(item(childNodes, i)), "ContentType")))
										&& (isXlsx = true)) {
									//
									break;
									//
								} // if
									//
							} // for
								//
						} // try
							//
					} // try
						//
				} // if
					//
				if (isXlsx) {
					//
					try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
						//
						mm = createMulitmapUnit(WorkbookFactory.create(is));
						//
					} // try
						//
				} // try
					//
			} // if
				//
			if (mm != null) {
				//
				return IValue0Util.getValue0(mm);
				//
			} // if
				//
		} // if
			//
		final Elements elements = selectXpath(
				testAndApply(x -> StringUtils.equalsAnyIgnoreCase(getProtocol(x), "http", "https"),
						testAndApply(Objects::nonNull, url, URL::new, null),
						x -> Jsoup.parse(x, intValue(toMillis(timeout), 0)), null),
				"//span[@class='mw-headline'][starts-with(.,'第')]");
		//
		Element element = null;
		//
		Pattern pattern = null;
		//
		Matcher matcher = null;
		//
		Multimap<String, String> multimap = null;
		//
		for (int i = 0; i < IterableUtils.size(elements); i++) {
			//
			if ((element = IterableUtils.get(elements, i)) == null || !matches(matcher = matcher(
					pattern = ObjectUtils.getIfNull(pattern, () -> Pattern.compile("(第(\\d+)学年)（\\d+字）")),
					ElementUtil.text(element))) || matcher == null || matcher.groupCount() <= 0) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.putAll(multimap = ObjectUtils.getIfNull(multimap, LinkedListMultimap::create),
					matcher.group(1),
					toList(map(stream(select(nextElementSibling(element.parent()), "a")), a -> ElementUtil.text(a))));
			//
		} // for
			//
		return multimap;
		//
	}

	private static Unit<Multimap<String, String>> createMulitmapUnit(final Workbook wb) {
		//
		Unit<Multimap<String, String>> mm = null;
		//
		final Sheet sheet = wb != null && wb.getNumberOfSheets() > 0 ? wb.getSheetAt(0) : null;
		//
		if (sheet != null && sheet.iterator() != null) {
			//
			int rowNum = 0;
			//
			for (final Row row : sheet) {
				//
				if (row == null || rowNum++ == 0 || row.getLastCellNum() < 2
						|| (mm = ObjectUtils.getIfNull(mm, () -> Unit.with(LinkedHashMultimap.create()))) == null) {
					//
					continue;
					//
				} // if
					//
				put(IValue0Util.getValue0(mm), toString(row.getCell(0)), toString(row.getCell(1)));
				//
			} // for
				//
		} // if
			//
		return mm;
		//
	}

	private static <K, V> void put(final Multimap<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static DocumentBuilder newDocumentBuilder(final DocumentBuilderFactory instance)
			throws ParserConfigurationException {
		return instance != null ? instance.newDocumentBuilder() : null;
	}

	private static Document parse(final DocumentBuilder instance, final InputStream is)
			throws SAXException, IOException {
		return instance != null ? instance.parse(is) : null;
	}

	private static org.w3c.dom.Element getDocumentElement(final Document instance) {
		return instance != null ? instance.getDocumentElement() : null;
	}

	private static NodeList getChildNodes(final Node instance) {
		return instance != null ? instance.getChildNodes() : null;
	}

	private static int getLength(final NodeList instance) {
		return instance != null ? instance.getLength() : 0;
	}

	private static Node item(final NodeList instance, final int index) {
		return instance != null ? instance.item(index) : null;
	}

	private static NamedNodeMap getAttributes(final Node instance) {
		return instance != null ? instance.getAttributes() : null;
	}

	private static Node getNamedItem(final NamedNodeMap instance, final String name) {
		return instance != null ? instance.getNamedItem(name) : null;
	}

	private static String getTextContent(final Node instance) {
		return instance != null ? instance.getTextContent() : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static Elements selectXpath(final org.jsoup.nodes.Element instance, final String xpath) {
		return instance != null ? instance.selectXpath(xpath) : null;
	}

	private static String getProtocol(final URL instance) {
		return instance != null ? instance.getProtocol() : null;
	}

	private static Long toMillis(final Duration instance) {
		return instance != null ? Long.valueOf(instance.toMillis()) : null;
	}

	private static int intValue(final Number instance, final int defaultValue) {
		return instance != null ? instance.intValue() : defaultValue;
	}

	private static Matcher matcher(final Pattern pattern, final CharSequence input) {
		return pattern != null && input != null ? pattern.matcher(input) : null;
	}

	private static boolean matches(final Matcher instance) {
		return instance != null && instance.matches();
	}

	private static org.jsoup.nodes.Element nextElementSibling(final Element instance) {
		return instance != null ? instance.nextElementSibling() : null;
	}

	private static Elements select(final Element instance, final String cssQuery) {
		return instance != null ? instance.select(cssQuery) : null;
	}

	private static <E> Stream<E> stream(final Collection<E> instance) {
		return instance != null ? instance.stream() : null;
	}

	private static <T, R> Stream<R> map(final Stream<T> instance, final Function<? super T, ? extends R> mapper) {
		//
		return instance != null && (Proxy.isProxyClass(getClass(instance)) || mapper != null) ? instance.map(mapper)
				: null;
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static <T> List<T> toList(final Stream<T> instance) {
		return instance != null ? instance.toList() : null;
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}