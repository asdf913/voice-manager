package org.apache.bcel.generic;

public interface TypeUtil {

	static String getClassName(final Type instance) {
		return instance != null ? instance.getClassName() : null;
	}

}