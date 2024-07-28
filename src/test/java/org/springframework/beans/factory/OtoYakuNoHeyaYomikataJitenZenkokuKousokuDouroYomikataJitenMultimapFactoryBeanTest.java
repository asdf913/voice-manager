package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

class OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBeanTest {

	private static final String SPACE = " ";

	private static Method METHOD_GET_UNICODE_BLOCKS, METHOD_TEST_AND_APPLY, METHOD_AND, METHOD_TO_MULTI_MAP_STRING,
			METHOD_TO_MULTI_MAP_ITERABLE, METHOD_TO_MULTI_MAP_3 = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_STRING = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_ITERABLE = clz.getDeclaredMethod("toMultimap", Iterable.class, Pattern.class))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_3 = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class, Iterable.class))
				.setAccessible(true);
		//
	}

	private static class MH implements MethodHandler {

		private String text = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof Element && Objects.equals(methodName, "text")) {
				//
				return text;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject = false;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBeanTest.testGetObject");
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setUrls(Collections.singleton(""));
			//
		} // if
			//
		final Multimap<?, ?> multimap = ImmutableMultimap.of();
		//
		Assertions.assertEquals(multimap, instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setUrls(Collections.singleton(SPACE));
			//
		} // if
			//
		Assertions.assertEquals(multimap, instance != null ? instance.getObject() : null);
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			if (instance != null) {
				//
				instance.setUrls(Arrays.asList("https://hiramatu-hifuka.com/onyak/onyak2/kosoku01.html",
						"https://hiramatu-hifuka.com/onyak/onyak2/kosoku02.html"));
				//
			} // if
				//
			Assertions.assertDoesNotThrow(() -> instance != null ? instance.getObject() : null);
			//
		} // if
			//
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
			//
		} // if
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
	void testGetUnicodeBlocks() throws Throwable {
		//
		Assertions.assertNull(getUnicodeBlocks(null));
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertEquals(Collections.singletonList(UnicodeBlock.BASIC_LATIN), getUnicodeBlocks("12"));
			//
		} // if
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
	void testAnd() throws Throwable {
		//
		Assertions.assertFalse(and(true, false));
		//
		Assertions.assertTrue(and(true, true, null));
		//
		Assertions.assertFalse(and(true, true, false));
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertFalse(and(false, false));
			//
			Assertions.assertFalse(and(true, true, false));
			//
			Assertions.assertTrue(and(true, true, true));
			//
		} // if
			//
	}

	private static boolean and(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = METHOD_AND.invoke(null, a, b, bs);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMultimap() throws Throwable {
		//
		Assertions.assertNull(toMultimap(Collections.singleton(null), null));
		//
		final Pattern pattern = Pattern.compile("^（(\\p{InHIRAGANA}+)）$");
		//
		final TextNode textNode = new TextNode("（さっそんじどうしゃどう）");
		//
		Assertions.assertNull(toMultimap(Arrays.asList(new TextNode(""), textNode), pattern));
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertNull(toMultimap((PatternMap) null, null));
			//
			Assertions.assertNull(toMultimap(Collections.singleton(textNode), pattern));
			//
			Assertions.assertNull(toMultimap(null, null, null));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("札樽自動車道", "さっそんじどうしゃどう")),
					MultimapUtil.entries(toMultimap(Arrays.asList(new TextNode("札樽自動車道"), textNode), pattern))));
			//
			final PatternMap patternMap = new PatternMapImpl();
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("札樽道", "さっそんどう")),
							MultimapUtil.entries(toMultimap(patternMap, "札樽自動車道 （札樽道） （さっそんどう）"))));
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("百石道路", "ももいしどうろ")),
							MultimapUtil.entries(toMultimap(patternMap, "百石道路 （ももいしどうろ）"))));
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("仙塩道路", "せんえんどうろ")),
							MultimapUtil.entries(toMultimap(patternMap, "＊仙台港北〜利府中 を仙塩道路（せんえんどうろ）"))));
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
			final Iterable<Element> es = Arrays.asList(null, document);
			//
			Assertions.assertNull(toMultimap(null, null, es));
			//
			Assertions.assertNull(toMultimap(patternMap, "仙台南（東北）", es));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("仙台南", mh.text = "せんだいみなみ")),
					MultimapUtil.entries(toMultimap(patternMap, "仙台南（東北）", es))));
			//
		} // if
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
			instance = FailableFunctionUtil.apply(function, clz);
			//
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

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s1,
			final Iterable<Element> nextElementSiblings) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_3.invoke(null, patternMap, s1, nextElementSiblings);
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

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String string)
			throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_STRING.invoke(null, patternMap, string);
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

	private static Multimap<String, String> toMultimap(final Iterable<TextNode> textNodes, final Pattern pattern)
			throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_ITERABLE.invoke(null, textNodes, pattern);
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
	void testSetUrls() throws Exception {
		//
		final Field urls = OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean.class
				.getDeclaredField("urls");
		//
		if (urls != null) {
			//
			urls.setAccessible(true);
			//
		} // if
			//
		setUrls(instance, null);
		//
		Assertions.assertNull(FieldUtils.readField(urls, instance));
		//
		String string = "";
		//
		setUrls(instance, string);
		//
		Assertions.assertEquals(Collections.singleton(string), FieldUtils.readField(urls, instance));
		//
		setUrls(instance, new String[] { string });
		//
		Assertions.assertEquals(Collections.singletonList(string), FieldUtils.readField(urls, instance));
		//
		setUrls(instance, string = SPACE);
		//
		Assertions.assertEquals(Collections.singleton(string), FieldUtils.readField(urls, instance));
		//
		final Number number = Integer.valueOf(1);
		//
		setUrls(instance, number);
		//
		Assertions.assertEquals(Collections.singleton(Util.toString(number)), FieldUtils.readField(urls, instance));
		//
		final Boolean b = Boolean.TRUE;
		//
		setUrls(instance, b);
		//
		Assertions.assertEquals(Collections.singleton(Util.toString(b)), FieldUtils.readField(urls, instance));
		//
		setUrls(instance, new ObjectMapper().writeValueAsString(Collections.emptySet()));
		//
		Assertions.assertEquals(Collections.emptyList(), FieldUtils.readField(urls, instance));
		//
		final ObjectMapper objectMapper = new ObjectMapper();
		//
		setUrls(instance, objectMapper.writeValueAsString(SPACE));
		//
		Assertions.assertEquals(Collections.singletonList(SPACE), FieldUtils.readField(urls, instance));
		//
		setUrls(instance, objectMapper.writeValueAsString(number));
		//
		Assertions.assertEquals(Collections.singletonList(Util.toString(number)), FieldUtils.readField(urls, instance));
		//
		setUrls(instance, objectMapper.writeValueAsString(b));
		//
		Assertions.assertEquals(Collections.singleton(Util.toString(b)), FieldUtils.readField(urls, instance));
		//
		Assertions.assertThrows(IllegalStateException.class,
				() -> setUrls(instance, objectMapper.writeValueAsString(Collections.emptyMap())));
		//
	}

	private void setUrls(final OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean instance,
			final Object input) throws JsonProcessingException {
		if (instance != null) {
			instance.setUrls(input);
		}
	}

}