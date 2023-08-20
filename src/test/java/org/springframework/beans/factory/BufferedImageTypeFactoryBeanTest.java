package org.springframework.beans.factory;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;

class BufferedImageTypeFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_IS_STATIC, METHOD_GET, METHOD_GET_TYPE,
			METHOD_IS_ASSIGNABLE_FROM, METHOD_IS_PRIMITIVE, METHOD_AND, METHOD_INT_VALUE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = BufferedImageTypeFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_IS_STATIC = clz.getDeclaredMethod("isStatic", Member.class)).setAccessible(true);
		//
		(METHOD_GET = clz.getDeclaredMethod("get", Field.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_TYPE = clz.getDeclaredMethod("getType", Field.class)).setAccessible(true);
		//
		(METHOD_IS_ASSIGNABLE_FROM = clz.getDeclaredMethod("isAssignableFrom", Class.class, Class.class))
				.setAccessible(true);
		//
		(METHOD_IS_PRIMITIVE = clz.getDeclaredMethod("isPrimitive", Class.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
		(METHOD_INT_VALUE = clz.getDeclaredMethod("intValue", Number.class, Integer.TYPE)).setAccessible(true);
		//
	}

	private BufferedImageTypeFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new BufferedImageTypeFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		// java.lang.Number
		//
		final int one = 1;
		//
		setValue(instance, Integer.valueOf(one));
		//
		Assertions.assertEquals(Integer.valueOf(one), getObject(instance));
		//
		// java.lang.CharSequence
		//
		final String empty = "";
		//
		setValue(instance, empty);
		//
		Assertions.assertNull(getObject(instance));
		//
		setValue(instance, empty.toCharArray());
		//
		Assertions.assertNull(getObject(instance));
		//
		setValue(instance, empty.getBytes());
		//
		Assertions.assertNull(getObject(instance));
		//
		setValue(instance, Integer.toString(one));
		//
		Assertions.assertEquals(Integer.valueOf(one), getObject(instance));
		//
		final String string = "TYPE_INT_ARGB";
		//
		setValue(instance, string);
		//
		Assertions.assertEquals(FieldUtils.readDeclaredStaticField(BufferedImage.class, string), getObject(instance));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instnace) throws Exception {
		return instnace != null ? instnace.getObject() : null;
	}

	private static void setValue(final BufferedImageTypeFactoryBean instance, final Object value) {
		if (instance != null) {
			instance.setValue(value);
			;
		}
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Integer.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysFalse(), null, null, null));
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
	void testIsStatic() throws Throwable {
		//
		Assertions.assertFalse(isStatic(null));
		//
	}

	private static boolean isStatic(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_STATIC.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
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

	private static Object get(final Field field, final Object instance) throws Throwable {
		try {
			return METHOD_GET.invoke(null, field, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetType() throws Throwable {
		//
		Assertions.assertNull(getType(null));
		//
	}

	private static Class<?> getType(final Field instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAssignableFrom() throws Throwable {
		//
		Assertions.assertFalse(isAssignableFrom(null, null));
		//
		Assertions.assertFalse(isAssignableFrom(Object.class, null));
		//
		Assertions.assertTrue(isAssignableFrom(Object.class, Object.class));
		//
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) throws Throwable {
		try {
			final Object obj = METHOD_IS_ASSIGNABLE_FROM.invoke(null, a, b);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsPrimitive() throws Throwable {
		//
		Assertions.assertFalse(isPrimitive(null));
		//
		Assertions.assertFalse(isPrimitive(Object.class));
		//
	}

	private static boolean isPrimitive(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_PRIMITIVE.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAnd() throws Throwable {
		//
		Assertions.assertFalse(and(true, false));
		//
		Assertions.assertTrue(and(true, true, null));
		//
	}

	private static boolean and(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = METHOD_AND.invoke(null, a, b, bs);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIntValue() throws Throwable {
		//
		final int integer = 0;
		//
		Assertions.assertEquals(integer, intValue(null, integer));
		//
	}

	private static int intValue(final Number instance, final int defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_INT_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}