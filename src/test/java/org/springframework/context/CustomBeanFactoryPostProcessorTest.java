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

	private static Method METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST, METHOD_GET_SOURCE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = CustomBeanFactoryPostProcessor.class;
		//
		if ((METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST = clz != null
				? clz.getDeclaredMethod("addPropertySourceToPropertySourcesToLast", Environment.class, Map.class)
				: null) != null) {
			//
			METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST.setAccessible(true);
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
	}

	private static void addPropertySourceToPropertySourcesToLast(final Environment environment,
			final Map<?, PropertySourcesPlaceholderConfigurer> propertySourcesPlaceholderConfigurers) throws Throwable {
		try {
			METHOD_ADD_PROPERTY_SOURCE_TO_PROPERTY_SOURCES_TO_LAST.invoke(null, environment,
					propertySourcesPlaceholderConfigurers);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSource() throws Throwable {
		//
		Assertions.assertNull(getSource(null));
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

}