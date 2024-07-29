package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.javatuples.valueintf.IValue0;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class RyutetsuKanjiHiraganaMapFactoryBeanTest {

	private static Class<?> CLASS_KANJI_HIRAGANA_ROMAJI = null;

	private static Method METHOD_TEST_AND_APPLY4, METHOD_TEST_AND_APPLY5, METHOD_GET_UNICODE_BLOCKS,
			METHOD_TEST_AND_ACCEPT, METHOD_CREATE_KANJI_HIRAGANA_ROMAJI_LIST, METHOD_CREATE_ENTRY, METHOD_CREATE_MAP,
			METHOD_SET_HIRAGANA_KANJI_ROMAJI = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = RyutetsuKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				FailableBiFunction.class, FailableBiFunction.class)).setAccessible(true);
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", char[].class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_CREATE_KANJI_HIRAGANA_ROMAJI_LIST = clz.getDeclaredMethod("createKanjiHiraganaRomajiList", List.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_ENTRY = clz.getDeclaredMethod("createEntry", Field[].class, Object.class)).setAccessible(true);
		//
		(METHOD_CREATE_MAP = clz.getDeclaredMethod("createMap", List.class)).setAccessible(true);
		//
		(METHOD_SET_HIRAGANA_KANJI_ROMAJI = clz.getDeclaredMethod("setHiraganaKanjiRomaji",
				CLASS_KANJI_HIRAGANA_ROMAJI = Class.forName(
						"org.springframework.beans.factory.RyutetsuKanjiHiraganaMapFactoryBean$KanjiHiraganaRomaji"),
				Iterable.class)).setAccessible(true);
		//
	}

	private static class MH implements MethodHandler {

		private String nodeName, attr = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = getName(thisMethod);
			//
			if (self instanceof Node) {
				//
				if (Objects.equals(methodName, "nodeName")) {
					//
					return nodeName;
					//
				} else if (Objects.equals(methodName, "attr")) {
					//
					return attr;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = getName(method);
			//
			if (proxy instanceof Iterable) {
				//
				if (Objects.equals(methodName, "iterator")) {
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

	private RyutetsuKanjiHiraganaMapFactoryBean instance = null;

	@BeforeEach
	private void beforeEach() {
		//
		instance = new RyutetsuKanjiHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
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
		Assertions.assertNull(testAndApply(Predicates.biAlwaysTrue(), null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.biAlwaysFalse(), null, null, null, null));
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
			final U u, final FailableBiFunction<T, U, R, E> functionTrue,
			final FailableBiFunction<T, U, R, E> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY5.invoke(null, predicate, t, u, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetUnicodeBlocks() throws Throwable {
		//
		Assertions.assertNull(getUnicodeBlocks(null));
		//
		Assertions.assertEquals(Collections.singletonList(UnicodeBlock.BASIC_LATIN),
				getUnicodeBlocks(new char[] { ' ' }));
		//
	}

	private static List<UnicodeBlock> getUnicodeBlocks(final char[] cs) throws Throwable {
		try {
			final Object obj = METHOD_GET_UNICODE_BLOCKS.invoke(null, cs);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.biAlwaysTrue(), null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.biAlwaysFalse(), null, null, null));
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT.invoke(null, instance, t, u, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateKanjiHiraganaRomajiList() throws Throwable {
		//
		Assertions.assertNotNull(createKanjiHiraganaRomajiList(
				Arrays.asList(null, Util.cast(Element.class, Narcissus.allocateInstance(Element.class)))));
		//
	}

	private static List<?> createKanjiHiraganaRomajiList(final List<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_KANJI_HIRAGANA_ROMAJI_LIST.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List<?>) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateEntry() throws Throwable {
		//
		Assertions.assertEquals(Pair.of(null, null), createEntry(null, null));
		//
		Assertions.assertEquals(Pair.of(null, null), createEntry(new Field[] { null }, null));
		//
		Assertions.assertEquals(Pair.of(null, null),
				createEntry(new Field[] { Boolean.class.getDeclaredField("value") }, Boolean.TRUE));
		//
		final Field[] fs = new Field[] { String.class.getDeclaredField("value") };
		//
		final Object value = "";
		//
		Assertions.assertEquals(Pair.of(null, null), createEntry(fs, value));
		//
		if (instance != null) {
			//
			instance.setKeyUnicodeBlock(UnicodeBlock.BASIC_LATIN);
			//
		} // if
			//
		Assertions.assertNotNull(createEntry(fs, value));
		//
		if (instance != null) {
			//
			instance.setKeyUnicodeBlock(null);
			//
			instance.setValueUnicodeBlock(UnicodeBlock.BASIC_LATIN);
			//
		} // if
			//
		Assertions.assertNotNull(createEntry(fs, value));
		//
	}

	private Entry<IValue0<String>, IValue0<String>> createEntry(final Field[] fs, final Object v) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_ENTRY.invoke(instance, fs, v);
			if (obj == null) {
				return null;
			} else if (obj instanceof Entry) {
				return (Entry) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetHiraganaKanjiRomaji() throws Throwable {
		//
		Assertions
				.assertDoesNotThrow(() -> setHiraganaKanjiRomaji(null, Reflection.newProxy(Iterable.class, new IH())));
		//
		Assertions.assertDoesNotThrow(() -> setHiraganaKanjiRomaji(null, Collections.singleton(null)));
		//
		final MH mh = new MH();
		//
		final Iterable<Node> nodes = Collections.singleton(ProxyUtil.createProxy(Node.class, mh));
		//
		Assertions.assertDoesNotThrow(() -> setHiraganaKanjiRomaji(null, nodes));
		//
		mh.nodeName = "a";
		//
		Assertions.assertDoesNotThrow(() -> setHiraganaKanjiRomaji(null, nodes));
		//
		final Constructor<?> constructor = CLASS_KANJI_HIRAGANA_ROMAJI != null
				? CLASS_KANJI_HIRAGANA_ROMAJI.getDeclaredConstructor()
				: null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		Assertions.assertDoesNotThrow(
				() -> setHiraganaKanjiRomaji(constructor != null ? constructor.newInstance() : null, nodes));
		//
	}

	private static void setHiraganaKanjiRomaji(final Object khr, final Iterable<Node> childNodes) throws Throwable {
		try {
			METHOD_SET_HIRAGANA_KANJI_ROMAJI.invoke(null, khr, childNodes);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMap() throws Throwable {
		//
		Assertions.assertNull(createMap(Collections.singletonList(null)));
		//
	}

	private Map<String, String> createMap(final List<?> khrs) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP.invoke(instance, khrs);
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

}