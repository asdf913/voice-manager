package org.apache.bcel.generic;

import javassist.util.proxy.ProxyFactory;

public interface InvokeInstructionUtil {

	static String getMethodName(final InvokeInstruction instance, final ConstantPoolGen cpg) {
		//
		if (instance == null) {
			//
			return null;
			//
		} else if (ProxyFactory.isProxyClass(getClass(instance))) {
			//
			return instance.getMethodName(cpg);
			//
		} else if (cpg == null || cpg.getConstantPool() == null) {
			//
			return null;
			//
		} // if
			//
		return instance.getMethodName(cpg);
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	static String getClassName(final InvokeInstruction instance, final ConstantPoolGen cpg) {
		return instance != null ? instance.getClassName(cpg) : null;
	}

	static Type[] getArgumentTypes(final InvokeInstruction instance, final ConstantPoolGen cpg) {
		//
		if (instance == null) {
			//
			return null;
			//
		} else if (ProxyFactory.isProxyClass(getClass(instance))) {
			//
			return instance.getArgumentTypes(cpg);
			//
		} // if
			//
		return cpg != null ? instance.getArgumentTypes(cpg) : null;
		//
	}

}