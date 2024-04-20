package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ACONST_NULL;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ARETURN;
import org.apache.bcel.generic.ATHROW;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.GOTO;
import org.apache.bcel.generic.IFNULL;
import org.apache.bcel.generic.INVOKEINTERFACE;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.NEW;
import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.TypedInstruction;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UrlAnnotationResourceTest {

	private static Method METHOD_CAST, METHOD_FOR_NAME, METHOD_GET_CLASS, METHOD_FILTER, METHOD_TO_INPUT_STREAM,
			METHOD_GET_DECLARED_METHODS = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = UrlAnnotationResource.class;
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_FOR_NAME = clz.getDeclaredMethod("forName", String.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_FILTER = clz.getDeclaredMethod("filter", Stream.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_TO_INPUT_STREAM = clz.getDeclaredMethod("toInputStream", Properties.class)).setAccessible(true);
		//
		(METHOD_GET_DECLARED_METHODS = clz.getDeclaredMethod("getDeclaredMethods", Class.class)).setAccessible(true);
		//
	}

	private UrlAnnotationResource instance = null;

	@BeforeEach
	private void beforeEach() {
		//
		instance = new UrlAnnotationResource();
		//
	}

	@Test
	void testThrowThrowableOnly() throws Throwable {
		//
		final Class<?> clz = UrlAnnotationResource.class;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(String.format("/%1$s.class", StringUtils.replace(clz.getName(), ".", "/")))
				: null) {
			//
			final org.apache.bcel.classfile.Method[] methods = JavaClassUtil.getMethods(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)));
			//
			org.apache.bcel.classfile.Method method = null;
			//
			Instruction[] ins = null;
			//
			boolean[] bs = null;
			//
			NEW n = null;
			//
			List<java.lang.reflect.Method> ms = null;
			//
			Class<?> c = null;
			//
			for (int i = 0; methods != null && i < methods.length; i++) {
				//
				if ((method = methods[i]) == null || (ins = InstructionListUtil
						.getInstructions(MethodGenUtil.getInstructionList(testAndApply(Objects::nonNull, method,
								x -> new MethodGen(x, null, new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(x))),
								null)))) == null) {
					//
					continue;
					//
				} // if
					//
				if (bs == null) {
					//
					bs = new boolean[4];
					//
				} else {
					//
					Arrays.fill(bs, false);
					//
				} // if
					//
				for (int j = 0; j < ins.length; j++) {
					//
					if (j == 0) {
						//
						bs[j] = (n = cast(NEW.class, ins[j])) != null;
						//
					} else if (j == 1) {
						//
						bs[j] = ins[j] instanceof DUP;
						//
					} else if (j == 2) {
						//
						bs[j] = ins[j] instanceof INVOKESPECIAL;
						//
					} else if (j == 3) {
						//
						bs[j] = ins[j] instanceof ATHROW;
						//
					} else {
						//
						break;
						//
					} // if
						//
				} // for.
					//
				final String name = getName(method);
				//
				if (Arrays.equals(new boolean[] { true, true, true, true }, bs)
						&& (ms = filter(Arrays.stream(clz.getDeclaredMethods()), x -> Objects.equals(getName(x), name))
								.toList()) != null
						&& ms.size() == 1 && Throwable.class.isAssignableFrom(c = Class
								.forName(getClassName(getType(n, new ConstantPoolGen(method.getConstantPool())))))) {
					//
					final java.lang.reflect.Method m = ms.get(0);
					//
					Assertions.assertThrows((Class<? extends Throwable>) c, () -> {
						//
						try {
							//
							invoke(m, instance, new Object[m.getParameterCount()]);
							//
						} catch (final InvocationTargetException e) {
							//
							throw e.getTargetException();
							//
						} // try
							//
					}, getName(method));
					//
				} // if
					//
			} // for
				//
		} // try
			//
	}

	private static Object invoke(final Method method, final Object instance, final Object... args)
			throws IllegalAccessException, InvocationTargetException {
		return method != null ? method.invoke(instance, args) : null;
	}

	@Test
	void testReturnNullOnly() throws Throwable {
		//
		final Class<?> clz = UrlAnnotationResource.class;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(String.format("/%1$s.class", StringUtils.replace(clz.getName(), ".", "/")))
				: null) {
			//
			final org.apache.bcel.classfile.Method[] methods = JavaClassUtil.getMethods(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)));
			//
			org.apache.bcel.classfile.Method method = null;
			//
			Instruction[] ins = null;
			//
			boolean[] bs = null;
			//
			List<java.lang.reflect.Method> ms = null;
			//
			for (int i = 0; methods != null && i < methods.length; i++) {
				//
				if ((method = methods[i]) == null || (ins = InstructionListUtil
						.getInstructions(MethodGenUtil.getInstructionList(testAndApply(Objects::nonNull, method,
								x -> new MethodGen(x, null, new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(x))),
								null)))) == null) {
					//
					continue;
					//
				} // if
					//
				if (bs == null) {
					//
					bs = new boolean[2];
					//
				} else {
					//
					Arrays.fill(bs, false);
					//
				} // if
					//
				for (int j = 0; j < ins.length; j++) {
					//
					if (j == 0) {
						//
						bs[j] = ins[j] instanceof ACONST_NULL;
						//
					} else if (j == 1) {
						//
						bs[j] = ins[j] instanceof ARETURN;
						//
					} else {
						//
						break;
						//
					} // if
						//
				} // for
					//
				final String name = getName(method);
				//
				if (Arrays.equals(new boolean[] { true, true }, bs)
						&& (ms = filter(Arrays.stream(clz.getDeclaredMethods()), x -> Objects.equals(getName(x), name))
								.toList()) != null
						&& ms.size() == 1) {
					//
					Assertions.assertNull(invoke(ms.get(0), instance));
					//
				} // if
					//
			} // for
				//
		} // try
			//
	}

	@Test
	void testIif() throws Throwable {
		//
		final Class<?> clz = UrlAnnotationResource.class;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(String.format("/%1$s.class", StringUtils.replace(clz.getName(), ".", "/")))
				: null) {
			//
			final org.apache.bcel.classfile.Method[] methods = JavaClassUtil.getMethods(
					ClassParserUtil.parse(testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)));
			//
			org.apache.bcel.classfile.Method method = null;
			//
			Instruction[] ins = null;
			//
			boolean[] bs = null;
			//
			List<java.lang.reflect.Method> ms = null;
			//
			for (int i = 0; methods != null && i < methods.length; i++) {
				//
				if ((method = methods[i]) == null || (ins = InstructionListUtil
						.getInstructions(MethodGenUtil.getInstructionList(testAndApply(Objects::nonNull, method,
								x -> new MethodGen(x, null, new ConstantPoolGen(FieldOrMethodUtil.getConstantPool(x))),
								null)))) == null) {
					//
					continue;
					//
				} // if
					//
				if (bs == null) {
					//
					bs = new boolean[7];
					//
				} else {
					//
					Arrays.fill(bs, false);
					//
				} // if
					//
				for (int j = 0; j < ins.length; j++) {
					//
					if (j == 0) {
						//
						bs[j] = ins[j] instanceof ALOAD;
						//
					} else if (j == 1) {
						//
						bs[j] = ins[j] instanceof IFNULL;
						//
					} else if (j == 2) {
						//
						bs[j] = ins[j] instanceof ALOAD;
						//
					} else if (j == 3) {
						//
						bs[j] = ins[j] instanceof INVOKEINTERFACE || ins[j] instanceof INVOKEVIRTUAL;
						//
					} else if (j == 4) {
						//
						bs[j] = ins[j] instanceof GOTO;
						//
					} else if (j == 5) {
						//
						bs[j] = ins[j] instanceof ACONST_NULL;
						//
					} else if (j == 6) {
						//
						bs[j] = ins[j] instanceof ARETURN;
						//
					} else {
						//
						break;
						//
					} // if
						//
				} // for
					//
				final String name = getName(method);
				//
				if (Arrays.equals(new boolean[] { true, true, true, true, true, true, true }, bs)
						&& (ms = filter(Arrays.stream(clz.getDeclaredMethods()), x -> Objects.equals(getName(x), name))
								.toList()) != null
						&& ms.iterator() != null) {
					//
					for (final java.lang.reflect.Method m : ms) {
						//
						if (m == null) {
							//
							continue;
							//
						} // if
							//
						m.setAccessible(true);
						//
						Assertions.assertNull(invoke(m, instance, new Object[m.getParameterCount()]));
						//
					} // for
						//
				} // if
					//
			} // for
				//
		} // try
			//
	}

	private static String getClassName(final Type instance) {
		return instance != null ? instance.getClassName() : null;
	}

	private static Type getType(final TypedInstruction instance, final ConstantPoolGen cpg) {
		return instance != null ? instance.getType(cpg) : null;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static String getName(final FieldOrMethod instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			final Function<T, R> functionFalse) {
		return test(predicate, value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T> boolean test(final Predicate<T> instance, final T value) {
		return instance != null && instance.test(value);
	}

	private static <T, R> R apply(final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

	@Test
	void testGetInputStream() throws IOException {
		//
		final UrlAnnotationResource instance = new UrlAnnotationResource();
		//
		try (final InputStream is = instance.getInputStream()) {
			//
			Assertions.assertNotNull(is);
			//
		} // try
			//
	}

	@Test
	void testForName() throws Throwable {
		//
		Assertions.assertNull(forName(null));
		//
		Assertions.assertNull(forName("A"));
		//
	}

	private static String toString(final Object instance) throws Throwable {
		return instance != null ? instance.toString() : null;
	}

	private static Class<?> forName(final String className) throws Throwable {
		try {
			final Object obj = invoke(METHOD_FOR_NAME, null, className);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = invoke(METHOD_GET_CLASS, null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class<?>) {
				return (Class<?>) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCaset() throws Throwable {
		//
		Assertions.assertNull(cast(null, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) throws Throwable {
		try {
			return (T) invoke(METHOD_CAST, null, clz, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testFilters() throws Throwable {
		//
		Assertions.assertNull(filter(null, null));
		//
		final Stream<?> empty = Stream.empty();
		//
		Assertions.assertSame(empty, filter(empty, null));
		//
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate)
			throws Throwable {
		try {
			final Object obj = invoke(METHOD_FILTER, null, instance, predicate);
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
	void testToInputStream() throws Throwable {
		//
		Assertions.assertNull(toInputStream(null));
		//
	}

	private static InputStream toInputStream(final Properties properties) throws Throwable {
		try {
			final Object obj = invoke(METHOD_TO_INPUT_STREAM, null, properties);
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
	void testGetDeclaredMethods() throws Throwable {
		//
		Assertions.assertNotNull(getDeclaredMethods(Class.class));
		//
	}

	private static Method[] getDeclaredMethods(final Class<?> instance) throws Throwable {
		try {
			final Object obj = invoke(METHOD_GET_DECLARED_METHODS, null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Method[]) {
				return (Method[]) obj;
			}
			throw new Throwable(toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}