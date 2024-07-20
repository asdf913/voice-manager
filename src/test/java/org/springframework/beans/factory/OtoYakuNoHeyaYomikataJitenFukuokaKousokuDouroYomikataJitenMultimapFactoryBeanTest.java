package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenFukuokaKousokuDouroYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_GET_UNICODE_BLOCKS, METHOD_TEST_AND_APPLY, METHOD_TO_MULTI_MAP = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenFukuokaKousokuDouroYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP = clz.getDeclaredMethod("toMultimap", Pattern.class, String.class, String.class))
				.setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenFukuokaKousokuDouroYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject = false;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenFukuokaKousokuDouroYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenFukuokaKousokuDouroYomikataJitenMultimapFactoryBeanTest.testGetObject");
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
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		final Entry<Field, Object> entry = getFieldWithUrlAnnotationAndValue();
		//
		final Field url = entry != null ? entry.getKey() : null;
		//
		Narcissus.setObjectField(instance, url, "");
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		Narcissus.setObjectField(instance, url, " ");
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
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

	private static Entry<Field, Object> getFieldWithUrlAnnotationAndValue() throws ClassNotFoundException {
		//
		final Class<?> clz = Class.forName("org.springframework.beans.factory.URL");
		//
		final List<Field> fs = FieldUtils
				.getAllFieldsList(OtoYakuNoHeyaYomikataJitenFukuokaKousokuDouroYomikataJitenMultimapFactoryBean.class);
		//
		Field f = null;
		//
		Annotation[] as = null;
		//
		InvocationHandler ih = null;
		//
		List<Field> fs2 = null;
		//
		int size = 0;
		//
		List<Method> ms = null;
		//
		Entry<Field, Object> entry = null;
		//
		for (int i = 0; fs != null && i < fs.size(); i++) {
			//
			if ((f = fs.get(i)) == null || (as = f != null ? f.getDeclaredAnnotations() : null) == null) {
				//
				continue;
				//
			} // if
				//
			for (final Annotation a : as) {
				//
				if (a == null) {
					//
					continue;
					//
				} // if
					//
				if ((ih = Proxy.isProxyClass(Util.getClass(a))
						? Util.cast(InvocationHandler.class, Proxy.getInvocationHandler(a))
						: null) != null) {
					//
					if ((size = IterableUtils.size(
							fs2 = Util.toList(Util.filter(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(ih))),
									x -> Objects.equals(Util.getType(x), Class.class))))) == 1) {
						//
						if (Objects.equals(clz, Narcissus.getField(ih, IterableUtils.get(fs2, 0)))) {
							//
							if ((size = IterableUtils.size(
									ms = Util.toList(Util.filter(Arrays.stream(Util.getClass(a).getDeclaredMethods()),
											x -> Objects.equals(String.class, x != null ? x.getReturnType() : null)
													&& !ReflectionUtils.isToStringMethod(x))))) == 1) {
								//
								if (entry == null) {
									//
									entry = Pair.of(f, Narcissus.invokeObjectMethod(a, IterableUtils.get(ms, 0)));
									//
								} else {
									//
									throw new IllegalStateException();
									//
								} // if
									//
							} else if (size > 1) {
								//
								throw new IllegalStateException();
								//
							} // if
								//
								//
						} // if
							//
					} else if (size > 1) {
						//
						throw new IllegalStateException();
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // for
			//
		return entry;
		//
	}

	@Test
	void testGetUnicodeBlocks() throws Throwable {
		//
		Assertions.assertNull(getUnicodeBlocks(null));
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
	void testToMultimap() throws Throwable {
		//
		Assertions.assertNull(toMultimap(null, null, null));
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertNull(
					toMultimap(Util.cast(Pattern.class, Narcissus.allocateInstance(Pattern.class)), null, null));
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("多", "た", "津", "つ")),
							MultimapUtil.entries(toMultimap(Pattern.compile("\\p{InHiragana}"), "多の津", "たのつ"))));
			//
		} // if
			//
	}

	private static Multimap<String, String> toMultimap(final Pattern pattern, final String s1, final String s2)
			throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP.invoke(null, pattern, s1, s2);
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