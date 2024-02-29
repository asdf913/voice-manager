package org.springframework.beans.factory;

import java.io.File;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.javatuples.Unit;
import org.javatuples.valueintf.IValue0;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.TriConsumer;
import org.meeuw.functional.TriPredicate;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

class OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_GET_STRINGS, METHOD_TEST_AND_APPLY4, METHOD_TEST_AND_APPLY5, METHOD_CLEAR,
			METHOD_GET_UNICODE_BLOCKS, METHOD_TEST_AND_ACCEPT4, METHOD_TEST_AND_ACCEPT5,
			METHOD_CREATE_MULTI_MAP_ITERABLE, METHOD_CREATE_MULTI_MAP1, METHOD_CREATE_MULTI_MAP2,
			METHOD_CREATE_MULTI_MAP_STRING_CHAR_ARRAY_ITERABLE, METHOD_CREATE_MULTI_MAP4, METHOD_CREATE_MULTI_MAP5,
			METHOD_CREATE_MULTI_MAP6, METHOD_CREATE_MULTI_MAP7, METHOD_CREATE_MULTI_MAP8, METHOD_AND = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException, ClassNotFoundException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_GET_STRINGS = clz.getDeclaredMethod("getStrings", String.class, UnicodeBlock.class,
				UnicodeBlock[].class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY4 = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY5 = clz.getDeclaredMethod("testAndApply", BiPredicate.class, Object.class, Object.class,
				BiFunction.class, BiFunction.class)).setAccessible(true);
		//
		(METHOD_CLEAR = clz.getDeclaredMethod("clear", StringBuilder.class)).setAccessible(true);
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT4 = clz.getDeclaredMethod("testAndAccept", BiPredicate.class, Object.class, Object.class,
				BiConsumer.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_ACCEPT5 = clz.getDeclaredMethod("testAndAccept", TriPredicate.class, Object.class,
				Object.class, Object.class, TriConsumer.class)).setAccessible(true);
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
		(METHOD_CREATE_MULTI_MAP4 = clz.getDeclaredMethod("createMultimap4", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP5 = clz.getDeclaredMethod("createMultimap5", String.class, Collection.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP6 = clz.getDeclaredMethod("createMultimap6", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP7 = clz.getDeclaredMethod("createMultimap7", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP8 = clz.getDeclaredMethod("createMultimap8", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP_STRING_CHAR_ARRAY_ITERABLE = clz.getDeclaredMethod("createMultimap", String.class,
				char[].class, Iterable.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Predicate.class, Object.class, Object.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private String text, url, description = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Link) {
				//
				if (Objects.equals(methodName, "getText")) {
					//
					return text;
					//
				} else if (Objects.equals(methodName, "getUrl")) {
					//
					return url;
					//
				} else if (Objects.equals(methodName, "getDescription")) {
					//
					return description;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
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
		final Link link = Reflection.newProxy(Link.class, new IH());
		//
		if (instance != null) {
			//
			instance.setLinks(Arrays.asList(link));
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
		if (instance != null) {
			//
			instance.setLinks(Arrays.asList(link));
			//
			instance.setText(null);
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
			final File file = new File("OtoYakuNoHeyaYomikataJitenKisyoYougoYomikataJitenMultimapFactoryBean.txt");
			//
			new FailableStream<>(Stream.of("text", "description")).forEach(x -> {
				//
				FieldUtils.writeDeclaredField(instance, x, null, true);
				//
			});
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
	void testGetObjecctType() {
		//
		Assertions.assertEquals(Multimap.class, FactoryBeanUtil.getObjectType(instance));
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
		Assertions.assertNull(testAndApply((a, b) -> false, null, null, null, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY4.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U, R> R testAndApply(final BiPredicate<T, U> predicate, final T t, final U u,
			final BiFunction<T, U, R> functionTrue, final BiFunction<T, U, R> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY5.invoke(null, predicate, t, u, functionTrue, functionFalse);
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
		Assertions.assertDoesNotThrow(() -> testAndAccept(null, null, null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> testAndAccept((a, b, c) -> true, null, null, null, null));
		//
	}

	private static <T, U> void testAndAccept(final BiPredicate<T, U> instance, final T t, final U u,
			final BiConsumer<T, U> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT4.invoke(null, instance, t, u, consumer);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, U, V> void testAndAccept(final TriPredicate<T, U, V> predicate, final T t, final U u, final V v,
			final TriConsumer<T, U, V> consumer) throws Throwable {
		try {
			METHOD_TEST_AND_ACCEPT5.invoke(null, predicate, t, u, v, consumer);
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
		Assertions.assertEquals(Unit.with(ImmutableMultimap.of()), createMultimap2("カルマンの渦", null));
		//
		Assertions.assertEquals("[{青空比色目盛り=[あおぞらひしょくめもり]}]",
				Util.toString(createMultimap2("リンケの青空比色目盛り", "（リンケのあおぞらひしょくめもり）")));
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
	void testCreateMultimap5() throws Throwable {
		//
		Assertions.assertEquals("[{青い太陽=[あおいたいよう]}]",
				Util.toString(createMultimap5("青い太陽", Collections.singleton("あおいたいよう"))));
		//
		Assertions.assertNull(createMultimap5("青い太陽", null));
		//
		Assertions.assertEquals("[{報告書=[ほうこくしょ]}]", Util.toString(
				createMultimap5("IPCC報告書", Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "ほうこくしょ"))));
		//
		Assertions.assertEquals("[{報告書=[null]}]", Util.toString(createMultimap5("IPCC報告書", null)));
		//
		Assertions.assertEquals("[{１ヶ月予報=[いっかげつよほう]}]",
				Util.toString(createMultimap5("１ヶ月予報", Collections.singleton("いっかげつよほう"))));
		//
		Assertions.assertEquals("[{１ヶ月予報=[null]}]", Util.toString(createMultimap5("１ヶ月予報", null)));
		//
	}

	private static IValue0<Multimap<String, String>> createMultimap5(final String s1, final Collection<String> ss2)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP5.invoke(null, s1, ss2);
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
	void testCreateMultimap6() throws Throwable {
		//
		Assertions.assertNull(createMultimap6("較差", null));
		//
		Assertions.assertEquals("[{較差=[こうさ, かくさ]}]",
				Util.toString(createMultimap6("較差", "日本国語大辞典（こうさ）の慣用読みとある 学術用語集は（かくさ）")));
		//
		Assertions.assertEquals("[{吹越=[ふっこし], 風花=[かざばな]}]",
				Util.toString(createMultimap6("風花", "（かざばな）　群馬県では吹越（ふっこし）")));
		//
	}

	private static IValue0<Multimap<String, String>> createMultimap6(final String s1, final String s3)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP6.invoke(null, s1, s3);
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
	void testCreateMultimap7() throws Throwable {
		//
		Assertions.assertNull(createMultimap7("雨脚・雨足", null));
		//
		Assertions.assertEquals("[{雨脚=[うきゃく, あめあし], 雨足=[あめあし]}]",
				Util.toString(createMultimap7("雨脚・雨足", "（あめあし）とも　雨脚（うきゃくとも）")));
		//
		Assertions.assertEquals("[{吹き溜り雪=[ふきだまりゆき]}]", Util.toString(createMultimap7("吹き溜り", "吹き溜り雪（ふきだまりゆき）")));
		//
	}

	private static IValue0<Multimap<String, String>> createMultimap7(final String s1, final String s3)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP7.invoke(null, s1, s3);
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
	void testCreateMultimap8() throws Throwable {
		//
		Assertions.assertNull(createMultimap8("寒気湖", "冷気湖（れいきこ）とも"));
		//
		Assertions.assertEquals("[{雨水=[あまみず]}]", Util.toString(createMultimap8("雨水", "1）二十四節気の一つ　 2）降水のひとつで（あまみず）とも")));
		//
	}

	private static IValue0<Multimap<String, String>> createMultimap8(final String s1, final String s3)
			throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP8.invoke(null, s1, s3);
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