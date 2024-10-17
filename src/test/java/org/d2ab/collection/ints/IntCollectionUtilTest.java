package org.d2ab.collection.ints;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class IntCollectionUtilTest {

	private IntCollection intCollection = null;

	@BeforeEach
	void beforeEach() {
		//
		intCollection = IntList.create();
		//
	}

	@Test
	void testAddInt() {
		//
		Assertions.assertDoesNotThrow(() -> IntCollectionUtil.addInt(intCollection, 0));
		//
	}

	@Test
	void testToIntArray() {
		//
		Assertions.assertArrayEquals(new int[] {}, IntCollectionUtil.toIntArray(intCollection));
		//
	}

	@Test
	void testAddAllInts() {
		//
		Assertions.assertDoesNotThrow(() -> IntCollectionUtil.addAllInts(intCollection, null));
		//
		Assertions.assertDoesNotThrow(() -> IntCollectionUtil.addAllInts(intCollection, intCollection));
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = IntCollectionUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Collection<Object> list = null;
		//
		Class<?>[] parameterTypes;
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
			Assertions.assertNull(Narcissus.invokeStaticMethod(m, toArray(list)), Objects.toString(m));
			//
		} // for
			//
	}

	private static int length(@Nullable final Object[] instance) {
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