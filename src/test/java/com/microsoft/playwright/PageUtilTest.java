package com.microsoft.playwright;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class PageUtilTest {

	private static class IH implements InvocationHandler {

		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String name = getName(method);
			//
			if (proxy instanceof Page
					&& anyMatch(Stream.of("navigate", "querySelectorAll"), x -> Objects.equals(x, name))) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(name);
			//
		}

		private static <T> boolean anyMatch(final Stream<T> instance, final Predicate<? super T> predicate) {
			return instance != null && instance.anyMatch(predicate);
		}

		private static String getName(final Member instance) {
			return instance != null ? instance.getName() : null;
		}

	}

	private Page page = null;

	@BeforeEach
	void beforeEach() {
		//
		page = Reflection.newProxy(Page.class, new IH());
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = PageUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ArrayUtils.get(ms, i)) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
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
	void testNavigate() {
		//
		Assertions.assertNull(PageUtil.navigate(page, null));
		//
	}

	@Test
	void testQuerySelectorAll() {
		//
		Assertions.assertNull(PageUtil.querySelectorAll(page, null));
		//
	}

}