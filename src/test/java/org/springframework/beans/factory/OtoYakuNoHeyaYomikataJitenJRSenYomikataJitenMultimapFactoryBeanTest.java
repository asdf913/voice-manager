package org.springframework.beans.factory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenJRSenYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		(METHOD_TEST_AND_APPLY = OtoYakuNoHeyaYomikataJitenJRSenYomikataJitenMultimapFactoryBean.class
				.getDeclaredMethod("testAndApply", Predicate.class, Object.class, FailableFunction.class,
						FailableFunction.class))
				.setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenJRSenYomikataJitenMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenJRSenYomikataJitenMultimapFactoryBean();
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
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.url")));
				//
			} // if
				//
			final File file = new File("OtoYakuNoHeyaYomikataJitenJRSenYomikataJitenMultimapFactoryBean.txt");
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
	void testNodeVisitorImpl() throws ReflectiveOperationException {
		//
		final Class<?> clz = Class.forName(
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenJRSenYomikataJitenMultimapFactoryBean$NodeVisitorImpl");
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final NodeVisitor nodeVisitor = Util.cast(NodeVisitor.class,
				constructor != null ? constructor.newInstance() : null);
		//
		Assertions.assertDoesNotThrow(() -> head(nodeVisitor, null, 0));
		//
		Assertions.assertDoesNotThrow(
				() -> head(nodeVisitor, Util.cast(TextNode.class, Narcissus.allocateInstance(TextNode.class)), 0));
		//
		Assertions.assertDoesNotThrow(() -> head(nodeVisitor, new TextNode("江差線（えさしせん）"), 0));
		//
		final Field multimap = clz != null ? clz.getDeclaredField("multimap") : null;
		//
		if (multimap != null) {
			//
			multimap.setAccessible(true);
			//
		} // if
			//
		final Object object = get(multimap, nodeVisitor);
		//
		Assertions.assertEquals(Collections.singleton(Pair.of("江差線", "えさしせん")),
				MultimapUtil.entries(Util.cast(Multimap.class, object)));
		//
		Assertions.assertDoesNotThrow(() -> head(nodeVisitor, new TextNode("江差線（えさしせん）"), 0));
		//
		Assertions.assertSame(object, get(multimap, nodeVisitor));
		//
	}

	private static Object get(final Field field, final Object instance) throws IllegalAccessException {
		return field != null ? field.get(instance) : null;
	}

	private static void head(final NodeVisitor instance, final Node node, final int depth) {
		if (instance != null) {
			instance.head(node, depth);
		}
	}

}