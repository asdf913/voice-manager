package org.apache.bcel.generic;

public interface LDCUtil {

	static Object getValue(final LDC instance, final ConstantPoolGen cpg) {
		return instance != null && cpg != null ? instance.getValue(cpg) : null;
	}

}