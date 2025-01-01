package org.springframework.beans.factory;

import java.io.File;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

class OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_GET_UNICODE_BLOCKS, METHOD_TEST_AND_ACCEPT,
			METHOD_TO_MULTI_MAP_ELEMENT, METHOD_TO_MULTI_MAP_STRING2, METHOD_TO_MULTI_MAP_STRING3 = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_ELEMENT = clz.getDeclaredMethod("toMultimap", Element.class, Object.class,
				PatternMap.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_STRING2 = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class,
				String.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_STRING3 = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class, String.class,
				String.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Link
					&& Util.contains(Arrays.asList("getText", "getUrl", "getDescription"), methodName)) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean();
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
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setText(null);
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singleton(null));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final IH ih = new IH();
		//
		final Link link = Reflection.newProxy(Link.class, ih);
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singleton(link));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.nCopies(2, link));
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singleton(link));
			//
		} // if
			//
		FieldUtils.writeDeclaredField(instance, "text", null, true);
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setDescription(null);
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.nCopies(2, link));
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getObject(instance));
		//
		FieldUtils.writeDeclaredField(instance, "description", null, true);
		//
		Assertions.assertNull(getObject(instance));
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean.url")) {
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean.url")));
				//
			} // if
				//
			final File file = Path.of("OtoYakuNoHeyaYomikataJitenMintetsuYomikataJitenMultimapFactoryBean.txt")
					.toFile();
			//
			FileUtils.writeLines(file, MultimapUtil.entries(getObject(instance)));
			//
			System.out.println(file.getAbsolutePath());
			//
		} // if
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
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
		Assertions.assertNull(getUnicodeBlocks(null));
		//
		Assertions.assertEquals(Collections.singletonList(UnicodeBlock.BASIC_LATIN), getUnicodeBlocks(" "));
		//
		Assertions.assertEquals(Collections.singletonList(UnicodeBlock.BASIC_LATIN), getUnicodeBlocks("  "));
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
	void testTestAndAccept() {
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b) -> true, null, null, null));
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT.invoke(null, instance, t, u, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMultimap() throws Throwable {
		//
		Assertions.assertNull(toMultimap((Element) null, null, (Object) null));
		//
		Assertions.assertEquals(ImmutableMultimap.of(), toMultimap((String) null, null, null));
		//
		final BiPredicate<Multimap<?, ?>, Multimap<?, ?>> biPredicate = (a, b) -> CollectionUtils
				.isEqualCollection(MultimapUtil.entries(a), MultimapUtil.entries(b));
		//
		final BiFunction<Multimap<?, ?>, Multimap<?, ?>, String> biFunction = (a, b) -> Util
				.collect(Util.map(Stream.of(a, b), Util::toString), Collectors.joining("!="));
		//
		assertTrue(biPredicate, toMultimap((Object) null, "東西線", "とうざいせん"), ImmutableMultimap.of("東西線", "とうざいせん"),
				biFunction);
		//
		final Object patternMap = new PatternMapImpl();
		//
		assertTrue(biPredicate, toMultimap(patternMap, "一条線", "いちじょうせん＊軌道線"), ImmutableMultimap.of("一条線", "いちじょうせん"),
				biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "千原線 ＊旧千葉急行電鉄", "ちはらせん"), ImmutableMultimap.of("千原線", "ちはらせん"),
				biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "千葉急行電鉄 （ちばきゅうこうでんてつ）", "＊平成10年10月1日京成電鉄に営業移管　千原線に 京成電鉄参照"),
				ImmutableMultimap.of("千葉急行電鉄", "ちばきゅうこうでんてつ"), biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "日比谷線（２号線）", "ひびやせん"), ImmutableMultimap.of("日比谷線", "ひびやせん"),
				biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "東西線（５号線）", "とうざいせん"), ImmutableMultimap.of("東西線", "とうざいせん"),
				biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "目黒線", "めぐろせん＊目蒲線（めかません）が二つに分かれる （目黒～多摩川～武蔵小杉）平成12年8月6日"),
				ImmutableMultimap.of("目黒線", "めぐろせん", "目蒲線", "めかません"), biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "６号線　三田線", "みたせん"), ImmutableMultimap.of("三田線", "みたせん"),
				biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "あぷとライン（井川線）", "あぷとらいん（いかわせん）"),
				ImmutableMultimap.of("井川線", "いかわせん"), biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "国際文化公園都市線 （彩都線）", "こくさいぶんかこうえんとしせん （さいとせん）"),
				ImmutableMultimap.of("国際文化公園都市線", "こくさいぶんかこうえんとしせん", "彩都線", "さいとせん"), biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "（鋼索線）", "（こうさくせん）"), ImmutableMultimap.of("鋼索線", "こうさくせん"),
				biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "札幌市交通局 （さっぽろしこうつうきょく）", "南北線", "なんぼくせん"),
				ImmutableMultimap.of("札幌市交通局", "さっぽろしこうつうきょく", "南北線", "なんぼくせん"), biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "太平洋石炭販売輸送 （たいへいようせきたんはんばいゆそう）", "臨港線", "りんこうせん＊貨物"),
				ImmutableMultimap.of("太平洋石炭販売輸送", "たいへいようせきたんはんばいゆそう", "臨港線", "りんこうせん"), biFunction);
		//
		assertTrue(biPredicate,
				toMultimap(patternMap, "苫小牧港開発 （とまこまいこうかいはつ）", "苫小牧港開発株式会社線",
						"とまこまいこうかいはつかぶしきがいしゃせん ＊貨物　1998年4月1日から休止中"),
				ImmutableMultimap.of("苫小牧港開発", "とまこまいこうかいはつ", "苫小牧港開発株式会社線", "とまこまいこうかいはつかぶしきがいしゃせん"), biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "北海道ちほく高原鉄道 （ほっかいどうちほくこうげんてつどう）", "ふるさと銀河線", "ふるさとぎんがせん"),
				ImmutableMultimap.of("銀河線", "ぎんがせん"), biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "秋田臨海鉄道 （あきたりんかいてつどう）", "秋田臨海鉄道線", "あきたりんかいてつどうせん ＊貨物"),
				ImmutableMultimap.of("秋田臨海鉄道", "あきたりんかいてつどう", "秋田臨海鉄道線", "あきたりんかいてつどうせん"), biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "くりはら田園鉄道 （くりはらでんえんてつどう）", "くりはら田園鉄道線", "くりはらでんえんてつどうせん"),
				ImmutableMultimap.of("田園鉄道線", "でんえんてつどうせん", "田園鉄道", "でんえんてつどう"), biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "ひたちなか海浜鉄道 （ひたちなかかいひんてつどう ）", "湊線", "みなとせん　　旧・茨城交通（いばらきこうつう）"),
				ImmutableMultimap.of("海浜鉄道", "かいひんてつどう", "湊線", "みなとせん", "茨城交通", "いばらきこうつう"), biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "高尾登山電鉄 （たかおとざんでんてつ）", "（鋼索線）", "（こうさくせん）"),
				ImmutableMultimap.of("高尾登山電鉄", "たかおとざんでんてつ", "鋼索線", "こうさくせん"), biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "帝都高速度交通営団 （ていとこうそくどこうつうえいだん）", "銀座線（３号線）", "ぎんざせん"),
				ImmutableMultimap.of("帝都高速度交通営団", "ていとこうそくどこうつうえいだん", "銀座線", "ぎんざせん"), biFunction);
		//
		assertTrue(biPredicate, toMultimap(patternMap, "東京都交通局 （とうきょうとこうつうきょく）", "１号線　浅草線", "あさくさせん"),
				ImmutableMultimap.of("東京都交通局", "とうきょうとこうつうきょく", "浅草線", "あさくさせん"), biFunction);
		//
	}

	private static <T> void assertTrue(final BiPredicate<T, T> biPredicate, final T a, final T b,
			final BiFunction<T, T, String> biFunction) {
		//
		Assertions.assertTrue(Util.test(biPredicate, a, b), Util.apply(biFunction, a, b));
		//
	}

	private static Multimap<String, String> toMultimap(final Element element, final Object firstRowTexts,
			final Object patternMap) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_ELEMENT.invoke(null, element, firstRowTexts, patternMap);
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

	private static Multimap<String, String> toMultimap(final Object patternMap, final String s0, final String s1)
			throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_STRING2.invoke(null, patternMap, s0, s1);
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

	private static Multimap<String, String> toMultimap(final Object patternMap, final String s0, final String s1,
			final String s2) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_STRING3.invoke(null, patternMap, s0, s1, s2);
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
	void testPatternMap() throws Throwable {
		//
		final Method getPattern = PatternMap.class.getDeclaredMethod("getPattern", PatternMap.class, String.class);
		//
		if (getPattern != null) {
			//
			getPattern.setAccessible(true);
			//
		} // if
			//
		Assertions.assertNull(getPattern != null ? getPattern.invoke(null, null, null) : null);
		//
	}

}