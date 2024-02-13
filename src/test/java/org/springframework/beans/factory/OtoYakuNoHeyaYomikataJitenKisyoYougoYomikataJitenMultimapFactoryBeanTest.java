package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.javatuples.valueintf.IValue0;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_GET_STRINGS, METHOD_TEST_AND_APPLY, METHOD_CLEAR, METHOD_GET_UNICODE_BLOCKS,
			METHOD_TEST_AND_ACCEPT, METHOD_MATCHES, METHOD_OR, METHOD_CREATE_MULTI_MAP_ITERABLE,
			METHOD_CREATE_MULTI_MAP1, METHOD_CREATE_MULTI_MAP2, METHOD_CREATE_MULTI_MAP_STRING_CHAR_ARRAY_ITERABLE,
			METHOD_CREATE_MULTI_MAP3, METHOD_CREATE_MULTI_MAP4, METHOD_AND = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException, ClassNotFoundException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_GET_STRINGS = clz.getDeclaredMethod("getStrings", String.class, UnicodeBlock.class,
				UnicodeBlock[].class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_CLEAR = clz.getDeclaredMethod("clear", StringBuilder.class)).setAccessible(true);
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_MATCHES = clz.getDeclaredMethod("matches", String.class, String.class)).setAccessible(true);
		//
		(METHOD_OR = clz.getDeclaredMethod("or", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP_ITERABLE = clz.getDeclaredMethod("createMultimap", Iterable.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP1 = clz.getDeclaredMethod("createMultimap1", String.class, Iterable.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP2 = clz.getDeclaredMethod("createMultimap2", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP3 = clz.getDeclaredMethod("createMultimap3", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP4 = clz.getDeclaredMethod("createMultimap4", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP_STRING_CHAR_ARRAY_ITERABLE = clz.getDeclaredMethod("createMultimap", String.class,
				char[].class, Iterable.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Predicate.class, Object.class, Object.class)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean();
		//
	}

	@Test
	void testGetObjecct() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl("");
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setUrl(" ");
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean.url")));
				//
			} // if
				//
			final Collection<Entry<String, String>> entries = MultimapUtil.entries(getObject(instance));
			//
			if (entries != null) {
				//
				entries.forEach(System.out::println);
				//
			} // if
				//
		} // if
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetObjecctType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetStrings() throws Throwable {
		//
		Assertions.assertNull(getStrings(null, null));
		//
		final String string = "あ";
		//
		Assertions.assertEquals(Arrays.asList(string),
				getStrings(StringUtils.appendIfMissing(string, "(", ")"), UnicodeBlock.HIRAGANA));
		//
		Assertions.assertEquals(Arrays.asList("", "", "雲"), getStrings("イオン雲", UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS));
		//
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

	private static List<String> getStrings(final String string, final UnicodeBlock ub, final UnicodeBlock... ubs)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_STRINGS.invoke(null, string, ub, ubs);
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
	void testClear() {
		//
		Assertions.assertDoesNotThrow(() -> clear(null));
		//
	}

	private static void clear(final StringBuilder instance) throws Throwable {
		try {
			METHOD_CLEAR.invoke(null, instance);
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
	void testMatches() throws Throwable {
		//
		Assertions.assertFalse(matches(null, null));
		//
		Assertions.assertFalse(matches("", null));
		//
		Assertions.assertTrue(matches("", ""));
		//
		final String string = new String("a");
		//
		Assertions.assertFalse(matches(string, ""));
		//
		FieldUtils.getAllFieldsList(Util.getClass(string)).forEach(f -> {
			//
			if (f == null || Modifier.isStatic(f.getModifiers()) || ClassUtils.isPrimitiveOrWrapper(f.getType())) {
				//
				return;
				//
			} // if
				//
			Narcissus.setObjectField(string, f, null);
			//
		});
		//
		Assertions.assertFalse(matches(string, ""));
		//
		Assertions.assertFalse(matches(string, string));
		//
	}

	private static boolean matches(final String instance, final String regex) throws Throwable {
		try {
			final Object obj = METHOD_MATCHES.invoke(null, instance, regex);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOr() throws Throwable {
		//
		Assertions.assertFalse(or(false, false));
		//
		Assertions.assertTrue(or(false, true));
		//
		Assertions.assertTrue(or(true, false));
		//
		Assertions.assertTrue(or(false, false, true));
		//
		Assertions.assertFalse(or(false, false, null));
		//
		Assertions.assertFalse(or(false, false, false));
		//
	}

	private static boolean or(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = METHOD_OR.invoke(null, a, b, bs);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMultimap() throws Throwable {
		//
		Assertions.assertNull(createMultimap(null));
		//
		Assertions.assertEquals("[{雨脚=[あまあし], 雨足=[あまあし]}]",
				Util.toString(createMultimap("雨脚・雨足", new char[] { '・' }, Collections.singleton("あまあし"))));
		//
		Assertions.assertEquals("[{雨脚=[null], 雨足=[null]}]",
				Util.toString(createMultimap("雨脚・雨足", new char[] { '・' }, null)));
		//
	}

	private static Multimap<String, String> createMultimap(final Iterable<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP_ITERABLE.invoke(null, es);
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

	private static IValue0<Multimap<String, String>> createMultimap(final String s, final char[] cs,
			final Iterable<String> iterable) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP_STRING_CHAR_ARRAY_ITERABLE.invoke(null, s, cs, iterable);
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
	void testCreateMultimap1() throws Throwable {
		//
		Assertions.assertEquals("[{光冠=[こうかん], 光環=[こうかん]}]",
				Util.toString(createMultimap1("光冠（光環）", Collections.singleton("こうかん"))));
		//
		Assertions.assertEquals("[{光冠=[null], 光環=[null]}]", Util.toString(createMultimap1("光冠（光環）", null)));
		//
	}

	private static IValue0<Multimap<String, String>> createMultimap1(final String s, final Iterable<String> iterable)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP1.invoke(null, s, iterable);
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
	void testCreateMultimap2() throws Throwable {
		//
		Assertions.assertEquals("[{渦=[うず]}]", Util.toString(createMultimap2("カルマンの渦", "（カルマンのうず）")));
		//
		Assertions.assertNull(createMultimap2("カルマンの渦", null));
		//
	}

	private static IValue0<Multimap<String, String>> createMultimap2(final String s1, final String s2)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP2.invoke(null, s1, s2);
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
	void testCreateMultimap3() throws Throwable {
		//
		Assertions.assertEquals("[{青空比色目盛り=[あおぞらひしょくめもり]}]",
				Util.toString(createMultimap3("リンケの青空比色目盛り", "（リンケのあおぞらひしょくめもり）")));
		//
	}

	private static IValue0<Multimap<String, String>> createMultimap3(final String s1, final String s2)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP3.invoke(null, s1, s2);
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
	void testCreateMultimap4() throws Throwable {
		//
		Assertions.assertEquals("[{荒天=[こうてん], 度=[ど]}]", Util.toString(createMultimap4("荒天の40度", "（こうてんの40ど）")));
		//
	}

	private static IValue0<Multimap<String, String>> createMultimap4(final String s1, final String s2)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP4.invoke(null, s1, s2);
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
	void testAnd() throws Throwable {
		//
		Assertions.assertFalse(and(null, null, null));
		//
	}

	private static <T> boolean and(final Predicate<T> predicate, final T a, final T b) throws Throwable {
		try {
			final Object obj = METHOD_AND.invoke(null, predicate, a, b);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}