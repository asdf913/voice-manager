package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;

import io.github.toolfactory.narcissus.Narcissus;
import it.unimi.dsi.fastutil.ints.IntObjectPair;

class OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBeanTest {

	private static final int ZERO = 0;

	private static final String EMPTY = "";

	private static final String SPACE = " ";

	private static Method METHOD_TEST_AND_APPLY, METHOD_GET_COMMON_SUFFIX, METHOD_TO_MULTI_MAP;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException, ClassNotFoundException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_COMMON_SUFFIX = clz.getDeclaredMethod("getCommonSuffix", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP = clz.getDeclaredMethod("toMultimap", PatternMap.class, IntObjectPair.class,
				Iterable.class)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBeanTest.testGetObject");
		//
	}

	@Test
	void testGetObjecctType() {
		//
		Assertions.assertEquals(Multimap.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		final Entry<Field, Object> entry = TestUtil.getFieldWithUrlAnnotationAndValue(
				OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean.class);
		//
		final Field url = Util.getKey(entry);
		//
		Narcissus.setObjectField(instance, url, EMPTY);
		//
		Assertions.assertNull(getObject(instance));
		//
		Narcissus.setObjectField(instance, url, SPACE);
		//
		Assertions.assertNull(getObject(instance));
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			Narcissus.setObjectField(instance, url, Util.getValue(entry));
			//
			Assertions.assertDoesNotThrow(() -> getObject(instance));
			//
		} // if
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testNull() {
		//
		final Method[] ms = getDeclaredMethods(
				OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean.class);
		//
		Method m = null;
		//
		Collection<Object> list = null;
		//
		Object invokeStaticMethod;
		//
		String toString;
		//
		for (int i = ZERO; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			clear(list = ObjectUtils.getIfNull(list, ArrayList::new));
			//
			for (int j = 0; j < Util.length(m.getParameterTypes()); j++) {
				//
				list.add(null);
				//
			} // for
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(list));
			//
			toString = Objects.toString(m);
			//
			if (Objects.equals(Util.getName(m), "getCommonSuffix")
					&& Arrays.equals(m.getParameterTypes(), new Class<?>[] { String.class, String.class })) {
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

	private static Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
	}

	private void clear(final Collection<Object> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
			//
		} // if
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
	void testGetCommonSuffix() throws Throwable {
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertEquals(EMPTY, getCommonSuffix(EMPTY, null));
			//
			Assertions.assertEquals(EMPTY, getCommonSuffix(EMPTY, EMPTY));
			//
			Assertions.assertEquals(EMPTY, getCommonSuffix(SPACE, EMPTY));
			//
			Assertions.assertEquals(SPACE, getCommonSuffix(SPACE, SPACE));
			//
		} // if
			//
	}

	private static String getCommonSuffix(final String s1, final String s2) throws Throwable {
		try {
			final Object obj = METHOD_GET_COMMON_SUFFIX.invoke(null, s1, s2);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMultimap() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		final PatternMap patternMap = new PatternMapImpl();
		//
		Assertions.assertNull(toMultimap(patternMap, null, null));
		//
		Assertions.assertNull(toMultimap(patternMap, IntObjectPair.of(ZERO, null), null));
		//
		Assertions.assertEquals("{家形文様=[いえがたもんよう], 文様=[もんよう], 家形=[いえがた]}", Objects.toString(toMultimap(patternMap,
				IntObjectPair.of(ZERO, "日本の伝統文様（もんよう）は美しく、心が和みます｡"), Arrays.asList(null, "家形文様（いえがたもんよう）"))));
		//
	}

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final IntObjectPair<String> iop,
			final Iterable<String> lines) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP.invoke(null, patternMap, iop, lines);
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

}