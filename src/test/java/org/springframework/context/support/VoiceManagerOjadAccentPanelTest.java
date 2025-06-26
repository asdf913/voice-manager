package org.springframework.context.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.github.toolfactory.narcissus.Narcissus;

class VoiceManagerOjadAccentPanelTest {

	private static Method METHOD_GET_FILE_EXTENSIONS, METHOD_FIND_MATCH, METHOD_QUERY_SELECTOR_ALL = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = VoiceManagerOjadAccentPanel.class;
		//
		(METHOD_GET_FILE_EXTENSIONS = clz.getDeclaredMethod("getFileExtensions", ContentInfo.class))
				.setAccessible(true);
		//
		(METHOD_FIND_MATCH = clz.getDeclaredMethod("findMatch", ContentInfoUtil.class, byte[].class))
				.setAccessible(true);
		//
		(METHOD_QUERY_SELECTOR_ALL = clz.getDeclaredMethod("querySelectorAll", Page.class, String.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof ElementHandle) {
				//
				if (Objects.equals(methodName, "screenshot")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Playwright) {
				//
				if (Objects.equals(methodName, "chromium")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Browser) {
				//
				if (Objects.equals(methodName, "newPage")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Page && Objects.equals(methodName, "querySelectorAll")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		ih = new IH();
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerOjadAccentPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic() || Objects.equals(Util.getName(m), "main")
					&& Arrays.equals(Util.getParameterTypes(m), new Class<?>[] { String[].class })) {
				//
				continue;
				//
			} // if
				//
			Assertions.assertNull(
					Narcissus.invokeStaticMethod(m, Util.toArray(Collections.nCopies(m.getParameterCount(), null))),
					Objects.toString(m));
			//
		} // for
			//
	}

	@Test
	void testMethodWithOneInterfaceParameter() {
		//
		final Method[] ms = VoiceManagerOjadAccentPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || (parameterTypes = Util.getParameterTypes(m)) == null
					|| parameterTypes.length != 1 || (parameterType = ArrayUtils.get(parameterTypes, 0)) == null
					|| !Modifier.isInterface(parameterType.getModifiers())) {
				//
				continue;
				//
			} // if
				//
			Assertions.assertNull(
					Narcissus.invokeStaticMethod(m, new Object[] { Reflection.newProxy(parameterType, ih) }),
					Objects.toString(m));
			//
		} // for
			//
	}

	@Test
	void testGetFileExtensions() throws Throwable {
		//
		Assertions.assertNull(
				getFileExtensions(Util.cast(ContentInfo.class, Narcissus.allocateInstance(ContentInfo.class))));
		//
	}

	private static String[] getFileExtensions(final ContentInfo instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_FILE_EXTENSIONS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String[]) {
				return (String[]) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFindMatch() throws Throwable {
		//
		final ContentInfoUtil cic = new ContentInfoUtil();
		//
		Assertions.assertNull(findMatch(cic, null));
		//
		Assertions.assertSame(ContentInfo.EMPTY_INFO, findMatch(cic, new byte[] {}));
		//
	}

	private static ContentInfo findMatch(final ContentInfoUtil instance, final byte[] bytes) throws Throwable {
		try {
			final Object obj = METHOD_FIND_MATCH.invoke(null, instance, bytes);
			if (obj == null) {
				return null;
			} else if (obj instanceof ContentInfo) {
				return (ContentInfo) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testQuerySelectorAll() throws Throwable {
		//
		Assertions.assertNull(querySelectorAll(Reflection.newProxy(Page.class, ih), null));
		//
	}

	private static List<ElementHandle> querySelectorAll(final Page instance, final String selector) throws Throwable {
		try {
			final Object obj = METHOD_QUERY_SELECTOR_ALL.invoke(null, instance, selector);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}