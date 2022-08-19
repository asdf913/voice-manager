package org.apache.bcel.classfile;

public interface CodeUtil {

	static byte[] getCode(final Code instance) {
		return instance != null ? instance.getCode() : null;
	}

}