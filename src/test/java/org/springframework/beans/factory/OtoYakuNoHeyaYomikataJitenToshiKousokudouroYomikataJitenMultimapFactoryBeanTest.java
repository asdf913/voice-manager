package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_GET_UNICODE_BLOCKS, METHOD_TEST_AND_APPLY, METHOD_TO_MULTI_MAP1, METHOD_TO_MULTI_MAP3,
			METHOD_TO_MULTI_MAP_ITERABLE, METHOD_TO_MULTI_MAP_4, METHOD_REPLACE_MULTI_MAP_ENTRIES = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP1 = clz.getDeclaredMethod("toMultimap", String.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP3 = clz.getDeclaredMethod("toMultimap", Pattern.class, String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_ITERABLE = clz.getDeclaredMethod("toMultimap", Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_4 = clz.getDeclaredMethod("toMultimap", char[].class, Matcher.class, String.class,
				Number.class)).setAccessible(true);
		//
		(METHOD_REPLACE_MULTI_MAP_ENTRIES = clz.getDeclaredMethod("replaceMultimapEntries", Multimap.class, Map.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Iterable<?> iterator = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (Boolean.logicalAnd(proxy instanceof Iterable, Objects.equals(methodName, "iterator"))) {
				//
				return iterator;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject = false;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBeanTest.testGetObject");
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
		Assertions.assertEquals(multimap, instance != null ? instance.getObject() : null);
		//
		final Entry<Field, Object> entry = TestUtil.getFieldWithUrlAnnotationAndValue(
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean.class);
		//
		final Field url = entry != null ? entry.getKey() : null;
		//
		Narcissus.setObjectField(instance, url, "");
		//
		Assertions.assertEquals(multimap, instance != null ? instance.getObject() : null);
		//
		Narcissus.setObjectField(instance, url, " ");
		//
		Assertions.assertEquals(multimap, instance != null ? instance.getObject() : null);
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			Narcissus.setObjectField(instance, url, entry != null ? entry.getValue() : null);
			//
			Assertions.assertDoesNotThrow(() -> instance != null ? instance.getObject() : null);
			//
		} // if
			//
	}

	@Test
	void testGetUnicodeBlocks() throws Throwable {
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertEquals(Collections.singletonList(UnicodeBlock.BASIC_LATIN), getUnicodeBlocks("  "));
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
	void testToMultimap01() throws Throwable {
		//
		Assertions.assertNull(toMultimap(null, null, null));
		//
		Assertions.assertNull(toMultimap(Reflection.newProxy(Iterable.class, new IH())));
		//
		Assertions.assertNull(toMultimap(null, null, null, null));
		//
	}

	@Test
	void testToMultimap02() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertNull(toMultimap("北"));
		//
		Assertions.assertNull(toMultimap("北こ"));
		//
		final Multimap<?, ?> multimap = ImmutableMultimap.of();
		//
		Assertions.assertEquals(multimap, toMultimap(Collections.singleton(null)));
		//
		Assertions.assertEquals(multimap,
				toMultimap(Collections.singleton(Util.cast(Node.class, Narcissus.allocateInstance(TextNode.class)))));
		//
		Assertions.assertEquals(multimap, toMultimap(Collections.singleton(new TextNode("建設中路線"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("糟屋郡粕屋町", "かすやぐんかすやまち", "戸原", "とばら", "蒲田", "かまた")),
				MultimapUtil.entries(toMultimap(Util.toList(
						Util.map(Util.map(Stream.of("建設中路線", "福岡高速４号線　糟屋郡粕屋町（かすやぐんかすやまち）大字戸原（とばら）〜福岡市東区蒲田（かまた）三丁目"),
								TextNode::new), x -> Util.cast(Node.class, x)))))));
		//
		// TODO
		//
		if (false) {
			//
			Assertions
					.assertTrue(
							CollectionUtils.isEqualCollection(
									MultimapUtil.entries(ImmutableMultimap.of("西月隈", "にしつきぐま", "福重", "ふくしげ")),
									MultimapUtil.entries(toMultimap(Util.toList(Util.map(Util.map(
											Stream.of("建設中路線", "福岡高速５号線　福岡市博多区西月隈（にしつきぐま）四丁目〜福岡市西区福重（ふくしげ）三丁目"),
											TextNode::new), x -> Util.cast(Node.class, x)))))));
			//
		} // if
			//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("小倉北区", "こくらきたく", "菜園場", "さえんば")),
				MultimapUtil.entries(toMultimap("北九州市小倉北区（こくらきたく）菜園場（さえんば）一丁目"))));
		//
		Assertions.assertNull(
				toMultimap(Util.cast(Pattern.class, Narcissus.allocateInstance(Pattern.class)), null, null));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("多", "た", "津", "つ")),
						MultimapUtil.entries(toMultimap(Pattern.compile("\\p{InHiragana}"), "多の津", "たのつ"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("小倉南区", "こくらみなみく")),
						MultimapUtil.entries(toMultimap("北九州市小倉南区（こくらみなみく）長野二丁目"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("福岡前原道路", "ふくおかまえばるどうろ")),
						MultimapUtil.entries(toMultimap("福岡前原道路（ふくおかまえばるどうろ）へ"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("許斐町", "このみまち")),
						MultimapUtil.entries(toMultimap("北九州市小倉北区許斐町（このみまち）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("香住ヶ丘", "かすみがおか")),
						MultimapUtil.entries(toMultimap("福岡市東区香住ヶ丘（かすみがおか）二丁目"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("八幡区西区茶屋", "やはたにしくちゃや", "原", "はる")),
				MultimapUtil.entries(toMultimap("北九州市八幡区西区茶屋の原（やはたにしくちゃやのはる）二丁目 九州自動車道八幡ＩＣへ"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("八重洲線", "やえすせん")),
						MultimapUtil.entries(toMultimap("高速八重洲線（Ｙ）へ分岐 （やえすせん）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("池袋線", "いけぶくろせん")),
						MultimapUtil.entries(toMultimap("高速５号池袋線（５）へ分岐 （いけぶくろせん）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("千代", "ちよ")),
				MultimapUtil.entries(toMultimap("福岡市博多区千代（ちよ）六丁目"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("戸畑区戸畑", "とばたくとばた")),
						MultimapUtil.entries(toMultimap("北九州市戸畑区大字戸畑（とばたくとばた）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("福岡前原道路", "ふくおかまえばるどうろ")),
						MultimapUtil.entries(toMultimap("福岡前原道路（ふくおかまえばるどうろ）へ"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("並木", "なみき", "金沢支線", "かなざわしせん")),
				MultimapUtil.entries(toMultimap("並木（なみき）横浜横須賀道路に接続 金沢支線（かなざわしせん）へ続く"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("許斐町", "このみまち")),
						MultimapUtil.entries(toMultimap("北九州市小倉北区許斐町（このみまち）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("向島線", "むこうじません", "上野線", "うえのせん")),
				MultimapUtil.entries(toMultimap("高速６号向島線（６）へ分岐 （むこうじません） 高速１号上野線（１）へ分岐 （うえのせん）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("川口線", "かわぐちせん")),
						MultimapUtil.entries(toMultimap("高速川口線（s１）に接続 （かわぐちせん）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("台場線", "だいばせん")),
						MultimapUtil.entries(toMultimap("高速１１号台場線（１１）と分岐 （だいばせん）"))));
		//
	}

	@Test
	void testToMultimap03() throws Throwable {
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			return;
			//
		} // if
			//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("横羽線", "よこはねせん")),
						MultimapUtil.entries(toMultimap("高速神奈川１号横羽線（ｋ１）に接続 （よこはねせん）・環状八号線に出入"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("江北橋", "こうほくばし")),
						MultimapUtil.entries(toMultimap("江北橋（こうほくばし）付近で 高速中央環状線（Ｃ２）と接続。将来は 江北橋付近で高速中央環状王子線に接続"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("谷河内", "やごうち", "京葉道路", "けいようどうろ")),
				MultimapUtil.entries(toMultimap("谷河内で京葉道路に接続 （やごうち）（けいようどうろ）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("常磐自動車道", "じょうばんじどうしゃどう")),
						MultimapUtil.entries(toMultimap("常磐自動車道に接続 （じょうばんじどうしゃどう） 東京外環自動車道に接続"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("狩場", "かりば", "横浜横須賀道路", "よこはまよこすかどうろ")),
				MultimapUtil.entries(toMultimap("狩場（かりば）料金所で 横浜横須賀道路に接続 （よこはまよこすかどうろ）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("市川市高谷", "いちかわしこうや")),
						MultimapUtil.entries(toMultimap("市川料金所 市川市高谷（いちかわしこうや）で 東関東自動車道に接続"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("大黒線", "だいこくせん")),
						MultimapUtil.entries(toMultimap("高速神奈川５号大黒線（ｋ５）が分岐 （だいこくせん）"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("第三京浜道路", "だいさんけいひんどうろ", "横浜新道", "よこはましんどう")),
				MultimapUtil.entries(toMultimap("第三京浜道路・横浜新道に接続 （だいさんけいひんどうろ）（よこはましんどう）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("湾岸線", "わんがんせん")),
						MultimapUtil.entries(toMultimap("高速湾岸線（Ｂ）（わんがんせん）から分岐"))));
		//
		Assertions
				.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("枝光", "えだみつ")),
						MultimapUtil.entries(toMultimap("北九州市八幡東区大字枝光（えだみつ）"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("市川市高谷", "いちかわしこうや")),
						MultimapUtil.entries(toMultimap(" 市川市高谷（いちかわしこうや）で"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("広島高速道路", "ひろしまこうそくどうろ")),
						MultimapUtil.entries(toMultimap(" 広島高速道路　ひろしまこうそくどうろ"))));
		//
		Assertions.assertTrue(
				CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("広島呉道路", "ひろしまくれどうろ")),
						MultimapUtil.entries(toMultimap("仁保、広島呉道路（ひろしまくれどうろ）、"))));
		//
		Assertions.assertTrue(CollectionUtils.isEqualCollection(
				MultimapUtil.entries(ImmutableMultimap.of("広島呉道路", "ひろしまくれどうろ", "海田大橋", "かいたおおはし")),
				MultimapUtil.entries(toMultimap("仁保、広島呉道路（ひろしまくれどうろ）、 海田大橋（かいたおおはし）へ"))));
		//
		Assertions
				.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("谷河内", "やごうち")),
						MultimapUtil.entries(toMultimap("谷河内・やごうち）"))));
		//
	}

	private static Multimap<String, String> toMultimap(final String s) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP1.invoke(null, s);
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

	private static Multimap<String, String> toMultimap(final Pattern pattern, final String s1, final String s2)
			throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP3.invoke(null, pattern, s1, s2);
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

	private static Multimap<String, String> toMultimap(final char[] cs, final Matcher matcher, final String text,
			final Number end) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_4.invoke(null, cs, matcher, text, end);
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
	void testName() throws Exception {
		//
		Util.filter(Arrays.stream(
				OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean.class.getDeclaredMethods()),
				m -> m != null && m.getParameterCount() == 1 && !m.isSynthetic()).forEach(m -> {
					//
					if (m == null) {
						//
						return;
						//
					} // if
						//
					if (Modifier.isStatic(m.getModifiers())) {
						//
						Assertions.assertDoesNotThrow(() -> Narcissus.invokeStaticMethod(m, (Object) null));
						//
					} // if
						//
				});
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = OtoYakuNoHeyaYomikataJitenToshiKousokudouroYomikataJitenMultimapFactoryBean.class
				.getDeclaredMethods();
		//
		Method m = null;
		//
		Collection<Object> list = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Object result = null;
		//
		String toString = null;
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
					if (Objects.equals(Boolean.TYPE, clz)) {
						//
						list.add(Boolean.FALSE);
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
			result = Narcissus.invokeStaticMethod(m, toArray(list));
			//
			toString = Objects.toString(m);
			//
			if (Objects.equals(Util.getName(m), "toMultimapByDocument")
					&& Arrays.equals(m.getParameterTypes(), new Class<?>[] { Document.class })) {
				//
				Assertions.assertEquals(ImmutableMultimap.of(), result, toString);
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

	@Test
	void testReplaceMultimapEntries() throws Throwable {
		//
		Multimap<String, String> multimap = ImmutableMultimap.of();
		//
		Assertions.assertSame(multimap, replaceMultimapEntries(multimap, null));
		//
		Assertions.assertEquals(multimap = ImmutableMultimap.of("八幡東区枝光", "えだみつ"),
				replaceMultimapEntries(multimap, Collections.singletonMap(Pair.of("八幡東区枝光", "えだみつ"), null)));
		//
	}

	private static Multimap<String, String> replaceMultimapEntries(final Multimap<String, String> mm,
			final Map<Entry<String, String>, Entry<String, String>> map) throws Throwable {
		try {
			final Object obj = METHOD_REPLACE_MULTI_MAP_ENTRIES.invoke(null, mm, map);
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