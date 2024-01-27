package org.springframework.context.support;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class UtilTest {

	private static Method METHOD_GET_JAVA_IO_FILE_SYSTEM_FIELD, METHOD_TEST, METHOD_CONTAINS, METHOD_IS_STATIC = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = Util.class;
		//
		(METHOD_GET_JAVA_IO_FILE_SYSTEM_FIELD = clz.getDeclaredMethod("getJavaIoFileSystemField", Object.class))
				.setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_CONTAINS = clz.getDeclaredMethod("contains", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_IS_STATIC = clz.getDeclaredMethod("isStatic", Member.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "map")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "filter")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "toList")) {
					//
					return null;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private Stream<?> stream = null;

	@BeforeEach
	public void beforeEach() {
		//
		stream = Reflection.newProxy(Stream.class, new IH());
		//
	}

	@Test
	void testMap() {
		//
		Assertions.assertNull(Util.map(stream, null));
		//
		Assertions.assertNull(Util.map(Stream.empty(), null));
		//
	}

	@Test
	void testFilter() {
		//
		Assertions.assertNull(Util.filter(stream, null));
		//
		Assertions.assertNull(Util.filter(Stream.empty(), null));
		//
	}

	@Test
	void testGetText() throws NoSuchFieldException {
		//
		final JTextComponent jtc = new JTextField();
		//
		Narcissus.setField(jtc, Narcissus.findField(Util.getClass(jtc), "model"), null);
		//
		Assertions.assertNull(Util.getText(jtc));
		//
	}

	@Test
	void testSetText() throws NoSuchFieldException {
		//
		final JTextComponent jtc = new JTextField();
		//
		Narcissus.setField(jtc, Narcissus.findField(Util.getClass(jtc), "model"), null);
		//
		Assertions.assertDoesNotThrow(() -> Util.setText(jtc, null));
		//
	}

	@Test
	void testGetName() {
		//
		new FailableStream<>(Util.filter(Arrays.stream(Util.class.getDeclaredMethods()),
				m -> m != null && Objects.equals(Util.getName(m), "getName") && Modifier.isStatic(m.getModifiers())))
				.forEach(m -> Assertions.assertNull(m != null ? m.invoke(null, (Object) null) : null));
		//
	}

	@Test
	void testGetDeclaringClass() {
		//
		Assertions.assertNull(Util.getDeclaringClass(null));
		//
	}

	@Test
	void testIsAssignableFrom() {
		//
		Assertions.assertFalse(Util.isAssignableFrom(null, null));
		//
	}

	@Test
	void testPut() {
		//
		Assertions.assertDoesNotThrow(() -> Util.put(null, null, null));
		//
	}

	@Test
	void testGetAbsolutePath() throws Throwable {
		//
		final File file = Util.cast(File.class, Narcissus.allocateInstance(File.class));
		//
		Assertions.assertNull(Util.getAbsolutePath(file));
		//
		final Field f = getJavaIoFileSystemField(file);
		//
		final Object fs = Narcissus.getStaticField(f);
		//
		try {
			//
			Narcissus.setStaticField(f, null);
			//
			Assertions.assertNull(Util.getAbsolutePath(file));
			//
		} finally {
			//
			Narcissus.setStaticField(f, fs);
			//
		} // try
			//
	}

	@Test
	void testGetJavaIoFileSystemField() throws Throwable {
		//
		Assertions.assertNull(getJavaIoFileSystemField(null));
		//
	}

	private static Field getJavaIoFileSystemField(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_JAVA_IO_FILE_SYSTEM_FIELD.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field) {
				return (Field) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null));
		//
	}

	private static final <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(contains(null, null));
		//
		Assertions.assertFalse(contains(Collections.emptyList(), null));
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS.invoke(null, items, item);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToList() throws Throwable {
		//
		Assertions.assertNull(Util.toList(stream));
		//
		final Stream<?> empty = Stream.empty();
		//
		new FailableStream<>(Util.stream(FieldUtils.getAllFieldsList(Util.getClass(empty)))).forEach(f -> {
			//
			if (f == null || contains(Arrays.asList(Integer.TYPE, Boolean.TYPE), f.getType())
					|| !contains(Arrays.asList("sourceStage"), Util.getName(f))) {
				//
				return;
				//
			} // if
				//
			if (Modifier.isStatic(f.getModifiers())) {
				//
				Narcissus.setStaticField(f, null);
				//
			} else {
				//
				Narcissus.setField(empty, f, null);
				//
			} // if
				//
		});
		//
		Assertions.assertNull(Util.toList(empty));
		//
	}

	@Test
	void testIsStatic() throws Throwable {
		//
		Assertions.assertFalse(isStatic(null));
		//
		Assertions.assertTrue(isStatic(Boolean.class.getDeclaredField("TRUE")));
		//
	}

	private static boolean isStatic(final Member instance) throws Throwable {
		try {
			final Object obj = METHOD_IS_STATIC.invoke(null, instance);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}