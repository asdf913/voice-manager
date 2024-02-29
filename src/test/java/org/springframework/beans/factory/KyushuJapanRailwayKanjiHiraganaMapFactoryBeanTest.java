package org.springframework.beans.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class KyushuJapanRailwayKanjiHiraganaMapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_CREATE_ENTRY_STRING, METHOD_CREATE_ENTRY_DOCUMENT = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = KyushuJapanRailwayKanjiHiraganaMapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_CREATE_ENTRY_STRING = clz.getDeclaredMethod("createEntry", String.class)).setAccessible(true);
		//
		(METHOD_CREATE_ENTRY_DOCUMENT = clz.getDeclaredMethod("createEntry", Document.class)).setAccessible(true);
		//
	}

	private static class MH implements MethodHandler {

		private Map<Object, Object> select = null;

		private Map<Object, Object> getSelect() {
			if (select == null) {
				select = new LinkedHashMap<>();
			}
			return select;
		}

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof Element) {
				//
				if (Objects.equals(methodName, "select") && args != null && args.length > 0) {
					//
					return MapUtils.getObject(getSelect(), args[0]);
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private KyushuJapanRailwayKanjiHiraganaMapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new KyushuJapanRailwayKanjiHiraganaMapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Map.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl("");
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl(" ");
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
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

	@Test
	void testCreateEntry() throws Throwable {
		//
		Assertions.assertNull(createEntry(""));
		//
		Assertions.assertNull(createEntry(" "));
		//
		final MH mh = new MH();
		//
		final Document document = createProxy(Document.class, mh, x -> {
			//
			final Constructor<?> constructor = getDeclaredConstructor(x, String.class);
			//
			if (constructor != null) {
				//
				constructor.setAccessible(true);
				//
			} // if
				//
			return Util.cast(Document.class, newInstance(constructor, ""));
			//
		});
		//
		Assertions.assertNull(createEntry(document));
		//
		final Elements elements = new Elements();
		//
		Util.put(mh.getSelect(), "div.box-station-name p.title", elements);
		//
		elements.add(null);
		//
		Assertions.assertEquals(Pair.of(null, null), createEntry(document));
		//
		Util.put(mh.getSelect(), "p.subtitle", elements);
		//
		Assertions.assertEquals(Pair.of(null, null), createEntry(document));
		//
		Util.put(mh.getSelect(), "p.subtitle", new Elements(Collections.nCopies(2, null)));
		//
		Assertions.assertThrows(IllegalStateException.class, () -> createEntry(document));
		//
		elements.add(null);
		//
		Assertions.assertThrows(IllegalStateException.class, () -> createEntry(document));
		//
	}

	private static <T> Constructor<T> getDeclaredConstructor(final Class<T> instance, final Class<?>... parameterTypes)
			throws NoSuchMethodException {
		return instance != null ? instance.getDeclaredConstructor(parameterTypes) : null;
	}

	private static <T> T newInstance(final Constructor<T> instance, final Object... initargs)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		return instance != null ? instance.newInstance(initargs) : null;
	}

	private static <T> T createProxy(final Class<T> superClass, final MethodHandler mh,
			final FailableFunction<Class<?>, T, Exception> function) throws Throwable {
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(superClass);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		Object instance = null;
		//
		if (function != null) {
			//
			instance = function != null ? function.apply(clz) : null;
		} else {
			//
			final Constructor<?> constructor = getDeclaredConstructor(clz);
			//
			if (constructor != null) {
				//
				constructor.setAccessible(true);
				//
			} // if
				//
			instance = newInstance(constructor);
			//
		} // if
			//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(mh);
			//
		} // if
			//
		return (T) Util.cast(clz, instance);
		//
	}

	private static Entry<String, String> createEntry(final String url) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_ENTRY_STRING.invoke(null, url);
			if (obj == null) {
				return null;
			} else if (obj instanceof Entry) {
				return (Entry) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Entry<String, String> createEntry(final Document document) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_ENTRY_DOCUMENT.invoke(null, document);
			if (obj == null) {
				return null;
			} else if (obj instanceof MutablePair) {
				return (MutablePair) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}