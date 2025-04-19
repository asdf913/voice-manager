package org.springframework.context.support;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.htmlunit.Page;
import org.htmlunit.SgmlPage;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.DomNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class VoiceManagerOnlineTtsPanelTest {

	private static Method METHOD_GET_LAYOUT_MANAGER, METHOD_TEST_AND_APPLY, METHOD_QUERY_SELECTOR,
			METHOD_GET_ELEMENTS_BY_TAG_NAME, METHOD_CLICK = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = VoiceManagerOnlineTtsPanel.class;
		//
		(METHOD_GET_LAYOUT_MANAGER = clz.getDeclaredMethod("getLayoutManager", Object.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_QUERY_SELECTOR = clz.getDeclaredMethod("querySelector", DomNode.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_ELEMENTS_BY_TAG_NAME = clz.getDeclaredMethod("getElementsByTagName", Document.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CLICK = clz.getDeclaredMethod("click", DomElement.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, @Nullable final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Document) {
				//
				if (Objects.equals(methodName, "getElementsByTagName")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static class MH implements MethodHandler {

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof DomNode) {
				//
				if (Objects.equals(methodName, "querySelector")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private VoiceManagerOnlineTtsPanel instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new VoiceManagerOnlineTtsPanel();
		//
		ih = new IH();
		//
	}

	@Test
	void testActionPerformed() {
		//
		if (instance == null) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> instance.actionPerformed(new ActionEvent("", 0, null)));
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = VoiceManagerOnlineTtsPanel.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Object[] os = null;
		//
		String toString = null;
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
			toString = Util.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
				//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeMethod(new VoiceManagerOnlineTtsPanel(), m, os), toString);
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
	void testGetLayoutManager() throws Throwable {
		//
		Assertions.assertNull(getLayoutManager(null, Collections.singleton(null)));
		//
		final LayoutManager layoutManager = Reflection.newProxy(LayoutManager.class, ih);
		//
		Assertions.assertNull(getLayoutManager(layoutManager, Collections.singleton(Pair.of(null, layoutManager))));
		//
	}

	private static LayoutManager getLayoutManager(final Object acbf, final Iterable<Entry<String, Object>> entrySet)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_LAYOUT_MANAGER.invoke(null, acbf, entrySet);
			if (obj == null) {
				return null;
			} else if (obj instanceof LayoutManager) {
				return (LayoutManager) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
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
	void testQuerySelector() throws Throwable {
		//
		Assertions.assertNull(querySelector(ProxyUtil.createProxy(DomNode.class, new MH(), clz -> {
			final Constructor<?> constructor = clz != null ? clz.getConstructor(SgmlPage.class) : null;
			if (constructor != null) {
				constructor.setAccessible(true);
			}
			return constructor != null ? constructor.newInstance((Object) null) : null;
		}), null));
		//
	}

	private static <N extends DomNode> N querySelector(final DomNode instance, final String selectors)
			throws Throwable {
		try {
			final Object obj = METHOD_QUERY_SELECTOR.invoke(null, instance, selectors);
			if (obj == null) {
				return null;
			} else if (obj instanceof DomNode) {
				return (N) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetElementsByTagName() throws Throwable {
		//
		Assertions.assertNull(getElementsByTagName(Reflection.newProxy(Document.class, ih), null));
		//
	}

	private static NodeList getElementsByTagName(final Document instance, final String tagname) throws Throwable {
		try {
			final Object obj = METHOD_GET_ELEMENTS_BY_TAG_NAME.invoke(null, instance, tagname);
			if (obj == null) {
				return null;
			} else if (obj instanceof NodeList) {
				return (NodeList) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testClick() throws Throwable {
		//
		Assertions.assertNull(click(Util.cast(DomElement.class, Narcissus.allocateInstance(DomElement.class))));
		//
	}

	private static <P extends Page> P click(final DomElement instance) throws Throwable {
		try {
			final Object obj = METHOD_CLICK.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Page) {
				return (P) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}