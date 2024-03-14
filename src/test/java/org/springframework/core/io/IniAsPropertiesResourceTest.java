package org.springframework.core.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.swing.JComboBox;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.javatuples.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Functions;
import org.meeuw.functional.Predicates;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.PropertyResolver;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class IniAsPropertiesResourceTest {

	private static final String EMPTY = "";

	private static Method METHOD_GET_SECTION, METHOD_TO_STRING, METHOD_TEST_AND_APPLY4, METHOD_TEST_AND_APPLY5,
			METHOD_FILTER, METHOD_TO_LIST, METHOD_GET_NAME, METHOD_TEST_AND_ACCEPT, METHOD_IS_STATIC,
			METHOD_TO_INPUT_STREAM, METHOD_CAST, METHOD_GET_TYPE, METHOD_GET_KEY, METHOD_GET_VALUE, METHOD_EXISTS,
			METHOD_TO_ARRAY, METHOD_READY, METHOD_GET_SELECTED_ITEM, METHOD_CONTAINS = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = IniAsPropertiesResource.class;
		//
		(METHOD_GET_SECTION = clz.getDeclaredMethod("getSection", Boolean.TYPE, Map.class, PropertyResolver.class,
				Console.class, Collection.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				BiFunction.class, BiFunction.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_TO_LIST = clz.getDeclaredMethod("toList", Stream.class)).setAccessible(true);
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				FailableConsumer.class)).setAccessible(true);
		//
		(METHOD_IS_STATIC = clz.getDeclaredMethod("isStatic", Member.class)).setAccessible(true);
		//
		(METHOD_TO_INPUT_STREAM = clz.getDeclaredMethod("toInputStream", Properties.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_TYPE = clz.getDeclaredMethod("getType", Field.class)).setAccessible(true);
		//
		(METHOD_GET_KEY = clz.getDeclaredMethod("getKey", Entry.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", Entry.class)).setAccessible(true);
		//
		(METHOD_EXISTS = clz.getDeclaredMethod("exists", File.class)).setAccessible(true);
		//
		(METHOD_TO_ARRAY = clz.getDeclaredMethod("toArray", Collection.class)).setAccessible(true);
		//
		(METHOD_READY = clz.getDeclaredMethod("ready", Reader.class)).setAccessible(true);
		//
		(METHOD_GET_SELECTED_ITEM = clz.getDeclaredMethod("getSelectedItem", JComboBox.class)).setAccessible(true);
		//
		(METHOD_CONTAINS = clz.getDeclaredMethod("contains", Collection.class, Object.class)).setAccessible(true);
		//
	}

	private class IH implements InvocationHandler {

		private InputStream inputStream = null;

		private Map<Object, Object> properties = null;

		private Map<Object, Object> getProperties() {
			if (properties == null) {
				properties = new LinkedHashMap<>();
			}
			return properties;
		}

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			if (Objects.equals(Void.TYPE, method != null ? method.getReturnType() : null)) {
				//
				return null;
				//
			} // if
				//
			final String methodName = getName(method);
			//
			if (proxy instanceof Resource) {
				//
				if (Objects.equals(methodName, "getInputStream")) {
					//
					return inputStream;
					//
				} else if (Objects.equals(methodName, "getFilename")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "filter")) {
					//
					return proxy;
					//
				} // if
					//
			} else if (proxy instanceof PropertyResolver) {
				//
				if (Objects.equals(methodName, "containsProperty") && args != null && args.length > 0) {
					//
					return containsKey(getProperties(), args[0]);
					//
				} else if (Objects.equals(methodName, "getProperty") && args != null && args.length > 0) {
					//
					return MapUtils.getObject(getProperties(), args[0]);
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

		private static boolean containsKey(final Map<?, ?> instance, final Object key) {
			return instance != null && instance.containsKey(key);
		}

	}

	private static class MH implements MethodHandler {

		private Boolean ready = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = getName(thisMethod);
			//
			if (self instanceof Reader) {
				if (Objects.equals(methodName, "ready")) {
					return ready;
				}
			}
			//
			throw new Throwable(methodName);
			//
		}

	}

	private IniAsPropertiesResource instance = null;

	private IH ih = null;

	private Resource resource = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new IniAsPropertiesResource(null);
		//
		resource = Reflection.newProxy(Resource.class, ih = new IH());
		//
	}

	@Test
	void testGetURL() throws IOException {
		//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class, "{}", () -> getURL(instance));
		//
	}

	private static URL getURL(final Resource instance) throws IOException {
		return instance != null ? instance.getURL() : null;
	}

	@Test
	void testGetURI() throws IOException {
		//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class, "{}", () -> getURI(instance));
		//
	}

	private static URI getURI(final Resource instance) throws IOException {
		return instance != null ? instance.getURI() : null;
	}

	@Test
	void testGetFile() throws IOException {
		//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class, "{}", () -> getFile(instance));
		//
	}

	private static File getFile(final Resource instance) throws IOException {
		return instance != null ? instance.getFile() : null;
	}

	@Test
	void testContentLength() throws IOException {
		//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class, "{}", () -> contentLength(instance));
		//
	}

	private static long contentLength(final Resource instance) throws IOException {
		return instance != null ? instance.contentLength() : null;
	}

	@Test
	void testLastModified() throws IOException {
		//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class, "{}", () -> lastModified(instance));
		//
	}

	private static long lastModified(final Resource instance) throws IOException {
		return instance != null ? instance.lastModified() : null;
	}

	@Test
	void testCreateRelative() throws IOException {
		//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class, "{}",
				() -> createRelative(instance, null));
		//
	}

	private static Resource createRelative(final Resource instance, final String relativePath) throws IOException {
		return instance != null ? instance.createRelative(relativePath) : null;
	}

	@Test
	void testGetDescription() throws IOException {
		//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class, "{}", () -> getDescription(instance));
		//
	}

	private static String getDescription(final Resource instance) {
		return instance != null ? instance.getDescription() : null;
	}

	@Test
	void testGetInputStream() throws Throwable {
		//
		Assertions.assertNotNull(new IniAsPropertiesResource(null).getInputStream());
		//
		instance = new IniAsPropertiesResource(resource);
		//
		Assertions.assertNotNull(InputStreamSourceUtil.getInputStream(instance));
		//
		try (final InputStream is = new ByteArrayInputStream(EMPTY.getBytes())) {
			//
			if (ih != null) {
				//
				ih.inputStream = is;
				//
			} // if
				//
			Assertions.assertNotNull(InputStreamSourceUtil.getInputStream(instance));
			//
		} // try
			//
		Assertions.assertNotNull(new IniAsPropertiesResource(testAndApply(Objects::nonNull,
				getClass().getResource("/applicationContext.xml"), UrlResource::new, null)).getInputStream());
		//
		try (final InputStream is = new ByteArrayInputStream(EMPTY.getBytes())) {
			//
			if (instance != null) {
				//
				instance.setApplicationEventPublisher(Reflection.newProxy(ApplicationEventPublisher.class, ih));
				//
			} // if
				//
			if (ih != null) {
				//
				ih.inputStream = is;
				//
			} // if
				//
			Assertions.assertNotNull(InputStreamSourceUtil.getInputStream(instance));
			//
		} // try
			//
	}

	@Test
	void testGetFilename() throws IOException {
		//
		Assertions.assertNull(new IniAsPropertiesResource(null).getFilename());
		//
		Assertions.assertNull(new IniAsPropertiesResource(resource).getFilename());
		//
	}

	@Test
	void testGetSection() throws Throwable {
		//
		Assertions.assertNull(getSection(true, null, null, null, null));
		//
		// org.springframework.core.env.PropertyResolver
		//
		final PropertyResolver propertyResolver = Reflection.newProxy(PropertyResolver.class, ih);
		//
		Assertions.assertNull(getSection(true, null, propertyResolver, null, null));
		//
		if (ih != null && ih.getProperties() != null) {
			//
			ih.getProperties().put("profile", "");
			//
		} // if
			//
		Assertions.assertEquals(Unit.with(""), getSection(true, null, propertyResolver, null, null));
		//
		// java.io.Console
		//
		Console console = System.console();
		//
		if (console == null) {
			//
			console = cast(Console.class, Narcissus.allocateInstance(Console.class));
			//
		} // if
			//
		Assertions.assertNull(getSection(true, null, null, console, null));
		//
		// java.io.Console.writeLock
		//
		List<Field> fs = Arrays.stream(Console.class.getDeclaredFields())
				.filter(f -> f != null && Objects.equals(f.getName(), "writeLock")).toList();
		//
		Field f = fs != null && fs.size() == 1 ? fs.get(0) : null;
		//
		if (f != null) {
			//
			Narcissus.setObjectField(console, f, new Object());
			//
			Assertions.assertNull(getSection(true, null, null, console, null));
			//
		} // if
			//
			// java.io.Console.readLock
			//
		if ((f = (fs = Arrays.stream(Console.class.getDeclaredFields())
				.filter(x -> x != null && Objects.equals(x.getName(), "readLock")).toList()) != null && fs.size() == 1
						? fs.get(0)
						: null) != null) {
			//
			Narcissus.setObjectField(console, f, new Object());
			//
			Assertions.assertNull(getSection(true, null, null, console, null));
			//
		} // if
			//
			// java.io.Console.pw
			//
		if ((f = (fs = Arrays.stream(Console.class.getDeclaredFields())
				.filter(x -> x != null && Objects.equals(x.getName(), "pw")).toList()) != null && fs.size() == 1
						? fs.get(0)
						: null) != null) {
			//
			try (final OutputStream os = new ByteArrayOutputStream(); final PrintWriter pw = new PrintWriter(os)) {
				//
				Narcissus.setObjectField(console, f, pw);
				//
			} // try
				//
		} // if
			//
		Assertions.assertNull(getSection(true, null, null, console, null));
		//
		// java.io.Console.rcb
		//
		if ((f = (fs = Arrays.stream(Console.class.getDeclaredFields())
				.filter(x -> x != null && Objects.equals(x.getName(), "rcb")).toList()) != null && fs.size() == 1
						? fs.get(0)
						: null) != null) {
			//
			Narcissus.setObjectField(console, f, new char[1]);
			//
			Assertions.assertNull(getSection(true, null, null, console, null));
			//
		} // if
			//
			// java.io.Console.reader
			//
		if ((f = (fs = Arrays.stream(Console.class.getDeclaredFields())
				.filter(x -> x != null && Objects.equals(x.getName(), "reader")).toList()) != null && fs.size() == 1
						? fs.get(0)
						: null) != null) {
			//
			try (final Reader reader = new StringReader(EMPTY)) {
				//
				Narcissus.setObjectField(console, f, reader);
				//
			} // try
				//
			Assertions.assertNull(getSection(true, null, null, console, null));
			//
			try (final Reader reader = new StringReader(EMPTY)) {
				//
				Narcissus.setObjectField(console, f, reader);
				//
				Assertions.assertEquals("[null]", toString(getSection(true, null, null, console, null)));
				//
			} // try
				//
		} // if
			//
		Assertions.assertNull(getSection(true, Collections.singletonMap(null, null), null, null, null));
		//
		final Map<?, ?> map = Collections.singletonMap("profile", null);
		//
		Assertions.assertEquals("[null]", toString(getSection(true, map, null, null, null)));
		//
		Assertions.assertEquals("[null]", toString(getSection(true, map, null, null, Collections.emptySet())));
		//
		Assertions.assertEquals("[null]", toString(getSection(true, map, null, null, Collections.singleton(null))));
		//
		Assertions.assertEquals("[null]",
				toString(getSection(true, map, null, null, new ArrayList<>(Collections.singleton("1")))));
		//
	}

	private static Unit<String> getSection(final boolean headless, final Map<?, ?> map,
			PropertyResolver propertyResolver, final Console console, final Collection<?> collection) throws Throwable {
		try {
			final Object obj = METHOD_GET_SECTION.invoke(null, headless, map, propertyResolver, console, collection);
			if (obj == null) {
				return null;
			} else if (obj instanceof Unit) {
				return (Unit) obj;
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
	void testToString() throws Throwable {
		//
		Assertions.assertNull(toString(null));
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

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(null, null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.biAlwaysTrue(), null, null, Functions.biAlways(null), null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY4.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U, R, E extends Throwable> R testAndApply(final BiPredicate<T, U> predicate, final T t,
			final U u, final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY5.invoke(null, predicate, t, u, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertNull(filter(null, null));
		//
		Assertions.assertNull(filter(Stream.empty(), null));
		//
		final Stream<?> steram = Reflection.newProxy(Stream.class, ih);
		//
		Assertions.assertSame(steram, filter(steram, null));
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
	void testToList() throws Throwable {
		//
		Assertions.assertNull(toList(null));
		//
	}

	private static <T> List<T> toList(final Stream<T> instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_LIST.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetName() throws Throwable {
		//
		Assertions.assertNull(getName(null));
		//
	}

	private static String getName(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_NAME.invoke(null, instance);
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
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
		//
	}

	private static <T, E extends Throwable> void testAndAccept(final Predicate<T> predicate, final T value,
			final FailableConsumer<T, E> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsStatic() throws Throwable {
		//
		Assertions.assertFalse(isStatic(null));
		//
		Assertions.assertTrue(isStatic(Objects.class.getDeclaredMethod("toString", Object.class)));
		//
	}

	private static boolean isStatic(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_STATIC.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToInputStream() throws Throwable {
		//
		Assertions.assertNotNull(toInputStream(null));
		//
	}

	private static InputStream toInputStream(final Properties properties) throws Throwable {
		try {
			final Object obj = METHOD_TO_INPUT_STREAM.invoke(null, properties);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStream) {
				return (InputStream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetType() throws Throwable {
		//
		Assertions.assertNull(getType(null));
		//
	}

	private static Class<?> getType(final Field instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetKey() throws Throwable {
		//
		Assertions.assertNull(getKey(null));
		//
	}

	private static <K> K getKey(final Entry<K, ?> instance) throws Throwable {
		try {
			return (K) METHOD_GET_KEY.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue() throws Throwable {
		//
		Assertions.assertNull(getValue(null));
		//
	}

	private static <V> V getValue(final Entry<?, V> instance) throws Throwable {
		try {
			return (V) METHOD_GET_VALUE.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExists() throws Throwable {
		//
		AssertionsUtil.assertThrowsAndEquals(UnsupportedOperationException.class, "{}", () -> exists(instance));
		//
		Assertions.assertFalse(exists((File) null));
		//
		Assertions.assertFalse(exists(new File("non_exists")));
		//
	}

	private static boolean exists(final Resource instance) {
		return instance != null && instance.exists();
	}

	private static boolean exists(final File instance) throws Throwable {
		try {
			final Object obj = METHOD_EXISTS.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToArray() throws Throwable {
		//
		Assertions.assertNull(toArray(null));
		//
		Assertions.assertArrayEquals(new Object[] {}, toArray(Collections.emptySet()));
		//
	}

	private static Object[] toArray(final Collection<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_ARRAY.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Object[]) {
				return (Object[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testReady() throws Throwable {
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(Reader.class);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		final List<Constructor<?>> cs = toList(
				filter(testAndApply(Objects::nonNull, clz != null ? clz.getDeclaredConstructors() : null,
						Arrays::stream, null), x -> x != null && x.getParameterCount() == 0));
		//
		final Constructor<?> c = testAndApply(x -> IterableUtils.size(x) == 1, cs, x -> IterableUtils.get(x, 0), null);
		//
		final Object instance = c != null ? c.newInstance() : null;
		//
		final MH mh = new MH();
		//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(mh);
			//
		} // if
			//
		Assertions.assertSame(mh.ready = Boolean.FALSE, Boolean.valueOf(ready(cast(Reader.class, instance))));
		//
	}

	private static boolean ready(final Reader instance) throws Throwable {
		try {
			final Object obj = METHOD_READY.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSelectedItem() throws Throwable {
		//
		Assertions.assertNull(getSelectedItem(null));
		//
		Assertions.assertNull(getSelectedItem(new JComboBox<>()));
		//
	}

	private static Object getSelectedItem(final JComboBox<?> instance) throws Throwable {
		try {
			return METHOD_GET_SELECTED_ITEM.invoke(null, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(contains(null, null));
		//
		final Collection<?> collection = Collections.singleton(EMPTY);
		//
		Assertions.assertFalse(contains(collection, null));
		//
		Assertions.assertTrue(contains(collection, EMPTY));
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS.invoke(null, items, item);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}