package org.springframework.context.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerImageToPdfPanelTest {

	private static Method METHOD_GET_WIDTH = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		(METHOD_GET_WIDTH = VoiceManagerImageToPdfPanel.class.getDeclaredMethod("getWidth", PDImage.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Integer width = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof PDImage) {
				//
				if (Objects.equals(methodName, "getWidth")) {
					//
					return width;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private VoiceManagerImageToPdfPanel instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerImageToPdfPanel();
		//
	}

	@Test
	void testActionPerformed() {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(null));
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerImageToPdfPanel.class.getDeclaredMethods();
		//
		Collection<Object> collection = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Method m = null;
		//
		Object invokeStaticMethod = null;
		//
		String toString = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			for (int j = 0; (parameterTypes = Util.getParameterTypes(m)) != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.TRUE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Float.TYPE)) {
					//
					Util.add(collection, Float.valueOf(0));
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // for
				//
			if (Util.isStatic(m)) {
				//
				invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(collection));
				//
				toString = Objects.toString(m);
				//
				if (Util.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE, Float.TYPE), Util.getReturnType(m))
						|| Boolean.logicalAnd(Objects.equals(Util.getName(m),
								"getPDImageXObjectCreateFromFileByContentDetectFileTypeMethodAndAllowedFileTypes"),
								Arrays.equals(Util.getParameterTypes(m), new Class<?>[] {}))) {
					//
					Assertions.assertNotNull(invokeStaticMethod, toString);
					//
				} else {
					//
					Assertions.assertNull(invokeStaticMethod, toString);
					//
				} // if
					//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeMethod(
						instance = ObjectUtils.getIfNull(instance, VoiceManagerImageToPdfPanel::new), m,
						toArray(collection)), toString);
				//
			} // if
				//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testGetWidth() throws Throwable {
		//
		final IH ih = new IH();
		//
		final int zero = 0;
		//
		ih.width = zero;
		//
		Assertions.assertEquals(zero, getWidth(Reflection.newProxy(PDImage.class, ih)));
		//
	}

	private static int getWidth(final PDImage instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_WIDTH.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.getName(Util.getClass(instance)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}