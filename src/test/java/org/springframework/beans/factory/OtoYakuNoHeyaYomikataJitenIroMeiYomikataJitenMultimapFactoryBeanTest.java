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

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenIroMeiYomikataJitenMultimapFactoryBeanTest.testGetObject");
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
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Assertions.assertNull(
					Narcissus.invokeStaticMethod(m, toArray(Collections.nCopies(m.getParameterCount(), null))),
					Objects.toString(m));
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
	void testToMultimap() throws Throwable {
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			final PatternMap patternMap = new PatternMapImpl();
			//
			Assertions
					.assertTrue(CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("藍", "あい")),
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
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("藍鑞", "あいろう")),
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
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("文色", "あいろ")),
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
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("薄青", "うすあお")),
							MultimapUtil.entries(toMultimap(patternMap, "薄青(うすあお）日本の色名"))));
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("位", "くらい", "色", "いろ")),
							MultimapUtil.entries(toMultimap(patternMap, "＜位の色（くらいのいろ）広辞苑＞"))));
			//
		} // if
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