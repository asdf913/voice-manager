package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.text.TextStringBuilder;
import org.d2ab.collection.ints.IntCollection;
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

	private static Method METHOD_TEST_AND_APPLY, METHOD_GET_COMMON_SUFFIX, METHOD_TO_MULTI_MAP_AND_INT_COLLECTION,
			METHOD_TEST_AND_ACCEPT4, METHOD_TEST_AND_ACCEPT5, METHOD_APPEND;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_COMMON_SUFFIX = clz.getDeclaredMethod("getCommonSuffix", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_AND_INT_COLLECTION = clz.getDeclaredMethod("toMultimapAndIntCollection", PatternMap.class,
				IntObjectPair.class, Iterable.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4 = clz.getDeclaredMethod("testAndAccept", ObjIntPredicate.class, Object.class,
				Integer.TYPE, ObjIntConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT5 = clz.getDeclaredMethod("testAndAccept", TriPredicate.class, Object.class,
				Object.class, Object.class, TriConsumer.class)).setAccessible(true);
		//
		(METHOD_APPEND = clz.getDeclaredMethod("append", TextStringBuilder.class, String.class)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBeanTest.testGetObject");
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
	void testNull() {
		//
		final Method[] ms = getDeclaredMethods(
				OtoYakuNoHeyaYomikataJitendDentouMonyouYomikataJitenMultimapFactoryBean.class);
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
				if (Objects.equals(Integer.TYPE, parameterTypes[j])) {
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
			if ((Objects.equals(name, "getCommonSuffix")
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
							new Class<?>[] { PatternMap.class, IntObjectPair.class, Iterable.class, Entry.class }))) {
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
	void testGetCommonSuffix() throws Throwable {
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertEquals(EMPTY, getCommonSuffix(EMPTY, null));
			//
			Assertions.assertEquals(EMPTY, getCommonSuffix(EMPTY, EMPTY));
			//
			Assertions.assertEquals(EMPTY, getCommonSuffix(SPACE, EMPTY));
			//
			Assertions.assertEquals(SPACE, getCommonSuffix(SPACE, SPACE));
			//
		} // if
			//
	}

	private static String getCommonSuffix(final String s1, final String s2) throws Throwable {
		try {
			final Object obj = METHOD_GET_COMMON_SUFFIX.invoke(null, s1, s2);
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
	void testToMultimapAndIntCollection() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		final PatternMap patternMap = new PatternMapImpl();
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
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, ZERO, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b, c) -> false, null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b, c) -> true, null, null, null, null));
		//
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

}