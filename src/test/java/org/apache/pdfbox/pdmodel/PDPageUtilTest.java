package org.apache.pdfbox.pdmodel;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class PDPageUtilTest {

	private PDPage pdPage = null;

	@BeforeEach
	void beforeEach() {
		//
		pdPage = new PDPage();
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = PDPageUtil.class.getDeclaredMethods();
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
		final Method[] ms = PDPageUtil.class.getDeclaredMethods();
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
	void testGetMediaBox() {
		//
		Assertions.assertNotNull(PDPageUtil.getMediaBox(pdPage));
		//
	}

	@Test
	void testGetAnnotations() throws IOException {
		//
		Assertions.assertNotNull(PDPageUtil.getAnnotations(pdPage));
		//
	}

}