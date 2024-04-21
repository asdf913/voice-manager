package org.apache.bcel.classfile;

import org.apache.commons.lang3.reflect.FieldUtils;

public interface FieldOrMethodUtil {

	static ConstantPool getConstantPool(final FieldOrMethod instance) {
		return instance != null ? instance.getConstantPool() : null;
	}

	static String getName(final FieldOrMethod instance) {
		//
		try {
			//
			if (instance == null || FieldUtils.readField(instance, "constant_pool", true) == null) {
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
		return instance.getName();
		//
	}

}