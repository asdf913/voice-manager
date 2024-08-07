package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
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

	private static Method METHOD_TEST_AND_APPLY, METHOD_TO_MULTI_MAP = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP = clz.getDeclaredMethod("toMultimap", PatternMap.class, String.class, String.class))
				.setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject = false;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBeanTest.testGetObject");
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
		final Entry<Field, Object> entry = TestUtil.getFieldWithUrlAnnotationAndValue(
				OtoYakuNoHeyaYomikataJitenNengouGengouNoYomikataJitenMultimapFactoryBean.class);
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
			final PatternMap patternMap = new PatternMapImpl();
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
		} // if
			//
	}

	private static IValue0<Multimap<String, String>> toMultimap(final PatternMap patternMap, final String s1,
			final String s2) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP.invoke(null, patternMap, s1, s2);
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