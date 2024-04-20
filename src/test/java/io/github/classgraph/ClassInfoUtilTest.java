package io.github.classgraph;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GOTO;
import org.apache.bcel.generic.ICONST;
import org.apache.bcel.generic.IFEQ;
import org.apache.bcel.generic.IFNULL;
import org.apache.bcel.generic.INVOKEINTERFACE;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.IRETURN;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClassInfoUtilTest {

	@Test
	void testIif() throws IOException, IllegalAccessException, InvocationTargetException {
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
	void testBoolean() throws IOException, IllegalAccessException, InvocationTargetException {
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
					bs = new boolean[10];
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
						bs[j] = ins[j] instanceof ALOAD;
						//
					} else if (j == 4) {
						//
						bs[j] = ins[j] instanceof INVOKEINTERFACE;
						//
					} else if (j == 5) {
						//
						bs[j] = ins[j] instanceof IFEQ;
						//
					} else if (j == 6) {
						//
						bs[j] = ins[j] instanceof ICONST;
						//
					} else if (j == 7) {
						//
						bs[j] = ins[j] instanceof GOTO;
						//
					} else if (j == 8) {
						//
						bs[j] = ins[j] instanceof ICONST;
						//
					} else if (j == 9) {
						//
						bs[j] = ins[j] instanceof IRETURN;
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
				if (Arrays.equals(new boolean[] { true, true, true, true, true, true, true, true, true, true }, bs)
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
							Assertions.assertEquals(Boolean.FALSE, m.invoke(null, new Object[m.getParameterCount()]));
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

	private static String getName(final FieldOrMethod instance) {
		return instance != null ? instance.getName() : null;
	}

	private static String getName(final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	private static <T> Stream<T> filter(final Stream<T> instance, final Predicate<? super T> predicate) {
		return instance != null && predicate != null ? instance.filter(predicate) : instance;
	}

}