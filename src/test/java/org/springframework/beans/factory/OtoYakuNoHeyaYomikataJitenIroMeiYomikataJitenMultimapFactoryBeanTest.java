package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.util.IntList;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
			METHOD_TEST_AND_APPLY_AS_INT, METHOD_CONTAINS, METHOD_REMOVE_VALUE, METHOD_FLAT_MAP, METHOD_ADD_ALL,
			METHOD_TO_MULTI_MAP_AND_INT_LIST, METHOD_COLLECT, METHOD_MAP;

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
		(METHOD_CONTAINS = clz.getDeclaredMethod("contains", IntList.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_REMOVE_VALUE = clz.getDeclaredMethod("removeValue", IntList.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_FLAT_MAP = clz.getDeclaredMethod("flatMap", Stream.class, Function.class)).setAccessible(true);
		//
		(METHOD_ADD_ALL = clz.getDeclaredMethod("addAll", IntList.class, IntList.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_AND_INT_LIST = clz.getDeclaredMethod("toMultimapAndIntList", PatternMap.class, List.class,
				Integer.TYPE)).setAccessible(true);
		//
		(METHOD_COLLECT = clz.getDeclaredMethod("collect", IntStream.class, Supplier.class, ObjIntConsumer.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_MAP = clz.getDeclaredMethod("map", IntStream.class, IntUnaryOperator.class)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject;

	private PatternMap patternMap;

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
		String toString;
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
					|| Objects.equals(Util.getName(m), "toMultimapAndIntList") && Arrays.equals(m.getParameterTypes(),
							new Class<?>[] { Integer.TYPE, Matcher.class, Matcher.class, Matcher.class })) {
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
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(contains(Util.cast(IntList.class, Narcissus.allocateInstance(IntList.class)), ZERO));
		//
		final IntList intList = new IntList();
		//
		intList.add(ZERO);
		//
		Assertions.assertTrue(contains(intList, ZERO));
		//
	}

	private static boolean contains(final IntList instance, final int o) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS.invoke(null, instance, o);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testRemoveValue() {
		//
		Assertions.assertDoesNotThrow(
				() -> removeValue(Util.cast(IntList.class, Narcissus.allocateInstance(IntList.class)), ZERO));
		//
	}

	private static void removeValue(final IntList instance, final int o) throws Throwable {
		try {
			METHOD_REMOVE_VALUE.invoke(null, instance, o);
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
		final IntList intList = Util.cast(IntList.class, Narcissus.allocateInstance(IntList.class));
		//
		Assertions.assertDoesNotThrow(() -> addAll(intList, null));
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> addAll(intList, intList));
		//
	}

	private static void addAll(final IntList a, final IntList b) throws Throwable {
		try {
			METHOD_ADD_ALL.invoke(null, a, b);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMultimapAndIntList() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		final ObjectMapper objectMapper = new ObjectMapper();
		//
		Assertions.assertEquals("{\"{色=[いろ]}\":[1,2]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("勝つ色（かついろ）", "逆引き", "熟語林"), 0))));
		//
		Assertions.assertEquals("{\"{小色=[こいろ]}\":[1,2]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("＜小色（こいろ）", "逆引き", "熟語林＞"), 0))));
		//
		Assertions.assertEquals("{\"{薄肉色=[うすにくいろ]}\":[1,2]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("薄肉色（うすにくいろ）", "逆引き", "熟語林"), 0))));
		//
		Assertions.assertEquals("{\"{色=[いろ]}\":[1,2]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("言わぬ色（いわぬいろ）", "逆引き", "熟語林"), 0))));
		//
		Assertions.assertEquals("{\"{色=[いろ]}\":[1,2]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("焼け色（やけいろ）", "逆引き", "熟語林"), 0))));
		//
		Assertions.assertEquals("{\"{八入=[やしお], 色=[いろ]}\":[1,2]}", ObjectMapperUtil.writeValueAsString(objectMapper,
				convert(toMultimapAndIntList(patternMap, Arrays.asList("＜八入の色（やしおのいろ）", "逆引き", "熟語林＞"), 0))));
		//
		Assertions.assertEquals("{\"{臙脂=[えんじ], 燕支=[えんじ], 燕脂=[えんじ], 烟脂=[えんじ], 烟子=[えんじ], 色=[いろ]}\":[1,2]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("臙脂・燕支・燕脂・烟脂（えんじ）日本国語大辞典", "烟子（えんじ）WEB", "臙脂色（えんじいろ）＜注：臙が燕のWEBあり＞"), 0))));
		//
		Assertions
				.assertEquals("{\"{褐色=[かっしょく, かちいろ, かちんいろ]}\":[1,2]}",
						ObjectMapperUtil.writeValueAsString(objectMapper,
								convert(toMultimapAndIntList(patternMap, Arrays.asList(
										" 褐色（かっしょく・日本語大辞典／かっしょく・かちいろ・色々な色／かっしょく・かちいろ・かちんいろ・日本の色辞典）＜注：（かっしょく）は茶系",
										"（かちいろ・かちんいろ）は青系＞"), 0))));
		//
		Assertions.assertEquals("{\"{端白=[つまじろ, つましろ], 爪白=[つまじろ, つましろ], 褄白=[つまじろ, つましろ]}\":[1,2,3]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("＜端白・爪白・褄白（つまじろ）日本国語大辞典", "（つましろ）とも", "とあり", "はしが白いこと＞"), 0))));
		//
		Assertions.assertEquals("{\"{濃藍色=[のうらんしょく, こあいいろ, こいあいいろ]}\":[1,2]}",
				ObjectMapperUtil.writeValueAsString(objectMapper, convert(toMultimapAndIntList(patternMap,
						Arrays.asList("濃藍色（のうらんしょく・日本国語大辞典", "／のうらんしょく・こあいいろ・こいあいいろ・WEB）"), 0))));
		//
	}

	private static Entry<Multimap<String, String>, int[]> convert(
			final Entry<Multimap<String, String>, IntList> instance) {
		return Pair.of(Util.getKey(instance), toArray(Util.getValue(instance)));
	}

	private static int[] toArray(final IntList instance) {
		return instance != null ? instance.toArray() : null;
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

	private static IntStream map(final IntStream instance, final IntUnaryOperator mapper)
			throws IllegalAccessException, IllegalArgumentException, Throwable {
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

}