package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		(METHOD_TEST_AND_APPLY = OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean.class
				.getDeclaredMethod("testAndApply", Predicate.class, Object.class, FailableFunction.class,
						FailableFunction.class))
				.setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBeanTest.testGetObject");
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
		final Entry<Field, Object> entry = TestUtil.getFieldWithUrlAnnotationAndValue(
				OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean.class);
		//
		final Field url = Util.getKey(entry);
		//
		Narcissus.setObjectField(instance, url, "");
		//
		Assertions.assertNull(getObject(instance));
		//
		Narcissus.setObjectField(instance, url, " ");
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
		final Method[] ms = OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean.class
				.getDeclaredMethods();
		//
		Method m = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Assertions.assertNull(
					Narcissus.invokeStaticMethod(m, toArray(Collections.nCopies(m.getParameterCount(), null))),
					Objects.toString(m));
			//
		} // for
			//
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

}