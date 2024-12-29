package org.apache.poi.ss.usermodel;

public interface CreationHelperUtil {

	static FormulaEvaluator createFormulaEvaluator(final CreationHelper instance) {
		return instance != null ? instance.createFormulaEvaluator() : null;
	}

	static ClientAnchor createClientAnchor(final CreationHelper instance) {
		return instance != null ? instance.createClientAnchor() : null;
	}

}