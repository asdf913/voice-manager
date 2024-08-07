package org.springframework.core.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.oxbow.swingbits.dialog.task.TaskDialogsUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.zeroturnaround.zip.ZipUtil;

public interface XlsxUtil {

	static boolean isXlsx(final InputStreamSource iss) throws IOException, SAXException, ParserConfigurationException {
		//
		boolean contentTypeXmlFound = false;
		//
		try (final InputStream is = InputStreamSourceUtil.getInputStream(iss);
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
			try (final InputStream is = InputStreamSourceUtil.getInputStream(iss)) {
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

	@Nullable
	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(@Nullable final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
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
		return obj instanceof ZipEntry ze ? ze : null;
		//
	}

	private static boolean isStatic(@Nullable final Member instance) {
		return instance != null && Modifier.isStatic(instance.getModifiers());
	}

	@Nullable
	private static Object invoke(@Nullable final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Nullable
	private static DocumentBuilder newDocumentBuilder(@Nullable final DocumentBuilderFactory instance)
			throws ParserConfigurationException {
		return instance != null ? instance.newDocumentBuilder() : null;
	}

	@Nullable
	private static Document parse(@Nullable final DocumentBuilder instance, final InputStream is)
			throws SAXException, IOException {
		return instance != null ? instance.parse(is) : null;
	}

	@Nullable
	private static Element getDocumentElement(@Nullable final Document instance) {
		return instance != null ? instance.getDocumentElement() : null;
	}

	@Nullable
	private static NodeList getChildNodes(@Nullable final Node instance) {
		return instance != null ? instance.getChildNodes() : null;
	}

	private static int getLength(@Nullable final NodeList instance) {
		return instance != null ? instance.getLength() : 0;
	}

	@Nullable
	private static Node item(@Nullable final NodeList instance, final int index) {
		return instance != null ? instance.item(index) : null;
	}

	@Nullable
	private static NamedNodeMap getAttributes(@Nullable final Node instance) {
		return instance != null ? instance.getAttributes() : null;
	}

	@Nullable
	private static Node getNamedItem(@Nullable final NamedNodeMap instance, final String name) {
		return instance != null ? instance.getNamedItem(name) : null;
	}

	@Nullable
	private static String getTextContent(@Nullable final Node instance) {
		return instance != null ? instance.getTextContent() : null;
	}

}