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
import org.javatuples.valueintf.IValue0;
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

	private static Method METHOD_GET_UNICODE_BLOCKS, METHOD_TEST_AND_APPLY, METHOD_TO_MULTI_MAP1, METHOD_TO_MULTI_MAP3,
			METHOD_APPEND, METHOD_TO_MULTI_MAP2, METHOD_LENGTH = null;

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
		(METHOD_TO_MULTI_MAP1 = clz.getDeclaredMethod("toMultimap", String.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP3 = clz.getDeclaredMethod("toMultimap", Pattern.class, String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_APPEND = clz.getDeclaredMethod("append", Appendable.class, Character.TYPE)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP2 = clz.getDeclaredMethod("toMultimap2", String.class)).setAccessible(true);
		//
		(METHOD_LENGTH = clz.getDeclaredMethod("length", char[].class)).setAccessible(true);
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
			Assertions.assertNull(toMultimap(null));
			//
			Assertions.assertNull(toMultimap("北"));
			//
			Assertions.assertNull(toMultimap("北こ"));
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
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("福岡前原道路", "ふくおかまえばるどうろ")),
					MultimapUtil.entries(toMultimap("福岡前原道路（ふくおかまえばるどうろ）へ"))));
			//
			Assertions.assertTrue(
					CollectionUtils.isEqualCollection(MultimapUtil.entries(ImmutableMultimap.of("許斐町", "このみまち")),
							MultimapUtil.entries(toMultimap("北九州市小倉北区許斐町（このみまち）"))));
			//
		} // if
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

	@Test
	void testAppend() {
		//
		Assertions.assertDoesNotThrow(() -> append(null, ' '));
		//
	}

	private static void append(final Appendable instance, final char c) throws Throwable {
		try {
			METHOD_APPEND.invoke(null, instance, c);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMultimap2() throws Throwable {
		//
		Assertions.assertNull(toMultimap2(null));
		//
	}

	private static IValue0<Multimap<String, String>> toMultimap2(final String s) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP2.invoke(null, s);
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

	@Test
	void testLength() throws Throwable {
		//
		Assertions.assertEquals(0, length(null));
		//
	}

	private static int length(final char[] instance) throws Throwable {
		try {
			final Object obj = METHOD_LENGTH.invoke(null, instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}