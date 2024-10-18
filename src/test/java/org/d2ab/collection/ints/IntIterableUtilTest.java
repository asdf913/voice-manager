package org.d2ab.collection.ints;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class IntIterableUtilTest {

	private static final int ZERO = 0;

	@Test
	void testContainsInt() {
		//
		final IntCollection intCollection = IntList.create();
		//
		Assertions.assertFalse(IntIterableUtil.containsInt(intCollection, ZERO));
		//
		IntCollectionUtil.addInt(intCollection, ZERO);
		//
		Assertions.assertTrue(IntIterableUtil.containsInt(intCollection, ZERO));
		//
	}

	@Test
	void testRemoveInt() {
		//
		Assertions.assertDoesNotThrow(() -> IntIterableUtil.removeInt(IntList.create(), ZERO));
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = IntIterableUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Collection<Object> list = null;
		//
		Class<?>[] parameterTypes;
		//
		Object invokeStaticMethod;
		//
		String toString;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			clear(list = ObjectUtils.getIfNull(list, ArrayList::new));
			//
			for (int j = 0; j < length(parameterTypes = m.getParameterTypes()); j++) {
				//
				if (Objects.equals(Integer.TYPE, parameterTypes[j])) {
					//
					list.add(Integer.valueOf(0));
					//
				} else {
					//
					list.add(null);
					//
				} // if
					//
			} // for
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(list));
			//
			toString = Objects.toString(m);
			//
			if (Objects.equals(Boolean.TYPE, m.getReturnType())) {
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

	private static int length(final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	private void clear(final Collection<Object> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

}