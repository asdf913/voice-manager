package org.apache.pdfbox.pdmodel;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class PDDocumentUtilTest {

	private static Method METHOD_MAP, METHOD_FILTER, METHOD_GET_CLASS, METHOD_GET_NAME = null;

	@BeforeAll
	static void beforeAll() throws Throwable {
		//
		final Class<?> clz = PDDocumentUtil.class;
		//
		(METHOD_MAP = clz.getDeclaredMethod("map", Stream.class, Function.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String name = getName(method);
			//
			if (proxy instanceof Stream) {
				//
				if (Objects.equals(name, "map") && args != null && args.length > 0) {
					//
					return null;
					//
				} else if (Objects.equals(name, "filter") && args != null && args.length > 0) {
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

		private static String getName(final Member instance) throws Throwable {
			try {
				final Object obj = METHOD_GET_NAME.invoke(null, instance);
				if (obj == null) {
					return null;
				} else if (obj instanceof String) {
					return (String) obj;
				}
				throw new Throwable(Objects.toString(PDDocumentUtilTest.getClass(obj)));
			} catch (final InvocationTargetException e) {
				throw e.getTargetException();
			}
		}

	}

	private Stream<?> stream = null;

	@BeforeEach
	void beforeEach() {
		//
		stream = Reflection.newProxy(Stream.class, new IH());
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = PDDocumentUtil.class.getDeclaredMethods();
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
			invokeStaticMethod = Narcissus.invokeStaticMethod(m,
					toArray(Collections.nCopies(m.getParameterCount(), null)));
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
	void testSave() throws Throwable {
		//
		final PDDocument pdDocument = new PDDocument();
		//
		Assertions.assertDoesNotThrow(() -> PDDocumentUtil.save(pdDocument, null));
		//
		Assertions.assertDoesNotThrow(() -> PDDocumentUtil.save(pdDocument, toFile(Path.of("."))));
		//
		final File file = File.createTempFile(RandomStringUtils.secure().nextAlphanumeric(3), null);
		//
		if (file != null) {
			//
			file.deleteOnExit();
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> PDDocumentUtil.save(pdDocument, file));
		//
		FieldUtils.getAllFieldsList(getClass(pdDocument)).forEach(x -> {
			//
			if (Boolean.logicalAnd((x == null || !Modifier.isStatic(x.getModifiers())),
					Object.class.isAssignableFrom(x.getType()))) {
				//
				Narcissus.setField(pdDocument, x, null);
				//
			} // if
				//
		});
		//
		Assertions.assertDoesNotThrow(() -> PDDocumentUtil.save(pdDocument, file));
		//
		Assertions.assertDoesNotThrow(
				() -> PDDocumentUtil.save(pdDocument, cast(File.class, Narcissus.allocateInstance(File.class))));
		//
	}

	private static File toFile(final Path instance) {
		return instance != null ? instance.toFile() : null;
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Test
	void testMap() throws Throwable {
		//
		Assertions.assertNull(map(stream, null));
		//
		Assertions.assertNull(map(Stream.empty(), null));
		//
	}

	private static <T, R> Stream<R> map(final Stream<T> instance, final Function<? super T, ? extends R> mapper)
			throws Throwable {
		try {
			final Object obj = METHOD_MAP.invoke(null, instance, mapper);
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
				return (Class) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertSame(stream, filter(stream, null));
		//
		Assertions.assertNull(filter(Stream.empty(), null));
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

}