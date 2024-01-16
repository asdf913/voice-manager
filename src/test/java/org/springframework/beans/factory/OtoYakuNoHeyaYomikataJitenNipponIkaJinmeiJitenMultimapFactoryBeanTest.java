package org.springframework.beans.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBeanTest {

	private static Method METHOD_CREATE_MULTI_MAP1, METHOD_CREATE_MULTI_MAP2, METHOD_TO_URL, METHOD_TEST_AND_APPLY,
			METHOD_REMOVE = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.class;
		//
		(METHOD_CREATE_MULTI_MAP1 = clz.getDeclaredMethod("createMultimap1", Iterable.class)).setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP2 = clz.getDeclaredMethod("createMultimap2", Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_URL = clz.getDeclaredMethod("toURL", URI.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_REMOVE = clz.getDeclaredMethod("remove", Multimap.class, Object.class, Object.class))
				.setAccessible(true);
		//
	}

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
			} else if (proxy instanceof Link) {
				//
				if (Objects.equals(methodName, "getText")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "getUrl")) {
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

	private OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean();
		//
		ih = new IH();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setText(null);
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singleton(null));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final Link link = Reflection.newProxy(Link.class, ih);
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singleton(link));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.nCopies(2, link));
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getObject(instance));
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setLinks(null);
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.url")));
				//
			} // if
				//
			Assertions.assertNotNull(getObject(instance));
			//
		} // if
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testCreateMultimap1() throws Throwable {
		//
		Assertions.assertNull(createMultimap1(
				Arrays.asList(null, Util.cast(Element.class, Narcissus.allocateInstance(Element.class)))));
		//
	}

	private static Multimap<String, String> createMultimap1(final Iterable<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP1.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMultimap2() throws Throwable {
		//
		Assertions.assertNull(createMultimap2(Collections.singleton(null)));
		//
	}

	private static Multimap<String, String> createMultimap2(final Iterable<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP2.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToURL() throws Throwable {
		//
		Assertions.assertNull(toURL(null));
		//
		final URI uri = Util.cast(URI.class, Narcissus.allocateInstance(URI.class));
		//
		Assertions.assertNull(toURL(uri));
		//
		Narcissus.setField(uri, URI.class.getDeclaredField("scheme"), "");
		//
		Assertions.assertThrows(MalformedURLException.class, () -> toURL(uri));
		//
	}

	private static URL toURL(final URI instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_URL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URL) {
				return (URL) obj;
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

	@Test
	void testRemove() {
		//
		final IH ih = new IH();
		//
		ih.remove = Boolean.TRUE;
		//
		Assertions.assertDoesNotThrow(() -> remove(Reflection.newProxy(Multimap.class, ih), null, null));
		//
	}

	private static void remove(final Multimap<?, ?> instance, final Object key, final Object value) throws Throwable {
		try {
			METHOD_REMOVE.invoke(null, instance, key, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}