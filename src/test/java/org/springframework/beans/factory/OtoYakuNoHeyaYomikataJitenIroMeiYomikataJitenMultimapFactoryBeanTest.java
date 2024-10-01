package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.d2ab.collection.ints.IntCollection;
import org.d2ab.collection.ints.IntCollectionUtil;
import org.d2ab.collection.ints.IntIterable;
import org.d2ab.collection.ints.IntList;
import org.d2ab.function.ObjIntPredicate;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriPredicate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBeanTest {

	private static final int ZERO = 0;

	private static Method METHOD_TEST_AND_APPLY, METHOD_TO_MULTI_MAP2, METHOD_TO_MULTI_MAP3,
			METHOD_TEST_AND_APPLY_AS_INT, METHOD_CONTAINS_INT, METHOD_REMOVE_INT, METHOD_FLAT_MAP,
			METHOD_TO_MULTI_MAP_AND_INT_LIST, METHOD_COLLECT, METHOD_MAP, METHOD_TEST_AND_ACCEPT3,
			METHOD_TEST_AND_ACCEPT4, METHOD_TEST_AND_ACCEPT5, METHOD_TO_ARRAY_COLLECTION, METHOD_TO_ARRAY_STREAM,
			METHOD_FOR_EACH_INT_STREAM, METHOD_IS_EMPTY, METHOD_MAX, METHOD_TO_MULTI_MAP_17_C_2, METHOD_ADD_ALL_INTS;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP2 = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP3 = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY_AS_INT = clz.getDeclaredMethod("testAndApplyAsInt", IntPredicate.class, Integer.TYPE,
				IntUnaryOperator.class, IntUnaryOperator.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_CONTAINS_INT = clz.getDeclaredMethod("containsInt", IntIterable.class, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_REMOVE_INT = clz.getDeclaredMethod("removeInt", IntList.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_FLAT_MAP = clz.getDeclaredMethod("flatMap", Stream.class, Function.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_AND_INT_LIST = clz.getDeclaredMethod("toMultimapAndIntList", PatternMap.class, List.class,
				Integer.TYPE)).setAccessible(true);
		//
		(METHOD_COLLECT = clz.getDeclaredMethod("collect", IntStream.class, Supplier.class, ObjIntConsumer.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_MAP = clz.getDeclaredMethod("map", IntStream.class, IntUnaryOperator.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT3 = clz.getDeclaredMethod("testAndAccept", Predicate.class, Object.class,
				Consumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4 = clz.getDeclaredMethod("testAndAccept", ObjIntPredicate.class, Object.class,
				Integer.TYPE, ObjIntConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT5 = clz.getDeclaredMethod("testAndAccept", TriPredicate.class, Object.class,
				Object.class, Object.class, TriConsumer.class)).setAccessible(true);
		//
		(METHOD_TO_ARRAY_COLLECTION = clz.getDeclaredMethod("toArray", Collection.class, Object[].class))
				.setAccessible(true);
		//
		(METHOD_TO_ARRAY_STREAM = clz.getDeclaredMethod("toArray", Stream.class, IntFunction.class))
				.setAccessible(true);
		//
		(METHOD_FOR_EACH_INT_STREAM = clz.getDeclaredMethod("forEach", IntStream.class, IntConsumer.class))
				.setAccessible(true);
		//
		(METHOD_IS_EMPTY = clz.getDeclaredMethod("isEmpty", IntList.class)).setAccessible(true);
		//
		(METHOD_MAX = clz.getDeclaredMethod("max", Stream.class, Comparator.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_17_C_2 = clz.getDeclaredMethod("toMultimap17C2", Iterable.class, Entry.class))
				.setAccessible(true);
		//
		(METHOD_ADD_ALL_INTS = clz.getDeclaredMethod("addAllInts", IntCollection.class, IntCollection.class))
				.setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject;

	private PatternMap patternMap;

	private ObjectMapper objectMapper = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBeanTest.testGetObject");
		//
		patternMap = new PatternMapImpl();
		//
		objectMapper = new ObjectMapper();
		//
	}

	@Test
	void testGetObjecctType() {
		//
		Assertions.assertEquals(Multimap.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		final Entry<Field, Object> entry = TestUtil.getFieldWithUrlAnnotationAndValue(
				OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean.class);
		//
		final Field url = Util.getKey(entry);
		//
		Narcissus.setObjectField(instance, url, "");
		//
		Assertions.assertNull(getObject(instance));
		//
		Narcissus.setObjectField(instance, url, " ");
		//
		Assertions.assertNull(getObject(instance));
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			Narcissus.setObjectField(instance, url, Util.getValue(entry));
			//
			Assertions.assertDoesNotThrow(() -> getObject(instance));
			//
		} // if
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testNull() {
		//
		final Method[] ms = OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean.class.getDeclaredMethods();
		//
		Method m = null;
		//
		Collection<Object> list = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Object invokeStaticMethod;
		//
		String toString, name;
		//
		for (int i = ZERO; ms != null && i < ms.length; i++) {
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
						list.add(Integer.valueOf(ZERO));
						//
					} else if (Objects.equals(Character.TYPE, clz)) {
						//
						list.add(Character.valueOf(' '));
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
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(list));
			//
			toString = Objects.toString(m);
			//
			if (Util.contains(Arrays.asList(Boolean.TYPE, Integer.TYPE), m.getReturnType())
					|| (Objects.equals(name = Util.getName(m), "toMultimapAndIntList") && Arrays.equals(parameterTypes,
							new Class<?>[] { Integer.TYPE, Matcher.class, Matcher.class, Matcher.class }))
					|| (Objects.equals(name, "getCommonSuffix")
							&& Arrays.equals(parameterTypes, new Class<?>[] { String.class, String.class }))
					|| (Objects.equals(name, "toMultimapAndIntList11") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, List.class, Integer.TYPE }))
					|| (Objects.equals(name, "toMultimapAndIntList11") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, List.class, String.class }))
					|| (Objects.equals(name, "toMultimapAndIntList12") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, List.class, Integer.TYPE, Character.TYPE }))
					|| (Util.contains(Arrays.asList("toMultimapAndIntList13a", "toMultimapAndIntList13b",
							"toMultimapAndIntList13c"), name)
							&& Arrays.equals(parameterTypes,
									new Class<?>[] { PatternMap.class, Iterable.class, Integer.TYPE, String.class }))
					|| (Util.contains(Arrays.asList("toMultimapAndIntList13a", "toMultimapAndIntList13b",
							"toMultimapAndIntList15"), name)
							&& Arrays.equals(parameterTypes,
									new Class<?>[] { Integer.TYPE, String.class, PatternMap.class, String.class,
											String.class, String.class }))
					|| (Objects.equals(name, "toMultimapAndIntList17A") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, Iterable.class, Integer.TYPE, String.class }))
					|| (Util.contains(Arrays.asList("toMultimapAndIntList17B", "toMultimapAndIntList17C"), name)
							&& Arrays.equals(parameterTypes,
									new Class<?>[] { PatternMap.class, Iterable.class, Integer.TYPE, String.class,
											Multimap.class }))
					|| (Objects.equals(name, "toMultimapAndIntList19A") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, Iterable.class, Integer.TYPE, Entry.class }))
					|| (Objects.equals(name, "toMultimapAndIntList19B")
							&& Arrays.equals(parameterTypes, new Class<?>[] { PatternMap.class, Iterable.class,
									Integer.TYPE, String.class, Multimap.class }))) {
				//
				Assertions.assertNotNull(invokeStaticMethod, toString);
				//
			} else {
				//
				Assertions.assertNull(invokeStaticMethod, toString);
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
	void testToMultimap1() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("藍", "あい")),
				MultimapUtil.entries(toMultimap(patternMap, "藍（あい）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("藍色鳩羽", "あいいろはとば")),
						MultimapUtil.entries(toMultimap(patternMap, "藍色鳩羽（あいいろはとば）日本の色名"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("藍群青", "あいぐんじょう")),
						MultimapUtil.entries(toMultimap(patternMap, "藍群青（あいぐんじょう）WEB"))));
		//
		Assertions
				.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("藍鑞", "あいろう")),
						MultimapUtil.entries(toMultimap(patternMap, "藍鑞（あいろう）ニコリ"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("あおいろ", "せいしょく"), x -> Pair.of("青色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "青色（あおいろ・せいしょく）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("相生鼠", "藍生鼠"), x -> Pair.of(x, "あいおいねず"))),
				MultimapUtil.entries(toMultimap(patternMap, "相生鼠・藍生鼠（あいおいねず）日本の色名"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("あいけねず", "あいけねずみ"), x -> Pair.of("藍気鼠", x))),
				MultimapUtil.entries(toMultimap(patternMap, "藍気鼠（あいけねず・日本の色名/あいけねずみ・日本国語大辞典）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("あいねず", "あいねずみ"), x -> Pair.of("藍鼠", x))),
				MultimapUtil.entries(toMultimap(patternMap, "藍鼠（あいねず/あいねずみ）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("文色", "あいろ")),
				MultimapUtil.entries(toMultimap(patternMap, "＜文色（あいろ）広辞苑＞"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("あかがねいろ", "どうしょく"), x -> Pair.of("銅色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "銅色（あかがねいろ・どうしょく）日本国語大辞典"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("あかきいろ", "せきおうしょく"), x -> Pair.of("赤黄色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "赤黄色（あかきいろ/せきおうしょく・WEB）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("うめねず", "うめねずみ"), x -> Pair.of("梅鼠", x))),
				MultimapUtil.entries(toMultimap(patternMap, "梅鼠（うめねず／うめねずみ）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("あおはいいろ", "せいかいしょく"), x -> Pair.of("青灰色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "青灰色（あおはいいろ／せいかいしょく・日本国語大辞典）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("赤", "緋", "紅", "朱"), x -> Pair.of(x, "あか"))),
				MultimapUtil.entries(toMultimap(patternMap, "赤・緋・紅・朱（あか）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("青青", "蒼蒼", "碧碧"), x -> Pair.of(x, "あおあお"))),
				MultimapUtil.entries(toMultimap(patternMap, "青青・蒼蒼・碧碧（あおあお）日本国語大辞典"))));
		//
		Assertions
				.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("薄青", "うすあお")),
						MultimapUtil.entries(toMultimap(patternMap, "薄青(うすあお）日本の色名"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("位", "くらい", "色", "いろ")),
						MultimapUtil.entries(toMultimap(patternMap, "＜位の色（くらいのいろ）広辞苑＞"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("赤蘇枋", "あかすおう", "赤蘇芳", "あかずおう")),
				MultimapUtil.entries(toMultimap(patternMap, "赤蘇枋・赤蘇芳（あかすおう・あかずおう）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("真緋", "あけ")),
				MultimapUtil.entries(toMultimap(patternMap, "真緋（あけ）日本語大辞典・色々な色"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("あやめいろ", "しょうぶいろ", "そうぶいろ"), x -> Pair.of("菖蒲色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "菖蒲色（あやめいろ・しょうぶいろ／そうぶいろ・大日本インキ）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("薄藤色", "淡藤色"), x -> Pair.of(x, "うすふじいろ"))),
				MultimapUtil.entries(toMultimap(patternMap, "薄藤色・淡藤色（うすふじいろ）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("うすきむらさき", "うすむらさき", "せんし"), x -> Pair.of("浅紫", x))),
				MultimapUtil.entries(toMultimap(patternMap, "浅紫（うすきむらさき・うすむらさき・せんし）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("あかこういろ", "あかごういろ"), x -> Pair.of("赤香色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "赤香色（あかこういろ・日本の色辞典／あかごういろ・日本語大辞典資料）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("臙脂墨", "えんじずみ")),
						MultimapUtil.entries(toMultimap(patternMap, "＜臙脂墨（えんじずみ）＞"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("おうしょく", "おうしき", "きいろ", "こうしょく"), x -> Pair.of("黄色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "黄色（おうしょく・おうしき・きいろ・こうしょく）"))));
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
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("えんしゅうねず", "えんしゅうねずみ"), x -> Pair.of("遠州鼠", x))),
				MultimapUtil.entries(toMultimap(patternMap, "遠州鼠（えんしゅうねず・日本の色名/えんしゅうねずみ・奇妙な色名事典）"))));
		//
		Assertions
				.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("依毘染", "いびぞめ")),
						MultimapUtil.entries(toMultimap(patternMap, "依毘染（いびぞめ）ＷＥＢ"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("うすき", "あさきき", "あさき"), x -> Pair.of("浅黄", x))),
				MultimapUtil.entries(toMultimap(patternMap, "浅黄（うすき・あさきき・あさき）日本の色名"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("炎色", "焔色"), x -> Pair.of(x, "えんしょく"))),
				MultimapUtil.entries(toMultimap(patternMap, "＜炎色・焔色（えんしょく）＞"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("こうはく", "おうびゃく"), x -> Pair.of("黄白", x))),
				MultimapUtil.entries(toMultimap(patternMap, "黄白（こうはく・おうびゃく）日本国語大辞典＊黄色と白色"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("うすくもねずみ", "うすぐもねず"), x -> Pair.of("薄雲鼠", x))),
				MultimapUtil.entries(toMultimap(patternMap, "薄雲鼠（うすくもねずみ／うすぐもねず・日本の色名）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("おうはくしょく", "こうはくしょく"), x -> Pair.of("黄白色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "黄白色（おうはくしょく・こうはくしょく）WEB"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("からさけいろ", "からざけいろ"), x -> Pair.of("乾鮭色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "乾鮭色（からさけいろ・からざけいろ）色々な色・広辞苑"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("こむらさき", "こきむらさき"), x -> Pair.of("濃紫", x))),
				MultimapUtil.entries(toMultimap(patternMap, "濃紫（こむらさき）奇妙な色名事典＊（こきむらさき）もあり"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("形見", "かたみ", "色", "いろ")),
						MultimapUtil.entries(toMultimap(patternMap, "形見の色（かたみのいろ）広辞苑"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("鴨", "かも", "羽色", "はいろ")),
						MultimapUtil.entries(toMultimap(patternMap, "鴨の羽色（かものはいろ）色々な色"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("仮", "かり", "色", "いろ")),
						MultimapUtil.entries(toMultimap(patternMap, "＜仮の色（かりのいろ）逆引き広辞苑＞"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("金墨", "きんずみ", "銀墨", "ぎんずみ")),
				MultimapUtil.entries(toMultimap(patternMap, "＜金墨（きんずみ）銀墨（ぎんずみ）＞"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("刈安", "苅安", "青茅"), x -> Pair.of(x, "かりやす"))),
				MultimapUtil.entries(toMultimap(patternMap, "刈安・苅安・青茅（かりやす）日本の色名"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("木枯茶", "こがらしちゃ", "木枯茶", "こがれちゃ")),
				MultimapUtil.entries(toMultimap(patternMap, "木枯茶（こがらしちゃ・日本の色名／こがれちゃ・こがらしちゃ・奇妙な色名事典"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("うらばやなぎ", "うらはやなぎ"), x -> Pair.of("裏葉柳", x))),
				MultimapUtil.entries(toMultimap(patternMap, "裏葉柳（うらばやなぎ・日本国語大辞典／うらはやなぎ）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("風色", "かぜいろ", "風", "かぜ", "色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "＜風色（かぜいろ）・風の色（かぜのいろ）広辞苑＞"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("けっしょく", "ちいろ"), x -> Pair.of("血色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "血色（けっしょく・日本の色名／けっしょく・ちいろ・日本国語大辞典）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "根摺の色（ねずりのいろ）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("かんぞういろ", "かぞういろ"), x -> Pair.of("萱草色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "萱草色（①かんぞういろ②かぞういろ）日本国語大辞典"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("しろねず", "しろねずみ"), x -> Pair.of("白鼠", x))),
				MultimapUtil.entries(toMultimap(patternMap, "白鼠（しろねず・色々な色・日本の色名／しろねずみ・日本の色辞典）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("ろくしょういろ", "りょくしょう"), x -> Pair.of("緑青色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "緑青色（ろくしょういろ）WEBで（りょくしょう）あり"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("蝉", "せみ", "羽", "はね", "襲", "かさね")),
				MultimapUtil.entries(toMultimap(patternMap, "蝉の羽の襲（せみのはねのかさね）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("撫子", "なでしこ", "若葉", "わかば", "色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "撫子の若葉の色（なでしこのわかばのいろ）広辞苑"))));
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
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("雌黄", "しおう", "藤黄", "しおう", "藤黄", "とうおう")),
				MultimapUtil.entries(toMultimap(patternMap, "雌黄・藤黄（しおう）WEB・藤黄（とうおう）のWEBあり"))));
		//
		Assertions
				.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("赤橙", "せきとう")),
						MultimapUtil.entries(toMultimap(patternMap, "赤橙（せきとう）元素111の新知識"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("しいにびいろ", "しいにぶいろ"), x -> Pair.of("椎鈍色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "椎鈍色（しいにびいろ・日本の色名／しいにぶいろ・WEB）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("端白", "爪白", "褄白"), x -> Pair.of(x, "つまじろ"))),
				MultimapUtil.entries(toMultimap(patternMap, "＜端白・爪白・褄白（つまじろ）日本国語大辞典"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "＜青枯れ色（あおがれいろ）広辞苑＞"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "勝つ色（かついろ）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("二", "ふた", "色", "いろ")),
						MultimapUtil.entries(toMultimap(patternMap, "二つ色（ふたついろ）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("濃", "こ", "紅", "くれない")),
						MultimapUtil.entries(toMultimap(patternMap, "濃き紅（こきくれない）奇妙な色名事典"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("金糸雀色", "かなりあいろ")),
						MultimapUtil.entries(toMultimap(patternMap, "金糸雀色・カナリア色（かなりあいろ）広辞苑"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("茶", "ちゃ")),
				MultimapUtil.entries(toMultimap(patternMap, "猩々茶（しょうじょうちゃ）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("緋", "ひ")),
				MultimapUtil.entries(toMultimap(patternMap, "猩猩緋・猩々緋（しょうじょうひ）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("しゅし", "あけむらさき"), x -> Pair.of("朱紫", x))),
				MultimapUtil.entries(toMultimap(patternMap, "朱紫（しゅし）日本国語大辞典＊（あけむらさき）は辞典参照"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("織色目", "おりのいろめ")),
						MultimapUtil.entries(toMultimap(patternMap, "織色目(おりのいろめ)"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "むささび色（むささびいろ）＜むささびは鼠+吾と鼠＞"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("皂色", "くりいろ", "皂", "くり", "色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "皂色（くりいろ）*日本の色名には「くり」に上下に「白＋十」もある"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("黒", "くろ", "色", "いろ")),
						MultimapUtil.entries(toMultimap(patternMap, "黒ろ色（くろろいろ）＊ろは臘の月が虫"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "謂わぬ色（いわぬいろ）色々な色・奇妙な色名事典"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "言わぬ色（いわぬいろ）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("朱", "しゅ")),
				MultimapUtil.entries(toMultimap(patternMap, "朱ふつ（しゅふつ＊ふつは祓の左側が糸）日本の色辞典"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "許しの色（ゆるしのいろ）広辞苑"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "しょく")),
				MultimapUtil.entries(toMultimap(patternMap, "条こん色（じょうこんしょく）原色ワイド図鑑＊黒っぽい緑色"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("五", "いつ", "色", "いろ")),
						MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "＜五つの色（いつつのいろ）", "広辞苑＞")))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("吉岡幸雄", "よしおかさちお")),
						MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "吉岡幸雄（よしおか", "さちお）著")))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("御色", "おいろ")),
				MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "＜御色（おいろ）", "広辞苑＞")))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("己", "おの", "色色", "いろいろ", "色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "＜己が色色（おのがいろいろ）広辞苑＞"))));
		//
	}

	@Test
	void testToMultimap4() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("しんく", "しんこう"), x -> Pair.of("真紅", x))),
				MultimapUtil.entries(toMultimap(patternMap, "真紅（しんく）＜注：（しんこう）と読むWEBあり＞"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("せっかっしょく", "せきかっしょく"), x -> Pair.of("赤褐色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "赤褐色（①せっかっしょく・②せきかっしょく）日本の色名・大辞林"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("きからちゃ", "きがらちゃ"), x -> Pair.of("黄唐茶", x))),
				MultimapUtil.entries(toMultimap(patternMap, "黄唐茶（きからちゃ）奇妙な色名事典＜注：（きがらちゃ）と読むWEBあり＞"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("胆", "たん", "色", "いろ")),
						MultimapUtil.entries(toMultimap(patternMap, "胆ば色（たんばいろ＊ばは攀の手が石）色々な色"))));
		//
		Assertions.assertEquals(ImmutableMultimap.of("色", "いろ"), toMultimap(patternMap, "青枯れ色（あおがれいろ）広辞苑"));
		//
		Assertions.assertEquals(ImmutableMultimap.of("色", "いろ"), toMultimap(patternMap, "思ひの色（おもひのいろ）奇妙な色名事典"));
		//
		Assertions.assertEquals(ImmutableMultimap.of("煤竹", "すすたけ"), toMultimap(patternMap, "とうきん煤竹（とうきんすすたけ）日本の色名"));
		//
		Assertions.assertEquals(ImmutableMultimap.of("色", "いろ"), toMultimap(patternMap, "潤み色（うるみいろ）＊広辞苑"));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("むしくりいろ", "むしぐりいろ"), x -> Pair.of("蒸栗色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "蒸栗色（むしくりいろ・奇妙な色名事典／むしぐりいろ・色々な色）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "＜嘆きの色（なげきのいろ）広辞苑＞"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("黄", "き")),
				MultimapUtil.entries(toMultimap(patternMap, "ナポリの黄（なぽりのき）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "惚け色（ぼけいろ）広辞苑"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("赤", "あか")),
				MultimapUtil.entries(toMultimap(patternMap, "ベニス赤（べにすあか）"))));
		//
		Assertions
				.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("翡翠", "かわせみ")),
						MultimapUtil.entries(toMultimap(patternMap, "翡翠（かわせみ）日本の色名*翠色の同類語"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("琅", "ろう", "色", "いろ")),
						MultimapUtil.entries(toMultimap(patternMap, "琅かん色（ろうかんいろ・かんは杆の左側が王）奇妙な色名事典"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("藍", "らん", "色", "しょく")),
						MultimapUtil.entries(toMultimap(patternMap, "藍てん色（らんてんしょく）日本国語大辞典＊（てん）は左が青の月が円+右が定"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("二", "ふた", "色", "いろ")),
						MultimapUtil.entries(toMultimap(patternMap, "二つ色（ふたついろ）色々な色"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("のうたん", "じょうたん"), x -> Pair.of("濃淡", x))),
				MultimapUtil.entries(toMultimap(patternMap, "＜濃淡（のうたん・じょうたん）日本国語大辞典＞"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("こくかいしょく", "こくはいしょく"), x -> Pair.of("黒灰色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "黒灰色（こくかいしょく・原色ワイド図鑑/こくはいしょく・WEB）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("酸化", "さんか")),
				MultimapUtil.entries(toMultimap(patternMap, "酸化チタン（さんかチタン）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("橡墨染", "つるばみすみぞめ")),
						MultimapUtil.entries(toMultimap(patternMap, "橡墨染（つるばみすみぞめ)WEB"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("しゃくどういろ", "あかがねいろ"), x -> Pair.of("赤銅色", x))),
				MultimapUtil.entries(toMultimap(patternMap, "赤銅色（しゃくどういろ・広辞苑・大辞林・日本語大辞典／しゃくどういろ・あかがねいろ・色々な色）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("朱華", "唐棣花", "棠棣", "翼酢", "波泥孺"), x -> Pair.of(x, "はねず"))),
				MultimapUtil.entries(toMultimap(patternMap, "朱華・唐棣花・棠棣・翼酢・波泥孺（はねず）日本の色名"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "映え色（はえいろ）"))));
		//
		Assertions
				.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("煤竹", "すすたけ")),
						MultimapUtil.entries(toMultimap(patternMap, "ないき煤竹（ないきすすたけ）"))));
		//
	}

	@Test
	void testToMultimap5() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "寝惚け色（ねぼけいろ）広辞苑"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ", "勝", "かち")),
						MultimapUtil.entries(toMultimap(patternMap, "勝色・勝ち色（かちいろ）色々な色・広辞苑"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("色", "いろ")),
				MultimapUtil.entries(toMultimap(patternMap, "移し色・移色（うつしいろ）広辞苑・日本の色名"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("濃", "のう")),
				MultimapUtil.entries(toMultimap(patternMap, "濃オリーブ（のうおりーぶ）原色ワイド図鑑"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("砥", "と")),
				MultimapUtil.entries(toMultimap(patternMap, "砥の粉色・砥粉色（とのこいろ）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("重色目", "かさねのいろめ", "襲", "かさね", "色目", "いろめ")),
				MultimapUtil.entries(toMultimap(patternMap,
						"重色目（かさねのいろめ・「色々な色」より）／襲の色目（かさねのいろめ・「日本の色辞典」より）についても読み方を調べましたので付け足しておきます。"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("冬", "とう")), MultimapUtil
						.entries(toMultimap(patternMap, "かん冬（かんとう・やまぶき・日本語大辞典資料／かんとう・つわぶき・日本の色名）＊かん・やま・つわは疑の右側が欠"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("紫", "むらさき")),
				MultimapUtil.entries(toMultimap(patternMap, "薄色・浅紫（うすき）日本の色名*浅紫は（うすきむらさき）とも"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("花", "はな")),
				MultimapUtil.entries(toMultimap(patternMap, "花つわ冬（はなつわぶき）日本の色名（*つわは疑の右側が欠）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("聴色", "ゆるしいろ", "色", "いろ")),
						MultimapUtil.entries(toMultimap(patternMap, "聴し色・聴色（ゆるしいろ）色々な色・奇妙な色名事典"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				Util.toList(Util.map(Stream.of("うすあけ", "うすきあけ", "あさひ", "あさあけ", "せんぴ", "あさきあけ"), x -> Pair.of("浅緋", x))),
				MultimapUtil.entries(
						toMultimap(patternMap, "浅緋（うすあけ・大日本インキ／うすきあけ・奇妙な色名事典／あさひ・うすあけ・あさあけ・せんぴ・日本国語大辞典/あさきあけ・ニコリ）"))));
		//
	}

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP2.invoke(null, patternMap, s);
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

	private static IValue0<Multimap<String, String>> toMultimap(final PatternMap patternMap, final String s1,
			final String s2) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP3.invoke(null, patternMap, s1, s2);
			if (obj == null) {
				return null;
			} else if (obj instanceof IValue0) {
				return (IValue0) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApplyAsInt() throws Throwable {
		//
		Assertions.assertEquals(ZERO, testAndApplyAsInt(x -> x == ZERO, ZERO, null, null, ZERO));
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

	@Test
	void testContainsInt() throws Throwable {
		//
		final IntList intList = IntList.create();
		//
		IntCollectionUtil.addInt(intList, ZERO);
		//
		Assertions.assertTrue(containsInt(intList, ZERO));
		//
	}

	private static boolean containsInt(final IntIterable instance, final int o) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS_INT.invoke(null, instance, o);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testRemoveValue() throws Throwable {
		//
		final IntList il2 = IntList.create();
		//
		forEach(IntStream.range(0, IterableUtils.size(il2)), x -> IntCollectionUtil.addInt(il2, x));
		//
		Assertions.assertDoesNotThrow(() -> removeValue(il2, IterableUtils.size(il2) - 1));
		//
	}

	private static void removeValue(final IntList instance, final int o) throws Throwable {
		try {
			METHOD_REMOVE_INT.invoke(null, instance, o);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFlatMap() throws Throwable {
		//
		final Stream<?> stream = Stream.empty();
		//
		Assertions.assertNull(flatMap(stream, null));
		//
		Assertions.assertNotNull(flatMap(stream, x -> null));
		//
	}

	private static <T, R> Stream<R> flatMap(final Stream<T> instance,
			final Function<? super T, ? extends Stream<? extends R>> mapper) throws Throwable {
		try {
			final Object obj = METHOD_FLAT_MAP.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAddAll() throws Throwable {
		//
		final IntList intList = IntList.create();
		//
		Assertions.assertDoesNotThrow(() -> Util.addAll(intList, null));
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> Util.addAll(intList, intList));
		//
	}

	@Test
	void testToMultimapAndIntList1() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("{\"{色=[いろ]}\":[0,1,2]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("勝つ色（かついろ）", "逆引き", "熟語林"), 0))));
		//
		Assertions.assertEquals("{\"{小色=[こいろ]}\":[0,1,2]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("＜小色（こいろ）", "逆引き", "熟語林＞"), 0))));
		//
		Assertions.assertEquals("{\"{薄肉色=[うすにくいろ]}\":[0,1,2]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("薄肉色（うすにくいろ）", "逆引き", "熟語林"), 0))));
		//
		Assertions.assertEquals("{\"{色=[いろ]}\":[0,1,2]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("言わぬ色（いわぬいろ）", "逆引き", "熟語林"), 0))));
		//
		Assertions.assertEquals("{\"{色=[いろ]}\":[0,1,2]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("焼け色（やけいろ）", "逆引き", "熟語林"), 0))));
		//
		Assertions.assertEquals("{\"{八入=[やしお], 色=[いろ]}\":[0,1,2]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("＜八入の色（やしおのいろ）", "逆引き", "熟語林＞"), 0))));
		//
		Assertions.assertEquals("{\"{臙脂=[えんじ], 燕支=[えんじ], 燕脂=[えんじ], 烟脂=[えんじ], 烟子=[えんじ], 色=[いろ]}\":[0,1,2]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("臙脂・燕支・燕脂・烟脂（えんじ）日本国語大辞典", "烟子（えんじ）WEB", "臙脂色（えんじいろ）＜注：臙が燕のWEBあり＞"), 0))));
		//
		Assertions
				.assertEquals("{\"{褐色=[かっしょく, かちいろ, かちんいろ]}\":[0,1]}",
						ObjectMapperUtil.writeValueAsString(objectMapper,
								convert(toMultimapAndIntList(patternMap, Arrays.asList(
										" 褐色（かっしょく・日本語大辞典／かっしょく・かちいろ・色々な色／かっしょく・かちいろ・かちんいろ・日本の色辞典）＜注：（かっしょく）は茶系",
										"（かちいろ・かちんいろ）は青系＞"), 0))));
		//
		Assertions.assertEquals("{\"{端白=[つまじろ, つましろ], 爪白=[つまじろ, つましろ], 褄白=[つまじろ, つましろ]}\":[1,2,3]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("＜端白・爪白・褄白（つまじろ）日本国語大辞典", "（つましろ）とも", "とあり", "はしが白いこと＞"), 0))));
		//
		Assertions.assertEquals("{\"{濃藍色=[のうらんしょく, こあいいろ, こいあいいろ]}\":[0,1]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("濃藍色（のうらんしょく・日本国語大辞典", "／のうらんしょく・こあいいろ・こいあいいろ・WEB）"), 0))));
		//
		Assertions.assertEquals("{\"{重色目=[かさねのいろめ], 襲=[かさね], 色目=[いろめ]}\":[0,1]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("＜重色目（かさねのいろめ）／襲の色目（かさねのいろめ）／参考WEB", "＞の説明に出てくる関連の言葉"), 0))));
		//
		Assertions.assertEquals("{\"{浅梔子=[あさくちなし], 浅=[あさ]}\":[0,1]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("浅梔子（あさくちなし）", "浅黒い（あさぐろい）"), 0))));
		//
		Assertions.assertEquals("{\"{浅支子=[うすくちなし], 薄梔子=[うすくちなし], 薄=[うす]}\":[0,1]}", ObjectMapperUtil.writeValueAsString(
				objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList(" 浅支子・薄梔子（うすくちなし）日本国語大辞典", "薄黒い（うすぐろい）"), 0))));
		//
		Assertions.assertEquals("{\"{生壁鼠=[なまかべねず], 生=[なま]}\":[0,1]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("生壁鼠（なまかべねず）", "生白い（なまじろい）日本国語大辞典"), 0))));
		//
		Assertions.assertEquals("{\"{黄=[き]}\":[0,4]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("＜黄黒い・きぐろい・日本国語大辞典＞", null, null, null, "＜黄白い・きしろい・日本国語大辞典＞"), 0))));
		//
		Assertions.assertEquals("{\"{生成=[きなり], 色=[いろ]}\":[0,1]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("生成（きなり）", " 生成り色・生成色（きなりいろ）"), 0))));
		//
		Assertions.assertEquals("{\"{渋墨=[しぶずみ], 渋=[しぶ]}\":[0,1]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("渋黒い（しぶぐろい）", "＜渋墨（しぶずみ）＞"), 0))));
		//
		Assertions.assertEquals("{\"{黒緋=[くろあけ], 深緋=[くろあけ], 黒色=[くろいろ], 黒=[くろ], 色=[いろ], 青=[あお], 赤=[あか]}\":[0,1,2,3]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("黒青い（くろあおい）", "黒赤い（くろあかい）", "黒緋・深緋（くろあけ）日本国語大辞典", " 黒色（くろいろ）"), 0))));
		//
		Assertions.assertEquals("{\"{琥珀色=[こはくいろ], 色=[いろ]}\":[0,1]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("琥珀色（こはくいろ）", "珈琲色（コーヒーいろ）"), 0))));
		//
		Assertions.assertEquals("{\"{灰黄=[はいき], 灰黄色=[はいきいろ], 色=[いろ], 灰=[はい], 黄=[き]}\":[0,1,2]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("灰黄（はいき）WEB", "灰黄色（はいきいろ）WEB", "灰黒し（はいぐろし）"), 0))));
		//
		Assertions.assertEquals("{\"{濃茶=[こいちゃ], 濃茶色=[こいちゃいろ], 濃納戸=[こいなんど], 色=[いろ], 納戸=[なんど], 茶=[ちゃ]}\":[0,1,2]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("濃茶（こいちゃ）", " 濃い茶色・濃茶色（こいちゃいろ）", "濃納戸（こいなんど）"), 0))));
		//
		Assertions.assertEquals("{\"{白銀色=[しろがねいろ], 白菫色=[しろすみれいろ], 白=[しろ], 色=[いろ], 白銀=[しろがね], 菫=[すみれ]}\":[0,1,2]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("白銀色（しろがねいろ）", "白殺し（しろころし・色々な色／しろごろし・日本の色辞典）", "白菫色（しろすみれいろ）"), 0))));
		//
		Assertions.assertEquals("{\"{山葵色=[わさびいろ], 勿忘草色=[わすれなぐさいろ], 色=[いろ], 山葵=[わさび]}\":[0,1,2]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList(" 山葵色（わさびいろ）", "忘れ草（わすれぐさ）日本の色名", "勿忘草色（わすれなぐさいろ）"), 0))));
		//
		Assertions.assertEquals(
				"{\"{鳩羽色=[はとばいろ], 鳩羽紫=[はとばむらさき], 鳩羽鼠=[はとばねず, はとばねずみ], 色=[いろ], 紫=[むらさき], 鼠=[ねず, ねずみ], 鳩羽=[はとば]}\":[0,1,2]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("鳩羽色（はとばいろ）", "鳩羽鼠（はとばねず・色々な色/はとばねずみ・日本の色辞典）", "鳩羽紫（はとばむらさき）"), 0))));
		//
		Assertions.assertEquals(
				"{\"{浅縹=[あさはなだ], 浅緋=[あさひ, うすあけ, あさあけ, せんぴ, あさきあけ], 浅=[あさ], 縹=[はなだ], 緋=[ひ, あけ]}\":[0,1]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("浅縹（あさはなだ）", "浅緋（あさひ・うすあけ・あさあけ・せんぴ・日本国語大辞典/あさきあけ・ニコリ）"), 0))));
		//
	}

	@Test
	void testToMultimapAndIntList2() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("{\"{青苔=[あおごけ], 青磁=[あおじ], 青瓷=[せいじ], 青=[あお], 苔=[ごけ], 磁=[じ]}\":[0,1]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(
						toMultimapAndIntList(patternMap, Arrays.asList("青苔（あおごけ）日本の色名", " 青磁・青瓷（あおじ／せいじ・日本の色名）"), 0))));
		//
		Assertions.assertEquals("{\"{青墨=[あおずみ], 青白い=[あおじろい], 蒼白い=[あおじろい], 青=[あお]}\":[0,1]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("青白い・蒼白い（あおじろい）広辞苑・日本語大辞典・大辞林", " 青墨（あおずみ）"), 0))));
		//
		Assertions.assertEquals("{\"{空色=[そらいろ], 蜜色=[みついろ], 蜜=[みつ], 色=[いろ], 空=[そら]}\":[0,1]}",
				ObjectMapperUtil.writeValueAsString(objectMapper,
						convert(toMultimapAndIntList(patternMap, Arrays.asList("み空色（みそらいろ）", "蜜色（みついろ）"), 0))));
		//
		Assertions.assertEquals("{\"{皆練=[かいねり], 皆=[かい], 白練=[しろねり], 白=[しろ], 練色=[ねりいろ], 色=[いろ]}\":[0,1,3,6,10,15]}",
				ObjectMapperUtil.writeValueAsString(objectMapper,
						convert(toMultimapAndIntList(patternMap,
								Arrays.asList("かい練（かいねり", "かいは掻の又の中と左に｀がある）日本の色辞典", null, " 皆練（かいねり）日本の色名", null, null,
										" 皆練（かいねり）日本の色名", null, null, null, " 白練（しろねり）日本の色名", null, null, null, null,
										"練色（ねりいろ）"),
								0))));
		//
		Assertions.assertEquals("{\"{朽葉鼠=[くちばねず], 鼠=[ねず], 朽葉=[くちば], 雲井鼠=[くもいねず], 雲井=[くもい]}\":[0,1]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("朽葉鼠（くちばねず）", "雲井鼠・雲居？鼠（くもいねず）日本の色名*資料に？あり"), 0))));
		//
		Assertions.assertEquals(
				"{\"{鴇浅葱=[ときあさぎ], 鴇色=[ときいろ], 鴇=[とき], 朱鷺色=[ときいろ], 桃花鳥色=[ときいろ], 紅鶴色=[ときいろ], 時色=[ときいろ], 浅葱=[あさぎ], 色=[いろ]}\":[0,1]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("鴇浅葱（ときあさぎ）", "鴇色・朱鷺色・桃花鳥色・紅鶴色・朱鷺色・時色（ときいろ）日本の色名"), 0))));
		//
		Assertions.assertEquals("{\"{青白橡=[あおしらつるばみ, あおしろつるばみ], 青=[あお], 白=[しろ, しら], 橡=[つるばみ]}\":[0,2,5]}",
				ObjectMapperUtil.writeValueAsString(objectMapper,
						convert(toMultimapAndIntList(patternMap,
								Arrays.asList("青白橡（あおしらつるばみ･広辞苑・大辞林／あおしろつるばみ・日本語大辞典資料／あおしろつるばみ・あおしらつるばみ・日本の色辞典）", null,
										"青（あお）", null, null, "白（しろ）"),
								0))));
		//
		Assertions.assertEquals("{\"{退紅=[あらそめ, あらぞめ], 褪染=[あらそめ, あらぞめ], あらそめ=[たいこう]}\":[0,1,2]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList(" 退紅・褪染（あらそめ・あらぞめ・退紅は", "たいこう", "とも）日本の色名・色々な色"), 0))));
		//
		Assertions.assertEquals("{\"{瓶覗=[かめのぞき], 甕覗=[かめのぞき], 覗色=[のぞきいろ], 覗=[のぞき], 瓶=[かめ], 甕=[かめ], 色=[いろ]}\":[0,1,2]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("瓶覗・瓶覗き（かめのぞき）日本語大辞典・色々な色", "甕覗（かめのぞき）日本の色辞典", "覗色・覗き色（のぞきいろ）"), 0))));
		//
		Assertions.assertEquals(
				"{\"{赭=[そほ, そお, しゃ], 暗赭色=[あんしゃしょく], 赭褐色=[しゃかっしょく], 赭紅=[しゃこう], 赭黒=[しゃこく], 赭黒色=[しゃこくしょく], 赭色=[しゃいろ, しゃしょく], 赭土=[そおに, そほに], 赭黄=[しゃおう], 柘黄=[しゃおう], 暗=[あん], 色=[しょく, いろ], 褐色=[かっしょく], 紅=[こう], 黒=[こく], 黒色=[こくしょく], 黄=[おう]}\":[0,1,2,5,6,7,8,9,3,10,4]}",
				ObjectMapperUtil.writeValueAsString(objectMapper,
						convert(toMultimapAndIntList(patternMap,
								Arrays.asList("赭（そほ）日本の色辞典／（そほ・そお・しゃ）日本国語大辞典）", "暗赭色（あんしゃしょく）", "赭（しゃ・そお・そほ）日本国語大辞典",
										"赭色（しゃいろ・しゃしょく）日本国語大辞典", " 赭黄・柘黄（しゃおう）日本の色名", "赭褐色（しゃかっしょく）", "赭紅（しゃこう）",
										"赭黒（しゃこく）", "赭黒色（しゃこくしょく）", "赭（そお・そほ・しゃ）日本国語大辞典", "赭土（そおに・そほに）"),
								0))));
		//
		Assertions.assertEquals("{\"{搗染=[かちそめ], 染=[そめ], 桑色=[くわいろ], 桑茶=[くわちゃ], 桑=[くわ], 色=[いろ], 茶=[ちゃ]}\":[0,1,2,3,4]}",
				ObjectMapperUtil.writeValueAsString(objectMapper,
						convert(toMultimapAndIntList(patternMap, Arrays.asList("桑・桑染（くわそめ・日本の色名／くわぞめ・日本国語大辞典）",
								"搗染（かちそめ）色々な色", "桑色（くわいろ）", "桑茶（くわちゃ）", "搗染（かちそめ）色々な色"), 0))));
		//
		Assertions.assertEquals(
				"{\"{牡丹色=[ぼたんいろ], 牡丹=[ぼうたん, ぼたん], 蛍光牡丹=[けいこうぼたん], 蛍光=[けいこう], 牡丹鼠=[ぼたんねず], 鼠=[ねず], 色=[いろ], 牡丹紫=[ぼたんむらさき], 紫=[むらさき], 襲=[かさね]}\":[0,1,2,3,4]}",
				ObjectMapperUtil.writeValueAsString(objectMapper,
						convert(toMultimapAndIntList(patternMap, Arrays.asList("牡丹色（ぼたんいろ）＜注：牡丹（ぼうたん）とも読むWEBあり＞",
								"蛍光牡丹（けいこうぼたん）WEB", "牡丹鼠（ぼたんねず）", "牡丹紫（ぼたんむらさき）", "牡丹の襲（ぼたんのかさね）"), 0))));
		//
		Assertions.assertEquals(
				"{\"{丼鼠=[どぶねず, どぶねずみ], 溝鼠=[どぶねず, どぶねずみ], 藍鼠=[あいねず, あいねずみ], 青鼠=[あおねず], 鼠=[ねず, ねずみ], 青=[あお], 薄鼠=[うすねず, うすねずみ], 薄=[うす], 藍=[あい], 濃鼠色=[こいねずみいろ, こねずみいろ], 濃=[こい], 色=[いろ], 薄藍鼠=[うすあいねずみ]}\":[0,1,2,3,6,4,7,5]}",
				ObjectMapperUtil.writeValueAsString(objectMapper,
						convert(toMultimapAndIntList(patternMap,
								Arrays.asList("丼鼠・溝鼠（どぶねず・日本の色名／どぶねずみ・日本の色辞典）", "藍鼠（あいねず/あいねずみ）", "青鼠（あおねず）",
										"薄鼠（うすねず・うすねずみ）", "濃鼠色（こいねずみいろ・こねずみいろ）", "むささび色（むささびいろ）＜むささびは鼠+吾と鼠＞",
										"薄藍鼠（うすあいねずみ）WEB", "濃鶯茶（こいうぐいすちゃ）WEB"),
								0))));
		//
		Assertions.assertEquals(
				"{\"{紺村濃=[こんむらご, こうむらご, こむらご], 紺斑濃=[こんむらご, こうむらご, こむらご], 紺=[こん], 紺藍=[こんあい], 藍=[あい], 紺藍色=[こんあいいろ], 藍色=[あいいろ], 紺青色=[こんじょういろ], 青色=[じょういろ], 色=[いろ], 紫=[むらさき], 村濃=[むらご]}\":[0,1,2,3,4]}",
				ObjectMapperUtil
						.writeValueAsString(objectMapper,
								convert(toMultimapAndIntList(
										patternMap, Arrays.asList("紺村濃・紺斑濃（こんむらご・こうむらご・こむらご）日本国語大辞典", "紺（こん）",
												"紺藍（こんあい）", "紺藍色（こんあいいろ）", "紺青色（こんじょういろ）", "紫の村濃（むらさきのむらご）", "村濃（むらご）"),
										0))));
		//
		Assertions.assertEquals("{\"{生=[なま], 壁=[かべ], 色=[いろ], 鼠=[ねず]}\":[0,1,2]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("生白い（なまじろい）日本国語大辞典", "生壁色（なまかべいろ）", "生壁鼠（なまかべねず）"), 0))));
		//
		Assertions.assertEquals("{\"{濡色=[ぬれいろ], 色=[いろ], 濡烏=[ぬれがらす], 濡=[ぬれ], 濡羽色=[ぬればいろ], 濡葉色=[ぬればいろ]}\":[0,1,3,2,4,5]}",
				ObjectMapperUtil.writeValueAsString(objectMapper,
						convert(toMultimapAndIntList(patternMap, Arrays.asList(" 濡色・濡れ色（ぬれいろ）色々な色", "濡烏（ぬれがらす）色々な色",
								"濡れ烏（ぬれがらす）奇妙な色名事典", "濡羽色（ぬればいろ）奇妙な色名事典", "濡れ羽色（ぬればいろ）色々な色", "濡葉色・濡れ葉色（ぬればいろ）奇妙な色名事典"),
								0))));
		//
	}

	private static Entry<Multimap<String, String>, int[]> convert(
			final Entry<Multimap<String, String>, IntList> instance) throws Throwable {
		return Pair.of(Util.getKey(instance), IntCollectionUtil.toIntArray(Util.getValue(instance)));
	}

	private static Entry<Multimap<String, String>, IntList> toMultimapAndIntList(final PatternMap patternMap,
			final List<String> list, final int i) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_AND_INT_LIST.invoke(null, patternMap, list, i);
			if (obj == null) {
				return null;
			} else if (obj instanceof Entry) {
				return (Entry) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCollect() throws Throwable {
		//
		final IntStream intStream = IntStream.empty();
		//
		Assertions.assertNull(collect(intStream, null, null, null));
		//
		Assertions.assertNull(collect(intStream, null, null, (a, b) -> {
		}));
		//
		Assertions.assertNull(collect(intStream, () -> null, null, (a, b) -> {
		}));
		//
	}

	private static <R> R collect(final IntStream instance, final Supplier<R> supplier,
			final ObjIntConsumer<R> accumulator, final BiConsumer<R, R> combiner) throws Throwable {
		try {
			return (R) METHOD_COLLECT.invoke(null, instance, supplier, accumulator, combiner);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMap() throws Throwable {
		//
		final IntStream intStream = IntStream.empty();
		//
		Assertions.assertSame(intStream, map(intStream, null));
		//
	}

	private static IntStream map(final IntStream instance, final IntUnaryOperator mapper) throws Throwable {
		try {
			final Object obj = METHOD_MAP.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof IntStream) {
				return (IntStream) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, 0, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> false, null, 0, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b, c) -> true, null, null, null, null));
		//
	}

	private static <T> void testAndAccept(final Predicate<T> predicate, final T value, final Consumer<T> consumer)
			throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT3.invoke(null, predicate, value, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T> void testAndAccept(final ObjIntPredicate<T> predicate, final T o, final int i,
			final ObjIntConsumer<T> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT4.invoke(null, predicate, o, i, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <A, B, C> void testAndAccept(final TriPredicate<A, B, C> predicate, final A a, final B b, final C c,
			final TriConsumer<A, B, C> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT5.invoke(null, predicate, a, b, c, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToArray() throws Throwable {
		//
		Assertions.assertNull(toArray(Collections.emptyList(), null));
		//
		Assertions.assertNull(toArray(Stream.empty(), null));
		//
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] a) throws Throwable {
		try {
			return (T[]) METHOD_TO_ARRAY_COLLECTION.invoke(null, instance, a);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <A> A[] toArray(final Stream<?> instance, final IntFunction<A[]> generator) throws Throwable {
		try {
			return (A[]) METHOD_TO_ARRAY_STREAM.invoke(null, instance, generator);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testForEach() {
		//
		Assertions.assertDoesNotThrow(() -> forEach(IntStream.empty(), null));
		//
	}

	private static void forEach(final IntStream instance, final IntConsumer action) throws Throwable {
		try {
			METHOD_FOR_EACH_INT_STREAM.invoke(null, instance, action);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIsEmpty() throws Throwable {
		//
		Assertions.assertTrue(isEmpty(IntList.create()));
		//
	}

	private static boolean isEmpty(final IntList instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_EMPTY.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMax() throws Throwable {
		//
		Assertions.assertNull(max(Stream.empty(), null));
		//
	}

	private static <T> Optional<T> max(final Stream<T> instance, final Comparator<? super T> comparator)
			throws Throwable {
		try {
			final Object obj = METHOD_MAX.invoke(null, instance, comparator);
			if (obj == null) {
				return null;
			} else if (obj instanceof Optional) {
				return (Optional) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMultimap17C2() throws Throwable {
		//
		Assertions.assertNull(toMultimap17C2(Collections.singleton(null), null));
		//
	}

	private static Multimap<String, String> toMultimap17C2(final Iterable<Entry<String, String>> entries,
			final Entry<String, String> entry) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_17_C_2.invoke(null, entries, entry);
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
	void testAddAllInts() {
		//
		Assertions.assertDoesNotThrow(() -> addAllInts(IntList.create(), null));
		//
	}

	private static void addAllInts(final IntCollection a, final IntCollection b) throws Throwable {
		try {
			METHOD_ADD_ALL_INTS.invoke(null, a, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}