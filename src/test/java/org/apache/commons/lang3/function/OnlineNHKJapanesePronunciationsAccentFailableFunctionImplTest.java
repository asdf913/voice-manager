package org.apache.commons.lang3.function;

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
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

	private static final int ZERO = 0, ONE = 1;

	private static final String EMPTY = "";

	private static Method METHOD_GET_SRC_MAP, METHOD_TO_STRING, METHOD_GET_CLASS, METHOD_GET_IMAGE_SRCS,
			METHOD_CREATE_MERGED_BUFFERED_IMAGE, METHOD_GET_GRAPHICS, METHOD_DRAW_IMAGE, METHOD_GET_WIDTH,
			METHOD_GET_HEIGHT, METHOD_INT_VALUE, METHOD_FOR_EACH, METHOD_SET_VALUE, METHOD_GET_VALUE, METHOD_ENTRY_SET,
			METHOD_GET_PROTOCOL, METHOD_GET_HOST, METHOD_TEST, METHOD_ADD, METHOD_MAP_TO_INT, METHOD_REDUCE,
			METHOD_OR_ELSE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = OnlineNHKJapanesePronunciationsAccentFailableFunctionImpl.class;
		//
		(METHOD_GET_SRC_MAP = clz.getDeclaredMethod("getSrcMap", Element.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
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
		(METHOD_MAP_TO_INT = clz.getDeclaredMethod("mapToInt", Stream.class, ToIntFunction.class)).setAccessible(true);
		//
		(METHOD_REDUCE = clz.getDeclaredMethod("reduce", IntStream.class, IntBinaryOperator.class)).setAccessible(true);
		//
		(METHOD_OR_ELSE = clz.getDeclaredMethod("orElse", OptionalInt.class, Integer.TYPE)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Integer width, height = null;

		private Object value = null;

		private Set<Entry<?, ?>> entrySet = null;

		private Iterator<?> iterator = null;

		private IntStream intStream = null;

		private OptionalInt optionalInt = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
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
				if (Objects.equals(methodName, "mapToInt")) {
					//
					return intStream;
					//
				} // if
					//
			} else if (proxy instanceof IntStream) {
				//
				if (Objects.equals(methodName, "reduce")) {
					//
					return optionalInt;
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
			instance.setUrl(EMPTY);
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
		if (imageType != null) {
			//
			imageType.setAccessible(true);
			//
		} // if
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
	}

	private static void setImageType(final OnlineNHKJapanesePronunciationsAccentFailableFunctionImpl instance,
			final Integer imageType) {
		if (instance != null) {
			instance.setImageType(imageType);
		}
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
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
	void testToString() throws Throwable {
		//
		Assertions.assertNull(toString(null));
		//
		Assertions.assertSame(EMPTY, toString(EMPTY));
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
		Assertions.assertNull(createMergedBufferedImage(null, null, ZERO));
		//
		final List<String> list = Collections.singletonList(null);
		//
		Assertions.assertThrows(UncheckedIOException.class, () -> createMergedBufferedImage(null, list, ZERO));
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
		Assertions.assertDoesNotThrow(() -> drawImage(null, null, ZERO, ZERO, null));
		//
		boolean b = true;
		//
		if (mh != null) {
			//
			mh.drawImage = Boolean.valueOf(b);
			//
		} // if
			//
		Assertions.assertEquals(b, drawImage(createProxy(Graphics.class, mh), null, ZERO, ZERO, null));
		//
		if (mh != null) {
			//
			mh.drawImage = Boolean.valueOf(b = false);
			//
		} // if
			//
		Assertions.assertEquals(b, drawImage(createProxy(Graphics.class, mh), null, ZERO, ZERO, null));
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
		final int zero = ZERO;
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
	void testMapToInt() throws Throwable {
		//
		Assertions.assertNull(mapToInt(null, null));
		//
		final Stream<?> stream = Stream.empty();
		//
		Assertions.assertNull(mapToInt(stream, null));
		//
		Assertions.assertNotNull(mapToInt(stream, x -> ZERO));
		//
		Assertions.assertNull(mapToInt(Reflection.newProxy(Stream.class, ih), null));
		//
	}

	private static <T> IntStream mapToInt(final Stream<T> instance, final ToIntFunction<? super T> mapper)
			throws Throwable {
		try {
			final Object obj = METHOD_MAP_TO_INT.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof IntStream) {
				return (IntStream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testReduce() throws Throwable {
		//
		Assertions.assertNull(reduce(null, null));
		//
		final IntStream intStream = IntStream.empty();
		//
		Assertions.assertNull(reduce(intStream, null));
		//
		Assertions.assertEquals(OptionalInt.empty(), reduce(intStream, Integer::sum));
		//
		Assertions.assertNull(reduce(Reflection.newProxy(IntStream.class, ih), null));
		//
	}

	private static OptionalInt reduce(final IntStream instance, final IntBinaryOperator op) throws Throwable {
		try {
			final Object obj = METHOD_REDUCE.invoke(null, instance, op);
			if (obj == null) {
				return null;
			} else if (obj instanceof OptionalInt) {
				return (OptionalInt) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOrElse() throws Throwable {
		//
		Assertions.assertEquals(ONE, orElse(null, ONE));
		//
		Assertions.assertEquals(ONE, orElse(OptionalInt.empty(), ONE));
		//
	}

	private static int orElse(final OptionalInt instance, final int other) throws Throwable {
		try {
			final Object obj = METHOD_OR_ELSE.invoke(null, instance, other);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}