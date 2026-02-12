package org.apache.pdfbox.pdmodel.font;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.JapanDictGui;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

import io.github.toolfactory.narcissus.Narcissus;

class PDFontDescriptorUtilTest {

	private PDFontDescriptor pdFontDescriptor = null;

	@BeforeEach
	void beforeEach() throws IOException {
		//
		PDFont pdFont = null;
		//
		try (final PDDocument document = new PDDocument();
				final InputStream is = getResourceAsStream(JapanDictGui.class, "/NotoSansCJKjp-Regular.otf")) {
			//
			final byte[] bs = testAndApply(Objects::nonNull, is, IOUtils::toByteArray, null);
			//
			final ContentInfo ci = testAndApply(Objects::nonNull, bs, new ContentInfoUtil()::findMatch, null);
			//
			try (final ByteArrayInputStream bais = testAndApply(Objects::nonNull, bs, ByteArrayInputStream::new,
					null)) {
				//
				pdFont = testAndApply(Objects::nonNull, testAndApply(
						x -> x != null && ci != null && StringsUtil.equals(Strings.CI, ci.getName(), "OpenType"), bais,
						new OTFParser()::parseEmbedded, null), x -> PDType0Font.load(document, x, false), null);
				//
			} // try
				//
		} // try
			//
		pdFontDescriptor = PDFontUtil.getFontDescriptor(pdFont);
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = PDFontDescriptorUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Collection<Object> collection = null;
		//
		String toString = null;
		//
		Object result = null;
		//
		Class<?>[] parameterTypes = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(ArrayUtils.get(parameterTypes, j), Float.TYPE)) {
					//
					add(collection, Float.valueOf(0));
					//
				} else {
					//
					add(collection, null);
					//
				} // if
					//
			} // for
				//
			toString = Objects.toString(m);
			//
			result = Narcissus.invokeStaticMethod(m, toArray(collection));
			//
			if (isPrimitive(m.getReturnType())) {
				//
				Assertions.assertNotNull(result, toString);
				//
			} else {
				//
				Assertions.assertNull(result, toString);
				//
			} // if
				//
		} // for
			//
	}

	@Test
	void testNotNull() {
		//
		final Method[] ms = PDFontDescriptorUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Collection<Object> collection = null;
		//
		String toString = null;
		//
		Object result = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Float.TYPE)) {
					//
					add(collection, Float.valueOf(0));
					//
				} else {
					//
					add(collection, Narcissus.allocateInstance(parameterType));
					//
				} // if
					//
			} // for
				//
			toString = Objects.toString(m);
			//
			result = Narcissus.invokeStaticMethod(m, toArray(collection));
			//
			if (isPrimitive(m.getReturnType())) {
				//
				Assertions.assertNotNull(result, toString);
				//
			} else {
				//
				Assertions.assertNull(result, toString);
				//
			} // if
				//
		} // for
			//
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static void clear(final Collection<?> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	private static boolean isPrimitive(final Class<?> instance) {
		return instance != null && instance.isPrimitive();
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testGetAscent() {
		//
		Assertions.assertEquals(Float.valueOf(1160), PDFontDescriptorUtil.getAscent(pdFontDescriptor, 0));
		//
	}

	@Test
	void testGetDescent() {
		//
		Assertions.assertEquals(Float.valueOf(-288), PDFontDescriptorUtil.getDescent(pdFontDescriptor, 0));
		//
	}

	private static <T, R, E extends Exception> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static InputStream getResourceAsStream(final Class<?> instance, final String name) {
		return instance != null && name != null ? instance.getResourceAsStream(name) : null;
	}

}