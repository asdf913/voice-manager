package org.springframework.beans.factory;

import java.io.File;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_VALUE_OF, METHOD_TEST_AND_APPLY, METHOD_IIF, METHOD_DECREASE,
			METHOD_GET_UNICODE_BLOCKS, METHOD_TO_MULTI_MAP, METHOD_TO_MULTI_MAP2 = null;

	private static final int ZERO = 0;

	private static final int ONE = 1;

	@BeforeAll
	static void beforeClass() throws ReflectiveOperationException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_VALUE_OF = clz.getDeclaredMethod("valueOf", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_IIF = clz.getDeclaredMethod("iif", Boolean.TYPE, Integer.TYPE, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_DECREASE = clz.getDeclaredMethod("decrease", Integer.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP = clz.getDeclaredMethod("toMultimap", PatternMap.class, List.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP2 = clz.getDeclaredMethod("toMultimap2", PatternMap.class, List.class, Integer.TYPE))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Link) {
				//
				if (Objects.equals(methodName, "getText")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "getUrl")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "getDescription")) {
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

		private String text = null;

		private List<Node> childNodes = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof Node) {
				//
				if (Objects.equals(methodName, "childNodeSize")) {
					//
					return IterableUtils.size(childNodes);
					//
				} // if
					//
			} // if
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

	private OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
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
			instance.setLinks(Collections.singleton(null));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final Link link = Reflection.newProxy(Link.class, new IH());
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
		if (instance != null) {
			//
			instance.setText("");
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.nCopies(2, link));
			//
			instance.setDescription(null);
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singletonList(link));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		FieldUtils.writeDeclaredField(instance, "description", null, true);
		//
		final Properties properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean.url")));
				//
			} // if
				//
			final File file = new File("OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean.txt");
			//
			FileUtils.writeLines(file, MultimapUtil.entries(getObject(instance)));
			//
			System.out.println(file.getAbsolutePath());
			//
		} // if
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testValueOf() throws Throwable {
		//
		Assertions.assertNull(valueOf(null));
		//
		Assertions.assertNull(valueOf(""));
		//
		Assertions.assertNull(valueOf(" "));
		//
		Assertions.assertNull(valueOf("A"));
		//
		Assertions.assertEquals(Integer.valueOf(ZERO), valueOf(Integer.toString(ZERO)));
		//
	}

	private static Integer valueOf(final String instance) throws Throwable {
		try {
			final Object obj = METHOD_VALUE_OF.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
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
	void testIif() throws Throwable {
		//
		Assertions.assertEquals(ZERO, iif(true, ZERO, ONE));
		//
		Assertions.assertEquals(ONE, iif(false, ZERO, ONE));
		//
	}

	private static int iif(final boolean condition, final int trueValue, final int falseValue) throws Throwable {
		try {
			final Object obj = METHOD_IIF.invoke(null, condition, trueValue, falseValue);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testDecrease() throws Throwable {
		//
		Assertions.assertEquals(ZERO, decrease(null, ZERO));
		//
		Assertions.assertEquals(ZERO, decrease(Integer.valueOf(ONE), ONE));
		//
	}

	private static Integer decrease(final Integer instance, final int i) throws Throwable {
		try {
			final Object obj = METHOD_DECREASE.invoke(null, instance, i);
			if (obj == null) {
				return null;
			} else if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetUnicodeBlocks() throws Throwable {
		//
		Assertions.assertNull(getUnicodeBlocks(null));
		//
		Assertions.assertEquals(Collections.singletonList(UnicodeBlock.BASIC_LATIN), getUnicodeBlocks("AA"));
		//
	}

	private static List<UnicodeBlock> getUnicodeBlocks(final String string) throws Throwable {
		try {
			final Object obj = METHOD_GET_UNICODE_BLOCKS.invoke(null, string);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMultimap() throws Throwable {
		//
		Assertions.assertNull(toMultimap(null, null, 0));
		//
		Assertions.assertNull(toMultimap(null, Collections.nCopies(3, null), 0));
		//
		final MH mh1 = new MH();
		//
		final FailableFunction<Class<?>, Element, Throwable> function = x -> {
			final Constructor<?> c = x != null ? x.getDeclaredConstructor(String.class) : null;
			//
			if (c != null) {
				//
				c.setAccessible(true);
				//
			} // if
				//
			return Util.cast(Element.class, c != null ? c.newInstance("A") : null);
			//
		};
		//
		final Element e1 = createProxy(Element.class, mh1, function);
		//
		Assertions.assertNull(toMultimap(null, Arrays.asList(e1, null, null), 0));
		//
		final MH mh2 = new MH();
		//
		final Element e2 = createProxy(Element.class, mh2, function);
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of(mh1.text = "御殿場", mh2.text = "ごてんば")),
				MultimapUtil.entries(toMultimap(null, Arrays.asList(e1, null, e2), 0))));
		//
		mh1.text = "清水いはら";
		//
		mh2.text = "しみずいはら";
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("清水", "しみず")),
				MultimapUtil.entries(toMultimap(null, Arrays.asList(e1, null, e2), 0))));
		//
	}

	private static <T> T createProxy(final Class<T> superClass, final MethodHandler mh,
			final FailableFunction<Class<?>, T, Throwable> function) throws Throwable {
		//
		final ProxyFactory proxyFactory = new ProxyFactory();
		//
		proxyFactory.setSuperclass(superClass);
		//
		final Class<?> clz = proxyFactory.createClass();
		//
		Object instance = null;
		//
		if (function == null) {
			//
			final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
			//
			instance = constructor != null ? constructor.newInstance() : null;
		} else {
			//
			instance = function.apply(clz);
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

	private static Multimap<String, String> toMultimap(final Object patternMap, final List<Element> children,
			final int offset) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP.invoke(null, patternMap, children, offset);
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
	void testToMultimap2() throws Throwable {
		//
		Assertions.assertNull(toMultimap2(null, null, 0));
		//
		final MH mh1 = new MH();
		//
		final FailableFunction<Class<?>, Element, Throwable> function = x -> {
			final Constructor<?> c = x != null ? x.getDeclaredConstructor(String.class) : null;
			//
			if (c != null) {
				//
				c.setAccessible(true);
				//
			} // if
				//
			return Util.cast(Element.class, c != null ? c.newInstance("A") : null);
			//
		};
		//
		final Element e = createProxy(Element.class, mh1, function);
		//
		mh1.text = "沼津市根古屋（ぬまづしねごや)";
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("沼津市根古屋", "ぬまづしねごや")),
						MultimapUtil.entries(toMultimap2(null, Arrays.asList(null, null, null, e), 0))));
		//
		mh1.text = "（上り）静岡市葵区小瀬戸（しずおかしあおいくこぜと） （下り）静岡市葵区飯間（しずおかしあおいくはんま)";
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("静岡市葵区小瀬戸", "しずおかしあおいくこぜと", "静岡市葵区飯間", "しずおかしあおいくはんま")),
				MultimapUtil.entries(toMultimap2(null, Arrays.asList(null, null, null, e), 0))));
		//
	}

	private static Multimap<String, String> toMultimap2(final Object patternMap, final List<Element> children,
			final int offset) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP2.invoke(null, patternMap, children, offset);
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
	void testNodeVisitorImpl() throws ReflectiveOperationException {
		//
		final Class<?> clz = Class.forName(
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSintomeiYomikataJitenMultimapFactoryBean$NodeVisitorImpl");
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final NodeVisitor nodeVisitor = Util.cast(NodeVisitor.class,
				constructor != null ? constructor.newInstance() : null);
		//
		Assertions.assertDoesNotThrow(() -> head(nodeVisitor, null, 0));
		//
		Assertions.assertDoesNotThrow(
				() -> head(nodeVisitor, Util.cast(TextNode.class, Narcissus.allocateInstance(TextNode.class)), 0));
		//
		Assertions.assertDoesNotThrow(() -> head(nodeVisitor, new TextNode("江差線（えさしせん）"), 0));
		//
		final Field multimap = clz != null ? clz.getDeclaredField("multimap") : null;
		//
		if (multimap != null) {
			//
			multimap.setAccessible(true);
			//
		} // if
			//
		Assertions.assertEquals(Collections.singleton(Pair.of("江差線", "えさしせん")),
				MultimapUtil.entries(Util.cast(Multimap.class, get(multimap, nodeVisitor))));
		//
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static void head(final NodeVisitor instance, final Node node, final int depth) {
		if (instance != null) {
			instance.head(node, depth);
		}
	}

}