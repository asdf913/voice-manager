package org.springframework.beans.factory;

import java.io.Writer;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import freemarker.template.Configuration;
import freemarker.template.Template;

class OdakyuBusKanjiHiraganaMapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_GET_OBJECT, METHOD_CREATE_MAP, METHOD_TEST_AND_APPLY, METHOD_PROCESS,
			METHOD_IS_ALL_CHARACTER_IN_SAME_UNICODE_BLOCK, METHOD_TEST3, METHOD_ACCEPT,
			METHOD_CHECK_IF_KEY_EXISTS_AND_DIFFERENCE_VALUE, METHOD_PERFORM = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = OdakyuBusKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_GET_OBJECT = clz.getDeclaredMethod("getObject", Configuration.class, List.class, ObjectMapper.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MAP = clz.getDeclaredMethod("createMap", List.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_IS_ALL_CHARACTER_IN_SAME_UNICODE_BLOCK = clz.getDeclaredMethod("isAllCharacterInSameUnicodeBlock",
				String.class, UnicodeBlock.class)).setAccessible(true);
		//
		(METHOD_TEST3 = clz.getDeclaredMethod("test", BiPredicate.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_ACCEPT = clz.getDeclaredMethod("accept", BiConsumer.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_CHECK_IF_KEY_EXISTS_AND_DIFFERENCE_VALUE = clz.getDeclaredMethod("checkIfKeyExistsAndDifferenceValue",
				Map.class, Entry.class)).setAccessible(true);
		//
		(METHOD_PERFORM = clz.getDeclaredMethod("perform", AtomicReference.class, Map.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Object key, value = null;

		private Set<Entry<?, ?>> entrySet = null;

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
			if (proxy instanceof Entry) {
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
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "entrySet")) {
					//
					return entrySet;
					//
				} // if
					//
			} // if
				//
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
		Assertions.assertNull(getObject(null, null, null));
		//
		Assertions.assertNull(getObject(null, Collections.singletonList(null), null));
		//
		Assertions.assertNull(getObject(null, Collections.singletonList(Collections.singletonMap("code", null)), null));
		//
		Assertions
				.assertNull(getObject(null,
						Collections.singletonList(
								ObjectMapperUtil.readValue(objectMapper, "{\"code\":null,\"count\":0}", Object.class)),
						null));
		//
		Assertions.assertEquals(Collections.emptyMap(),
				getObject(null,
						Collections.singletonList(
								ObjectMapperUtil.readValue(objectMapper, "{\"code\":null,\"count\":1}", Object.class)),
						null));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	private static Map<String, String> getObject(final Configuration configuration, final List<?> items,
			final ObjectMapper objectMapper) throws Throwable {
		try {
			final Object obj = METHOD_GET_OBJECT.invoke(null, configuration, items, objectMapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
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
			throw new Throwable(Util.toString(Util.getClass(obj)));
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

	@SuppressWarnings("unused")
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
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null, null));
		//
	}

	private static <T, U> boolean test(final BiPredicate<T, U> instance, final T t, final U u) throws Throwable {
		try {
			final Object obj = METHOD_TEST3.invoke(null, instance, t, u);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
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
	void testCheckIfKeyExistsAndDifferenceValue() {
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
	void testPerform() {
		//
		Assertions.assertDoesNotThrow(() -> perform(null, Collections.singletonMap(null, null)));
		//
		final Map<?, ?> map = Reflection.newProxy(Map.class, ih);
		//
		Assertions.assertDoesNotThrow(() -> perform(null, map));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> perform(null, map));
		//
		if (ih != null) {
			//
			ih.entrySet = Reflection.newProxy(Set.class, ih);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> perform(null, map));
		//
	}

	private static void perform(final AtomicReference<Map<String, String>> ar, final Map<?, ?> map) throws Throwable {
		try {
			METHOD_PERFORM.invoke(null, ar, map);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}