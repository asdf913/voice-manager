package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Constructor;
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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_GET_UNICODE_BLOCKS, METHOD_TEST_AND_APPLY, METHOD_AND, METHOD_TO_MULTI_MAP_STRING,
			METHOD_TO_MULTI_MAP_ITERABLE, METHOD_TO_MULTI_MAP_3_ITERABLE, METHOD_TO_MULTI_MAP_3_MULTI_MAP,
			METHOD_CONTAINS_ENTRY = null;

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
		(METHOD_TO_MULTI_MAP_3_ITERABLE = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class,
				Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_3_MULTI_MAP = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class,
				Multimap.class)).setAccessible(true);
		//
		(METHOD_CONTAINS_ENTRY = clz.getDeclaredMethod("containsEntry", Multimap.class, Object.class, Object.class))
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
		Assertions.assertEquals(ImmutableMultimap.of(), instance != null ? instance.getObject() : null);
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.getValue(TestUtil.getFieldWithUrlAnnotationAndValue(
						OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean.class))));
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
			Assertions.assertNull(toMultimap(null, null, (Iterable) null));
			//
			Assertions.assertNull(toMultimap(null, null, (Multimap) null));
			//
			final PatternMap patternMap = new PatternMapImpl();
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("米原市", "まいばらし")),
							MultimapUtil.entries(toMultimap(patternMap, "米原市（まいばらし）", (Multimap) null))));
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("米原市", "まいばらし")),
							MultimapUtil.entries(toMultimap(patternMap, "米原市（まいばらし）", ImmutableMultimap.of()))));
			//
			Assertions.assertNull(toMultimap(patternMap, "米原市（まいばらし）", ImmutableMultimap.of("米原市", "まいばらし")));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("札樽自動車道", "さっそんじどうしゃどう")),
					MultimapUtil.entries(toMultimap(Arrays.asList(new TextNode("札樽自動車道"), textNode), pattern))));
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
			final Document document = ProxyUtil.createProxy(Document.class, mh, x -> {
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
			mh.text = "さかたみなと";
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("酒田", "さかた")),
							MultimapUtil.entries(toMultimap(patternMap, "酒田みなと", es))));
			//
			mh.text = "しらかしだい";
			//
			Assertions
					.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("台", "だい")),
							MultimapUtil.entries(toMultimap(patternMap, "しらかし台", es))));
			//
			mh.text = "ぬまのはたにし";
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("沼", "ぬま", "端西", "はたにし")),
					MultimapUtil.entries(toMultimap(patternMap, "沼ノ端西", es))));
			//
			mh.text = "すながわはいうぇい おあしす";
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("砂川", "すながわ")),
							MultimapUtil.entries(toMultimap(patternMap, "砂川ハイウェイ オアシス", es))));
			//
			mh.text = "ＩＣ";
			//
			Assertions.assertNull(toMultimap(patternMap, "東京湾アクアライン", es));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("南郷", mh.text = "なんごう")),
					MultimapUtil.entries(toMultimap(patternMap, "南郷(京滋ＢＰ）", es))));
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

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String string,
			final Multimap<?, ?> excluded) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_3_MULTI_MAP.invoke(null, patternMap, string, excluded);
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

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s1,
			final Iterable<Element> nextElementSiblings) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_3_ITERABLE.invoke(null, patternMap, s1, nextElementSiblings);
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
	void testContainsEntry() throws Throwable {
		//
		Assertions.assertFalse(containsEntry(null, null, null));
		//
	}

	private static boolean containsEntry(final Multimap<?, ?> instance, final Object key, final Object value)
			throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS_ENTRY.invoke(null, instance, key, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}