package io.github.classgraph;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ACONST_NULL;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ARETURN;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GOTO;
import org.apache.bcel.generic.IFNULL;
import org.apache.bcel.generic.INVOKEINTERFACE;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.TypeUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;

import io.github.toolfactory.narcissus.Narcissus;

class ClassInfoUtilTest {

	private static Method METHOD_GET_CLASS_NAME_JAVA_CLASS, METHOD_GET_CLASS, METHOD_FOR_NAME, METHOD_REMOVE_IF,
			METHOD_TEST_AND_APPLY, METHOD_CAST, METHOD_GET_FIELDS, METHOD_LENGTH, METHOD_GET_TYPE = null;

	@BeforeAll
	static void beforeAll() throws ReflectiveOperationException {
		//
		final Class<?> clz = ClassInfoUtil.class;
		//
		(METHOD_GET_CLASS_NAME_JAVA_CLASS = clz.getDeclaredMethod("getClassName", JavaClass.class)).setAccessible(true);
		//
		(METHOD_GET_CLASS = clz.getDeclaredMethod("getClass", Object.class)).setAccessible(true);
		//
		(METHOD_FOR_NAME = clz.getDeclaredMethod("forName", String.class)).setAccessible(true);
		//
		(METHOD_REMOVE_IF = clz.getDeclaredMethod("removeIf", Collection.class, Predicate.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class, Function.class,
				Function.class)).setAccessible(true);
		//
		(METHOD_CAST = clz.getDeclaredMethod("cast", Class.class, Object.class)).setAccessible(true);
		//
		(METHOD_GET_FIELDS = clz.getDeclaredMethod("getFields", JavaClass.class)).setAccessible(true);
		//
		(METHOD_LENGTH = clz.getDeclaredMethod("length", Object[].class)).setAccessible(true);
		//
		(METHOD_GET_TYPE = clz.getDeclaredMethod("getType", Field.class)).setAccessible(true);
		//
	}

	@Test
	void testIif() throws Throwable {
		//
		final Class<?> clz = ClassInfoUtil.class;
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
				final String name = FieldOrMethodUtil.getName(method);
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
						if (Modifier.isStatic(m.getModifiers())) {
							//
							Assertions.assertNull(m.invoke(null, new Object[m.getParameterCount()]));
							//
						} // if
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

	@Test
	void testBoolean() throws Throwable {
		//
		final Class<?> clz = ClassInfoUtil.class;
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
			Type[] argumentTypes = null;
			//
			for (int i = 0; methods != null && i < methods.length; i++) {
				//
				if ((method = methods[i]) == null) {
					//
					continue;
					//
				} // if
					//
				if ((argumentTypes = method.getArgumentTypes()) != null) {
					//
					final org.apache.bcel.classfile.Method m = method;
					//
					final Type argumentType0 = argumentTypes.length > 0 ? argumentTypes[0] : null;
					//
					if (argumentTypes.length == 1 && method.isStatic() && !method.isSynthetic()
							&& !StringUtils.startsWith(TypeUtil.getClassName(argumentType0), "[")) {
						//
						Assertions.assertDoesNotThrow(() -> Narcissus.invokeStaticMethod(Narcissus.findMethod(clz,
								FieldOrMethodUtil.getName(m), forName(TypeUtil.getClassName(argumentType0))),
								(Object) null));
						//
						if (Objects.equals("java.lang.Object", TypeUtil.getClassName(argumentType0))) {
							//
							Assertions.assertDoesNotThrow(() -> Narcissus.invokeStaticMethod(Narcissus.findMethod(clz,
									FieldOrMethodUtil.getName(m), forName(TypeUtil.getClassName(argumentType0))), ""));
							//
						} // if
							//
					} else if (argumentTypes.length == 2 && method.isStatic()) {
						//
						final Type argumentType1 = argumentTypes[1];
						//
						Assertions.assertDoesNotThrow(() -> Narcissus.invokeStaticMethod(Narcissus.findMethod(clz,
								FieldOrMethodUtil.getName(m), forName(TypeUtil.getClassName(argumentType0)),
								forName(TypeUtil.getClassName(argumentType1))), null, null));
						//
					} // if
						//
				} // if
					//
			} // for
				//
		} // try
			//
	}

	@Test
	void testGetClassName() throws Throwable {
		//
		Assertions.assertNull(getClassName(cast(JavaClass.class, Narcissus.allocateInstance(JavaClass.class))));
		//
	}

	private static String getClassName(final JavaClass instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS_NAME_JAVA_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof String) {
				return (String) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> getClass(final Object instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_CLASS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Class<?> forName(final String className) throws Throwable {
		try {
			final Object obj = METHOD_FOR_NAME.invoke(null, className);
			if (obj == null) {
				return null;
			} else if (obj instanceof Class) {
				return (Class<?>) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static <T, R> R testAndApply(final Predicate<T> predicate, final T value, final Function<T, R> functionTrue,
			final Function<T, R> functionFalse) throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		return instance != null && predicate != null ? instance.filter(predicate) : instance;
	}

	@Test
	void testRemoveIf() {
		//
		Assertions.assertDoesNotThrow(() -> removeIf(null, null));
		//
		Assertions.assertDoesNotThrow(() -> removeIf(Collections.emptySet(), null));
		//
		Assertions.assertDoesNotThrow(() -> removeIf(Collections.emptySet(), Predicates.alwaysFalse()));
		//
	}

	private static <T> void removeIf(final Collection<T> instance, final Predicate<T> predicate) throws Throwable {
		try {
			METHOD_REMOVE_IF.invoke(null, instance, predicate);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCast() throws Throwable {
		//
		final String string = "";
		//
		Assertions.assertSame(string, cast(String.class, string));
		//
		Assertions.assertNull(cast(Iterable.class, null));
		//
	}

	private static <T> T cast(final Class<T> clz, final Object instance) throws Throwable {
		try {
			return (T) METHOD_CAST.invoke(null, clz, instance);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetFields() throws Throwable {
		//
		Assertions.assertNull(getFields(cast(JavaClass.class, Narcissus.allocateInstance(JavaClass.class))));
		//
	}

	private static Field[] getFields(final JavaClass instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_FIELDS.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Field[]) {
				return (Field[]) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testLength() throws Throwable {
		//
		Assertions.assertEquals(0, length(null));
		//
		Assertions.assertEquals(0, length(new Object[] {}));
		//
	}

	private static int length(final Object[] instance) throws Throwable {
		try {
			final Object obj = METHOD_LENGTH.invoke(null, (Object) instance);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetType() throws Throwable {
		//
		Assertions.assertNull(getType(cast(Field.class, Narcissus.allocateInstance(Field.class))));
		//
	}

	private static Type getType(final Field instance) throws Throwable {
		try {
			final Object obj = METHOD_GET_TYPE.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof Type) {
				return (Type) obj;
			}
			throw new Throwable(Objects.toString(getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}