package org.springframework.beans.factory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

class OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_INT_VALUE, METHOD_VALUE_OF, METHOD_TEST_AND_APPLY = null;

	private static final int ZERO = 0;

	@BeforeAll
	static void beforeClass() throws ReflectiveOperationException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_INT_VALUE = clz.getDeclaredMethod("intValue", Number.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_VALUE_OF = clz.getDeclaredMethod("valueOf", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		final Properties properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean.url")));
				//
			} // if
				//
			final File file = new File("OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean.txt");
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
	void testIntValue() throws Throwable {
		//
		final int one = 1;
		//
		Assertions.assertEquals(one, intValue(null, one));
		//
		Assertions.assertEquals(ZERO, intValue(Integer.valueOf(ZERO), one));
		//
	}

	private static int intValue(final Number instance, final int defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_INT_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Number) {
				return ((Number) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testValueOf() throws Throwable {
		//
		Assertions.assertNull(valueOf(null));
		//
		Assertions.assertNull(valueOf(""));
		//
		Assertions.assertNull(valueOf(" "));
		//
		Assertions.assertNull(valueOf("A"));
		//
		Assertions.assertEquals(Integer.valueOf(ZERO), valueOf(Integer.toString(ZERO)));
		//
	}

	private static Integer valueOf(final String instance) throws Throwable {
		try {
			final Object obj = METHOD_VALUE_OF.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
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

}