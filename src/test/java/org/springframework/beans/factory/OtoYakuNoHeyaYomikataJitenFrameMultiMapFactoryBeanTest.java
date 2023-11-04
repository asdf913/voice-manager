package org.springframework.beans.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.jsoup.nodes.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.reflect.Reflection;

class OtoYakuNoHeyaYomikataJitenFrameMultiMapFactoryBeanTest {

	private static Method METHOD_CREATE_FRAME, METHOD_TEST_AND_APPLY = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenFrameMultiMapFactoryBean.class;
		//
		(METHOD_CREATE_FRAME = clz.getDeclaredMethod("createFrame", Node.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenFrameMultiMapFactoryBean instance = null;

	private ObjectMapper objectMapper = null;

	@BeforeEach
	public void before() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenFrameMultiMapFactoryBean();
		//
		objectMapper = new ObjectMapper();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(ImmutableMultimap.of("", ""));
			//
		} // if
			//
		Assertions.assertEquals(ImmutableMultimap.of(), getObject(instance));
		//
		final Map<Object, Object> systemProperties = System.getProperties();
		//
		if (!Util.containsKey(systemProperties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url")) {
			//
			return;
			//
		} // if
			//
		if (instance != null) {
			//
			instance.setLinks(ImmutableMultimap.of("", Util.toString(Util.get(systemProperties,
					"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url"))));
			//
		} // if
			//
		final Multimap<String, Frame> fs = getObject(instance);
		//
		Assertions.assertEquals("{\"empty\":false}", ObjectMapperUtil.writeValueAsString(
				objectMapper != null ? objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY) : null, fs));
		//
		new FailableStream<>(Util.stream(fs != null ? fs.values() : null))
				.forEach(x -> ObjectMapperUtil.writeValueAsString(new ObjectMapper(), x));
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testCreateFrame() throws Throwable {
		//
		if (Util.containsKey(System.getProperties(),
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkMapFactoryBean.url")) {
			//
			return;
			//
		} // if
			//
		Assertions.assertEquals("{\"name\":null,\"src\":null}",
				ObjectMapperUtil.writeValueAsString(objectMapper, createFrame(null)));
		//
	}

	private static Frame createFrame(final Node node) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_FRAME.invoke(null, node);
			if (obj == null) {
				return null;
			} else if (obj instanceof Frame) {
				return (Frame) obj;
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

	@Test
	void testIh() throws Throwable {
		//
		final Class<?> clz = Class
				.forName("org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenFrameMultiMapFactoryBean$IH");
		//
		final Constructor<?> constructor = clz != null ? clz.getDeclaredConstructor() : null;
		//
		if (constructor != null) {
			//
			constructor.setAccessible(true);
			//
		} // if
			//
		final InvocationHandler invocationHandler = Util.cast(InvocationHandler.class,
				constructor != null ? constructor.newInstance() : null);
		//
		if (invocationHandler != null) {
			//
			Assertions.assertThrows(Throwable.class, () -> invocationHandler.invoke(null, null, null));
			//
			Assertions.assertThrows(Throwable.class,
					() -> invocationHandler.invoke(Reflection.newProxy(Frame.class, invocationHandler), null, null));
			//
		} // if
			//
	}

}