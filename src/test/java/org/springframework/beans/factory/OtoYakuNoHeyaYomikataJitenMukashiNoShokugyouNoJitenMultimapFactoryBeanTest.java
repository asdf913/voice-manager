package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_GET_UNICODE_BLOCKS, METHOD_TO_MULTI_MAP_ITERABLE,
			METHOD_TO_MULTI_MAP_STRING_ARRAY, METHOD_TO_MULTI_MAP_STRING, METHOD_TEST_AND_APPLY_AS_INT;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_ITERABLE = clz.getDeclaredMethod("toMultimap", Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_STRING_ARRAY = clz.getDeclaredMethod("toMultimap", PatternMap.class, String[].class))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_STRING = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY_AS_INT = clz.getDeclaredMethod("testAndApplyAsInt", IntPredicate.class, Integer.TYPE,
				IntUnaryOperator.class, IntUnaryOperator.class, Integer.TYPE)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject;

	private PatternMap patternMap = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBeanTest.testGetObject");
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
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		final Entry<Field, Object> entry = TestUtil.getFieldWithUrlAnnotationAndValue(
				OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean.class);
		//
		final Field url = Util.getKey(entry);
		//
		Narcissus.setObjectField(instance, url, "");
		//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		Narcissus.setObjectField(instance, url, " ");
		//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			Narcissus.setObjectField(instance, url, Util.getValue(entry));
			//
			Assertions.assertDoesNotThrow(() -> FactoryBeanUtil.getObject(instance));
			//
		} // if
			//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = OtoYakuNoHeyaYomikataJitenMukashiNoShokugyouNoJitenMultimapFactoryBean.class
				.getDeclaredMethods();
		//
		Method m;
		//
		Collection<Object> list = null;
		//
		Class<?>[] parameterTypes;
		//
		Object[] os;
		//
		String toString;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			clear(list = ObjectUtils.getIfNull(list, ArrayList::new));
			//
			if ((parameterTypes = m.getParameterTypes()) != null) {
				//
				for (final Class<?> clz : parameterTypes) {
					//
					if (Objects.equals(Integer.TYPE, clz)) {
						//
						list.add(Integer.valueOf(0));
						//
					} else {
						//
						list.add(null);
						//
					} // if
						//
				} // for
					//
			} // if
				//
			os = toArray(list);
			//
			toString = Util.toString(m);
			//
			if (Objects.equals(Integer.TYPE, m.getReturnType())) {
				//
				Assertions.assertEquals(0, Narcissus.invokeStaticIntMethod(m, os), toString);
				//
			} else {
				//
				Assertions.assertNull(Narcissus.invokeStaticMethod(m, os), toString);
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
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertEquals(Collections.singletonList(UnicodeBlock.BASIC_LATIN), getUnicodeBlocks(" "));
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
	void testToMultimap1() throws Throwable {
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertDoesNotThrow(() -> toMultimap(Collections.singleton(null)));
			//
			Assertions.assertEquals(ImmutableMultimap.of(), toMultimap(null, new String[] { null }));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("銅細工", "あかがねざいく")),
					MultimapUtil.entries(toMultimap(patternMap, "銅細工（あかがねざいく）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("鉄屋", "金屋"), x -> Pair.of(x, "かなや"))),
					MultimapUtil.entries(toMultimap(patternMap, "鉄屋・金屋（かなや）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("こうや", "こんや"), x -> Pair.of("紺屋", x))),
					MultimapUtil.entries(toMultimap(patternMap, "紺屋（こうや・こんや）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("挽物職", "ひきものしょく", "挽物師", "ひきものし")),
					MultimapUtil.entries(toMultimap(patternMap, "挽物職・挽物師（ひきものしょく・ひきものし）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("酒麹売", "しゅきくうり")),
					MultimapUtil.entries(toMultimap(patternMap, "酒麹売（しゅきくうり）＊麹の麦は麥"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("馬買", "うまか")),
					MultimapUtil.entries(toMultimap(patternMap, "馬買おう（うまかおう）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("桂庵", "慶庵", "慶安"), x -> Pair.of(x, "けいあん"))),
					MultimapUtil.entries(toMultimap(patternMap, "桂庵・慶庵・慶安（けいあん）"))));
			//
			Assertions.assertEquals(ImmutableMultimap.of("酒師", "さけし"),
					toMultimap(patternMap, "きき酒師（ききさけし）＊（きき）は口へんに利"));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("荏胡麻", "えごま", "油売", "あぶらうり")),
					MultimapUtil.entries(toMultimap(patternMap, "荏胡麻の油売（えごまのあぶらうり）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("医師", "薬師"), x -> Pair.of(x, "くすし"))),
					MultimapUtil.entries(toMultimap(patternMap, "医師･薬師（くすし）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("坊主", "ぼうず")),
					MultimapUtil.entries(toMultimap(patternMap, "すたすた坊主（すたすたぼうず）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("やきものし", "すえものし"), x -> Pair.of("陶物師", x))),
					MultimapUtil.entries(toMultimap(patternMap, "陶物師（やきものし）＊（すえものし）とも言う。"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("赤蛙", "あかがえる")),
					MultimapUtil.entries(toMultimap(patternMap, "赤蛙売り（あかがえるうり）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("塗師屋", "ぬしや")),
					MultimapUtil.entries(toMultimap(patternMap, "塗師屋（ぬしや）塗士とも"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("足駄", "あしだ")),
					MultimapUtil.entries(toMultimap(patternMap, "足駄作り（あしだづくり）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("綾", "あや")),
					MultimapUtil.entries(toMultimap(patternMap, "綾取り（あやとり）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("海", "うみ")),
					MultimapUtil.entries(toMultimap(patternMap, "海ほうづき売り（うみほうづきうり）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("雪踏", "せった")),
					MultimapUtil.entries(toMultimap(patternMap, "雪踏直し（せったなおし）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("黒木", "くろき", "黒鍬之者", "くろくわのもの")),
					MultimapUtil.entries(toMultimap(patternMap, "黒木売り（くろきうり）黒鍬之者（くろくわのもの）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("印肉", "いんにく")),
					MultimapUtil.entries(toMultimap(patternMap, "印肉の仕替へ（いんにくのしかえ）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("鏡", "かがみ")),
					MultimapUtil.entries(toMultimap(patternMap, "鏡磨き（かがみみがき）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("葛西", "かさい")),
					MultimapUtil.entries(toMultimap(patternMap, "葛西踊り（かさいおどり）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("傘", "かさ")),
					MultimapUtil.entries(toMultimap(patternMap, "傘張り（かさはり）"))));
			//
		} // if
			//
	}

	@Test
	void testToMultimap2() throws Throwable {
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("鋸", "のこぎり", "目立", "めたて")),
					MultimapUtil.entries(toMultimap(patternMap, "鋸の目立（のこぎりのめたて）"))));
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("歯", "は", "屋", "や")),
							MultimapUtil.entries(toMultimap(patternMap, "歯入れ屋（はいれや）＊下駄"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("古鉄", "ふるかね")),
					MultimapUtil.entries(toMultimap(patternMap, "古鉄買い（ふるかねかい）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("枇杷葉湯", "びわようとう")),
					MultimapUtil.entries(toMultimap(patternMap, "枇杷葉湯売り（（びわようとううり）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("行燈仕", "あんどん")),
					MultimapUtil.entries(toMultimap(patternMap, "行燈仕替へ（あんどんしかえ）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("菜", "な")),
					MultimapUtil.entries(toMultimap(patternMap, "摘み菜売り（つみなうり）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("人形", "にんぎょう")),
					MultimapUtil.entries(toMultimap(patternMap, "人形舞い（にんぎょうまい）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("職", "しょく")),
					MultimapUtil.entries(toMultimap(patternMap, "ぬいもの職（ぬいものしょく）＊（ぬいもの）は繍の旧字体か。"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("下駄歯", "げたは")),
					MultimapUtil.entries(toMultimap(patternMap, "下駄歯入れ（げたはいれ）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("油", "あぶら")),
					MultimapUtil.entries(toMultimap(patternMap, "油揚げ売り（あぶらあげうり）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("こころぶとうり", "ところてんうり"), x -> Pair.of("心太売", x))),
					MultimapUtil.entries(toMultimap(patternMap, "心太売（こころぶとうり）日本国語大辞典＊（ところてんうり）に同じ"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("歯", "は")),
					MultimapUtil.entries(toMultimap(patternMap, "歯磨き売り（はみがきうり）"))));
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("舞", "まい", "曲", "くせ")),
							MultimapUtil.entries(toMultimap(patternMap, "曲舞々（くせまいまい）"))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(Collections.singleton(Pair.of("犬", "いぬ")),
					MultimapUtil.entries(toMultimap(patternMap, "犬拾ひ（いぬひろい）"))));
			//
		} // if
			//
	}

	private static Multimap<String, String> toMultimap(final Iterable<Node> nodes) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_ITERABLE.invoke(null, nodes);
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

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String[] ss)
			throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_STRING_ARRAY.invoke(null, patternMap, ss);
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

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_STRING.invoke(null, patternMap, s);
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
	void testTestAndApplyAsInt() throws Throwable {
		//
		final int zero = 0;
		//
		Assertions.assertEquals(zero, testAndApplyAsInt(x -> x == zero, zero, null, null, zero));
		//
	}

	private static int testAndApplyAsInt(final IntPredicate predicate, final int value, final IntUnaryOperator t,
			final IntUnaryOperator f, final int defaultValue) throws Throwable {
		try {
			final Object obj = METHOD_TEST_AND_APPLY_AS_INT.invoke(null, predicate, value, t, f, defaultValue);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}