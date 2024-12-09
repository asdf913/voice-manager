package org.d2ab.function;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class LanguageCodeToTextObjIntFunctionTest {

	private LanguageCodeToTextObjIntFunction instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new LanguageCodeToTextObjIntFunction();
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = LanguageCodeToTextObjIntFunction.class.getDeclaredMethods();
		//
		Method m = null;
		//
		List<Object> list = null;
		//
		Object[] os = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Object invoke;
		//
		String toString;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
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
				if (Objects.equals(parameterTypes[j], Integer.TYPE)) {
					//
					add(list, Integer.valueOf(0));
					//
				} else {
					//
					add(list, null);
					//
				} // if
					//
			} // for
				//
			os = toArray(list);
			//
			toString = Objects.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
			} else {
				//
				invoke = Narcissus.invokeMethod(instance, m, os);
				//
			} // if
				//
			if (ArrayUtils.contains(new Class<?>[] { Boolean.TYPE, }, m.getReturnType())) {
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