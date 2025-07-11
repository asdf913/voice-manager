package org.apache.bcel.generic;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.FieldOrMethodUtil;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.toolfactory.narcissus.Narcissus;

class LDCUtilTest {

	@Test
	void testNull() {
		//
		final Method[] ms = LDCUtil.class.getDeclaredMethods();
		//
		Method m = null;
		//
		for (int i = 0; ms != null && i < ms.length; i++) {
			//
			if ((m = ms[i]) == null || !Modifier.isStatic(m.getModifiers()) || m.isSynthetic()) {
				//
				continue;
				//
			} // if
				//
			Assertions.assertNull(
					Narcissus.invokeStaticMethod(m, toArray(Collections.nCopies(m.getParameterCount(), null))),
					Objects.toString(m));
			//
		} // for
			//
	}

	private static Object[] toArray(final Collection<?> instance) {
		return instance != null ? instance.toArray() : null;
	}

	@Test
	void testGetValue() throws IOException, ReflectiveOperationException {
		//
		final LDC ldc = new LDC(0);
		//
		Assertions.assertNull(LDCUtil.getValue(ldc, null));
		//
		Assertions.assertThrows(ClassFormatException.class, () -> LDCUtil.getValue(ldc, new ConstantPoolGen()));
		//
		final Class<?> clz = LDC.class;
		//
		try (final InputStream is = clz != null
				? clz.getResourceAsStream(
						String.format("/%1$s.class", StringsUtil.replace(Strings.CS, clz.getName(), ".", "/")))
				: null) {
			//
			final org.apache.bcel.classfile.Method m = JavaClassUtil.getMethod(
					ClassParserUtil.parse(is != null ? new ClassParser(is, null) : null),
					clz != null ? clz.getDeclaredMethod("getType", ConstantPoolGen.class) : null);
			//
			final ConstantPoolGen cpg = testAndApply(Objects::nonNull, FieldOrMethodUtil.getConstantPool(m),
					ConstantPoolGen::new, null);
			//
			final Instruction[] instructions = InstructionListUtil.getInstructions(MethodGenUtil
					.getInstructionList(testAndApply(Objects::nonNull, m, x -> new MethodGen(x, null, cpg), null)));
			//
			for (int i = 0; instructions != null && i < instructions.length; i++) {
				//
				if (ArrayUtils.get(instructions, i) instanceof LDC l) {
					//
					Assertions.assertNotNull(LDCUtil.getValue(l, cpg));
					//
				} // if
					//
			} // for
				//
		} // try
			//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final Function<T, R> functionTrue, final Function<T, R> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? apply(functionTrue, value) : apply(functionFalse, value);
	}

	private static <T, R> R apply(final Function<T, R> instance, final T value) {
		return instance != null ? instance.apply(value) : null;
	}

}