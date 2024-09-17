package org.apache.bcel.generic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.apache.bcel.classfile.ConstantPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoUtil;
import io.github.classgraph.HasNameUtil;
import io.github.toolfactory.narcissus.Narcissus;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyUtil;

class InvokeInstructionUtilTest {

	private static class MH implements MethodHandler {

		private ConstantPool constantPool = null;

		@Override
		public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args)
				throws Throwable {
			//
			final String methodName = thisMethod != null ? thisMethod.getName() : null;
			//
			if (self instanceof ConstantPoolGen) {
				//
				if (Objects.equals(methodName, "getConstantPool")) {
					//
					return constantPool;
					//
				} // if
					//
			} else if (self instanceof InvokeInstruction && Objects.equals(methodName, "getMethodName")) {
				//
				return null;
				//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Test
	void testNull() throws IllegalAccessException, InvocationTargetException {
		//
		final Method[] ms = InvokeInstructionUtil.class.getDeclaredMethods();
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
	void testGetMethodName() throws Throwable {
		//
		final MH mh = new MH();
		//
		Assertions.assertNull(
				InvokeInstructionUtil.getMethodName(ProxyUtil.createProxy(InvokeInstruction.class, mh), null));
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
		Class<?> clz;
		//
		ConstantPoolGen cpg = null;
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
				if (isAssignableFrom(InvokeInstruction.class, Class.forName(name = HasNameUtil.getName(classInfo)))
						&& !(clz = Class.forName(name)).isInterface() && !Modifier.isAbstract(clz.getModifiers())) {
					//
					final InvokeInstruction ii = cast(InvokeInstruction.class, Narcissus.allocateInstance(clz));
					//
					System.out.println(name);
					//
					Assertions.assertNull(InvokeInstructionUtil.getMethodName(ii, null), name);
					//
					Assertions.assertNull(InvokeInstructionUtil.getMethodName(ii,
							cpg = cpg != null ? cpg : ProxyUtil.createProxy(ConstantPoolGen.class, mh)), name);
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

	static <T> T cast(final Class<T> clz, final Object instance) {
		return clz != null && clz.isInstance(instance) ? clz.cast(instance) : null;
	}

	static boolean isAssignableFrom(final Class<?> a, final Class<?> b) {
		return a != null && b != null && a.isAssignableFrom(b);
	}

}