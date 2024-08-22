package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_TO_MULTI_MAP;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class)).setAccessible(true);
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
		Object invokeStaticMethod;
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
			invokeStaticMethod = Narcissus.invokeStaticMethod(m,
					toArray(Collections.nCopies(m.getParameterCount(), null)));
			//
			toString = Objects.toString(m);
			//
			if (Objects.equals(Boolean.TYPE, m.getReturnType())) {
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
				Util.toList(Util.map(Stream.of("臙脂", "燕支", "燕脂", "烟脂"), x -> Pair.of(x, "えんじ"))),
				MultimapUtil.entries(toMultimap(patternMap, "臙脂・燕支・燕脂・烟脂（えんじ）日本国語大辞典"))));
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
	}

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final String s) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP.invoke(null, patternMap, s);
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

}