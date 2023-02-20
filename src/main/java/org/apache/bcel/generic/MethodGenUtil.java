package org.apache.bcel.generic;

public interface MethodGenUtil {

	static InstructionList getInstructionList(final MethodGen instance) {
		return instance != null ? instance.getInstructionList() : null;
	}

}