package org.springframework.beans.factory;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.jsoup.nodes.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meeuw.functional.Predicates;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapperUtil;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
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

	private static class IH implements InvocationHandler {

		private Boolean exists, isFile, isReadable;

		private byte[] contentAsByteArray = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Resource) {
				//
				if (Objects.equal(methodName, "exists")) {
					//
					return exists;
					//
				} else if (Objects.equal(methodName, "isFile")) {
					//
					return isFile;
					//
				} else if (Objects.equal(methodName, "isReadable")) {
					//
					return isReadable;
					//
				} else if (Objects.equal(methodName, "getContentAsByteArray")) {
					//
					return contentAsByteArray;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

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
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(ImmutableMultimap.of("", ""));
			//
		} // if
			//
		Assertions.assertEquals(ImmutableMultimap.of(), FactoryBeanUtil.getObject(instance));
		//
		final IH ih = new IH();
		//
		final Resource resource = Reflection.newProxy(Resource.class, ih);
		//
		if (instance != null) {
			//
			instance.setResource(resource);
			//
		} // if
			//
		ih.exists = Boolean.FALSE;
		//
		Assertions.assertEquals(ImmutableMultimap.of(), FactoryBeanUtil.getObject(instance));
		//
		ih.exists = Boolean.TRUE;
		//
		ih.isFile = Boolean.FALSE;
		//
		Assertions.assertEquals(ImmutableMultimap.of(), FactoryBeanUtil.getObject(instance));
		//
		ih.isFile = Boolean.TRUE;
		//
		ih.isReadable = Boolean.FALSE;
		//
		Assertions.assertEquals(ImmutableMultimap.of(), FactoryBeanUtil.getObject(instance));
		//
		ih.isReadable = Boolean.TRUE;
		//
		Assertions.assertNull(FactoryBeanUtil.getObject(instance));
		//
		try (final Workbook wb = WorkbookFactory.create(true);
				final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			wb.write(baos);
			//
			ih.contentAsByteArray = baos.toByteArray();
			//
			Assertions.assertNull(FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
		try (final Workbook wb = WorkbookFactory.create(true);
				final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			WorkbookUtil.createSheet(wb);
			//
			wb.write(baos);
			//
			ih.contentAsByteArray = baos.toByteArray();
			//
			Assertions.assertNull(FactoryBeanUtil.getObject(instance));
			//
		} // try
			//
		try (final Workbook wb = WorkbookFactory.create(true);
				final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			//
			final Sheet sheet = WorkbookUtil.createSheet(wb);
			//
			IntStream.range(0, 2).forEach(i -> SheetUtil.createRow(sheet, i));
			//
			wb.write(baos);
			//
			ih.contentAsByteArray = baos.toByteArray();
			//
			final Iterable<Entry<String, Frame>> entries = MultimapUtil.entries(FactoryBeanUtil.getObject(instance));
			//
			Assertions.assertEquals(1, IterableUtils.size(entries));
			//
			final Entry<String, Frame> entry = IterableUtils.get(entries, 0);
			//
			Assertions.assertNull(Util.getKey(entry));
			//
			final Frame frame = Util.getValue(entry);
			//
			Assertions.assertNull(frame != null ? frame.getName() : null);
			//
			Assertions.assertNull(frame != null ? frame.getSrc() : null);
			//
		} // try
			//
		if (instance != null) {
			//
			instance.setResource(null);
			//
		} // if
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
		final Multimap<String, Frame> fs = FactoryBeanUtil.getObject(instance);
		//
		Assertions.assertEquals("{\"empty\":false}", ObjectMapperUtil.writeValueAsString(
				objectMapper != null ? objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY) : null, fs));
		//
		new FailableStream<>(Util.stream(fs != null ? fs.values() : null))
				.forEach(x -> ObjectMapperUtil.writeValueAsString(new ObjectMapper(), x));
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, FactoryBeanUtil.getObjectType(instance));
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
		final String json = ObjectMapperUtil.writeValueAsString(objectMapper, createFrame(null));
		//
		Assertions.assertTrue(
				Util.contains(Arrays.asList("{\"name\":null,\"src\":null}", "{\"src\":null,\"name\":null}"), json),
				json);
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