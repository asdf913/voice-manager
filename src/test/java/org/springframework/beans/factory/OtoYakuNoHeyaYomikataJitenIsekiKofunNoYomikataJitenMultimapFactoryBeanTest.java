package org.springframework.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.jsoup.nodes.Element;
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

class OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_TO_MULTI_MAP = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP = clz.getDeclaredMethod("toMultimap", PatternMap.class, Iterable.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Iterable && Objects.equals(methodName, "iterator")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject = false;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBeanTest.testGetObject");
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
				OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBean.class);
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
		final Method[] ms = OtoYakuNoHeyaYomikataJitenIsekiKofunNoYomikataJitenMultimapFactoryBean.class
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
					if (Objects.equals(Integer.TYPE, clz)) {
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
			} // if
				//
			result = Narcissus.invokeStaticMethod(m, toArray(list));
			//
			toString = Objects.toString(m);
			//
			if (Objects.equals(Util.getName(m), "join")) {
				//
				Assertions.assertEquals("null", result, toString);
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
		Assertions.assertNull(toMultimap(null, Reflection.newProxy(Iterable.class, new IH())));
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertNull(toMultimap(null, Collections.singleton(null)));
			//
			Element e = new Element("a");
			//
			for (int i = 0; i < 3; i++) {
				//
				e.appendChild(new Element("a"));
				//
			} // for
				//
			Assertions.assertNull(toMultimap(null, Collections.singleton(e)));
			//
			append(append(append(e = new Element("a"), "<b>青田遺跡</b>"), "<b>あおたいせき</b>"), "<b/>");
			//
			final PatternMap patternMap = new PatternMapImpl();
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("青田遺跡", "あおたいせき")),
							MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>「禾津頓宮」跡</b>"), "<b>あわづとんぐうあと</b>"), "<b/>");
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("禾津頓宮跡", "あわづとんぐうあと")),
							MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>入口遺跡</b>"), "<b>いりぐちいせき）</b>"), "<b/>");
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("入口遺跡", "いりぐちいせき")),
							MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>島の山古墳</b>"), "<b>しまのやまこふん</b>"), "<b/>");
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("島", "しま", "山古墳", "やまこふん")),
					MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>お亀石古墳</b>"), "<b>おかめいしこふん</b>"), "<b/>");
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("亀石古墳", "かめいしこふん")),
							MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>在自西ノ後遺跡</b>"), "<b>あらじにしのうしろいせき</b>"), "<b/>");
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("在自西", "あらじにし", "後遺跡", "うしろいせき")),
					MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>オガンジ池瓦窯跡</b>"), "<b>おがんじいけかわらがまあと</b>"), "<b/>");
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("池瓦窯跡", "いけかわらがまあと")),
							MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>保渡田八幡塚古墳</b>"), "<b>ほどたはちまんづかこふん ほとだはちまんづかこふん</b>"), "<b/>");
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("保渡田八幡塚古墳", "ほどたはちまんづかこふん", "保渡田八幡塚古墳", "ほとだはちまんづかこふん")),
					MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>金山城跡</b>"),
					"<b>かなやまじょうあと （文化庁文化財政部伝統文化課） かなやまじょうせき （郷土文化財コレクション）</b>"), "<b/>");
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("金山城跡", "かなやまじょうあと", "金山城跡", "かなやまじょうせき")),
					MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>鷹島沖海底遺跡 （元寇遺跡）</b>"), "<b>たかしまおきかいていいせき （げんこういせき）</b>"),
					"<b/>");
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("鷹島沖海底遺跡", "たかしまおきかいていいせき", "元寇遺跡", "げんこういせき")),
					MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>黒塚古墳</b>"), "<b>くろづかこふん・くろつかこふん</b>"), "<b/>");
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("黒塚古墳", "くろづかこふん", "黒塚古墳", "くろつかこふん")),
					MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>条（條）ウル神古墳</b>"), "<b>じょううるがみこふん</b>"), "<b/>");
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("条", "じょ", "條", "じょ", "神古墳", "がみこふん")),
					MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>唐古鍵遺跡</b>"), "<b>からこ・かぎいせき</b>"), "<b/>");
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("唐古鍵遺跡", "からこかぎいせき")),
							MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>祢布ヶ森遺跡</b>"), "<b>にょうがもりいせき</b>"), "<b/>");
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("祢布", "にょう", "森遺跡", "もりいせき")),
					MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
			append(append(append(e = new Element("a"), "<b>紅葉山49号遺跡</b>"), "<b>もみじやま49ごういせき</b>"), "<b/>");
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("紅葉山", "もみじやま", "号遺跡", "ごういせき")),
					MultimapUtil.entries(toMultimap(patternMap, Collections.singleton(e)))));
			//
		} // if
			//
	}

	private static Element append(final Element instance, final String html) {
		return instance != null ? instance.append(html) : instance;
	}

	private static Multimap<String, String> toMultimap(final PatternMap patternMap, final Iterable<Element> trs)
			throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP.invoke(null, patternMap, trs);
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