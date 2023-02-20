package org.apache.bcel.generic;

public interface InstructionListUtil {

	static Instruction[] getInstructions(final InstructionList instance) {
		return instance != null ? instance.getInstructions() : null;
	}

}