package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.NodeUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;
import org.springframework.core.io.XlsxUtil;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

/*
 * https://hiramatu-hifuka.com/onyak/onyindx.html
 */
public class OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBean implements FactoryBean<Multimap<String, String>> {

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("url")
	private String url = null;

	@Note("title")
	private String title = null;

	private Resource resource = null;

	public void setUrl(final String url) {
		this.url = url;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	@Override
	public Multimap<String, String> getObject() throws Exception {
		//
		if (resource != null) {
			//
			return getMultimap(resource, true);
			//
		} // if
			//
		return getMultimap(
				getElement(testAndApply(StringUtils::isNotBlank, url, x -> new URI(x).toURL(), null), title));
		//
	}

	@Nullable
	private static Multimap<String, String> getMultimap(final Resource resource, final boolean header)
			throws Exception {
		//
		final Sheet sheet = getSheet(resource);
		//
		Multimap<String, String> multimap = null;
		//
		Row row = null;
		//
		final AtomicBoolean first = new AtomicBoolean(false);
		//
		for (int i = 0; sheet != null && i < sheet.getPhysicalNumberOfRows(); i++) {
			//
			if ((row = sheet.getRow(i)) == null || row.getPhysicalNumberOfCells() < 2
					|| (header && !first.getAndSet(true))) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.put(multimap = ObjectUtils.getIfNull(multimap, ArrayListMultimap::create),
					getString(CellUtil.getCell(row, 0)), getString(CellUtil.getCell(row, 1)));
			//
		} // for
			//
		return multimap;
		//
	}

	@Nullable
	private static String getString(@Nullable final Cell cell) throws Exception {
		//
		if (cell == null) {
			//
			return null;
			//
		} // if
			//
		final CellType cellType = cell.getCellType();
		//
		if (Objects.equals(cellType, CellType.NUMERIC)) {
			//
			// _cell
			//
			final List<Field> fs = Arrays.stream(Util.getDeclaredFields(Util.getClass(cell)))
					.filter(f -> Objects.equals(Util.getName(Util.getType(f)),
							"org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCell"))
					.toList();
			//
			final int size = IterableUtils.size(fs);
			//
			if (size > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			final Field f = size == 1 ? IterableUtils.get(fs, 0) : null;
			//
			if (f != null) {
				//
				f.setAccessible(true);
				//
			} // if
				//
			Document document = null;
			//
			try (final Reader reader = testAndApply(Objects::nonNull, Util.toString(get(f, cell)), StringReader::new,
					null)) {
				//
				document = testAndApply(Objects::nonNull, reader,
						x -> parse(newDocumentBuilder(DocumentBuilderFactory.newDefaultInstance()), new InputSource(x)),
						null);
				//
			} // try
				//
			return ObjectUtils.defaultIfNull(Util.toString(
					evaluate(newXPath(XPathFactory.newDefaultInstance()), "/*/*", document, XPathConstants.STRING)),
					Double.toString(cell.getNumericCellValue()));
			//
		} else if (Objects.equals(cellType, CellType.STRING) || Objects.equals(cellType, CellType.BLANK)) {
			//
			return cell.getStringCellValue();
			//
		} else if (Objects.equals(cellType, CellType.BOOLEAN)) {
			//
			return Boolean.toString(cell.getBooleanCellValue());
			//
		} // if
			//
		throw new IllegalStateException(Util.toString(cellType));
		//
	}

	@Nullable
	private static Object get(@Nullable final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	@Nullable
	private static DocumentBuilder newDocumentBuilder(@Nullable final DocumentBuilderFactory instance)
			throws ParserConfigurationException {
		return instance != null ? instance.newDocumentBuilder() : null;
	}

	@Nullable
	private static Document parse(@Nullable final DocumentBuilder instance, @Nullable final InputSource is)
			throws SAXException, IOException {
		//
		if (Objects.equals("com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderImpl",
				Util.getName(Util.getClass(instance))) && is == null) {
			//
			return null;
			//
		} // if
			//
		return instance != null ? instance.parse(is) : null;
		//
	}

	@Nullable
	private static XPath newXPath(@Nullable final XPathFactory instance) {
		return instance != null ? instance.newXPath() : null;
	}

	@Nullable
	private static Sheet getSheet(final Resource resource)
			throws IOException, SAXException, ParserConfigurationException {
		//
		final byte[] bs = ResourceUtil.getContentAsByteArray(resource);
		//
		final ContentInfo ci = testAndApply(Objects::nonNull, bs, new ContentInfoUtil()::findMatch, null);
		//
		final String mimeType = Util.getMimeType(ci);
		//
		if (Objects.equals("application/vnd.openxmlformats-officedocument", mimeType)
				|| Objects.equals("OLE 2 Compound Document", Util.getMessage(ci)) || XlsxUtil.isXlsx(resource)) {
			//
			try (final InputStream is = new ByteArrayInputStream(bs);
					final Workbook wb = testAndApply(Objects::nonNull, is, WorkbookFactory::create, null)) {
				//
				final int numberOfSheets = wb.getNumberOfSheets();
				//
				if (numberOfSheets == 0) {
					//
					throw new IllegalArgumentException("There is no sheet in the workbook");
					//
				} // if
					//
				return wb.getSheetAt(0);
				//
			} // try
				//
		} // if
			//
		return null;
		//
	}

	@Nullable
	private static Object evaluate(@Nullable final XPath instance, @Nullable final String expression,
			@Nullable final Object item, @Nullable final QName returnType) throws XPathExpressionException {
		//
		if (Objects.equals("com.sun.org.apache.xpath.internal.jaxp.XPathImpl", Util.getName(Util.getClass(instance)))
				&& (item == null || expression == null || returnType == null)) {
			//
			return null;
			//
		} // if
			//
		return instance != null ? instance.evaluate(expression, item, returnType) : null;
		//
	}

	private static Multimap<String, String> getMultimap(@Nullable final Element element) {
		//
		return Util.collect(
				Util.filter(Util.stream(ElementUtil.select(element, "td[align=\"center\"]")),
						x -> ElementUtil.text(x).matches("\\d+")),
				ArrayListMultimap::create, OtoYakuNoHeyaYomikataJitenLinkMultiMapFactoryBean::putHref,
				Multimap::putAll);
		//
	}

	private static void putHref(final Multimap<String, String> m, final Element v) {
		//
		final String text = ElementUtil.text(v);
		//
		final List<Element> as = ElementUtil.select(ElementUtil.nextElementSibling(v), "a");
		//
		Element a = null;
		//
		String href = null;
		//
		for (int i = 0; as != null && i < as.size(); i++) {
			//
			if ((a = as.get(i)) == null) {
				//
				continue;
				//
			} // if
				//
			MultimapUtil.put(m, ElementUtil.text(a), href = NodeUtil.absUrl(a, "href"));
			//
			MultimapUtil.put(m, text, href);
			//
		} // for
			//
	}

	@Nullable
	private static Element getParentByNodeName(@Nullable final Element element, final String nodeName) {
		//
		return orElse(findFirst(
				Util.filter(Util.stream(parents(element)), x -> Objects.equals(nodeName, NodeUtil.nodeName(x)))), null);
		//
	}

	@Nullable
	private static Element getElement(final URL url, final String title) throws IOException {
		//
		final List<Element> bs = ElementUtil.select(testAndApply(Objects::nonNull, url, x -> Jsoup.parse(x, 0), null),
				"b");
		//
		final List<Element> es = Util.collect(Util.filter(Util.stream(bs),
				x -> StringUtils.equals(StringUtils.defaultIfBlank(title, "音訳の部屋読み方辞典"), trim(ElementUtil.text(x)))),
				Collectors.toList());
		//
		final int size = IterableUtils.size(es);
		//
		if (size > 1) {
			//
			throw new IllegalStateException();
			//
		} // if
			//
		return getParentByNodeName(size == 1 ? IterableUtils.get(es, 0) : null, "table");
		//
	}

	@Nullable
	private static <T> T orElse(@Nullable final Optional<T> instance, @Nullable final T value) {
		return instance != null ? instance.orElse(value) : value;
	}

	@Nullable
	private static <T> Optional<T> findFirst(@Nullable final Stream<T> instance) {
		return instance != null ? instance.findFirst() : null;
	}

	@Nullable
	private static List<Element> parents(@Nullable final Element instance) {
		return instance != null ? instance.parents() : null;
	}

	@Nullable
	private static String trim(@Nullable final String string) {
		//
		if (StringUtils.isEmpty(string)) {
			//
			return string;
			//
		} // if
			//
		final char[] cs = Util.toCharArray(string);
		//
		StringBuilder sb = null;
		//
		char c = ' ';
		//
		for (int i = 0; cs != null && i < cs.length; i++) {
			//
			if (Character.isWhitespace(c = cs[i])) {
				//
				continue;
				//
			} // if
				//
			append(sb = ObjectUtils.getIfNull(sb, StringBuilder::new), c);
			//
		} // for
			//
		return StringUtils.defaultString(Util.toString(sb));
		//
	}

	private static void append(@Nullable final StringBuilder instance, final char c) {
		if (instance != null) {
			instance.append(c);
		}
	}

	private static <T, R, E extends Throwable> R testAndApply(@Nullable final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}