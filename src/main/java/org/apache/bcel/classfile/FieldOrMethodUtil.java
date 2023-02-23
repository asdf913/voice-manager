package org.apache.bcel.classfile;

public interface FieldOrMethodUtil {

	static ConstantPool getConstantPool(final FieldOrMethod instance) {
		return instance != null ? instance.getConstantPool() : null;
	}

}