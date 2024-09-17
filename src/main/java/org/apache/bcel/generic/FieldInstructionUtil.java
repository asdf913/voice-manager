package org.apache.bcel.generic;

import javassist.util.proxy.ProxyFactory;

public interface FieldInstructionUtil {

	static String getFieldName(final FieldInstruction instance, final ConstantPoolGen cpg) {
		//
		if (instance == null) {
			//
			return null;
			//
		} else if (ProxyFactory.isProxyClass(getClass(instance))) {
			//
			return instance.getFieldName(cpg);
			//
		} else if (cpg == null || cpg.getConstantPool() == null) {
			//
			return null;
			//
		} // if
			//
		return instance.getFieldName(cpg);
		//
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

}