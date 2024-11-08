package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.text.TextStringBuilder;
import org.d2ab.collection.ints.IntCollection;
import org.d2ab.function.CharPredicate;
import org.d2ab.function.ObjIntPredicate;
import org.javatuples.Quartet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.TriPredicate;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;

import io.github.toolfactory.narcissus.Narcissus;
import it.unimi.dsi.fastutil.ints.IntObjectPair;

class OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBeanTest {

	private static final int ZERO = 0;

	private static final String EMPTY = "";

	private static final String SPACE = " ";

	private static Method METHOD_TEST_AND_APPLY, METHOD_TO_MULTI_MAP_AND_INT_COLLECTION, METHOD_TEST_AND_ACCEPT3,
			METHOD_TEST_AND_ACCEPT4, METHOD_TEST_AND_ACCEPT5, METHOD_APPEND, METHOD_SUB_STRING, METHOD_TEST_AND_RUN;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_AND_INT_COLLECTION = clz.getDeclaredMethod("toMultimapAndIntCollection", PatternMap.class,
				IntObjectPair.class, Iterable.class)).setAccessible(true);
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
		(METHOD_APPEND = clz.getDeclaredMethod("append", TextStringBuilder.class, String.class)).setAccessible(true);
		//
		(METHOD_SUB_STRING = clz.getDeclaredMethod("substring", TextStringBuilder.class, Integer.TYPE, Integer.TYPE))
				.setAccessible(true);
		//
		(METHOD_TEST_AND_RUN = clz.getDeclaredMethod("testAndRun", Boolean.TYPE, Runnable.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_RUN = clz.getDeclaredMethod("testAndRun", Boolean.TYPE, Runnable.class)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject;

	private PatternMap patternMap = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBeanTest.testGetObject");
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
				OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean.class);
		//
		final Field url = Util.getKey(entry);
		//
		Narcissus.setObjectField(instance, url, EMPTY);
		//
		Assertions.assertNull(getObject(instance));
		//
		Narcissus.setObjectField(instance, url, SPACE);
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
	void testNull() throws ClassNotFoundException {
		//
		final Method[] ms = ArrayUtils.addAll(ArrayUtils.addAll(
				getDeclaredMethods(OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean.class),
				getDeclaredMethods(Class.forName(
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean$ObjObjIntObjObjFunction"))),
				getDeclaredMethods(Class.forName(
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean$ObjIntObjObjFunction")));
		//
		Method m = null;
		//
		Collection<Object> list = null;
		//
		Object invokeStaticMethod;
		//
		String name, toString;
		//
		Class<?>[] parameterTypes;
		//
		Class<?> parameterType;
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
			for (int j = 0; j < Util.length(parameterTypes = m.getParameterTypes()); j++) {
				//
				if (Objects.equals(parameterType = parameterTypes[j], Integer.TYPE)) {
					//
					list.add(Integer.valueOf(0));
					//
				} else if (Objects.equals(parameterType, Boolean.TYPE)) {
					//
					list.add(Boolean.FALSE);
					//
				} else if (Objects.equals(parameterType, Character.TYPE)) {
					//
					list.add(' ');
					//
				} else {
					//
					list.add(null);
					//
				} // if
					//
			} // for
				//
			if (Objects.equals(name = Util.getName(m), "toQuartet")
					&& Arrays.equals(parameterTypes, new Class<?>[] { Iterable.class })) {
				//
				final Method m1 = m;
				//
				final Object[] array = toArray(list);
				//
				Assertions.assertThrows(IllegalStateException.class, () -> Narcissus.invokeStaticMethod(m1, array));
				//
				continue;
				//
			} // if
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(list));
			//
			toString = Objects.toString(m);
			//
			if (ArrayUtils.contains(new Class<?>[] { Boolean.TYPE, Long.TYPE, Integer.TYPE }, m.getReturnType())
					|| (Objects.equals(name, "getCommonSuffix")
							&& Arrays.equals(parameterTypes, new Class<?>[] { String.class, String.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollection2") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, Iterable.class, Entry.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollection4A") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, IntObjectPair.class, Iterable.class, Quartet.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollection4A1") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, IntObjectPair.class, IntObjectPair.class,
									Quartet.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollection4B") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, IntObjectPair.class, Iterable.class, Multimap.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollection4A11") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, IntObjectPair.class, IntObjectPair.class,
									Quartet.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollection5A") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, IntObjectPair.class, Iterable.class, Sextet.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollectionAndTriplet6A") && Arrays.equals(parameterTypes,
							new Class<?>[] { IntObjectPair.class, Iterable.class, Triplet.class, String.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollection8A") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, IntObjectPair.class, Iterable.class, Entry.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollection8B") && Arrays.equals(parameterTypes,
							new Class<?>[] { IntObjectPair.class, Iterable.class, Iterable.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollection10A") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, IntObjectPair.class, Iterable.class, Entry.class }))
					|| (Objects.equals(name, "toMultimap12A")
							&& Arrays.equals(parameterTypes, new Class<?>[] { String.class, String.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollection12B1") && Arrays.equals(parameterTypes,
							new Class<?>[] { PatternMap.class, Integer.TYPE, String.class, Map.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollection17") && Arrays.equals(parameterTypes,
							new Class<?>[] { IntObjectPair.class, Iterable.class, Pattern.class }))
					|| (Objects.equals(name, "toMultimapAndIntCollection17A")
							&& Arrays.equals(parameterTypes, new Class<?>[] { IntObjectPair.class }))
					|| (Objects.equals(name, "indexOf")
							&& Arrays.equals(parameterTypes, new Class<?>[] { String.class, CharPredicate.class }))) {
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

	private static Method[] getDeclaredMethods(final Class<?> instance) {
		return instance != null ? instance.getDeclaredMethods() : null;
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
	void testToMultimapAndIntCollection1() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertNull(toMultimapAndIntCollection(patternMap, null, null));
		//
		Assertions.assertNull(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, null), null));
		//
		Assertions.assertEquals("({家形文様=[いえがたもんよう], 文様=[もんよう], 家形=[いえがた]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap,
						IntObjectPair.of(ZERO, "日本の伝統文様（もんよう）は美しく、心が和みます｡"), Arrays.asList(null, "家形文様（いえがたもんよう）"))));
		//
		Assertions.assertEquals(
				"({青貝塗=[あおがいぬり], 漆工=[しっこう], 青木間道=[あおきかんとう], 名物裂=[めいぶつぎれ], 工字文=[こうじもん], 工=[こう], 青=[あお], 愛染明王図文=[あいぜんみょうおうずもん], 文=[もん]},[0, 1, 2, 3])",
				Objects.toString(toMultimapAndIntCollection(patternMap,
						IntObjectPair.of(ZERO, "青貝塗（あおがいぬり）＊漆工（しっこう）技法"),
						Arrays.asList(null, "青木間道（あおきかんとう）＊名物裂（めいぶつぎれ）", "工字文（こうじもん）", "愛染明王図文（あいぜんみょうおうずもん）"))));
		//
		Assertions.assertEquals("({麻=[あさ], 小紋=[こもん], 葉=[は], 文=[もん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "麻の葉小紋（あさのはこもん）"),
						Arrays.asList(null, "麻の葉文（あさのはもん）"))));
		//
		Assertions.assertEquals(
				"({網目=[あみめ], 網=[あみ], 目=[め], 網文=[あみもん], 文=[もん], 魚文=[うおもん, ぎょもん], 様=[よう], 魚=[うお], 鳥=[とり]},[0, 1, 2, 3, 4, 5])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "網に魚文様（あみにうおもんよう）"),
						Arrays.asList(null, "網目（あみめ）", "網文（あみもん）", "魚文（うおもん）", "魚文（ぎょもん）",
								"アメリカ・インディアンの魚と鳥文（---のうおととりもん）"))));
		//
		Assertions.assertEquals("({鳳文=[おおとりもん], 文=[もん], 鳳=[おおとり], 魚=[うお], 鳥=[とり]},[0, 1, 2])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "走る鳳凰文（はしるほうおうもん）"),
						Arrays.asList(null, "鳳文（おおとりもん）", "アメリカ・インディアンの魚と鳥文（---のうおととりもん）"))));
		//
		Assertions.assertEquals("({梅文=[うめもん], 梅=[うめ], 文=[もん], 松文=[まつもん], 松=[まつ], 魚=[うお], 鳥=[とり]},[0, 1, 2, 3])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "梅に松林文（うめにまつばやしもん）"),
						Arrays.asList(null, "梅文（うめもん）", "松文（まつもん）", "アメリカ・インディアンの魚と鳥文（---のうおととりもん）"))));
		//
		Assertions.assertEquals("({梅文=[うめもん], 梅=[うめ], 文=[もん], 松文=[まつもん], 松=[まつ], 魚=[うお], 鳥=[とり]},[0, 1, 2, 3])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "梅に松林文（うめにまつばやしもん）"),
						Arrays.asList(null, "梅文（うめもん）", "松文（まつもん）", "アメリカ・インディアンの魚と鳥文（---のうおととりもん）"))));
		//
		Assertions.assertEquals(
				"({葦=[あし], 雁=[がん], 水仙花文=[すいせんかもん], 水=[すい], 水天宮文=[すいてんぐうもん], 水辺文=[すいへんもん], 水辺=[すいへん], 水手絵=[みずでえ], 水波文=[すいはもん], 水手文=[みずでもん], 水煙=[すいえん], 文様=[もんよう], 辺=[へん], 煙=[えん], 水手=[みずで], 絵=[え], 文=[もん], 波=[は], 水仙花=[すいせんか], 仙花=[せんか], 水天宮=[すいてんぐう], 風景=[ふうけい]},[0, 1, 2, 3, 4, 5, 6, 7, 8])",
				Objects.toString(
						toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "葦に雁の水辺風景文（あしにがんのすいへんふうけいもん）"),
								Arrays.asList(null, "水仙花文（すいせんかもん）", "水天宮文（すいてんぐうもん）", "水辺文（すいへんもん）", "水手絵（みずでえ）",
										"水波文（すいはもん）", "水手文（みずでもん）", "水辺文（すいへんもん）", "水煙の文様（すいえんのもんよう）"))));
		//
		Assertions.assertEquals(
				"({霊獣=[れいじゅう], 葵文=[あおいもん], 葵=[あおい], 文=[もん], 霊鳥=[れいちょう], 霊=[れい], 鳥=[ちょう], 獣=[じゅう]},[0, 1, 2])",
				Objects.toString(
						toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "アメリカ・インディアンの霊獣文（---のれいじゅうもん）"),
								Arrays.asList(null, "葵文（あおいもん）", "霊鳥（れいちょう）"))));
		//
		Assertions.assertEquals("({苺裂=[いちごぎれ], 古裂=[こぎれ], 古=[こ], 裂=[ぎれ], 苺=[いちご]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "苺裂（いちごぎれ）＊名物裂"),
						Arrays.asList(null, "古裂（こぎれ）"))));
		//
		Assertions.assertEquals(
				"({印籠=[いんろう], 印金=[いんきん], 印=[いん], 金=[きん], 籠=[ろう], 印花文=[いんかもん], 葵文=[あおいもん], 葵=[あおい], 文=[もん], 印花=[いんか], 花=[か]},[0, 1, 2, 3])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "印籠（いんろう）＊薬入れ"),
						Arrays.asList(null, "印金（いんきん）＊名物裂、技法", "印花文（いんかもん）＊文様装飾技法", "葵文（あおいもん）"))));
		//
		Assertions.assertEquals("({卯=[う], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "卯の文（うのもん）"), null)));
		//
		Assertions.assertEquals("({桜川=[さくらがわ], 桜文=[さくらもん], 桜=[さくら], 川=[がわ], 文=[もん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 桜川（さくらがわ）"),
						Arrays.asList(null, "桜文（さくらもん）"))));
		//
		Assertions.assertEquals("({中形=[ちゅうがた], 中柄=[ちゅうがら], 中=[ちゅう], 形=[がた], 柄=[がら]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 中形（ちゅうがた）"),
						Arrays.asList(null, "中柄（ちゅうがら）"))));
		//
		Assertions.assertEquals("({羯磨=[かつま], 達磨=[だるま], 磨=[ま], 羯=[かつ]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 羯磨（かつま）"),
						Arrays.asList(null, "達磨（だるま）"))));
		//
		Assertions.assertEquals("({空間=[あきま], 欄間=[らんま], 欄=[らん], 間=[ま]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 空間（あきま）"),
						Arrays.asList(null, "欄間（らんま）"))));
		//
		Assertions.assertEquals("({水文=[みずもん, すいもん], 文=[もん], 水=[みず, すい]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 水文（みずもん）"),
						Arrays.asList(null, "水文（すいもん）"))));
		//
		Assertions.assertEquals("({上松縞=[あげまつじま], 上絵=[うわえ], 上文=[うわもん], 上=[うわ], 文=[もん]},[0, 1, 2])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 上松縞（あげまつじま）"),
						Arrays.asList(null, "上絵（うわえ）＊工芸用語", "上文（うわもん）"))));
		//
		Assertions.assertEquals(
				"({網目文=[あみめもん], 網=[あみ], 文=[もん], 魚=[うお], 様=[よう], 網目=[あみめ], 網目七曜文=[あみめしちようもん], 七曜=[しちよう], 網文=[あみもん], 目=[め], 代=[しろ], 網代=[あじろ], 干=[ほし], 網干=[あぼし]},[0, 1, 2, 3, 4, 5, 6])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 網目文（あみめもん）"),
						Arrays.asList(null, "網に魚文様（あみにうおもんよう）", "網目（あみめ）", "網目七曜文（あみめしちようもん）", "網文（あみもん）", "網代文（あじろもん）",
								"網干文（あぼしもん）"))));
		//
		Assertions.assertEquals(
				"({大内菱=[おおうちびし], 文=[もん], 根=[こん], 大=[だい, おお], 大燈金襴=[だいとうきんらん], 襴=[らん], 金=[きん], 大徳寺牡丹文=[だいとくじぼたんもん], 丹=[たん], 大文字文=[だいもんじもん], 字=[じ], 大内=[おおうち], 大絣=[おおがすり], 絣=[かすり], 菱=[ひし]},[0, 2, 3, 4, 5, 6, 7])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 大内菱（おおうちびし）"),
						Arrays.asList(null, "網大牡丹文（おおぼたんもん）", "大根文（だいこんもん）", "大燈金襴（だいとうきんらん）＊名物裂の一種",
								"大徳寺牡丹文（だいとくじぼたんもん）", "大文字文（だいもんじもん）", "大内桐金襴（おおうちきりきんらん）", "大絣（おおがすり）"))));
		//
		Assertions.assertEquals(
				"({海馬文=[かいまもん], 海辺=[うみべ], 松=[まつ], 海老文=[えびもん], 海老=[えび], 文=[もん], 海=[かい], 桐=[きり], 賦=[ふ], 海賦文=[かいぶもん, みるもん], 海賦=[かいぶ], 海賦文様=[かいふもんよう], 様=[よう], 海松文=[みるもん], 海松=[みる]},[0, 1, 2, 3, 4, 5])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 海馬文（かいまもん）"),
						Arrays.asList(null, "海辺の松（うみべのまつ）", "海老文（えびもん）", "海賦に桐文（かいぶにきりもん）",
								"海賦文（かいぶもん）＊海部・海浦とも書く/海賦文様（かいふもんよう）", "海松文・海賦文（みるもん）"))));
		//
		Assertions.assertEquals(
				"({金輪文=[かなわもん], 金華布=[きんかふ], 金=[きん, こん, かね, かな], 魚=[ぎょ], 文=[もん], 金銭文=[きんせんもん], 銭=[せん], 金通=[きんつう], 通=[つう], 金襴=[きんらん], 襴=[らん], 金襴手文様=[きんらんでもんよう], 様=[よう], 金襴牡丹唐草文=[きんらんぼたんからくさもん], 牡丹=[ぼたん], 唐草文=[からくさもん], 金剛杵文=[こんごうしょもん], 金剛鈴=[こんごうれい], 手=[て], 函=[はこ], 金函=[かねばこ], 輪=[わ]},[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 金輪文（かなわもん）"),
						Arrays.asList(null, "金華布（きんかふ）＊更紗", "金魚文（きんぎょもん）", "金銭文（きんせんもん）", "金通（きんつう）", "金襴（きんらん）＊織物の一種",
								"金襴手文様（きんらんでもんよう）", "金襴牡丹唐草文（きんらんぼたんからくさもん）", "金剛杵文（こんごうしょもん）", "金剛鈴（こんごうれい）＊密教法具の一種",
								"金函文（かねばこもん）"))));
		//
		Assertions.assertEquals("({笠松文=[かさまつもん], 文=[もん], 面=[めん], 扇=[せん], 笠=[かさ]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 笠松文（かさまつもん）"),
						Arrays.asList(null, "笠扇面繋ぎ文（かさせんめんつなぎもん）"))));
		//
	}

	@Test
	void testToMultimapAndIntCollection2() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({亀背文=[きはいもん], 亀=[かめ], 文=[もん], 亀蛇文=[かめへびもん]},[0, 1, 2])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 亀背文（きはいもん）"),
						Arrays.asList(null, "亀文（かめもん）", "亀蛇文（かめへびもん）"))));
		//
		Assertions.assertEquals(
				"({小葵文=[こあおいもん], 小草文=[おぐさもん, こくさもん], 文=[もん], 小草=[おぐさ], 小紋=[こもん], 小=[こ], 紋=[もん], 草=[くさ]},[0, 1, 2, 3])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 小葵文（こあおいもん）"),
						Arrays.asList(null, "小草文（おぐさもん・こくさもん）", "小紋（こもん）＊小紋染の略", "小草文（こくさもん・おぐさもん）"))));
		//
		Assertions.assertEquals("({小飛文=[ことびもん], 文=[もん], 飛=[ひ], 小=[こ], 鳥=[とり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 小飛文（ことびもん）"),
						Arrays.asList(null, "小鳥文（ことりもん）"))));
		//
		Assertions.assertEquals("({河骨文=[こうほねもん]},[0])", Objects.toString(toMultimapAndIntCollection(patternMap,
				IntObjectPair.of(ZERO, " 河骨文（こうほねもん）"), Arrays.asList(null, " 河骨文（こうほねもん）"))));
		//
		Assertions.assertEquals("({鮫市松=[さめいちまつ], 鮫=[さめ], 市松=[いちまつ], 鮫小紋=[さめこもん], 小紋=[こもん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 鮫市松（さめいちまつ）"),
						Arrays.asList(null, "鮫小紋（さめこもん）＊小紋型の一種"))));
		//
		Assertions.assertEquals("({瑞雲文=[ずいうんもん], 瑞垣文=[みずがきもん], 文=[もん], 瑞籬文=[みずがきもん], 水垣文=[みずがきもん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 瑞雲文（ずいうんもん）"),
						Arrays.asList(null, "瑞垣文・瑞籬文・水垣文（みずがきもん）"))));
		//
		Assertions.assertEquals(
				"({鳥樹文=[ちょうじゅもん], 鳥=[とり], 唐草文=[からくさもん], 唐草=[からくさ], 文=[もん], 鳥獣連珠文=[ちょうじゅうれんじゅもん], 連=[れん], 鳥獣=[ちょうじゅう], 珠=[しゅ]},[0, 1, 2])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 鳥樹文（ちょうじゅもん）"),
						Arrays.asList(null, "鳥に唐草文（とりにからくさもん）", "鳥獣連珠文（ちょうじゅうれんじゅもん）"))));
		//
		Assertions.assertEquals(
				"({花筏文=[はないかだもん], 花=[か, はな], 瓶=[びん], 文=[もん], 花布=[かふ], 布=[ふ], 華布=[かふ], 華=[か], 花麒麟金襴=[はなきりんきんらん], 襴=[らん], 金=[きん], 麟=[りん], 麒=[き], 鳥=[とり], 花字文=[かじもん], 字=[じ]},[0, 1, 2, 3, 4, 5, 6, 7])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 花筏文（はないかだもん）"),
						Arrays.asList(null, "花瓶文（かびんもん）", "花布・華布（かふ）＊更紗", "花麒麟金襴（はなきりんきんらん）＊名物裂の一種", "花麒麟文（はなきりんもん）",
								"花つかみ鳥文（はなつかみとりもん）", "花に遊ぶ鳥文（はなにあそぶとりもん）", "花字文（かじもん）"))));
		//
		Assertions.assertEquals("({花喰鳥=[はなくいどり], 花文=[かもん], 花=[か, はな], 文=[もん], 鳥=[とり]},[0, 1, 2])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 花喰鳥（はなくいどり）"),
						Arrays.asList(null, "花文（かもん）", "花つかみ鳥文（はなつかみとりもん）"))));
		//
		Assertions.assertEquals("({虎杖文=[いたどりもん], 虎絣=[とらがすり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 虎杖文（いたどりもん）"),
						Arrays.asList(null, "虎絣（とらがすり）＊絣織物の一種"))));
		//
		Assertions.assertEquals("({菱藤文=[ひしふじもん], 文=[もん], 字=[じ], 万=[まん], 菱=[ひし], 連=[れん], 珠=[しゅ]},[0, 1, 2])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 菱藤文（ひしふじもん）"),
						Arrays.asList(null, "菱万字文（ひしまんじもん）", "菱連珠文（ひしれんじゅもん）"))));
		//
		Assertions.assertEquals(
				"({三重襷=[みえだすき], 三纈=[さんけち], 三=[さん, み], 纈=[けち], 三段十字=[さんだんじゅうじ], 三段=[さんだん], 十字=[じゅうじ], 文=[もん], 吉祥=[きっしょう], 大島=[おおしま], 臈纈=[ろうけち], 臈=[ろう], 夾纈=[きょうけち], 夾=[きょう], 纐纈=[こうけち], 纐=[こう], 多=[た], 三本立葵文=[さんぼんたちあおいもん], 本=[ほん]},[0, 1, 2, 3, 4, 5, 6, 7, 8])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 三重襷（みえだすき）"),
						Arrays.asList(null, "三纈（さんけち）染色法の名称", "三段十字と十字崩し文（さんだんじゅうじとじゅうじくずしもん）", "三の吉祥文（さんのきっしょうもん）",
								"三つ鱗文（みつうろこもん）", "三つ大島（みつおおしま）＊縞柄の一種", "三纈（さんけち）--臈纈（ろうけち）夾纈（きょうけち）纐纈（こうけち）",
								"三多文（さんたもん）", "三本立葵文（さんぼんたちあおいもん）"))));
		//
		Assertions.assertEquals(
				"({木菟文=[みみづくもん], 木地蒔絵=[きじまきえ], 木画=[もくが], 木=[もく], 画=[が], 木象嵌=[もくぞうがん], 象=[ぞう], 文=[もん], 嵌=[かん]},[0, 1, 2, 3])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 木菟文（みみづくもん）"),
						Arrays.asList(null, "木地蒔絵（きじまきえ）＊漆工蒔絵の一種", "木画（もくが）／木象嵌（もくぞうがん）＊現代の寄木細工のこと。",
								"木こう文（もっこうもん）＊（こう）は穴かんむりに果"))));
		//
		Assertions.assertEquals(
				"({結綿文=[ゆいわたもん], 結綿=[ゆいわた], 結束文様=[けっそくもんよう], 結束=[けっそく], 文=[もん], 様=[よう], 文様=[もんよう], 亀甲文=[きっこうもん], 亀甲=[きっこう]},[0, 1, 2, 3])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 結綿文（ゆいわたもん）"),
						Arrays.asList(null, "結束文様（けっそくもんよう）", "結び文（むすびもん）", "結び亀甲文（むすびきっこうもん）"))));
		//
		Assertions.assertEquals("({雷雨文=[らいうもん], 雷文菱=[らいもんびし], 雷=[らい], 文=[もん], 雲文=[くももん], 雲=[くも], 菱=[ひし]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 雷雨文（らいうもん）"),
						Arrays.asList(null, "雷文菱に雲文（らいもんびしにくももん）"))));
		//
		Assertions.assertEquals("({蘆辺文=[あしべもん], 蘆=[あし], 雁=[かり], 文=[もん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 蘆辺文（あしべもん）"),
						Arrays.asList(null, "蘆に雁文（あしにかりもん）"))));
		//
		Assertions.assertEquals(
				"({印半纏=[しるしばんてん], 印花文=[いんかもん], 印金=[いんきん], 印伝革=[いんでんがわ], 印度=[いんど], 印籠=[いんろう], 鳥=[とり]},[0, 1, 2, 3, 4, 5])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 印半纏（しるしばんてん）"),
						Arrays.asList(null, "印花文（いんかもん）＊文様装飾技法", "印金（いんきん）＊名物裂、技法", "印伝革（いんでんがわ）＊染革",
								"印度の花食い鳥（いんどのはなくいどり）", "印籠（いんろう）＊薬入れ"))));
		//
		Assertions.assertEquals(
				"({永楽銭紋=[えいらくせんもん], 永楽=[えいらく], 銭=[せん], 紋=[もん], 金銭文=[きんせんもん], 金=[きん], 文=[もん], 紋章=[もんしょう], 章=[しょう], 裏紋=[うらもん], 裏=[うら]},[0, 2, 3, 4])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 永楽銭紋（えいらくせんもん）"),
						Arrays.asList(null, "永高（えいだか）", "金銭文（きんせんもん）", "紋章（もんしょう）", "裏紋（うらもん）"))));
		//
		Assertions.assertEquals("({三寸模様=[さんずんもよう], 三=[さん], 模様=[もよう], 寸=[すん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 三寸模様（さんずんもよう）"), null)));
		//
		Assertions.assertEquals("({浮線綾文=[ふせんりょうもん], 浮=[ふ], 線=[せん], 綾=[りょう], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 浮線綾文（ふせんりょうもん）"), null)));
		//
		Assertions.assertEquals("({将軍星文=[しょうぐんほしもん], 将=[しょう], 軍=[ぐん], 星=[ほし], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 将軍星文（しょうぐんほしもん）"), null)));
		//
		Assertions.assertEquals("({団禧字文=[だんきじもん], 団=[だん], 文=[もん], 禧=[き], 字=[じ]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 団禧字文（だんきじもん）"), null)));
		//
		Assertions.assertEquals(
				"({片子持縞=[かたこもちじま], 縞=[しま], 文=[もん], 雲=[くも], 片=[かた], 片木=[へぎ], 片滝縞=[かたたきじま], 滝=[たき], 水=[すい], 流=[りゅう]},[0, 1, 3, 5])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 片子持縞（かたこもちじま）"),
						Arrays.asList(null, "縞（しま）", "片男波文（かたおなみもん）", "片木（へぎ）", "子持雲文（こもちくももん）", "片滝縞（かたたきじま）",
								"片輪車流水文（かたわぐるまりゅうすいもん）", "片輪車文（かたわぐるまもん）"))));
		//
		Assertions.assertEquals(
				"({縫取模様=[ぬいとりもよう], 縫箔=[ぬいはく], 箔=[はく], 紋=[もん], 縫目模様=[ぬいめもよう], 目=[め], 模様=[もよう], 縫文様=[ぬいもんよう], 文様=[もんよう], 文=[もん], 様=[よう]},[0, 1, 3, 4])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 縫取模様（ぬいとりもよう）"),
						Arrays.asList(null, "縫箔（ぬいはく）", "縫取紋（ぬいとりもん）", "縫目模様（ぬいめもよう）", "縫文様（ぬいもんよう）"))));
		//
		Assertions.assertEquals("({葡萄唐草=[ぶどうからくさ], 草=[くさ], 文=[もん], 葡萄文=[ぶどうもん], 葡萄=[ぶどう], 栗鼠=[りす]},[0, 3, 4])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 葡萄唐草（ぶどうからくさ）"),
						Arrays.asList(null, "草花文（くさばなもん）", "唐草文（からくさもん）", "葡萄文（ぶどうもん）", "葡萄栗鼠文（ぶどうりすもん）"))));
		//
	}

	@Test
	void testToMultimapAndIntCollection3() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals(
				"({間明大明=[まあきだいみょう], 文=[もん], 丹=[たん], 大=[だい], 明=[みょう], 大十絣=[だいじゅうがすり], 十=[じゅう], 小=[しょう], 菊花=[きくか], 絣=[かすり]},[0, 4, 5])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 間明大明（まあきだいみょう）"),
						Arrays.asList(null, "大牡丹文（おおぼたんもん）", "大文字文（だいもんじもん）", "大大明（おおだいみょう）", "大十絣（だいじゅうがすり）",
								"大小菊花文（だいしょうきくかもん）"))));
		//
		Assertions.assertEquals(
				"({唐獅子文=[からししもん], 雲文=[うんもん], 雲=[うん], 文=[もん], 卍文=[まんじもん], 卍=[まんじ], 鼈文=[すっぽんもん], 鼈=[すっぽん], 雁木文=[がんぎもん], 雁=[がん], 雲竜文=[うんりゅうもん], 竜=[りゅう], 火焔文=[かえんもん], 火=[か], 焔=[えん], 白雲文=[はくうんもん], 白=[はく], 斧文=[ふもん], 斧=[ふ], 木=[き]},[0, 1, 2, 3, 4, 5, 6, 7, 8])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, " 唐獅子文（からししもん）"),
						Arrays.asList(null, "雲文（うんもん）", "卍文（まんじもん）", "鼈文（すっぽんもん）", "雁木文（がんぎもん）", "雲竜文（うんりゅうもん）",
								"火焔文（かえんもん）", "白雲文（はくうんもん）", "斧文（ふもん）"))));
		//
		Assertions.assertEquals("({尉=[じょう], 姥=[うば]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "尉と姥（じょうとうば）"), null)));
		//
		Assertions.assertEquals("({二=[ふた], 紋=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "二つ紋（ふたつもん）"), null)));
		//
		Assertions.assertEquals("({文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "結び文（むすびもん）"), null)));
		//
		Assertions.assertEquals("({角=[かく]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "撫で角（なでかく）"), null)));
		//
		Assertions.assertEquals("({鮫=[さめ]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "乱れ鮫（みだれざめ）"), null)));
		//
		Assertions.assertEquals("({縞=[しま]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "破れ縞（やぶれじま）"), null)));
		//
		Assertions.assertEquals("({江=[え], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "住の江文（すみのえもん）"), null)));
		//
		Assertions.assertEquals("({子=[こ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鹿の子文（かのこもん）"), null)));
		//
		Assertions.assertEquals("({一=[いち]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "一の貝絣（いちのかいがすり）"), null)));
		//
		Assertions.assertEquals("({二=[ふた], 蝶=[ちょう], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "二つ蝶文（ふたつちょうもん）"), null)));
		//
		Assertions.assertEquals("({杖=[つえ], 瓢=[ひょう], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "杖と瓢文（つえとひょうもん）"), null)));
		//
		Assertions.assertEquals("({鶴=[つる], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "歩く鶴文（あるくつるもん）"), null)));
		//
		Assertions.assertEquals("({柳=[やなぎ], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "靡く柳文（なびくやなぎもん）"), null)));
		//
		Assertions.assertEquals("({友禅=[ゆうぜん], 友=[ゆう], 禅=[ぜん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "写し友禅（うつしゆうぜん）"), null)));
		//
		Assertions.assertEquals("({模様=[もよう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "散し模様（ちらしもよう）"), null)));
		//
		Assertions.assertEquals("({波=[なみ], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "怒る波文（いかるなみもん）"), null)));
		//
		Assertions.assertEquals("({猪=[いのしし], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "走る猪文（はしるいのししもん）"), null)));
		//
		Assertions.assertEquals("({子=[こ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "踊り子文（おどりこもん）"), null)));
		//
		Assertions.assertEquals("({桐=[きり], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "登り桐文（のぼりきりもん）"), null)));
		//
		Assertions.assertEquals("({梅文=[うめもん], 梅=[うめ], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "重ね梅文（かさねうめもん）"), null)));
		//
		Assertions.assertEquals("({松=[まつ], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "重ね松文（かさねまつもん）"), null)));
		//
		Assertions.assertEquals("({模様=[もよう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "重ね模様（かさねもよう）"), null)));
		//
		Assertions.assertEquals("({桐=[きり], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "靡き桐文（なびききりもん））"), null)));
		//
	}

	@Test
	void testToMultimapAndIntCollection4() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({菊文=[きくもん], 菊=[きく], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "流れ菊文（ながれきくもん）"), null)));
		//
		Assertions.assertEquals("({雲=[くも], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "流れ雲文（ながれくももん）"), null)));
		//
		Assertions.assertEquals("({蝶文=[ちょうもん], 蝶=[ちょう], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "向い蝶文（むかいちょうもん）"), null)));
		//
		Assertions.assertEquals("({鶴=[つる], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "向い鶴文（むかいつるもん）"), null)));
		//
		Assertions.assertEquals("({蘆=[あし], 雁文=[かりもん], 雁=[かり], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蘆に雁文（あしにかりもん）"), null)));
		//
		Assertions.assertEquals("({波=[なみ], 魚=[うお], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "波に魚文（なみにうおもん）"), null)));
		//
		Assertions.assertEquals("({波=[なみ], 鳥=[とり], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "波に鳥文（なみにとりもん）"), null)));
		//
		Assertions.assertEquals("({波=[なみ], 舟=[ふね], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "波に舟文（なみにふねもん）"), null)));
		//
		Assertions.assertEquals("({雁金=[かりがね]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "飛び雁金（とびかりがね）"), null)));
		//
		Assertions.assertEquals("({絵羽=[えば], 絵=[え], 羽=[は]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "絵羽（えば）"), null)));
		//
		Assertions.assertEquals("({章魚=[たこ]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "章魚（たこ）"), null)));
		//
		Assertions.assertEquals("({迦楼羅=[かるら], 迦=[か], 羅=[ら]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "迦楼羅（かるら）"), null)));
		//
		Assertions.assertEquals("({雲気=[うんき], 雲=[うん], 気=[き]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲気（うんき）"), null)));
		//
		Assertions.assertEquals("({如意=[にょい], 如=[にょ], 意=[い]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "如意（にょい）"), null)));
		//
		Assertions.assertEquals("({渦文=[かもん], 渦=[か], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "渦文（かもん）"), null)));
		//
		Assertions.assertEquals("({花文=[かもん], 花=[か], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "花文（かもん）"), null)));
		//
		Assertions.assertEquals("({緯錦=[いきん], 緯=[い], 錦=[きん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "緯錦（いきん）"), null)));
		//
		Assertions.assertEquals("({厚子=[あつし], 子=[し], 格子=[こうし], 格=[こう]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "厚子（あつし）"),
						Arrays.asList(null, "格子（こうし）"))));
		//
		Assertions.assertEquals("({縄目=[なわめ], 縄=[なわ], 目=[め], 網目=[あみめ], 網=[あみ]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "縄目（なわめ）"),
						Arrays.asList(null, "網目（あみめ）"))));
		//
		Assertions.assertEquals("({雲間=[くもま], 雲=[くも], 間=[ま], 欄間=[らんま], 欄=[らん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲間（くもま）"),
						Arrays.asList(null, "欄間（らんま）"))));
		//
		Assertions.assertEquals("({厚司=[あつし]},[0])", Objects.toString(toMultimapAndIntCollection(patternMap,
				IntObjectPair.of(ZERO, "厚司（あつし）"), Arrays.asList(null, "井桁（いげた）"))));
		//
		Assertions.assertEquals("({蒼縞=[あおじま]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蒼縞（あおじま）"), null)));
		//
		Assertions.assertEquals("({雲文=[うんもん], 雲=[うん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲文（うんもん）"), null)));
		//
		Assertions.assertEquals("({円文=[えんもん], 円=[えん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "円文（えんもん）"), null)));
		//
		Assertions.assertEquals("({斑点=[はんてん], 斑=[はん], 点=[てん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "斑点（はんてん）"), null)));
		//
	}

	@Test
	void testToMultimapAndIntCollection5() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({今春=[こんぱる], 今=[こん], 春=[はる]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "今春（こんぱる）"), null)));
		//
		Assertions.assertEquals("({転風=[てんぷう], 転=[てん], 風=[ふう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "転風（てんぷう）"), null)));
		//
		Assertions.assertEquals("({蘆文=[あしもん], 蘆=[あし], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蘆文（あしもん）"), null)));
		//
		Assertions.assertEquals("({羽状=[うじょう], 羽=[う], 状=[じょう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "羽状（うじょう）"), null)));
		//
		Assertions.assertEquals("({牙象=[げしょう], 牙=[げ], 象=[しょう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "牙象（げしょう）"), null)));
		//
		Assertions.assertEquals("({刺繍=[ししゅう], 刺=[し], 繍=[しゅう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "刺繍（ししゅう）"), null)));
		//
		Assertions.assertEquals("({亀甲=[きっこう], 亀=[き], 甲=[こう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "亀甲（きっこう）"), null)));
		//
		Assertions.assertEquals("({目交=[もっこう], 交=[こう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "目交（もっこう）"), null)));
		//
		Assertions.assertEquals("({八掛=[はっかけ], 掛=[かけ]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "八掛（はっかけ）"), null)));
		//
		Assertions.assertEquals("({亀綾=[かめあや], 亀文=[かめもん], 亀=[かめ], 綾=[あや], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "亀綾（かめあや）"),
						Arrays.asList(null, "亀文（かめもん）"))));
		//
		Assertions.assertEquals("({杉綾=[すぎあや], 杉文=[すぎもん], 杉=[すぎ], 綾=[あや], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "杉綾（すぎあや）"),
						Arrays.asList(null, "杉文（すぎもん）"))));
		//
		Assertions.assertEquals("({鶴亀=[つるかめ], 鶴文=[つるもん], 鶴=[つる], 亀=[かめ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鶴亀（つるかめ）"),
						Arrays.asList(null, "鶴文（つるもん）"))));
		//
		Assertions.assertEquals("({指樽=[さしだる], 指貫=[さしぬき], 指=[さし], 貫=[ぬき], 樽=[たる]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "指樽（さしだる）"),
						Arrays.asList(null, "指貫（さしぬき）"))));
		//
		Assertions.assertEquals("({指貫=[さしぬき], 指樽=[さしだる], 指=[さし], 貫=[ぬき], 樽=[たる]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "指貫（さしぬき）"),
						Arrays.asList(null, "指樽（さしだる）"))));
		//
		Assertions.assertEquals("({角樽=[つのだる], 角文=[つのもん], 角=[つの], 文=[もん], 樽=[たる]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "角樽（つのだる）"),
						Arrays.asList(null, "角文（つのもん）"))));
		//
		Assertions.assertEquals("({遠菱=[とおびし], 菱=[もん]},[0, 1])", Objects.toString(toMultimapAndIntCollection(patternMap,
				IntObjectPair.of(ZERO, "遠菱（とおびし）"), Arrays.asList(null, "遠文（とうもん）"))));
		//
		Assertions.assertEquals("({絵絣=[えがすり], 絵=[え], 詞=[ことば], 絣=[かすり]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "絵絣（えがすり）"),
						Arrays.asList(null, "絵詞（えことば）"))));
		//
	}

	private static Entry<Multimap<String, String>, IntCollection> toMultimapAndIntCollection(
			final PatternMap patternMap, final IntObjectPair<String> iop, final Iterable<String> lines)
			throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_AND_INT_COLLECTION.invoke(null, patternMap, iop, lines);
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
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept(Predicates.alwaysTrue(), null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, ZERO, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b, c) -> false, null, null, null, null));
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

	private static <T> void testAndAccept(final ObjIntPredicate<T> predicate, final T a, final int b,
			final ObjIntConsumer<T> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT4.invoke(null, predicate, a, b, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U, V> void testAndAccept(final TriPredicate<T, U, V> instance, final T t, final U u, final V v,
			final TriConsumer<T, U, V> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT5.invoke(null, instance, t, u, v, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAppend() throws Throwable {
		//
		TextStringBuilder tsb = new TextStringBuilder();
		//
		Assertions.assertSame(tsb, append(tsb, Util.cast(String.class, Narcissus.allocateInstance(String.class))));
		//
		Assertions.assertSame(
				tsb = Util.cast(TextStringBuilder.class, Narcissus.allocateInstance(TextStringBuilder.class)),
				append(tsb, Util.cast(String.class, Narcissus.allocateInstance(String.class))));
		//
	}

	private static TextStringBuilder append(final TextStringBuilder instance, final String str) throws Throwable {
		try {
			final Object obj = METHOD_APPEND.invoke(null, instance, str);
			if (obj == null) {
				return null;
			} else if (obj instanceof TextStringBuilder) {
				return (TextStringBuilder) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testSubstring() throws Throwable {
		//
		Assertions.assertNull(substring(
				Util.cast(TextStringBuilder.class, Narcissus.allocateInstance(TextStringBuilder.class)), 0, 1));
		//
	}

	private static String substring(final TextStringBuilder instance, final int startIndex, final int endIndex)
			throws Throwable {
		try {
			final Object obj = METHOD_SUB_STRING.invoke(null, instance, startIndex, endIndex);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndRun() {
		//
		Assertions.assertDoesNotThrow(() -> testAndRun(true, null));
		//
	}

	private static void testAndRun(final boolean b, final Runnable runnable) throws Throwable {
		try {
			METHOD_TEST_AND_RUN.invoke(null, b, runnable);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testIH() throws ReflectiveOperationException {
		//
		final Class<?> clz = Class.forName(
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean$IH");
		//
		final InvocationHandler ih = Util.cast(InvocationHandler.class, Narcissus.allocateInstance(clz));
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, null, null, null));
		//
		final Map<?, ?> map = new LinkedHashMap<>();
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, map, null, null));
		//
		final Method putAll = Map.class.getDeclaredMethod("putAll", Map.class);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, map, putAll, null));
		//
		final Object[] emptyArray = new Object[] {};
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, map, putAll, emptyArray));
		//
		final Method get = Map.class.getDeclaredMethod("get", Object.class);
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, map, get, null));
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, map, get, emptyArray));
		//
		Assertions.assertThrows(Throwable.class, () -> invoke(ih, map, get, new Object[] { null }));
		//
	}

	private static Object invoke(final InvocationHandler instance, final Object proxy, final Method method,
			final Object[] args) throws Throwable {
		return instance != null ? instance.invoke(proxy, method, args) : null;
	}

}