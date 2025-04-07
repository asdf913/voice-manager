package org.apache.commons.lang3.function;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlunit.ScriptResult;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;
import com.microsoft.playwright.Page;

import io.github.toolfactory.narcissus.Narcissus;

class FuriganaMueckeFailableFunctionTest {

	private static Method METHOD_TO_STRING, METHOD_CLICK, METHOD_CAST, METHOD_TEST_AND_APPLY, METHOD_GET_ATTRIBUTE,
			METHOD_SET_TEXT_CONTENT, METHOD_GET_JAVA_SCRIPT_RESULT, METHOD_EXECUTE_JAVA_SCRIPT, METHOD_FILTER,
			METHOD_GET_ELEMENTS_BY_TAG_NAME = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = FuriganaMueckeFailableFunction.class;
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_CLICK = clz.getDeclaredMethod("click", DomElement.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_ATTRIBUTE = clz.getDeclaredMethod("getAttribute", Element.class, String.class)).setAccessible(true);
		//
		(METHOD_SET_TEXT_CONTENT = clz.getDeclaredMethod("setTextContent", Node.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_JAVA_SCRIPT_RESULT = clz.getDeclaredMethod("getJavaScriptResult", ScriptResult.class))
				.setAccessible(true);
		//
		(METHOD_EXECUTE_JAVA_SCRIPT = clz.getDeclaredMethod("executeJavaScript", HtmlPage.class, String.class))
				.setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_GET_ELEMENTS_BY_TAG_NAME = clz.getDeclaredMethod("getElementsByTagName", HtmlPage.class, String.class))
				.setAccessible(true);
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
			if (Boolean.logicalOr(
					proxy instanceof Element && Objects.equals(methodName, "getAttribute")
							&& Arrays.equals(parameterTypes, new Class<?>[] { String.class }),
					proxy instanceof Stream && Objects.equals(methodName, "filter")
							&& Arrays.equals(parameterTypes, new Class<?>[] { Predicate.class }))) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private FuriganaMueckeFailableFunction instance = null;

	private IH ih = null;

	private HtmlPage htmlPage = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		instance = new FuriganaMueckeFailableFunction();
		//
		ih = new IH();
		//
		htmlPage = cast(HtmlPage.class, Narcissus.allocateInstance(HtmlPage.class));
		//
	}

	@Test
	void testApply() throws Exception {
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
	void testClick() {
		//
		Assertions
				.assertDoesNotThrow(() -> click(cast(DomElement.class, Narcissus.allocateInstance(DomElement.class))));
		//
	}

	private static <P extends Page> P click(final DomElement instance) throws Throwable {
		try {
			return (P) METHOD_CLICK.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T> T cast(final Class<T> clz, final Object instance) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, FailableFunction.identity(), null));
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
	void testGetAttribute() throws Throwable {
		//
		Assertions.assertNull(getAttribute(Reflection.newProxy(Element.class, ih), null));
		//
	}

	private static String getAttribute(final Element instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_ATTRIBUTE.invoke(null, instance, name);
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

	@Test
	void testSetTextContent() {
		//
		Assertions.assertDoesNotThrow(() -> setTextContent(Reflection.newProxy(Node.class, ih), null));
		//
	}

	private static void setTextContent(final Node instance, final String textContent) throws Throwable {
		try {
			METHOD_SET_TEXT_CONTENT.invoke(null, instance, textContent);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetJavaScriptResult() throws Throwable {
		//
		Assertions.assertNull(
				getJavaScriptResult(cast(ScriptResult.class, Narcissus.allocateInstance(ScriptResult.class))));
		//
	}

	private static Object getJavaScriptResult(final ScriptResult instance) throws Throwable {
		try {
			return METHOD_GET_JAVA_SCRIPT_RESULT.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExecuteJavaScript() throws Throwable {
		//
		Assertions.assertNull(executeJavaScript(htmlPage, null));
		//
	}

	private static ScriptResult executeJavaScript(final HtmlPage instance, final String sourceCode) throws Throwable {
		try {
			final Object obj = METHOD_EXECUTE_JAVA_SCRIPT.invoke(null, instance, sourceCode);
			if (obj == null) {
				return null;
			} else if (obj instanceof ScriptResult) {
				return (ScriptResult) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		final Stream<?> empty = Stream.empty();
		//
		Assertions.assertSame(empty, filter(empty, null));
		//
		Assertions.assertNull(filter(Reflection.newProxy(Stream.class, ih), null));
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = METHOD_FILTER.invoke(null, instance, predicate);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetElementsByTagName() throws Throwable {
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(getElementsByTagName(htmlPage, null), Collections.emptySet()));
		//
	}

	private static DomNodeList<DomElement> getElementsByTagName(final HtmlPage instance, final String tagname)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_ELEMENTS_BY_TAG_NAME.invoke(null, instance, tagname);
			if (obj == null) {
				return null;
			} else if (obj instanceof DomNodeList) {
				return (DomNodeList) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}