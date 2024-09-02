package com.google.common.collect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class MultimapUtilTest {

	private static class IH implements InvocationHandler {

		private Boolean remove = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Multimap) {
				//
				if (Objects.equals(methodName, "remove")) {
					//
					return remove;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Test
	void testPutAll() {
		//
		Assertions.assertDoesNotThrow(() -> MultimapUtil.putAll(null, null));
		//
		final Multimap<?, ?> multimap = LinkedHashMultimap.create();
		//
		Assertions.assertDoesNotThrow(() -> MultimapUtil.putAll(multimap, null));
		//
		Assertions.assertDoesNotThrow(() -> MultimapUtil.putAll(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> MultimapUtil.putAll(multimap, null, null));
		//
	}

	@Test
	void testContainsEntry() {
		//
		final Object k = "k", v = "v";
		//
		final Multimap<?, ?> multimap = ImmutableMultimap.of(k, v);
		//
		Assertions.assertFalse(MultimapUtil.containsEntry(multimap, null, null));
		//
		Assertions.assertTrue(MultimapUtil.containsEntry(multimap, k, v));
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = MultimapUtil.class.getDeclaredMethods();
		//
		Method m = null;
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
			invokeStaticMethod = Narcissus.invokeStaticMethod(m,
					toArray(Collections.nCopies(m.getParameterCount(), null)));
			//
			toString = Objects.toString(m);
			//
			if (ArrayUtils.contains(new Class<?>[] { Boolean.TYPE, Integer.TYPE }, m.getReturnType())) {
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

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testRemove() {
		///
		final IH ih = new IH();
		//
		ih.remove = Boolean.FALSE;
		//
		Assertions.assertDoesNotThrow(() -> MultimapUtil.remove(Reflection.newProxy(Multimap.class, ih), null, null));
		//
	}

}