package org.springframework.beans.factory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.Signature;
import java.sql.DriverManager;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javax.script.ScriptEngine;
import javax.swing.JButton;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.reflect.Reflection;

class WestJapanRailwayKanjiRomajiOrHiraganaMapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY4, METHOD_TEST_AND_APPLY5, METHOD_CREATE_TABLE,
			METHOD_GET_UNICODE_BLOCKS, METHOD_TEST, METHOD_ACCEPT, METHOD_IS_INSTANCE, METHOD_OPEN_STREAM,
			METHOD_GET_TRIPLES_1, METHOD_GET_TRIPLES_2, METHOD_GET_NAME_MODULE, METHOD_GET_MODULE, METHOD_GET = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = WestJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				BiFunction.class, BiFunction.class)).setAccessible(true);
		//
		(METHOD_CREATE_TABLE = clz.getDeclaredMethod("createTable", Object[].class)).setAccessible(true);
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", BiPredicate.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_ACCEPT = clz.getDeclaredMethod("accept", BiConsumer.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_IS_INSTANCE = clz.getDeclaredMethod("isInstance", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_OPEN_STREAM = clz.getDeclaredMethod("openStream", URL.class)).setAccessible(true);
		//
		(METHOD_GET_TRIPLES_1 = clz.getDeclaredMethod("getTriples", Map.class)).setAccessible(true);
		//
		(METHOD_GET_TRIPLES_2 = clz.getDeclaredMethod("getTriples", Field[].class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_NAME_MODULE = clz.getDeclaredMethod("getName", Module.class)).setAccessible(true);
		//
		(METHOD_GET_MODULE = clz.getDeclaredMethod("getModule", Class.class)).setAccessible(true);
		//
		(METHOD_GET = clz.getDeclaredMethod("get", ScriptEngine.class, String.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private InputStream inputStream = null;

		private Boolean exists = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = getName(method);
			//
			if (proxy instanceof InputStreamSource) {
				//
				if (Objects.equals(methodName, "getInputStream")) {
					//
					return inputStream;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof Resource) {
				//
				if (Objects.equals(methodName, "exists")) {
					//
					return exists;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private WestJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new WestJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean();
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
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setUrl("");
			//
		} // if
			//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setUrl(" ");
			//
		} // if
			//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setResourceJs(Reflection.newProxy(Resource.class, ih));
			//
		} // if
			//
		if (ih != null) {
			//
			ih.exists = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		try (final InputStream is = new ByteArrayInputStream("".getBytes())) {
			//
			if (ih != null) {
				//
				ih.inputStream = is;
				//
			} // if
				//
			Assertions.assertNull(instance != null ? instance.getObject() : null);
			//
		} // try
			//
		if (ih != null) {
			//
			ih.exists = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
	}

	@Test
	void testSetUnicodeBlock() throws Throwable {
		//
		final Field unicodeBlock = WestJapanRailwayKanjiRomajiOrHiraganaMapFactoryBean.class
				.getDeclaredField("unicodeBlock");
		//
		if (unicodeBlock != null) {
			//
			unicodeBlock.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock(null));
		//
		Assertions.assertNull(get(unicodeBlock, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock(""));
		//
		Assertions.assertNull(get(unicodeBlock, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock("".toCharArray()));
		//
		Assertions.assertNull(get(unicodeBlock, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock("".getBytes()));
		//
		Assertions.assertNull(get(unicodeBlock, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock("123"));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock("HIRAGANA".toCharArray()));
		//
		final Object hiragana = get(unicodeBlock, instance);
		//
		Assertions.assertEquals("HIRAGANA", Util.toString(hiragana));
		//
		Assertions.assertDoesNotThrow(() -> instance.setUnicodeBlock(hiragana));
		//
		Assertions.assertSame(hiragana, get(unicodeBlock, instance));
		//
		Assertions.assertThrows(IllegalArgumentException.class, () -> instance.setUnicodeBlock(Collections.emptyMap()));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> instance.setUnicodeBlock("h"));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> instance.setUnicodeBlock("map"));
		//
	}

	private static Object get(final Field field, final Object instance)
			throws IllegalArgumentException, IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
		//
		Assertions.assertNull(testAndApply(null, null, null, null, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY4.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY5.invoke(null, predicate, t, u, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static class InnerClass {

		private String string1, string2 = null;

	}

	@Test
	void testCreateTable() throws Throwable {
		//
		Assertions.assertNull(createTable(new Object[] { null }));
		//
		final InnerClass cls = new InnerClass();
		//
		Assertions.assertNull(createTable(new Object[] { "", cls }));
		//
		cls.string1 = "";
		//
		Assertions.assertNull(createTable(new Object[] { "", cls }));
		//
		cls.string1 = "一";
		//
		Assertions.assertNull(createTable(new Object[] { "", cls }));
		//
		Assertions.assertEquals(ImmutableTable.of(cls.string1 = "一一", UnicodeBlock.BASIC_LATIN, cls.string2 = " "),
				createTable(new Object[] { "", cls }));
		//
	}

	private static Table<String, UnicodeBlock, String> createTable(final Object[] os) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_TABLE.invoke(null, (Object) os);
			if (obj == null) {
				return null;
			} else if (obj instanceof Table) {
				return (Table) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetUnicodeBlocks() throws Throwable {
		//
		Assertions.assertNull(getUnicodeBlocks(null));
		//
	}

	private static List<UnicodeBlock> getUnicodeBlocks(final String string) throws Throwable {
		try {
			final Object obj = METHOD_GET_UNICODE_BLOCKS.invoke(null, string);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
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
			final Object obj = METHOD_TEST.invoke(null, instance, t, u);
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
	void testIsInstance() throws Throwable {
		//
		Assertions.assertFalse(isInstance(null, null));
		//
	}

	private static boolean isInstance(final Class<?> clz, final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_INSTANCE.invoke(null, clz, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOpenStream() throws Throwable {
		//
		Assertions.assertNotNull(openStream(new File("pom.xml").toURI().toURL()));
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
	void testGetTriples() throws Throwable {
		//
		Assertions.assertNull(getTriples(null, null));
		//
		Assertions.assertNull(getTriples(new Field[] { null }, null));
		//
		// "java.sql" module
		//
		Assertions.assertNull(
				getTriples(new Field[] { DriverManager.class.getDeclaredField("JDBC_DRIVERS_PROPERTY") }, null));
		//
		// "java.desktop" module
		//
		Assertions.assertNull(getTriples(new Field[] { JButton.class.getDeclaredField("uiClassID") }, null));
		//
		// "java.logging" module
		//
		Assertions.assertNull(getTriples(new Field[] { Logger.class.getDeclaredField("SYSTEM_LOGGER_RB_NAME") }, null));
		//
		Assertions.assertNull(getTriples(new Field[] { Signature.class.getDeclaredField("algorithm") }, null));
		//
		final Map<UnicodeBlock, String> map = new LinkedHashMap<>();
		//
		Assertions.assertNull(getTriples(map));
		//
		map.put(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS, null);
		//
		map.put(UnicodeBlock.CJK_STROKES, null);
		//
		Assertions.assertThrows(IllegalStateException.class, () -> getTriples(map));
		//
	}

	private static List<Triple<String, UnicodeBlock, String>> getTriples(final Map<UnicodeBlock, String> map)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_TRIPLES_1.invoke(null, map);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static List<Triple<String, UnicodeBlock, String>> getTriples(final Field[] fs, final Object instance)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_TRIPLES_2.invoke(null, fs, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(getName((Module) null));
		//
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static String getName(final Module instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME_MODULE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetModule() throws Throwable {
		//
		Assertions.assertNull(getModule(null));
		//
	}

	private static Module getModule(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_MODULE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Module) {
				return (Module) obj;
			}
			throw new Throwable(Util.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGet() throws Throwable {
		//
		Assertions.assertNull(get((Field) null, null));
		//
		Assertions.assertNull(get((ScriptEngine) null, null));
		//
	}

	private static Object get(final ScriptEngine instance, final String key) throws Throwable {
		try {
			return METHOD_GET.invoke(null, instance, key);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}