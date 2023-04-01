package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.function.FailableFunction;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;

class MigLayoutFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_CAST, METHOD_GET_IVALUE0_OBJECT,
			METHOD_GET_IVALUE0_DOUBLE_ARRAY, METHOD_GET_IVALUE0_FLOAT_ARRAY, METHOD_GET_IVALUE0_BYTE_ARRAY,
			METHOD_GET_IVALUE0_BOOLEAN_ARRAY = null;

	private static Field FIELD_ARGUMENTS = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = MigLayoutFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_IVALUE0_OBJECT = clz.getDeclaredMethod("getIValue0", Object.class)).setAccessible(true);
		//
		(METHOD_GET_IVALUE0_DOUBLE_ARRAY = clz.getDeclaredMethod("getIValue0", double[].class)).setAccessible(true);
		//
		(METHOD_GET_IVALUE0_FLOAT_ARRAY = clz.getDeclaredMethod("getIValue0", float[].class)).setAccessible(true);
		//
		(METHOD_GET_IVALUE0_BYTE_ARRAY = clz.getDeclaredMethod("getIValue0", byte[].class)).setAccessible(true);
		//
		(METHOD_GET_IVALUE0_BOOLEAN_ARRAY = clz.getDeclaredMethod("getIValue0", boolean[].class)).setAccessible(true);
		//
		(FIELD_ARGUMENTS = clz.getDeclaredField("arguments")).setAccessible(true);
		//
	}

	private MigLayoutFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new MigLayoutFactoryBean();
		//
	}

	@Test
	void testSetArguments1() throws ReflectiveOperationException {
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments(new short[] { 1 }));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { "1" }, get(FIELD_ARGUMENTS, instance)));
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments(new long[] { 1 }));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { "1" }, get(FIELD_ARGUMENTS, instance)));
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments(new double[] { 1 }));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { "1.0" }, get(FIELD_ARGUMENTS, instance)));
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments(new float[] { 1 }));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { "1.0" }, get(FIELD_ARGUMENTS, instance)));
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments(new byte[] { 1 }));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { "1" }, get(FIELD_ARGUMENTS, instance)));
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments(new boolean[] { true }));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { "true" }, get(FIELD_ARGUMENTS, instance)));
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments(new Object[] { null, "" }));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { null, "" }, get(FIELD_ARGUMENTS, instance)));
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments(""));
		//
		Assertions.assertNull(get(FIELD_ARGUMENTS, instance));
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments("[1,2]"));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { "1", "2" }, get(FIELD_ARGUMENTS, instance)));
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments("1"));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { "1" }, get(FIELD_ARGUMENTS, instance)));
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments("true"));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { "true" }, get(FIELD_ARGUMENTS, instance)));
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments("\"\""));
		//
		Assertions.assertTrue(Objects.deepEquals(new String[] { "" }, get(FIELD_ARGUMENTS, instance)));
		//
	}

	@Test
	void testSetArguments2() throws ReflectiveOperationException, IOException {
		//
		Assertions.assertDoesNotThrow(() -> instance.setArguments("null"));
		//
		Assertions.assertNull(get(FIELD_ARGUMENTS, instance));
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalArgumentException.class,
				"{localizedMessage=class java.util.LinkedHashMap, message=class java.util.LinkedHashMap}",
				() -> instance.setArguments("{}"));
		//
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertEquals(
				"MigLayout[colConstraints=,lastSize=0,layoutConstraints=,rowConstraints=,scrConstrMap={}]",
				ToStringBuilder.reflectionToString(instance.getObject(), ToStringStyle.SHORT_PREFIX_STYLE));
		//
		instance.setArguments(new String[] {});
		//
		Assertions.assertEquals(
				"MigLayout[colConstraints=,lastSize=0,layoutConstraints=,rowConstraints=,scrConstrMap={}]",
				ToStringBuilder.reflectionToString(instance.getObject(), ToStringStyle.SHORT_PREFIX_STYLE));
		//
		instance.setArguments(new String[] { "" });
		//
		Assertions.assertEquals(
				"MigLayout[colConstraints=,lastSize=0,layoutConstraints=,rowConstraints=,scrConstrMap={}]",
				ToStringBuilder.reflectionToString(instance.getObject(), ToStringStyle.SHORT_PREFIX_STYLE));
		//
		instance.setArguments(new String[] { "", "" });
		//
		Assertions.assertEquals(
				"MigLayout[colConstraints=,lastSize=0,layoutConstraints=,rowConstraints=,scrConstrMap={}]",
				ToStringBuilder.reflectionToString(instance.getObject(), ToStringStyle.SHORT_PREFIX_STYLE));
		//
		instance.setArguments(new String[] { "", "", "" });
		//
		Assertions.assertEquals(
				"MigLayout[colConstraints=,lastSize=0,layoutConstraints=,rowConstraints=,scrConstrMap={}]",
				ToStringBuilder.reflectionToString(instance.getObject(), ToStringStyle.SHORT_PREFIX_STYLE));
		//
		instance.setArguments(new String[] { "", "", "", "" });
		//
		AssertionsUtil.assertThrowsAndEquals(IllegalStateException.class,
				"{localizedMessage=java.lang.reflect.Constructor is null, message=java.lang.reflect.Constructor is null}",
				() -> instance.getObject());
		//
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, x -> x));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, x -> x));
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
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
		Assertions.assertNull(cast(Object.class, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object value) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetIValue0() throws Throwable {
		//
		Assertions.assertNull(getIValue0((Object) null));
		//
		Assertions.assertEquals(Unit.with(null), getIValue0((double[]) null));
		//
		Assertions.assertEquals(Unit.with(null), getIValue0((float[]) null));
		//
		Assertions.assertEquals(Unit.with(null), getIValue0((byte[]) null));
		//
		Assertions.assertEquals(Unit.with(null), getIValue0((boolean[]) null));
		//
	}

	private static IValue0<Object> getIValue0(final Object value) throws Throwable {
		try {
			final Object obj = METHOD_GET_IVALUE0_OBJECT.invoke(null, value);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static IValue0<Object> getIValue0(final double[] ds) throws Throwable {
		try {
			final Object obj = METHOD_GET_IVALUE0_DOUBLE_ARRAY.invoke(null, ds);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static IValue0<Object> getIValue0(final float[] fs) throws Throwable {
		try {
			final Object obj = METHOD_GET_IVALUE0_FLOAT_ARRAY.invoke(null, fs);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static IValue0<Object> getIValue0(final byte[] bs) throws Throwable {
		try {
			final Object obj = METHOD_GET_IVALUE0_BYTE_ARRAY.invoke(null, bs);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static IValue0<String[]> getIValue0(final boolean[] bs) throws Throwable {
		try {
			final Object obj = METHOD_GET_IVALUE0_BOOLEAN_ARRAY.invoke(null, bs);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

}