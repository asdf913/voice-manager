package org.apache.bcel.classfile;

import org.apache.commons.lang3.reflect.FieldUtils;

public interface JavaClassUtil {

	static Method[] getMethods(final JavaClass instance) {
		return instance != null ? instance.getMethods() : null;
	}

	static Method getMethod(final JavaClass instance, final java.lang.reflect.Method m) {
		//
		if (instance == null) {
			//
			return null;
			//
		} // if
			//
		try {
			//
			if (FieldUtils.readDeclaredField(instance, "methods", true) == null) {
				//
				return null;
				//
			} // if
				//
		} catch (final IllegalAccessException e) {
			//
			throw new RuntimeException(e);
			//
		} // try
			//
		return instance.getMethod(m);
		//
	}

}