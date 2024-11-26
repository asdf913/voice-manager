package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
import org.apache.commons.lang3.tuple.Pair;
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
			METHOD_TEST_AND_ACCEPT4, METHOD_TEST_AND_ACCEPT5, METHOD_APPEND, METHOD_SUB_STRING, METHOD_TEST_AND_RUN,
			METHOD_TO_ENTRY_18A1;

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
		(METHOD_TO_ENTRY_18A1 = clz.getDeclaredMethod("toEntry18A1", Iterable.class, Entry.class)).setAccessible(true);
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
		final Method[] ms = ArrayUtils.addAll(ArrayUtils.addAll(ArrayUtils.addAll(ArrayUtils.addAll(
				getDeclaredMethods(OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean.class),
				getDeclaredMethods(Class.forName(
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean$ObjObjIntObjObjFunction"))),
				getDeclaredMethods(Class.forName(
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean$ObjIntObjObjFunction"))),
				getDeclaredMethods(Class.forName(
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean$IntMap"))),
				getDeclaredMethods(Class.forName(
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean$IntObjFunction")));
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
				} else if (Objects.equals(parameterType, Long.TYPE)) {
					//
					list.add(Long.valueOf(0));
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
			if (ArrayUtils.contains(new Class<?>[] { Boolean.TYPE, Long.TYPE, Integer.TYPE, Character.TYPE },
					m.getReturnType())
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
		Assertions.assertEquals("({聖樹文=[せいじゅもん], 聖=[せい], 樹=[じゅ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "聖樹文（せいじゅもん）"), null)));
		//
		Assertions.assertEquals("({双魚文=[そうぎょもん], 双=[そう], 魚=[ぎょ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "双魚文（そうぎょもん）"), null)));
		//
		Assertions.assertEquals("({宝珠文=[ほうじゅもん], 宝=[ほう], 文=[もん], 珠=[しゅ]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "宝珠文（ほうじゅもん）"), null)));
		//
		Assertions.assertEquals("({鈴杵文=[れいしょもん], 鈴=[れい], 杵=[しょ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鈴杵文（れいしょもん）"), null)));
		//
		Assertions.assertEquals("({銀杏文=[いちょうもん], 銀杏=[いちょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "銀杏文（いちょうもん）"), null)));
		//
		Assertions.assertEquals("({桔梗文=[ききょうもん], 桔梗=[ききょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "桔梗文（ききょうもん）"), null)));
		//
		Assertions.assertEquals("({飛鳥文=[ひちょうもん], 飛=[ひ], 鳥=[ちょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "飛鳥文（ひちょうもん）"), null)));
		//
		Assertions.assertEquals("({飛龍文=[ひりゅうもん], 飛=[ひ], 龍=[りゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "飛龍文（ひりゅうもん）"), null)));
		//
	}

	@Test
	void testToMultimapAndIntCollection6() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({浮線稜=[ふりょうせん], 浮=[ふ], 稜=[りょう], 線=[せん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "浮線稜（ふりょうせん）"), null)));
		//
		Assertions.assertEquals("({華虫文=[かちゅうもん], 華=[か], 虫=[ちゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "華虫文（かちゅうもん）"), null)));
		//
		Assertions.assertEquals("({花鳥文=[かちょうもん], 花=[か], 鳥=[ちょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "花鳥文（かちょうもん）"), null)));
		//
		Assertions.assertEquals("({火龍文=[かりゅうもん], 火=[か], 龍=[りゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "火龍文（かりゅうもん）"), null)));
		//
		Assertions.assertEquals("({奇獣文=[きじゅうもん], 奇=[き], 獣=[じゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "奇獣文（きじゅうもん）"), null)));
		//
		Assertions.assertEquals("({孔雀文=[くじゃくもん], 孔=[く], 雀=[じゃく], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "孔雀文（くじゃくもん）"), null)));
		//
		Assertions.assertEquals("({胡蝶文=[こちょうもん], 胡=[こ], 蝶=[ちょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "胡蝶文（こちょうもん）"), null)));
		//
		Assertions.assertEquals("({亀甲文=[きっこうもん], 亀甲=[きっこう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "亀甲文（きっこうもん）"), null)));
		//
		Assertions.assertEquals("({八宝文=[はっぽうもん], 八宝=[はっぽう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "八宝文（はっぽうもん）"), null)));
		//
		Assertions.assertEquals("({鹿鶴文=[ろっかくもん], 鹿鶴=[ろっかく], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鹿鶴文（ろっかくもん）"), null)));
		//
		Assertions.assertEquals("({丁子文=[ちょうじもん], 丁子=[ちょうじ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "丁子文（ちょうじもん）"), null)));
		//
		Assertions.assertEquals("({茗荷文=[みょうがもん], 茗荷=[みょうが], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "茗荷文（みょうがもん）"), null)));
		//
		Assertions.assertEquals("({京小紋=[きょうこもん], 京=[きょう], 小=[こ], 紋=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "京小紋（きょうこもん）"), null)));
		//
		Assertions.assertEquals("({菖蒲文=[しょうぶもん], 菖=[しょう], 蒲=[ぶ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "菖蒲文（しょうぶもん）"), null)));
		//
		Assertions.assertEquals("({書籍文=[しょせきもん], 書=[しょ], 籍=[せき], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "書籍文（しょせきもん）"), null)));
		//
		Assertions.assertEquals("({蛇篭文=[じゃかごもん], 蛇=[じゃ], 篭=[かご], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蛇篭文（じゃかごもん）"), null)));
		//
		Assertions.assertEquals("({蛇体文=[じゃたいもん], 蛇=[じゃ], 体=[たい], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蛇体文（じゃたいもん）"), null)));
		//
		Assertions.assertEquals("({十字文=[じゅうじもん], 十=[じゅう], 字=[じ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "十字文（じゅうじもん）"), null)));
		//
		Assertions.assertEquals("({樹木文=[じゅもくもん], 樹=[じゅ], 木=[もく], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "樹木文（じゅもくもん）"), null)));
		//
		Assertions.assertEquals("({寿老人=[じゅろうじん], 寿=[じゅ], 老=[ろう], 人=[じん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "寿老人（じゅろうじん）"), null)));
		//
		Assertions.assertEquals("({龍花文=[りゅうかもん], 龍=[りゅう], 花=[か], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "龍花文（りゅうかもん）"), null)));
		//
		Assertions.assertEquals("({龍虎文=[りゅうこもん], 龍=[りゅう], 虎=[こ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "龍虎文（りゅうこもん）"), null)));
		//
		Assertions.assertEquals("({蝶文=[ちょうもん], 蝶=[ちょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蝶文（ちょうもん）"), null)));
		//
		Assertions.assertEquals("({平文=[ひょうもん], 平=[ひょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "平文（ひょうもん）"), null)));
		//
		Assertions.assertEquals("({龍文=[りゅうもん], 龍=[りゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "龍文（りゅうもん）"), null)));
		//
	}

	@Test
	void testToMultimapAndIntCollection7() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({縄文=[じょうもん], 縄=[じょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "縄文（じょうもん）"), null)));
		//
		Assertions.assertEquals("({鯱文=[しゃちほこもん], 鯱=[しゃちほこ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鯱文（しゃちほこもん）"), null)));
		//
		Assertions.assertEquals("({汽車文=[きしゃもん], 汽=[き], 車=[しゃ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "汽車文（きしゃもん）"), null)));
		//
		Assertions.assertEquals("({鯉魚文=[りぎょもん], 鯉=[り], 魚=[ぎょ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鯉魚文（りぎょもん）"), null)));
		//
		Assertions.assertEquals("({種子文=[しゅじもん], 種子=[しゅじ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "種子文（しゅじもん）"), null)));
		//
		Assertions.assertEquals("({雪花文=[せっかもん], 雪花=[せっか], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雪花文（せっかもん）"), null)));
		//
		Assertions.assertEquals("({独鈷文=[とっこもん], 独鈷=[とっこ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "独鈷文（とっこもん）"), null)));
		//
		Assertions.assertEquals("({八掛文=[はっけもん], 八掛=[はっけ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "八掛文（はっけもん）"), null)));
		//
		Assertions.assertEquals("({寿字文=[じゅじもん], 寿=[じゅ], 字=[じ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "寿字文（じゅじもん）"), null)));
		//
		Assertions.assertEquals("({棕梠文=[しゅろもん], 棕=[しゅ], 梠=[ろ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "棕梠文（しゅろもん）"), null)));
		//
		Assertions.assertEquals("({鋸歯文=[きょしもん], 鋸歯=[きょし], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鋸歯文（きょしもん）"), null)));
		//
		Assertions.assertEquals("({双龍文=[そうりゅうもん], 双=[そう], 龍=[りゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "双龍文（そうりゅうもん）"), null)));
		//
		Assertions.assertEquals("({蒼龍文=[そうりゅうもん], 蒼=[そう], 龍=[りゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蒼龍文（そうりゅうもん）"), null)));
		//
		Assertions.assertEquals("({大漁文=[たいりょうもん], 大=[たい], 漁=[りょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "大漁文（たいりょうもん）"), null)));
		//
		Assertions.assertEquals("({白鳥文=[はくちょうもん], 白=[はく], 鳥=[ちょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "白鳥文（はくちょうもん）"), null)));
		//
		Assertions.assertEquals("({引両文=[ひきりょうもん], 引=[ひき], 両=[りょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "引両文（ひきりょうもん）"), null)));
		//
		Assertions.assertEquals("({方勝文=[ほうしょうもん], 方=[ほう], 勝=[しょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "方勝文（ほうしょうもん）"), null)));
		//
		Assertions.assertEquals("({痩龍文=[やせりゅうもん], 痩=[やせ], 龍=[りゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "痩龍文（やせりゅうもん）"), null)));
		//
		Assertions.assertEquals("({蜥龍文=[せきりゅうもん], 蜥=[せき], 龍=[りゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蜥龍文（せきりゅうもん）"), null)));
		//
		Assertions.assertEquals("({応竜文=[おうりゅうもん], 応=[おう], 竜=[りゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "応竜文（おうりゅうもん）"), null)));
		//
		Assertions.assertEquals("({篭十文=[かごじゅうもん], 篭=[かご], 十=[じゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "篭十文（かごじゅうもん）"), null)));
		//
		Assertions.assertEquals("({曲尺文=[かねじゃくもん], 曲=[かね], 文=[もん], 尺=[しゃく]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "曲尺文（かねじゃくもん）"), null)));
		//
		Assertions.assertEquals("({雨龍紋=[あめりゅうもん], 雨=[あめ], 龍=[りゅう], 紋=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雨龍紋（あめりゅうもん）"), null)));
		//
		Assertions.assertEquals("({青龍文=[せいりゅうもん], 青=[せい], 龍=[りゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "青龍文（せいりゅうもん）"), null)));
		//
		Assertions.assertEquals("({蜻龍文=[せいりゅうもん], 蜻=[せい], 龍=[りゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蜻龍文（せいりゅうもん）"), null)));
		//
	}

	@Test
	void testToMultimapAndIntCollection8() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({蜥龍文=[せきりゅうもん], 蜥=[せき], 龍=[りゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蜥龍文（せきりゅうもん）"), null)));
		//
		Assertions.assertEquals("({瑠璃鳥文=[るりちょうもん], 瑠璃=[るり], 鳥=[ちょう], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "瑠璃鳥文（るりちょうもん）"), null)));
		//
		Assertions.assertEquals("({亀甲花文=[きっこうかもん], 亀甲=[きっこう], 花=[か], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "亀甲花文（きっこうかもん）"), null)));
		//
		Assertions.assertEquals("({杏葉文=[きょうようもん], 杏=[きょう], 葉=[よう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "杏葉文（きょうようもん）"), null)));
		//
		Assertions.assertEquals("({氷竹文=[ひょうちくもん], 氷=[ひょう], 竹=[ちく], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "氷竹文（ひょうちくもん）"), null)));
		//
		Assertions.assertEquals("({蜀江文=[しょくこうもん], 蜀=[しょく], 江=[こう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蜀江文（しょくこうもん）"), null)));
		//
		Assertions.assertEquals("({植物文=[しょくぶつもん], 植=[しょく], 物=[ぶつ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "植物文（しょくぶつもん）"), null)));
		//
		Assertions.assertEquals("({御所解文=[ごしょどきもん], 御所解=[ごしょどき], 御=[ご], 所=[しょ], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "御所解文（ごしょどきもん）"), null)));
		//
		Assertions.assertEquals("({中啓文=[ちゅうけいもん], 中=[ちゅう], 啓=[けい], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "中啓文（ちゅうけいもん）"), null)));
		//
		Assertions.assertEquals("({龍宮文=[りゅうぐうもん], 龍=[りゅう], 宮=[ぐう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "龍宮文（りゅうぐうもん）"), null)));
		//
		Assertions.assertEquals("({流水文=[りゅうすいもん], 流=[りゅう], 水=[すい], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "流水文（りゅうすいもん）"), null)));
		//
		Assertions.assertEquals("({龍頭文=[りゅうとうもん], 龍=[りゅう], 頭=[とう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "龍頭文（りゅうとうもん）"), null)));
		//
		Assertions.assertEquals("({薊蝶文=[あざみちょうもん], 薊=[あざみ], 蝶=[ちょう], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "薊蝶文（あざみちょうもん）"), null)));
		//
		Assertions.assertEquals("({花鳥襷文=[かちょうたすきもん], 花=[か], 鳥=[ちょう], 襷=[たすき], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "花鳥襷文（かちょうたすきもん）"), null)));
		//
		Assertions.assertEquals("({胡蝶瓢文=[こちょうひさごもん], 胡=[こ], 蝶=[ちょう], 瓢=[ひさご], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "胡蝶瓢文（こちょうひさごもん）"), null)));
		//
		Assertions.assertEquals("({御所車文=[ごしょぐるまもん], 御=[ご], 所=[しょ], 文=[もん], 車=[くるま]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "御所車文（ごしょぐるまもん）"), null)));
		//
		Assertions.assertEquals("({十字架文=[じゅうじかもん], 十=[じゅう], 字=[じ], 架=[か], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "十字架文（じゅうじかもん）"), null)));
		//
		Assertions.assertEquals("({十二支文=[じゅうにしもん], 十=[じゅう], 二=[に], 支=[し], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "十二支文（じゅうにしもん）"), null)));
		//
		Assertions.assertEquals("({雨竜=[あまりゅう], 雨=[あま], 竜=[りゅう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雨竜（あまりゅう）"), null)));
		//
		Assertions.assertEquals("({蛟龍=[こうりゅう], 蛟=[こう], 龍=[りゅう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蛟龍（こうりゅう）"), null)));
		//
		Assertions.assertEquals("({眼象=[げんじょう], 眼=[げん], 象=[しょう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "眼象（げんじょう）"), null)));
		//
		Assertions.assertEquals("({乱絣=[らんがすり], 乱=[らん], 絣=[かすり]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "乱絣（らんがすり）"), null)));
		//
		Assertions.assertEquals("({甃文=[いしだたみもん], 甃=[いしだたみ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "甃文（いしだたみもん）"), null)));
		//
		Assertions.assertEquals("({猪文=[いのししもん], 猪=[いのしし], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "猪文（いのししもん）"), null)));
		//
		Assertions.assertEquals("({鳳文=[おおとりもん], 鳳=[おおとり], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鳳文（おおとりもん）"), null)));
		//
	}

	@Test
	void testToMultimapAndIntCollection9() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({笄文=[こうがいもん], 笄=[こうがい], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "笄文（こうがいもん）"), null)));
		//
		Assertions.assertEquals("({橘文=[たちばなもん], 橘=[たちばな], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "橘文（たちばなもん）"), null)));
		//
		Assertions.assertEquals("({蛤文=[はまぐりもん], 蛤=[はまぐり], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蛤文（はまぐりもん）"), null)));
		//
		Assertions.assertEquals("({鉞文=[まさかりもん], 鉞=[まさかり], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鉞文（まさかりもん）"), null)));
		//
		Assertions.assertEquals("({葵文=[あおいもん], 葵=[あおい], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "葵文（あおいもん）"), null)));
		//
		Assertions.assertEquals("({纏文=[まといもん], 纏=[まとい], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "纏文（まといもん）"), null)));
		//
		Assertions.assertEquals("({霰文=[あられもん], 霰=[あられ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "霰文（あられもん）"), null)));
		//
		Assertions.assertEquals("({菫文=[すみれもん], 菫=[すみれ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "菫文（すみれもん）"), null)));
		//
		Assertions.assertEquals("({筏紋=[いかだもん], 筏=[いかだ], 紋=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "筏紋（いかだもん）"), null)));
		//
		Assertions.assertEquals("({兎文=[うさぎもん], 兎=[うさぎ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "兎文（うさぎもん）"), null)));
		//
		Assertions.assertEquals("({扇文=[おうぎもん], 扇=[おうぎ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "扇文（おうぎもん）"), null)));
		//
		Assertions.assertEquals("({柳文=[やなぎもん], 柳=[やなぎ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "柳文（やなぎもん）"), null)));
		//
		Assertions.assertEquals("({表紋=[おもてもん], 表=[おもて], 紋=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "表紋（おもてもん）"), null)));
		//
		Assertions.assertEquals("({隠文=[かくしもん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "隠文（かくしもん）"), null)));
		//
		Assertions.assertEquals("({楓文=[かえでもん], 楓=[かえで], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "楓文（かえでもん）"), null)));
		//
		Assertions.assertEquals("({柏文=[かしわもん], 柏=[かしわ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "柏文（かしわもん）"), null)));
		//
		Assertions.assertEquals("({轡文=[くつわもん], 轡=[くつわ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "轡文（くつわもん）"), null)));
		//
		Assertions.assertEquals("({烏文=[からすもん], 烏=[からす], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "烏文（からすもん）"), null)));
		//
		Assertions.assertEquals("({車文=[くるまもん], 車=[くるま], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "車文（くるまもん）"), null)));
		//
		Assertions.assertEquals("({蕨文=[わらびもん], 蕨=[わらび], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蕨文（わらびもん）"), null)));
		//
		Assertions.assertEquals("({雀文=[すずめもん], 雀=[すずめ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雀文（すずめもん）"), null)));
		//
		Assertions.assertEquals("({巴文=[ともえもん], 巴=[ともえ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "巴文（ともえもん）"), null)));
		//
		Assertions.assertEquals("({瓢文=[ひさごもん], 瓢=[ひさご], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "瓢文（ひさごもん）"), null)));
		//
		Assertions.assertEquals("({羊文=[ひつじもん], 羊=[ひつじ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "羊文（ひつじもん）"), null)));
		//
		Assertions.assertEquals("({竝文=[ならべもん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "竝文（ならべもん）"), null)));
		//
	}

	@Test
	void testToMultimapAndIntCollection10() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({襷文=[たすきもん], 襷=[たすき], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "襷文（たすきもん）"), null)));
		//
		Assertions.assertEquals("({椿文=[つばきもん], 椿=[つばき], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "椿文（つばきもん）"), null)));
		//
		Assertions.assertEquals("({籬文=[まがきもん], 籬=[まがき], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "籬文（まがきもん）"), null)));
		//
		Assertions.assertEquals("({滕文=[ちきりもん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "滕文（ちきりもん）"), null)));
		//
		Assertions.assertEquals("({碇紋=[いかりもん], 碇=[いかり], 紋=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "碇紋（いかりもん）"), null)));
		//
		Assertions.assertEquals("({鏃文=[やじりもん], 鏃=[やじり], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鏃文（やじりもん）"), null)));
		//
		Assertions.assertEquals("({鶉文=[うずらもん], 鶉=[うずら], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鶉文（うずらもん）"), null)));
		//
		Assertions.assertEquals("({桜文=[さくらもん], 桜=[さくら], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "桜文（さくらもん）"), null)));
		//
		Assertions.assertEquals("({俵文=[たわらもん], 俵=[たわら], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "俵文（たわらもん）"), null)));
		//
		Assertions.assertEquals("({櫓文=[やぐらもん], 櫓=[やぐら], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "櫓文（やぐらもん）"), null)));
		//
		Assertions.assertEquals("({薊文=[あざみもん], 薊=[あざみ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "薊文（あざみもん）"), null)));
		//
		Assertions.assertEquals("({鏡文=[かがみもん], 鏡=[かがみ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鏡文（かがみもん）"), null)));
		//
		Assertions.assertEquals("({霞文=[かすみもん], 霞=[かすみ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "霞文（かすみもん）"), null)));
		//
		Assertions.assertEquals("({鼓文=[つずみもん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鼓文（つずみもん）"), null)));
		//
		Assertions.assertEquals("({鼠文=[ねずみもん], 鼠=[ねずみ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鼠文（ねずみもん）"), null)));
		//
		Assertions.assertEquals("({霰絣=[あられがすり], 霰=[あられ], 霰文=[あられもん], 文=[もん], 絣=[かすり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "霰絣（あられがすり）"),
						Arrays.asList(null, "霰文（あられもん）"))));
		//
		Assertions.assertEquals("({馬標=[うまじるし], 馬=[うま], 馬文=[うまもん], 文=[もん], 標=[しるし]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "馬標（うまじるし）"),
						Arrays.asList(null, "馬文（うまもん）"))));
		//
		Assertions.assertEquals("({霞入=[かすみいり], 霞=[かすみ], 霞文=[かすみもん], 文=[もん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "霞入（かすみいり）"),
						Arrays.asList(null, "霞文（かすみもん）"))));
		//
		Assertions.assertEquals("({経絣=[たてがすり], 経文=[たてもん], 文=[もん], 絣=[かすり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "経絣（たてがすり）"),
						Arrays.asList(null, "経文（たてもん）"))));
		//
		Assertions.assertEquals("({鼠絣=[ねずみがすり], 鼠=[ねずみ], 鼠文=[ねずみもん], 文=[もん], 絣=[かすり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鼠絣（ねずみがすり）"),
						Arrays.asList(null, "鼠文（ねずみもん）"))));
		//
		Assertions.assertEquals("({鼠縞=[ねずみじま], 鼠=[ねずみ], 鼠文=[ねずみもん], 文=[もん], 縞=[しま]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鼠縞（ねずみじま）"),
						Arrays.asList(null, "鼠文（ねずみもん）"))));
		//
		Assertions.assertEquals("({雨絣=[あめがすり], 雨=[あめ], 絵絣=[えがすり], 絵=[え], 絣=[かすり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雨絣（あめがすり）"),
						Arrays.asList(null, "絵絣（えがすり）"))));
		//
		Assertions.assertEquals("({朧絣=[おぼろがすり], 朧=[おぼろ], 絵絣=[えがすり], 絵=[え], 絣=[かすり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "朧絣（おぼろがすり）"),
						Arrays.asList(null, "絵絣（えがすり）"))));
		//
		Assertions.assertEquals("({経絣=[たてがすり], 絵絣=[えがすり], 絵=[え], 絣=[かすり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "経絣（たてがすり）"),
						Arrays.asList(null, "絵絣（えがすり）"))));
		//
		Assertions.assertEquals("({抜絣=[ぬきがすり], 絵絣=[えがすり], 絵=[え], 絣=[かすり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "抜絣（ぬきがすり）"),
						Arrays.asList(null, "絵絣（えがすり）"))));
		//
	}

	@Test
	void testToMultimapAndIntCollection11() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({鼠絣=[ねずみがすり], 鼠=[ねずみ], 絵絣=[えがすり], 絵=[え], 絣=[かすり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鼠絣（ねずみがすり）"),
						Arrays.asList(null, "絵絣（えがすり）"))));
		//
		Assertions.assertEquals("({闇絣=[やみがすり], 闇=[やみ], 絵絣=[えがすり], 絵=[え], 絣=[かすり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "闇絣（やみがすり）"),
						Arrays.asList(null, "絵絣（えがすり）"))));
		//
		Assertions.assertEquals("({緯絣=[よこがすり], 絵絣=[えがすり], 絵=[え], 絣=[かすり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "緯絣（よこがすり）"),
						Arrays.asList(null, "絵絣（えがすり）"))));
		//
		Assertions.assertEquals("({朧縞=[おぼろじま], 朧=[おぼろ], 蒼縞=[あおじま], 蒼=[あお], 縞=[しま]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "朧縞（おぼろじま）"),
						Arrays.asList(null, "蒼縞（あおじま）"))));
		//
		Assertions.assertEquals("({鼠縞=[ねずみじま], 鼠=[ねずみ], 蒼縞=[あおじま], 蒼=[あお], 縞=[しま]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鼠縞（ねずみじま）"),
						Arrays.asList(null, "蒼縞（あおじま）"))));
		//
		Assertions.assertEquals("({盲縞=[めくらじま], 蒼縞=[あおじま], 蒼=[あお], 縞=[しま]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "盲縞（めくらじま）"),
						Arrays.asList(null, "蒼縞（あおじま）"))));
		//
		Assertions.assertEquals("({間道=[かんとう], 間=[かん], 道=[とう]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "間道（かんとう）＊織物の一種"), null)));
		//
		Assertions.assertEquals("({珍絣=[ちんがすり], 珍=[ちん], 絣=[かすり]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "珍絣（ちんがすり）＊縞柄の一種"), null)));
		//
		Assertions.assertEquals("({緞子=[どんす], 緞=[どん], 子=[す]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "緞子（どんす）＊織物の一種"), null)));
		//
		Assertions.assertEquals("({万筋=[まんすじ], 万=[まん], 筋=[すじ]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "万筋（まんすじ）＊竪縞の一種"), null)));
		//
		Assertions.assertEquals("({意匠=[いしょう], 意=[い], 匠=[しょう]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "意匠（いしょう）＊図案と同義"), null)));
		//
		Assertions.assertEquals("({手柏=[てがしわ], 手=[て], 手杵文=[てぎねもん], 文=[もん], 柏=[かしわ], 杵=[きね]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "手柏（てがしわ）＊縞柄の呼称"),
						Arrays.asList(null, "手杵文（てぎねもん）"))));
		//
		Assertions.assertEquals("({矢絣=[やがすり], 矢=[や], 矢車文=[やぐるまもん], 文=[もん], 絣=[かすり], 車=[くるま]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "矢絣（やがすり）＊絣柄の一種"),
						Arrays.asList(null, "矢車文（やぐるまもん）"))));
		//
		Assertions.assertEquals("({棒縞=[ぼうじま], 棒=[ぼう], 蒼縞=[あおじま], 蒼=[あお], 縞=[しま]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "棒縞（ぼうじま）＊縞柄の一種"),
						Arrays.asList(null, "蒼縞（あおじま）"))));
		//
		Assertions.assertEquals("({矢絣=[やがすり], 矢=[や], 雨絣=[あめがすり], 雨=[あめ], 絣=[かすり]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "矢絣（やがすり）＊絣柄の一種"),
						Arrays.asList(null, "雨絣（あめがすり）"))));
		//
		Assertions.assertEquals("({嫁柄=[よめがら], 嫁=[よめ], 柄=[がら], 中柄=[ちゅうがら], 中=[ちゅう]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "嫁柄（よめがら）＊絣柄の呼称"),
						Arrays.asList(null, "中柄（ちゅうがら）"))));
		//
		Assertions.assertEquals("({沈金=[ちんきん], 沈=[ちん], 金=[きん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "沈金（ちんきん）＊漆工技法の一種"), null)));
		//
		Assertions.assertEquals("({平塵=[へいじん], 平=[へい], 塵=[じん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "平塵（へいじん）＊漆工技法の一種"), null)));
		//
		Assertions.assertEquals("({螺鈿=[らでん], 螺=[ら], 鈿=[でん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "螺鈿（らでん）＊漆工技法の一種"), null)));
		//
		Assertions.assertEquals("({蒔絵=[まきえ], 絵=[え], 上絵=[うわえ], 上=[うわ]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蒔絵（まきえ）＊漆工技法の一種"),
						Arrays.asList(null, "上絵（うわえ）＊工芸用語"))));
		//
		Assertions.assertEquals("({堆黒=[ついこく], 堆=[つい], 黒=[こく], 堆朱=[ついしゅ], 朱=[しゅ]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "堆黒（ついこく）＊漆工技法の一種"),
						Arrays.asList(null, " 堆朱（ついしゅ）＊漆工技法の一種"))));
		//
		Assertions.assertEquals("({平脱=[へいだつ], 平=[へい], 脱=[だつ], 平塵=[へいじん], 塵=[じん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "平脱（へいだつ）＊漆工技法の一種"),
						Arrays.asList(null, "平塵（へいじん）＊漆工技法の一種"))));
		//
		Assertions.assertEquals("({竜=[りょう]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "あま竜（あまりょう）＊あまは璃の左が虫"), null)));
		//
		Assertions.assertEquals("({霰=[あられ]},[0])", Objects.toString(
				toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "かに霰（かにあられ）＊（か）は穴かんむりに果"), null)));
		//
		Assertions.assertEquals("({文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "か文（かもん）＊（か）は穴かんむりに果"), null)));
		//
	}

	@Test
	void testToMultimapAndIntCollection12() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({縞=[しま]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "どし縞（どしじま）＊縞柄の一種"), null)));
		//
		Assertions.assertEquals("({文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "ひじき文（ひじきもん）"), null)));
		//
		Assertions.assertEquals("({文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "ほなが文（ほながもん）"), null)));
		//
		Assertions.assertEquals("({縞=[しま]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "みくら縞（みくらしま）"), null)));
		//
		Assertions.assertEquals("({蒟醤=[きんま]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蒟醤（きんま）＊香箱"), null)));
		//
		Assertions.assertEquals("({懸魚=[けぎょ], 懸=[け], 魚=[ぎょ]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "懸魚（けぎょ）＊飾板"), null)));
		//
		Assertions.assertEquals("({平文=[ひょうもん], 平=[ひょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "平文（ひょうもん）＊技法"), null)));
		//
		Assertions.assertEquals("({庵紋=[いおりもん], 庵=[いおり], 紋=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "庵紋（いおりもん）＊紋章"), null)));
		//
		Assertions.assertEquals("({囘文=[かいもん], 囘=[かい], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "囘文（かいもん）＊中国"), null)));
		//
		Assertions.assertEquals("({摺箔=[すりはく], 箔=[はく], 摺文=[すりもん], 文=[もん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "摺箔（すりはく）＊技法"),
						Arrays.asList(null, "摺文（すりもん）＊捺染法の一種"))));
		//
		Assertions.assertEquals("({石持=[こくもち], 石=[こく], 持=[もち], 城持文=[しろもちもん], 城=[しろ], 文=[もん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "石持（こくもち）＊紋章"),
						Arrays.asList(null, "城持文（しろもちもん）"))));
		//
		Assertions.assertEquals("({臈纈=[ろうけち], 臈=[ろう], 纈=[けち], 夾纈=[きょうけち], 夾=[きょう]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "臈纈（ろうけち）＊染物"),
						Arrays.asList(null, "夾纈（きょうけち）＊代表的染物"))));
		//
		Assertions.assertEquals("({印金=[いんきん], 印=[いん], 金=[きん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "印金（いんきん）＊名物裂、技法"), null)));
		//
		Assertions.assertEquals("({浮紋=[うきもん], 紋=[もん], 浮=[う]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "浮紋（うきもん）＊浮織物の一つ"), null)));
		//
		Assertions.assertEquals("({暈繝=[うんげん], 暈=[うん], 繝=[げん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "暈繝（うんげん）＊彩色法の一つ"), null)));
		//
		Assertions.assertEquals("({纐纈=[こうけち], 纐=[こう], 纈=[けち], 三纈=[さんけち], 三=[さん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "纐纈（こうけち）＊絞り染め"),
						Arrays.asList(null, "三纈（さんけち）染色法の名称"))));
		//
		Assertions.assertEquals("({印伝革=[いんでんがわ], 印=[いん], 伝=[でん], 革=[かわ]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "印伝革（いんでんがわ）＊染革"), null)));
		//
		Assertions.assertEquals("({尚美紋=[しょうびもん], 尚=[しょう], 美=[び], 紋=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "尚美紋（しょうびもん）＊紋章"), null)));
		//
		Assertions.assertEquals("({尚武紋=[しょうぶもん], 尚=[しょう], 武=[ぶ], 紋=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "尚武紋（しょうぶもん）＊紋章"), null)));
		//
		Assertions.assertEquals("({臼=[うす], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "臼づき文（うすづきもん）"), null)));
		//
		Assertions.assertEquals("({貝=[かい], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "貝づくし文（かいづくしもん）"), null)));
		//
		Assertions.assertEquals("({菊=[きく], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "菊づくし文（きくづくしもん）"), null)));
		//
		Assertions.assertEquals("({雲竜文=[うんりゅうもん], 雲=[うん], 竜=[りゅう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲竜文（うんりゅうもん）"), null)));
		//
		Assertions.assertEquals("({円相文=[えんそうもん], 円=[えん], 相=[そう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "円相文（えんそうもん）"), null)));
		//
		Assertions.assertEquals("({火焔文=[かえんもん], 火=[か], 焔=[えん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "火焔文（かえんもん）"), null)));
		//
	}

	@Test
	void testToMultimapAndIntCollection13() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({花瓶文=[かびんもん], 花=[か], 瓶=[びん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "花瓶文（かびんもん）"), null)));
		//
		Assertions.assertEquals("({階段文=[かいだんもん], 階=[かい], 段=[だん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "階段文（かいだんもん）"), null)));
		//
		Assertions.assertEquals("({海豚文=[かいとんもん], 海=[かい], 豚=[とん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "海豚文（かいとんもん）"), null)));
		//
		Assertions.assertEquals("({扇円文=[おうぎえんもん], 扇=[おうぎ], 円=[えん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "扇円文（おうぎえんもん）"), null)));
		//
		Assertions.assertEquals("({雲錦手=[うんきんで], 雲=[うん], 錦=[きん], 手=[て]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲錦手（うんきんで）"), null)));
		//
		Assertions.assertEquals("({暈繝錦=[うんげんにしき], 暈=[うん], 繝=[げん], 錦=[にしき]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "暈繝錦（うんげんにしき）"), null)));
		//
		Assertions.assertEquals("({長寿文=[ちょうじゅもん], 長=[ちょう], 寿=[じゅ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "長寿文（ちょうじゅもん）"), null)));
		//
		Assertions.assertEquals("({繍球文=[しゅうきゅうもん], 繍=[しゅう], 球=[きゅう], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "繍球文（しゅうきゅうもん）"), null)));
		//
		Assertions.assertEquals("({大十絣=[だいじゅうがすり], 大=[だい], 十=[じゅう], 絣=[かすり]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "大十絣（だいじゅうがすり）"), null)));
		//
		Assertions.assertEquals("({金翅鳥=[きんしちょう], 金=[きん], 翅=[し], 鳥=[ちょう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "金翅鳥（きんしちょう）"), null)));
		//
		Assertions.assertEquals("({御紋章=[ごもんしょう], 御=[ご], 紋=[もん], 章=[しょう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "御紋章（ごもんしょう）"), null)));
		//
		Assertions.assertEquals("({浮線蝶=[ふせんちょう], 浮=[ふ], 線=[せん], 蝶=[ちょう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "浮線蝶（ふせんちょう）"), null)));
		//
		Assertions.assertEquals("({人形手=[にんぎょうで], 人=[にん], 形=[ぎょう], 手=[て]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "人形手（にんぎょうで）"), null)));
		//
		Assertions.assertEquals("({雲去来=[うんきょらい], 雲=[うん], 去=[きょ], 来=[らい]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲去来（うんきょらい）"), null)));
		//
		Assertions.assertEquals("({梅玉縞=[ばいぎょくじま], 梅=[ばい], 玉=[ぎょく], 縞=[しま]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "梅玉縞（ばいぎょくじま）"), null)));
		//
		Assertions.assertEquals("({吉祥文=[きっしょうもん], 吉祥=[きっしょう], 祥=[しょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "吉祥文（きっしょうもん）"), null)));
		//
		Assertions.assertEquals("({月象文=[げっしょうもん], 月象=[げっしょう], 象=[しょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "月象文（げっしょうもん）"), null)));
		//
		Assertions.assertEquals("({日象文=[にっしょうもん], 日象=[にっしょう], 象=[しょう], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "日象文（にっしょうもん）"), null)));
		//
		Assertions.assertEquals("({北条鱗=[ほうじょううろこ], 条=[じょう], 鱗=[うろこ]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "北条鱗（ほうじょううろこ）"), null)));
		//
		Assertions.assertEquals("({間条状=[しまじょう], 状=[じょう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "間条状（しまじょう）"), null)));
		//
		Assertions.assertEquals("({雲板文=[うんばんもん], 雲=[うん], 板=[ばん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲板文（うんばんもん）"), null)));
		//
		Assertions.assertEquals("({算盤絣=[そろばんがすり], 算盤=[そろばん], 盤=[ばん], 絣=[かすり]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "算盤絣（そろばんがすり）"), null)));
		//
		Assertions.assertEquals("({一本絣=[いっぽんがすり], 一本=[いっぽん], 本=[ぽん], 絣=[かすり]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "一本絣（いっぽんがすり）"), null)));
		//
		Assertions.assertEquals("({藍弁慶=[あいべんけい], 藍=[あい], 弁=[べん], 慶=[けい]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "藍弁慶（あいべんけい）"), null)));
		//
		Assertions.assertEquals("({光琳菊=[こうりんぎく], 光=[こう], 琳=[りん], 菊=[きく]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "光琳菊（こうりんぎく）"), null)));
		//
	}

	@Test
	void testToMultimapAndIntCollection14() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({茶弁慶=[ちゃべんけい], 茶弁=[ちゃべん], 茶=[ちゃ], 弁=[べん], 慶=[けい]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "茶弁慶（ちゃべんけい）"), null)));
		//
		Assertions.assertEquals("({友禅染=[ゆうぜんぞめ], 友=[ゆう], 禅=[ぜん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "友禅染（ゆうぜんぞめ）"), null)));
		//
		Assertions.assertEquals("({獅子文=[ししもん], 獅子=[しし], 子=[し], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "獅子文（ししもん）"), null)));
		//
		Assertions.assertEquals("({禾稼文=[かかもん], 禾稼=[かか], 禾=[か], 稼=[か], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "禾稼文（かかもん）"), null)));
		//
		Assertions.assertEquals("({荷花文=[かかもん], 荷花=[かか], 荷=[か], 花=[か], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "荷花文（かかもん）"), null)));
		//
		Assertions.assertEquals("({茄子文=[なすもん], 茄子=[なす], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "茄子文（なすもん）"), null)));
		//
		Assertions.assertEquals("({文字文=[もじもん], 文字=[もじ], 字=[じ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "文字文（もじもん）"), null)));
		//
		Assertions.assertEquals("({蜘蛛文=[くももん], 蜘蛛=[くも], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蜘蛛文（くももん）"), null)));
		//
		Assertions.assertEquals("({海老文=[えびもん], 海老=[えび], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "海老文（えびもん）"), null)));
		//
		Assertions.assertEquals("({熨斗文=[のしもん], 熨斗=[のし], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "熨斗文（のしもん）"), null)));
		//
		Assertions.assertEquals("({伊達紋=[だてもん], 伊達=[だて], 紋=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "伊達紋（だてもん）"), null)));
		//
		Assertions.assertEquals("({独楽文=[こまもん], 独楽=[こま], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "独楽文（こまもん）"), null)));
		//
		Assertions.assertEquals("({屈輪文=[ぐりもん], 屈輪=[ぐり], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "屈輪文（ぐりもん）"), null)));
		//
		Assertions.assertEquals("({枇杷文=[びわもん], 枇杷=[びわ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "枇杷文（びわもん）"), null)));
		//
		Assertions.assertEquals("({琵琶文=[びわもん], 琵琶=[びわ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "琵琶文（びわもん）"), null)));
		//
		Assertions.assertEquals("({鉸具文=[かこもん], 鉸具=[かこ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "鉸具文（かこもん）"), null)));
		//
		Assertions.assertEquals("({幾何文=[きかもん], 幾何=[きか], 幾=[き], 何=[か], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "幾何文（きかもん）"), null)));
		//
		Assertions.assertEquals("({羽根文=[はねもん], 羽根=[はね], 羽=[は], 根=[ね], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "羽根文（はねもん）"), null)));
		//
		Assertions.assertEquals("({真帆文=[まほもん], 真帆=[まほ], 真=[ま], 帆=[ほ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "真帆文（まほもん）"), null)));
		//
		Assertions.assertEquals("({花字文=[かじもん], 花=[か], 字=[じ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "花字文（かじもん）"), null)));
		//
		Assertions.assertEquals("({禧字文=[きじもん], 禧=[き], 字=[じ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "禧字文（きじもん）"), null)));
		//
		Assertions.assertEquals("({喜字文=[きじもん], 喜=[き], 字=[じ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "喜字文（きじもん）"), null)));
		//
		Assertions.assertEquals("({九字文=[くじもん], 九=[く], 字=[じ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "九字文（くじもん）"), null)));
		//
		Assertions.assertEquals("({無字文=[むじもん], 無=[む], 字=[じ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "無字文（むじもん）"), null)));
		//
		Assertions.assertEquals("({古文龍=[こもんりゅう], 古=[こ], 文=[もん], 龍=[りゅう]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "古文龍（こもんりゅう）"), null)));
		//
	}

	@Test
	void testToMultimapAndIntCollection15() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({小中柄=[こちゅうがら], 小=[こ], 中=[ちゅう], 柄=[がら]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "小中柄（こちゅうがら）"), null)));
		//
		Assertions.assertEquals("({石橋文=[しゃっきょうもん], 石橋=[しゃっきょう], 橋=[きょう], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "石橋文（しゃっきょうもん）"), null)));
		//
		Assertions.assertEquals("({猩猩文=[しょうじょうもん], 猩猩=[しょうじょう], 猩=[しょう], 文=[もん]},[0])", Objects
				.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "猩猩文（しょうじょうもん）"), null)));
		//
		Assertions.assertEquals("({桔梗辻=[ききょうつじ], 桔梗=[ききょう], 梗=[きょう], 辻=[つじ]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "桔梗辻（ききょうつじ）"), null)));
		//
		Assertions.assertEquals("({金剛杵=[こんごうしょ], 金剛=[こんごう], 金=[こん], 剛=[ごう], 杵=[しょ]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "金剛杵（こんごうしょ）"), null)));
		//
		Assertions.assertEquals("({拍子木=[ひょうしぎ], 拍=[ひょう], 子=[し], 木=[き]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "拍子木（ひょうしぎ）"), null)));
		//
		Assertions.assertEquals("({直弧文=[ちょっこもん], 弧=[こ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "直弧文（ちょっこもん）"), null)));
		//
		Assertions.assertEquals("({白虎文=[びゃっこもん], 白虎=[びゃっこ], 虎=[こ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "白虎文（びゃっこもん）"), null)));
		//
		Assertions.assertEquals("({浮線桜=[ふせんざくら], 浮=[ふ], 線=[せん], 桜=[ざくら]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "浮線桜（ふせんざくら）"), null)));
		//
		Assertions.assertEquals("({芝翫縞=[しかんじま], 芝=[し], 翫=[がん], 縞=[しま]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "芝翫縞（しかんじま）"), null)));
		//
		Assertions.assertEquals("({微塵縞=[みじんじま], 微=[み], 塵=[じん], 縞=[しま]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "微塵縞（みじんじま）"), null)));
		//
		Assertions.assertEquals("({璃寛縞=[りかんじま], 璃=[り], 寛=[かん], 縞=[しま]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "璃寛縞（りかんじま）"), null)));
		//
		Assertions.assertEquals("({花狭間=[はなざま], 花=[はな], 間=[ま], 文=[もん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "花狭間（はなざま）"),
						Arrays.asList(null, "花寄せ文（はなよせもん）"))));
		//
		Assertions.assertEquals("({夕顔文=[ゆうがおもん], 夕=[ゆう], 文=[もん], 顔=[かお]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "夕顔文（ゆうがおもん）"),
						Arrays.asList(null, "夕涼み文（ゆうすずみもん）"))));
		//
		Assertions.assertEquals("({虫籠文=[むしかごもん], 虫籠=[むしかご], 虫=[むし], 籠=[かご], 文=[もん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "虫籠文（むしかごもん）"),
						Arrays.asList(null, "虫尽し文（むしづくしもん）"))));
		//
		Assertions.assertEquals("({扇風文=[おうぎかぜもん], 扇=[おうぎ], 風=[かぜ], 文=[もん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "扇風文（おうぎかぜもん）"),
						Arrays.asList(null, "扇散し文（おうぎちらしもん）"))));
		//
		Assertions.assertEquals("({宝船文=[たからぶねもん], 宝=[たから], 文=[もん], 船=[ふね]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "宝船文（たからぶねもん）"),
						Arrays.asList(null, "宝尽し文（たからずくしもん）"))));
		//
		Assertions.assertEquals("({雪笹文=[ゆきささもん], 雪笹=[ゆきささ], 雪=[ゆき], 笹=[ささ], 文=[もん], 丸=[まる]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雪笹文（ゆきささもん）"),
						Arrays.asList(null, "雪持ち笹丸文（ゆきもちささまるもん）"))));
		//
		Assertions.assertEquals("({祝鯛文=[いわいたいもん], 祝鯛=[いわいた], 鯛=[たい], 文=[もん], 樽=[たる]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "祝鯛文（いわいたいもん）"),
						Arrays.asList(null, "祝樽に鯛文（いわいだるにたいもん）"))));
		//
		Assertions.assertEquals("({初音文=[はつねもん], 初音=[はつね], 初=[はつ], 音=[ね], 文=[もん], 初日=[はつひ], 日=[ひ], 出=[で]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "初音文（はつねもん）"),
						Arrays.asList(null, "初日の出文（はつひのでもん）"))));
		//
		Assertions.assertEquals("({雪輪文=[ゆきわもん], 雪輪=[ゆきわ], 雪=[ゆき], 輪=[わ], 文=[もん]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雪輪文（ゆきわもん）"),
						Arrays.asList(null, "雪持ち笹丸文（ゆきもちささまるもん）"))));
		//
		Assertions.assertEquals("({菊花文=[きくかもん], 菊花=[きくか], 菊=[きく], 花=[か], 文=[もん], 鳳凰=[ほうおう]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "菊花文（きくかもん）"),
						Arrays.asList(null, "菊食い鳳凰文（きくくいほうおうもん）"))));
		//
		Assertions.assertEquals("({雲渦文=[くもうずもん], 雲=[くも], 渦=[うず], 文=[もん], 梅=[うめ]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲渦文（くもうずもん）"),
						Arrays.asList(null, "雲居の梅文（くもいのうめもん）"))));
		//
		Assertions.assertEquals("({雲形文=[くもがたもん], 雲形=[くもがた], 雲=[くも], 文=[もん], 梅=[うめ], 形=[かた]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲形文（くもがたもん）"),
						Arrays.asList(null, "雲居の梅文（くもいのうめもん）"))));
		//
		Assertions.assertEquals("({雲鳥文=[くもとりもん], 雲鳥=[くもとり], 雲=[くも], 鳥=[とり], 文=[もん], 梅=[うめ]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲鳥文（くもとりもん）"),
						Arrays.asList(null, "雲居の梅文（くもいのうめもん）"))));
		//
	}

	@Test
	void testToMultimapAndIntCollection16() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("({雲花文=[くもはなもん], 雲=[くも], 花=[はな], 文=[もん], 梅=[うめ]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲花文（くもはなもん）"),
						Arrays.asList(null, "雲居の梅文（くもいのうめもん）"))));
		//
		Assertions.assertEquals("({雲丸文=[くもまるもん], 雲=[くも], 丸=[まる], 文=[もん], 梅=[うめ]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲丸文（くもまるもん）"),
						Arrays.asList(null, "雲居の梅文（くもいのうめもん）"))));
		//
		Assertions.assertEquals("({菊水文=[きくすいもん], 菊=[きく], 水=[すい], 文=[もん], 鳳凰=[ほうおう]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "菊水文（きくすいもん）"),
						Arrays.asList(null, "菊食い鳳凰文（きくくいほうおうもん）"))));
		//
		Assertions.assertEquals("({菊丸文=[きくまるもん], 菊=[きく], 丸=[まる], 文=[もん], 鳳凰=[ほうおう]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "菊丸文（きくまるもん）"),
						Arrays.asList(null, "菊食い鳳凰文（きくくいほうおうもん）"))));
		//
		Assertions.assertEquals("({葵水文=[あおいみずもん], 葵=[あおい], 水=[みず], 文=[もん], 車=[くるま]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "葵水文（あおいみずもん）"),
						Arrays.asList(null, "葵車に立湧（あおいぐるまにたちわき）"))));
		//
		Assertions.assertEquals("({立鶴文=[たちづるもん], 立=[たち], 文=[もん], 鶴=[つる]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "立鶴文（たちづるもん）"),
						Arrays.asList(null, "立湧に尾長鳥文（たちわきにおながどりもん）"))));
		//
		Assertions.assertEquals("({立波文=[たちなみもん], 立=[たち], 波=[なみ], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "立波文（たちなみもん）"),
						Arrays.asList(null, "立湧に尾長鳥文（たちわきにおながどりもん）"))));
		//
		Assertions.assertEquals("({秋草紋=[あきくさもん], 秋=[あき], 草=[くさ], 紋=[もん], 文=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "秋草紋（あきくさもん）"),
						Arrays.asList(null, "秋野鹿文（あきののしかもん）"))));
		//
		Assertions.assertEquals("({桐竹文=[きりたけもん], 桐=[きり], 竹=[たけ], 文=[もん], 鳳凰=[ほうおう], 紋=[もん]},[0])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "桐竹文（きりたけもん）"),
						Arrays.asList(null, "桐鳳凰紋（きりほうおうもん）"))));
		//
		Assertions.assertEquals("({雲鶴手=[うんかくで], 雲=[うん], 鶴=[かく], 錦=[きん], 手=[て]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "雲鶴手（うんかくで）"),
						Arrays.asList(null, "雲錦手（うんきんで）"))));
		//
		Assertions.assertEquals("({蘆穂文=[あしほもん], 蘆=[あし], 穂=[ほ], 文=[もん], 辺=[へ]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蘆穂文（あしほもん）"),
						Arrays.asList(null, "蘆辺文（あしべもん）"))));
		//
		Assertions.assertEquals("({蘆辺文=[あしべもん], 蘆=[あし], 文=[もん], 穂=[ほ], 辺=[へ]},[0, 1])",
				Objects.toString(toMultimapAndIntCollection(patternMap, IntObjectPair.of(ZERO, "蘆辺文（あしべもん）"),
						Arrays.asList(null, "蘆穂文（あしほもん）"))));
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
	void testToEntry18A1() {
		//
		final Entry<String, String> entry = Pair.of("k", "v");
		//
		Assertions.assertThrows(IllegalStateException.class, () -> toEntry18A1(Collections.nCopies(2, entry), entry));
		//
	}

	private static Entry<String, String> toEntry18A1(final Iterable<Entry<String, String>> entries,
			final Entry<String, String> kv) throws Throwable {
		try {
			final Object obj = METHOD_TO_ENTRY_18A1.invoke(null, entries, kv);
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