package org.apache.commons.lang3.function;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.github.toolfactory.narcissus.Narcissus;

class FuriganaMueckeFailableFunctionTest {

	private static Method METHOD_NEW_PAGE, METHOD_TO_STRING, METHOD_CHROMIUM, METHOD_LOCATOR, METHOD_EVALUATE,
			METHOD_CLICK, METHOD_FILL, METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = FuriganaMueckeFailableFunction.class;
		//
		(METHOD_NEW_PAGE = clz.getDeclaredMethod("newPage", Browser.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_CHROMIUM = clz.getDeclaredMethod("chromium", Playwright.class)).setAccessible(true);
		//
		(METHOD_LOCATOR = clz.getDeclaredMethod("locator", Page.class, String.class)).setAccessible(true);
		//
		(METHOD_EVALUATE = clz.getDeclaredMethod("evaluate", Locator.class, String.class)).setAccessible(true);
		//
		(METHOD_CLICK = clz.getDeclaredMethod("click", Locator.class)).setAccessible(true);
		//
		(METHOD_FILL = clz.getDeclaredMethod("fill", Locator.class, String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class, Function.class,
				Function.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(method != null ? method.getReturnType() : null, Void.TYPE)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = method != null ? method.getName() : null;
			//
			final Class<?>[] parameterTypes = method != null ? method.getParameterTypes() : null;
			//
			if (or(proxy instanceof Playwright && Objects.equals(methodName, "chromium")
					&& Arrays.equals(parameterTypes, new Class<?>[] {}),
					proxy instanceof Browser && Objects.equals(methodName, "newPage")
							&& Arrays.equals(parameterTypes, new Class<?>[] {}),
					proxy instanceof Locator && Objects.equals(methodName, "evaluate")
							&& Arrays.equals(parameterTypes, new Class<?>[] { String.class }),
					proxy instanceof Page && Objects.equals(methodName, "locator")
							&& Arrays.equals(parameterTypes, new Class<?>[] { String.class }))) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

		private static boolean or(final boolean a, final boolean b, final boolean... bs) {
			//
			boolean result = a || b;
			//
			if (result) {
				//
				return result;
				//
			} // if
				//
			for (int i = 0; bs != null && i < bs.length; i++) {
				//
				if (result |= bs[i]) {
					//
					return result;
					//
				} // if
					//
			} // for
				//
			return result;
			//
		}

	}

	private FuriganaMueckeFailableFunction instance = null;

	private IH ih = null;

	private Page page = null;

	private Locator locator = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new FuriganaMueckeFailableFunction();
		//
		page = Reflection.newProxy(Page.class, ih = new IH());
		//
		locator = Reflection.newProxy(Locator.class, ih);
		//
	}

	@Test
	void testApply() {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		instance.setUrl("");
		//
		Assertions.assertNull(FailableFunctionUtil.apply(instance, null));
		//
		instance.setUrl(" ");
		//
		Assertions.assertNull(FailableFunctionUtil.apply(instance, null));
		//
		instance.setUrl("a");
		//
		Assertions.assertNull(FailableFunctionUtil.apply(instance, null));
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = FuriganaMueckeFailableFunction.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Object[] os = null;
		//
		String toString = null;
		//
		Object invokeStaticMethod = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			os = toArray(Collections.nCopies(m.getParameterCount(), null));
			//
			toString = Objects.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invokeStaticMethod = Narcissus.invokeStaticMethod(m, os);
				//
				if (Objects.equals(m.getReturnType(), Boolean.TYPE)) {
					//
					Assertions.assertNotNull(invokeStaticMethod, toString);
					//
				} else {
					//
					Assertions.assertNull(invokeStaticMethod, toString);
					//
				} // if
					//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeMethod(instance, m, os), toString);
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
	void testNewPage() throws Throwable {
		//
		Assertions.assertNull(newPage(Reflection.newProxy(Browser.class, ih)));
		//
	}

	private static Page newPage(final Browser instance) throws Throwable {
		try {
			final Object obj = METHOD_NEW_PAGE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Page) {
				return (Page) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToString() throws Throwable {
		//
		Assertions.assertSame(StringUtils.EMPTY, toString(StringUtils.EMPTY));
		//
	}

	private static String toString(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_STRING.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Test
	void testChromium() throws Throwable {
		//
		Assertions.assertNull(chromium(Reflection.newProxy(Playwright.class, ih)));
		//
	}

	private static BrowserType chromium(final Playwright instance) throws Throwable {
		try {
			final Object obj = METHOD_CHROMIUM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof BrowserType) {
				return (BrowserType) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testLocator() throws Throwable {
		//
		Assertions.assertNull(locator(page, null));
		//
	}

	private static Locator locator(final Page instance, final String selector) throws Throwable {
		try {
			final Object obj = METHOD_LOCATOR.invoke(null, instance, selector);
			if (obj == null) {
				return null;
			} else if (obj instanceof Locator) {
				return (Locator) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testEvaluate() throws Throwable {
		//
		Assertions.assertNull(evaluate(locator, null));
		//
	}

	private static Object evaluate(final Locator instance, final String expression) throws Throwable {
		try {
			return METHOD_EVALUATE.invoke(null, instance, expression);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testClick() {
		//
		Assertions.assertDoesNotThrow(() -> click(locator));
		//
	}

	private static void click(final Locator instance) throws Throwable {
		try {
			METHOD_CLICK.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFill() {
		//
		Assertions.assertDoesNotThrow(() -> fill(locator, null));
		//
	}

	private static void fill(final Locator instance, final String value) throws Throwable {
		try {
			METHOD_FILL.invoke(null, instance, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, Function.identity(), null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final Function<T, R> functionTrue, final Function<T, R> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}