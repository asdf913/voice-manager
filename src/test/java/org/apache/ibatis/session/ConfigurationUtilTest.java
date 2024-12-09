package org.apache.ibatis.session;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class ConfigurationUtilTest {

	private static Method METHOD_CAST = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		(METHOD_CAST = ConfigurationUtil.class.getDeclaredMethod("cast", Class.class, Object.class))
				.setAccessible(true);
		//
	}

	@Test
	void testGetMapper() throws Throwable {
		//
		Assertions.assertNull(ConfigurationUtil.getMapper(null, null, null));
		//
		Assertions.assertNull(ConfigurationUtil
				.getMapper(cast(Configuration.class, Narcissus.allocateInstance(Configuration.class)), null, null));
		//
		Assertions.assertNull(ConfigurationUtil.getMapper(new Configuration(), null, null));
		//
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testNull() {
		//
		final Method[] ms = ConfigurationUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		List<Object> list = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Object invoke = null;
		//
		String toString = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			if ((list = ObjectUtils.getIfNull(list, ArrayList::new)) != null) {
				//
				list.clear();
				//
			} // if
				//
			for (int j = 0; (parameterTypes = m.getParameterTypes()) != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterTypes[j], Boolean.TYPE)) {
					//
					add(list, Boolean.FALSE);
					//
				} else {
					//
					add(list, null);
					//
				} // if
					//
			} // for
				//
			invoke = Narcissus.invokeStaticMethod(m, toArray(list));
			//
			toString = Objects.toString(m);
			//
			if (Objects.equals(m.getReturnType(), Boolean.TYPE)) {
				//
				Assertions.assertNotNull(invoke, toString);
				//
			} else {
				//
				Assertions.assertNull(invoke, toString);
				//
			} // if
				//
		} // for
			//
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

}