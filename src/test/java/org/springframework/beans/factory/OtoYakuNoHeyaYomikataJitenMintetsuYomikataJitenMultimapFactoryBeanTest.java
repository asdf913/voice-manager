package org.springframework.beans.factory;

import java.io.File;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_GET_UNICODE_BLOCKS, METHOD_TEST_AND_ACCEPT,
			METHOD_TO_MULTI_MAP = null;

	private static Class<?> CLASS_PATTERN_MAP = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException, ClassNotFoundException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP = clz.getDeclaredMethod("toMultimap", Element.class, Object.class,
				CLASS_PATTERN_MAP = Class.forName(
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean$PatternMap")))
				.setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean.url")) {
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean.url")));
				//
			} // if
				//
			final File file = new File("OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean.txt");
			//
			FileUtils.writeLines(file, MultimapUtil.entries(getObject(instance)));
			//
			System.out.println(file.getAbsolutePath());
			//
		} // if
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
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
	void testGetUnicodeBlocks() throws Throwable {
		//
		Assertions.assertNull(getUnicodeBlocks(null));
		//
		Assertions.assertEquals(Collections.singletonList(UnicodeBlock.BASIC_LATIN), getUnicodeBlocks(" "));
		//
		Assertions.assertEquals(Collections.singletonList(UnicodeBlock.BASIC_LATIN), getUnicodeBlocks("  "));
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
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, null, null));
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
	void testToMultimap() throws Throwable {
		//
		Assertions.assertNull(toMultimap(null, null, null));
		//
	}

	private static Multimap<String, String> toMultimap(final Element element, final Object firstRowTexts,
			final Object patternMap) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP.invoke(null, element, firstRowTexts, patternMap);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIH() throws Throwable {
		//
		final InvocationHandler ih = Util.cast(InvocationHandler.class, Narcissus.allocateInstance(Class.forName(
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean$IH")));
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, null, null, null));
		//
		final Object patternMap = Reflection.newProxy(CLASS_PATTERN_MAP, ih);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, patternMap, null, null));
		//
		final Method getPattern = CLASS_PATTERN_MAP != null
				? CLASS_PATTERN_MAP.getDeclaredMethod("getPattern", String.class)
				: null;
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, patternMap, getPattern, null));
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, patternMap, getPattern, new Object[] {}));
		//
		Assertions.assertNull(invoke(ih, patternMap, getPattern, new Object[] { null }));
		//
		final Object o1 = invoke(ih, patternMap, getPattern, new Object[] { "" });
		//
		Assertions.assertNotNull(o1);
		//
		Assertions.assertSame(o1, invoke(ih, patternMap, getPattern, new Object[] { "" }));
		//
	}

	private static Object invoke(final InvocationHandler instance, final Object proxy, final Method method,
			final Object[] args) throws Throwable {
		return instance != null ? instance.invoke(proxy, method, args) : null;
	}

	@Test
	void testPatternMap() throws Throwable {
		//
		final Method getPattern = CLASS_PATTERN_MAP != null
				? CLASS_PATTERN_MAP.getDeclaredMethod("getPattern", CLASS_PATTERN_MAP, String.class)
				: null;
		//
		if (getPattern != null) {
			//
			getPattern.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(getPattern != null ? getPattern.invoke(null, Reflection.newProxy(CLASS_PATTERN_MAP,
				Util.cast(InvocationHandler.class, Narcissus.allocateInstance(Class.forName(
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean$IH")))),
				null) : null);
		//
		Assertions.assertNull(getPattern != null ? getPattern.invoke(null, null, null) : null);
		//
	}

}