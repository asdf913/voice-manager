package org.springframework.context.support;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.swing.JLabel;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableSupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class JapanDictGuiTest {

	private static Method METHOD_SET_VISIBLE, METHOD_TEST_AND_GET = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = JapanDictGui.class;
		//
		(METHOD_SET_VISIBLE = Util.getDeclaredMethod(clz, "setVisible", Component.class, Boolean.TYPE))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_GET = Util.getDeclaredMethod(clz, "testAndGet", Boolean.TYPE, FailableSupplier.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean test;

		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String name = Util.getName(method);
			//
			if (proxy instanceof Predicate) {
				//
				if (Objects.equals(name, "test")) {
					//
					return test;
					//
				} // if
					//
			} else if (proxy instanceof Function) {
				//
				if (Objects.equals(name, "apply")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(name);
			//
		}

	}

	private JapanDictGui instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = Util.cast(JapanDictGui.class, Narcissus.allocateInstance(JapanDictGui.class));
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = JapanDictGui.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType, returnType = null;
		//
		Collection<Object> collection = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		Object result = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // for
				//
			os = toArray(collection);
			//
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				result = Narcissus.invokeStaticMethod(m, os);
				//
				if (Boolean.logicalAnd(isPrimitive(returnType = Util.getReturnType(m)),
						!Objects.equals(returnType, Void.TYPE))) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} else {
				//
				Assertions
						.assertNull(
								Narcissus
										.invokeMethod(
												instance = ObjectUtils.getIfNull(instance,
														() -> Util.cast(JapanDictGui.class,
																Narcissus.allocateInstance(JapanDictGui.class))),
												m, os),
								toString);
				//
			} // if
				//
		} // for
			//
	}

	private static boolean isPrimitive(final Class<?> instance) {
		return instance != null && instance.isPrimitive();
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testNonNull() {
		//
		final Method[] ms = JapanDictGui.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType, returnType = null;
		//
		Collection<Object> collection = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		Object result = null;
		//
		IH ih = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Util.clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = ArrayUtils.get(parameterTypes, j), Boolean.TYPE)) {
					//
					Util.add(collection, Boolean.FALSE);
					//
				} else if (Objects.equals(parameterType, Integer.TYPE)) {
					//
					Util.add(collection, Integer.valueOf(0));
					//
				} else if (isArray(parameterType)) {
					//
					Util.add(collection, Array.newInstance(componentType(parameterType), 0));
					//
				} else if (isInterface(parameterType)) {
					//
					if (ih == null) {
						//
						(ih = new IH()).test = Boolean.FALSE;
						//
					} // if
						//
					Util.add(collection, Reflection.newProxy(parameterType, ih));
					//
				} else if (parameterType != null && !Modifier.isAbstract(parameterType.getModifiers())) {
					//
					Util.add(collection, Narcissus.allocateInstance(parameterType));
					//
				} else {
					//
					Util.add(collection, null);
					//
				} // if
					//
			} // for
				//
			os = toArray(collection);
			//
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				result = Narcissus.invokeStaticMethod(m, os);
				//
				if (Boolean.logicalAnd(isPrimitive(returnType = Util.getReturnType(m)),
						!Objects.equals(returnType, Void.TYPE))) {
					//
					Assertions.assertNotNull(result, toString);
					//
				} else {
					//
					Assertions.assertNull(result, toString);
					//
				} // if
					//
			} else {
				//
				Assertions
						.assertNull(
								Narcissus
										.invokeMethod(
												instance = ObjectUtils.getIfNull(instance,
														() -> Util.cast(JapanDictGui.class,
																Narcissus.allocateInstance(JapanDictGui.class))),
												m, os),
								toString);
				//
			} // if
				//
		} // for
			//
	}

	private static Class<?> componentType(final Class<?> instance) {
		return instance != null ? instance.componentType() : null;
	}

	private static boolean isInterface(final Class<?> instance) {
		return instance != null && instance.isInterface();
	}

	private static boolean isArray(final Class<?> instance) {
		return instance != null && instance.isArray();
	}

	@Test
	void testActionPerformed() {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent("", 0, null)));
		//
	}

	@Test
	void testSetVisible() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_SET_VISIBLE, null, new JLabel(), Boolean.TRUE));
		//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Test
	void testTestAndGet() throws IllegalAccessException, InvocationTargetException {
		//
		Assertions.assertNull(invoke(METHOD_TEST_AND_GET, null, Boolean.TRUE, null));
		//
	}

}