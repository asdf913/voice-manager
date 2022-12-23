package org.springframework.context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.jena.ext.com.google.common.base.Predicates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import com.google.common.reflect.Reflection;

class CustomBeanFactoryPostProcessorTest {

	private static Method METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST_MAP,
			METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST_ITERABLE, METHOD_GET_SOURCE, METHOD_IS_STATIC,
			METHOD_GET_MESSAGE, METHOD_GET_CLASS, METHOD_ERROR_OR_PRINT_STACK_TRACE, METHOD_GET_DECLARED_METHODS,
			METHOD_FILTER, METHOD_TO_LIST, METHOD_TEST_AND_ACCEPT, METHOD_GET_NAME, METHOD_INVOKE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = CustomBeanFactoryPostProcessor.class;
		//
		if ((METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST_MAP = clz != null
				? clz.getDeclaredMethod("addPropertySourceToPropertySourcesToLast", Environment.class, Map.class)
				: null) != null) {
			//
			METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST_MAP.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST_ITERABLE = clz != null
				? clz.getDeclaredMethod("addPropertySourceToPropertySourcesToLast", Environment.class, Iterable.class)
				: null) != null) {
			//
			METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST_ITERABLE.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_GET_SOURCE = clz != null ? clz.getDeclaredMethod("getSource", PropertySource.class)
				: null) != null) {
			//
			METHOD_GET_SOURCE.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_IS_STATIC = clz != null ? clz.getDeclaredMethod("isStatic", Member.class) : null) != null) {
			//
			METHOD_IS_STATIC.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_GET_MESSAGE = clz != null ? clz.getDeclaredMethod("getMessage", Throwable.class) : null) != null) {
			//
			METHOD_GET_MESSAGE.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_GET_CLASS = clz != null ? clz.getDeclaredMethod("getClass", Object.class) : null) != null) {
			//
			METHOD_GET_CLASS.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_ERROR_OR_PRINT_STACK_TRACE = clz != null
				? clz.getDeclaredMethod("errorOrPrintStackTrace", Logger.class, Throwable.class, Throwable.class)
				: null) != null) {
			//
			METHOD_ERROR_OR_PRINT_STACK_TRACE.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_GET_DECLARED_METHODS = clz != null ? clz.getDeclaredMethod("getDeclaredMethods", Class.class)
				: null) != null) {
			//
			METHOD_GET_DECLARED_METHODS.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_FILTER = clz != null ? clz.getDeclaredMethod("filter", Stream.class, Predicate.class)
				: null) != null) {
			//
			METHOD_FILTER.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_TO_LIST = clz != null ? clz.getDeclaredMethod("toList", Stream.class) : null) != null) {
			//
			METHOD_TO_LIST.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_TEST_AND_ACCEPT = clz != null
				? clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class, FailableConsumer.class)
				: null) != null) {
			//
			METHOD_TEST_AND_ACCEPT.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_GET_NAME = clz != null ? clz.getDeclaredMethod("getName", Member.class) : null) != null) {
			//
			METHOD_GET_NAME.setAccessible(true);
			//
		} // if
			//
		if ((METHOD_INVOKE = clz != null ? clz.getDeclaredMethod("invoke", Method.class, Object.class, Object[].class)
				: null) != null) {
			//
			METHOD_INVOKE.setAccessible(true);
			//
		} // if
			//
	}

	private static class IH implements InvocationHandler {

		private Iterator<?> iterator = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(Void.TYPE, method != null ? method.getReturnType() : null)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Iterable) {
				//
				if (Objects.equals(methodName, "iterator")) {
					//
					return iterator;
					//
				} // if
					//
			} else if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "filter")) {
					//
					return proxy;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private CustomBeanFactoryPostProcessor instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new CustomBeanFactoryPostProcessor();
		//
		ih = new IH();
		//
	}

	@Test
	void testSetEnvironment() {
		//
		Assertions.assertDoesNotThrow(() -> instance.setEnvironment(null));
		//
	}

	@Test
	void testPostProcessBeanFactory() {
		//
		Assertions.assertDoesNotThrow(() -> instance.postProcessBeanFactory(null));
		//
	}

	@Test
	void testAddPropertySourceToPropertySourcesToLast() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(
				() -> addPropertySourceToPropertySourcesToLast(null, Collections.singletonMap(null, null)));
		//
		final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		//
		if (ih != null) {
			//
			ih.iterator = Collections.emptyIterator();
			//
		} // if
			//
		propertySourcesPlaceholderConfigurer.setPropertySources(Reflection.newProxy(PropertySources.class, ih));
		//
		FieldUtils.writeDeclaredField(propertySourcesPlaceholderConfigurer, "appliedPropertySources",
				Reflection.newProxy(PropertySources.class, ih), true);
		//
		Assertions.assertDoesNotThrow(() -> addPropertySourceToPropertySourcesToLast(null,
				Collections.singletonMap(null, propertySourcesPlaceholderConfigurer)));
		//
		Assertions.assertDoesNotThrow(
				() -> addPropertySourceToPropertySourcesToLast(null, Collections.singletonList(null)));
		//
		ih.iterator = null;
		//
		Assertions.assertDoesNotThrow(
				() -> addPropertySourceToPropertySourcesToLast(null, Reflection.newProxy(Iterable.class, ih)));
		//
	}

	private static void addPropertySourceToPropertySourcesToLast(final Environment environment,
			final Map<?, PropertySourcesPlaceholderConfigurer> propertySourcesPlaceholderConfigurers) throws Throwable {
		try {
			METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST_MAP.invoke(null, environment,
					propertySourcesPlaceholderConfigurers);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static void addPropertySourceToPropertySourcesToLast(final Environment environment,
			final Iterable<PropertySource<?>> propertySources) throws Throwable {
		try {
			METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST_ITERABLE.invoke(null, environment, propertySources);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSource() throws Throwable {
		//
		final Map<String, Object> map = Collections.emptyMap();
		//
		Assertions.assertSame(map, getSource(new SystemEnvironmentPropertySource("A", map)));
		//
	}

	private static <T> T getSource(final PropertySource<T> instance) throws Throwable {
		try {
			return (T) METHOD_GET_SOURCE.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsStatic() throws Throwable {
		//
		Assertions.assertFalse(isStatic(null));
		//
		Assertions.assertFalse(isStatic(Object.class.getDeclaredMethod("toString")));
		//
		Assertions.assertTrue(isStatic(Objects.class.getDeclaredMethod("hashCode", Object.class)));
		//
	}

	private static boolean isStatic(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_STATIC.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(instance)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetMessage() throws Throwable {
		//
		Assertions.assertNull(getMessage(null));
		//
		Assertions.assertNull(getMessage(new Throwable()));
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
			throw new Throwable(toString(getClass(instance)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetClass() throws Throwable {
		//
		Assertions.assertNull(getClass(null));
		//
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testErrorOrPrintStackTrace() throws IllegalAccessException {
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintStackTrace(null, null, null));
		//
		final Throwable throwable = new Throwable();
		//
		FieldUtils.writeDeclaredField(throwable, "stackTrace", new StackTraceElement[0], true);
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintStackTrace(null, throwable, null));
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintStackTrace(null, null, throwable));
		//
		final Logger logger = Reflection.newProxy(Logger.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintStackTrace(logger, null, null));
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintStackTrace(logger, throwable, null));
		//
		Assertions.assertDoesNotThrow(() -> errorOrPrintStackTrace(logger, null, throwable));
		//
	}

	private static void errorOrPrintStackTrace(final Logger logger, final Throwable a, final Throwable b)
			throws Throwable {
		try {
			METHOD_ERROR_OR_PRINT_STACK_TRACE.invoke(null, logger, a, b);
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
			throw new Throwable(toString(getClass(instance)));
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
		Assertions.assertNotNull(filter(Reflection.newProxy(Stream.class, ih), null));
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
			throw new Throwable(toString(getClass(instance)));
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
			throw new Throwable(toString(getClass(instance)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysFalse(), null, null));
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
			throw new Throwable(toString(getClass(instance)));
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

}