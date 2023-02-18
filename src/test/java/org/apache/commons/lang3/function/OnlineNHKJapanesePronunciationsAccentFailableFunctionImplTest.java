package org.apache.commons.lang3.function;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class OnlineNHKJapanesePronunciationsAccentFailableFunctionImplTest {

	private static final int ONE = 1;

	private static Method METHOD_GET, METHOD_GET_SRC_MAP, METHOD_GET_CLASS, METHOD_TO_STRING, METHOD_GET_IMAGE_SRCS,
			METHOD_CREATE_MERGED_BUFFERED_IMAGE, METHOD_GET_GRAPHICS, METHOD_DRAW_IMAGE, METHOD_GET_WIDTH,
			METHOD_GET_HEIGHT, METHOD_INT_VALUE, METHOD_FOR_EACH, METHOD_SET_VALUE, METHOD_GET_VALUE, METHOD_ENTRY_SET,
			METHOD_GET_PROTOCOL, METHOD_GET_HOST, METHOD_TEST, METHOD_ADD, METHOD_FILTER, METHOD_TO_LIST,
			METHOD_GET_NAME, METHOD_GET_TYPE, METHOD_IS_ASSIGNABLE_FROM, METHOD_IS_PRIMITIVE, METHOD_AND = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = OnlineNHKJapanesePronunciationsAccentFailableFunctionImpl.class;
		//
		(METHOD_GET = clz.getDeclaredMethod("get", Field.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_SRC_MAP = clz.getDeclaredMethod("getSrcMap", Element.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_GET_IMAGE_SRCS = clz.getDeclaredMethod("getImageSrcs", Element.class)).setAccessible(true);
		//
		(METHOD_CREATE_MERGED_BUFFERED_IMAGE = clz.getDeclaredMethod("createMergedBufferedImage", String.class,
				List.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_GRAPHICS = clz.getDeclaredMethod("getGraphics", Image.class)).setAccessible(true);
		//
		(METHOD_DRAW_IMAGE = clz.getDeclaredMethod("drawImage", Graphics.class, Image.class, Integer.TYPE, Integer.TYPE,
				ImageObserver.class)).setAccessible(true);
		//
		(METHOD_GET_WIDTH = clz.getDeclaredMethod("getWidth", RenderedImage.class)).setAccessible(true);
		//
		(METHOD_GET_HEIGHT = clz.getDeclaredMethod("getHeight", RenderedImage.class)).setAccessible(true);
		//
		(METHOD_INT_VALUE = clz.getDeclaredMethod("intValue", Number.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_FOR_EACH = clz.getDeclaredMethod("forEach", Iterable.class, Consumer.class)).setAccessible(true);
		//
		(METHOD_SET_VALUE = clz.getDeclaredMethod("setValue", Entry.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_VALUE = clz.getDeclaredMethod("getValue", Entry.class)).setAccessible(true);
		//
		(METHOD_ENTRY_SET = clz.getDeclaredMethod("entrySet", Map.class)).setAccessible(true);
		//
		(METHOD_GET_PROTOCOL = clz.getDeclaredMethod("getProtocol", URL.class)).setAccessible(true);
		//
		(METHOD_GET_HOST = clz.getDeclaredMethod("getHost", URL.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_TO_LIST = clz.getDeclaredMethod("toList", Stream.class)).setAccessible(true);
		//
		(METHOD_GET_NAME = clz.getDeclaredMethod("getName", Member.class)).setAccessible(true);
		//
		(METHOD_GET_TYPE = clz.getDeclaredMethod("getType", Field.class)).setAccessible(true);
		//
		(METHOD_IS_ASSIGNABLE_FROM = clz.getDeclaredMethod("isAssignableFrom", Class.class, Class.class))
				.setAccessible(true);
		//
		(METHOD_IS_PRIMITIVE = clz.getDeclaredMethod("isPrimitive", Class.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Integer width, height, size = null;

		private Object key, value, selectedItem, get = null;

		private Set<Entry<?, ?>> entrySet = null;

		private Iterator<?> iterator = null;

		private Boolean hasNext, containsKey, isEmpty = null;

		private Component component = null;

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
			if (proxy instanceof Iterable) {
				//
				if (Objects.equals(methodName, "iterator")) {
					//
					return iterator;
					//
				} // if
					//
			} // if
				//
			if (proxy instanceof RenderedImage) {
				//
				if (Objects.equals(methodName, "getWidth")) {
					//
					return width;
					//
				} else if (Objects.equals(methodName, "getHeight")) {
					//
					return height;
					//
				} // if
					//
			} else if (proxy instanceof Entry) {
				//
				if (Objects.equals(methodName, "getValue") || Objects.equals(methodName, "setValue")) {
					//
					return value;
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "entrySet")) {
					//
					return entrySet;
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
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private static class MH implements MethodHandler {

		private Graphics graphics = null;

		private Boolean drawImage = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof Image) {
				//
				if (Objects.equals(methodName, "getGraphics")) {
					//
					return graphics;
					//
				} // if
					//
			} else if (self instanceof Graphics) {
				//
				if (Objects.equals(methodName, "drawImage")) {
					//
					return drawImage;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OnlineNHKJapanesePronunciationsAccentFailableFunctionImpl instance = null;

	private IH ih = null;

	private MH mh = null;

	private RenderedImage renderedImage = null;

	private Entry<?, ?> entry = null;

	@BeforeEach
	void beforeEach() throws Throwable {
		//
		if (!GraphicsEnvironment.isHeadless()) {
			//
			final Constructor<OnlineNHKJapanesePronunciationsAccentFailableFunctionImpl> constructor = OnlineNHKJapanesePronunciationsAccentFailableFunctionImpl.class
					.getDeclaredConstructor();
			//
			if (constructor != null) {
				//
				constructor.setAccessible(true);
				//
			} // if
				//
			instance = constructor != null ? constructor.newInstance() : null;
			//
		} else {
			//
			instance = cast(OnlineNHKJapanesePronunciationsAccentFailableFunctionImpl.class,
					Narcissus.allocateInstance(OnlineNHKJapanesePronunciationsAccentFailableFunctionImpl.class));
			//
		} // if
			//
		mh = new MH();
		//
		renderedImage = Reflection.newProxy(RenderedImage.class, ih = new IH());
		//
		entry = Reflection.newProxy(Entry.class, ih);
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	@Test
	void testApply() throws IOException {
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
	void testSetImageType() throws Throwable {
		//
		final Field imageType = OnlineNHKJapanesePronunciationsAccentFailableFunctionImpl.class
				.getDeclaredField("imageType");
		//
		Assertions.assertDoesNotThrow(() -> setImageType(instance, null));
		//
		Assertions.assertNull(get(imageType, instance));
		//
		// java.lang.Number
		//
		final Integer I = Integer.valueOf(ONE);
		//
		Assertions.assertDoesNotThrow(() -> setImageType(instance, I));
		//
		Assertions.assertEquals(I, get(imageType, instance));
		//
		// java.lang.String
		//
		Assertions.assertDoesNotThrow(() -> setImageType(instance, Integer.toString(ONE)));
		//
		Assertions.assertEquals(I, get(imageType, instance));
		//
		if (imageType != null) {
			//
			imageType.setAccessible(true);
			//
		} // if
			//
		set(imageType, instance, null);
		//
		Assertions.assertDoesNotThrow(() -> setImageType(instance, ""));
		//
		Assertions.assertNull(get(imageType, instance));
		//
		set(imageType, instance, null);
		//
		Assertions.assertThrows(NumberFormatException.class, () -> setImageType(instance, " "));
		//
		// char[]
		//
		set(imageType, instance, null);
		//
		final String string = "TYPE_INT_ARGB";
		//
		Assertions.assertDoesNotThrow(() -> setImageType(instance, string != null ? string.toCharArray() : null));
		//
		Assertions.assertEquals(FieldUtils.readDeclaredStaticField(BufferedImage.class, string),
				get(imageType, instance));
		//
	}

	private static void set(final Field field, final Object instance, final Object value)
			throws IllegalArgumentException, IllegalAccessException {
		if (field != null) {
			field.set(instance, value);
		}
	}

	private static void setImageType(final OnlineNHKJapanesePronunciationsAccentFailableFunctionImpl instance,
			final Object object) {
		if (instance != null) {
			instance.setImageType(object);
		}
	}

	@Test
	void testGet() throws Throwable {
		//
		Assertions.assertNull(get(null, null));
		//
	}

	private static Object get(final Field field, final Object instance) throws Throwable {
		try {
			return METHOD_GET.invoke(null, field, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetSrcMap() throws Throwable {
		//
		Assertions.assertNull(getSrcMap(null));
		//
	}

	private static Map<String, String> getSrcMap(final Element input) throws Throwable {
		try {
			final Object obj = METHOD_GET_SRC_MAP.invoke(null, input);
			if (obj == null) {
				return null;
			} else if (obj instanceof Map) {
				return (Map) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetClass() throws Throwable {
		//
		Assertions.assertNull(getClass(null));
		//
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
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
	void testGetImageSrcs() throws Throwable {
		//
		Assertions.assertNull(getImageSrcs(null));
		//
	}

	private static List<String> getImageSrcs(final Element element) throws Throwable {
		try {
			final Object obj = METHOD_GET_IMAGE_SRCS.invoke(null, element);
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
	void testCreateMergedBufferedImage() throws Throwable {
		//
		Assertions.assertNull(createMergedBufferedImage(null, null, 0));
		//
		Assertions.assertThrows(UncheckedIOException.class,
				() -> createMergedBufferedImage(null, Collections.singletonList(null), 0));
		//
	}

	private static BufferedImage createMergedBufferedImage(final String urlString, final List<String> srcs,
			final int imageType) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MERGED_BUFFERED_IMAGE.invoke(null, urlString, srcs, imageType);
			if (obj == null) {
				return null;
			} else if (obj instanceof BufferedImage) {
				return (BufferedImage) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetGraphics() throws Throwable {
		//
		Assertions.assertNull(getGraphics(null));
		//
		Assertions.assertNull(getGraphics(createProxy(Image.class, mh)));
		//
	}

	private static <T> T createProxy(final Class<T> superClass, final MethodHandler mh) throws Throwable {
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(superClass);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		final Object instance = constructor != null ? constructor.newInstance() : null;
		//
		if (instance instanceof ProxyObject) {
			//
			((ProxyObject) instance).setHandler(mh);
			//
		} // if
			//
		return (T) cast(clz, instance);
		//
	}

	private static Graphics getGraphics(final Image instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_GRAPHICS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Graphics) {
				return (Graphics) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testDrawImage() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> drawImage(null, null, 0, 0, null));
		//
		boolean b = true;
		//
		if (mh != null) {
			//
			mh.drawImage = Boolean.valueOf(b);
			//
		} // if
			//
		Assertions.assertEquals(b, drawImage(createProxy(Graphics.class, mh), null, 0, 0, null));
		//
		if (mh != null) {
			//
			mh.drawImage = Boolean.valueOf(b = false);
			//
		} // if
			//
		Assertions.assertEquals(b, drawImage(createProxy(Graphics.class, mh), null, 0, 0, null));
		//
	}

	private static boolean drawImage(final Graphics instance, final Image image, final int x, final int y,
			final ImageObserver observer) throws Throwable {
		try {
			final Object obj = METHOD_DRAW_IMAGE.invoke(null, instance, image, x, y, observer);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetWidth() throws Throwable {
		//
		Assertions.assertNull(getWidth(null));
		//
		if (ih != null) {
			//
			ih.width = ONE;
			//
		} // if
			//
		Assertions.assertEquals(ONE, getWidth(renderedImage));
		//
	}

	private static Integer getWidth(final RenderedImage instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_WIDTH.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetHeight() throws Throwable {
		//
		Assertions.assertNull(getHeight(null));
		//
		if (ih != null) {
			//
			ih.height = ONE;
			//
		} // if
			//
		Assertions.assertEquals(ONE, getHeight(renderedImage));
		//
	}

	private static Integer getHeight(final RenderedImage instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_HEIGHT.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIntValue() throws Throwable {
		//
		Assertions.assertEquals(ONE, intValue(null, ONE));
		//
		final int zero = 0;
		//
		Assertions.assertEquals(zero, intValue(Integer.valueOf(zero), ONE));
		//
	}

	private static int intValue(final Number instance, final int defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_INT_VALUE.invoke(null, instance, defaultValue);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForEach() {
		//
		Assertions.assertDoesNotThrow(() -> forEach(null, null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Collections.emptyList(), null));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Collections.singleton(null), x -> {
		}));
		//
		Assertions.assertDoesNotThrow(() -> forEach(Reflection.newProxy(Iterable.class, ih), null));
		//
	}

	private static <T> void forEach(final Iterable<T> instance, final Consumer<? super T> action) throws Throwable {
		try {
			METHOD_FOR_EACH.invoke(null, instance, action);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSetValue() {
		//
		Assertions.assertDoesNotThrow(() -> setValue(null, null));
		//
		Assertions.assertDoesNotThrow(() -> setValue(entry, null));
		//
	}

	private static <V> void setValue(final Entry<?, V> instance, final V value) throws Throwable {
		try {
			METHOD_SET_VALUE.invoke(null, instance, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetValue() throws Throwable {
		//
		Assertions.assertNull(getValue(null));
		//
		Assertions.assertNull(getValue(entry));
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
	void testEntrySet() throws Throwable {
		//
		Assertions.assertNull(entrySet(null));
		//
		Assertions.assertNull(entrySet(Reflection.newProxy(Map.class, ih)));
		//
	}

	private static <K, V> Set<Entry<K, V>> entrySet(final Map<K, V> instance) throws Throwable {
		try {
			final Object obj = METHOD_ENTRY_SET.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Set) {
				return (Set) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetProtocol() throws Throwable {
		//
		Assertions.assertNull(getProtocol(null));
		//
		final String protocol = "http";
		//
		Assertions.assertEquals(protocol, getProtocol(new URL(String.format("%1$s://www.google.com", protocol))));
		//
	}

	private static String getProtocol(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_PROTOCOL.invoke(null, instance);
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
	void testGetHost() throws Throwable {
		//
		Assertions.assertNull(getHost(null));
		//
		final String host = "www.google.com";
		//
		Assertions.assertEquals(host, getHost(new URL(String.format("http://%1$s", host))));
		//
	}

	private static String getHost(final URL instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_HOST.invoke(null, instance);
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
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null));
		//
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAdd() {
		//
		Assertions.assertDoesNotThrow(() -> add(null, null));
		//
		Assertions.assertDoesNotThrow(() -> add(new ArrayList<>(), null));
		//
	}

	private static <E> void add(final Collection<E> items, final E item) throws Throwable {
		try {
			METHOD_ADD.invoke(null, items, item);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilter() throws Throwable {
		//
		Assertions.assertNull(filter(null, null));
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
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsAssignableFrom() throws Throwable {
		//
		Assertions.assertFalse(isAssignableFrom(null, null));
		//
		Assertions.assertFalse(isAssignableFrom(Object.class, null));
		//
		Assertions.assertTrue(isAssignableFrom(Object.class, Object.class));
		//
	}

	private static boolean isAssignableFrom(final Class<?> a, final Class<?> b) throws Throwable {
		try {
			final Object obj = METHOD_IS_ASSIGNABLE_FROM.invoke(null, a, b);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsPrimitive() throws Throwable {
		//
		Assertions.assertFalse(isPrimitive(null));
		//
		Assertions.assertFalse(isPrimitive(Object.class));
		//
	}

	private static boolean isPrimitive(final Class<?> instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_PRIMITIVE.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAnd() throws Throwable {
		//
		Assertions.assertFalse(and(true, false));
		//
		Assertions.assertFalse(and(true, true, null));
		//
	}

	private static boolean and(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = METHOD_AND.invoke(null, a, b, bs);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}