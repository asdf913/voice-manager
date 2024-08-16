package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.javatuples.valueintf.IValue0;
import org.javatuples.valueintf.IValue0Util;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_TO_MULTI_MAP2, METHOD_TO_MULTI_MAP3 = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP2 = clz.getDeclaredMethod("toMultimap", PatternMap.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP3 = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class, String.class))
				.setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject = false;

	private PatternMap patternMap = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBeanTest.testGetObject");
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
		final Multimap<?, ?> multimap = ImmutableMultimap.of();
		//
		Assertions.assertEquals(multimap, getObject(instance));
		//
		final Entry<Field, Object> entry = TestUtil.getFieldWithUrlAnnotationAndValue(
				OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean.class);
		//
		final Field url = Util.getKey(entry);
		//
		Narcissus.setObjectField(instance, url, "");
		//
		Assertions.assertEquals(multimap, getObject(instance));
		//
		Narcissus.setObjectField(instance, url, " ");
		//
		Assertions.assertEquals(multimap, getObject(instance));
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
		final Method[] ms = OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean.class
				.getDeclaredMethods();
		//
		Method m = null;
		//
		Collection<Object> list = null;
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
			list.addAll(Collections.nCopies(m.getParameterCount(), null));
			//
			Assertions.assertNull(Narcissus.invokeStaticMethod(m, toArray(list)), Objects.toString(m));
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
	void testToMultimap() throws Throwable {
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertEquals(ImmutableMultimap.of("大化", "たいか"),
					IValue0Util.getValue0(toMultimap(patternMap, "大化", "（たいか）")));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("けいうん", "きょううん"), x -> Pair.of("慶雲", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "慶雲", "（けいうん） （きょううん）")))));
			//
			Assertions.assertEquals(ImmutableMultimap.of("元弘", "げんこう"),
					IValue0Util.getValue0(toMultimap(patternMap, "元弘　　", "（げんこう）")));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("しょうけい", "しょうきょう"), x -> Pair.of("正慶", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "正慶　　", "（しょうけい） （しょうきょう）")))));
			//
			Assertions.assertEquals(ImmutableMultimap.of("白雉", "びゃくち"),
					IValue0Util.getValue0(toMultimap(patternMap, "白雉", "『日本史用語大辞典』は他に（びゃくち）の読み")));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("しゅちょう", "すちょう", "あかみどり"), x -> Pair.of("朱鳥", x))),
					MultimapUtil.entries(
							IValue0Util.getValue0(toMultimap(patternMap, "朱鳥", "広辞苑（しゅちょう）／（すちょう）（あかみどり）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("じんき", "しんき"), x -> Pair.of("神亀", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "神亀", "広辞苑（じんき）／（しんき）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("かんぴょう", "かんべい", "かんぺい", "かんへい"), x -> Pair.of("寛平", x))),
					MultimapUtil.entries(IValue0Util
							.getValue0(toMultimap(patternMap, "寛平", "広辞苑（かんぴょう）／（かんべい）（かんぺい）（かんへい）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("じょうわ", "しょうわ", "そうわ"), x -> Pair.of("承和", x))),
					MultimapUtil.entries(IValue0Util.getValue0(
							toMultimap(patternMap, "承和", "日本国語大辞典（じょうわ）／（しょうわ）ともとある 広辞苑（じょうわ）／（しょうわ）（そうわ）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("しょうりゃく", "じょうりゃく", "しょうれき"), x -> Pair.of("正暦", x))),
					MultimapUtil.entries(IValue0Util.getValue0(
							toMultimap(patternMap, "正暦", "日本国語大辞典（しょうりゃく）のみ 広辞苑（じょうりゃく）／（しょうりゃく）（しょうれき）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("じょうとく", "しょうとく"), x -> Pair.of("承徳", x))),
					MultimapUtil.entries(IValue0Util.getValue0(
							toMultimap(patternMap, "承徳", "日本国語大辞典（じょうとく）／（しょうとく）ともとある 広辞苑（じょうとく）／（しょうとく）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("じしょう", "じじょう", "ちしょう"), x -> Pair.of("治承", x))),
					MultimapUtil.entries(IValue0Util
							.getValue0(toMultimap(patternMap, "治承", "広辞苑（じしょう）／（じじょう）ともとある 『日本史用語大辞典』は他に（ちしょう）の読み")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("にんあん", "にんなん"), x -> Pair.of("仁安", x))),
					MultimapUtil.entries(IValue0Util
							.getValue0(toMultimap(patternMap, "仁安", "日本国語大辞典（にんあん）のみ 広辞苑（にんあん）／（にんなん）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("けんむ", "けんぶ"), x -> Pair.of("建武", x))),
					MultimapUtil.entries(IValue0Util
							.getValue0(toMultimap(patternMap, "建武", "南北朝とも建武（北朝は〜1338まで） 広辞苑（けんむ）／（けんぶ）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("かしょう", "かじょう", "かそう"), x -> Pair.of("嘉承", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "嘉承",
							"『現代こよみ読み解き事典』（かしょう） 『朝日新聞の用語の手引き』（かじょう） 日本国語大辞典（かしょう） 広辞苑（かしょう）（かじょう）両方あり、他に（かそう）ともある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("たいえい", "だいえい"), x -> Pair.of("大永", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "大永",
							"『現代こよみ読み解き事典』（たいえい） 『朝日新聞の用語の手引き』（だいえい） 日本国語大辞典（だいえい）／（たいえい）ともとある 広辞苑（だいえい）")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("かけい", "かきょう"), x -> Pair.of("嘉慶", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "嘉慶",
							"『現代こよみ読み解き事典』（かけい） 『朝日新聞の用語の手引き』（かけい） 日本国語大辞典（かけい） 広辞苑（かきょう）／（かけい）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("えんぎょう", "えんきょう", "えんけい"), x -> Pair.of("延慶", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "延慶",
							"『現代こよみ読み解き事典』(えんきょう） 『朝日新聞の用語の手引き』（えんぎょう） 日本国語大辞典（えんきょう）／（えんぎょう）ともとある 広辞苑（えんきょう）／（えんぎょう）（えんけい）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("たいほう", "だいほう"), x -> Pair.of("大宝", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "大宝",
							"『現代こよみ読み解き事典』の表には（たいほう）とあるが文中には今日、 一般には（たいほう）と読んでいるが（だいほう）が正しいとある 広辞苑（たいほう）／（だいほう）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("宝暦暦", "ほうりゃくれき", "宝暦", "ほうれき", "宝暦", "ほうりゃく")),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "宝暦",
							"『現代こよみ読み解き事典』 上記（明暦）参照　 　参考：宝暦暦（ほうりゃくれき） 広辞苑（ほうれき）／（ほうりゃく）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil
							.entries(ImmutableMultimap.of("天平宝字", "てんぴょうほうじ", "天平宝字", "てんびょうほうじ", "天平宝字", "てんぺいほうじ")),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "天平宝字",
							"『現代こよみ読み解き事典』(てんぴょうほうじ） 『朝日新聞の用語の手引き』（てんぴょうほうじ） 日本国語大辞典（てんぴょうほうじ） 広辞苑（てんぴょうほうじ）／（てんびょうほうじ）（てんぺいほうじ）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("明徳", "めいとく")),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "*", "北朝年号「明徳（めいとく）」を使用")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("てんぴょうしょうほう", "てんびょうしょうぼう", "てんぺいしょうほう"), x -> Pair.of("天平勝宝", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "天平勝宝",
							"『現代こよみ読み解き事典』(てんぴょうしょうほう） 『朝日新聞の用語の手引き』（てんぴょうしょうほう） 日本国語大辞典（てんぴょうしょうほう） 広辞苑（てんぴょうしょうほう）／ 　　　（てんびょうしょうぼう）（てんぺいしょうほう）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("てんぴょうかんぽう", "てんびょうかんぽう", "てんぴょうかんほう", "てんぺいかんほう"),
							x -> Pair.of("天平感宝", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "天平感宝",
							"『現代こよみ読み解き事典』(てんぴょうかんぽう） 『朝日新聞の用語の手引き』（てんぴょうかんぽう） 日本国語大辞典（てんぴょうかんぽう） 広辞苑（てんぴょうかんぽう）／（てんびょうかんぽう）ともとある 『日本史用語大辞典』は他に 　　　（てんぴょうかんほう）（てんぺいかんほう）の読み")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("にんべい", "にんぺい", "にんびょう", "にんひょう", "にんへい"), x -> Pair.of("仁平", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "仁平",
							"『現代こよみ読み解き事典』に（にんべい）とある 　同じ本の50音順の項には（にんぺい）になっている 日本国語大辞典（にんぺい）のみ 広辞苑には（にんぺい）／（にんびょう）（にんひょう）（にんへい）ともとある")))));
			//
		} // if
			//
	}

	@Test
	void testToMultimap2() throws Throwable {
		//
		Assertions.assertNull(toMultimap(null, Collections.singleton(null)));
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("岡田芳朗", "おかだよしろう")),
							MultimapUtil.entries(toMultimap(new PatternMapImpl(),
									Collections.singleton(new Element("p").appendText("岡田芳朗（おかだよしろう）"))))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("しゅちょう", "すちょう", "あかみどり"), x -> Pair.of("朱鳥", x))),
					MultimapUtil.entries(
							IValue0Util.getValue0(toMultimap(patternMap, "朱鳥", "広辞苑（しゅちょう）／（すちょう）（あかみどり）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(
							Util.map(Stream.of("てんぴょうじんご", "てんびょうじんご", "てんへいじんご", "てんぺいじんご"), x -> Pair.of("天平神護", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "天平神護",
							"『現代こよみ読み解き事典』(てんぴょうじんご） 『朝日新聞の用語の手引き』（てんぴょうじんご） 日本国語大辞典（てんぴょうじんご） 広辞苑（てんぴょうじんご）／（てんびょうじんご）（てんへいじんご）ともとある 『日本史用語大辞典』は他に（てんぺいじんご）の読み")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("げんけい", "がんぎょう", "がんきょう"), x -> Pair.of("元慶", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "元慶",
							"『現代こよみ読み解き事典』(げんけい） 『朝日新聞の用語の手引き』（がんぎょう） 日本国語大辞典（がんぎょう）／（がんきょう）とも／ 　　　（げんけい）→（がんぎょう）とある 広辞苑（がんぎょう）／（がんきょう）（げんけい）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("てんぴょう", "てんびょう", "てんへい", "てんぺい"), x -> Pair.of("天平", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "天平",
							"『現代こよみ読み解き事典』の表には（てんぴょう）とあるが文中には 今日は誰でも(てんぴょう）と読むが正しくは（てんびょう）か（てんへい）とある 『朝日新聞の用語の手引き』（てんぴょう） 日本国語大辞典（てんぴょう）／（てんびょう）ともとある 広辞苑（てんぴょう）／（てんびょう）（てんへい）ともとある 『日本史用語大辞典』は他に（てんぺい）の読み")))));
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("神護景雲", "じんごけいうん")),
							MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "神護景雲",
									"『現代こよみ読み解き事典』に（じんごうんけい）とあるのは誤植とおもわれる 　同じ本の50音順の項には（じんごけいうん）になっている")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("かんのう", "かんおう"), x -> Pair.of("観応", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "観応",
							"『現代こよみ読み解き事典』（かんのう） 『朝日新聞の用語の手引き』（かんおう） 日本国語大辞典（かんのう）／（かんおう）→（かんのう）とある 広辞苑（かんおう）／（かんのう）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("りゃくおう", "れきおう"), x -> Pair.of("観応", x))),
					MultimapUtil.entries(IValue0Util.getValue0(
							toMultimap(patternMap, "観応", "日本国語大辞典（りゃくおう）／（れきおう）→（りゃくおう）とある 広辞苑（りゃくおう）／（れきおう）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					Util.toList(Util.map(Stream.of("しょうけい", "しょうきょう"), x -> Pair.of("正慶", x))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "正慶　　",
							"『現代こよみ読み解き事典』（しょうけい） 『朝日新聞の用語の手引き』（しょうきょう） 日本国語大辞典（しょうきょう）／（しょうけい）→（しょうきょう）とある 広辞苑（しょうきょう）／（しょうけい）ともとある")))));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					CollectionUtils.union(
							Util.toList(Util.map(Stream.of("めいれき", "めいりゃく", "みょうりゃく"), x -> Pair.of("明暦", x))),
							Arrays.asList(Pair.of("暦", "りゃく"), Pair.of("大火", "たいか"))),
					MultimapUtil.entries(IValue0Util.getValue0(toMultimap(patternMap, "明暦",
							"『現代こよみ読み解き事典』の表は（めいれき）であるが 文中にはこの頃まで年号の暦の字は（りゃく）と読まれていたとある 　参考：明暦の大火（めいりゃくのたいか） 広辞苑（めいれき）／（みょうりゃく）（めいりゃく）ともとある")))));
			//
		} // if
			//
	}

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final Iterable<Element> es)
			throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP2.invoke(null, patternMap, es);
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

}