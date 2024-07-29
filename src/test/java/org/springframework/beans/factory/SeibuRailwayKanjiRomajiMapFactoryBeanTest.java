package org.springframework.beans.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class SeibuRailwayKanjiRomajiMapFactoryBeanTest {

	private static Method METHOD_CREATE_MAP, METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = SeibuRailwayKanjiRomajiMapFactoryBean.class;
		//
		(METHOD_CREATE_MAP = clz.getDeclaredMethod("createMap", List.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
	}

	private static class MH implements MethodHandler {

		private String text = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof Element) {
				//
				if (Objects.equals(methodName, "text")) {
					//
					return text;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private SeibuRailwayKanjiRomajiMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new SeibuRailwayKanjiRomajiMapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testCreateMap() throws Throwable {
		//
		Assertions.assertNull(createMap(Collections.singletonList(null)));
		//
		final Element element1 = cast(Element.class, Narcissus.allocateInstance(Element.class));
		//
		if (element1 != null) {
			//
			FieldUtils.writeField(element1, "childNodes",
					Collections.nCopies(2, cast(Element.class, Narcissus.allocateInstance(Element.class))), true);
			//
		} // if
			//
		Assertions.assertEquals(Collections.singletonMap(null, null), createMap(Collections.singletonList(element1)));
		//
		Assertions.assertEquals(Collections.singletonMap(null, null), createMap(Collections.nCopies(2, element1)));
		//
		final MH mh = new MH();
		//
		mh.text = "";
		//
		final Element childNode2 = ProxyUtil.createProxy(Element.class, mh, clz -> {
			//
			final Constructor<?> c = clz != null ? clz.getDeclaredConstructor(String.class) : null;
			//
			if (c != null) {
				//
				c.setAccessible(true);
				//
			} // if
				//
			return c != null ? c.newInstance("A") : null;
			//
		});
		//
		final Element element2 = cast(Element.class, Narcissus.allocateInstance(Element.class));
		//
		if (element2 != null) {
			//
			FieldUtils.writeField(element2, "childNodes",
					Arrays.asList(cast(Element.class, Narcissus.allocateInstance(Element.class)), childNode2), true);
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> createMap(Arrays.asList(element1, element2)));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static Map<String, String> createMap(final List<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MAP.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
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

}