package org.springframework.context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import com.google.common.reflect.Reflection;

class CustomBeanFactoryPostProcessorTest {

	private static Method METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST_MAP,
			METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST_ITERABLE, METHOD_GET_SOURCE,
			METHOD_GET_MESSAGE = null;

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
			//
		if ((METHOD_GET_MESSAGE = clz != null ? clz.getDeclaredMethod("getMessage", Throwable.class) : null) != null) {
			//
			METHOD_GET_MESSAGE.setAccessible(true);
			//
		} // if
			//
	}

	private static class IH implements InvocationHandler {

		private Iterator<?> iterator = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
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
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private CustomBeanFactoryPostProcessor instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new CustomBeanFactoryPostProcessor();
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
		final IH ih = new IH();
		//
		ih.iterator = Collections.emptyIterator();
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
			throw new Throwable(obj.getClass() != null ? obj.getClass().toString() : null);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
}