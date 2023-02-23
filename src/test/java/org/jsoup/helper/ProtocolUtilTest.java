package org.jsoup.helper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.generic.IFNE;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.LDC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.Reflection;

class ProtocolUtilTest {

	private static Method METHOD_GET_ALLOW_PROTOCOLS_CLASS, METHOD_GET_ALLOW_PROTOCOLS_INSTRUCTION_ARRAY,
			METHOD_GET_CLASS, METHOD_TO_STRING, METHOD_CONTAINS, METHOD_ADD, METHOD_TO_ARRAY, METHOD_MAP, METHOD_TEST,
			METHOD_GET_DECLARED_METHOD = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = ProtocolUtil.class;
		//
		(METHOD_GET_ALLOW_PROTOCOLS_CLASS = clz.getDeclaredMethod("getAllowProtocols", Class.class))
				.setAccessible(true);
		//
		(METHOD_GET_ALLOW_PROTOCOLS_INSTRUCTION_ARRAY = clz.getDeclaredMethod("getAllowProtocols", Instruction[].class,
				ConstantPool.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_TO_STRING = clz.getDeclaredMethod("toString", Object.class)).setAccessible(true);
		//
		(METHOD_CONTAINS = clz.getDeclaredMethod("contains", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_ADD = clz.getDeclaredMethod("add", Collection.class, Object.class)).setAccessible(true);
		//
		(METHOD_TO_ARRAY = clz.getDeclaredMethod("toArray", Collection.class, Object[].class)).setAccessible(true);
		//
		(METHOD_MAP = clz.getDeclaredMethod("map", Stream.class, Function.class)).setAccessible(true);
		//
		(METHOD_TEST = clz.getDeclaredMethod("test", Predicate.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_METHOD = clz.getDeclaredMethod("getDeclaredMethod", Class.class, String.class,
				Class[].class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Stream<?> stream = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "map")) {
					//
					return stream;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Test
	void testGetAllowProtocols() throws Throwable {
		//
		Assertions.assertDoesNotThrow(() -> ProtocolUtil.getAllowProtocols());
		//
		Assertions.assertNull(getAllowProtocols(null));
		//
		final LDC ldc = new LDC(0);
		//
		Assertions.assertNull(getAllowProtocols(new Instruction[] { ldc }, null));
		//
		Assertions.assertNull(getAllowProtocols(new Instruction[] { ldc, null, new IFNE(null) }, null));
		//
	}

	private static List<String> getAllowProtocols(final Class<?> clz) throws Throwable {
		try {
			final Object obj = METHOD_GET_ALLOW_PROTOCOLS_CLASS.invoke(null, clz);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static List<String> getAllowProtocols(final Instruction[] is, final ConstantPool cp) throws Throwable {
		try {
			final Object obj = METHOD_GET_ALLOW_PROTOCOLS_INSTRUCTION_ARRAY.invoke(null, is, cp);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetClass() throws Throwable {
		//
		Assertions.assertNull(getClass(null));
		//
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToString() throws Throwable {
		//
		Assertions.assertNull(toString(null));
		//
	}

	private static String toString(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_STRING.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testContains() throws Throwable {
		//
		Assertions.assertFalse(contains(null, null));
		//
		Assertions.assertTrue(contains(Collections.singleton(null), null));
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) throws Throwable {
		try {
			final Object obj = METHOD_CONTAINS.invoke(null, items, item);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testAdd() {
		//
		Assertions.assertDoesNotThrow(() -> add(null, null));
		//
	}

	private static <E> void add(final Collection<E> items, final E item) throws Throwable {
		try {
			METHOD_ADD.invoke(null, items, item);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToArray() throws Throwable {
		//
		Assertions.assertNull(toArray(null, null));
		//
		Assertions.assertNull(toArray(Collections.emptyList(), null));
		//
	}

	private static <T> T[] toArray(final Collection<T> instance, final T[] array) throws Throwable {
		try {
			return (T[]) METHOD_TO_ARRAY.invoke(null, instance, array);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMap() throws Throwable {
		//
		Assertions.assertNull(map(Stream.empty(), null));
		//
		Assertions.assertNull(map(Reflection.newProxy(Stream.class, new IH()), null));
		//
	}

	private static <T, R> Stream<R> map(final Stream<T> instance, final Function<? super T, ? extends R> mapper)
			throws Throwable {
		try {
			final Object obj = METHOD_MAP.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof Stream) {
				return (Stream) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTtest() throws Throwable {
		//
		Assertions.assertFalse(test(null, null));
		//
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) throws Throwable {
		try {
			final Object obj = METHOD_TEST.invoke(null, instance, value);
			if (obj instanceof Boolean) {
				return ((Boolean) obj).booleanValue();
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetDeclaredMethod() throws Throwable {
		//
		Assertions.assertNull(getDeclaredMethod(null, null));
		//
	}

	private static Method getDeclaredMethod(final Class<?> clz, final String name, final Class<?>... parameterTypes)
			throws Throwable {
		try {
			final Object obj = METHOD_GET_DECLARED_METHOD.invoke(null, clz, name, parameterTypes);
			if (obj == null) {
				return null;
			} else if (obj instanceof Method) {
				return (Method) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}