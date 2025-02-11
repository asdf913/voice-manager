package org.apache.bcel.classfile;

import org.apache.bcel.generic.Type;

public interface FieldUtil {

	static Type getType(final Field instance) {
		return instance != null && instance.getConstantPool() != null ? instance.getType() : null;
	}

}