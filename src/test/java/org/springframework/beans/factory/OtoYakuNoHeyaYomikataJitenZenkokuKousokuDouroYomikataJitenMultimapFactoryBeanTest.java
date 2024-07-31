package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.validator.routines.IntegerValidator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Functions;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_GET_UNICODE_BLOCKS, METHOD_TEST_AND_APPLY4, METHOD_TEST_AND_APPLY5, METHOD_AND,
			METHOD_TO_MULTI_MAP_STRING, METHOD_TO_MULTI_MAP_ELEMENT, METHOD_TO_MULTI_MAP_ITERABLE,
			METHOD_TO_MULTI_MAP_3_ITERABLE, METHOD_TO_MULTI_MAP_3_MULTI_MAP, METHOD_VALIDATE = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				BiFunction.class, BiFunction.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_STRING = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_ELEMENT = clz.getDeclaredMethod("toMultimap", PatternMap.class, Element.class))
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
		(METHOD_VALIDATE = clz.getDeclaredMethod("validate", IntegerValidator.class, String.class)).setAccessible(true);
		//
	}

	private static class MH implements MethodHandler {

		private String text, attr;

		private Elements nextElementSiblings = null;

		private Boolean hasAttr = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = Util.getName(thisMethod);
			//
			if (self instanceof Node) {
				//
				if (Objects.equals(methodName, "attr")) {
					//
					return attr;
					//
				} else if (Objects.equals(methodName, "hasAttr")) {
					//
					return hasAttr;
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
				} else if (Objects.equals(methodName, "nextElementSiblings")) {
					//
					return nextElementSiblings;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject = false;

	private MH mh = null;

	private PatternMap patternMap = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBeanTest.testGetObject");
		//
		mh = new MH();
		//
		patternMap = new PatternMapImpl();
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
		Assertions.assertNull(testAndApply(org.meeuw.functional.Predicates.biAlwaysFalse(), null, null, null, null));
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
			//
			Assertions.assertNull(testAndApply(org.meeuw.functional.Predicates.biAlwaysTrue(), null, null,
					Functions.biAlways(null), null));
			//
		} // if
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

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY5.invoke(null, predicate, t, u, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetUnicodeBlocks() throws Throwable {
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
	void testToMultimap1() throws Throwable {
		//
		Assertions.assertNull(toMultimap(Collections.singleton(null), null));
		//
		final Pattern pattern = Pattern.compile("^（(\\p{InHIRAGANA}+)）$");
		//
		final TextNode textNode = new TextNode("（さっそんじどうしゃどう）");
		//
		Assertions.assertNull(toMultimap(Arrays.asList(new TextNode(""), textNode), pattern));
		//
	}

	@Test
	void testToMultimap2() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		final Pattern pattern = Pattern.compile("^（(\\p{InHIRAGANA}+)）$");
		//
		final TextNode textNode = new TextNode("（さっそんじどうしゃどう）");
		//
		Assertions.assertNull(toMultimap(Collections.singleton(textNode), pattern));
		//
		final Multimap<?, ?> multimap = ImmutableMultimap.of();
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
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("札樽自動車道", "さっそんじどうしゃどう")),
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
		Assertions.assertEquals(multimap, toMultimap(null, null, es));
		//
		Assertions.assertEquals(multimap, toMultimap(patternMap, "仙台南（東北）", es));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("仙台南", mh.text = "せんだいみなみ")),
				MultimapUtil.entries(toMultimap(patternMap, "仙台南（東北）", es))));
		//
		if (mh != null) {
			//
			mh.text = "さかたみなと";
			//
		} // if
			//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("酒田", "さかた")),
				MultimapUtil.entries(toMultimap(patternMap, "酒田みなと", es))));
		//
		if (mh != null) {
			//
			mh.text = "しらかしだい";
			//
		} // if
			//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("台", "だい")),
				MultimapUtil.entries(toMultimap(patternMap, "しらかし台", es))));
		//
		if (mh != null) {
			//
			mh.text = "ぬまのはたにし";
			//
		} // if
			//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("沼", "ぬま", "端西", "はたにし")),
						MultimapUtil.entries(toMultimap(patternMap, "沼ノ端西", es))));
		//
		if (mh != null) {
			//
			mh.text = "すながわはいうぇい おあしす";
			//
		} // if
			//
		Assertions
				.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("砂川", "すながわ")),
						MultimapUtil.entries(toMultimap(patternMap, "砂川ハイウェイ オアシス", es))));
		//
		if (mh != null) {
			//
			mh.text = "ＩＣ";
			//
		} // if
			//
		Assertions.assertEquals(multimap, toMultimap(patternMap, "東京湾アクアライン", es));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("南郷", mh.text = "なんごう")),
						MultimapUtil.entries(toMultimap(patternMap, "南郷(京滋ＢＰ）", es))));
		//
		if (mh != null) {
			//
			mh.text = "とうぶゆのまる";
			//
		} // if
			//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("東部湯", "とうぶゆ", "丸", "まる")),
						MultimapUtil.entries(toMultimap(patternMap, "東部湯の丸", es))));
		//
		if (mh != null) {
			//
			mh.text = "いなわしろ ばんだいこうげん";
			//
		} // if
			//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("猪苗代", "いなわしろ", "磐梯高原", "ばんだいこうげん")),
				MultimapUtil.entries(toMultimap(patternMap, "猪苗代 磐梯高原", es))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("首都高速", mh.text = "しゅとこうそく")),
				MultimapUtil.entries(toMultimap(patternMap, "（首都高速）", es))));
		//
		String string = "国縫";
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of(string, mh.text = "くんぬい")),
						MultimapUtil.entries(toMultimap(patternMap, string, es))));
		//
		if (mh != null) {
			//
			mh.text = "ちとせえにわ（道東道へ）";
			//
		} // if
			//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of(string = "千歳恵庭", "ちとせえにわ")),
						MultimapUtil.entries(toMultimap(patternMap, string, es))));
		//
		final Element element1 = ProxyUtil.createProxy(Element.class, mh, x -> {
			//
			final Constructor<?> constructor = getDeclaredConstructor(x, String.class);
			//
			if (constructor != null) {
				//
				constructor.setAccessible(true);
				//
			} // if
				//
			return Util.cast(Element.class, newInstance(constructor, "A"));
			//
		});
		//
		Assertions.assertNull(toMultimap(patternMap, element1));
		//
	}

	@Test
	void testToMultimap3() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		final Element element1 = ProxyUtil.createProxy(Element.class, mh, x -> {
			//
			final Constructor<?> constructor = getDeclaredConstructor(x, String.class);
			//
			if (constructor != null) {
				//
				constructor.setAccessible(true);
				//
			} // if
				//
			return Util.cast(Element.class, newInstance(constructor, "A"));
			//
		});
		//
		Assertions.assertNull(toMultimap(patternMap, element1));
		//
		if (mh != null) {
			//
			mh.text = "和光北 （新倉ＰＡ併設）";
			//
		} // if
			//
		Assertions.assertNull(toMultimap(patternMap, element1));
		//
		if (mh != null) {
			//
			mh.nextElementSiblings = new Elements(Collections.nCopies(2, null));
			//
		} // if
			//
		Assertions.assertNull(toMultimap(patternMap, element1));
		//
		final MH mh2 = new MH();
		//
		final Element element2 = ProxyUtil.createProxy(Element.class, mh2, x -> {
			//
			final Constructor<?> constructor = getDeclaredConstructor(x, String.class);
			//
			if (constructor != null) {
				//
				constructor.setAccessible(true);
				//
			} // if
				//
			return Util.cast(Element.class, newInstance(constructor, "A"));
			//
		});
		//
		if (mh != null) {
			//
			mh.nextElementSiblings = new Elements(Arrays.asList(null, element2));
			//
		} // if
			//
		mh2.text = "わこうきた";
		//
		if (mh != null) {
			//
			mh.hasAttr = Boolean.FALSE;
			//
		} // if
			//
		Assertions.assertNull(toMultimap(patternMap, element1));
		//
		if (mh != null) {
			//
			mh.hasAttr = Boolean.TRUE;
			//
			mh.attr = "2";
			//
		} // if
			//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("和光北", mh2.text)),
						MultimapUtil.entries(toMultimap(patternMap, element1))));
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

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final Element element)
			throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_ELEMENT.invoke(null, patternMap, element);
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
	void testValidate() throws Throwable {
		//
		Assertions.assertNull(validate(IntegerValidator.getInstance(), null));
		//
	}

	private static Integer validate(final IntegerValidator instance, final String value) throws Throwable {
		try {
			final Object obj = METHOD_VALIDATE.invoke(null, instance, value);
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
	void testNull() {
		//
		final Method[] ms = OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean.class
				.getDeclaredMethods();
		//
		Method m = null;
		//
		Object result = null;
		//
		String toString = null;
		//
		Collection<Object> collection = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Object[] os = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || m.isSynthetic()
					|| Boolean.logicalAnd(Objects.equals(Util.getName(m), "and"), Arrays.equals(m.getParameterTypes(),
							new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class }))) {
				//
				continue;
				//
			} // if
				//
			toString = Objects.toString(m);
			//
			clear(collection = ObjectUtils.getIfNull(collection, ArrayList::new));
			//
			collection.addAll(Collections.nCopies(m.getParameterCount(), null));
			//
			os = toArray(collection);
			//
			result = Modifier.isStatic(m.getModifiers()) ? Narcissus.invokeStaticMethod(m, os)
					: Narcissus.invokeMethod(instance, m, os);
			//
			parameterTypes = m.getParameterTypes();
			//
			if (Objects.equals(Util.getName(m), "toMultimap")
					&& Arrays.equals(parameterTypes, new Class<?>[] { Document.class, PatternMap.class })
					|| Objects.equals(Util.getName(m), "toMultimap") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, String.class, Iterable.class })
					|| Objects.equals(Util.getName(m), "getObject")
							&& Arrays.equals(parameterTypes, new Class<?>[] {})) {
				//
				Assertions.assertEquals(ImmutableMultimap.of(), result, toString);
				//
			} else if (Objects.equals(Util.getName(m), "getObjectType")
					&& Arrays.equals(parameterTypes, new Class<?>[] {})) {
				//
				Assertions.assertEquals(Multimap.class, result, toString);
				//
			} else if (Objects.equals(Util.getName(m), "containsEntry")
					&& Arrays.equals(parameterTypes, new Class<?>[] { Multimap.class, Object.class, Object.class })) {
				//
				Assertions.assertEquals(Boolean.FALSE, result, toString);
				//
			} else {
				//
				Assertions.assertNull(result, toString);
				//
			} // if
				//
		} // for
			//
	}

	private void clear(final Collection<Object> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

}