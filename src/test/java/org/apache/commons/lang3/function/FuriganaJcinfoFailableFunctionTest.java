package org.apache.commons.lang3.function;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class FuriganaJcinfoFailableFunctionTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_ADD_PARAMTER, METHOD_GET_CLASS, METHOD_COLLECT, METHOD_FILTER,
			METHOD_MAP, METHOD_BUILD, METHOD_TO_URL, METHOD_GET_ELEMENTS_BY_CLASS, METHOD_STREAM = null;

	@BeforeAll
	static void beforeAll() throws Throwable {
		//
		final Class<?> clz = FuriganaJcinfoFailableFunction.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_ADD_PARAMTER = clz.getDeclaredMethod("addParameter", URIBuilder.class, String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_COLLECT = clz.getDeclaredMethod("collect", Stream.class, Collector.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_MAP = clz.getDeclaredMethod("map", Stream.class, Function.class)).setAccessible(true);
		//
		(METHOD_BUILD = clz.getDeclaredMethod("build", URIBuilder.class)).setAccessible(true);
		//
		(METHOD_TO_URL = clz.getDeclaredMethod("toURL", URI.class)).setAccessible(true);
		//
		(METHOD_GET_ELEMENTS_BY_CLASS = clz.getDeclaredMethod("getElementsByClass", Element.class, String.class))
				.setAccessible(true);
		//
		(METHOD_STREAM = clz.getDeclaredMethod("stream", Collection.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Stream) {
				//
				if (ArrayUtils.contains(new Object[] { "collect", "filter", "map" }, methodName)) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Collection) {
				//
				if (Objects.equals(methodName, "stream")) {
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

	private FuriganaJcinfoFailableFunction instance = null;

	private IH ih = null;

	private Stream<?> stream = null;

	private Element element = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new FuriganaJcinfoFailableFunction();
		//
		stream = Reflection.newProxy(Stream.class, ih = new IH());
		//
		element = cast(Element.class, Narcissus.allocateInstance(Element.class));
		//
	}

	@Test
	void testApply() throws Exception {
		//
		Assertions.assertNull(FailableFunctionUtil.apply(instance, null));
		//
		if (instance != null) {
			//
			instance.setUrl("");
			//
		} // if
			//
		Assertions.assertNull(FailableFunctionUtil.apply(instance, null));
		//
	}

	@Test
	void testNull() throws Throwable {
		//
		final Class<?> clz = FuriganaJcinfoFailableFunction.class;
		//
		final Method[] ms = getDeclaredMethods(clz);
		//
		Method m = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Class<?> parameterType = null;
		//
		Collection<Object> collection = null;
		//
		Object invoke = null;
		//
		String toString = null;
		//
		Object[] os = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			if ((collection = ObjectUtils.getIfNull(collection, ArrayList::new)) != null) {
				//
				collection.clear();
				//
			} // if
				//
			parameterTypes = m.getParameterTypes();
			//
			for (int j = 0; parameterTypes != null && j < parameterTypes.length; j++) {
				//
				if (Objects.equals(parameterType = parameterTypes[j], Integer.TYPE)) {
					//
					add(collection, Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Boolean.TYPE)) {
					//
					add(collection, Boolean.FALSE);
					//
				} else {
					//
					add(collection, null);
					//
				} // if
					//
			} // if
				//
			os = toArray(collection);
			//
			toString = Objects.toString(m);
			//
			if (Modifier.isStatic(m.getModifiers())) {
				//
				invoke = Narcissus.invokeStaticMethod(m, os);
				//
				if (contains(Arrays.asList(Integer.TYPE, Boolean.TYPE), m.getReturnType())) {
					//
					Assertions.assertNotNull(invoke, toString);
					//
				} else {
					//
					Assertions.assertNull(invoke, toString);
					//
				} // if
					//
			} else {
				//
				Assertions.assertNull(
						Narcissus.invokeMethod(
								instance = ObjectUtils.getIfNull(instance, FuriganaJcinfoFailableFunction::new), m, os),
						toString);
				//
			} // if
				//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	private static <E> void add(final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	private static boolean contains(final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
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
	void testAddParameter() throws Throwable {
		//
		final URIBuilder uriBuilder = cast(URIBuilder.class, Narcissus.allocateInstance(URIBuilder.class));
		//
		Assertions.assertSame(uriBuilder, addParameter(uriBuilder, null, null));
		//
		Assertions.assertSame(uriBuilder, addParameter(uriBuilder, "", null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	private static URIBuilder addParameter(final URIBuilder instance, final String param, final String value)
			throws Throwable {
		try {
			final Object obj = METHOD_ADD_PARAMTER.invoke(null, instance, param, value);
			if (obj == null) {
				return null;
			} else if (obj instanceof URIBuilder) {
				return (URIBuilder) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCollect() throws Throwable {
		//
		final Stream<?> stream = Stream.empty();
		//
		Assertions.assertNull(collect(stream, null));
		//
		Assertions.assertEquals(Collections.emptyList(), collect(stream, Collectors.toList()));
		//
		Assertions.assertNull(collect(stream, null));
		//
	}

	private static <T, R, A> R collect(final Stream<T> instance, final Collector<? super T, A, R> collector)
			throws Throwable {
		try {
			return (R) METHOD_COLLECT.invoke(null, instance, collector);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertNull(filter(stream, null));
		//
		final Stream<?> stream = Stream.empty();
		//
		Assertions.assertSame(stream, filter(stream, null));
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
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMap() throws Throwable {
		//
		Assertions.assertNull(map(stream, null));
		//
		final Stream<?> stream = Stream.empty();
		//
		Assertions.assertNull(map(stream, null));
		//
		Assertions.assertNotNull(map(stream, Function.identity()));
		//
	}

	private static <T, R> Stream<R> map(final Stream<T> instance, final Function<? super T, ? extends R> mapper)
			throws Throwable {
		try {
			final Object obj = METHOD_MAP.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testBuild() throws Throwable {
		//
		Assertions.assertNotNull(build(cast(URIBuilder.class, Narcissus.allocateInstance(URIBuilder.class))));
		//
	}

	private static URI build(final URIBuilder instance) throws Throwable {
		try {
			final Object obj = METHOD_BUILD.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URI) {
				return (URI) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToURL() throws Throwable {
		//
		Assertions.assertNull(toURL(cast(URI.class, Narcissus.allocateInstance(URI.class))));
		//
		Assertions.assertNotNull(toURL(toURI(toFile(Path.of(".")))));
		//
	}

	private static URI toURI(final File instance) {
		return instance != null ? instance.toURI() : null;
	}

	private static File toFile(final Path instance) {
		return instance != null ? instance.toFile() : null;
	}

	private static URL toURL(final URI instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_URL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URL) {
				return (URL) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetElementsByClass() throws Throwable {
		//
		Assertions.assertNull(getElementsByClass(null, null));
		//
		Assertions.assertNull(getElementsByClass(element, null));
		//
		Assertions.assertNull(getElementsByClass(element, " "));
		//
		Assertions.assertNotNull(getElementsByClass(new Element("a"), " "));
		//
	}

	private static Elements getElementsByClass(final Element instance, final String className) throws Throwable {
		try {
			final Object obj = METHOD_GET_ELEMENTS_BY_CLASS.invoke(null, instance, className);
			if (obj == null) {
				return null;
			} else if (obj instanceof Elements) {
				return (Elements) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSteram() throws Throwable {
		//
		Assertions.assertNull(stream(Reflection.newProxy(Collection.class, ih)));
		//
	}

	private static <E> Stream<E> stream(final Collection<E> instance) throws Throwable {
		try {
			final Object obj = METHOD_STREAM.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}