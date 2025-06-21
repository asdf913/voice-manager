package org.apache.pdfbox.rendering;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class PDFRendererUtilTest {

	private static Method METHOD_FILTER, METHOD_GET_CLASS, METHOD_TEST = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = PDFRendererUtil.class;
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Stream && Objects.equals(methodName, "filter")) {
				//
				return proxy;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Test
	void testRenderImage() throws IOException {
		//
		Assertions.assertNull(
				PDFRendererUtil.renderImage(cast(PDFRenderer.class, Narcissus.allocateInstance(PDFRenderer.class)), 0));
		//
		final PDDocument pdDocument = new PDDocument();
		//
		pdDocument.addPage(new PDPage());
		//
		Assertions.assertNotNull(PDFRendererUtil.renderImage(new PDFRenderer(pdDocument), 0));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Test
	void testNull() {
		//
		final Method[] ms = PDFRendererUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Object invokeStaticMethod = null;
		//
		String toString = null;
		//
		Collection<Object> collection = null;
		//
		Class<?>[] parameterTypes = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			if ((collection = ObjectUtils.getIfNull(collection, ArrayList::new)) != null
					&& (parameterTypes = m.getParameterTypes()) != null) {
				//
				collection.clear();
				//
				for (final Class<?> parameterType : parameterTypes) {
					//
					if (Objects.equals(parameterType, Integer.TYPE)) {
						//
						collection.add(Integer.valueOf(0));
						//
					} else {
						//
						collection.add(null);
						//
					} // if
						//
				} // for
					//
			} // if
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(collection));
			//
			toString = Objects.toString(m);
			//
			if (Objects.equals(m.getReturnType(), Boolean.TYPE)) {
				//
				Assertions.assertNotNull(invokeStaticMethod, toString);
				//
			} else {
				//
				Assertions.assertNull(invokeStaticMethod, toString);
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
	void testFilter() throws Throwable {
		//
		Stream<?> stream = Reflection.newProxy(Stream.class, new IH());
		//
		Assertions.assertSame(stream, filter(stream, null));
		//
		Assertions.assertSame(stream = Stream.ofNullable(null), filter(stream, null));
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = METHOD_FILTER.invoke(null, instance, predicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null));
		//
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}