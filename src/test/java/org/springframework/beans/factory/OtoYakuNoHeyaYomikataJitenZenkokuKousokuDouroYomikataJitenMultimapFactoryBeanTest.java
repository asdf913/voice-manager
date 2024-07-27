package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.nodes.TextNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

class OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBeanTest {

	private static final String SPACE = " ";

	private static Method METHOD_GET_UNICODE_BLOCKS, METHOD_TEST_AND_APPLY, METHOD_AND, METHOD_TO_MULTI_MAP = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_AND = clz.getDeclaredMethod("and", Boolean.TYPE, Boolean.TYPE, boolean[].class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP = clz.getDeclaredMethod("toMultimap", Iterable.class, Pattern.class)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean instance = null;

	private boolean isSystemPropertiesContainsTestGetObject = false;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean();
		//
		isSystemPropertiesContainsTestGetObject = Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBeanTest.testGetObject");
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
		if (instance != null) {
			//
			instance.setUrls(Collections.singleton(""));
			//
		} // if
			//
		final Multimap<?, ?> multimap = ImmutableMultimap.of();
		//
		Assertions.assertEquals(multimap, instance != null ? instance.getObject() : null);
		//
		if (instance != null) {
			//
			instance.setUrls(Collections.singleton(SPACE));
			//
		} // if
			//
		Assertions.assertEquals(multimap, instance != null ? instance.getObject() : null);
		//
		if (isSystemPropertiesContainsTestGetObject) {
			//
			if (instance != null) {
				//
				instance.setUrls(Arrays.asList("https://hiramatu-hifuka.com/onyak/onyak2/kosoku01.html",
						"https://hiramatu-hifuka.com/onyak/onyak2/kosoku02.html"));
				//
			} // if
				//
			Assertions.assertDoesNotThrow(() -> instance != null ? instance.getObject() : null);
			//
		} // if
			//
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
	void testGetUnicodeBlocks() throws Throwable {
		//
		Assertions.assertNull(getUnicodeBlocks(null));
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertEquals(Collections.singletonList(UnicodeBlock.BASIC_LATIN), getUnicodeBlocks("12"));
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
	void testAnd() throws Throwable {
		//
		Assertions.assertFalse(and(true, false));
		//
		Assertions.assertTrue(and(true, true, null));
		//
		Assertions.assertFalse(and(true, true, false));
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertFalse(and(false, false));
			//
			Assertions.assertFalse(and(true, true, false));
			//
			Assertions.assertTrue(and(true, true, true));
			//
		} // if
			//
	}

	private static boolean and(final boolean a, final boolean b, final boolean... bs) throws Throwable {
		try {
			final Object obj = METHOD_AND.invoke(null, a, b, bs);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMultimap() throws Throwable {
		//
		Assertions.assertNull(toMultimap(Collections.singleton(null), null));
		//
		final Pattern pattern = Pattern.compile("^（(\\p{InHIRAGANA}+)）$");
		//
		final TextNode textNode = new TextNode("（さっそんじどうしゃどう）");
		//
		Assertions.assertNull(toMultimap(Arrays.asList(new TextNode(""), textNode), pattern));
		//
		if (!isSystemPropertiesContainsTestGetObject) {
			//
			Assertions.assertNull(toMultimap(Collections.singleton(textNode), pattern));
			//
			Assertions.assertTrue(CollectionUtils.isEqualCollection(
					MultimapUtil.entries(ImmutableMultimap.of("札樽自動車道", "さっそんじどうしゃどう")),
					MultimapUtil.entries(toMultimap(Arrays.asList(new TextNode("札樽自動車道"), textNode), pattern))));
			//
		} // if
			//
	}

	private static Multimap<String, String> toMultimap(final Iterable<TextNode> textNodes, final Pattern pattern)
			throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP.invoke(null, textNodes, pattern);
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
	void testSetUrls() throws Exception {
		//
		final Field urls = OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean.class
				.getDeclaredField("urls");
		//
		if (urls != null) {
			//
			urls.setAccessible(true);
			//
		} // if
			//
		setUrls(instance, null);
		//
		Assertions.assertNull(FieldUtils.readField(urls, instance));
		//
		String string = "";
		//
		setUrls(instance, string);
		//
		Assertions.assertEquals(Collections.singleton(string), FieldUtils.readField(urls, instance));
		//
		setUrls(instance, new String[] { string });
		//
		Assertions.assertEquals(Collections.singletonList(string), FieldUtils.readField(urls, instance));
		//
		setUrls(instance, string = SPACE);
		//
		Assertions.assertEquals(Collections.singleton(string), FieldUtils.readField(urls, instance));
		//
		final Number number = Integer.valueOf(1);
		//
		setUrls(instance, number);
		//
		Assertions.assertEquals(Collections.singleton(Util.toString(number)), FieldUtils.readField(urls, instance));
		//
		final Boolean b = Boolean.TRUE;
		//
		setUrls(instance, b);
		//
		Assertions.assertEquals(Collections.singleton(Util.toString(b)), FieldUtils.readField(urls, instance));
		//
		setUrls(instance, new ObjectMapper().writeValueAsString(Collections.emptySet()));
		//
		Assertions.assertEquals(Collections.emptyList(), FieldUtils.readField(urls, instance));
		//
		final ObjectMapper objectMapper = new ObjectMapper();
		//
		setUrls(instance, objectMapper.writeValueAsString(SPACE));
		//
		Assertions.assertEquals(Collections.singletonList(SPACE), FieldUtils.readField(urls, instance));
		//
		setUrls(instance, objectMapper.writeValueAsString(number));
		//
		Assertions.assertEquals(Collections.singletonList(Util.toString(number)), FieldUtils.readField(urls, instance));
		//
		setUrls(instance, objectMapper.writeValueAsString(b));
		//
		Assertions.assertEquals(Collections.singleton(Util.toString(b)), FieldUtils.readField(urls, instance));
		//
		setUrls(instance, objectMapper.writeValueAsString(Collections.emptyMap()));
		//
		Assertions.assertThrows(IllegalStateException.class,
				() -> setUrls(instance, objectMapper.writeValueAsString(Collections.emptyMap())));
		//
	}

	private void setUrls(final OtoYakuNoHeyaYomikataJitenZenkokuKousokuDouroYomikataJitenMultimapFactoryBean instance,
			final Object input) throws JsonProcessingException {
		if (instance != null) {
			instance.setUrls(input);
		}
	}

}