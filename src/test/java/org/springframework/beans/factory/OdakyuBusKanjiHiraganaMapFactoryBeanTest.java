package org.springframework.beans.factory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.function.FailableFunction;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import io.github.toolfactory.narcissus.Narcissus;

class OdakyuBusKanjiHiraganaMapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_GET_OBJECT, METHOD_GET_CLASS, METHOD_CREATE_MAP, METHOD_TEST_AND_APPLY,
			METHOD_GET_TEMPLATE, METHOD_CAST, METHOD_PROCESS, METHOD_IS_ALL_CHARACTER_IN_SAME_UNICODE_BLOCK,
			METHOD_CONTAINS, METHOD_ADD, METHOD_TEST2, METHOD_TEST3, METHOD_ACCEPT, METHOD_PUT, METHOD_OPEN_STREAM,
			METHOD_GET_DECLARED_FIELD, METHOD_GET, METHOD_CHECK_IF_KEY_EXISTS_AND_DIFFERENCE_VALUE,
			METHOD_GET_VALUE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = OdakyuBusKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_GET_OBJECT = clz.getDeclaredMethod("getObject", Configuration.class, List.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_CREATE_MAP = clz.getDeclaredMethod("createMap", List.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_TEMPLATE = clz.getDeclaredMethod("getTemplate", Configuration.class, String.class))
				.setAccessible(true);
		//
		(METHOD_PROCESS = clz.getDeclaredMethod("process", Template.class, Object.class, Writer.class))
				.setAccessible(true);
		//
		(METHOD_IS_ALL_CHARACTER_IN_SAME_UNICODE_BLOCK = clz.getDeclaredMethod("isAllCharacterInSameUnicodeBlock",
				String.class, UnicodeBlock.class)).setAccessible(true);
		//
		(METHOD_CONTAINS = clz.getDeclaredMethod("contains", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_TEST2 = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_TEST3 = clz.getDeclaredMethod("test", BiPredicate.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_ACCEPT = clz.getDeclaredMethod("accept", BiConsumer.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_PUT = clz.getDeclaredMethod("put", Map.class, Object.class, Object.class)).setAccessible(true);
		//
		(METHOD_OPEN_STREAM = clz.getDeclaredMethod("openStream", URL.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_FIELD = clz.getDeclaredMethod("getDeclaredField", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET = clz.getDeclaredMethod("get", Map.class, Object.class)).setAccessible(true);
		//
		(METHOD_CHECK_IF_KEY_EXISTS_AND_DIFFERENCE_VALUE = clz.getDeclaredMethod("checkIfKeyExistsAndDifferenceValue",
				Map.class, Entry.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", Entry.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Object templateSource, key, value = null;

		private Long lastModified = null;

		private Reader reader = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (ReflectionUtils.isToStringMethod(method)) {
				//
				return ToStringBuilder.reflectionToString(this);
				//
			} // if
				//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof TemplateLoader) {
				//
				if (Objects.equals(Void.TYPE, method != null ? method.getReturnType() : null)) {
					//
					return null;
					//
				} // if
					//
				if (Objects.equals(methodName, "findTemplateSource")) {
					//
					return templateSource;
					//
				} else if (Objects.equals(methodName, "getLastModified")) {
					//
					return lastModified;
					//
				} else if (Objects.equals(methodName, "getReader")) {
					//
					return reader;
					//
				} // if
					//
			} else if (proxy instanceof Entry) {
				//
				if (Objects.equals(methodName, "getKey")) {
					//
					return key;
					//
				} else if (Objects.equals(methodName, "getValue")) {
					//
					return value;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OdakyuBusKanjiHiraganaMapFactoryBean instance = null;

	private ObjectMapper objectMapper = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OdakyuBusKanjiHiraganaMapFactoryBean();
		//
		objectMapper = new ObjectMapper();
		//
		ih = new IH();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetObject() throws Throwable {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrlTemplate(EMPTY);
			//
			instance.setConfiguration(null);
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		Assertions.assertNull(getObject(instance));
		//
		Assertions.assertNull(getObject(null, null));
		//
		Assertions.assertNull(getObject(null, Collections.singletonList(null)));
		//
		Assertions.assertNull(getObject(null, Collections.singletonList(Collections.singletonMap("code", null))));
		//
		Assertions.assertNull(getObject(null, Collections
				.singletonList(ObjectMapperUtil.readValue(objectMapper, "{\"code\":null,\"count\":0}", Object.class))));
		//
		Assertions.assertNull(getObject(null, Collections
				.singletonList(ObjectMapperUtil.readValue(objectMapper, "{\"code\":null,\"count\":1}", Object.class))));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	private static Map<String, String> getObject(final Configuration configuration, final List<?> items)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_OBJECT.invoke(null, configuration, items);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
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
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMap() throws Throwable {
		//
		Assertions.assertNull(createMap(Collections.singletonList(null)));
		//
		Assertions.assertNull(createMap(Collections.singletonList(Collections.singletonMap(null, null))));
		//
		Assertions.assertNull(createMap(Collections.singletonList(Collections.singletonMap("name", null))));
		//
		Assertions.assertNull(createMap(Collections.singletonList(
				ObjectMapperUtil.readValue(objectMapper, "{\"name\":null,\"ruby\":null}", Object.class))));
		//
		Assertions.assertNull(createMap(Collections
				.singletonList(ObjectMapperUtil.readValue(objectMapper, "{\"name\":1,\"ruby\":null}", Object.class))));
		//
		Assertions.assertEquals(Collections.singletonMap("一", null), createMap(Collections.singletonList(
				ObjectMapperUtil.readValue(objectMapper, "{\"name\":\"一\",\"ruby\":null}", Object.class))));
		//
	}

	private static Map<String, String> createMap(final List<?> items) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP.invoke(null, items);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, FailableFunction.identity(), null));
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
	void testGetTemplate() throws Throwable {
		//
		Assertions.assertNull(
				getTemplate(cast(Configuration.class, Narcissus.allocateInstance(Configuration.class)), null));
		//
		final Configuration configuration1 = new Configuration(Configuration.getVersion());
		//
		Assertions.assertNull(getTemplate(configuration1, null));
		//
		Assertions.assertNull(getTemplate(configuration1, EMPTY));
		//
		final TemplateLoader templateLoader = Reflection.newProxy(TemplateLoader.class, ih);
		//
		configuration1.setTemplateLoader(templateLoader);
		//
		Assertions.assertThrows(TemplateNotFoundException.class, () -> getTemplate(configuration1, EMPTY));
		//
		final Configuration configuration2 = new Configuration(Configuration.getVersion());
		//
		if (ih != null) {
			//
			ih.templateSource = EMPTY;
			//
			ih.lastModified = Long.valueOf(0);
			//
		} // if
			//
		try (final Reader reader = new StringReader(EMPTY)) {
			//
			if (ih != null) {
				//
				ih.reader = reader;
				//
			} // if
				//
			configuration2.setTemplateLoader(templateLoader);
			//
			Assertions.assertNotNull(getTemplate(configuration2, EMPTY));
			//
		} // try
			//
	}

	private static Template getTemplate(final Configuration instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_TEMPLATE.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Template) {
				return (Template) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCast() throws Throwable {
		//
		Assert.assertNull(cast(null, null));
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
	void testProcess() throws IOException {
		//
		Assertions.assertDoesNotThrow(
				() -> process(cast(Template.class, Narcissus.allocateInstance(Template.class)), null, null));
		//
		final Template template = new Template(EMPTY, EMPTY, null);
		//
		Assertions.assertDoesNotThrow(() -> process(template, null, null));
		//
		try (final Writer writer = new StringWriter()) {
			//
			Assertions.assertDoesNotThrow(() -> process(template, null, writer));
			//
		} // try
			//
	}

	private static void process(final Template instance, final Object dataModel, final Writer out) throws Throwable {
		try {
			METHOD_PROCESS.invoke(null, instance, dataModel, out);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAllCharacterInSameUnicodeBlock() throws Throwable {
		//
		Assertions.assertTrue(isAllCharacterInSameUnicodeBlock(null, null));
		//
		Assertions.assertTrue(isAllCharacterInSameUnicodeBlock(null, null));
		//
	}

	private static boolean isAllCharacterInSameUnicodeBlock(final String string, final UnicodeBlock unicodeBlock)
			throws Throwable {
		try {
			final Object obj = METHOD_IS_ALL_CHARACTER_IN_SAME_UNICODE_BLOCK.invoke(null, string, unicodeBlock);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(contains(null, null));
		//
		Assertions.assertTrue(contains(Collections.singleton(null), null));
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS.invoke(null, items, item);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAdd() {
		//
		Assertions.assertDoesNotThrow(() -> add(null, null));
		//
	}

	private static <E> void add(final Collection<E> items, final E item) throws Throwable {
		try {
			METHOD_ADD.invoke(null, items, item);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null));
		//
		Assertions.assertFalse(test(null, null, null));
		//
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST2.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U> boolean test(final BiPredicate<T, U> instance, final T t, final U u) throws Throwable {
		try {
			final Object obj = METHOD_TEST3.invoke(null, instance, t, u);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAccept() {
		//
		Assertions.assertDoesNotThrow(() -> accept(null, null, null));
		//
	}

	private static <T, U> void accept(final BiConsumer<T, U> instance, final T t, final U u) throws Throwable {
		try {
			METHOD_ACCEPT.invoke(null, instance, t, u);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPut() {
		//
		Assertions.assertDoesNotThrow(() -> put(null, null, null));
	}

	private static <K, V> void put(final Map<K, V> instance, final K key, final V value) throws Throwable {
		try {
			METHOD_PUT.invoke(null, instance, key, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOpenStream() throws Throwable {
		//
		Assertions.assertNotNull(openStream(new File("pom.xml").toURI().toURL()));
		//
		Assertions.assertNull(openStream(cast(URL.class, Narcissus.allocateInstance(URL.class))));
		//
	}

	private static InputStream openStream(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_OPEN_STREAM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStream) {
				return (InputStream) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredField() throws Throwable {
		//
		Assertions.assertNull(getDeclaredField(null, null));
		//
		Assertions.assertNull(getDeclaredField(Object.class, null));
		//
	}

	private static Field getDeclaredField(final Class<?> instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_FIELD.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field) {
				return (Field) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGet() throws Throwable {
		//
		Assertions.assertNull(get(null, null));
		//
	}

	private static <V> V get(final Map<?, V> instance, final Object key) throws Throwable {
		try {
			return (V) METHOD_GET.invoke(null, instance, key);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCheckIfKeyExistsAndDifferenceValue() {
		//
		Assertions.assertDoesNotThrow(() -> checkIfKeyExistsAndDifferenceValue(null, null));
		//
		final Entry<?, ?> entry = Reflection.newProxy(Entry.class, ih);
		//
		Assertions.assertDoesNotThrow(
				() -> checkIfKeyExistsAndDifferenceValue(Collections.singletonMap(null, null), entry));
		//
		Assertions.assertThrows(IllegalStateException.class,
				() -> checkIfKeyExistsAndDifferenceValue(Collections.singletonMap(null, EMPTY), entry));
		//
	}

	private static void checkIfKeyExistsAndDifferenceValue(final Map<?, ?> map, final Entry<?, ?> entry)
			throws Throwable {
		try {
			METHOD_CHECK_IF_KEY_EXISTS_AND_DIFFERENCE_VALUE.invoke(null, map, entry);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue() throws Throwable {
		//
		Assertions.assertNull(getValue(null));
		//
	}

	private static <V> V getValue(final Entry<?, V> instance) throws Throwable {
		try {
			return (V) METHOD_GET_VALUE.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}