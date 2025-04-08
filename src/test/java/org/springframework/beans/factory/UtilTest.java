package org.springframework.beans.factory;

import java.io.IOException;
import java.io.InputStream;
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
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ARETURN;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GETFIELD;
import org.apache.bcel.generic.INVOKEINTERFACE;
import org.apache.bcel.generic.Instruction;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.FailableBiPredicate;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.stream.FailableStreamUtil;
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

	private static Method METHOD_GET_DECLARED_FIELD, METHOD_EXECUTE_FOR_EACH_METHOD4, METHOD_EXECUTE_FOR_EACH_METHOD5,
			METHOD_GET_RESOURCE_AS_STREAM, METHOD_EXECUTE_FOR_EACH_METHOD_3A, METHOD_EXECUTE_FOR_EACH_METHOD_3B,
			METHOD_EXECUTE_FOR_EACH_METHOD_3C;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = Util.class;
		//
		(METHOD_GET_DECLARED_FIELD = clz.getDeclaredMethod("getDeclaredField", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_EXECUTE_FOR_EACH_METHOD4 = clz.getDeclaredMethod("executeForEachMethod", Map.class, String.class,
				Object.class, FailableBiPredicate.class)).setAccessible(true);
		//
		(METHOD_EXECUTE_FOR_EACH_METHOD5 = clz.getDeclaredMethod("executeForEachMethod", Object.class, String.class,
				Map.class, Instruction[].class, ConstantPoolGen.class)).setAccessible(true);
		//
		(METHOD_GET_RESOURCE_AS_STREAM = clz.getDeclaredMethod("getResourceAsStream", Class.class, String.class))
				.setAccessible(true);
		//
		(METHOD_EXECUTE_FOR_EACH_METHOD_3C = clz.getDeclaredMethod("executeForEachMethod3c", Instruction[].class,
				ConstantPoolGen.class, Entry.class, Map.class)).setAccessible(true);
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

	private ALOAD aLoad = null;

	private GETFIELD getField = null;

	private INVOKEINTERFACE invokeInterface = null;

	private ARETURN aReturn = null;

	@BeforeEach
	void beforeEach() {
		//
		stream = Reflection.newProxy(Stream.class, ih = new IH());
		//
		aLoad = new ALOAD(0);
		//
		getField = new GETFIELD(0);
		//
		invokeInterface = new INVOKEINTERFACE(0, 1);
		//
		aReturn = new ARETURN();
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
	void testFlatMap() {
		//
		Assertions.assertNull(Util.flatMap(stream, null));
		//
		Assertions.assertNotNull(Util.flatMap(Stream.empty(), x -> Stream.of(x)));
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
		FailableStreamUtil.forEach(new FailableStream<>(Util.filter(Arrays.stream(Util.class.getDeclaredMethods()),
				m -> m != null && Objects.equals("matches", Util.getName(m)))), m -> {
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

	@Test
	void testMapToObj() {
		//
		final IntStream intStream = IntStream.empty();
		//
		Assertions.assertNull(Util.mapToObj(intStream, null));
		//
		Assertions.assertNotNull(Util.mapToObj(intStream, x -> null));
		//
	}

	@Test
	void testForEach() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> Util.forEach(IntStream.empty(), null));
		//
		Assertions.assertDoesNotThrow(() -> Util.forEach(IntStream.of(0), x -> {
		}));
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
		List<Consumer<?>> consumers = null;
		//
		for (final ClassInfo classInfo : classInfos) {
			//
			try {
				//
				if (Util.isAssignableFrom(Consumer.class, Class.forName(name = HasNameUtil.getName(classInfo)))
						&& !(clz = Class.forName(name)).isInterface() && !Modifier.isAbstract(clz.getModifiers())) {
					//
					System.out.println(name);
					//
					Util.add(consumers = ObjectUtils.getIfNull(consumers, ArrayList::new),
							Util.cast(Consumer.class, Narcissus.allocateInstance(clz)));
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
				if (Util.isAssignableFrom(Iterable.class, Class.forName(name = HasNameUtil.getName(classInfo)))
						&& !(clz = Class.forName(name)).isInterface() && !Modifier.isAbstract(clz.getModifiers())) {
					//
					final Iterable<?> iterable = Util.cast(Iterable.class, Narcissus.allocateInstance(clz));
					//
					System.out.println(name);
					//
					for (final Consumer<?> consumer : consumers) {
						//
						Assertions.assertDoesNotThrow(() -> Util.forEach(iterable, (Consumer) consumer), name);
						//
					} // for
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

	@Test
	void testExecuteForEachMethod() throws Throwable {
		//
		Assertions.assertTrue(executeForEachMethod(Collections.singletonMap(null, null), null, null, null));
		//
		Assertions.assertTrue(executeForEachMethod(Collections.singletonMap(null, null), null, null, (a, b) -> false));
		//
		final List<Instruction> instructions = new ArrayList<>(
				Arrays.asList(aLoad, getField, aLoad, invokeInterface, null));
		//
		for (int i = IterableUtils.size(instructions) - 1; i >= 0; i--) {
			//
			instructions.set(i, null);
			//
			Assertions.assertTrue(
					executeForEachMethod(null, null, null, instructions.toArray(new Instruction[] {}), null));
			//
		} // for
			//
	}

	private static boolean executeForEachMethod(final Map<String, String> map, final String name, final Object instance,
			final FailableBiPredicate<Object, String, ReflectiveOperationException> predicate) throws Throwable {
		try {
			final Object obj = METHOD_EXECUTE_FOR_EACH_METHOD4.invoke(null, map, name, instance, predicate);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static boolean executeForEachMethod(final Object instance, final String name,
			final Map<String, FailableFunction<Object, Object, Exception>> map, final Instruction[] instructions,
			final ConstantPoolGen cpg) throws Throwable {
		try {
			final Object obj = METHOD_EXECUTE_FOR_EACH_METHOD5.invoke(null, instance, name, map, instructions, cpg);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetResourceAsStream() throws Throwable {
		//
		Assertions.assertNull(getResourceAsStream(getClass(), null));
		//
	}

	private static InputStream getResourceAsStream(final Class<?> instance, final String name) throws Throwable {
		try {
			final Object obj = METHOD_GET_RESOURCE_AS_STREAM.invoke(null, instance, name);
			if (obj == null) {
				return null;
			} else if (obj instanceof InputStream) {
				return (InputStream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExecuteForEachMethod3a() throws Throwable {
		//
		Assertions.assertFalse(executeForEachMethod3a(new Instruction[] { aLoad, getField, invokeInterface, aReturn },
				null, null, null, null));
		//
		Assertions.assertTrue(executeForEachMethod3a(new Instruction[] { aLoad, getField, invokeInterface, aReturn },
				null, null, "", null));
		//
		Assertions.assertTrue(executeForEachMethod3a(new Instruction[] { aLoad, getField, invokeInterface, null }, null,
				null, null, null));
		//
		Assertions.assertTrue(
				executeForEachMethod3a(new Instruction[] { null, null, null, null }, null, null, null, null));
		//
	}

	private static boolean executeForEachMethod3a(final Instruction[] instructions, final ConstantPoolGen cpg,
			final Entry<String, Object> entry, final String methodName,
			final Map<String, FailableFunction<Object, Object, Exception>> map) throws Throwable {
		try {
			final Object obj = METHOD_EXECUTE_FOR_EACH_METHOD_3A.invoke(null, instructions, cpg, entry, methodName,
					map);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExecuteForEachMethod3b() throws Throwable {
		//
		Assertions.assertFalse(
				executeForEachMethod3b(new Instruction[] { aLoad, getField, invokeInterface, invokeInterface, aReturn },
						null, null, null, null));
		//
		Assertions.assertTrue(executeForEachMethod3b(
				new Instruction[] { aLoad, null, invokeInterface, invokeInterface, aReturn }, null, null, null, null));
		//
		Assertions.assertTrue(executeForEachMethod3b(
				new Instruction[] { aLoad, getField, invokeInterface, null, aReturn }, null, null, null, null));
		//
		Assertions.assertTrue(
				executeForEachMethod3b(new Instruction[] { aLoad, getField, invokeInterface, invokeInterface, aReturn },
						null, null, "", null));
		//
		Assertions.assertTrue(executeForEachMethod3b(
				new Instruction[] { aLoad, getField, invokeInterface, invokeInterface, null }, null, null, null, null));
		//
		Assertions.assertTrue(
				executeForEachMethod3b(new Instruction[] { null, null, null, null, null }, null, null, null, null));
		//
	}

	private static boolean executeForEachMethod3b(final Instruction[] instructions, final ConstantPoolGen cpg,
			final Entry<String, Object> entry, final String methodName,
			final Map<String, FailableFunction<Object, Object, Exception>> map) throws Throwable {
		try {
			final Object obj = METHOD_EXECUTE_FOR_EACH_METHOD_3B.invoke(null, instructions, cpg, entry, methodName,
					map);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testExecuteForEachMethod3c() throws Throwable {
		//
		Assertions.assertTrue(executeForEachMethod3c(new Instruction[] { aLoad, getField, null }, null, null, null));
		//
		Assertions.assertTrue(executeForEachMethod3c(new Instruction[] { null, getField, aReturn }, null, null, null));
		//
	}

	private static boolean executeForEachMethod3c(final Instruction[] instructions, final ConstantPoolGen cpg,
			final Entry<String, Object> entry, final Map<String, FailableFunction<Object, Object, Exception>> map)
			throws Throwable {
		try {
			final Object obj = METHOD_EXECUTE_FOR_EACH_METHOD_3C.invoke(null, instructions, cpg, entry, map);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAccept() {
		//
		Assertions.assertDoesNotThrow(() -> Util.accept((a, b) -> {
		}, null, null));
		//
		Assertions.assertDoesNotThrow(() -> Util.accept((a, b) -> {
		}, null, 0));
		//
	}

}