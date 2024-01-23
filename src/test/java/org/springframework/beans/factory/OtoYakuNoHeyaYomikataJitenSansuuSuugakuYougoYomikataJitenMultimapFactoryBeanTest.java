package org.springframework.beans.factory;

import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_GET_STRINGS, METHOD_CLEAR, METHOD_TO_URL, METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeClass() throws NoSuchMethodException, ClassNotFoundException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_GET_STRINGS = clz.getDeclaredMethod("getStrings", String.class, UnicodeBlock.class))
				.setAccessible(true);
		//
		(METHOD_CLEAR = clz.getDeclaredMethod("clear", StringBuilder.class)).setAccessible(true);
		//
		(METHOD_TO_URL = clz.getDeclaredMethod("toURL", URI.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(instance != null ? instance.getObject() : null);
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenSansuuSuugakuYougoYomikataJitenMultimapFactoryBean.url")));
				//
			} // if
				//
			final Object map = FieldUtils.readField(instance != null ? instance.getObject() : null, "map", true);
			//
			Util.filter(
					Arrays.stream(Util.cast(Object[].class,
							Narcissus.getField(map, Narcissus.findField(Util.getClass(map), "table")))),
					Objects::nonNull).forEach(x -> {
						//
						final Entry<?, ?> entry = Util.cast(Entry.class, x);
						//
						System.out.println(Util.getKey(entry) + " " + Util.getValue(entry));
						//
					});
			//
		} // if
			//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testGetStrings() throws Throwable {
		//
		Assertions.assertNull(getStrings(null, null));
		//
		Assertions.assertNull(getStrings(" ", null));
		//
		final String string = "あ";
		//
		Assertions.assertEquals(Arrays.asList(string), getStrings(string, UnicodeBlock.HIRAGANA));
		//
		Assertions.assertEquals(Arrays.asList(string),
				getStrings(StringUtils.appendIfMissing(string, "(", ")"), UnicodeBlock.HIRAGANA));
		//
		Assertions.assertEquals(Arrays.asList(string),
				getStrings(StringUtils.appendIfMissing(string, "("), UnicodeBlock.HIRAGANA));
		//
	}

	private static List<String> getStrings(final String string, final UnicodeBlock unicodeBlock) throws Throwable {
		try {
			final Object obj = METHOD_GET_STRINGS.invoke(null, string, unicodeBlock);
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
	void testToURL() throws Throwable {
		//
		Assertions.assertNull(toURL(null));
		//
		Assertions.assertNull(toURL(new URI("")));
		//
		final URI uri = new URI("http://www.z.cn");
		//
		Assertions.assertEquals(uri.toURL(), toURL(uri));
		//
	}

	private static URL toURL(final URI instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_URL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URL) {
				return (URL) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
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

}