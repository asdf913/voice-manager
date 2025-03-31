package org.springframework.context.support;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerRubyHtmlPanelTest {

	private static Method METHOD_LENGTH, METHOD_GET_ACTUAL_TYPE_ARGUMENTS, METHOD_GET_RAW_TYPE, METHOD_GET_GENERIC_TYPE,
			METHOD_GET_GENERIC_INTERFACES, METHOD_TEST_AND_APPLY, METHOD_GET_LAYOUT_MANAGER = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = VoiceManagerRubyHtmlPanel.class;
		//
		(METHOD_LENGTH = clz.getDeclaredMethod("length", Object[].class)).setAccessible(true);
		//
		(METHOD_GET_ACTUAL_TYPE_ARGUMENTS = clz.getDeclaredMethod("getActualTypeArguments", ParameterizedType.class))
				.setAccessible(true);
		//
		(METHOD_GET_RAW_TYPE = clz.getDeclaredMethod("getRawType", ParameterizedType.class)).setAccessible(true);
		//
		(METHOD_GET_GENERIC_TYPE = clz.getDeclaredMethod("getGenericType", Field.class)).setAccessible(true);
		//
		(METHOD_GET_GENERIC_INTERFACES = clz.getDeclaredMethod("getGenericInterfaces", Class.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_LAYOUT_MANAGER = clz.getDeclaredMethod("getLayoutManager", Object.class, Iterable.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Type[] actualTypeArguments = null;

		private Type rawType = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof ParameterizedType) {
				//
				if (Objects.equals(methodName, "getActualTypeArguments")) {
					//
					return actualTypeArguments;
					//
				} else if (Objects.equals(methodName, "getRawType")) {
					//
					return rawType;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private VoiceManagerRubyHtmlPanel instance = null;

	private IH ih = null;

	private ParameterizedType parameterizedType = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerRubyHtmlPanel();
		//
		parameterizedType = Reflection.newProxy(ParameterizedType.class, ih = new IH());
		//
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
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(null));
		//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent(new Object(), 0, null)));
		//
	}

	@Test
	void testNull() throws Throwable {
		//
		final Method[] ms = VoiceManagerRubyHtmlPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		List<Object> list = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		Object invoke = null;
		//
		for (int i = 0; i < length(ms); i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			if ((list = ObjectUtils.getIfNull(list, ArrayList::new)) != null
					&& (parameterTypes = m.getParameterTypes()) != null) {
				//
				list.clear();
				//
				for (final Class<?> parameterType : parameterTypes) {
					//
					if (Objects.equals(parameterType, Double.TYPE)) {
						//
						list.add(Double.valueOf(0));
						//
					} else if (Objects.equals(parameterType, Boolean.TYPE)) {
						//
						list.add(Boolean.TRUE);
						//
					} else {
						//
						list.add(null);
						//
					} // if
						//
				} // for
					//
			} // if
				//
			os = toArray(list);
			//
			toString = Objects.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (Util.contains(Arrays.asList(Integer.TYPE, Double.TYPE), m.getReturnType())) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
					//
			} else {
				//
				invoke = Narcissus.invokeMethod(instance, m, os);
				//
				if (Objects.equals(m.getReturnType(), Double.TYPE) || Objects.equals(Util.getName(m), "getTitle")) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
			} // if
				//
		} // for
			//
	}

	private static int length(final Object[] instance) throws Throwable {
		try {
			final Object obj = METHOD_LENGTH.invoke(null, (Object) instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testGetActualTypeArguments() throws Throwable {
		//
		Assertions.assertNull(getActualTypeArguments(parameterizedType));
		//
	}

	private static Type[] getActualTypeArguments(final ParameterizedType instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_ACTUAL_TYPE_ARGUMENTS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Type[]) {
				return (Type[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetGenericType() throws Throwable {
		//
		Assertions.assertNull(getGenericType(Util.cast(Field.class, Narcissus.allocateInstance(Field.class))));
		//
	}

	private static Type getGenericType(final Field instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_GENERIC_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Type) {
				return (Type) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetRawType() throws Throwable {
		//
		Assertions.assertNull(getRawType(parameterizedType));
		//
	}

	private static Type getRawType(final ParameterizedType instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_RAW_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Type) {
				return (Type) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetGenericInterfaces() throws Throwable {
		//
		Assertions.assertArrayEquals(new Class<?>[] {}, getGenericInterfaces(Object.class));
		//
	}

	private static Type[] getGenericInterfaces(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_GENERIC_INTERFACES.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Type[]) {
				return (Type[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
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
	void testGetLayoutManager() throws Throwable {
		//
		Assertions.assertNull(getLayoutManager(null, Util.entrySet(Collections.singletonMap(null, null))));
		//
		final Iterable<Entry<String, Object>> entrySet = Util
				.entrySet(Collections.singletonMap(null, Reflection.newProxy(LayoutManager.class, ih)));
		//
		Assertions.assertNull(getLayoutManager(null, entrySet));
		//
		Assertions.assertNull(getLayoutManager(Util.cast(DefaultSingletonBeanRegistry.class,
				Narcissus.allocateInstance(DefaultSingletonBeanRegistry.class)), entrySet));
		//
	}

	private static LayoutManager getLayoutManager(final Object acbf, final Iterable<Entry<String, Object>> entrySet)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_LAYOUT_MANAGER.invoke(null, acbf, entrySet);
			if (obj == null) {
				return null;
			} else if (obj instanceof LayoutManager) {
				return (LayoutManager) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}