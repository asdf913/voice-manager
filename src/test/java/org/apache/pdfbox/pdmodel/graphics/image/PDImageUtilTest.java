package org.apache.pdfbox.pdmodel.graphics.image;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class PDImageUtilTest {

	private static final int ZERO = 0;

	private static Method METHOD_GET_NAME_CLASS, METHOD_GET_CLASS, METHOD_FILTER, METHOD_TEST = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = PDImageUtil.class;
		//
		(METHOD_GET_NAME_CLASS = clz.getDeclaredMethod("getName", Class.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Integer width, height = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String name = getName(method);
			//
			if (proxy instanceof PDImage) {
				//
				if (Objects.equals(name, "getWidth")) {
					//
					return width;
					//
				} else if (Objects.equals(name, "getHeight")) {
					//
					return height;
					//
				} // if
					//
			} else if (proxy instanceof Stream) {
				//
				if (Objects.equals(name, "filter")) {
					//
					return proxy;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(name);
			//
		}

		private static String getName(final Member instance) {
			return instance != null ? instance.getName() : null;
		}

	}

	private IH ih = null;

	private PDImage pdImage = null;

	@BeforeEach
	void beforeEach() {
		//
		pdImage = Reflection.newProxy(PDImage.class, ih = new IH());
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = PDImageUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		List<Object> list = null;
		//
		Class<?>[] parameterTypes = null;
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
			clear(list = ObjectUtils.getIfNull(list, ArrayList::new));
			//
			for (int j = 0; (parameterTypes = m.getParameterTypes()) != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					list.add(Boolean.TRUE);
					//
				} else {
					//
					list.add(null);
					//
				} // if
					//
			} // for
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(list));
			//
			toString = Objects.toString(m);
			//
			if (contains(Arrays.asList(Boolean.TYPE, Integer.TYPE), m.getReturnType())) {
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

	private static boolean contains(final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	private static void clear(final Collection<?> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testGetWidth() throws Throwable {
		//
		if (ih != null) {
			//
			ih.width = Integer.valueOf(ZERO);
			//
		} // if
			//
		Assertions.assertEquals(ZERO, PDImageUtil.getWidth(pdImage));
		//
		final Class<?>[] classes = new Class<?>[] { PDImageXObject.class, PDInlineImage.class };
		//
		Class<?> clz = null;
		//
		for (int i = ZERO; classes != null && i < classes.length; i++) {
			//
			Assertions.assertEquals(ZERO,
					PDImageUtil.getWidth(
							cast(PDImage.class, Narcissus.allocateInstance(clz = ArrayUtils.get(classes, i)))),
					getName(clz));
			//
		} // for
			//
	}

	@Test
	void testGetHeight() throws Throwable {
		//
		if (ih != null) {
			//
			ih.height = Integer.valueOf(ZERO);
			//
		} // if
			//
		Assertions.assertEquals(ZERO, PDImageUtil.getHeight(pdImage));
		//
		final Class<?>[] classes = new Class<?>[] { PDImageXObject.class, PDInlineImage.class };
		//
		Class<?> clz = null;
		//
		for (int i = ZERO; classes != null && i < classes.length; i++) {
			//
			Assertions.assertEquals(ZERO,
					PDImageUtil.getHeight(
							cast(PDImage.class, Narcissus.allocateInstance(clz = ArrayUtils.get(classes, i)))),
					getName(clz));
			//
		} // for
			//
	}

	private static String getName(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
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
				return (Class) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Test
	void testFilter() throws Throwable {
		//
		Stream<?> stream = Stream.empty();
		//
		Assertions.assertSame(stream, filter(stream, null));
		//
		Assertions.assertSame(stream = Reflection.newProxy(Stream.class, ih), filter(stream, null));
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

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(Predicates.alwaysFalse(), null));
		//
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
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