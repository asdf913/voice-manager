package org.apache.pdfbox.pdmodel.common.filespecification;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class PDEmbeddedFileUtilTest {

	private PDEmbeddedFile pdEmbeddedFile = null;

	private PDDocument pdDocument = null;

	@BeforeEach
	void beforEach() {
		//
		pdEmbeddedFile = new PDEmbeddedFile(pdDocument = new PDDocument());
		//
	}

	@AfterEach
	void afterEach() {
		//
		IOUtils.closeQuietly(pdDocument);
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = PDEmbeddedFileUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Collection<Object> collection = null;
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
				if (Objects.equals(ArrayUtils.get(parameterTypes, j), Integer.TYPE)) {
					//
					add(collection, Integer.valueOf(0));
					//
				} else {
					//
					add(collection, null);
					//
				} // if
					//
			} // for
				//
			Assertions.assertNull(Narcissus.invokeStaticMethod(m, toArray(collection)), Objects.toString(m));
			//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testNotNull() {
		//
		final Method[] ms = PDEmbeddedFileUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Collection<Object> collection = null;
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
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Integer.TYPE)) {
					//
					add(collection, Integer.valueOf(0));
					//
				} else {
					//
					add(collection, Narcissus.allocateInstance(parameterType));
					//
				} // if
					//
			} // for
				//
			Assertions.assertNull(Narcissus.invokeStaticMethod(m, toArray(collection)), Objects.toString(m));
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

	@Test
	void testSetSubtype() {
		//
		Assertions.assertDoesNotThrow(() -> PDEmbeddedFileUtil.setSubtype(pdEmbeddedFile, null));
		//
	}

	@Test
	void testSetSize() {
		//
		Assertions.assertDoesNotThrow(() -> PDEmbeddedFileUtil.setSize(pdEmbeddedFile, 0));
		//
	}

}