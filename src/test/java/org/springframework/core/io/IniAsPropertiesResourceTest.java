package org.springframework.core.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.jena.ext.com.google.common.base.Predicates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLogger;

import com.google.common.reflect.Reflection;

class IniAsPropertiesResourceTest {

	private static Method METHOD_GET_SECTION, METHOD_ERROR_OR_ASSERT_OR_SHOW_EXCEPTION, METHOD_GET_MESSAGE,
			METHOD_PRINT_STACK_TRACE, METHOD_GET_DECLARED_METHODS, METHOD_TEST_AND_APPLY, METHOD_FILTER,
			METHOD_FOR_NAME, METHOD_TO_LIST, METHOD_GET_NAME, METHOD_GET_PARAMETER_TYPES, METHOD_INVOKE,
			METHOD_TEST_AND_ACCEPT, METHOD_IS_STATIC, METHOD_TO_RUNTIME_EXCEPTION, METHOD_TO_INPUT_STREAM, METHOD_CAST,
			METHOD_GET_TYPE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = IniAsPropertiesResource.class;
		//
		(METHOD_GET_SECTION = clz.getDeclaredMethod("getSection", Boolean.TYPE, Map.class, Collection.class))
				.setAccessible(true);
		//
		(METHOD_ERROR_OR_ASSERT_OR_SHOW_EXCEPTION = clz.getDeclaredMethod("errorOrAssertOrShowException", Boolean.TYPE,
				Logger.class, Throwable.class)).setAccessible(true);
		//
		(METHOD_GET_MESSAGE = clz.getDeclaredMethod("getMessage", Throwable.class)).setAccessible(true);
		//
		(METHOD_PRINT_STACK_TRACE = clz.getDeclaredMethod("printStackTrace", Throwable.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_METHODS = clz.getDeclaredMethod("getDeclaredMethods", Class.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_FOR_NAME = clz.getDeclaredMethod("forName", String.class)).setAccessible(true);
		//
		(METHOD_TO_LIST = clz.getDeclaredMethod("toList", Stream.class)).setAccessible(true);
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
		(METHOD_GET_PARAMETER_TYPES = clz.getDeclaredMethod("getParameterTypes", Executable.class)).setAccessible(true);
		//
		(METHOD_INVOKE = clz.getDeclaredMethod("invoke", Method.class, Object.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				FailableConsumer.class)).setAccessible(true);
		//
		(METHOD_IS_STATIC = clz.getDeclaredMethod("isStatic", Member.class)).setAccessible(true);
		//
		(METHOD_TO_RUNTIME_EXCEPTION = clz.getDeclaredMethod("toRuntimeException", Throwable.class))
				.setAccessible(true);
		//
		(METHOD_TO_INPUT_STREAM = clz.getDeclaredMethod("toInputStream", Properties.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_TYPE = clz.getDeclaredMethod("getType", Field.class)).setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		private InputStream inputStream = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Resource) {
				//
				if (Objects.equals(methodName, "getInputStream")) {
					//
					return inputStream;
					//
				} else if (Objects.equals(methodName, "getFilename")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private IH ih = null;

	private Resource resource = null;

	@BeforeEach
	void beforeEach() {
		//
		resource = Reflection.newProxy(Resource.class, ih = new IH());
		//
	}

	@Test
	void testGetInputStream() throws IOException {
		//
		Assertions.assertNotNull(new IniAsPropertiesResource(null).getInputStream());
		//
		final IniAsPropertiesResource instance = new IniAsPropertiesResource(resource);
		//
		Assertions.assertNotNull(instance.getInputStream());
		//
		try (final InputStream is = new ByteArrayInputStream("".getBytes())) {
			//
			if (ih != null) {
				//
				ih.inputStream = is;
				//
			} // if
				//
			Assertions.assertNotNull(instance.getInputStream());
			//
		} // try
			//
		Assertions.assertNotNull(
				new IniAsPropertiesResource(new UrlResource(getClass().getResource("/applicationContext.xml")))
						.getInputStream());
		//
	}

	@Test
	void testGetFilename() throws IOException {
		//
		Assertions.assertNull(new IniAsPropertiesResource(null).getFilename());
		//
		Assertions.assertNull(new IniAsPropertiesResource(resource).getFilename());
		//
	}

	@Test
	void testGetSection() throws Throwable {
		//
		Assertions.assertEquals(0, getSection(true, null, null));
		//
		Assertions.assertEquals(0, getSection(true, Collections.singletonMap(null, null), null));
		//
		final Map<?, ?> map = Collections.singletonMap("profile", null);
		//
		Assertions.assertEquals(0, getSection(true, map, null));
		//
		Assertions.assertEquals(0, getSection(true, map, Collections.emptySet()));
		//
		Assertions.assertEquals(1, getSection(true, map, Collections.singleton(null)));
		//
		Assertions.assertEquals(0, getSection(true, map, new ArrayList<>(Collections.singleton("1"))));
		//
	}

	private static int getSection(final boolean headless, final Map<?, ?> map, final Collection<?> collection)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_SECTION.invoke(null, headless, map, collection);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testErrorOrAssertOrShowException() {
		//
		Assertions.assertDoesNotThrow(() -> errorOrAssertOrShowException(true, null, null));
		//
		final Throwable throwable = new RuntimeException();
		//
		Assertions.assertDoesNotThrow(() -> errorOrAssertOrShowException(true, null, throwable));
		//
		Assertions.assertDoesNotThrow(() -> errorOrAssertOrShowException(true, NOPLogger.NOP_LOGGER, throwable));
		//
		Assertions.assertDoesNotThrow(
				() -> errorOrAssertOrShowException(true, LoggerFactory.getLogger(getClass()), throwable));
		//
		Assertions.assertDoesNotThrow(() -> errorOrAssertOrShowException(false, null, null));
		//
		Assertions.assertThrows(RuntimeException.class, () -> errorOrAssertOrShowException(false, null, throwable));
		//
	}

	private static void errorOrAssertOrShowException(final boolean headless, final Logger logger,
			final Throwable throwable) throws Throwable {
		try {
			METHOD_ERROR_OR_ASSERT_OR_SHOW_EXCEPTION.invoke(null, headless, logger, throwable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMessage() throws Throwable {
		//
		Assertions.assertNull(getMessage(null));
		//
	}

	private static String getMessage(final Throwable instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_MESSAGE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPrintStackTrace() {
		//
		Assertions.assertDoesNotThrow(() -> printStackTrace(null));
		//
	}

	private static void printStackTrace(final Throwable throwable) throws Throwable {
		try {
			METHOD_PRINT_STACK_TRACE.invoke(null, throwable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredMethods() throws Throwable {
		//
		Assertions.assertNull(getDeclaredMethods(null));
		//
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_METHODS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Method[]) {
				return (Method[]) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertNull(filter(null, null));
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
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForName() throws Throwable {
		//
		Assertions.assertNull(forName(null));
		//
		Assertions.assertNull(forName("A"));
		//
	}

	private static Class<?> forName(final String className) throws Throwable {
		try {
			final Object obj = METHOD_FOR_NAME.invoke(null, className);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToList() throws Throwable {
		//
		Assertions.assertNull(toList(null));
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_LIST.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(getName(null));
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
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetParameterTypes() throws Throwable {
		//
		Assertions.assertNull(getParameterTypes(null));
		//
	}

	private static Class<?>[] getParameterTypes(final Executable instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PARAMETER_TYPES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>[]) {
				return (Class<?>[]) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testInvoke() throws Throwable {
		//
		Assertions.assertNull(invoke(null, null));
		//
	}

	private static Object invoke(final Method method, final Object instance, Object... args) throws Throwable {
		try {
			return METHOD_INVOKE.invoke(null, method, instance, args);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsStatic() throws Throwable {
		//
		Assertions.assertFalse(isStatic(null));
		//
		Assertions.assertTrue(isStatic(Objects.class.getDeclaredMethod("toString", Object.class)));
		//
	}

	private static boolean isStatic(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_STATIC.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(obj != null && obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToRuntimeException() throws Throwable {
		//
		final RuntimeException runtimeException = new RuntimeException();
		//
		Assertions.assertSame(runtimeException, toRuntimeException(runtimeException));
		//
	}

	private static RuntimeException toRuntimeException(final Throwable instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_RUNTIME_EXCEPTION.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof RuntimeException) {
				return (RuntimeException) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToInputStream() throws Throwable {
		//
		Assertions.assertNotNull(toInputStream(null));
		//
	}

	private static InputStream toInputStream(final Properties properties) throws Throwable {
		try {
			final Object obj = METHOD_TO_INPUT_STREAM.invoke(null, properties);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStream) {
				return (InputStream) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetType() throws Throwable {
		//
		Assertions.assertNull(getType(null));
		//
	}

	private static Class<?> getType(final Field instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}