package org.apache.bcel.classfile;

import org.apache.bcel.generic.Type;

public interface MethodUtil {

	static Type[] getArgumentTypes(final Method instance) {
		//
		return instance != null && instance.getConstantPool() != null ? instance.getArgumentTypes() : null;
		//
	}

}