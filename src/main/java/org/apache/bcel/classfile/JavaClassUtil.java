package org.apache.bcel.classfile;

public interface JavaClassUtil {

	static Method[] getMethods(final JavaClass instance) {
		return instance != null ? instance.getMethods() : null;
	}

}