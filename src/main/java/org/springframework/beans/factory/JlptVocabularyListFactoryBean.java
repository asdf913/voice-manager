package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.springframework.core.io.InputStreamSourceUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.zeroturnaround.zip.ZipUtil;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import domain.JlptVocabulary;

public class JlptVocabularyListFactoryBean implements FactoryBean<List<JlptVocabulary>> {

	private List<String> urls = null;

	private Resource resource = null;

	public void setUrls(final List<String> urls) {
		this.urls = urls;
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	@Override
	public List<JlptVocabulary> getObject() throws Exception {
		//
		if (ResourceUtil.exists(resource)) {
			//
			ContentInfo ci = null;
			//
			try (final InputStream is = InputStreamSourceUtil.getInputStream(resource)) {
				//
				ci = testAndApply(Objects::nonNull, testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null),
						new ContentInfoUtil()::findMatch, null);
				//
			} // try
				//
			final String mimeType = getMimeType(ci);
			//
			final String message = getMessage(ci);
			//
			if (or(Objects.equals("application/vnd.openxmlformats-officedocument", mimeType),
					Boolean.logicalAnd(Objects.equals("application/zip", mimeType), isXlsx(resource)),
					Objects.equals("OLE 2 Compound Document", message))) {
				//
				try (final InputStream is = InputStreamSourceUtil.getInputStream(resource);
						final Workbook wb = is != null ? WorkbookFactory.create(is) : null) {
					//
					final Sheet sheet = wb != null && wb.getNumberOfSheets() == 1 ? wb.getSheetAt(0) : null;
					//
					IValue0<List<JlptVocabulary>> list = null;
					//
					if (sheet != null && sheet.iterator() != null) {
						//
						final AtomicBoolean first = new AtomicBoolean(true);
						//
						int size = 0;
						//
						List<Field> fs = null;
						//
						Map<Integer, Field> fieldMap = null;
						//
						JlptVocabulary jv = null;
						//
						Field f = null;
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
								for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
									//
									if ((size = IterableUtils
											.size(fs = getFieldsByName(JlptVocabulary.class.getDeclaredFields(),
													getStringCellValue(row.getCell(i))))) == 1) {
										//
										put(fieldMap = ObjectUtils.getIfNull(fieldMap, LinkedHashMap::new),
												Integer.valueOf(i), IterableUtils.get(fs, 0));
										//
									} else if (size > 1) {
										//
										throw new IllegalStateException();
										//
									} // if
										//
								} // for
									//
								continue;
								//
							} // if
								//
							jv = null;
							//
							for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
								//
								if ((f = MapUtils.getObject(fieldMap, Integer.valueOf(i))) == null) {
									//
									continue;
									//
								} // if
									//
								f.setAccessible(true);
								//
								f.set(jv = ObjectUtils.getIfNull(jv, JlptVocabulary::new),
										getStringCellValue(row.getCell(i)));
								//
							} // for
								//
							add(IValue0Util.getValue0(
									list = ObjectUtils.getIfNull(list, () -> Unit.with(new ArrayList<>()))), jv);
							//
						} // for
							//
						if (list != null) {
							//
							return IValue0Util.getValue0(list);
							//
						} // if
							//
					} // if
						//
				} // try
					//
			} // if
				//
		} // if
			//
		return getObjectByUrls(urls);
		//
	}

	private static List<JlptVocabulary> getObjectByUrls(final List<String> urls)
			throws CsvValidationException, IllegalAccessException, IOException {
		//
		List<JlptVocabulary> list = null;
		//
		if (urls != null && urls.iterator() != null) {
			//
			List<JlptVocabulary> temp = null;
			//
			for (final String urlString : urls) {
				//
				if ((temp = getJlptVocabularies(urlString)) == null) {
					//
					continue;
					//
				} // if
					//
				addAll(list = ObjectUtils.getIfNull(list, ArrayList::new), temp);
				//
			} // for
				//
		} // if
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

	private static String getMimeType(final ContentInfo instance) {
		return instance != null ? instance.getMimeType() : null;
	}

	private static String getMessage(final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
	}

	private static boolean isXlsx(final Resource resource)
			throws IOException, SAXException, ParserConfigurationException {
		//
		boolean contentTypeXmlFound = false;
		//
		try (final InputStream is = InputStreamSourceUtil.getInputStream(resource);
				final ZipInputStream zis = testAndApply(Objects::nonNull, is, ZipInputStream::new, null)) {
			//
			ZipEntry ze = null;
			//
			while ((ze = getNextEntry(zis)) != null) {
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
				try (final InputStream bais = testAndApply(x -> x != null && x.length > 0,
						ZipUtil.unpackEntry(is, "[Content_Types].xml"), ByteArrayInputStream::new, null)) {
					//
					final NodeList childNodes = getChildNodes(getDocumentElement(
							bais != null ? parse(newDocumentBuilder(DocumentBuilderFactory.newDefaultInstance()), bais)
									: null));
					//
					for (int i = 0; i < getLength(childNodes); i++) {
						//
						if (Objects.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml",
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
		return isXlsx;
		//
	}

	private static DocumentBuilder newDocumentBuilder(final DocumentBuilderFactory instance)
			throws ParserConfigurationException {
		return instance != null ? instance.newDocumentBuilder() : null;
	}

	private static Document parse(final DocumentBuilder instance, final InputStream is)
			throws SAXException, IOException {
		return instance != null ? instance.parse(is) : null;
	}

	private static Element getDocumentElement(final Document instance) {
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

	private static ZipEntry getNextEntry(final ZipInputStream instance) {
		//
		Object obj = null;
		//
		try {
			//
			final Method method = ZipInputStream.class.getDeclaredMethod("getNextEntry");
			//
			obj = Boolean.logicalOr(isStatic(method), instance != null) ? invoke(method, instance) : null;
			//
		} catch (final NoSuchMethodException | IllegalAccessException e) {
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(e);
			//
		} catch (final InvocationTargetException e) {
			//
			final Throwable targetException = e.getTargetException();
			//
			TaskDialogsUtil.errorOrPrintStackTraceOrAssertOrShowException(ObjectUtils.firstNonNull(
					ExceptionUtils.getRootCause(targetException), targetException, ExceptionUtils.getRootCause(e), e));
			//
		} // try
			//
		return obj instanceof ZipEntry ? (ZipEntry) obj : null;
		//
	}

	private static boolean isStatic(final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	private static boolean or(final boolean a, final boolean b, final boolean... bs) {
		//
		boolean result = a || b;
		//
		if (result) {
			//
			return result;
			//
		} // if
			//
		for (int i = 0; bs != null && i < bs.length; i++) {
			//
			if (result |= bs[i]) {
				//
				return result;
				//
			} // if
				//
		} // for
			//
		return result;
		//
	}

	private static String getStringCellValue(final Cell instance) {
		return instance != null ? instance.getStringCellValue() : null;
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static <E> void addAll(final Collection<E> a, final Collection<? extends E> b) {
		if (a != null && b != null) {
			a.addAll(b);
		}
	}

	private static List<JlptVocabulary> getJlptVocabularies(final String urlString)
			throws IOException, CsvValidationException, IllegalAccessException {
		//
		final URL url = StringUtils.isNotBlank(urlString) ? new URL(urlString) : null;
		//
		List<JlptVocabulary> list = null;
		//
		try (final InputStream is = url != null ? url.openStream() : null;
				final Reader r = is != null ? new InputStreamReader(is) : null;
				final CSVReader csvReader = r != null ? new CSVReader(r) : null) {
			//
			final String level = StringUtils
					.substringAfterLast(StringUtils.substringBefore(url != null ? url.getFile() : null, '.'), '/');
			//
			String[] ss = null;
			//
			List<Field> fs = null;
			//
			int size = 0;
			//
			Map<Integer, Field> fieldMap = null;
			//
			JlptVocabulary jv = null;
			//
			Field f = null;
			//
			final AtomicBoolean first = new AtomicBoolean(true);
			//
			while ((ss = csvReader != null ? csvReader.readNext() : null) != null) {
				//
				if (first.getAndSet(false)) {
					//
					for (int i = 0; i < ss.length; i++) {
						//
						if ((size = IterableUtils
								.size(fs = getFieldsByName(JlptVocabulary.class.getDeclaredFields(), ss[i]))) == 1) {
							//
							put(fieldMap = ObjectUtils.getIfNull(fieldMap, LinkedHashMap::new), Integer.valueOf(i),
									IterableUtils.get(fs, 0));
							//
						} else if (size > 1) {
							//
							throw new IllegalStateException();
							//
						} // if
							//
					} // for
						//
					continue;
					//
				} // if
					//
				(jv = new JlptVocabulary()).setLevel(level);
				//
				for (int i = 0; i < ss.length; i++) {
					//
					if ((f = MapUtils.getObject(fieldMap, Integer.valueOf(i))) == null) {
						//
						continue;
						//
					} // if
						//
					f.setAccessible(true);
					//
					f.set(jv, ss[i]);
					//
				} // for
					//
				add(list = ObjectUtils.getIfNull(list, ArrayList::new), jv);
				//
			} // while
				//
		} // try
			//
		return list;
		//
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) {
		if (instance != null) {
			instance.put(key, value);
		}
	}

	private static List<Field> getFieldsByName(final Field[] fs, final String name) {
		//
		List<Field> list = null;
		//
		Field f = null;
		//
		for (int i = 0; fs != null && i < fs.length; i++) {
			//
			if ((f = fs[i]) == null || !Objects.equals(f.getName(), name)) {
				//
				continue;
				//
			} // if
				//
			add(list = ObjectUtils.getIfNull(list, ArrayList::new), f);
			//
		} // for
			//
		return list;
		//
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

}