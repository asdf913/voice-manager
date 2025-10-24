package org.apache.bcel.classfile;

import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class MethodUtilTest {

	@Test
	void testNull() {
		//
		final java.lang.reflect.Method[] ms = MethodUtil.class.getDeclaredMethods();
		//
		java.lang.reflect.Method m = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Assertions.assertNull(
					Narcissus.invokeStaticMethod(m, toArray(Collections.nCopies(m.getParameterCount(), null))),
					Objects.toString(m));
			//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testGetArgumentTypes() throws Exception {
		//
		Assert.assertNull(MethodUtil.getArgumentTypes(cast(Method.class, Narcissus.allocateInstance(Method.class))));
		//
		final Class<?> clz = Object.class;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(StringUtils.join("/", Strings.CS.replace(clz.getName(), ".", "/"), ".class"))
				: null) {
			//
			final JavaClass javaClass = ClassParserUtil.parse(new ClassParser(is, null));
			//
			final Iterable<Method> ms = Arrays.stream(JavaClassUtil.getMethods(javaClass))
					.filter(m -> m != null && Objects.equals(FieldOrMethodUtil.getName(m), "toString")
							&& length(MethodUtil.getArgumentTypes(m)) == 0)
					.toList();
			//
			if (IterableUtils.size(ms) > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			Assert.assertArrayEquals(new Object[] {},
					MethodUtil.getArgumentTypes(IterableUtils.size(ms) == 1 ? IterableUtils.get(ms, 0) : null));
			//
		} // try
			//
	}

	private static int length(final Object[] instance) {
		return instance != null ? instance.length : 0;
	}

	private static <T> T cast(final Class<T> clz, final Object value) {
		return clz != null && clz.isInstance(value) ? clz.cast(value) : null;
	}

}