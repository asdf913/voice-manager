package org.springframework.beans.factory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.javatuples.valueintf.IValue0;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoUtil;
import io.github.classgraph.HasNameUtil;
import io.github.toolfactory.narcissus.Narcissus;

class UtilTest {

	private static Method METHOD_GET_DECLARED_FIELD = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		(METHOD_GET_DECLARED_FIELD = Util.class.getDeclaredMethod("getDeclaredField", Class.class, String.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean addAll = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "filter")) {
					//
					return proxy;
					//
				} else if (Objects.equals(methodName, "map") || Objects.equals(methodName, "collect")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Collection) {
				//
				if (Objects.equals(methodName, "addAll")) {
					//
					return addAll;
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "putAll")) {
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

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		stream = Reflection.newProxy(Stream.class, ih = new IH());
		//
	}

	@Test
	void testCast() throws Throwable {
		//
		Assertions.assertNull(Util.cast(Object.class, null));
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static String toString(final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	@Test
	void testIsAssignableFrom() throws Throwable {
		//
		Assertions.assertFalse(Util.isAssignableFrom(Object.class, null));
		//
		Assertions.assertTrue(Util.isAssignableFrom(Object.class, String.class));
		//
		Assertions.assertFalse(Util.isAssignableFrom(String.class, Object.class));
		//
	}

	@Test
	void testAddAll() {
		//
		Assertions.assertDoesNotThrow(() -> Util.addAll(null, null));
		//
		if (ih != null) {
			//
			ih.addAll = Boolean.TRUE;
			//
		} // if
			//
		Assertions.assertDoesNotThrow(() -> Util.addAll(Reflection.newProxy(Collection.class, ih), null));
		//
	}

	@Test
	void testFilter() {
		//
		Assertions.assertSame(stream, Util.filter(stream, null));
		//
	}

	@Test
	void testMap() {
		//
		Assertions.assertNull(Util.map(stream, null));
		//
	}

	@Test
	void testMatcher() {
		//
		Assertions.assertNull(Util.matcher(Pattern.compile(""), null));
		//
		Assertions.assertNull(Util.matcher(Util.cast(Pattern.class, Narcissus.allocateInstance(Pattern.class)), ""));
		//
	}

	@Test
	void testMatches() {
		//
		new FailableStream<>(Util.filter(Arrays.stream(Util.class.getDeclaredMethods()),
				m -> m != null && Objects.equals("matches", Util.getName(m)))).forEach(m -> {
					//
					if (m == null) {
						//
						return;
						//
					} // if
						//
					Assertions.assertEquals(Boolean.FALSE, m.invoke(null, new Object[m.getParameterCount()]),
							Util.toString(m));
					//
				});
		//
		Assertions.assertTrue(Util.matches(Util.matcher(Pattern.compile("\\d"), "1")));
		//
		Assertions.assertFalse(Util.matches(Util.cast(Matcher.class, Narcissus.allocateInstance(Matcher.class))));
		//
		Assertions.assertFalse(Util.matches("", null));
		//
		final String string = new String("a");
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
		Assertions.assertFalse(Util.matches(string, ""));
		//
		Assertions.assertFalse(Util.matches("", string));
		//
	}

	@Test
	void testCollect() {
		//
		Assertions.assertNull(Util.collect(stream, null));
		//
		final Stream<Object> empty = Stream.empty();
		//
		Assertions.assertNull(Util.collect(empty, null));
		//
		Assertions.assertNull(Util.collect(stream, null, null, null));
		//
		Assertions.assertNull(Util.collect(empty, null, null, null));
		//
		final Supplier<Object> supplier = () -> null;
		//
		Assertions.assertNull(Util.collect(empty, supplier, null, null));
		//
		Assertions.assertNull(Util.collect(empty, supplier, (a, b) -> {
		}, null));
		//
	}

	@Test
	void testGroupCount() {
		//
		Assertions.assertEquals(0, Util.groupCount(null));
		//
	}

	@Test
	void testGroup() {
		//
		Assertions.assertNull(Util.group(null));
		//
		Assertions.assertNull(Util.group(null, 0));
		//
	}

	@Test
	void testFind() {
		//
		Assertions.assertFalse(Util.find(Util.cast(Matcher.class, Narcissus.allocateInstance(Matcher.class))));
		//
	}

	@Test
	void testOpenStream() throws IOException {
		//
		Assertions.assertNull(Util.openStream(Util.cast(URL.class, Narcissus.allocateInstance(URL.class))));
		//
	}

	@Test
	void testLongValue() {
		//
		final long zero = Long.valueOf(0);
		//
		Assertions.assertEquals(zero, Util.longValue(null, zero));
		//
	}

	@Test
	void testIsStatic() throws Throwable {
		//
		Assertions.assertFalse(Util.isStatic(getDeclaredField(Boolean.class, "value")));
		//
		Assertions.assertTrue(Util.isStatic(getDeclaredField(Boolean.class, "TRUE")));
		//
	}

	@Test
	void testToCharArray() {
		//
		Assertions.assertNull(Util.toCharArray(Util.cast(String.class, Narcissus.allocateInstance(String.class))));
		//
	}

	@Test
	void testGetDeclaredField() throws Throwable {
		//
		Assertions.assertNull(getDeclaredField(Object.class, null));
		//
	}

	private static Field getDeclaredField(final Class<?> instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_FIELD.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field) {
				return (Field) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testPutAll() {
		//
		Assertions.assertDoesNotThrow(() -> Util.putAll(null, null));
		//
		final Map<Object, Object> map = Collections.emptyMap();
		//
		Assertions.assertDoesNotThrow(() -> Util.putAll(map, null));
		//
		Assertions.assertDoesNotThrow(() -> Util.putAll(map, map));
		//
		Assertions.assertDoesNotThrow(() -> Util.putAll(Reflection.newProxy(Map.class, ih), null));
		//
	}

	@Test
	void testOrElse() {
		//
		Assertions.assertNull(Util.orElse(Optional.empty(), null));
		//
	}

	@Test
	void testFindFirst() {
		//
		Assertions.assertEquals(Optional.empty(), Util.findFirst(Stream.empty()));
		//
	}

	@Test
	void testAppend() {
		//
		Assertions.assertDoesNotThrow(() -> Util.append(null, ' '));
		//
		Assertions.assertDoesNotThrow(() -> Util
				.append(Util.cast(StringBuilder.class, Narcissus.allocateInstance(StringBuilder.class)), ' '));
		//
	}

	@Test
	void testOr() {
		//
		Assertions.assertTrue(Util.or(false, true, null));
		//
		Assertions.assertFalse(Util.or(false, false, null));
		//
		Assertions.assertTrue(Util.or(false, false, true));
		//
	}

	@Test
	void testAnd() {
		//
		Assertions.assertFalse(Util.and(true, false));
		//
		Assertions.assertTrue(Util.and(true, true, null));
		//
	}

	@Test
	void testLength() {
		//
		Assertions.assertEquals(1, Util.length(new Object[] { 1 }));
		//
	}

	@Test
	void testNull() {
		//
		final Method[] ms = Util.class.getDeclaredMethods();
		//
		Method m = null;
		//
		List<Object> list = null;
		//
		Class<?>[] parameterTypes = null;
		//
		Object invokeStaticMethod;
		//
		String toString;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			parameterTypes = (m = ms[i]) != null ? m.getParameterTypes() : null;
			//
			if (m == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()
					|| (Objects.equals(Util.getName(m), "or") && Arrays
							.equals(new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class }, parameterTypes))
					|| (Objects.equals(Util.getName(m), "and") && Arrays
							.equals(new Class<?>[] { Boolean.TYPE, Boolean.TYPE, boolean[].class }, parameterTypes))) {
				//
				continue;
				//
			} // if
				//
			clear(list = ObjectUtils.getIfNull(list, ArrayList::new));
			//
			if (parameterTypes != null) {
				//
				for (final Class<?> clz : parameterTypes) {
					//
					if (Objects.equals(clz, Integer.TYPE)) {
						//
						list.add(Integer.valueOf(0));
						//
					} else if (Objects.equals(clz, Character.TYPE)) {
						//
						list.add(Character.valueOf(' '));
						//
					} else if (Objects.equals(clz, Long.TYPE)) {
						//
						list.add(Long.valueOf(0));
						//
					} else {
						//
						list.add(null);
						//
					} // if
						//
				} // for
					//
			} // if
				//
			invokeStaticMethod = Narcissus.invokeStaticMethod(m, toArray(list));
			//
			toString = Objects.toString(m);
			//
			if (ArrayUtils.contains(new Class<?>[] { Boolean.TYPE, Integer.TYPE, Long.TYPE, IValue0.class },
					m.getReturnType())) {
				//
				Assertions.assertNotNull(invokeStaticMethod, toString);
				//
			} else {
				//
				Assertions.assertNull(invokeStaticMethod, toString);
				//
			} // if
				//
		} // for
			//
	}

	private void clear(final Collection<Object> instance) {
		if (instance != null) {
			instance.clear();
		}
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testApplyAsInt() {
		//
		final int zero = 0;
		//
		Assertions.assertEquals(0, Util.applyAsInt(IntUnaryOperator.identity(), zero, zero));
		//
	}

	@Test
	void testChars() throws Throwable {
		//
		final Iterable<ClassInfo> classInfos = ClassInfoUtil.getClassInfos();
		//
		if (classInfos == null || classInfos.iterator() == null) {
			//
			return;
			//
		} // if
			//
		String name = null;
		//
		Class<?> clz = null;
		//
		for (final ClassInfo classInfo : classInfos) {
			//
			if (classInfo == null) {
				//
				continue;
				//
			} // if
				//
			try {
				//
				if (Util.isAssignableFrom(CharSequence.class, Class.forName(name = HasNameUtil.getName(classInfo)))
						&& !(clz = Class.forName(name)).isInterface() && !Modifier.isAbstract(clz.getModifiers())) {
					//
					final CharSequence cs = Util.cast(CharSequence.class, Narcissus.allocateInstance(clz));
					//
					System.out.println(name);
					//
					Assertions.assertDoesNotThrow(() -> Util.chars(cs), name);
					//
				} // if
					//
			} catch (final Throwable e) {
				//
				System.err.println(name);
				//
				throw e;
				//
			} // try
				//
		} // for
			//
	}

}