package org.springframework.beans.factory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
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

class OtoYakuNoHeyaYomikataJitenRobottoYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		(METHOD_TEST_AND_APPLY = OtoYakuNoHeyaYomikataJitenRobottoYomikataJitenMultimapFactoryBean.class
				.getDeclaredMethod("testAndApply", Predicate.class, Object.class, FailableFunction.class,
						FailableFunction.class))
				.setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenRobottoYomikataJitenMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenRobottoYomikataJitenMultimapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
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
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenRobottoYomikataJitenMultimapFactoryBean.url")) {
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenRobottoYomikataJitenMultimapFactoryBean.url")));
				//
			} // if
				//
			final File file = new File("OtoYakuNoHeyaYomikataJitenRobottoYomikataJitenMultimapFactoryBean.txt");
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
	void testNodeVisitorImpl() throws ReflectiveOperationException {
		//
		final Class<?> clz = Class.forName(
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenRobottoYomikataJitenMultimapFactoryBean$NodeVisitorImpl");
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
		if (nodeVisitor != null) {
			//
			Assertions.assertDoesNotThrow(() -> nodeVisitor.head(null, 0));
			//
			Assertions.assertDoesNotThrow(
					() -> nodeVisitor.head(Util.cast(TextNode.class, Narcissus.allocateInstance(TextNode.class)), 0));
			//
			Assertions.assertDoesNotThrow(
					() -> nodeVisitor.head(new TextNode("　（エイモス）＊芝浦工大　大学院生　高橋良至（たかはしよしゆき）氏　朝日新聞　200１/3/16夕刊"), 0));
			//
			Assertions.assertEquals(Collections.singleton(Pair.of("高橋良至", "たかはしよしゆき")), MultimapUtil
					.entries(Util.cast(Multimap.class, FieldUtils.readDeclaredField(nodeVisitor, "multimap", true))));
			//
		} // if
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

}